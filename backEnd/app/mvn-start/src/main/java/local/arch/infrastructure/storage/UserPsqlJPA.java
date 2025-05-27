package local.arch.infrastructure.storage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import at.favre.lib.crypto.bcrypt.BCrypt;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import local.arch.application.interfaces.page.user.IStorageUser;
import local.arch.domain.entities.page.Rating;
import local.arch.domain.entities.page.User;
import local.arch.infrastructure.storage.model.ECertificate;
import local.arch.infrastructure.storage.model.ERole;
import local.arch.infrastructure.storage.model.EUser;

@Named
public class UserPsqlJPA implements IStorageUser {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    @Transactional
    public User registrationUser(User user) {

        String queryString = "SELECT p FROM EUser p WHERE p.login = :login";

        TypedQuery<EUser> query = entityManager.createQuery(queryString, EUser.class);
        query.setParameter("login", user.getLogin());

        try {
            EUser person = query.getSingleResult();
            if (person != null) {
                User u = new User();
                u.setMsg("Пользователь с таким login уже существует");
                u.setStatus(false);

                return u;
            }
        } catch (NoResultException e) {
            EUser newUser = new EUser();

            ERole role = entityManager
                    .createQuery("SELECT r FROM ERole r WHERE r.nameRoles = :nameRoles", ERole.class)
                    .setParameter("nameRoles", "Пользователь")
                    .getSingleResult();

            newUser.setLogin(user.getLogin());

            String bcryptHashString = BCrypt.withDefaults().hashToString(14, user.getPassword().toCharArray());
            newUser.setPassword(bcryptHashString);

            newUser.setFkRoleID(role);
            newUser.setDateCreation(timestamp);
            entityManager.persist(newUser);

            User u = new User();
            u.setMsg("Регистрация прошла успешно");
            u.setStatus(true);
            u.setUserID(newUser.getIdUser());

            return u;
        }

        User u = new User();
        u.setMsg("Произошла ошибка при регистрации");
        u.setStatus(false);

        return u;
    }

    @Override
    public User loginUser(User user) {
        entityManager.clear();

        String queryString = "SELECT p FROM EUser p WHERE p.login = :login";

        TypedQuery<EUser> query = entityManager.createQuery(queryString, EUser.class);
        query.setParameter("login", user.getLogin());

        try {
            EUser existingUser = query.getSingleResult();

            if (BCrypt.verifyer().verify(user.getPassword().toCharArray(), existingUser.getPassword()).verified) {
                User u = new User();
                u.setMsg("Успешный вход");
                u.setStatus(true);
                u.setUserID(existingUser.getIdUser());
                u.setRole(existingUser.getFkRoleID().getNameRoles());

                return u;
            } else {
                User u = new User();
                u.setMsg("Неверный логин или пароль");
                u.setStatus(false);

                return u;
            }
        } catch (NoResultException e) {
            User u = new User();
            u.setMsg("Неверный логин или пароль");
            u.setStatus(false);

            return u;
        }
    }

    @Override
    public User receiveUserData(Integer idUser) {
        EUser result = entityManager.find(EUser.class, idUser);

        User user = new User();

        user.setAgeStamp(result.getAgeStamp());
        user.setClothingSize(result.getClothingSize());
        user.setName(result.getFirstName());
        user.setLastName(result.getLastName());
        user.setPatronymic(result.getPatronymic());
        user.setFormEducation(result.getFormEducation());
        user.setBasisEducation(result.getBasisEducation());

        return user;
    }

    @Override
    @Transactional
    public void updateUserData(User user) {
        EUser result = entityManager.find(EUser.class, user.getUserID());

        result.setAgeStamp(user.getAgeStamp());
        result.setClothingSize(user.getClothingSize());
        result.setFirstName(user.getName());
        result.setLastName(user.getLastName());
        result.setPatronymic(user.getPatronymic());
        result.setBasisEducation(user.getBasisEducation());
        result.setFormEducation(user.getFormEducation());

        entityManager.merge(result);
    }

