package com.net.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private UserDetailsService userDetailsService;
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService= userDetailsService;
    }

    @Bean
    static PasswordEncoder passwordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests((auth)->
                auth.requestMatchers(HttpMethod.GET,"/api/**").permitAll().anyRequest().authenticated()
                //auth.anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults());
        return http.build();
    }
//
//    @Bean
//    UserDetailsService userDetailsService()
//    {
//        UserDetails ali= User.builder().username("ali").password(passwordEncoder().encode("ali@123")).roles("ADMIN").build();
//        UserDetails leila= User.builder().username("leila").password(passwordEncoder().encode("leila@123")).roles("ADMIN").build();
//        return  new InMemoryUserDetailsManager(ali,leila);
//
//    }


}
