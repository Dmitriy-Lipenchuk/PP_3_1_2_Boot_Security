package ru.kata.spring.boot_security.demo.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.web.models.Role;
import ru.kata.spring.boot_security.demo.web.models.User;
import ru.kata.spring.boot_security.demo.web.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminUsersController {

    private final UserService userService;

    @Autowired
    public AdminUsersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getUsers(Model model, Principal principal) {
        User admin = userService.findByUsername(principal.getName());

        model.addAttribute("admin", admin);
        model.addAttribute("empty_user", new User());
        model.addAttribute("empty_role", new Role());
        model.addAttribute("users", userService.getAllUsers());

        return "/users";
    }

    @GetMapping("/user/{id}")
    public String singleUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));

        return "/single";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, @ModelAttribute("empty_role") Role role,
                             BindingResult bindingResult) {

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));

        if (role.getRole() != null) {
            role.setRoleId();
            user.setRoles(Collections.singletonList(role));
        } else {
            user.setRoles(Collections.emptyList());
        }

        userService.addUser(user);

        return "redirect:/admin";
    }

    @PatchMapping()
    public String update(@ModelAttribute("user") @Valid User user, @ModelAttribute("empty_role") Role role,
                         BindingResult bindingResult) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));

        if (role.getRole() != null) {
            role.setRoleId();
            user.setRoles(Collections.singletonList(role));
        } else {
            user.setRoles(Collections.emptyList());
        }

        userService.updateUser(user);

        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.removeUser(id);

        return "redirect:/admin";
    }
}
