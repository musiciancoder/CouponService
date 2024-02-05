package com.bharath.springcloud.couponservice.security;

import com.bharath.springcloud.couponservice.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

//esto es para implementar un custom login
@Service //al parecer en el servicio de springsecurity no va inyectado un repositorio.
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(token); // se lo pasamos al authentication manager
        boolean result = token.isAuthenticated(); //este metodo es una bandera q tiene el authentication manager y que podemos usar para saber si el logueo ha sido o no exitoso
    if (result) // if true...
        SecurityContextHolder.getContext().setAuthentication(token); //se lo pasamos y se lo seteamos al Security Context (ver diagrama de arquitectura en los apuntes q tome en notepad++)
      return result;
    }

}
