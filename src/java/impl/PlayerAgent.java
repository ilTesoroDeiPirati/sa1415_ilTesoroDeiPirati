package impl;

import java.util.ArrayList;
import java.util.Random;

import main.Main;
import utils.Utils;
import interfaces.ICard;
import interfaces.IPlayerAgent;

public class PlayerAgent implements IPlayerAgent {
	
	private ArrayList<Card> cards, missingCards;
	private String name, color;
	private String[] turn;
	private Grid grid;
	private PlayerAgent otherPlayer;
	
	public PlayerAgent(String name, String color){
		this.name = name;
		this.color = color;
		this.grid = Main.grid;
	}
	
	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public void setTurn(String[] turn){
		this.turn = turn;
	}
	
	public void setOtherPlayer(PlayerAgent otherPlayer){
		this.otherPlayer = otherPlayer;
	}
	
	public void startTurn() {
		System.out.println("Gioca il player " + this.getName() + " Turno: " + Main.currentTurn);
		deliberate();
	}

	public void deliberate() {
		boolean search = false;
		int i;
		int n = new Random().nextInt(missingCards.size());
		ICard randomCard = missingCards.get(n);
		if (randomCard.getType()=="alpha"){
			//cerco un tassello numero tra i tasselli mancanti
			i = 0;
			while(!search || i > missingCards.size()){
				if(missingCards.get(i).getType() == "number"){
					search = true;
				}
				i++;
			}
			positionPirate(randomCard, missingCards.get(i-1));
		}else{
			//cerco un tassello lettera tra i tasselli mandanti
			i = 0;
			while(!search || i > missingCards.size()){
				if(missingCards.get(i).getType() == "alpha"){
					search = true;
				}
				i++;
			}
			positionPirate(missingCards.get(i-1), randomCard);
		}
	}

	public void positionPirate(ICard x, ICard y) {
		int alpha = Utils.convertAlpha(x.getValue());
		int number = Integer.parseInt(y.getValue());
		grid.setPirate(new Position(alpha, number), new Pirate(this.getName(), new Position(alpha, number)));
		System.out.println("Player: " + getName() + 
				" Posizionamento pirata in: " + alpha + " " + number);
		endTurn();
	}

	public void endTurn() {
		if(Main.currentTurn == turn.length-1){
			System.out.println("Posizionamento terminato!!");
			Main.finishGame();
		}else{
			Main.currentTurn = Main.currentTurn + 1;
			if(turn[Main.currentTurn].equals(this.getName())){
				System.out.println("Turno corrente " + Main.currentTurn);
				this.startTurn();
			}else{
				System.out.println("Turno corrente " + Main.currentTurn);
				otherPlayer.startTurn();
			}
		}
	}

	public void setMissingCards(ArrayList<Card> missingCards) {
		this.missingCards = missingCards;
	}



}
