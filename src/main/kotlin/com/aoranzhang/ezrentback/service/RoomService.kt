package com.aoranzhang.ezrentback.service

import com.aoranzhang.ezrentback.data.entity.Room
import com.aoranzhang.ezrentback.data.repository.RoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService {
    @Autowired
    private val roomRepository: RoomRepository? = null

    @Transactional
    fun saveRoom(room: Room): Room {
        val existing = roomRepository!!.findById(room.id)
        if (existing.isPresent) {
            existing.get().copy(room)
            return roomRepository.save(existing.get())
        }
        return roomRepository.save(room)
    }
}
