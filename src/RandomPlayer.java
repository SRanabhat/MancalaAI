import java.util.Random;

public class RandomPlayer extends Player {

	@Override
	public void move(GameState state) {
		// TODO Auto-generated method stub

		if(state.gameOver()){
			return;
		}

		Random rand = new Random();
		int randomNum = rand.nextInt(6);
		
		while(state.illegalMove(randomNum)){
			randomNum = rand.nextInt(6);
		}
		
		move = randomNum;
		
	}
}
