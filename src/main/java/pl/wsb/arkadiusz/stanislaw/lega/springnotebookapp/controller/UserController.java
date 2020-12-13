package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.UserRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.UserService;

@Controller
@RequestMapping(path="/user") // This means URL's start with /demo (after Application path)
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listUsers", userService.listAll());
        return "user/index";
    }

    @RequestMapping("/sign-up")
    public String addNewUser (Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "user/sign-up";
    }
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.signUpUser(user);
        return "redirect:/user/";
    }
}