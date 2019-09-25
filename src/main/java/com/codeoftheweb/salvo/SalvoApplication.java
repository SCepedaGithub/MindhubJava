package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.Models.Game;
import com.codeoftheweb.salvo.Models.GamePlayer;
import com.codeoftheweb.salvo.Models.Player;
import com.codeoftheweb.salvo.Repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.Repositories.GameRepository;
import com.codeoftheweb.salvo.Repositories.PlayerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class);
	}


	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository) {
		return (args) -> {
			// save players
			Player p1= new Player("j.bauer@ctu.gov");
			Player p2=new Player("c.obrian@ctu.gov");
			Player p3=new Player("kim_bauer@gmail.com");
			Player p4=new Player("t.almeida@ctu.gov");
			playerRepository.saveAll(Arrays.asList(p1,p2,p3,p4));

			Date date1 = new Date();
			Date date2= Date.from(date1.toInstant().plusSeconds(3600));
			Date date3= Date.from(date2.toInstant().plusSeconds(3600));
			Date date4= Date.from(date3.toInstant().plusSeconds(3600));
			Date date5= Date.from(date4.toInstant().plusSeconds(3600));
			Date date6= Date.from(date5.toInstant().plusSeconds(3600));
			Date date7= Date.from(date6.toInstant().plusSeconds(3600));
			Date date8= Date.from(date7.toInstant().plusSeconds(3600));

			Game g1= new Game(date1);
			Game g2= new Game(date2);
			Game g3= new Game(date3);
			Game g4= new Game(date4);
			Game g5= new Game(date5);
			Game g6= new Game(date6);
			Game g7= new Game(date7);
			Game g8= new Game(date8);

			gameRepository.saveAll(Arrays.asList(g1,g2,g3,g4,g5,g6,g7,g8));

			GamePlayer gp1= new GamePlayer(date1,p1,g1);
			GamePlayer gp2= new GamePlayer(date1,p2,g1);
			GamePlayer gp3= new GamePlayer(date2,p1,g2);
			GamePlayer gp4= new GamePlayer(date2,p2,g2);
			GamePlayer gp5= new GamePlayer(date3,p2,g3);
			GamePlayer gp6= new GamePlayer(date3,p4,g3);
			GamePlayer gp7= new GamePlayer(date4,p2,g4);
			GamePlayer gp8= new GamePlayer(date4,p1,g4);
			GamePlayer gp9= new GamePlayer(date5,p4,g5);
			GamePlayer gp10= new GamePlayer(date5,p1,g5);
			GamePlayer gp11= new GamePlayer(date6,p3,g6);
			GamePlayer gp12= new GamePlayer(date7,p4,g7);
			GamePlayer gp13= new GamePlayer(date8,p1,g8);
			GamePlayer gp14= new GamePlayer(date8,p4,g8);

			gamePlayerRepository.saveAll(Arrays.asList(gp1,gp2,gp3,gp4,gp5,gp6,gp7,gp8,gp9,gp10,gp11,gp12,gp13,gp14));
		};
	}
}