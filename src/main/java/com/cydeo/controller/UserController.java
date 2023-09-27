package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/create")
    public String openUserCreate(Model model){



        model.addAttribute("user", new UserDTO());

        // Do not generate list of roles in a controller. Envoke appropriate service instead.
        // List of Roles should be the act of bringing all roles from the DataBase.
        // Role Service has the findAll method that we need.


        //model.addAttribute("listOfRoles", User)


        return "/user/create";
    }

}
