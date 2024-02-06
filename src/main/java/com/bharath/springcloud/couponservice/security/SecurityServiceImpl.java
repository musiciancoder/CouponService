package com.bharath.springcloud.couponservice.security;

import com.bharath.springcloud.couponservice.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service //al parecer en el servicio de springsecurity no va inyectado un repositorio.
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SecurityContextRepository securityContextRepo;

    //esto es para implementar un custom login
    @Override
    public boolean login(String userName, String password //antes de 3.2
    , HttpServletRequest request, HttpServletResponse response) { //despues de 3.2. se requieren estos parametros adicionales
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(token); // se lo pasamos al authentication manager
        boolean result = token.isAuthenticated(); //este metodo es una bandera q tiene el authentication manager y que podemos usar para saber si el logueo ha sido o no exitoso
    if (result) {// if true...
        //Version anterior a 3.2
        //  SecurityContextHolder.getContext().setAuthentication(token);
        //version posterior a 3.2
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token); //se lo pasamos y se lo seteamos al Security Context (ver diagrama de arquitectura en los apuntes q tome en notepad++)
        securityContextRepo.saveContext(context, request, response); //guardamos en el security context el request y la respuesta para que haya un registro de seguridad a futuro
    }
      return result;
    }

}
