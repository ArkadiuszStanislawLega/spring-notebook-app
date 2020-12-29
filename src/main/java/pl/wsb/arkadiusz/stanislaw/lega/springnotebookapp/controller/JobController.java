package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.util.*;

@Controller
public class JobController {
    private Map<Integer, JobsList> parents = new HashMap<>();

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User owner = ownerService.findUserByUserName(auth.getName());
        this.parents.put(owner.getId(), parentService.find(parentId));

        Job job = new Job();

        model.addAttribute("job", job);
        return url.JOB_NEW_PAGE;
    }

    @RequestMapping(value =  url.JOB_SAVE_PAGE, method = {RequestMethod.POST, RequestMethod.PUT})
    public String save(@ModelAttribute("job") Job job) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = ownerService.findUserByUserName(auth.getName());
        User jobParentOwner = this.parents.get(userLoggedIn.getId()).getOwner();

        if (userLoggedIn.getId() == jobParentOwner.getId()) {
            job.setParent(this.parents.get(userLoggedIn.getId()));
            job.setCreated(new Date());
            job.setEdited(new Date());

            jobService.saveJob(job);

            this.parents.remove(userLoggedIn.getId());
        }
        return "redirect:" + url.JOBS_LIST_DETAILS_PAGE + "/" + job.getParent().getId();
    }

    @GetMapping(value = url.JOB_EDIT_PAGE+"/{id}")
    public ModelAndView edit(@PathVariable(name = "id") int id) {
        ModelAndView modelAndView = new ModelAndView(url.JOB_EDIT_PAGE);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User owner = ownerService.findUserByUserName(auth.getName());

        Job job = jobService.find(id);
        this.parents.put(owner.getId(), job.getParent());

        modelAndView.addObject("job", job);
        return modelAndView;
    }

    @RequestMapping(value = url.JOB_SAVE_UPDATE_PAGE, method = {RequestMethod.GET, RequestMethod.PUT})
    public String saveUpdate(@ModelAttribute("job") Job job) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = ownerService.findUserByUserName(auth.getName());
        User jobParentOwner = this.parents.get(userLoggedIn.getId()).getOwner();

        if (userLoggedIn.getId() == jobParentOwner.getId()){
            job.setParent(this.parents.get(userLoggedIn.getId()));
            jobService.saveJob(job);

            this.parents.remove(userLoggedIn.getId());
        }

        return "redirect:" + url.JOB_HOME_PAGE;
    }

    @RequestMapping(value = url.JOB_DELETE_PAGE+"/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ownerService.findUserByUserName(auth.getName());
        Job job = jobService.find(id);

        if (user.getId() == job.getParent().getOwner().getId()) {
            jobService.removeJob(job);
        }

        return "redirect:" + url.JOB_HOME_PAGE;
    }
}
