// Agent basicAgent in project sa1415_ilTesoroDeiPirati

/* Initial beliefs and rules */

//bluff(). indice di bluff proprio dell'agente
//trust(). indice di fiducia nei confronti dell'avversario
//cardsOnHand(). tasselli in possesso dell'agente
//missingCards(). tasselli mancanti
//otherPlayer(). riferimento all'altro giocatore
//turn(). informazione sul turno corrente

canStart :- .my_name(MyName) & turn(Player) & (MyName == Player).

/* Initial goals */

!startGame.

/* Plans */

+!startGame : canStart & not gameEnded <- -canStart; 
						                  !movePirate. 				
						  		  						  
+!startGame : not canStart & not gameEnded <- true.


+!movePirate : true <- !selectPosition.
						
+!selectPosition : true <- selectRandom(missingCards);
							!endTurn.

+!savePlayer : gameEnded <- controlPlayer(nameAgent).
														
+!updateTurn : not gameEnded <-  continueMatch(N);
						         ?otherPlayer(Player);
						         .send(Player,achieve,startGame);
						         !startGame.
						         
+gameEnded : gameEnded <- !savePlayer.						         
						
+!endTurn : true <- !updateTurn.

/* Plans failure handling*/

-!updateTurn : true.	

-!startGame : true.

					
					