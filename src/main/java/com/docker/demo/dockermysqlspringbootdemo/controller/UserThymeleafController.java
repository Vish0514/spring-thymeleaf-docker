package com.docker.demo.dockermysqlspringbootdemo.controller;

import com.docker.demo.dockermysqlspringbootdemo.dto.UserDto;
import com.docker.demo.dockermysqlspringbootdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserThymeleafController {

    private final UserService userService;

    // Show list of users
    @GetMapping
    public String getAll(Model model) {
        List<UserDto> users = userService.getAll();
        model.addAttribute("users", users);
        return "user-list"; // Return user-list.html view
    }

    // Show form to add a new user
    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "user-form"; // user-form.html for adding a new user
    }

    // Show form to edit a user
    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        UserDto user = userService.getById(id);
        model.addAttribute("user", user);
        return "edit-user-form"; // edit-user-form.html for editing user
    }

    // Add or update user
    @PostMapping
    public String saveOrUpdate(@Valid @ModelAttribute("user") UserDto userDto) {
        if (userDto.getId() != null) {
            userService.update(userDto);
        } else {
            userService.save(userDto);
        }
        return "redirect:/users"; // Redirect to user list
    }

    // Delete user
    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/users"; // Redirect to user list after delete
    }
}
