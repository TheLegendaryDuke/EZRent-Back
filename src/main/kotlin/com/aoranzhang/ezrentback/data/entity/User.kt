package com.aoranzhang.ezrentback.data.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.common.collect.ImmutableSet

import javax.persistence.*
import java.util.Date

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "app_user")
class User : AbstractPersistentObject {

    @Column(name = "roomType")
    val name: String

    @Column(name = "email", unique = true)
    val email: String

    @Column(name = "password")
    var password: String

    @Column(name = "last_login_date", columnDefinition = "timestamp with time zone")
    @Temporal(TemporalType.TIMESTAMP)
    val lastLogin: Date

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    val buildings: Set<Building>

    constructor(id: String, version: Int, name: String, email: String, password: String, lastLogin: Date, buildings: Set<Building>) : super(id, version) {
        this.name = name
        this.email = email
        this.password = password
        this.lastLogin = lastLogin
        this.buildings = buildings
    }

    constructor(name: String, email: String, password: String, lastLogin: Date, buildings: Set<Building>): super() {
        this.name = name
        this.email = email
        this.password = password
        this.lastLogin = lastLogin
        this.buildings = buildings
    }

    constructor(user: User): super(user.id, user.version) {
        this.name = user.name
        this.email = user.email
        this.password = user.password
        this.lastLogin = user.lastLogin
        this.buildings = user.buildings
    }
}
