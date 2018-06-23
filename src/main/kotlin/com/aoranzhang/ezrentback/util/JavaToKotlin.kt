package com.aoranzhang.ezrentback.util

import java.util.*

object JavaToKotlin {
    fun <T> OptionalToNullable(t : Optional<T>): T? {
        return if(t.isPresent) {
            t.get()
        }else {
            null
        }
    }
}