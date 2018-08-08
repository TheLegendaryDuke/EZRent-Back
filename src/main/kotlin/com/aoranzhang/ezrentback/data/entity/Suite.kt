package com.aoranzhang.ezrentback.data.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.common.collect.ImmutableSet

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "suite")
class Suite : AbstractPersistentObject {

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "building", nullable = false)
    var building: Building? = null

    @OneToMany(mappedBy = "suite", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    var rooms: Set<Room> = HashSet()

    var floor: String = ""

    @NotNull
    var available: Int = 0

    var name: String = ""

    @NotNull
    var rent: Int = 0

    var isRentNegotiable: Boolean = false

    constructor(): super() {}

    constructor(building: Building, rooms: Set<Room>, floor: String, available: Int = 1, name: String, rent: Int, isRentNegotiable: Boolean = true) : super() {
        this.building = building
        this.rooms = rooms
        this.floor = floor
        this.available = available
        this.name = name
        this.rent = rent
        this.isRentNegotiable = isRentNegotiable
    }

    constructor(suite: Suite): super(suite.id, suite.version) {
        this.building = suite.building
        this.rooms = suite.rooms
        this.floor = suite.floor
        this.available = suite.available
        this.name = suite.name
        this.rent = suite.rent
        this.isRentNegotiable = suite.isRentNegotiable
    }

    fun copy(suite: Suite) {
        this.building = suite.building
        this.rooms = suite.rooms
        this.floor = suite.floor
        this.available = suite.available
        this.name = suite.name
        this.rent = suite.rent
        this.isRentNegotiable = suite.isRentNegotiable
    }
}
