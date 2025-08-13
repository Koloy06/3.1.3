package ru.hasa.springbootapp.dao

import ru.hasa.springbootapp.model.User

interface UserDao {
    fun findByUsername(username: String): User?
    fun saveUser(user: User): User
    fun removeUserById(id: Long)
    fun getAllUsers(): List<User>
    fun showUser(id: Long): User?
    fun updateUser(user: User): User
    fun findByMail(mail: String): User?
}
