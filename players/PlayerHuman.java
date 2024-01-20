package Players;

import java.util.Scanner;

import go.Go;
import ihm.IPlayer;
import ihm.PlayerTurn;

public class PlayerHuman implements IPlayer {

	@Override
	public String getMove(Go g, PlayerTurn pt) {
		Scanner sc = new Scanner(System.in);
		String reponse = sc.nextLine();
		return reponse;
	}

}
