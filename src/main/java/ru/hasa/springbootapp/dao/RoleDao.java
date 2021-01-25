package ru.hasa.springbootapp.dao;

import ru.hasa.springbootapp.model.Role;

import java.util.List;

public interface RoleDao {

    Role findByRolename(String name);

    List<Role> getAllRoles();

    Role getRoleById(Long id);
}
