package com.bharath.springcloud.couponservice.security.config;



import java.util.List;

import com.bharath.springcloud.couponservice.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;



//@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { //esta clase la copié y pegué desde github, en vista y considerando que apareció por arte de magia en la clase llamada "Test".
   // Apartir de este punto el código me dejó de funcionar, por lo que solo sirve como apoyo para ver apuntes.

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    //En este método definió q para leer un cupon (con la peticion GET) cualquier rol puede hacerlo, pero para crear un cupon (peticion POST) tiene que tener rol "ADMIN".
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/couponapi/coupons/{code:^[A-Z]*$}", "/index", "/showGetCoupon",
                        "/getCoupon", "/couponDetails")
                .hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/showCreateCoupon", "/createCoupon", "/createResponse").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.POST, "/couponapi/coupons", "/saveCoupon", "/getCoupon").hasRole("ADMIN")
                .mvcMatchers("/", "/login", "/logout", "/showReg", "/registerUser").permitAll().anyRequest().denyAll()
                .and().logout().logoutSuccessUrl("/");

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}