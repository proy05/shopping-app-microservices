package com.roypr.microservices.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest()
                .authenticated()) //This is where you define your Access Rules. Translation : "I don't care who you are or what URL you are trying to visit; if you aren't logged in (authenticated), you aren't getting past the front door."
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) //This tells the application how to verify the user.
                //This means this app(API GW) holds "resources" (data) and is waiting for someone to show up with a valid "ID card."
                //Here JWT (JSON Web Token) is the specific type of ID card. Instead of a session cookie, API GW expects a digitally signed token.
                //Customizer.withDefaults(): This tells Spring to use standard settings to validate that token (e.g., checking if the token is expired or if the signature is valid).
                .build();
    }
}
