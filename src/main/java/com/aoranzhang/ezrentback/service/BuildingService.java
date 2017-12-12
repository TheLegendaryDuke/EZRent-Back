package com.aoranzhang.ezrentback.service;

import com.aoranzhang.ezrentback.data.entity.Building;
import com.aoranzhang.ezrentback.data.entity.City;
import com.aoranzhang.ezrentback.data.entity.User;
import com.aoranzhang.ezrentback.data.repository.BuildingRepository;
import com.aoranzhang.ezrentback.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    public Building getById(String id) {
        return buildingRepository.findOne(id);
    }

    @Transactional
    public void save(Building building) {
        if(building.getOwner() != null) {
            User owner = userRepository.findByEmail(building.getOwner().getEmail());
            if(owner != null) {
                building.setOwner(owner);
            }
        }
        buildingRepository.save(building);
    }

    public Set<Building> listByOwner(User owner) {
        return owner.getBuildings();
    }

    public Set<Building> listByCity(City city) {
        return city.getBuildings();
    }
}
