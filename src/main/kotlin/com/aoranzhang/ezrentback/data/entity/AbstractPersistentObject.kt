package com.aoranzhang.ezrentback.data.entity

import com.aoranzhang.ezrentback.util.IdGenerator

import javax.persistence.*

@MappedSuperclass
abstract class AbstractPersistentObject(
        @Id @Column(name = "id") override val id: String = IdGenerator.createId(),
        @Version @Column(name = "version") override val version: Int = 0
) : PersistentObject {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is PersistentObject) return false

        return id == other.id
    }

    override fun hashCode() = id.hashCode()

    override fun toString()= this.javaClass.name + "[id=" + id + "]"
}