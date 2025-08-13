package ru.hasa.springbootapp.service

import ru.hasa.springbootapp.dto.RoleDto
import ru.hasa.springbootapp.dto.UserDto

interface ApiRestUserService {
    fun getAllUsers(): List<UserDto>
    fun getUserById(id: Long): UserDto
    fun saveUser(userDto: UserDto): UserDto
    fun updateUser(userDto: UserDto): UserDto
    fun deleteUser(id: Long)
    fun getAllRoles(): List<RoleDto>
}
