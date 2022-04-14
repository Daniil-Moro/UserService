package net.proselyte.springbootdemo.component;

import net.proselyte.springbootdemo.custom.CustomUser;
import net.proselyte.springbootdemo.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthComponent {
    public boolean hasPermission(String username) {
        CustomUser customUser =
                (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//


        //Collection<? extends GrantedAuthority> authorities = customUser.getAuthorities();
        //boolean authorized = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return ( customUser.getUsername() == username) ||
                customUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ;
    }
}