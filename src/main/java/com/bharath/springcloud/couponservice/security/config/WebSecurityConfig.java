package com.bharath.springcloud.couponservice.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
Mark it with at configuration annotation.The first bean is the Bcrypt password encoder in earlier versions of spring boot before 3.0, we had
to inject this user detail service (usualmente era llamado UserDetailsSerciceImpl) and configure it manually, but with version 3.0 and above, we no longer have to do it.
Spring security will automatically try to find a bean or a class that implements the user details service and it will use it.
We only need to configure the Bcrypt password Encoder Bcrypt Password Encoder Call this method Password Encoder.
 */


@Configuration
public class WebSecurityConfig { //class containing config beans

    /*
    So this encoder will be used when the requests come in to encode the password and compare with what we have in the database.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http){

            http.httpBasic();
            http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET, "/couponapi/coupons/**") //a esta url...
                    .hasAnyRole("USER","ADMIN") //...pueden acceder usuarios que tengan cualquiera de estos roles roles (user o admin)
                    .requestMatchers(HttpMethod.POST,"/couponapi/coupons")
                    .hasAnyRole("ADMIN")//solo usuarios con rol admin podrán guardar datos
                    .and().csrf().disable(); //que no pesque los cfrs o cors porque vienen activados para POST por defecto y si lo dejamos por defecto va a fallar*/
            return null;

    }

}

