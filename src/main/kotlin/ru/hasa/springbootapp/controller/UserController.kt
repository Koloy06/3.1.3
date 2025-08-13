package ru.hasa.springbootapp.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import ru.hasa.springbootapp.model.User
import ru.hasa.springbootapp.service.RoleService
import ru.hasa.springbootapp.service.UserService
import java.security.Principal

@Controller
@RequestMapping("/")
class UserController(
    private val userService: UserService,
    private val roleService: RoleService
) {

    @RequestMapping(value = ["/users/user.html"], method = [RequestMethod.GET])
    fun printWelcome(model: ModelMap, principal: Principal): String {
        model.addAttribute("user", userService.findByMail(principal.name))
        model.addAttribute("user_auth", principal)
        return "users/user"
    }

    @RequestMapping(value = ["login"], method = [RequestMethod.GET])
    fun loginPage(): String = "login"

    @GetMapping("/")
    fun printWelcome(): String = "login"

    @GetMapping("/admin/users")
    fun printUser(model: Model, principal: Principal): String {
        model.addAttribute("users", userService.getAllUsers())
        model.addAttribute("roles", roleService.getAllRoles())
        model.addAttribute("user_auth", principal)
        return "/users/users"
    }

    @GetMapping("/admin/users/new")
    fun addUser(model: Model, principal: Principal): String {
        model.addAttribute("user", User())
        model.addAttribute("roles", roleService.getAllRoles())
        model.addAttribute("user_auth", principal)
        return "/users/new"
    }

    @PostMapping("/admin/users/create")
    fun create(@ModelAttribute("user") user: User): String {
        userService.saveUser(user)
        return "redirect:/admin/users/"
    }

    @GetMapping("/admin/users/delete/{id}")
    fun deleteUser(@PathVariable("id") id: Long): String {
        userService.removeUserById(id)
        return "redirect:/admin/users/"
    }

    @GetMapping("/admin/users/edit/{id}")
    fun editUser(model: Model, @PathVariable("id") id: Long): String {
        model.addAttribute("user", userService.showUser(id))
        return "/users/edit"
    }

    @PostMapping("/admin/users/update")
    fun updateUser(@ModelAttribute("user") user: User): String {
        userService.updateUser(user)
        return "redirect:/admin/users/"
    }
}
