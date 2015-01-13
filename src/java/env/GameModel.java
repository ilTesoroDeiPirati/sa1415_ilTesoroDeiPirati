package env;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import utils.Utils;
import impl.Card;
import impl.Grid;
import impl.Pirate;
import impl.PlayerJason;
import impl.Position;
import impl.Treasure;
import interfaces.ICard;
import interfaces.IPirate;

/**
 * @author Alessia Papini
 */

public class GameModel {

	public static PlayerJason p1, p2;
	public static Treasure treasure;
	public static Grid grid;
	public static int currentTurn = 0;
	public static Position treasurePosition;
	public static String numberSelect, alphaSelect;
	public static String[] turn = new String[8];

	public GameModel(){
		System.out.println("GameModel inizializzato");
		init();
	}
	
	public void init(){
		ArrayList<String> number = new ArrayList<>(Arrays.asList("1","2", "3", "4", "5", "6", "7", "8"));
		ArrayList<String> alpha = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H"));
		ArrayList<Card> cardInit = new ArrayList<Card>();
		ArrayList<Card> p1Cards = new ArrayList<Card>();
		ArrayList<Card> p2Cards = new ArrayList<Card>();
		
		//Definisco il tesoro
		int nNumber = new Random().nextInt(number.size());
		numberSelect = number.get(nNumber);
		int randomTreasureNumber= Integer.parseInt(numberSelect);
		int nAlpha = new Random().nextInt(alpha.size());
		alphaSelect = alpha.get(nAlpha);
		int randomTreasureAlpha= Utils.convertAlpha(alphaSelect);
		treasurePosition = new Position(randomTreasureAlpha, randomTreasureNumber);
		treasure = new Treasure(treasurePosition);
		System.out.println("Il tesoro è in: " + alphaSelect + " " + numberSelect);
		number.remove(numberSelect);
		alpha.remove(alphaSelect);
		
		//Creo le carte
		for(int i = 0; i < number.size(); i++){
			cardInit.add(i, new Card("number", number.get(i)));
		}
		for(int i = 0; i < alpha.size(); i++){
			cardInit.add(i, new Card("alpha", alpha.get(i)));
		}
		
		grid = new Grid(8, 8);
		System.out.println("Griglia inizializzata");
			
		//Sorteggio i tasselli per i due giocatori
		for(int i = 0; i < 7; i++){
			int n = new Random().nextInt(cardInit.size());
			p1Cards.add(cardInit.get(n));
			cardInit.remove(n);
		}
		for(int i = 0; i < 7; i++){
			int n = new Random().nextInt(cardInit.size());
			p2Cards.add(cardInit.get(n));
			cardInit.remove(n);
		}
		
		p1 = new PlayerJason("p1",0);
		p2 = new PlayerJason("p2",50);
		
		//Sorteggio i turni
		int[] player = {1,2};
		String playerSelect = "";
		String stamp = "Turni: ";
		int player1 = 4;
		int player2 = 4;
		for(int i = 0; i < turn.length; i++){
			int n = new Random().nextInt(player.length);
			if (n == 0){
				if(player1 == 0){
					player2--;
					turn[i] = p2.getName();
				}else{
				player1--;
				turn[i] = p1.getName();
				}
			}else if (n == 1){
				if(player2 == 0){
					player1--;
					turn[i] = p1.getName();
				}else{
				player2--;
				turn[i] = p2.getName();
				}
			}
			stamp = stamp+ " " +turn[i];
		}
		System.out.println(stamp);
		initPlayers(p1Cards, p2Cards, turn);

	}
	
