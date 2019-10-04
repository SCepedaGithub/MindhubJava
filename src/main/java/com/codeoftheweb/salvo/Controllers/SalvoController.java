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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gRepo;
    @Autowired
    private GamePlayerRepository gpRepo;
    @Autowired
    private PlayerRepository pRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/games")
    public Map<String, Object> getAll(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();

        if (isGuest(authentication)) {
            dto.put("player", "Guest");
        } else {
            Player player = pRepo.findByUserName(authentication.getName());
            dto.put("player", player.makePlayerDTO());
        }
        dto.put("games", gRepo.findAll()
                    .stream()
                    .map(game -> game.makeGameDTO())
                .collect(Collectors.toList()));
        return dto;
        }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> CreatePlayer(@RequestParam String username, @RequestParam String password) {

        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        if (pRepo.findByUserName(username) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        pRepo.save(new Player(username, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }


    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> getAllGameViews(Authentication authentication, @PathVariable Long gamePlayerId) {

        GamePlayer gp = gpRepo.findById(gamePlayerId).get();

        if (authentication.getName() == gp.getPlayer().getUserName()) {
            return ResponseEntity.ok(makeGamePlayerDTO2(gpRepo.findById(gamePlayerId)
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



}
