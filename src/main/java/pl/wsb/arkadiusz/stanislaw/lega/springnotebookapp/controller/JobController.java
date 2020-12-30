package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.exceptions.NotFoundException;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.exceptions.UnauthorizedException;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.Job;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.JobService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.JobsListService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.UserService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.Messages;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.Urls;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class JobController {
    private Map<Integer, JobsList> parents = new HashMap<>();

    @Autowired
    private JobService jobService;

    @Autowired
    private JobsListService parentService;

    @Autowired
    private UserService ownerService;

    @GetMapping(value = Urls.JOB_HOME_PAGE)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView(Urls.JOB_HOME_PAGE);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ownerService.findUserByUserName(auth.getName());

        modelAndView.addObject("information", "Użytkownik " + user.getUserName() + " posiada " + user.getSortedByDateJobs().size() + " zadań.");
        modelAndView.addObject("jobsList", user.getSortedByDateJobs());

        return modelAndView;
    }


    @RequestMapping(value = Urls.JOB_NEW_PAGE + "/{id}")
    public String create(@PathVariable(name = "id") Integer parentId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User owner = ownerService.findUserByUserName(auth.getName());
        this.parents.put(owner.getId(), parentService.find(parentId));

        Job job = new Job();
        model.addAttribute("job", job);
        return Urls.JOB_NEW_PAGE;
    }

    /**
     * Save created job in database.
     * Update edited date parent jobs list.
     *
     * @param job Created new job.
     * @return Redirect to parent job list page.
     */
    @PostMapping(value = Urls.JOB_SAVE_PAGE)
    public String save(@ModelAttribute("job") Job job) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = ownerService.findUserByUserName(auth.getName());

        job.setParent(this.parents.get(userLoggedIn.getId()));
        job.setCreated(new Date());
        job.setEdited(new Date());
        jobService.saveJob(job);

        //Update parent edited date.
        JobsList dbJobList = parentService.find(job.getParent().getId());
        dbJobList.setEdited(new Date());
        parentService.saveJobsList(dbJobList);

        this.parents.remove(userLoggedIn.getId());

        return "redirect:" + Urls.JOBS_LIST_DETAILS_PAGE + "/" + job.getParent().getId();
    }

    @RequestMapping(value = Urls.JOB_EDIT_PAGE + "/{id}")
    public ModelAndView edit(@PathVariable(name = "id") int id) {
        ModelAndView modelAndView = new ModelAndView(Urls.JOB_EDIT_PAGE);
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User owner = ownerService.findUserByUserName(auth.getName());

            Job job = jobService.find(id);

            if (job == null)
                throw new NotFoundException(Urls.JOB_EDIT_PAGE + "/" + id + "-> " + Messages.ERROR_MESSAGE_NOT_FOUND);

            if (owner.getId() == job.getParent().getOwner().getId()) {
                this.parents.put(owner.getId(), job.getParent());
                modelAndView.addObject("job", job);
                return modelAndView;
            } else {
                throw new UnauthorizedException(Urls.JOB_EDIT_PAGE + "/" + id + "-> " + Messages.ERROR_MESSAGE_UNAUTHORIZED);
            }

        } catch (UnauthorizedException ex) {
            modelAndView.setViewName(Urls.ERROR_UNAUTHORIZED);
            return modelAndView;
        } catch (NotFoundException ex) {
            modelAndView.setViewName(Urls.ERROR_NOT_FOUND);
            return modelAndView;
        }
    }

    @GetMapping(value = Urls.JOB_SAVE_UPDATE_PAGE)
    public String saveUpdate(@ModelAttribute("job") Job job) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn = ownerService.findUserByUserName(auth.getName());

        Date editedDate = new Date();
        job.setParent(this.parents.get(userLoggedIn.getId()));
        job.getParent().setEdited(editedDate);
        job.setEdited(editedDate);
        jobService.saveJob(job);

        this.parents.remove(userLoggedIn.getId());

        return "redirect:" + Urls.JOBS_LIST_DETAILS_PAGE + "/" + job.getParent().getId();
    }

    @GetMapping(value = Urls.JOB_DELETE_PAGE + "/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = ownerService.findUserByUserName(auth.getName());
            Job job = jobService.find(id);

            if (job == null)
                throw new NotFoundException(Urls.JOB_DELETE_PAGE + "/" + id + "-> " + Messages.ERROR_MESSAGE_NOT_FOUND);

            if (user.getId() == job.getParent().getOwner().getId()) {
                job.getParent().setEdited(new Date());
                jobService.removeJob(job);
            } else {
                throw new UnauthorizedException(Urls.JOB_DELETE_PAGE + "/" + id + "-> " + Messages.ERROR_MESSAGE_UNAUTHORIZED);
            }

            return "redirect:" + Urls.JOBS_LIST_DETAILS_PAGE + "/" + job.getParent().getId();

        } catch (UnauthorizedException ex) {
            return Urls.ERROR_UNAUTHORIZED;
        } catch (
                NotFoundException ex) {
            return Urls.ERROR_NOT_FOUND;
        }
    }
}