    @Override
    public List<Rating> receiveUserRating(Integer userID) {

        List<Object[]> results = entityManager.createQuery(
                " Select u.idUser, coalesce(sum(e.numberPointsEvent), 0) + coalesce(sum(l.numberPoints), 0) as point, "
                        +
                        " u.lastName, u.firstName " +
                        " From EPoints p " +
                        " left join EEvent e on p.fkEventID = e " +
                        " left join ELessons l on p.fkLessonID = l " +
                        " left join EUser u on p.fkUserID = u " +
                        " left join ERole r on u.fkRoleID = r " +
                        " where u.lastName is not null and u.firstName is not null and r.nameRoles not like :role " +
                        " group by p.fkUserID, u.lastName, u.firstName, r.nameRoles, u.idUser " +
                        " order by point desc, u.lastName desc ",
                Object[].class).setParameter("role", "Администратор").getResultList();

        List<Rating> ratingList = new ArrayList<>();
        for (Object[] res : results) {
            Integer level = entityManager.createQuery(
                    " Select min(le.level) " +
                            " From ELevel le " +
                            " where :points <= le.maxPoints",
                    Integer.class)
                    .setParameter("points", (Long) res[1])
                    .getSingleResult();

            Integer maxPoint = entityManager.createQuery(
                    " Select min(le.maxPoints) " +
                            " From ELevel le " +
                            " where :points <= le.maxPoints",
                    Integer.class)
                    .setParameter("points", (Long) res[1])
                    .getSingleResult();

            User user = new User();
            Rating rating = new Rating();

            user.setUserID((Integer) res[0]);
            user.setLastName((String) res[2]);
            user.setName((String) res[3]);

            rating.setInfo(user);
            rating.setPoint(((Long) res[1]).intValue());
            rating.setMaxPoint(maxPoint);
            rating.setLevel(level);

            ratingList.add(rating);
        }

        return ratingList;
    }

    @Override
    public User findUser(Integer userID) {
        EUser euser = entityManager.find(EUser.class, userID);

        User user = new User();
        user.setLastName(euser.getLastName());
        user.setName(euser.getFirstName());
        user.setPatronymic(euser.getPatronymic());

        return user;
    }

    @Override
    public Rating receiveCertificate(Integer userID) {
        EUser user = entityManager.find(EUser.class, userID);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        List<ECertificate> resCertificate = entityManager
                .createQuery("select p from ECertificate p where p.fkUserID = :user", ECertificate.class)
                .setParameter("user", user).getResultList();

        Long totalPoints = entityManager.createQuery(
                " Select coalesce(sum(e.numberPointsEvent), 0) + coalesce(sum(l.numberPoints), 0) as point " +
                        " From EPoints p " +
                        " left join EEvent e on p.fkEventID = e " +
                        " left join ELessons l on p.fkLessonID = l " +
                        " where p.fkUserID = :user ",
                Long.class)
                .setParameter("user", user)
                .getSingleResult();

        Integer level = entityManager.createQuery(
                " Select min(le.level) " +
                        " From ELevel le " +
                        " where :points <= le.maxPoints",
                Integer.class)
                .setParameter("points", totalPoints)
                .getSingleResult();

        Integer maxPoint = entityManager.createQuery(
                " Select min(le.maxPoints) " +
                        " From ELevel le " +
                        " where :points <= le.maxPoints",
                Integer.class)
                .setParameter("points", totalPoints)
                .getSingleResult();

        Rating achievements = new Rating();
        achievements.setCertificates(
                resCertificate.stream()
                        .map(ECertificate::getImageURL)
                        .collect(Collectors.toList()));
        achievements.setLevel(level != null ? level : 0);
        achievements.setMaxPoint(maxPoint != null ? maxPoint : 0);
        achievements.setPoint(totalPoints.intValue());

        return achievements;
    }

    @Override
    public String changeUserPasswd(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeUserPasswd'");
    }
}
