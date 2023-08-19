package com.caku.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.caku.service.CustomUserDetailService;

@Configuration
public class SecurityConfig {

       @Bean
       public UserDetailsService userDetailsService(){
        return new CustomUserDetailService();
       }

       @Bean
        public static PasswordEncoder passwordEncoder(){
                return new BCryptPasswordEncoder();
        }

       @Autowired
       public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
                auth.userDetailsService(userDetailsService())
                        .passwordEncoder(passwordEncoder());
       }
       

        //configure
        @Bean
        public SecurityFilterChain defauSecurityFilterChain(HttpSecurity http) throws Exception{
                http.csrf().disable()
                        .authorizeHttpRequests((authorize)->
                        authorize.requestMatchers("/register/**", "/shop", "h2-console").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/admin").hasRole("ADMIN")
                        ).formLogin(
                                form -> form
                                        .loginPage("/login")
                                        .loginProcessingUrl("/login")
                                        .defaultSuccessUrl("/")
                                        .permitAll()
                        ).logout(
                                logout -> logout
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                        .permitAll()
                        );


                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
                return configuration.getAuthenticationManager();
        }
}       
