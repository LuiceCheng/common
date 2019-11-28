package com.example.common.config.security.jwt.service.impl;

import com.example.common.config.security.jwt.JwtUtil;
import com.example.common.config.security.jwt.service.IAuthService;
import com.example.common.entity.Msg;
import com.example.common.model.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/10/29 9:21
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    public AuthServiceImpl(AuthenticationManager authenticationManager, @Qualifier("userDetailsService") UserDetailsService userDetailsService, JwtUtil jwtUtil) {
//    public AuthServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//        this.jwtUtil = jwtUtil;
//    }

    @Override
    public Msg<JwtUser> login(String userName, String password) {
        Authentication authentication = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        return null;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public Msg<JwtUser> refresh(String token) {
        return null;
    }

    @Override
    public JwtUser getUserByToken(String token) {
        return null;
    }

    private Authentication authenticate(String userName, String password){
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            return authenticate;
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
