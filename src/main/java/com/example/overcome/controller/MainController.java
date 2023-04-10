package com.example.overcome.controller;

import com.example.overcome.Service.UserServiceImp;
import com.example.overcome.repository.RoleRepository;
import com.example.overcome.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class MainController {

    private final UserServiceImp userService;

    private final RoleRepository roleRepository;

    public MainController(UserServiceImp userService,  RoleRepository roleRepository) {
        this.userService = userService;

        this.roleRepository = roleRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login2";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout2";
    }

    @GetMapping()
    public String auth() {
        return "home";
    }

    @GetMapping("/user/{email}")
    public String userPage(@PathVariable("email") String email, Model model, Principal principal) {
        email = principal.getName();
        model.addAttribute("user",
                userService.findByEmail(email));


        return "user";
    }


    //testing space
    @GetMapping("/admin")
    public String tryPage(
            Model model,
            Principal principal,
            @ModelAttribute("userToSave") User userToSave) {
        model.addAttribute("admin", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("butAdmin", userService.findAllButAdmin());

        return "test";
    }

    @PostMapping("/admin")
    public String adding(User userToSave) {
        userService.add(userToSave);

        return "redirect:/admin";
    }


    @DeleteMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


    @PatchMapping("/admin/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/admin/get/{email}")
    public String getUser(@PathVariable("email") String email, Model model, Principal principal) {
        model.addAttribute("users", userService.findAllButAdmin());
        model.addAttribute("emailUser", userService.findByEmail(email));
        model.addAttribute("adminData", userService.findByEmail(principal.getName()));
        return "get";
    }
}