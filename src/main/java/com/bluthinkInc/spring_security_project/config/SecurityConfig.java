package com.bluthinkInc.spring_security_project.config;

import com.bluthinkInc.spring_security_project.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final MyUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;
    public SecurityConfig(MyUserDetailsService userDetailsService,JwtFilter jwtFilter){
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        //disable csrf-token
////        http.csrf(customizer->customizer.disable());
//////                //enable login form
////        http.authorizeHttpRequests(request->request.anyRequest().authenticated());
//////        http.formLogin(Customizer.withDefaults());
////        http.httpBasic(Customizer.withDefaults());
////        //making http stateless
////        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
////        return http.build();
//
        return http
                .cors(Customizer.withDefaults())
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/register", "/login", "/refresh_token")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /// /    @Bean
    /// /    public UserDetailsService userDetailsService(){
    /// /        UserDetails user1 = User
    /// /                .withDefaultPasswordEncoder()
    /// /                .username("root")
    /// /                .password("root")
    /// /                .roles("user")
    /// /                .build();
    /// /
    /// /        return new InMemoryUserDetailsManager(user1);
    /// /    }
//
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
