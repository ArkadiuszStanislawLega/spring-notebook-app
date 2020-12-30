package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.UserService;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.Messages;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.url;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = url.LOGIN_PAGE)
    public ModelAndView login() {
        return new ModelAndView(url.LOGIN_PAGE);
    }

    @GetMapping(value = url.USER_PROFILE_PAGE)
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView(url.USER_PROFILE_PAGE);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("id", user.getId());
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("email", user.getEmail());
        modelAndView.addObject("roles", user.getRoles());
        return modelAndView;
    }


    @RequestMapping(value = url.REGISTRATION_PAGE, method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView(url.REGISTRATION_PAGE);
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping(value = url.REGISTRATION_PAGE)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(url.REGISTRATION_PAGE);
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user", Messages.ERROR_MESSAGE_REGISTRATION_FAIL);
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", Messages.REGISTRATION_SUCCESSFUL);
            modelAndView.addObject("user", new User());
        }
        return modelAndView;
    }
}