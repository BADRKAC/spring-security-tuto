package com.example.demo.student.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.example.demo.student.security.ApplicationUserPermission.*;
import static com.example.demo.student.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

        private final PasswordEncoder passwordEncoder;
        @Autowired
        public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                //Basic auth
                http.csrf().disable()
                 //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                        .authorizeRequests()
                        .antMatchers("/","index","/css/*","/js/*").permitAll()
                        .antMatchers("/api/**").hasRole(STUDENT.name())
//                        .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                        .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                        .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                        .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
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
                                            .password(passwordEncoder.encode("password123"))
                                            //.roles(STUDENT.name())
                                            .authorities(STUDENT.getGrantedAuthorities())
                                            .build();

                UserDetails badrUser = User.builder()
                        .username("badr")
                        .password(passwordEncoder.encode("password123"))
                        //.roles(ADMIN.name())
                        .authorities(ADMIN.getGrantedAuthorities())
                        .build();

                UserDetails tomUser = User.builder()
                        .username("tom")
                        .password(passwordEncoder.encode("password123"))
                        //.roles(ADMINTRAINEE.name())
                        .authorities(ADMINTRAINEE.getGrantedAuthorities())
                        .build();

                return new InMemoryUserDetailsManager(liliaUser, badrUser,tomUser);

        }
}
