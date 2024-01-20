package ihm;

import java.util.Scanner;

import Players.PlayerBot;
import Players.PlayerHuman;
import go.ContenuCase;
import go.Go;

public class Ihm {

	private static Go go = new Go();
	private static PlayerTurn quiJoue = new PlayerTurn();
	private static IPlayer playerB = new PlayerHuman();
	private static IPlayer playerW = new PlayerHuman();

	public static void main(String[] args) {
		while (true) {
			IPlayer player = new PlayerHuman();
			if(quiJoue.currentPlayer() == ContenuCase.BLACK) {
				player = playerB;
			}
			else
				player = playerW;
			
			String user_input = player.getMove(go, quiJoue);
			Scanner sc_input = new Scanner(user_input);

			if (sc_input.hasNext()) {
				String id = "";

				if (sc_input.hasNextInt()) {
					id = sc_input.next();
				}

				String action = sc_input.next();
				choixAction(id, action, sc_input);

			} else {
				System.out.println("? Unknown command");
			}

			sc_input.close();
		}
	}

	private static void choixAction(String id, String action, Scanner sc_input) {
		switch (action) {
		case "quit":
			quit(id);
			break;

		case "boardsize":
			boardSize(id, sc_input);
			break;

		case "showboard":
			showboard(id);
			break;

		case "play":
			play(id, sc_input);
			break;

		case "clear_board":
			clear_board(id);
			break;
		case "player":
			player(id, sc_input);
			break;
		case "skip":
			skip(id);
			break;

		default:
			System.out.println("?" + id + " Unknown command");
		}
	}

	private static void skip(String id) {
		System.out.println("=" + id);
		go.skip();
		quiJoue.switchPlayer();
	}

	private static void player(String id, Scanner sc_input) {
		try {
			if (!sc_input.hasNext()) {
				throw new IllegalArgumentException("Invalid color or coordinate");
			}
			String couleur = sc_input.next();
			if (!couleur.equalsIgnoreCase("BLACK")||!couleur.equalsIgnoreCase("WHITE"))
			if (!sc_input.hasNext()) {
				throw new IllegalArgumentException("Invalid color or coordinate");
			}
			String parametre = sc_input.next();
			if(parametre.equalsIgnoreCase("CONSOLE")) {
				if(couleur.equalsIgnoreCase("BLACK")) {
					playerB = new PlayerHuman();
				}
				else
					playerW = new PlayerHuman();
			}
			else if (parametre.equalsIgnoreCase("RANDOM")) {
				if(couleur.equalsIgnoreCase("BLACK")) {
					playerB = new PlayerBot();
				}
				else
					playerW = new PlayerBot();
			}
			else 
				throw new IllegalArgumentException("Unknown player kind");
			System.out.println("=" + id);
		} catch (IllegalArgumentException | IllegalStateException e) {
			System.out.println("?" + id + " " + e.getMessage());
		}
	}

	private static void quit(String id) {
		System.out.println("=" + id);
		go.quit();
		System.exit(0);
	}

	private static void boardSize(String id, Scanner sc_input) {
		if (sc_input.hasNextInt()) {
			int size = sc_input.nextInt();
			if (size >= 6 && size <= 19) {
				go.boardSize(size);
				System.out.println("=" + id);
			} else {
				System.out.println("?" + id + " Unacceptable size");
			}
		} else {
			System.out.println("?" + id + " Boardsize not an integer");
		}
	}

	private static void showboard(String id) {
		System.out.println("=" + id);
		System.out.println(go.showboard());
	}

	private static void play(String id, Scanner sc_input) {
		try {
			if (!sc_input.hasNext()) {
				throw new IllegalArgumentException("Invalid color or coordinate");
			}
			String couleur = sc_input.next();
			if(quiJoue.currentPlayer() == ContenuCase.BLACK && couleur.equalsIgnoreCase("WHITE"))
				throw new IllegalStateException("Illegal move");
			if(quiJoue.currentPlayer() == ContenuCase.WHITE && couleur.equalsIgnoreCase("BLACK"))
				throw new IllegalStateException("Illegal move");
			
			if (!sc_input.hasNext()) {
				throw new IllegalArgumentException("Invalid color or coordinate");
			}
			go.play(couleur, sc_input.next());
			System.out.println("=" + id);
			quiJoue.switchPlayer();
		} catch (IllegalArgumentException | IllegalStateException e) {
			System.out.println("?" + id + " " + e.getMessage());
		}
	}

	private static void clear_board(String id) {
		System.out.println("=" + id);
		go.clear_board();
	}
}
