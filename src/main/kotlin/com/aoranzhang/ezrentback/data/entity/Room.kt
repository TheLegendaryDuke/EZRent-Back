package com.aoranzhang.ezrentback.data.entity

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.xml.stream.events.Comment

@Entity
@Table(name = "room")
class Room : AbstractPersistentObject {

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "suite", nullable = false)
    var suite: Suite? = null

    var name: String = ""

    var comment: String = ""

    @Enumerated(value = EnumType.STRING)
    var roomType: RoomType? = null

    @NotNull
    var rent: Int = 0

    @NotNull
    var available: Int = 0

    var isPublicSpace: Boolean = false
        get() = !(roomType == RoomType.BEDROOM || roomType == RoomType.MASTER)

    constructor(): super() {}

    constructor(room: Room): super(room.id, room.version) {
        this.suite = room.suite
        this.name = room.name
        this.comment = room.comment
        this.roomType = room.roomType
        this.rent = room.rent
        this.available = room.available
    }

    constructor(suite: Suite, name: String, comment: String = "", roomType: RoomType, rent: Int, available: Int = 1): super() {
        this.suite = suite
        this.name = name
        this.comment = comment
        this.roomType = roomType
        this.rent = rent
        this.available = available
    }

    fun copy(room: Room) {
        this.suite = room.suite
        this.name = room.name
        this.comment = room.comment
        this.roomType = room.roomType
        this.rent = room.rent
        this.available = room.available
    }
}
