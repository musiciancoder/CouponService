package com.bharath.springcloud.couponservice.security.config;



import java.util.List;

import com.bharath.springcloud.couponservice.security.UserDetailsServiceImpl;
import jdk.internal.joptsimple.util.RegexMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.servlet.http.HttpServletRequest;


@Configuration
//@EnableMethodSecurity //dijo q esto se usaba en vez de @EnableGlobalMethodSecurity en las versiones mas nuevas
@EnableGlobalMethodSecurity(prePostEnabled=true) //Lo agregó en sección "level security method".
//@EnableGlobalMethodSecurity(prePostEnabled=true, jsr250Enabled = true, securedEnabled = true) Esto es para usar en el controlador @ROLESALLOWED y @SECURITY respectivamente, q se usaba en versiones anteriores
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
        //Lo comentó en sección "level security method"
    /*    http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/couponapi/coupons/{code:^[A-Z]*$}", "/index", "/showGetCoupon",
                        "/getCoupon", "/couponDetails")
                .hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/showCreateCoupon", "/createCoupon", "/createResponse").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.POST, "/couponapi/coupons", "/saveCoupon", "/getCoupon").hasRole("ADMIN")
                .mvcMatchers("/", "/login", "/logout", "/showReg", "/registerUser").permitAll().anyRequest().denyAll()
                .and().logout().logoutSuccessUrl("/");

     */

        //con esto customizamos qué urls no queremos que tengam CSRF. Lo dejo comentado en la seccion de CORS
      /*
        http.csrf(csrfCustomizar->{
            csrfCustomizar.ignoringAntMatchers("/couponapi/coupons/**");
            RequestMatcher requestMatchers=new RegexRequestMatcher("/couponapi/coupons/{code:^[A-Z]*$}","POST");
           requestMatchers = new MvcRequestMatcher(new HandlerMappingIntrospector(),"/getCoupon");
            csrfCustomizar.ignoringAntMatchers(String.valueOf(requestMatchers));


        });

       */

        //CORS Personalizados.
        http.cors(corsCustomizer->{
            CorsConfigurationSource configurationSource=request->{
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("localhost:3000"));
                corsConfiguration.setAllowedMethods(List.of("GET"));
                return corsConfiguration;
            };
            corsCustomizer.configurationSource(configurationSource);
        });
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