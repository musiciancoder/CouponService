package com.bharath.springcloud.couponservice.security.config;

import com.bharath.springcloud.couponservice.model.User;
import com.bharath.springcloud.couponservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


//OJOOO!!! Esta clase no es del proyecto como tal, solo la puse para hacer referencia a lo que se hacia antes de la version 3.0, ahora estose hace con la clase WebSecurityConfig
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepo.findByEmail(username);
        if (user==null){throw new UsernameNotFoundException("User not found for email" + username);}
      // return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), user.getRoles());
   return null;
    }
}
