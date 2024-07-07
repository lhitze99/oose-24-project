package com.sse.ooseproject.models;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "room")
public class Room {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String number;
    private int seats;
    private boolean isAuditorium;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(mappedBy = "room")
    private List<RoomOccupancy> occupancies;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean getIsAuditorium() { return isAuditorium; }

    public void setAuditorium(boolean auditorium) {
        isAuditorium = auditorium;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<RoomOccupancy> getOccupancies() {
        return occupancies;
    }

    public void setOccupancies(List<RoomOccupancy> occupancies) {
        this.occupancies = occupancies;
    }
}
