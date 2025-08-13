package ru.hasa.springbootapp.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hasa.springbootapp.dao.UserDao
import ru.hasa.springbootapp.model.Role
import ru.hasa.springbootapp.model.User

@Service
class UserServiceImpl(private val userDao: UserDao) : UserService {

    override fun saveUser(user: User) {
        userDao.saveUser(user)
    }

    override fun removeUserById(id: Long) {
        userDao.removeUserById(id)
    }

    override fun getAllUsers(): List<User> = userDao.getAllUsers()

    override fun showUser(id: Long): User? = userDao.showUser(id)

    override fun updateUser(user: User): User = userDao.updateUser(user)

    override fun findByUsername(username: String): User? = userDao.findByUsername(username)

    @Transactional
    override fun loadUserByUsername(mail: String): UserDetails {
        val user = findByMail(mail) ?: throw UsernameNotFoundException(String.format("Mail '%s' not found", mail))
        return org.springframework.security.core.userdetails.User(user.mail, user.password, mapRolesToAuthorities(user.roles))
    }

    override fun mapRolesToAuthorities(roles: Collection<Role>): Collection<GrantedAuthority> =
        roles.map { r -> SimpleGrantedAuthority(r.name) }

    override fun findByMail(mail: String): User? = userDao.findByMail(mail)
}
