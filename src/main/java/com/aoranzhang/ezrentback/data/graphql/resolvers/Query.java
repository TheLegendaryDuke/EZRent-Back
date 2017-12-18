package com.aoranzhang.ezrentback.data.graphql.resolvers;

import com.aoranzhang.ezrentback.data.entity.City;
import com.aoranzhang.ezrentback.data.entity.User;
import com.aoranzhang.ezrentback.service.CityService;
import com.aoranzhang.ezrentback.service.UserService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Query implements GraphQLQueryResolver {
//    @Autowired
//    private UserService userService;

    @Autowired
    private CityService cityService;

//    public User user(String id) {
//        return userService.getUserById(id);
//    }

    public Set<City> cities(){
        return cityService.getAllCities();
    }
}
