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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.caku.service.CustomUserDetailService;

@Configuration
public class SecurityConfig {

        // @Autowired
        // GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
        @Autowired
        CustomUserDetailService customUserDetailService;

       @Bean
       public UserDetailsService userDetailsService(){
        return new CustomUserDetailService();
       }

       @Bean
       public BCryptPasswordEncoder bCryptPasswordEncoder(){
                return new BCryptPasswordEncoder();
       }

       @Autowired
       public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
                auth.userDetailsService(customUserDetailService);
                        
       }
       
        //RequestMatcher adminRequestMatcher = new AntPathRequestMatcher("/admin", "/h2-console/**", "/shop/**", "/");
      // Define an array of patterns
        AntPathRequestMatcher[] permitAllPatterns = {
                new AntPathRequestMatcher("/register/**"),
                new AntPathRequestMatcher("/shop/**"),
                new AntPathRequestMatcher("/"),
                new AntPathRequestMatcher("/h2-console/**"),
                // Add more patterns as needed
                new AntPathRequestMatcher("/css/**"),  // Static resources
                new AntPathRequestMatcher("/js/**"),   // Static resources
                new AntPathRequestMatcher("/productImages/**"),// Static resources
                new AntPathRequestMatcher("/cakeImages/**"),//static resources
                new AntPathRequestMatcher("/images/**")//static resources
        };

        //configure
        @Bean
        public SecurityFilterChain defauSecurityFilterChain(HttpSecurity http) throws Exception{
                http.csrf().disable()
                .authorizeHttpRequests((authorize) ->{
                        authorize
                                .requestMatchers(permitAllPatterns).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                                .anyRequest().authenticated(); // This ensures that other requests are authenticated.
                    }).formLogin(
                                form -> form
                                        .loginPage("/login")
                                        .loginProcessingUrl("/login")
                                        .permitAll()
                                        .failureUrl("/login?=true")
                                        .defaultSuccessUrl("/")
                                        .usernameParameter("email")
                                        .passwordParameter("password")
                                         
                        // ).oauth2Login(
                        //         oauth2->oauth2
                        //                 .loginPage("/login")
                        //                 .successHandler(googleOAuth2SuccessHandler)
                        ).logout(
                                logout -> logout
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                        .logoutSuccessUrl("/login")
                                        .invalidateHttpSession(true)
                                        .deleteCookies("JSESSIONID")
                        );
                        http.headers().frameOptions().disable();

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
                return configuration.getAuthenticationManager();
        }
}       
