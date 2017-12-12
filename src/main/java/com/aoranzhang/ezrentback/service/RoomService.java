package com.aoranzhang.ezrentback.service;

import com.aoranzhang.ezrentback.data.entity.Room;
import com.aoranzhang.ezrentback.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {
  @Autowired
  private RoomRepository roomRepository;

  @Transactional
  public Room saveRoom(Room room) {
    Room existing = roomRepository.findOne(room.getId());
    if(existing != null) {
      existing.copy(room);
      return roomRepository.save(existing);
    }
    return roomRepository.save(room);
  }
}
