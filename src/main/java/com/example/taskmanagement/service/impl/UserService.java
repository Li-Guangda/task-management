package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.mapper.UserMapper;
import com.example.taskmanagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private JwtEncoder jwtEncoder;

    @Override
    public boolean signUp(String username, String password, String role) {
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = "{bcrypt}" + bCryptPasswordEncoder.encode(password);
        Integer res = userMapper.addUser(username, encodedPassword, role);
        if (res != 1)
            return false;
        return true;
    }

    @Override
    public String logIn(String username, String password) {
        Authentication beforeAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication afterAuthentication;
        try {
            afterAuthentication = authenticationManager.authenticate(beforeAuthentication);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(afterAuthentication);
        } catch (AuthenticationException e) {
            return null;
        }

        String scope = afterAuthentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        Instant now = Instant.now();
        long expiry = 36000L;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(username)
                .claim("authorities", scope)
                .build();
        String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return token;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtEncoder(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
}
