package com.aoranzhang.ezrentback.data.repository

import com.aoranzhang.ezrentback.data.entity.Building
import org.springframework.data.repository.CrudRepository

interface BuildingRepository : CrudRepository<Building, String>
