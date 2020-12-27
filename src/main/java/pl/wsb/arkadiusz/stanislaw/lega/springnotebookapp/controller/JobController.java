package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.Job;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.JobService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.JobsListService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.UserService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.stat.url;

import java.util.Date;

@Controller
public class JobController {
    @Autowired
    private JobService jobService;

    @Autowired
    private JobsListService parentService;

    @Autowired
    private UserService ownerService;

    @GetMapping(value = url.JOB_HOME_PAGE)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView(url.JOB_HOME_PAGE);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ownerService.findUserByUserName(auth.getName());

        modelAndView.addObject("information", "Użytkownik " + user.getUserName() + " posiada " + user.getJobs().size() + " zadań.");
        modelAndView.addObject("jobsList", user.getJobs());

        return modelAndView;
    }


    @RequestMapping(value = url.JOB_NEW_PAGE+"/{id}")
    public String create(@ModelAttribute("id") Integer parentId, Model model) {
        JobsList parent = parentService.find(parentId);
        Job job = new Job();
        model.addAttribute("job", job);
        model.addAttribute("parent", parent);
        return url.JOB_NEW_PAGE;
    }

    @RequestMapping(value =  url.JOB_SAVE_PAGE, method = {RequestMethod.POST, RequestMethod.PUT})
    public String save(@ModelAttribute("job") Job job, @ModelAttribute("parent") JobsList parent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User owner = ownerService.findUserByUserName(auth.getName());

        job.setCreated(new Date());
        job.setEdited(new Date());
        job.setParent(parent);
        job.setOwner(owner);

        jobService.saveJob(job);

        return "redirect:" + url.JOB_HOME_PAGE;
    }
}
