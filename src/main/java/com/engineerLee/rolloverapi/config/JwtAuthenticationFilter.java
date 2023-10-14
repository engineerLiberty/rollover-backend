package com.engineerLee.rolloverapi.config;

import com.mongodb.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        String userName;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
       userName = jwtService.extractUserName(jwt);
       if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails registeredUser = this.userDetailsService.loadUserByUsername(userName);
           if (jwtService.isTokenValid(jwt,registeredUser)) {
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registeredUser,
                       null,
                       registeredUser.getAuthorities()
               );
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)
               );
               SecurityContextHolder.getContext().setAuthentication(authToken);
           }
       }
       filterChain.doFilter(request, response);
    }
}
