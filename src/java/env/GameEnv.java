package env;
// Environment code for project sa1415_ilTesoroDeiPirati

import impl.PlayerJason;
import jason.asSyntax.*;
import jason.environment.*;

import java.util.logging.*;

import utils.Utils;

public class GameEnv extends Environment {
	
	public static final Literal sp = Literal.parseLiteral("selectRandom(missingCards)");
	public static final Literal cm = Literal.parseLiteral("continueMatch(N)");
	public static final Literal cp = Literal.parseLiteral("controlPlayer(nameAgent)");
	
	public static int currentTurn = 1;
	
    private Logger logger = Logger.getLogger("sa1415_ilTesoroDeiPirati."+GameEnv.class.getName());
    
    GameModel gameModel;
   
    
    @Override
    public void init(String[] args) {
    	logger.info("GameEnv inizializzato");
    	initPercept();
    }
    
    public void initPercept(){
    	clearPercepts();
    	gameModel = new GameModel(); //inizializza il gioco
    	setupOtherPlayer();
    	updateTrust();
    	updateBluff();
    	updateCardsPercept();
    	updateMissingCardsPercept();
    	updateTurnPercept();
    	}
    
    public void updatePercept(){
    	clearPercepts(gameModel.p1.getName()); 
    	clearPercepts(gameModel.p2.getName());
    	updateTrust();
    	updateBluff();
    	setupOtherPlayer();
    	updateCardsPercept();
    	updateMissingCardsPercept();
    	updateTurnPercept();
    }
    
    public void setupOtherPlayer(){
    	for(int i= 1; i < 3; i++){
    		PlayerJason p = gameModel.getPlayer("p"+i);
    		String litString = "otherPlayer("+p.getOtherPlayer().getName()+")";
    		Literal otherPlayerLit = Literal.parseLiteral(litString);
    		addPercept(p.getName(),otherPlayerLit);
    	}
    }
    
    public void updateCardsPercept(){
    	for(int i= 1; i < 3; i++){
    		PlayerJason p = gameModel.getPlayer("p"+i);
    		addPercept(p.getName(),p.getCardsLiteral());
    	}
    }
    
    public void updateMissingCardsPercept(){
    	for(int i= 1; i < 3; i++){
    		PlayerJason p = gameModel.getPlayer("p"+i);
    		addPercept(p.getName(),p.getMissingCardsLiteral());
    	}
    }
    
    public void updateTurnPercept(){
    	for(int i= 1; i < 3; i++){
    		PlayerJason p = gameModel.getPlayer("p"+i);
    		addPercept(p.getName(),p.getTurnLiteral());
    	}
    }
    
    public void updateTrust(){
    	for(int i= 1; i < 3; i++){
    		PlayerJason p = gameModel.getPlayer("p"+i);
    		String otherName = p.getOtherPlayer().getName();
    		int trust = Utils.searchTrust(p.getName(), otherName);
    		p.setTrust(trust);
    		Literal trustPlayer = Literal.parseLiteral("trustOtherPlayer("+trust+")");
    		addPercept(p.getName(), trustPlayer);
    	}
    }
    
    public void updateBluff(){
    	for(int i= 1; i < 3; i++){
    		PlayerJason p = gameModel.getPlayer("p"+i);
    		Literal bluffPlayer = Literal.parseLiteral("bluff("+p.getBluff()+")");
    		addPercept(p.getName(), bluffPlayer);
    	}
    }
    
    public void finishPercept(){
    	System.out.println("Partita terminata");
    	for(int i= 1; i < 3; i++){
    		PlayerJason p = gameModel.getPlayer("p"+i);
    		addPercept(p.getName(),Literal.parseLiteral("gameEnded"));
    	}
    	gameModel.finishGame();
    }
    
    @Override
    public boolean executeAction(String agName, Structure action) {
    	boolean result = false;
    	String functor = action.getFunctor();
    	if (action.equals(sp)) {
    		gameModel.selectRandom(agName);
    	    result = true;
    	    updatePercept();
    	}
    	if (action.equals(cp)) {
    		gameModel.controlPlayer(agName);
    	    result = true;
    	}
    	if (action.equals(cm)) {
    		if(currentTurn < 8){
    	    	updatePercept();
    	    	gameModel.continueMatch(agName, currentTurn);
        		currentTurn++;   
        		result = true;
    	    }else{
    	    	finishPercept();
    	    	result = true;
    	    }
	    }
    	
    	return result;
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
}
