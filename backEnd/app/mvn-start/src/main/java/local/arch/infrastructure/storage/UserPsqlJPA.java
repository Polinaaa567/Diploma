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
import local.arch.apllication.interfaces.user.IStorageUser;
import local.arch.domain.entities.User;
import local.arch.infrastructure.storage.model.ERole;
import local.arch.infrastructure.storage.model.EUser;

@Named
public class UserPsqlJPA implements IStorageUser {

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    public User updateUserData(User user) {

        throw new UnsupportedOperationException("Unimplemented method 'updateUserData'");
    }

    @Override
    public User receiveUserData(Integer idUser, User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveUserData'");
    }

    @Override
    public List<User> receiveUserRating() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveUserRating'");
    }

    @Override
    public List<User> receiveCertificate(Integer userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveCertificate'");
    }

    @Override
    @Transactional
    public String registrationUser(User user) {

        String queryString = user.getEmail() != null
                ? "SELECT p FROM EUser p WHERE p.email =:email"
                : "SELECT p FROM EUser p WHERE p.number_phone =:numberPhone";

        TypedQuery<EUser> query = entityManager.createQuery(queryString, EUser.class);
        if (user.getEmail() != null) {
            query.setParameter("email", user.getEmail());
        } else {
            query.setParameter("numberPhone", user.getNumberPhone());
        }

        try {
            EUser person = query.getSingleResult();
            if (person != null) {
                return "false";
            }
        } catch (NoResultException e) {
            EUser newUser = new EUser();
            ERole role = entityManager
                    .createQuery("SELECT r FROM ERole r WHERE r.nameRoles =:nameRoles", ERole.class)
                    .setParameter("nameRoles", "Пользователь")
                    .getSingleResult();

            newUser.setEmail(user.getEmail());
            newUser.setNumberPhone(user.getNumberPhone());

            String bcryptHashString = BCrypt.withDefaults().hashToString(14, user.getPassword().toCharArray());
            newUser.setPassword(bcryptHashString);

            newUser.setFkRoleID(role);
            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDateTime);

            newUser.setDateCreation(timestamp);
            entityManager.persist(newUser);

            return "" + newUser.getIdUser();
        }
        return "false";
    }

    @Override
    public String loginUser(User user) {
        String queryString = user.getEmail() != null
                ? "SELECT p FROM EUser p WHERE p.email =:email"
                : "SELECT p FROM EUser p WHERE p.number_phone =:numberPhone";

        TypedQuery<EUser> query = entityManager.createQuery(queryString, EUser.class);
        if (user.getEmail() != null) {
            query.setParameter("email", user.getEmail());
        } else {
            query.setParameter("numberPhone", user.getNumberPhone());
        }

        try {
            EUser existingUser = query.getSingleResult();

            if (BCrypt.verifyer().verify(user.getPassword().toCharArray(), existingUser.getPassword()).verified) {
                return "" + existingUser.getIdUser();
            } else {
                return "Неверный номер телефона/почта или пароль";
            }
        } catch (NoResultException e) {
            return "Пользователь не найден";
        }
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
