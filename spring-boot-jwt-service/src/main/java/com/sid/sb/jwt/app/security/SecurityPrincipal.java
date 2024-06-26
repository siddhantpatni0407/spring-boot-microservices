package com.sid.sb.jwt.app.security;

import com.sid.sb.jwt.app.entity.User;
import com.sid.sb.jwt.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SecurityPrincipal {

    private static SecurityPrincipal securityPrincipal = null;

    private Authentication principal = SecurityContextHolder.getContext().getAuthentication();

    private static UserService userService;

    @Autowired
    private SecurityPrincipal(UserService userService) {
        this.userService = userService;
    }

    public static SecurityPrincipal getInstance() {
        securityPrincipal = new SecurityPrincipal(userService);
        return securityPrincipal;
    }

    public User getLoggedInPrincipal() {
        if (principal != null) {
            UserDetails loggedInPrincipal = (UserDetails) principal.getPrincipal();
            return userService.findByUsername(loggedInPrincipal.getUsername());
        }
        return null;
    }

    public Collection<?> getLoggedInPrincipalAuthorities() {
        return ((UserDetails) principal.getPrincipal()).getAuthorities();
    }

}