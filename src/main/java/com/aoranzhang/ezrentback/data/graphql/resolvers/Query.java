package com.aoranzhang.ezrentback.data.graphql.resolvers;

import com.aoranzhang.ezrentback.data.entity.Building;
import com.aoranzhang.ezrentback.data.entity.City;
import com.aoranzhang.ezrentback.data.entity.User;
import com.aoranzhang.ezrentback.service.BuildingService;
import com.aoranzhang.ezrentback.service.CityService;
import com.aoranzhang.ezrentback.service.UserService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private BuildingService buildingService;

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

    public List<String> socialInfo() {
        if(!((Boolean) httpSession.getAttribute("onGoingRegister"))) {
            return null;
        }
        List<String> list = new ArrayList();
        list.add((String) httpSession.getAttribute("newEmail"));
        list.add((String) httpSession.getAttribute("newName"));

        httpSession.setAttribute("onGoingRegister", false);

        return list;
    }

    public Set<Building> buildings(String city) {
        return buildingService.listByCity(cityService.findCityByName(city));
    }

    public Set<Building> properties(String email) {
        return buildingService.listByOwner(userService.getUserByEmail(email));
    }
}
