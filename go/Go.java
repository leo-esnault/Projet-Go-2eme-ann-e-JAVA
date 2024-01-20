package go;

public class Go {

	private StringBuilder sb;
	private int taille;
	private final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int bScore;
	private int wScore;
	private ContenuCase[][] plateau;
	private boolean skipped;

	public Go() {
		this.sb = new StringBuilder();
		bScore = 0;
		wScore = 0;
		taille = 19;
		plateau = new ContenuCase[taille][taille];
		skipped = false;
		clear_board();
	}

	public void quit() {
		System.exit(0);
	}

	public String showboard() {
		sb.delete(0, sb.capacity());
		sb.append("   ");
        for (int i = taille; i > 0; --i) {
        	sb.append((char) ('A' + taille - i) + " ");
        }
        sb.append("\n");
        int startScore = (taille - 9 < 2) ? 2 : taille - 9;
        for (int i = taille; i > 0; --i) {
            if (i < 10) {
            	sb.append(" ");
            }
            sb.append((i) + " ");
            for (int j = 0; j < taille; j++) {
            	if(plateau[i-1][j] == ContenuCase.EMPTY)
            		sb.append('.');
            	else if (plateau[i-1][j] == ContenuCase.BLACK)
            		sb.append('X');
            	else
            		sb.append('O');
                sb.append(' ');
            }
            sb.append((i));  // Affiche les chiffres à droite
            if(i == startScore) {
				if(i<10)
					sb.append(" ");
				sb.append("    BLACK (X) has captured "+ bScore +" stones");
			}
			if(i == startScore - 1) {
				if(i<10)
					sb.append(" ");
				sb.append("    WHITE (O) has captured "+ wScore +" stones");
			}
			sb.append("\n");
        }

        // Affiche les lettres en bas
        sb.append("   ");
        for (int i = 0; i < taille; i++) {
            sb.append((char) ('A' + i) + " ");
        }
        sb.append("\n");
        return sb.toString();
	}

	public void boardSize(int taille_) {
		if (taille_ > 19 || taille_ < 6 ) {
			System.out.println("taille incorecte");
			return;
		}
		taille = taille_;
	}
	
	public void play(String couleur, String position) {
	    if (position.length() >= 2) {
	        char lettre = Character.toUpperCase(position.charAt(0));
	        String chiffreStr = position.substring(1);
	        int chiffre;
	        try {
	            chiffre = Integer.parseInt(chiffreStr) - 1;
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Invalid color or coordinate");
	        }

	        ContenuCase symbole;
	        if ("BLACK".equalsIgnoreCase(couleur)) {
	            symbole = ContenuCase.BLACK;
	        } else if ("WHITE".equalsIgnoreCase(couleur)) {
	            symbole = ContenuCase.WHITE;
	        } else {
	            throw new IllegalArgumentException("Invalid color or coordinate");
	        }

	        if (alphabet.indexOf(lettre) != -1 && chiffre >= 0 && chiffre < plateau.length) {
	            if (plateau[chiffre][alphabet.indexOf(lettre)] != ContenuCase.EMPTY) {
	                throw new IllegalStateException("Illegal move");
	            } else {
	                plateau[chiffre][alphabet.indexOf(lettre)] = symbole;

	                if (isSuicide(symbole, chiffre, alphabet.indexOf(lettre))) {
	                    // Annuler le mouvement si c'est un suicide
	                    plateau[chiffre][alphabet.indexOf(lettre)] = ContenuCase.EMPTY;
	                    throw new IllegalStateException("Illegal move");
	                }

	                captureOpponents(symbole, chiffre, alphabet.indexOf(lettre));
	                skipped = false;
	            }
	        } else {
	            throw new IllegalArgumentException("Invalid color or coordinate");
	        }
	    } else {
	        throw new IllegalArgumentException("Invalid color or coordinate");
	    }
	}

	private void captureOpponents(ContenuCase player, int row, int col) {
	    ContenuCase opponents = (player == ContenuCase.WHITE) ? ContenuCase.BLACK : ContenuCase.WHITE;  

	    int[][] neighbors = {{row-1, col}, {row+1, col}, {row, col-1}, {row, col+1}};

	    for (int[] neighbor : neighbors) {
	        int r = neighbor[0];
	        int c = neighbor[1];

	        if (r >= 0 && r < taille && c >= 0 && c < taille) {
	            if (plateau[r][c] == opponents) {
	                if (!hasLiberties(r, c)) {
	                    captureStones(opponents, r, c);
	                }
	            }
	        }
	    }
	}

	private boolean hasLiberties(int row, int col) {
		boolean[][] visited = new boolean[taille][taille];
		ContenuCase couleur = plateau[row][col];
	    return getLiberties(couleur, row, col, visited) != 0;
	}
	
	private int getLiberties(ContenuCase couleur, int row, int col, boolean[][] visited) {
	    if (row < 0 || row >= taille || col < 0 || col >= taille || visited[row][col]) {
	        return 0;
	    }
	    
	    visited[row][col] = true;
	    
	    if (plateau[row][col] == ContenuCase.EMPTY) {
	        return 1;
	    }
	    if (plateau[row][col] != couleur) {
	    	return 0;
	    }
	    
	    int liberties = 0;

	    // definit les cases voisine
	    int[][] neighbors = {{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}};

	    // Recursité 
	    for (int[] neighbor : neighbors) {
	        int r = neighbor[0];
	        int c = neighbor[1];

	        liberties += getLiberties(couleur, r, c, visited);
	    }

	    return liberties;
	}

	private void captureStones(ContenuCase player, int row, int col) {
	    plateau[row][col] = ContenuCase.EMPTY;
	    if(player.equals(ContenuCase.BLACK))
	    	++wScore;
	    else
	    	++bScore;

	    int[][] neighbors = {{row-1, col}, {row+1, col}, {row, col-1}, {row, col+1}};
	    for (int[] neighbor : neighbors) {
	        int r = neighbor[0];
	        int c = neighbor[1];

	        if (r >= 0 && r < taille && c >= 0 && c < taille && plateau[r][c] == player) {
	            captureStones(player, r, c);
	        }
	    }
	}

	public void clear_board() {
		for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                plateau[i][j] = ContenuCase.EMPTY;
            }
        }
	}

	public boolean isSuicide(ContenuCase player, int row, int col) {
	    // Vérifier si la pierre nouvellement placée n'a pas de liberté
	    if (!hasLiberties(row, col)) {
	        // Si elle capture des pierres ennemies, ce n'est pas un suicide
	        return !capturesEnemies(player, row, col);
	    }
	    return false; // La pierre a des libertés, ce n'est pas un suicide
	}

	private boolean capturesEnemies(ContenuCase player, int row, int col) {
	    ContenuCase opponents = (player == ContenuCase.WHITE) ? ContenuCase.BLACK : ContenuCase.WHITE;

	    int[][] neighbors = {{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}};

	    for (int[] neighbor : neighbors) {
	        int r = neighbor[0];
	        int c = neighbor[1];

	        if (r >= 0 && r < taille && c >= 0 && c < taille) {
	            if (plateau[r][c] == opponents && !hasLiberties(r, c)) {
	                // La pierre nouvellement placée capture des pierres ennemies
	                captureStones(opponents, r, c);
	                return true;
	            }
	        }
	    }
	    return false; 
	}

	public int getTaille() {
		return taille;
	}

	public Object[][] getPlateau() {
	    return plateau;
	}

	public void skip() {
		if(skipped == true) {
			quit();
		}
		else
			skipped = true;
	}
	
}
