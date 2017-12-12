package com.aoranzhang.ezrentback.data.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "room")
public class Room extends AbstractPersistentObject{
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "suite", nullable = false)
    private Suite suite;

    @NotEmpty
    private String name;

    @NotEmpty
    private String comment;

    @Enumerated(value = EnumType.STRING)
    private RoomType roomType;

    @NotNull
    private Integer rent;

    @NotNull
    private Integer available;

    private boolean forRent;

    private boolean publicSpace;

    public Room() {super();}

    public Room(Room room) {
        super();
        copy(room);
    }

    public void copy(Room room) {
        if(!(room.getId() == null || room.getId().equals(""))) {
            setId(room.getId());
        }
        this.suite = room.getSuite();
        this.name = room.getName();
        this.comment = room.getComment();
        this.roomType = room.getRoomType();
        this.rent = room.getRent();
        this.available = room.getAvailable();
        this.forRent = room.isForRent();
        this.publicSpace = room.isPublicSpace();
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public boolean isPublicSpace() {
        return !(roomType.equals(RoomType.BEDROOM) || roomType.equals(RoomType.MASTER));
    }

    public boolean isForRent() {
        if(publicSpace) {
            return false;
        }
        return forRent;
    }

    public void setForRent(boolean forRent) {
        this.forRent = forRent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Suite getSuite() {
        return suite;
    }

    public void setSuite(Suite suite) {
        this.suite = suite;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
