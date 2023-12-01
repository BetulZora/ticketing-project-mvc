package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


        model.addAttribute("project", new ProjectDTO());
        //changing the project listing method so that the view shows the projects restricted by loggedInManager
        //model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("projects", projectService.listAllProjectDetails());
        model.addAttribute("managers", userService.listAllByRole("manager"));

        return "project/create";
    }

    @PostMapping("/create")
    public String insertProject(ProjectDTO project, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors())
        {
            //changing the project listing method so that the view shows the projects restricted by loggedInManager
            //model.addAttribute("projects", projectService.listAllProjects());
            model.addAttribute("projects", projectService.listAllProjectDetails());
            model.addAttribute("managers", userService.listAllByRole("managers"));
            return "/project/create";
        }

        projectService.save(project);
        return "redirect:/project/create";
    }
    @GetMapping("/delete/{projectCode}")
    public String delete(@PathVariable("projectCode") String projectCode){
        //perform a soft delete
        projectService.delete(projectCode);
        return "redirect:/project/create";
    }
    @GetMapping("/complete/{projectCode}")
    public String completeProject(@PathVariable("projectCode") String projectCode){

        projectService.complete(projectCode);
        return "redirect:/project/create";

    }


    @GetMapping("/update/{projectCode}")
    public String editProject(@PathVariable("projectCode") String projectCode, Model model){

        model.addAttribute("project", projectService.getByProjectCode(projectCode));
        //changing the project listing method so that the view shows the projects restricted by loggedInManager
        //model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("projects", projectService.listAllProjectDetails());
        model.addAttribute("managers", userService.listAllByRole("manager"));

        return "project/update";
    }

    @PostMapping("/update")
    public String updateProject(@ModelAttribute("project")ProjectDTO project, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            //changing the project listing method so that the view shows the projects restricted by loggedInManager
            //model.addAttribute("projects", projectService.listAllProjects());
            model.addAttribute("projects", projectService.listAllProjectDetails());
            model.addAttribute("managers", userService.listAllByRole("manager"));
            return "/project/update";

        }
        projectService.update(project);

        return "redirect:/project/create";
    }

    @GetMapping("/manager/project-status")
    public String projectStatus(Model model){
        List<ProjectDTO> projects = projectService.listAllProjectDetails();

        model.addAttribute("projects", projects);
        return "/manager/project-status";
    }




}
