package com.aoranzhang.ezrentback.data.entity

interface PersistentObject {
    val id: String
    val version: Int
}
