package com.codeoftheweb.salvo.Controllers;
import com.codeoftheweb.salvo.Models.Game;
import com.codeoftheweb.salvo.Models.GamePlayer;
import com.codeoftheweb.salvo.Models.Player;
import com.codeoftheweb.salvo.Models.Ship;
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
                    .map(game -> makeGameDTO(game))
                    .collect(Collectors.toList()) ;

        }


    private Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("creationDate", game.getCreationDate());
        dto.put("gamePlayers",getAllGamePlayers(game.getGamePlayers()));
        return dto;
    }


    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String, Object> getAllGameViews(@PathVariable Long gamePlayerId) {
        return makeGamePlayerDTO2(repo2.findById(gamePlayerId)
                .get());

    }

    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
        return dto;

    }

    private Map<String, Object> makeGamePlayerDTO2(GamePlayer gamePlayer) {
        Map<String, Object> dto2 = new LinkedHashMap<String, Object>();
        dto2.put("id", gamePlayer.getGame().getId());
        dto2.put("created", gamePlayer.getGame().getCreationDate());
        dto2.put("gamePlayers", getAllGamePlayers(gamePlayer.getGame().getGamePlayers()));
        dto2.put("ships", getAllShips(gamePlayer.getShips()));
        return dto2;

    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        return dto;
    }

    public List<Map<String, Object>> getAllGamePlayers(Set<GamePlayer> gamePlayers) {
        return gamePlayers
                .stream()
                .map(gamePlayer -> makeGamePlayerDTO(gamePlayer))
                .collect(Collectors.toList()) ;

    }

    public List<Map<String, Object>> getAllShips(Set<Ship> ships) {
        return ships
                .stream()
                .map(ship -> makeShipDTO(ship))
                .collect(Collectors.toList());
    }

    private Map<String, Object> makeShipDTO(Ship ship) {
        Map<String, Object> shipDto = new LinkedHashMap<String, Object>();
        shipDto.put("type", ship.getShipType());
        shipDto.put("locations", ship.getLocations());
        return shipDto;
    }

}
