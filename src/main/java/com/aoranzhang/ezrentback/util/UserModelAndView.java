package com.aoranzhang.ezrentback.util;

import com.aoranzhang.ezrentback.data.entity.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

public class UserModelAndView extends ModelAndView {

    public UserModelAndView(String viewName, Object user, boolean loadFullUser) {
        super(viewName);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            if(loadFullUser) {
                super.addObject("user", user);
                super.addObject("userName", ((User)user).getName());
            }else {
                super.addObject("userName", user);
            }
        } else {
            super.addObject("userName", null);
        }
    }
}
