package com.bluthinkInc.spring_security_project.config;

import com.bluthinkInc.spring_security_project.repo.TokenBlacklistedRepo;
import com.bluthinkInc.spring_security_project.service.JWTService;
import com.bluthinkInc.spring_security_project.service.MyUserDetailsService;
import com.bluthinkInc.spring_security_project.service.TokenBlacklistedService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    ApplicationContext context;
    private final TokenBlacklistedService tokenBlacklistedService;
    public JwtFilter(JWTService jwtService,ApplicationContext context
                     ,TokenBlacklistedService tokenBlacklistedService) {
        this.jwtService = jwtService;
        this.context = context;
        this.tokenBlacklistedService = tokenBlacklistedService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb25hayIsImlhdCI6MTc3NjMyMjA5MywiZXhwIjoxNzc2MzIzODkzfQ.W0uwXpvgQquIv7KG0aP6NnC0eOFReRIBpdbdeYeYbQo
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        String path = request.getServletPath();
        if (path.equals("/refresh_token")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            username = jwtService.extractUserName(token);
            token = authHeader.substring(7);

            //check blacklisted
            if (tokenBlacklistedService.isBlacklisted(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("token revoked! please login again with credentials");
                return;
            }
            username = jwtService.extractUserName(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //check token type
            String type = jwtService.extractTokenType(token);

            if (!"access_token".equals(type)){
                throw new RuntimeException("Invalid token! please enter correct access token");
            }

            UserDetails userDetails = context.getBean(MyUserDetailsService.class)
                    .loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                String role = jwtService.extractRole(token);
                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(role));
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request, response);
    }
}
