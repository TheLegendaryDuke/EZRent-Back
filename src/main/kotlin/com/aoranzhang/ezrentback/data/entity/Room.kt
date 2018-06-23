package com.aoranzhang.ezrentback.data.entity

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.xml.stream.events.Comment

@Entity
@Table(name = "room")
class Room : AbstractPersistentObject {

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "suite", nullable = false)
    var suite: Suite

    var name: String

    var comment: String

    @Enumerated(value = EnumType.STRING)
    var roomType: RoomType

    @NotNull
    var rent: Int

    @NotNull
    var available: Int

    var isForRent: Boolean
        get() = if (isPublicSpace) {
            false
        } else field

    var isPublicSpace: Boolean = false
        get() = !(roomType == RoomType.BEDROOM || roomType == RoomType.MASTER)

    constructor(room: Room): super(room.id, room.version) {
        this.suite = room.suite
        this.name = room.name
        this.comment = room.comment
        this.roomType = room.roomType
        this.rent = room.rent
        this.available = room.available
        this.isForRent = room.isForRent
    }

    constructor(suite: Suite, name: String, comment: String = "", roomType: RoomType, rent: Int, available: Int = 1, isForRent: Boolean): super() {
        this.suite = suite
        this.name = name
        this.comment = comment
        this.roomType = roomType
        this.rent = rent
        this.available = available
        this.isForRent = isForRent
    }

    fun copy(room: Room) {
        this.suite = room.suite
        this.name = room.name
        this.comment = room.comment
        this.roomType = room.roomType
        this.rent = room.rent
        this.available = room.available
        this.isForRent = room.isForRent
    }
}
