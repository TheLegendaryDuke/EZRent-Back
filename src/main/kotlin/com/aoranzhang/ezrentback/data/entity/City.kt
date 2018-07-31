package com.aoranzhang.ezrentback.data.entity

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
@Table(name = "city")
class City : AbstractPersistentObject {
    var name: String = ""

    var longitude: Double = 0.0
    var latitude: Double = 0.0

    @OneToMany(mappedBy = "city", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    var buildings: Set<Building> = HashSet()


    constructor() : super() {}

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