package com.bharath.springcloud.couponservice.controllers;

import com.bharath.springcloud.couponservice.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

public class UserController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/login")
    public String login(String email, String password) { //estos son los inputs q vienen en el html para llenar email y password. Como se llaman igual q en el html ya estan mapeados, sino deberiamos usar @RequestParams
        boolean loginResponse = securityService.login(email, password); //lo pasamos al servicio para q loguee
        if (loginResponse) {
            return "index.html"; //si nos logueamos exitosamente nos manda al index...
        }
        return "login";//...sino nos devuelve a login.html
    }
}
