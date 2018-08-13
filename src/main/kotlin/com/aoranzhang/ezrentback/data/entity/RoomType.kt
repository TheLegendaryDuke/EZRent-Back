package com.aoranzhang.ezrentback.data.entity

import com.fasterxml.jackson.annotation.JsonValue

enum class RoomType {
    BEDROOM,
    WASHROOM,
    KITCHEN,
    MASTER;

    @JsonValue
    fun type(): String {
        return this.name
    }

    override fun toString(): String {
        return this.name
    }

    companion object {
        var privateRooms = arrayOf(BEDROOM, MASTER)
        var publicRooms = arrayOf(WASHROOM, KITCHEN)
    }
}
