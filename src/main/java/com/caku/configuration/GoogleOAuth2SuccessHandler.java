package com.caku.configuration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.caku.model.Role;
import com.caku.model.User;
import com.caku.repository.RoleRepository;
import com.caku.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String email = token.getPrincipal().getAttributes().get("email").toString();

        // Check if a user with this email already exists
        if (userRepository.findUserByEmail(email).isPresent()) {
            // Handle the case when the user already exists
            // You can update user information or perform other actions here
        } else {
            // User doesn't exist, create a new user
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setEmail(email);
            
            // Assuming that roleRepository.findById(2) retrieves the appropriate role
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).orElse(null)); // Handle role not found
            
            user.setRoles(roles);
            
            
            userRepository.save(user);
        }
        
        // Redirect the user to the root URL
        redirectStrategy.sendRedirect(request, response, "/");
    }
}
