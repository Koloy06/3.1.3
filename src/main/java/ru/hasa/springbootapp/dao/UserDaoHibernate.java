package ru.hasa.springbootapp.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.hasa.springbootapp.model.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoHibernate implements UserDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
        return entityManager.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username).getSingleResult();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        entityManager.persist(user);
        return user;
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User showUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User user1 = entityManager.find(User.class, user.getId());
        user1.setUsername(user.getUsername());
        user1.setAge(user.getAge());
        user1.setMail(user.getMail());
        user1.setRoles(user.getRoles());
        if (!user.getPassword().isEmpty()) {
            user1.setPassword(passwordEncoder().encode(user.getPassword()));
        }
        return user;
    }

    @Override
    public User findByMail(String mail) {
        return entityManager.createQuery("FROM User u WHERE u.mail = :mail", User.class)
                .setParameter("mail", mail).getSingleResult();
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
