package com.aoranzhang.ezrentback.data.graphql.resolvers;

import com.aoranzhang.ezrentback.data.entity.User;
import com.aoranzhang.ezrentback.service.UserService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    private UserService userService;

    public User user(String id) {
        return userService.getUserById(id);
    }
}
