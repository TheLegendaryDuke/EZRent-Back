package com.aoranzhang.ezrentback.util;

import java.util.UUID;

public class IdGenerator {
    public static String createId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
