package org.example.app.security;

import lombok.AllArgsConstructor;
import org.example.app.services.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    public final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetailsDto user = userRepository.findByUserName(userName);

        if (user == null){
            throw new UsernameNotFoundException(userName);
        }

        return user;
    }
}
