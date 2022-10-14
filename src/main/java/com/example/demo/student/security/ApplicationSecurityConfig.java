package com.example.demo.student.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.demo.student.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

        private final PasswordEncoder passwordEncoder;
        @Autowired
        public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                //Basic auth
                http.authorizeRequests()
                        .antMatchers("/","index","/css/*","/js/*").permitAll()
                        .antMatchers("/api/**").hasRole(STUDENT.name())
                        .anyRequest()
                        .authenticated()
                        .and()
                        .httpBasic();
        }

        @Override
        @Bean // to be instantiated for us
        protected UserDetailsService userDetailsService() {
                UserDetails liliaUser = User.builder()
                                            .username("Lilia")
                                            .password(passwordEncoder.encode("pass"))
                                            .roles(STUDENT.name())
                                            .build();
                UserDetails badrUser = User.builder()
                        .username("Badr")
                        .password(passwordEncoder.encode("KKKK"))
                        .roles(ADMIN.name())
                        .build();

                return new InMemoryUserDetailsManager(liliaUser, badrUser);

        }
}
