package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.Models.Game;
import com.codeoftheweb.salvo.Models.GamePlayer;
import com.codeoftheweb.salvo.Models.Player;
import com.codeoftheweb.salvo.Models.Ship;
import com.codeoftheweb.salvo.Repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.Repositories.GameRepository;
import com.codeoftheweb.salvo.Repositories.PlayerRepository;
import com.codeoftheweb.salvo.Repositories.ShipRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import java.util.*;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class);
	}


	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository) {
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
/*
			Set<String> shipLoc1 = new HashSet<>();
			shipLoc1.add("H2");
			shipLoc1.add("H3");
			shipLoc1.add("H4");
			Ship s1= new Ship("Destroyer",gp1,shipLoc1);
			Set<String> shipLoc2 = new HashSet<>("E1", "F1", "G1");
			Ship s2= new Ship("Submarine",gp1,shipLoc2);
			Set<String> shipLoc3 = new HashSet<>("B4","B5"));
			Ship s3= new Ship("Patrol Boat",gp1,shipLoc3); */

			Ship s1 = new Ship(gp1, "Destroyer", new HashSet<>(Arrays.asList("H2", "H3", "H4")));
			Ship s2 = new Ship(gp1, "Submarine", new HashSet<>(Arrays.asList("E1", "F1", "G1")));
			Ship s3 = new Ship(gp1, "Patrol Boat", new HashSet<>(Arrays.asList("B4", "B5")));
			Ship s4 = new Ship(gp2, "Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")));
			Ship s5 = new Ship(gp2, "Patrol Boat", new HashSet<>(Arrays.asList("F1", "F2")));
			Ship s6 = new Ship(gp3, "Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")));
			Ship s7 = new Ship(gp3, "Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")));
			Ship s8 = new Ship(gp4, "Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")));
			Ship s9 = new Ship(gp4, "Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")));
			Ship s10 = new Ship(gp5, "Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")));
			Ship s11 = new Ship(gp5, "Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")));
			Ship s12 = new Ship(gp6, "Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")));
			Ship s13 = new Ship(gp6, "Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")));
			Ship s14 = new Ship(gp7, "Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")));
			Ship s15 = new Ship(gp7, "Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")));
			Ship s16 = new Ship(gp8, "Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")));
			Ship s17 = new Ship(gp8, "Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")));
			Ship s18 = new Ship(gp9, "Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")));
			Ship s19 = new Ship(gp9, "Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")));
			Ship s20 = new Ship(gp10, "Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")));
			Ship s21 = new Ship(gp10, "Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")));
			Ship s22 = new Ship(gp11, "Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")));
			Ship s23 = new Ship(gp11, "Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")));
			Ship s24 = new Ship(gp13, "Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")));
			Ship s25 = new Ship(gp13, "Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")));
			Ship s26 = new Ship(gp14, "Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")));
			Ship s27 = new Ship(gp14, "Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")));

			shipRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8,
					s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21,
					s22, s23, s24, s25, s26, s27));

		};
	}
}