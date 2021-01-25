package ru.hasa.springbootapp.dao;

import ru.hasa.springbootapp.model.User;

import java.util.List;

public interface UserDao {

    User findByUsername(String username);

    User saveUser(User user);

    void removeUserById(Long id);

    List<User> getAllUsers();

    User showUser(Long id);

    User updateUser(User user);

    User findByMail(String mail);
}