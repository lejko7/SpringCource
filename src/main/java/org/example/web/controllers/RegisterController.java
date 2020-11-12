package org.example.web.controllers;


import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.example.app.services.UserRepository;
import org.example.web.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {

    private final UserRepository userRepository;

    private final Logger logger = Logger.getLogger(RegisterController.class);

    @GetMapping
    public String getRegistrationForm(Model model, @RequestParam(required = false) Boolean error) {
        logger.info("Any get registration!");
        model.addAttribute("user", new User());
        model.addAttribute("error", error);
        return "register_new_user";
    }

    @PostMapping
    public String registerNewUser(Model model, User user) throws Exception {
        logger.info("Any try registration!");
        if (userRepository.registration(user)) {
            return "login_page";
        }
        else {
            return "redirect:/register?error=true";
        }
    }
}
