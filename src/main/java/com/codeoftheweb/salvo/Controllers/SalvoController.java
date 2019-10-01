package com.codeoftheweb.salvo.Controllers;

import com.codeoftheweb.salvo.Models.*;
import com.codeoftheweb.salvo.Repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.Repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

        @Autowired
        private GameRepository repo;
    @Autowired
    private GamePlayerRepository repo2;

    @RequestMapping("/games")
        public List<Map<String, Object>> getAll() {
            return repo.findAll()
                    .stream()
                    .map(game -> game.makeGameDTO())
                    .collect(Collectors.toList()) ;

        }


    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String, Object> getAllGameViews(@PathVariable Long gamePlayerId) {
        return makeGamePlayerDTO2(repo2.findById(gamePlayerId)
                .get());

    }

    private Map<String, Object> makeGamePlayerDTO2(GamePlayer gamePlayer) {
        Map<String, Object> dto2 = new LinkedHashMap<String, Object>();
        dto2.put("id", gamePlayer.getGame().getId());
        dto2.put("created", gamePlayer.getGame().getCreationDate());
        dto2.put("gamePlayers", gamePlayer.getGame().getGamePlayers()
                .stream()
                .map(gamePlayer1 -> gamePlayer1.makeGamePlayerDTO())
                .collect(Collectors.toList()));
        dto2.put("ships", gamePlayer.getShips()
                .stream()
                .map(ship -> ship.makeShipDTO())
                .collect(Collectors.toList()));
        dto2.put("salvoes", gamePlayer.getGame().getGamePlayers()
                .stream()
                .flatMap(gamePlayer1 -> gamePlayer1.getSalvoes()
                        .stream()
                        .map(salvo -> salvo.makeSalvoDTO()))
                .collect(Collectors.toList()));
        return dto2;

    }



}
