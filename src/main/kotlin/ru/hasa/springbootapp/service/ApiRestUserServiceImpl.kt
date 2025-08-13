package ru.hasa.springbootapp.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hasa.springbootapp.dao.RoleDao
import ru.hasa.springbootapp.dao.UserDao
import ru.hasa.springbootapp.dto.RoleDto
import ru.hasa.springbootapp.dto.UserDto
import ru.hasa.springbootapp.model.Role
import ru.hasa.springbootapp.model.User

@Service
class ApiRestUserServiceImpl(
    private val userDao: UserDao,
    private val roleDao: RoleDao
) : ApiRestUserService {

    override fun getAllUsers(): List<UserDto> {
        val allUsers = userDao.getAllUsers()
        return mapListUserToListUserDto(allUsers)
    }

    override fun getUserById(id: Long): UserDto = mapUserToUserDto(userDao.showUser(id)!!)

    @Transactional
    override fun saveUser(userDto: UserDto): UserDto {
        val user = mapUserDtoToUser(userDto)
        val persistRoles = user.roles.map { roleDao.getRoleById(it.id!!)!! }.toMutableSet()
        user.roles = persistRoles
        return mapUserToUserDto(userDao.saveUser(user))
    }

    @Transactional
    override fun updateUser(userDto: UserDto): UserDto {
        val user = mapUserDtoToUser(userDto)
        val persistRoles = user.roles.map { roleDao.getRoleById(it.id!!)!! }.toMutableSet()
        user.roles = persistRoles
        return mapUserToUserDto(userDao.updateUser(user))
    }

    @Transactional
    override fun deleteUser(id: Long) {
        userDao.removeUserById(id)
    }

    override fun getAllRoles(): List<RoleDto> {
        val allRole = roleDao.getAllRoles()
        return mapListRoleToListRoleDto(allRole)
    }

    private fun mapListRoleToListRoleDto(allRole: List<Role>): List<RoleDto> =
        allRole.map { role -> RoleDto(role.id, role.name) }

    private fun mapListUserToListUserDto(allUsers: List<User>): List<UserDto> =
        allUsers.map { user ->
            UserDto(
                id = user.id,
                username = user.username,
                age = user.age,
                mail = user.mail,
                roles = mapSetRoleToSetRoleDto(user.roles).toMutableSet()
            )
        }

    private fun mapUserToUserDto(user: User): UserDto =
        UserDto(
            id = user.id,
            username = user.username,
            age = user.age,
            mail = user.mail,
            password = user.password,
            roles = mapSetRoleToSetRoleDto(user.roles).toMutableSet()
        )

    private fun mapUserDtoToUser(userDto: UserDto): User =
        User(
            id = userDto.id,
            username = userDto.username ?: "",
            age = userDto.age ?: 0,
            mail = userDto.mail ?: "",
            password = userDto.password ?: "",
            roles = mapSetRoleDtoToSetRole(userDto.roles).toMutableSet()
        )

    private fun mapSetRoleToSetRoleDto(roles: MutableSet<Role>): Set<RoleDto> =
        roles.map { role -> RoleDto(role.id, role.name) }.toSet()

    private fun mapSetRoleDtoToSetRole(rolesDto: MutableSet<RoleDto>): Set<Role> =
        rolesDto.map { role -> Role(role.id, role.name) }.toSet()
}
