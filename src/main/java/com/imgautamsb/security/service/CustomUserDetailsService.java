package com.imgautamsb.security.service;

import com.imgautamsb.db.UserMapper;
import com.imgautamsb.security.entity.TenantUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TenantUser tenantUser = userMapper.findUserByUsername(username);

        if (tenantUser == null) {
            System.out.println("NULL");
            throw new UsernameNotFoundException("User not found");
        }
        return tenantUser;
    }

    public List<String> getOriginList() {
        return userMapper.getOriginList();
    }
}
