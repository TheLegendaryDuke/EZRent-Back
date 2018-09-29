package com.aoranzhang.ezrentback.service

import com.aoranzhang.ezrentback.data.entity.Building
import com.aoranzhang.ezrentback.data.entity.City
import com.aoranzhang.ezrentback.data.entity.User
import com.aoranzhang.ezrentback.data.repository.BuildingRepository
import com.aoranzhang.ezrentback.data.repository.UserRepository
import com.aoranzhang.ezrentback.util.JavaToKotlin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class BuildingService {
    @Autowired
    private lateinit var buildingRepository: BuildingRepository

    @Autowired
    private val userRepository: UserRepository? = null

    fun getById(id: String): Building? {
        return JavaToKotlin.OptionalToNullable(buildingRepository.findById(id))
    }

    @Transactional
    fun save(building: Building) : Building {
//        if (building.owner != null) {
//            val owner = userRepository!!.findByEmail(building.owner.email!!)
//            if (owner != null) {
//                building.setOwner(owner)
//            }
//        }
        return buildingRepository.save(building)
    }

    fun listByOwner(owner: User): Set<Building> {
        return owner.buildings
    }

    fun listByCity(city: City): Set<Building> {
        return city.buildings
    }
}
