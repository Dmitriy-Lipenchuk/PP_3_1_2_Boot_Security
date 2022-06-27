package ru.kata.spring.boot_security.demo.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.web.models.User;
import ru.kata.spring.boot_security.demo.web.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminUsersController {

    private final UserService userService;

    @Autowired
    public AdminUsersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "/users";
    }

    @GetMapping("/user/{id}")
    public String singleUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));

        return "/single";
    }

    @GetMapping("/create")
    public String newUser(Model model) {
        model.addAttribute("user", new User());

        return "/create";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/create";
        }

        userService.addUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));

        return "/edit";
    }

    @PatchMapping()
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/edit";
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
