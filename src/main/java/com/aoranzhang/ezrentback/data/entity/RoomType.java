package com.aoranzhang.ezrentback.data.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomType {
    BEDROOM("bedroom"),
    WASHROOM("washroom"),
    KITCHEN("kitchen"),
    MASTER("master bedroom");

    public static RoomType privateRooms[] = {BEDROOM, MASTER};
    public static RoomType publicRooms[] = {WASHROOM, KITCHEN};

    private String type;

    RoomType(String type) {
        this.type = type;
    }

    @JsonValue
    public String type() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

    public String getName() {
        return type;
    }
}
