package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.Job;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.JobService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.JobsListService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.UserService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.stat.url;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class JobController {
    private JobsList parent;

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

        List<Job> jobs = new ArrayList<>();

        for(JobsList parent : user.getJobsLists()){
            for(Job job: parent.getJobsList()){
                jobs.add(job);
            }
        }

        modelAndView.addObject("information", "Użytkownik " + user.getUserName() + " posiada " + jobs.size() + " zadań.");
        modelAndView.addObject("jobsList", jobs);

        return modelAndView;
    }


    @RequestMapping(value = url.JOB_NEW_PAGE+"/{id}")
    public String create(@PathVariable(name = "id") Integer parentId, Model model) {
        this.parent = parentService.find(parentId);

        Job job = new Job();
        job.setParent(parent);

        model.addAttribute("job", job);
        return url.JOB_NEW_PAGE;
    }

    @RequestMapping(value =  url.JOB_SAVE_PAGE, method = {RequestMethod.POST, RequestMethod.PUT})
    public String save(@ModelAttribute("job") Job job) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User owner = ownerService.findUserByUserName(auth.getName());

        job.setParent(this.parent);
        job.setCreated(new Date());
        job.setEdited(new Date());

        jobService.saveJob(job);

        this.parent = null;
        return "redirect:" + url.JOB_HOME_PAGE;
    }
}
