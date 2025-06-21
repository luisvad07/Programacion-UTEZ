package utez.edu.mx.foodster.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CurrentUserDetails {

    public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }
    public Set<String> getCurrentUserAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
