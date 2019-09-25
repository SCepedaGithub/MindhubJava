package com.codeoftheweb.salvo.Models;

import com.codeoftheweb.salvo.Models.GamePlayer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    public Player() { }

    public Player( String userName) {

        this.userName = userName;
    }

    public long getId() {

        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public String getUserName() {

        return userName;
    }


    public Set<GamePlayer> getGamePlayers() {

        return gamePlayers;
    }
}