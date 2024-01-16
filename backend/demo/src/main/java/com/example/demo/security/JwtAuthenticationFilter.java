package com.example.demo.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// see https://www.youtube.com/watch?v=KxqlJblhzfI

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService; 
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
            ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        

        /// DO NOT CORRECT TO !authHeader.St.... <- WRONG DO CORRECT IT TO !authHeader.St...
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt); 

        //if user is not authenticated 
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // get details from the database, matching the email
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // if the user is valid or not
            if(jwtService.isTokenValid(jwt, userDetails)){
                //create the users auth token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //add details to the token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // update auth token
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        // continue executing other filters
        filterChain.doFilter(request, response);



        //throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
    }
    
}
