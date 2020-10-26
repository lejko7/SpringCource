package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);
    private List<User> userList = new ArrayList<>();

    public LoginService() {
        userList.add(new User("root", "123"));
    }

    public boolean authenticate(User user) {
        logger.info("try auth with user-form: " + user);
        return userList.contains(user);
    }

    public boolean registration(User user) {
        return userList.stream().map(User::getUserName).anyMatch(name -> name.equals(user.getUserName()));
    }
}
