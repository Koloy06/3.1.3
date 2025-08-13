package ru.hasa.springbootapp.service

import ru.hasa.springbootapp.model.Role

interface RoleService {
    fun getAllRoles(): List<Role>
}
