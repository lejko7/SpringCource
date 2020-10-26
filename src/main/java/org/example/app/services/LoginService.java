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

    public boolean authenticate(User user) {
        logger.info("try auth with user-form: " + user);
        return userList.contains(user);
    }

    public void init() {
        userList.add(new User("root", "123"));
    }

    public boolean registration(User user) {
        Set<String> userNamesSet = userList.stream().map(User::getUserName).collect(Collectors.toSet());
        if (!userNamesSet.contains(user.getUserName())) {
            userList.add(user);
            return true;
        }

        return false;
    }
}
