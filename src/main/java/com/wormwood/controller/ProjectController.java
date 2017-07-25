package com.wormwood.controller;

import com.google.common.collect.Maps;
import com.wormwood.DTO.Project;
import com.wormwood.response.Response;
import com.wormwood.result.ResultEnum;
import com.wormwood.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kasimodo on 2017-07-06.
 */

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("/toAddProject")
    public String toAddProject() {
        return "projectMgmt/addProject";
    }

    @RequestMapping("/toProjectList")
    public String toProjectList() {
        return "projectMgmt/projectList";
    }


    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Response> addProject(@RequestBody @Valid Project project) {
        project.setCrtBy("admin");
        project.setCrtOn(new Date());
        projectService.insertProject(project);
        return new Response(ResultEnum.SUCCESS).build();
    }

    @RequestMapping(value = "/getAllProjectList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response<List<Project>>> getAllProjectList() {

        List<Project> allProject = projectService.getAllProject();
        return new Response(allProject, ResultEnum.SUCCESS).build();
    }


    @RequestMapping(value = "/removeProject/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Response> removeProject(@PathVariable("id") Integer id) {
        projectService.deleteProject(id);
        return new Response(ResultEnum.SUCCESS).build();
    }


    @RequestMapping(value = "/getProjectById/{id}", method = RequestMethod.GET)
    public ModelAndView getProjectById(@PathVariable("id") Integer id) {
        Project project = projectService.findProjectById(id);
        Map<String, Object> model = Maps.newHashMap();
        model.put("data", project);
        return new ModelAndView("projectMgmt/editProject", model);
    }


    @RequestMapping(value = "/updateProject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Response> updateProject(@RequestBody @Valid Project project) {
        projectService.updateProject(project);
        return new Response(ResultEnum.SUCCESS).build();
    }


}
