package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.security.UserDetailsDto;
import org.example.web.dto.User;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRepository {

    private final Logger logger = Logger.getLogger(UserRepository.class);
    private final List<UserDetailsDto> userList = new ArrayList<>();

    public UserRepository() {
        userList.add(new UserDetailsDto("root", new MessageDigestPasswordEncoder("MD5").encode("123")));
    }

    public UserDetailsDto findByUserName(String userName){
        for (UserDetailsDto user : userList){
            if (user.getUsername().equals(userName))
                return user;
        }

        return null;
    }

    public boolean authenticate(User user) {
        logger.info("try auth with user-form: " + user);
        return userList.contains(new UserDetailsDto(user));
    }

    public boolean registration(User user) {
        if (userList.stream().map(UserDetailsDto::getUsername).noneMatch(name -> name.equals(user.getUsername()))) {
            userList.add(new UserDetailsDto(user.getUsername(), new MessageDigestPasswordEncoder("MD5").encode(user.getPassword())));
            return true;
        }

        return false;
    }
}
