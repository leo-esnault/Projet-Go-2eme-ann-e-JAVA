package Players;

import java.util.Random;

import go.ContenuCase;
import go.Go;
import ihm.IPlayer;
import ihm.PlayerTurn;

public class PlayerBot implements IPlayer {

	@Override
	public String getMove(Go g, PlayerTurn pt) {
		StringBuilder sb = new StringBuilder();
		sb.append("play ");
		Random random = new Random();
		sb.append(pt.toString() + " ");
		int i = 0;

        // Sélectionnez une case au hasard jusqu'à ce qu'une case valide soit trouvée
        while (true) {
        	++i;
            char lettre = (char) ('A' + random.nextInt(19));
            int chiffre = random.nextInt(19) + 1;
            
            if (isValidMove(g, pt, lettre, chiffre)) {
                sb.append(lettre + Integer.toString(chiffre));
                System.out.println(sb.toString());
                return sb.toString();
            }
            if (i>100) {
            	sb.setLength(0);
            	sb.append("skip");
            	return sb.toString();
            }
        }
        
	}
	
	 // Méthode pour vérifier si un coup est valide
    private boolean isValidMove(Go g, PlayerTurn pt, char lettre, int chiffre) {
        int row = chiffre - 1;
        int col = lettre - 'A';

        // Vérifiez les limites du plateau
        if (row < 0 || row >= g.getTaille() || col < 0 || col >= g.getTaille()) {
            return false;
        }

        // Vérifiez si la case est déjà occupée
        if (g.getPlateau()[row][col] != ContenuCase.EMPTY) {
            return false;
        }

        // Simulez le coup pour vérifier s'il s'agit d'un suicide
        g.getPlateau()[row][col] = pt.currentPlayer();
        boolean isSuicide = g.isSuicide(pt.currentPlayer(), row, col);
        g.getPlateau()[row][col] = ContenuCase.EMPTY; // Annuler la simulation

        return !isSuicide; // Retourne true si ce n'est pas un suicide
    }

}
