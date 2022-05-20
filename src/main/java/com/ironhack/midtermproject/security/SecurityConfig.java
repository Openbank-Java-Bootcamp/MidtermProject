package com.ironhack.midtermproject.security;

import com.ironhack.midtermproject.filter.CustomAuthenticationFilter;
import com.ironhack.midtermproject.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/api/admins/saveaccount").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/admins/savecreditcard").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/admins/savesavings").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/admins/savethirdpartyuser").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PATCH, "/api/savings/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_HOLDER");
        http.authorizeRequests().antMatchers(PATCH, "/api/creditcards/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_HOLDER");
        http.authorizeRequests().antMatchers(GET, "/api/admins/balances").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/accountholders/balances").hasAnyAuthority("ROLE_HOLDER");
        http.authorizeRequests().antMatchers(PATCH, "/api/accountholders/transfer").hasAnyAuthority("ROLE_HOLDER");
        http.authorizeRequests().antMatchers(PATCH, "/api/accountholders/transfer/thirdparty").hasAnyAuthority("ROLE_HOLDER");
        http.authorizeRequests().antMatchers(DELETE, "/api/admins/deletethirdparty/{id}").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PATCH,"/api/thirdpartyusers/transfer").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}