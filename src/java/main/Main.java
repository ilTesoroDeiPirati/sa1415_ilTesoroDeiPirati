package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import utils.Utils;
import impl.Card;
import impl.Grid;
import impl.Pirate;
import impl.PlayerAgent;
import impl.Position;
import impl.Treasure;
import interfaces.ICard;

/**
 * @author Alessia Papini
 */

public class Main {
	
	public static PlayerAgent p1, p2;
	public static Treasure treasure;
	public static Grid grid;
	public static int currentTurn = 0;
	public static Position treasurePosition;
	
	public static void main(String[] args) {
		ArrayList<String> number = new ArrayList<>(Arrays.asList("1","2", "3", "4", "5", "6", "7", "8"));
		ArrayList<String> alpha = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H"));
		ArrayList<Card> cardInit = new ArrayList<Card>();
		ArrayList<Card> p1Cards = new ArrayList<Card>();
		ArrayList<Card> p2Cards = new ArrayList<Card>();
		
		//Definisco il tesoro
		int nNumber = new Random().nextInt(number.size());
		int randomTreasureNumber= Integer.parseInt(number.get(nNumber));
		int nAlpha = new Random().nextInt(number.size());
		int randomTreasureAlpha= Utils.convertAlpha(alpha.get(nAlpha));
		treasurePosition = new Position(randomTreasureAlpha, randomTreasureNumber);
		treasure = new Treasure(treasurePosition);
		System.out.println("Il tesoro è in: " + randomTreasureAlpha + " " + randomTreasureNumber);
		number.remove(nNumber);
		alpha.remove(nAlpha);
		
		for(int i = 0; i < number.size(); i++){
			cardInit.add(i, new Card("number", number.get(i)));
		}
		for(int i = 0; i < alpha.size(); i++){
			cardInit.add(i, new Card("alpha", alpha.get(i)));
		}
		
		grid = new Grid(8, 8);
		System.out.println("Griglia inizializzata");
			
		//Sorteggio i tasselli per i due giocatori
		for(int i = 0; i < 8; i++){
			int n = new Random().nextInt(cardInit.size());
			p1Cards.add(cardInit.get(n));
			cardInit.remove(n);
		}
		for(int i = 0; i < cardInit.size(); i++){
			int n = new Random().nextInt(cardInit.size());
			p2Cards.add(cardInit.get(n));
			cardInit.remove(n);
		}
		
		//Sorteggio i turni
		//TODO
		String[] turn = {"p1", "p1", "p2", "p1", "p2", "p2", "p2", "p1"}; 
		
		initPlayers(p1Cards, p2Cards, turn);
		
		if(turn[0].equals("p1")){
			p1.startTurn();
		}else{
			p2.startTurn();
		}
	}	
	
	private static void initPlayers(ArrayList<Card> p1Cards, ArrayList<Card> p2Cards, String[] turn){
		p1 = new PlayerAgent("p1", "red");
		p2 = new PlayerAgent("p2", "blue");
		
		p1.setCards(p1Cards);
		p2.setCards(p2Cards);
		
		p1Cards.add(new Card("alpha", "A"));
		p1Cards.add(new Card("number", "1"));
		p2Cards.add(new Card("alpha", "A"));
		p2Cards.add(new Card("number", "1"));
		
		p1.setOtherPlayer(p2);
		p2.setOtherPlayer(p1);		
		
		p1.setMissingCards(p2Cards);
		p2.setMissingCards(p1Cards);
		
		p1.setTurn(turn);
		p2.setTurn(turn);
	}
	
	public static void finishGame(){
		if(!grid.isFree(treasurePosition)){
			String winnerAgent = grid.getPirate(treasurePosition).getPlayerName();
			System.out.println("Il vincitore è: " + winnerAgent);
		}else{
			System.out.println("Il tesoro non è stato trovato!!");
		}
	}
}
