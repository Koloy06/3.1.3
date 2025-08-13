package ru.hasa.springbootapp.service

import org.springframework.stereotype.Service
import ru.hasa.springbootapp.dao.RoleDao
import ru.hasa.springbootapp.model.Role

@Service
class RoleServiceImpl(private val roleDao: RoleDao) : RoleService {
    override fun getAllRoles(): List<Role> = roleDao.getAllRoles()
}
