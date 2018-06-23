package com.aoranzhang.ezrentback.data.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.common.collect.ImmutableSet

import javax.persistence.*
import java.util.HashSet

@Entity
@Table(name = "building")
class Building : AbstractPersistentObject {
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "city", nullable = false)
    val city: City

    val address:String

    val postalCode: String

    @OneToMany(mappedBy = "building", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    val suites: Set<Suite>

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "owner", nullable = false)
    val owner: User

    val longitude: Double

    val latitude: Double

    val verified: Boolean

    constructor(city: City, address:String, postalCode: String, suites: Set<Suite>, owner: User, longitude: Double, latitude: Double, verified: Boolean) : super() {
        this.city = city
        this.address = address
        this.postalCode = postalCode
        this.suites = suites
        this.owner = owner
        this.latitude = latitude
        this.longitude = longitude
        this.verified = verified
    }


    constructor(building: Building) : super(building.id, building.version) {
        this.city = building.city
        this.address = building.address
        this.postalCode = building.postalCode
        this.suites = building.suites
        this.owner = building.owner
        this.latitude = building.latitude
        this.longitude = building.longitude
        this.verified = building.verified
    }
}
