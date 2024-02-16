package modele;
import java.lang.*;

public class Grille {

    private char[][] grille = new char[7][7];

    public Grille(){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                this.grille[i][j] = ' '; // on initialise la grille avec des espaces
            }
        }
    }

    public int getNbColonne(){
        return this.grille[0].length;
    }

    public char getCase(int row, int col){
        return this.grille[row][col];
    }

    public void setCase(int row, int col, char c){
        this.grille[row][col] = c;
    }

    public String toString(){
        StringBuilder s = new StringBuilder("---------------\n");
        for (int row = 0; row < this.grille.length; row++){
            s.append("|");
            for (int col = 0; col < this.grille[0].length; col++){
                s.append(this.getCase(row, col));
                s.append("|");
            }
            s.append("\n");
            s.append("---------------\n");
        }
        s.append(" 1 2 3 4 5 6 7\n");
        return s.toString();
    }

    public void clear() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                this.grille[i][j] = ' ';
            }
        }
    }

    public void gravity() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (this.grille[i][j] == ' ') {
                    for (int k = i;k > 0; k--) {
                        this.grille[k][j] = this.grille[k-1][j];
                    }
                    this.grille[0][j] = ' ';
                }
            }
        }
    }

    public void rotateDroite() {
        char[][] grilleRotate = new char[7][7];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {

                grilleRotate[j][6-i] = grille[i][j];

            }
        }
        this.grille = grilleRotate;
        gravity();
    }

    public void rotateGauche() {
        char[][] grilleRotate = new char[7][7];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {

                grilleRotate[6-j][i] = grille[i][j];

            }
        }
        this.grille = grilleRotate;
        gravity();
    }

    public boolean validerCoup(CoupGrille coup) {
        if (coup.getColonne() < 0 || coup.getColonne() > this.getNbColonne() - 1) { // Vérifie si le coup est dans les colonnes
            return false;
        }
        if (this.getCase(0, coup.getColonne()) != ' ') { // Vérifie si la case est vide
            return false;
        }
        return true;
    }

    public boolean checkWinner(char joueur) {
        for (int row = 0; row < this.getNbColonne(); row++) {
            for (int col = 0; col < this.getNbColonne() - 3; col++) {
                if (this.getCase(row, col) == joueur &&
                        this.getCase(row, col + 1) == joueur &&
                        this.getCase(row, col + 2) == joueur &&
                        this.getCase(row, col + 3) == joueur) { // Vérifie si le joueur a gagné en ligne
                    return true;
                }
            }
        }
        for (int row = 0; row < this.getNbColonne() - 3; row++) {
            for (int col = 0; col < this.getNbColonne(); col++) {
                if (this.getCase(row, col) == joueur &&
                        this.getCase(row + 1, col) == joueur &&
                        this.getCase(row + 2, col) == joueur &&
                        this.getCase(row + 3, col) == joueur) { // Vérifie si le joueur a gagné en colonne
                    return true;
                }
            }
        }
        for (int row = 3; row < this.getNbColonne(); row++) {
            for (int col = 0; col < this.getNbColonne() - 3; col++) {
                if (this.getCase(row, col) == joueur &&
                        this.getCase(row - 1, col + 1) == joueur &&
                        this.getCase(row - 2, col + 2) == joueur &&
                        this.getCase(row - 3, col + 3) == joueur) { // Vérifie si le joueur a gagné en diagonale
                    return true;
                }
            }
        }
        for (int row = 0; row < this.getNbColonne() - 3; row++) {
            for (int col = 0; col < this.getNbColonne() - 3; col++) {
                if (this.getCase(row, col) == joueur &&
                        this.getCase(row + 1, col + 1) == joueur &&
                        this.getCase(row + 2, col + 2) == joueur &&
                        this.getCase(row + 3, col + 3) == joueur) { // Vérifie si le joueur a gagné en diagonale
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFull() {
        for (int row = 0; row < this.getNbColonne(); row++) {
            for (int col = 0; col < this.getNbColonne(); col++) {
                if (this.getCase(row, col) == ' ') {
                    return false;
                }
            }
        }

        return true;
    }

    public void copyGrille(Grille grille) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                this.grille[i][j] = grille.getCase(i, j);
            }
        }
    }
}
