package com.cecilia.order.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.authentication.AuthenticationProvider;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private final AuthenticationEntryPoint authEntryPoint;
        private final AuthenticationProvider authenticationProvider;

        @Autowired
        public SecurityConfig(AuthenticationEntryPoint authEntryPoint, OrderAppAuthenticationProvider authenticationProvider) {
            this.authEntryPoint = authEntryPoint;
            this.authenticationProvider = authenticationProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(STATELESS);
            http.csrf().disable().authorizeRequests()
                    .anyRequest().authenticated()
                    .and().httpBasic()
                    .authenticationEntryPoint(authEntryPoint);
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider((org.springframework.security.authentication.AuthenticationProvider) authenticationProvider);
        }

    }


