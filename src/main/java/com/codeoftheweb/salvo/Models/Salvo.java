package com.codeoftheweb.salvo.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private int turn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name = "salvoLocation")
    Set<String> locations;


    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public int getTurn() {
        return turn;
    }
    public Set<String> getLocations() {
        return locations;
    }

    public Salvo() {
    }

    public Salvo(int turn, GamePlayer gamePlayer, Set<String> locations) {
        this.gamePlayer = gamePlayer;
        this.turn = turn;
        this.locations = locations;
    }

    public long getId() {
        return id;
    }

    public Map<String, Object> makeSalvoDTO() {
        Map<String, Object> salvoDto = new LinkedHashMap<String, Object>();
        salvoDto.put("turn", this.getTurn());
        salvoDto.put("player", this.getGamePlayer().getId());
        salvoDto.put("locations", this.getLocations());
        return salvoDto;
    }


}
