package net.proselyte.springbootdemo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.proselyte.springbootdemo.custom.CustomUser;
import net.proselyte.springbootdemo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByName((userName));

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + userName);
        }

        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole()));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new CustomUser(user.getName(), user.getPassword(),
                true, true, true, true, grantedAuthorities);
    }
}