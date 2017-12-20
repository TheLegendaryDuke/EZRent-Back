package com.aoranzhang.ezrentback.spring.security;

import com.aoranzhang.ezrentback.data.entity.User;
import com.aoranzhang.ezrentback.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashSet;

@RestController
public class RegisterController {

    @Autowired
    ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;

    private static final transient org.slf4j.Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    @PostMapping("/register")
    public User register(HttpServletRequest request, @RequestParam(name = "username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
        User user = new User();
        user.setLastLogin(new Date());
        user.setName(username);
        user.setEmail(email);
        user.setPassword(password);
        try {
            userService.saveUser(user);
        }catch (DataIntegrityViolationException exception) {
            if(exception.getCause() instanceof ConstraintViolationException) {
                //TODO: handle this
                return null;
            }
        }

        try {
            request.login(user.getEmail(), user.getPassword());
        }catch (ServletException e) {
            LOG.error("Login failed:", e);
        }

        providerSignInUtils.doPostSignUp(user.getEmail(), RequestContextHolder.getRequestAttributes());

        httpSession.setAttribute("userEmail", user.getEmail());
        httpSession.setAttribute("userName", user.getName());

        return user;
    }
}
