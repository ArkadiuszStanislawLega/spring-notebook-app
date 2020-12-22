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

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/jobsList")
public class JobsListController {

    @Autowired
    private JobsListService jobsListService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/new")
    public String create(Model model){
        JobsList jobsList = new JobsList();
        model.addAttribute("jobsList", jobsList);
//        ModelAndView modelAndView = new ModelAndView();
//        JobsList jobsList = new JobsList();
//        modelAndView.addObject("jobsList", jobsList);
//        modelAndView.setViewName("jobsList/create");
        return  "jobsList/new";
    }

    @PostMapping(value = "/save")
    public String saveJobsList(@ModelAttribute("jobsList") JobsList jobsList){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findUserByUserName(auth.getName());
        user.addJobsList(jobsList);
        jobsList.addOwner(user);

        if (jobsList.getCreated() == null){
            jobsList.setCreated(new Date());
        }
        jobsList.setEdited(new Date());
        jobsListService.saveJobsList(jobsList);
        return "redirect:/jobsList/home";
    }

    @GetMapping(value = "/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        modelAndView.addObject("information", "Użytkownik " + user.getUserName() + " posiada " + user.getJobsList().size() + " list.");
        modelAndView.addObject("jobsLists",  user.getJobsList());
        modelAndView.setViewName("jobsList/home");
        return modelAndView;
    }

    @GetMapping(value="/edit/{id}")
    public ModelAndView edit(@PathVariable(name="id") int id){
        ModelAndView modelAndView = new ModelAndView("jobsList/edit");
        modelAndView.addObject("jobsList", jobsListService.find(id));
        return modelAndView;
    }

    @GetMapping(value="/delete")
    public ModelAndView delete(){
        ModelAndView modelAndView = new ModelAndView();
        JobsList jobsList = new JobsList();
        modelAndView.addObject("jobsList", jobsList);
        modelAndView.setViewName("jobsList/delete");
        return  modelAndView;
    }
}
