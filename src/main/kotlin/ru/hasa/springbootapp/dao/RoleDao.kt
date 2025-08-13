package ru.hasa.springbootapp.dao

import ru.hasa.springbootapp.model.Role

interface RoleDao {
    fun findByRolename(name: String): Role?
    fun getAllRoles(): List<Role>
    fun getRoleById(id: Long): Role?
}
