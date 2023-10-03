package com.alfish.arealmvp.security;

import com.alfish.arealmvp.service.ConfigProvider;
import com.alfish.arealmvp.service.UserAuthService;
import com.alfish.arealmvp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final UserAuthService userDetailsService;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final ConfigProvider config;

    @Autowired
    public WebSecurityConfiguration(UserAuthService userDetailsService, BCryptPasswordEncoder encoder,
                                    JwtUtils jwtUtils, ConfigProvider config) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.config = config;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/register", "/user/login")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/user/reset")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager(), jwtUtils, config)) // For user:pswd
                .addFilter(new AuthorizationFilter(authenticationManager(), jwtUtils, config)) // For jwt
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

}
