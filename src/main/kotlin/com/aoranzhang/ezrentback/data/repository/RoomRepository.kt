package com.aoranzhang.ezrentback.data.repository

import com.aoranzhang.ezrentback.data.entity.Room
import org.springframework.data.repository.CrudRepository

interface RoomRepository : CrudRepository<Room, String>