	private void initPlayers(ArrayList<Card> p1Cards, ArrayList<Card> p2Cards, String[] turn){
				
		p1.setCards(p1Cards);
		p2.setCards(p2Cards);
		String cardsOnHand = "";
		for(int i = 0; i < p1Cards.size(); i++){
			cardsOnHand = cardsOnHand + " " + p1Cards.get(i).getValue();
		}
		System.out.println("Tasselli di " +p1.getName() + ": " + cardsOnHand);
		cardsOnHand = "";
		for(int i = 0; i < p2Cards.size(); i++){
			cardsOnHand = cardsOnHand + " " + p2Cards.get(i).getValue();
		}
		System.out.println("Tasselli di " +p2.getName() + ": " + cardsOnHand);
		
		ArrayList<Card> p1MissingCards = (ArrayList<Card>) p1Cards.clone();
		ArrayList<Card> p2MissingCards = (ArrayList<Card>) p2Cards.clone();
		ArrayList<Card> combP1Cards = new ArrayList<Card>();
		ArrayList<Card> combP2Cards = new ArrayList<Card>();
		
		p1MissingCards.add(new Card("alpha", alphaSelect));
		p1MissingCards.add(new Card("number", numberSelect));
		p2MissingCards.add(new Card("alpha", alphaSelect));
		p2MissingCards.add(new Card("number", numberSelect));
		
		combP1Cards=generateCombinations(p1MissingCards);
		combP2Cards=generateCombinations(p2MissingCards);
		
		p1.setOtherPlayer(p2);
		p2.setOtherPlayer(p1);		
		
		p1.setMissingCards(combP2Cards);
		p2.setMissingCards(combP1Cards);
		
		p1.setTurn(turn[0]);
		p2.setTurn(turn[0]);
	}
	
	public ArrayList<Card> generateCombinations(ArrayList<Card> missingCards){
		
		ArrayList<Card> alpha = new ArrayList<Card>();
		ArrayList<Card> number = new ArrayList<Card>();
		ArrayList<Card> combCards = new ArrayList<Card>();
		
		for(int i=0; i < missingCards.size(); i++){
			if(missingCards.get(i).getType().equals("alpha")){
				alpha.add(missingCards.get(i));
			}else{
				number.add(missingCards.get(i));
			}
		}
		
		for(int i=0;i < alpha.size();i++){
			for(int j=0; j < number.size();j++){
				String value = alpha.get(i).getValue()+"-"+number.get(j).getValue();
				combCards.add(new Card("split",value));
			}
		}
		return combCards;
	}
	
	public boolean continueMatch(String name, int currentTurn){
		p1.setTurn(turn[currentTurn]);
		p2.setTurn(turn[currentTurn]);
		return true;
	}
	
	public boolean selectRandom(String name){
		
		//Il giocatore intende bluffare o meno
		boolean found = false;
		Card selectCard = null;
		ArrayList<Card> missingCards = (ArrayList<Card>) getPlayer(name).getMissingCard().clone();
		ArrayList<Card> position = new ArrayList<Card>();
		//se esiste una combinazione con "confidence>=10" 
		//scegli quella, altrimenti random
		for(int i = 0; i < missingCards.size() && !found; i++){
			if(missingCards.get(i).getConfidence()>=10){
				selectCard = missingCards.get(i); 
				found = true;
			}
		}
		//random
		if(!found){
			int n = new Random().nextInt(missingCards.size());
			selectCard = missingCards.get(n);
		}
		int bluff = getPlayer(name).getBluff();
		int number = (int) (Math.random()*100);
		String[] items = selectCard.getValue().split("-");
		if (number < bluff){
			//Mento
			String valueAlpha = "";
			String valueNumber = "";
			boolean search = false;
			for(int i = 0; i < getPlayer(name).getCards().size() && !search;i++){
				if(getPlayer(name).getCards().get(i).getType().equals("alpha")){
					valueAlpha = getPlayer(name).getCards().get(i).getValue();					
					if(grid.isFree(new Position(Utils.convertAlpha(valueAlpha), Integer.parseInt(items[1])))){
						selectCard = new Card("split",valueAlpha+"-"+items[1]);
						grid.setPirate(new Position(Utils.convertAlpha(valueAlpha), Integer.parseInt(items[1])), new Pirate(name, new Position(Utils.convertAlpha(valueAlpha), Integer.parseInt(items[1]))));
						search = true;
					}
				}
				if(getPlayer(name).getCards().get(i).getType().equals("number")){
					valueNumber = getPlayer(name).getCards().get(i).getValue();					
					if(grid.isFree(new Position(Utils.convertAlpha(items[0]), Integer.parseInt(valueNumber)))){
						selectCard = new Card("split",items[0]+"-"+valueNumber);
						grid.setPirate(new Position(Utils.convertAlpha(items[0]), Integer.parseInt(valueNumber)), new Pirate(name, new Position(Utils.convertAlpha(items[0]), Integer.parseInt(valueNumber))));
						search = true;
					}
				}
				
			}
			System.out.println("["+name+"] "+ "Ho bluffato e ho posizionato il mio pirata in " + selectCard.getValue());
		}else{
			//Non mento
			grid.setPirate(new Position(Utils.convertAlpha(items[0]), Integer.parseInt(items[1])), new Pirate(name, new Position(Utils.convertAlpha(items[0]), Integer.parseInt(items[1]))));
			missingCards.remove(selectCard);
			getPlayer(name).setMissingCards(missingCards);
			System.out.println("["+name+"] "+ "Non ho bluffato e ho posizionato il mio pirata in " + selectCard.getValue());
		}	
		
		//L'avversario decide di credere o meno
		int believe = (int) (Math.random()*100);
		int newConfidence = 0;
		if (believe > getPlayer(name).getOtherPlayer().getTrust()){
			//decido di non credere
			ArrayList<Card> missingCardsOtherP = (ArrayList<Card>) getPlayer(name).getOtherPlayer().getMissingCard().clone();
			missingCardsOtherP.remove(Utils.indexCards(missingCardsOtherP, selectCard));
			//
			getPlayer(name).getOtherPlayer().setMissingCards(missingCardsOtherP);
			System.out.println("["+getPlayer(name).getOtherPlayer().getName()+"] "+ "Non credo alla mossa del mio avversario");
		}else{
			//decido di credere
			ArrayList<Card> missingCardsOtherP = (ArrayList<Card>) getPlayer(name).getOtherPlayer().getMissingCard().clone();
			for(int i = 0; i < missingCardsOtherP.size();i++){
				String[] itemsCard = missingCardsOtherP.get(i).getValue().split("-");
				if(items[0].equals(itemsCard[0])){
					newConfidence = missingCardsOtherP.get(i).getConfidence()+10;
					missingCardsOtherP.get(i).setConfidence(newConfidence);
				}
				if(items[1].equals(itemsCard[1])){
				   newConfidence = missingCardsOtherP.get(i).getConfidence()+10;
				   missingCardsOtherP.get(i).setConfidence(newConfidence);
				}
			}
			missingCardsOtherP.remove(Utils.indexCards(missingCardsOtherP, selectCard));
			getPlayer(name).getOtherPlayer().setMissingCards(missingCardsOtherP);
			System.out.println("["+getPlayer(name).getOtherPlayer().getName()+"] "+ "Credo alla mossa del mio avversario");
		}
		return true;
	}
	
