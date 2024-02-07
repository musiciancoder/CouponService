package com.bharath.springcloud.couponservice.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.RequestAttribute;

@Configuration
public class WebSecurityConfig2 {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    SecurityContextRepository securityContextRepository(){
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    AuthenticationManager authManager(){
      DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(bCryptPasswordEncoder());
    return new ProviderManager(provider);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     //   http.formLogin(); //no lo necesitamos con custom login
      //  http.httpBasic(Customizer.withDefaults());
        http.authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/couponapi/coupons/{code:^[A-Z]*$}","/", "/showGetCoupon",
                        "/getCoupon", "/couponDetails")
                .hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET,"/showCreateCoupon", "/createCoupon", "/createResponse")
                .requestMatchers(HttpMethod.POST, "/couponapi/coupons")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/getCoupon")
                .hasAnyRole("USER","ADMIN")
                .requestMatchers("/","login","/showReg","/registerUser").permitAll() //esto es para custom login
                .and().logout().logoutSuccessfulUrl("/") ;//para logout con custom login
             //   .and().csrf().disable(); //cuando no tiene esta linea CSRF is enabled

        http.securityContext(context->context.requireExplicitSave(true)); //starting from spring 3.2 this is required for custom login
        return http.build();
    }


}
