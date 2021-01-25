package ru.hasa.springbootapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.hasa.springbootapp.model.User;
import ru.hasa.springbootapp.service.RoleService;
import ru.hasa.springbootapp.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/users/user.html", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal) {
        model.addAttribute("user", userService.findByMail(principal.getName()));
        model.addAttribute("user_auth", principal);
        return "users/user";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/")
    public String printWelcome() {
        return "login";
    }

    @GetMapping(value = "/admin/users")
    public String printUser(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user_auth", principal);
        return "/users/users";
    }

    @GetMapping(value = "/admin/users/new")
    public String addUser(Model model, Principal principal) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user_auth", principal);
        return "/users/new";
    }

    @PostMapping(value = "/admin/users/create")
    public String create(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/users/";
    }

    @GetMapping(value = "/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin/users/";
    }

    @GetMapping(value = "/admin/users/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.showUser(id));
        return "/users/edit";
    }

    @PostMapping("/admin/users/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/users/";
    }

}