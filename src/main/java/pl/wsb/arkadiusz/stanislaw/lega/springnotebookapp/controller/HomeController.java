package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.Urls;

@Controller
public class HomeController {

    @GetMapping(Urls.HOME_PAGE)
    public String homePage(){
        return Urls.HOME_HOME_PAGE;
    }
}
