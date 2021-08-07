package com.example.demo.security.service;

import com.example.demo.security.model.MyUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.*;

public class MyAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) {

        var myUserDetails = new MyUserDetails();

        var key = token.getPrincipal().toString();

        /**
         * Check API Key
         */
        if (!(key.equals("hello") || key.equals("world"))) {
            throw new UsernameNotFoundException("Invalid key.");
        }

        myUserDetails.setKey(key);

        /**
         * Add Authority
         */
        if (key.equals("world")) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("WORLD"));
            myUserDetails.setAuthorities(authorities);
        }

        return myUserDetails;
    }
}
