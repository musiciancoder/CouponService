package com.bharath.springcloud.couponservice.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig2 {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

  /*  @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeHttpRequests() //nofunciona!!
                .requestMatchers(HttpMethod.GET, "/couponapi/coupons/{code:^[A-Z]*$}","/")
                .hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET,"/showCreateCoupon", "/createCoupon", "/createResponse")
                .requestMatchers(HttpMethod.POST, "/couponapi/coupons")
                .hasRole("ADMIN").and()
                .csrf().disable();
        return http.build();
    } */


}
