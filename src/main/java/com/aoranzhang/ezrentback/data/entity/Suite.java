package com.aoranzhang.ezrentback.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "suite")
public class Suite extends AbstractPersistentObject{

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "building", nullable = false)
    private Building building;

    @OneToMany(
            mappedBy = "suite",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonIgnore
    private Set<Room> rooms;

    @NotEmpty
    private String floor;

    @NotNull
    private Integer availability;

    @NotEmpty
    private String name;

    @NotNull
    private Integer rent;

    private boolean negotiable;

    public void copy(Suite suite) {
        if(suite.getId() != null && !suite.getId().equals("")) {
            setId(suite.getId());
        }
        setBuilding(suite.getBuilding());
        setAvailability(suite.getAvailability());
        setFloor(suite.getFloor());
        setName(suite.getName());
        setNegotiable(suite.isNegotiable());
        setRent(suite.getRent());
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public boolean isNegotiable() {
        return negotiable;
    }

    public void setNegotiable(boolean negotiable) {
        this.negotiable = negotiable;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

}
