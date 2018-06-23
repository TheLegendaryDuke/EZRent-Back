package com.aoranzhang.ezrentback.data.repository

import com.aoranzhang.ezrentback.data.entity.City
import org.springframework.data.repository.CrudRepository

interface CityRepository : CrudRepository<City, String> {
    fun findByName(name: String): City?
}
