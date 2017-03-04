import java.util.ArrayList;
import java.math.*;

/****************************************************************
 * studPlayer.java
 * Implements MiniMax search with A-B pruning and iterative deepening search (IDS). The static board
 * evaluator (SBE) function is simple: the # of stones in studPlayer's
 * mancala minus the # in opponent's mancala.
 * -----------------------------------------------------------------------------------------------------------------
 * Licensing Information: You are free to use or extend these projects for educational purposes provided that
 * (1) you do not distribute or publish solutions, (2) you retain the notice, and (3) you provide clear attribution to UW-Madison
 *
 * Attribute Information: The Mancala Game was developed at UW-Madison.
 *
 * The initial project was developed by Chuck Dyer(dyer@cs.wisc.edu) and his TAs.
 *
 * Current Version with GUI was developed by Fengan Li(fengan@cs.wisc.edu).
 * Some GUI componets are from Mancala Project in Google code.
 */




//################################################################
// studPlayer class
//################################################################

public class testPlayer extends Player {

	int value;
	
    /*Use IDS search to find the best move. The step starts from 1 and keeps incrementing by step 1 until
	 * interrupted by the time limit. The best move found in each step should be stored in the
	 * protected variable move of class Player.
     */
    public void move(GameState state){
    	
    	int maxDepth = 1;
    	
		GameState copy = new GameState(state);
    	
    	while(true){    		
    		this.move = maxAction(copy, maxDepth);
    		
    		maxDepth++; // increase depth by 1 each time
    	}
    }

    // Return best move for max player. Note that this is a wrapper function created for ease to use.
	// In this function, you may do one step of search. Thus you can decide the best move by comparing the 
	// sbe values returned by maxSBE. This function should call minAction with 5 parameters.
    public int maxAction(GameState state, int maxDepth){
        
    	
    	ArrayList<Integer> moves = legalMoves(state); 
    	int choose = moves.get(0); // the number of the bin of the chosen move
    	int bestSbe = Integer.MIN_VALUE; // keeps track of the the highest sbe
    	int currSbe = 0;
    	
    	int alpha = Integer.MIN_VALUE;
    	int beta = Integer.MAX_VALUE;
    	    	
    	value = Integer.MIN_VALUE;
    	
    	for(Integer i : moves){
    		        	
    		GameState copyState = new GameState(state); // copy to avoid messing up the original state
        	    		
        	boolean moveAgain = copyState.applyMove(i);
        	
        	if(moveAgain){
            	value = Math.max(value, maxAction(copyState, 1, maxDepth, alpha, beta));
        	}else{
        		copyState.rotate();
        		value = Math.max(value, minAction(copyState, 1, maxDepth, alpha, beta));
        	}
        	
        	currSbe = value;
    	        	
        	if(bestSbe < currSbe){
        		bestSbe = currSbe;
        		choose = i;
        	}        		
    	}
    	
    	System.out.println(choose);
    	return choose;
    }
    
	//return sbe value related to the best move for max player
    public int maxAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta){
    	    	
    	if(currentDepth == maxDepth){
    		return sbe(state);
    	}
    	value = Integer.MIN_VALUE;
    	
    	ArrayList<Integer> moves = legalMoves(state);
    	
    	for(Integer i : moves){
    		
        	GameState copyState = new GameState(state);
    		    		
    		boolean moveAgain = copyState.applyMove(i);
    		
    		if(moveAgain){
    			value = Math.max(value, maxAction(copyState, currentDepth + 1, maxDepth, alpha, beta));
    		}else{
    			copyState.rotate();
        		value = Math.max(value, minAction(copyState, currentDepth + 1, maxDepth, alpha, beta ));
    		}
    		
    		if(value >= beta){
    			return value;
    		}
    		alpha = Math.max(alpha, value);
    	}
    	
    	return value;
    	
    }
    //return sbe value related to the bset move for min player
    public int minAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta){
    	
    	
    	if(currentDepth == maxDepth){
    		return -sbe(state);
    	}
    	value = Integer.MAX_VALUE;
    	
    	ArrayList<Integer> moves = legalMoves(state);
    	
    	for(Integer i : moves){
    		
        	GameState copyState = new GameState(state);  
        	
    		boolean moveAgain = copyState.applyMove(i);
    		
    		if(moveAgain){
    			value = Math.min(value, minAction(copyState, currentDepth + 1, maxDepth, alpha, beta));
    		}else{
    			copyState.rotate();
        		value = Math.min(value, maxAction(copyState, currentDepth + 1, maxDepth, alpha, beta));
    		}
    		
    		if(value <= alpha){
    			return value;
    		}
    		beta = Math.min(beta, value);
    	}
    	return value;
    }

    //the sbe function for game state. Note that in the game state, the bins for current player are always in the bottom row.
    private int sbe(GameState state){
    	    	    	
    	return state.stoneCount(6) - state.stoneCount(13);
    }
    
    private ArrayList<Integer> legalMoves(GameState state){
    	    	
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	
    	for(int i = 0; i < 6; i++){
    		if(!state.illegalMove(i)){
    			list.add(i);
    		}
    	}
    	
    	return list;
    }
    
    /**private int distanceToMancala(GameState state, int bin){
    	
    	//int distance = 0;
    	int mancala = state.mancalaOf(bin);
    	if(bin < 6){
    		distance = mancala - bin;
    	}
    	if(bin > 6){
    		distance = mancala - bin;
    	}
    	
		//distance = mancala - bin;

    	return mancala - bin;
    }*/
    
    // returns true if player has more shots at playing again
    private boolean goAgain(GameState state){
    	
    	
    	// collective goAgain
    	/**int count1 = 0;
    	int count2 = 0;
    	
    	for(int i = 0; i < 6; i++){
    		int stones = state.stoneCount(i);
    		if((6 - i) == stones){
    			count1++;
    		}
    	}
    	for(int i = 7; i < 13; i++){
    		int stones = state.stoneCount(i);
    		if((13 - i) == stones){
    			count2++;
    		}
    	}
    	
    	if(count1 > count2){
    		return 5;
    	}else if(count1 > 0){
    		return 3;
    	}else{
    		return -1;
    	}*/
    	
    	// singular goAgain
    	
    	boolean goAgain = false;
    	
    	for(int i = 0; i < 6; i++){
    		int stones = state.stoneCount(i);
    		if((6 - i) == stones){
    			goAgain = true;
    		}
    	}
    	
    	return goAgain;
    }
    
    private boolean manyStones(GameState state){
    	
    	ArrayList<Integer> list = legalMoves(state);
    	boolean manyStones = false;
    	
    	for(Integer i : list){
    		if(state.stoneCount(i) > 6){
    			manyStones = true;
    		}
    	}

    	return manyStones;
    }
    
    // returns true if player has more stones on their side
    private boolean higherStoneCount(GameState state){
    	
    	int playerStones = 0;
    	int opponentStones = 0;
    	
    	for(int i = 0; i < 6; i++){
    		if(state.stoneCount(i) != 0){
    			playerStones += state.stoneCount(i);
    		}
    	}
    	for(int i = 7; i < 13; i++){
    		if(state.stoneCount(i) == 0){
    			opponentStones += state.stoneCount(i);
    		}
    	}
    	
    	return playerStones > opponentStones;
    }
    
    // returns true if the game is at the halfway point
    private boolean halfway(GameState state){
    	
    	int stones = 0;
    	
    	for(int i = 0; i < 13; i++){
    		stones += state.stoneCount(i);
    	}
    	
    	stones -= state.stoneCount(6);
    	
    	return stones < 24;
    }
   
}

