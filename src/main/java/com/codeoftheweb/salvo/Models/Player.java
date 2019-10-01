package com.codeoftheweb.salvo.Models;

import com.codeoftheweb.salvo.Models.GamePlayer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
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

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<Score> scores;

    public Player() { }

    public Map<String, Object> makePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("email", this.getUserName());
        return dto;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public Player(String userName) {

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

    public Optional<Score> getScore(Game game) {
        Optional<Score> score = this.getScores().stream().filter(score1 -> score1.getGame()
                .getId() == game.getId()).findFirst();
        return score;

    }
}
