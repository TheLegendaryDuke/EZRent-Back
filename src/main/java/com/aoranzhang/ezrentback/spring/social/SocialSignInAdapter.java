package com.aoranzhang.ezrentback.spring.social;

import com.aoranzhang.ezrentback.data.entity.User;
import com.aoranzhang.ezrentback.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.HashSet;

public class SocialSignInAdapter implements SignInAdapter {
    private UserService userService;

    public SocialSignInAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
        User user = userService.getUserByEmail(localUserId);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        localUserId, user.getPassword(), new HashSet<>()));

        return null;
    }
}
