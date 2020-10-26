package org.example.web.controllers;


import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.web.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {

    private final LoginService loginService;

    private final Logger logger = Logger.getLogger(RegisterController.class);

    @GetMapping
    public String getRegistrationForm(Model model){
        logger.info("Any get registration!");
        model.addAttribute("user", new User());
        return "register_new_user";
    }

    @PostMapping
    public String registerNewUser(User user) throws Exception {
        logger.info("Any try registration!");
        if (loginService.registration(user))
            return "login_page";
        else throw new Exception("User with this name already exsist");
    }
}
