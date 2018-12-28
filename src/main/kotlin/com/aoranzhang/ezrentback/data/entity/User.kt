package com.aoranzhang.ezrentback.data.entity

import com.aoranzhang.ezrentback.data.EnumListConverter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.common.collect.ImmutableSet

import javax.persistence.*
import java.util.Date

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "app_user")
class User : AbstractPersistentObject {

    @Column(name = "username")
    var name: String = ""

    @Column(name = "email", unique = true)
    var email: String = ""

    @Column(name = "password")
    var password: String = ""

    @Column(name = "last_login_date", columnDefinition = "timestamp with time zone")
    @Temporal(TemporalType.TIMESTAMP)
    var lastLogin: Date = Date()

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    var buildings: Set<Building> = HashSet()

    @Column
    @Convert(converter = EnumListConverter::class)
    var roles: Set<Role> = ImmutableSet.of(Role.ROLE_CLIENT)

    constructor(): super() {}

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

    constructor(id: String, version: Int) : super(id, version)
}
