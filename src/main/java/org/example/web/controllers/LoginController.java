package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.UserRepository;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private final Logger logger = Logger.getLogger(LoginController.class);
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String login(Model model,
                        @RequestParam(required = false) String error) {
        logger.info("GET /login returns login_page.html");
        model.addAttribute("user", new User());
        model.addAttribute("error", error);
        return "login_page";
    }

    @PostMapping("/auth")
    public String authenticate(User user) {
        logger.info("try to auth");
        if (userRepository.authenticate(user)) {
            logger.info("login OK redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            logger.info("login FAIL redirect back to login");
            return "redirect:/404";
        }
    }
}
