package ihm;

import go.ContenuCase;

public class PlayerTurn {
	private ContenuCase couleur;
	
	public PlayerTurn() {
		couleur = ContenuCase.BLACK;
	}
	public void switchPlayer() {
		if(couleur == ContenuCase.BLACK)
			couleur = ContenuCase.WHITE;
		else if (couleur == ContenuCase.WHITE)
			couleur = ContenuCase.BLACK;
		else 
			throw new IllegalArgumentException("Invalid color");
	}
	public ContenuCase currentPlayer() {
		return couleur;
	}
	public String toString() {
		if(couleur == ContenuCase.BLACK)
			return "black";
		else 
			return "white";
	}
	
}
