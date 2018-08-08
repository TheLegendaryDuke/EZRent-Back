package com.aoranzhang.ezrentback.service

import com.aoranzhang.ezrentback.data.entity.Room
import com.aoranzhang.ezrentback.data.repository.RoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService {
    @Autowired
    private lateinit var roomRepository: RoomRepository

    @Transactional
    fun saveRoom(room: Room): Room? {
        return roomRepository.save(room)
    }
}
