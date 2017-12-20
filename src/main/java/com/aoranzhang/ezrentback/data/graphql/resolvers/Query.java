package com.aoranzhang.ezrentback.data.graphql.resolvers;

import com.aoranzhang.ezrentback.data.entity.User;
import com.aoranzhang.ezrentback.service.UserService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Set;

@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    public User user() {
        String user = (String) httpSession.getAttribute("userEmail");
        if(user != null && user.length() != 0) {
            return userService.getUserByEmail(user);
        }
        return null;
    }

    public Set<City> cities() {
        return cityService.getAllCities();
    }

}
