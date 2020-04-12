package com.imgautamsb.security.config;

import com.imgautamsb.security.fliters.AuthenticationFilter;
import com.imgautamsb.security.fliters.CORSFilter;
import com.imgautamsb.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint unAuthorizedHandler;


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().
                csrf().disable()

                .exceptionHandling().authenticationEntryPoint(unAuthorizedHandler).and()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // allow anonymous resource requests
//                .antMatchers(
//                        HttpMethod.GET,
//                        "/",
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js"
//                ).permitAll()
                .antMatchers("/api/users/**").permitAll()
                .anyRequest().authenticated();
//        http.addFilterAfter(samlFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(corsConfFilter(), ChannelProcessingFilter.class);
        System.out.println("PS: " + bCryptPasswordEncoder.encode("123456"));
    }

    //TODO here
    // http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    // http.addFilterBefore(corsConfFilter(), ChannelProcessingFilter.class);
    // above two lines or below FilterChainProxy both are working

//    @Bean
//    public FilterChainProxy samlFilter() throws Exception {
//        List<SecurityFilterChain> chains = new ArrayList<>();
//
//        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/api/users/test/**"), corsConfFilter(),authenticationFilter()));
//        return new FilterChainProxy(chains);
//    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        return new AuthenticationFilter();
    }

    @Bean
    public CORSFilter corsConfFilter() {
        return new CORSFilter();
    }


    // this FilterRegistrationBean for set enabled to false means it stops only once call filter
    @Bean
    public FilterRegistrationBean authenticationRegistration(AuthenticationFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public FilterRegistrationBean corsRegistration(CORSFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
}
