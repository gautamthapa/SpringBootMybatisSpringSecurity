package com.imgautamsb.security.fliters;

import com.google.gson.Gson;
import com.imgautamsb.security.entity.InvalidLoginResponse;
import com.imgautamsb.security.service.CustomUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestHeader = request.getHeader("Auth");
        String username = request.getParameter("username");
        String password = request.getParameter("username");
        Authentication authentication = null;
        System.out.println("***AuthenticationFilter***");
        System.out.println("Username: " + username);
        System.out.println("PS: " + password);
        chain.doFilter(request, response);
//        try {
//            authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            username,
//                            password
//                    )
//            );
//            if (authentication != null)
//                chain.doFilter(request, response);
//
//        } catch (AuthenticationException e) {
//            System.out.println(e.getMessage());
//            String jsonLoginResponse = new Gson().toJson(new InvalidLoginResponse(1001, "INVALID_USERNAME_AND_PASSWORD"));
//            logger.info("JwtAuthenticationEntryPoint.............");
//            response.setContentType("application/json");
//            response.setStatus(401);
//            response.getWriter().print(jsonLoginResponse);
//        }


    }
}
