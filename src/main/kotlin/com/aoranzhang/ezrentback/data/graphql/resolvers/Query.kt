package com.aoranzhang.ezrentback.data.graphql.resolvers

import com.aoranzhang.ezrentback.data.entity.Building
import com.aoranzhang.ezrentback.data.entity.City
import com.aoranzhang.ezrentback.data.entity.User
import com.aoranzhang.ezrentback.service.BuildingService
import com.aoranzhang.ezrentback.service.CityService
import com.aoranzhang.ezrentback.service.UserService
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.servlet.http.HttpSession

@Component
class Query : GraphQLQueryResolver {
    @Autowired
    private val httpSession: HttpSession? = null

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val cityService: CityService? = null

    @Autowired
    private val buildingService: BuildingService? = null

    fun user(): User? {
        val user = httpSession!!.getAttribute("userEmail") as String
        return if (user.length != 0) {
            userService!!.getUserByEmail(user)
        } else null
    }

    fun cities(): Set<City> {
        return cityService!!.allCities
    }

    fun buildings(city: String): Set<Building>? {
        val nullableCity = cityService!!.findCityByName(city)
        return if (nullableCity == null) {
            null
        }else {
            return buildingService!!.listByCity(nullableCity)
        }
    }

    fun properties(email: String): Set<Building> {
        val user = userService!!.getUserByEmail(email)
        return if (user != null) {
            buildingService!!.listByOwner(user)
        }else {
            HashSet()
        }
    }
}
