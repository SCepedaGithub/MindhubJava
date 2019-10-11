package com.codeoftheweb.salvo.Controllers;

import com.codeoftheweb.salvo.Models.*;
import com.codeoftheweb.salvo.Repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.Repositories.GameRepository;
import com.codeoftheweb.salvo.Repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GamePlayerRepository gamePlayerRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/games")
    public Map<String, Object> getAll(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();

        if (isGuest(authentication)) {
            dto.put("player", "Guest");
        } else {
            Player player = playerRepository.findByUserName(authentication.getName());
            dto.put("player", player.makePlayerDTO());
        }
        dto.put("games", gameRepository.findAll()
                    .stream()
                    .map(game -> game.makeGameDTO())
                .collect(Collectors.toList()));
        return dto;
        }


    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> createGame(Authentication authentication) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>("No esta loggeado", HttpStatus.UNAUTHORIZED);
        } else {
            Player player = playerRepository.findByUserName(authentication.getName());

            Game game = gameRepository.save(new Game());
            GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, game));

            return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
        }
    }

    @RequestMapping(path = "/game/{id}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> joinGame(Authentication authentication, @PathVariable Long id) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>("No esta loggeado", HttpStatus.UNAUTHORIZED);
        } else {
            Player player = playerRepository.findByUserName(authentication.getName());
            Game game = gameRepository.findById(id).get();
            if (game == null) {
                return new ResponseEntity<>("No existe el juego", HttpStatus.FORBIDDEN);
            } else {
                if (game.getGamePlayers().size() == 2) {
                    return new ResponseEntity<>("Game is full", HttpStatus.FORBIDDEN);
                } else {
                    GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, game));
                    return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
                }
            }

        }
    }






    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> CreatePlayer(@RequestParam String username, @RequestParam String password) {

        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByUserName(username) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(username, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }


    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> getAllGameViews(Authentication authentication, @PathVariable Long gamePlayerId) {

        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId).get();

        if (authentication.getName() == gamePlayer.getPlayer().getUserName()) {
            return ResponseEntity.ok(makeGamePlayerDTO2(gamePlayerRepository.findById(gamePlayerId)
                    .get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        //return makeGamePlayerDTO2();

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

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
