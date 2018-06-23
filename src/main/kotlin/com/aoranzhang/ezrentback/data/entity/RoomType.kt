package com.aoranzhang.ezrentback.data.entity

import com.fasterxml.jackson.annotation.JsonValue

enum class RoomType constructor(val roomType: String) {
    BEDROOM("bedroom"),
    WASHROOM("washroom"),
    KITCHEN("kitchen"),
    MASTER("master bedroom");

    @JsonValue
    fun type(): String {
        return this.roomType
    }

    override fun toString(): String {
        return this.roomType
    }

    companion object {
        var privateRooms = arrayOf(BEDROOM, MASTER)
        var publicRooms = arrayOf(WASHROOM, KITCHEN)
    }
}
