package com.cydeo.controller;


import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final RoleService roleService;
    private final UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String openUserCreate(Model model){

        model.addAttribute("user", new UserDTO());
        model.addAttribute("listOfRoles", roleService.listAllRoles());
        model.addAttribute("listOfUsers", userService.listAllUsers());

        return "/user/create";
    }


/*
    @PostMapping("/create")
    public String insertUser(@ModelAttribute("user") UserDTO user, Model model){


        userService.save(user);


        return "redirect:/user/create";
    }


    @GetMapping("/update/{username}") // use the username as a path parameter
    public String editUser(@PathVariable("username") String username, Model model){
        // Will use the selected user's username as a path parameter to update a user

        // Need these to be able to populate the list of roles and the users table
        model.addAttribute("listOfRoles", roleService.findAll());
        model.addAttribute("listOfUsers", userService.findAll());

        // I need to send my chosen user from path parameter instead of a new userDTO
        model.addAttribute("user", userService.findById(username) );
        return "user/update";



    }

    @PostMapping("/update")
    public String postUpdate(@ModelAttribute("user") UserDTO user){

        userService.update(user);

        return "redirect:/user/create";

    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username){

        userService.deleteById(username);

        return "redirect:/user/create";
    }

     */


}
