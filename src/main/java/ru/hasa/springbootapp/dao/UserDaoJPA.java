package ru.hasa.springbootapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hasa.springbootapp.model.Role;

public interface UserDaoJPA extends JpaRepository<Role, Long> {
}
