package com.myblog.config;

import com.myblog.entity.User;
import com.myblog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        
        // 这里返回一个Spring Security的UserDetails实现
        // 为简化，直接使用Spring Security提供的User类
        org.springframework.security.core.userdetails.User.UserBuilder builder = 
            org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        builder.password(user.getPassword());
        builder.authorities("USER"); // 可以根据用户角色动态设置权限
        
        return builder.build();
    }
}