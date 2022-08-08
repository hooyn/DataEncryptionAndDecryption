package hooyn.rsa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserControllerVer {


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String loginForm(){
        return "login";
    }
}
