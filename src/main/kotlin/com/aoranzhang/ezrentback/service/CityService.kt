package com.aoranzhang.ezrentback.service

import com.aoranzhang.ezrentback.data.entity.City
import com.aoranzhang.ezrentback.data.repository.CityRepository
import com.aoranzhang.ezrentback.util.JavaToKotlin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CityService {

    @Autowired
    private val cityRepository: CityRepository? = null

    val allCities: Set<City>
        get() {
            val cities = HashSet<City>()
            cityRepository!!.findAll().forEach { c: City -> cities.add(c) }
            return cities
        }

    @Transactional
    fun createNewCity(city: City) {
        if (cityRepository!!.findByName(city.name) != null) {
            cityRepository.findByName(city.name)
        } else {
            cityRepository.save(city)
        }
    }

    fun findCityByName(name: String): City? {
        return cityRepository!!.findByName(name)
    }

    fun getCityById(ID: String): City? {
        return JavaToKotlin.OptionalToNullable(cityRepository!!.findById(ID))
    }
}
