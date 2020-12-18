package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.JobsListService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.UserService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;

@Controller
@RequestMapping("/jobsList")
public class JobsListController {

    @Autowired
    private JobsListService jobsListService;

    @Autowired
    private UserService userService;

    @GetMapping(value="/new")
    public ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView();
        JobsList jobsList = new JobsList();
        modelAndView.addObject("jobsList", jobsList);
        modelAndView.setViewName("createJobsList");
        return  modelAndView;
    }

    @PostMapping(value = "/save")
    public ModelAndView save(@Valid JobsList jobsList, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        jobsList.setOwner(new HashSet<User>(Arrays.asList(user)));

        if (!bindingResult.hasErrors()) {
            jobsListService.saveJobsList(jobsList);
        }

        modelAndView.setViewName("saveCreateJobsList");

        return modelAndView;
    }

    @GetMapping(value = "/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        modelAndView.addObject("userName", "Użytkownik " + user.getUserName() + " posiada ileś tam list. ");
        modelAndView.setViewName("jobsList/home");
        return modelAndView;
    }
}
