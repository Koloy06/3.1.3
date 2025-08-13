package ru.hasa.springbootapp.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import ru.hasa.springbootapp.model.Role
import ru.hasa.springbootapp.model.User

interface UserService : UserDetailsService {
    fun saveUser(user: User)
    fun removeUserById(id: Long)
    fun getAllUsers(): List<User>
    fun showUser(id: Long): User?
    fun updateUser(user: User): User
    fun findByUsername(username: String): User?
    override fun loadUserByUsername(username: String): UserDetails
    fun mapRolesToAuthorities(roles: Collection<Role>): Collection<GrantedAuthority>
    fun findByMail(mail: String): User?
}
