package com.wormwood.controller;

import com.google.common.collect.Maps;
import com.wormwood.DTO.Project;
import com.wormwood.service.ProjectService;
import com.wormwood.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by kasimodo on 2016-07-26.
 */

@Controller
public class HomePageController {

    private final Logger log = LoggerFactory.getLogger(HomePageController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String rootPage() {
        return "redirect:/index";
    }

    @RequestMapping(value = "/index/{type}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("type") String type) {
        Integer totalProject = projectService.findTotalProject();
        List<Project> projectList = projectService.findByProjectType(type);
        Map<String, Object> model = Maps.newHashMap();
        model.put("data", projectList);
        model.put("total", totalProject);
        return new ModelAndView("index", model);
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }


    @RequestMapping(value = "/toProject", method = RequestMethod.GET)
    public String gg(HttpServletRequest request, HttpServletResponse response) {

        return "";
    }
}
