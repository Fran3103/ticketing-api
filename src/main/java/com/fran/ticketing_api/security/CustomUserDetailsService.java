package com.fran.ticketing_api.security;

import com.fran.ticketing_api.entitie.User;
import com.fran.ticketing_api.repository.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private  IUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{


        User u = userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getPasswordHash(),
                u.isEnabled(),
                true,true,true,
                List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole().name()))
        );
    }


}
