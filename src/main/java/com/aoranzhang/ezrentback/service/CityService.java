package com.aoranzhang.ezrentback.service;

import com.aoranzhang.ezrentback.data.entity.City;
import com.aoranzhang.ezrentback.data.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public void createNewCity(City city) {
        if(cityRepository.findByName(city.getName()) != null) {
            cityRepository.findByName(city.getName());
        }else {
            cityRepository.save(city);
        }
    }

    public Set<City> getAllCities() {
        Set<City> cities = new HashSet<>();
        cityRepository.findAll().forEach((City c) -> cities.add(c));
        return cities;
    }

    public City findCityByName(String name) {
        return cityRepository.findByName(name);
    }

    public City getCityById(String ID) {
        return cityRepository.findOne(ID);
    }
}
