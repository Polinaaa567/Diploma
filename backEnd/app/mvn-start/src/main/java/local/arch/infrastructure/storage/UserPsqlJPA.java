package local.arch.infrastructure.storage;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import local.arch.application.interfaces.user.IStorageUser;
import local.arch.domain.entities.User;
import local.arch.infrastructure.storage.model.ERole;
import local.arch.infrastructure.storage.model.EUser;

@Named
public class UserPsqlJPA implements IStorageUser {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    @Transactional
    public String registrationUser(User user) {

        String queryString = "SELECT p FROM EUser p WHERE p.login = :login";

        TypedQuery<EUser> query = entityManager.createQuery(queryString, EUser.class);
        query.setParameter("login", user.getLogin());

        try {
            EUser person = query.getSingleResult();
            if (person != null) {
                return "{\n \"status\": false, \n\"message\": \"Пользователь с таким login уже существует\" \n}";
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

            // EPoints newUserPoints = new EPoints();
            // newUserPoints.setDateChange(timestamp);
            // newUserPoints.setFkUserID(newUser);
            // newUserPoints.setPoints(1);
            // entityManager.persist(newUserPoints);

            return "{ \n \"status\": true,\n\"message\":\"Регистрация прошла успешно\", \n\"id\": "
                    + newUser.getIdUser() + "\n}";
        }
        return "{\n\"status\": false, \"message\": \"Произошла ошибка при регистрации\"\n}";
    }

    @Override
    public String loginUser(User user) {
        entityManager.clear();
        String queryString = "SELECT p FROM EUser p WHERE p.login = :login";

        TypedQuery<EUser> query = entityManager.createQuery(queryString, EUser.class);
        query.setParameter("login", user.getLogin());

        try {
            EUser existingUser = query.getSingleResult();

            if (BCrypt.verifyer().verify(user.getPassword().toCharArray(), existingUser.getPassword()).verified) {
                return "{ \n \"status\": true, \n\"message\": \"Успешный вход\",\n\"id\": " + existingUser.getIdUser()
                        + ",\n\"nameRole\": \""
                        + existingUser.getFkRoleID().getNameRoles() + "\" \n}";
            } else {
                return "{ \n\"status\": false, \n\"message\": \"Неверный логин или пароль\" \n}";
            }
        } catch (NoResultException e) {
            return "{ \n \"status\": false, \n\"message\": \"Неверный логин или пароль\" \n}";
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
    public List<User> receiveUserRating(Integer userID) {
        
        
        
        throw new UnsupportedOperationException("Unimplemented method 'receiveUserRating'");
    }

    @Override
    public List<User> receiveCertificate(Integer userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveCertificate'");
    }

    @Override
    public boolean findUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUser'");
    }

    @Override
    public String changeUserPasswd(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeUserPasswd'");
    }
}

// select u.id_user, (sum(e.number_points_event ) + sum(l.number_points)) as point, u.last_name, u.first_name , (
//     SELECT Min(level_number)
//     FROM levels 
//     WHERE (sum(e.number_points_event ) + sum(l.number_points)) <= max_number_points
// ) AS user_level
// from users_points p 
// left join events e on p.fk_event_id = e.event_id 
// left join lessons l on p.fk_lesson_id = l.lesson_id 
// left join users u on u.id_user = p.fk_user_id
// group by p.fk_user_id, u.last_name, u.first_name, u.fk_role_id, u.id_user
// having u.last_name IS NOT NULL and u.first_name IS NOT NULL and u.fk_role_id != 1
// order by point desc;
