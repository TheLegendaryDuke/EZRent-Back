package com.aoranzhang.ezrentback.spring.social;

import com.aoranzhang.ezrentback.data.entity.User;
import com.aoranzhang.ezrentback.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class SocialSignInAdapter implements SignInAdapter {

    private final static Log logger = LogFactory.getLog(SocialSignInAdapter.class);

    private UserService userService;

    private HttpSession httpSession;

    public SocialSignInAdapter(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @Override
    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
        User user = userService.getUserByEmail(localUserId);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(localUserId, user.getPassword(), new HashSet<>()));
        httpSession.setAttribute("userEmail", user.getEmail());
        httpSession.setAttribute("userName", user.getName());

        return null;
    }
}
