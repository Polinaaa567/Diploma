package local.arch.infrastructure.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDateTime);

            newUser.setDateCreation(timestamp);
            entityManager.persist(newUser);

            return "{ \n \"status\": true,\n\"message\":\"Регистрация прошла успешно\", \n\"id\": "
                    + newUser.getIdUser() + "\n}";
        }
        return "{\n\"status\": false, \"message\": \"Произошла ошибка при регистрации\"\n}";
    }

    @Override
    public String loginUser(User user) {
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
