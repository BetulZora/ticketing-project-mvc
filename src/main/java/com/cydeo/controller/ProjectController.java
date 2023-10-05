package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class ProjectController {

    ProjectService projectService;
    UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String projectCreate(Model model){


        List<UserDTO> managers = userService.findAll().stream().filter(p->p.getRole().getDescription().equals("Manager"))
                        .collect(Collectors.toList());

        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("managers", managers);

        return "project/create";
    }

    @PostMapping("/create")
    public String insertProject(ProjectDTO project){

        // Although status can be set from the controller, it is better to do it in the SericeImpl
        //project.setStatus(Status.OPEN);

        projectService.save(project);


        return "redirect:/project/create";
    }
}
