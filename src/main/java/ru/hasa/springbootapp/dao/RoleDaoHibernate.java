package ru.hasa.springbootapp.dao;

import org.springframework.stereotype.Repository;
import ru.hasa.springbootapp.model.Role;
import ru.hasa.springbootapp.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoHibernate implements RoleDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Role findByRolename(String name) {
        return entityManager.createQuery("FROM Role r WHERE r.name = :name", Role.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("FROM Role", Role.class).getResultList();
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }


}
