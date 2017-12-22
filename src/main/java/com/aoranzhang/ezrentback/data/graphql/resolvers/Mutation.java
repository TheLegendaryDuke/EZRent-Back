package com.aoranzhang.ezrentback.data.graphql.resolvers;

import com.aoranzhang.ezrentback.data.entity.User;
import com.aoranzhang.ezrentback.service.UserService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;

@Component
public class Mutation implements GraphQLMutationResolver {
    @Autowired
    private UserService userService;

    @Autowired
    ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private HttpSession httpSession;

    public User register(String email, String username, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(username);
        user.setPassword(password);
        user.setLastLogin(new Date());
        try {
            userService.saveUser(user);
            ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), new HashSet<>());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            RequestAttributes webRequest = RequestContextHolder.getRequestAttributes();

            providerSignInUtils.doPostSignUp(user.getEmail(), webRequest);

            httpSession.setAttribute("userEmail", user.getEmail());
            httpSession.setAttribute("userName", user.getName());
        }catch (Exception e) {
            return null;
        }
        return user;
    }
}
