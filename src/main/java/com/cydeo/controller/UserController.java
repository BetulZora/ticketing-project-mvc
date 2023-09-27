package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    RoleService roleService;
    UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
        // need the constructor for successful dependency injection
    }

    @GetMapping("/create")
    public String openUserCreate(Model model){



        model.addAttribute("user", new UserDTO());

        // Do not generate list of roles in a controller. Invoke appropriate service instead.
        // List of Roles should be the act of bringing all roles from the DataBase.
        // RoleService has the findAll method that we need.


        model.addAttribute("listOfRoles", roleService.findAll());
        model.addAttribute("listOfUsers", userService.findAll());


        return "/user/create";
    }

    @PostMapping("/create")
    public String insertUser(@ModelAttribute("user") UserDTO user, Model model){


        userService.save(user);


        return "redirect:/user/create";
    }

}
