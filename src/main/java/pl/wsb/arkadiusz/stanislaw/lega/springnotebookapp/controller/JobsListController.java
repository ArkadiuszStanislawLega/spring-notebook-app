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
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.JobsListService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.UserService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.Messages;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.Urls;

import java.util.Date;

@Controller
public class JobsListController {

    @Autowired
    private JobsListService jobsListService;

    @Autowired
    private UserService userService;

    @GetMapping(value = Urls.JOBS_LIST_HOME_PAGE)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView(Urls.JOBS_LIST_HOME_PAGE);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        modelAndView.addObject("information", "Użytkownik " + user.getUserName() + " posiada " + user.getJobsLists().size() + " list.");
        modelAndView.addObject("jobsLists", user.getSortedByDateJobsList());

        return modelAndView;
    }


    @RequestMapping(value = Urls.JOBS_LIST_NEW_PAGE)
    public String create(Model model) {
        JobsList jobsList = new JobsList();
        model.addAttribute("jobsList", jobsList);
        return Urls.JOBS_LIST_NEW_PAGE;
    }

    @RequestMapping(value = Urls.JOBS_LIST_SAVE_PAGE, method = {RequestMethod.POST, RequestMethod.PUT})
    public String save(@ModelAttribute("jobsList") JobsList jobsList) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        jobsList.setOwner(user);
        jobsList.setCreated(new Date());
        jobsList.setEdited(new Date());

        user.addJobsList(jobsList);

        jobsListService.saveJobsList(jobsList);
        return "redirect:" + Urls.JOBS_LIST_HOME_PAGE;
    }

    @PostMapping(value = Urls.JOBS_LIST_SAVE_UPDATE_PAGE)
    public String saveUpdate(@ModelAttribute("jobsList") JobsList jobsList) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUserName(auth.getName());
            User dbUser = jobsListService.find(jobsList.getId()).getOwner();

            if (jobsList == null)
                throw new NotFoundException(Urls.JOBS_LIST_SAVE_UPDATE_PAGE + " " + Messages.ERROR_MESSAGE_NOT_FOUND);

            if (user.getId() == dbUser.getId()) {
                for (JobsList userJobsList : user.getJobsLists()) {
                    if (userJobsList.getId() == jobsList.getId()) {
                        jobsList.setCreated(userJobsList.getCreated());
                        jobsList.setOwner(user);
                        jobsList.setEdited(new Date());
                        jobsListService.saveJobsList(jobsList);
                        break;
                    }
                }
                return "redirect:" + Urls.JOBS_LIST_HOME_PAGE;
            } else {
                throw new UnauthorizedException(Urls.JOBS_LIST_SAVE_UPDATE_PAGE + " " + Messages.ERROR_MESSAGE_UNAUTHORIZED);
            }
        } catch (UnauthorizedException ex) {
            return Urls.ERROR_UNAUTHORIZED;
        } catch (NotFoundException ex) {
            return Urls.ERROR_NOT_FOUND;
        }
    }

    @GetMapping(value = Urls.JOBS_LIST_EDIT_PAGE + "/{id}")
    public ModelAndView edit(@PathVariable(name = "id") int id) {
        ModelAndView modelAndView = new ModelAndView(Urls.JOBS_LIST_EDIT_PAGE);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUserName(auth.getName());
            JobsList jobsList = jobsListService.find(id);

            if (jobsList == null)
                throw new NotFoundException(Urls.JOBS_LIST_EDIT_PAGE + "/" + id + "-> " + Messages.ERROR_MESSAGE_NOT_FOUND);

            if (jobsList.getOwner().getId() == user.getId())
                modelAndView.addObject("jobsList", jobsListService.find(id));
            else {
                throw new UnauthorizedException(Urls.JOBS_LIST_EDIT_PAGE + "/" + id + "-> " + Messages.ERROR_MESSAGE_UNAUTHORIZED);
            }
            return modelAndView;
        } catch (UnauthorizedException ex) {
            modelAndView.setViewName(Urls.ERROR_UNAUTHORIZED);
            return modelAndView;
        } catch (NotFoundException ex) {
            modelAndView.setViewName(Urls.ERROR_NOT_FOUND);
            return modelAndView;
        }
    }

    @RequestMapping(value = Urls.JOBS_LIST_DELETE_PAGE + "/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUserName(auth.getName());
            JobsList jobsList = jobsListService.find(id);

            if (jobsList == null)
                throw new NotFoundException(Urls.JOBS_LIST_DELETE_PAGE + "/" + id + "-> " + Messages.ERROR_MESSAGE_NOT_FOUND);

            if (jobsList.getOwner().getId() == user.getId()) {
                jobsListService.removeJobsList(jobsList);
                return "redirect:" + Urls.JOBS_LIST_HOME_PAGE;
            } else {
                throw new UnauthorizedException(Urls.JOBS_LIST_DELETE_PAGE + "/" + id + "-> " + Messages.ERROR_MESSAGE_UNAUTHORIZED);
            }
        } catch (UnauthorizedException ex) {
            return Urls.ERROR_UNAUTHORIZED;
        } catch (NotFoundException ex) {
            return Urls.ERROR_NOT_FOUND;
        }
    }

    @GetMapping(value = Urls.JOBS_LIST_DETAILS_PAGE + "/{id}")
    public ModelAndView details(@PathVariable(name = "id") int id) {
        ModelAndView modelAndView = new ModelAndView(Urls.JOBS_LIST_DETAILS_PAGE);
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUserName(auth.getName());
            JobsList jobsList = jobsListService.find(id);

            if (jobsList == null)
                throw new NotFoundException(Urls.JOBS_LIST_DETAILS_PAGE + "/" + id + "-> " + Messages.ERROR_MESSAGE_NOT_FOUND);

            if (jobsList.getOwner().getId() == user.getId())
                modelAndView.addObject("jobsList", jobsListService.find(id));
            else {
                throw new UnauthorizedException(Urls.JOBS_LIST_DETAILS_PAGE + "/" + id + "-> " + Messages.ERROR_MESSAGE_UNAUTHORIZED);
            }

            return modelAndView;
        } catch (UnauthorizedException ex) {
            modelAndView.setViewName(Urls.ERROR_UNAUTHORIZED);
            return modelAndView;
        } catch (NotFoundException ex) {
            modelAndView.setViewName(Urls.ERROR_NOT_FOUND);
            return modelAndView;
        }
    }


}
