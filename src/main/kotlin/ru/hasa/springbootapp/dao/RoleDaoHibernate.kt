package ru.hasa.springbootapp.dao

import org.springframework.stereotype.Repository
import ru.hasa.springbootapp.model.Role
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class RoleDaoHibernate : RoleDao {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun findByRolename(name: String): Role =
        entityManager.createQuery("FROM Role r WHERE r.name = :name", Role::class.java)
            .setParameter("name", name)
            .singleResult

    override fun getAllRoles(): List<Role> =
        entityManager.createQuery("FROM Role", Role::class.java).resultList

    override fun getRoleById(id: Long): Role? =
        entityManager.find(Role::class.java, id)
}
