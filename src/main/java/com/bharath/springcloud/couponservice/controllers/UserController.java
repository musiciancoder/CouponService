package com.bharath.springcloud.couponservice.controllers;

import com.bharath.springcloud.couponservice.model.User;
import com.bharath.springcloud.couponservice.repos.UserRepo;
import com.bharath.springcloud.couponservice.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    //para registrar usuario
    @GetMapping("/showReg")
    public String showRegistrationPage(){
        return "registerUser"; //pagina html
    }

    //tambien para registrar usuario
    @PostMapping("/registerUser")
    public String register(User user){
        user.setPassword(encoder.encode(user.getPassword())); //encripta el password antes de guardarlo
        userRepo.save(user);
        return "login"; //html
    }

    @PostMapping("/login")
    public String login(String email, String password //estos son los inputs q vienen en el html para llenar email y password. Como se llaman igual q en el html ya estan mapeados, sino deberiamos usar @RequestParams
    , HttpServletRequest request, HttpServletResponse response) {
        boolean loginResponse = securityService.login(email, password, request, response); //lo pasamos al servicio para q loguee
        if (loginResponse) {
            return "index.html"; //si nos logueamos exitosamente nos manda al index...
        }
        return "login";//...sino nos devuelve a login.html
    }
}
