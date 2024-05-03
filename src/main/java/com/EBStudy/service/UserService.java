package com.EBStudy.service;

import com.EBStudy.config.UserContext;
import com.EBStudy.entity.User;
import com.EBStudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    //회원가입
    public User saveUser(User user) {
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        User findUser = userRepository.findByEmail(user.getEmail());
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException(email);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if("ADMIN".equals(user.getRole().toString())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if("TEACHER".equals(user.getRole().toString())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }

        return new UserContext(user, authorities);
    }
}