	public PlayerJason getPlayer(String player){
		if(player.equals(p1.getName())){
			return p1;
		}else{
			return p2;
		}
	}
	
	public void finishGame(){
		if(!grid.isFree(treasurePosition)){
			String winnerAgent = grid.getPirate(treasurePosition).getPlayerName();
			System.out.println("Il vincitore è: " + winnerAgent);
		}else{
			System.out.println("Il tesoro non è stato trovato!!");
		}
		//Controllo pirati
		/*List<IPirate> pirates = grid.allPiratePosition();
		for(int i = 0; i < pirates.size(); i++){
			System.out.println("player: " + pirates.get(i).getPlayerName()+ " x: "+pirates.get(i).getPosition().getX() + " y: " + pirates.get(i).getPosition().getY() +"\n");
		}*/
	}
	
	public boolean controlPlayer(String name){
		int newTrust = 100;
		ArrayList<Card> otherPlayerCards = getPlayer(name).getOtherPlayer().getCards();
		//Controllo posizione pirati
		List<IPirate> pirates = grid.allPiratePosition();
		for(int i = 0; i < pirates.size(); i++){
			if(pirates.get(i).getPlayerName()!=name){
				int x = pirates.get(i).getPosition().getX();
				int y = pirates.get(i).getPosition().getY();
				for(int j = 0; j < otherPlayerCards.size(); j++){
					if(otherPlayerCards.get(j).getType().equals("number")){
						if(y == Integer.parseInt(otherPlayerCards.get(j).getValue())){
							newTrust = newTrust - 25;
						}
					}else if(otherPlayerCards.get(j).getType().equals("alpha")){
						if(x == Utils.convertAlpha(otherPlayerCards.get(j).getValue())){
							newTrust = newTrust - 25;
						}
					}		
				}
			}
		}
		getPlayer(name).setTrust(newTrust);
		Utils.savePlayer(name, getPlayer(name).getOtherPlayer().getName(), getPlayer(name).getTrust());
		return true;
	}

}

