package com.caku.configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Check the user's roles
        boolean isAdmin = hasRole(userDetails, "ROLE_ADMIN");

        if (isAdmin) {
            // Redirect to the admin panel
            response.sendRedirect("/admin");
        } else {
            // Redirect to the user panel
            response.sendRedirect("/");
        }
    }

    private boolean hasRole(UserDetails userDetails, String targetRole) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(targetRole)) {
                return true;
            }
        }
        return false;
    }
}

