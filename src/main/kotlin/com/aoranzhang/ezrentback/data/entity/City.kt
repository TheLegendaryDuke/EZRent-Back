package com.aoranzhang.ezrentback.data.entity

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
@Table(name = "city")
class City : AbstractPersistentObject {
    val name: String

    val longitude: Double
    val latitude: Double

    @OneToMany(mappedBy = "city", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    val buildings: Set<Building>

    constructor(city: City) : super(city.id, city.version) {
        this.name = city.name
        this.longitude = city.longitude
        this.latitude = city.latitude
        this.buildings = city.buildings
    }

    constructor(name: String, longitude: Double, latitude: Double, buildings: Set<Building>) : super() {
        this.name = name
        this.longitude = longitude
        this.latitude = latitude
        this.buildings = buildings
    }
}