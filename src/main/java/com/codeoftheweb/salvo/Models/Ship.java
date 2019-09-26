package com.codeoftheweb.salvo.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String shipType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name = "shipLocation")
    Set<String> locations;

    public String getShipType() {
        return shipType;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public Set<String> getLocations() {
        return locations;
    }

    public Ship() {
    }

    public Ship(GamePlayer gamePlayer, String shipType, Set<String> locations) {
        this.gamePlayer = gamePlayer;
        this.shipType = shipType;
        this.locations = locations;
    }

    public long getId() {
        return id;
    }


}
