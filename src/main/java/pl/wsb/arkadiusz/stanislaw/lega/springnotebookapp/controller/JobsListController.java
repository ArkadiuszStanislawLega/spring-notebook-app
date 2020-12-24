package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.JobsListService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.UserService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.stat.url;

import javax.validation.Valid;
import java.util.*;

@Controller
public class JobsListController {

    @Autowired
    private JobsListService jobsListService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = url.JOBS_LIST_NEW_PAGE)
    public String create(Model model) {
        JobsList jobsList = new JobsList();
        model.addAttribute("jobsList", jobsList);
        return url.JOBS_LIST_NEW_PAGE;
    }

    @PostMapping(value = url.JOBS_LIST_SAVE_PAGE)
    public String saveJobsList(@ModelAttribute("jobsList") JobsList jobsList) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        user.addJobsList(jobsList);
        jobsList.addOwner(user);

        if (jobsList.getCreated() == null) {
            jobsList.setCreated(new Date());
        }
        jobsList.setEdited(new Date());
        jobsListService.saveJobsList(jobsList);
        return "redirect:" + url.JOBS_LIST_HOME_PAGE;
    }

    @GetMapping(value = url.JOBS_LIST_HOME_PAGE)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView(url.JOBS_LIST_HOME_PAGE);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        modelAndView.addObject("information", "Użytkownik " + user.getUserName() + " posiada " + user.getJobsList().size() + " list.");
        modelAndView.addObject("jobsLists", user.getJobsList());
        return modelAndView;
    }

    @GetMapping(value = url.JOBS_LIST_EDIT_PAGE+"/{id}")
    public ModelAndView edit(@PathVariable(name = "id") int id) {
        ModelAndView modelAndView = new ModelAndView(url.JOBS_LIST_EDIT_PAGE);
        modelAndView.addObject("jobsList", jobsListService.find(id));
        return modelAndView;
    }

    @GetMapping(value = url.JOBS_LIST_DELETE_PAGE)
    public ModelAndView delete() {
        ModelAndView modelAndView = new ModelAndView(url.JOBS_LIST_DELETE_PAGE);
        modelAndView.addObject("jobsList", new JobsList());
        return modelAndView;
    }
}
