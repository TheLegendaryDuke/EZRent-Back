package com.aoranzhang.ezrentback.util

import java.util.UUID

object IdGenerator {
    fun createId() = UUID.randomUUID().toString()
}
