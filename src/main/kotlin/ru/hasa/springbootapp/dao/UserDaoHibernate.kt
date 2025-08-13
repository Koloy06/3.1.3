package ru.hasa.springbootapp.dao

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.hasa.springbootapp.model.User
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class UserDaoHibernate : UserDao {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun findByUsername(username: String): User =
        entityManager.createQuery("FROM User u WHERE u.username = :username", User::class.java)
            .setParameter("username", username)
            .singleResult

    @Transactional
    override fun saveUser(user: User): User {
        user.password = passwordEncoder().encode(user.password)
        entityManager.persist(user)
        return user
    }

    @Transactional
    override fun removeUserById(id: Long) {
        val user = entityManager.find(User::class.java, id)
        if (user != null) {
            entityManager.remove(user)
        }
    }

    override fun getAllUsers(): List<User> =
        entityManager.createQuery("FROM User", User::class.java).resultList

    override fun showUser(id: Long): User? =
        entityManager.find(User::class.java, id)

    @Transactional
    override fun updateUser(user: User): User {
        val user1 = entityManager.find(User::class.java, user.id)
        user1.username = user.username
        user1.age = user.age
        user1.mail = user.mail
        user1.roles = user.roles
        if (user.password.isNotEmpty()) {
            user1.password = passwordEncoder().encode(user.password)
        }
        return user1
    }

    override fun findByMail(mail: String): User =
        entityManager.createQuery("FROM User u WHERE u.mail = :mail", User::class.java)
            .setParameter("mail", mail)
            .singleResult

    private fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
