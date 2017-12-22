package com.aoranzhang.ezrentback.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller(value = "register")
public class RegisterController {

    @Value("${application.URL}")
    private String applicationURL;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @GetMapping
    public RedirectView postSocial(WebRequest request) {
        ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);

        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);

        if(connection != null) {
            UserProfile userProfile = connection.fetchUserProfile();
            httpSession.setAttribute("onGoingRegister", true);
            httpSession.setAttribute("newEmail", userProfile.getEmail());
            httpSession.setAttribute("newName", userProfile.getName());
        }
        return new RedirectView(applicationURL+"/registerWithSocial");
    }
}
