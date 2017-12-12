package com.aoranzhang.ezrentback.data.repository;

import com.aoranzhang.ezrentback.data.entity.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, String> {
    City findByName(String name);
}
