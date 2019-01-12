package com.aoranzhang.ezrentback.data.graphql.resolvers

import com.aoranzhang.ezrentback.data.entity.Building
import com.aoranzhang.ezrentback.data.entity.City
import com.aoranzhang.ezrentback.data.entity.User
import com.aoranzhang.ezrentback.service.BuildingService
import com.aoranzhang.ezrentback.service.CityService
import com.aoranzhang.ezrentback.service.UserService
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Query @Autowired constructor(
        private val userService: UserService,
        private val cityService: CityService,
        private val buildingService: BuildingService
) : GraphQLQueryResolver {

    val LOGGER = LoggerFactory.getLogger(Query::class.java)!!

    fun cities(): Set<City> {
        return cityService.allCities
    }

    fun buildings(city: String): Set<Building>? {
        val nullableCity = cityService.findCityByName(city)
        return if (nullableCity == null) {
            null
        }else {
            return buildingService.listByCity(nullableCity)
        }
    }

    fun properties(email: String): Set<Building> {
        val user = userService.getUserByEmail(email)
        return if (user != null) {
            buildingService.listByOwner(user)
        }else {
            HashSet()
        }
    }
}
