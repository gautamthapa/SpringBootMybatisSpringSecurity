package com.imgautamsb.web;

import com.imgautamsb.entity.LoginRequest;
import com.imgautamsb.security.exceptions.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/test")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result, HttpServletRequest request) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationErrorService(result);
        if (errorMap != null) {
            return errorMap;
        }
        Authentication authentication = null;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
//        try {
//
//        } catch (AuthenticationException e) {
//            System.out.println(e.getMessage());
//        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("true", HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String sayHello(){
        System.out.println("Hello gautam");
       return  "true";
    }

}
