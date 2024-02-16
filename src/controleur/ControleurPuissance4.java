package controleur;

import modele.CoupGrille;
import modele.Grille;
import modele.Joueur;
import vue.Ihm;

public class ControleurPuissance4 extends Controleur {

    Grille grille = new Grille();

    public ControleurPuissance4(Ihm ihm) {
        super(ihm);
    }

    public void setup(String[] nomsJoueurs) {
        nomJoueur1 = nomsJoueurs[0];
        nomJoueur2 = nomsJoueurs[1];

        myPlayer1 = new Joueur(nomJoueur1); // Créer le joueur 1
        myPlayer2 = new Joueur(nomJoueur2); // Créer le joueur 2

        ihm.afficherPionJ1(myPlayer1); // Afficher le pion du joueur 1
        ihm.afficherPionJ2(myPlayer2); // Afficher le pion du joueur 2
    }

    public void playContrainte() {
        int compteurRotaJ1 = 0;
        int compteurRotaJ2 = 0;
        grille.clear(); // Rénitialise la grille pour la vider
        boolean winner = false;
        char joueur = 'X';

        while (!winner && !grille.isFull()) {
            ihm.displayPuissance4Game(grille);
            currentPlayer(joueur);
            ihm.textPlayerTurn();
            String turn = ihm.askPlayerTurn();
            while (true) {
                if (turn.equals("j")) { // Si le joueur appuie sur J, on joue un tour normal
                    int coup = askColumn();
                    playPawn(coup, joueur, grille); // Joue le pion
                    winner = grille.checkWinner(joueur); // Vérifie si le joueur a gagné
                    joueur = switchTurn(joueur); // Change de joueur
                    break;
                } else if (turn.equals("o")) {// Sinon on joue le tour avec la contrainte
                    if (joueur == 'X' && compteurRotaJ1 <= 4) {// On vérifie que le joueur 1 a fait moins de 5 rotations
                        playRotation(joueur, compteurRotaJ1);
                        compteurRotaJ1++;
                    } else if (joueur == 'O' && compteurRotaJ2 <= 4) {
                        playRotation(joueur, compteurRotaJ2);
                        compteurRotaJ2++;
                    }
                    winner = grille.checkWinner(joueur); // Vérifie si le joueur a gagné
                    joueur = switchTurn(joueur); // Change de joueur
                    break;
                } else {
                    ihm.displayError();
                    turn = ihm.askPlayerTurn();
                }
            }

        }
        ihm.displayPuissance4Game(grille);
        incrementScore(joueur);
        displayEndGame(joueur);
    }

    public void playContrainteIA() {
        int compteurRotaJoueur = 0;
        boolean winner = false;
        char joueur = 'X';
        grille.clear();

        int rotationTest = checkRotationIA();

        while (!winner && !grille.isFull()) {
            ihm.displayPuissance4Game(grille);
            currentPlayer(joueur);
            ihm.textPlayerTurn();
            String turn = ihm.askPlayerTurn();

            while (true) {
                if (joueur == 'X') {
                    if (turn.equals("j")) { // Si le joueur appuie sur J, on joue un tour normal
                        int coup = askColumn();
                        playPawn(coup, joueur, grille); // Joue le pion
                        winner = grille.checkWinner(joueur); // Vérifie si le joueur a gagné
                        joueur = switchTurn(joueur); // Change de joueur
                        break;
                    } else if (turn.equals("o")) {// Sinon on joue le tour avec la contrainte
                        if (compteurRotaJoueur <= 4) {// On vérifie que le joueur a fait moins de 5 rotations
                            playRotation(joueur, compteurRotaJoueur);
                            winner = grille.checkWinner(joueur); // Vérifie si le joueur a gagné
                            joueur = switchTurn(joueur); // Change de joueur
                            compteurRotaJoueur++;
                            break;
                        }
                    } else {
                        ihm.displayError();
                        turn = ihm.askPlayerTurn();
                    }
                }
            }

            if (checkRotationIA() == 1) {   // on fait la rotation droite
                grille.rotateDroite();
                winner = grille.checkWinner(joueur);
                joueur = switchTurn(joueur);
            } else if (checkRotationIA() == 2) {   // on fait la rotation droite
                grille.rotateGauche();
                winner = grille.checkWinner(joueur);
                joueur = switchTurn(joueur);
            } else {
                ihm.displayPuissance4Game(grille);
                int[] coup = getScoreCoup();
                playPawn(coup[1], joueur, grille);
                ihm.afficherColonechoisitParIA((coup[1] + 1)); // on affiche que l'IA joue
                winner = grille.checkWinner(joueur); // Vérifie si le joueur a gagné
                joueur = switchTurn(joueur); // Change de joueur
            }
        }
        ihm.displayPuissance4Game(grille);
        incrementScore(joueur);
        displayEndGame(joueur);
    }

    public void playRound() {
        grille.clear(); // Rénitialise la grille pour la vider
        boolean winner = false;
        char joueur = 'X';
        while (!winner && !grille.isFull()) {
            ihm.displayPuissance4Game(grille);
            currentPlayer(joueur);
            int coup = askColumn();
            playPawn(coup, joueur, grille); // Joue le pion
            winner = grille.checkWinner(joueur); // Vérifie si le joueur a gagné
            joueur = switchTurn(joueur); // Change de joueur
        }
        ihm.displayPuissance4Game(grille);
        incrementScore(joueur);
        displayEndGame(joueur);
    }

    public void playRoundIA() {
        grille.clear();
        boolean winner = false;
        char joueur = 'X';
        while (!winner && !grille.isFull()) {
            ihm.displayPuissance4Game(grille);
            currentPlayer(joueur);
            if (joueur == 'X') {
                int coup = askColumn();
                playPawn(coup, joueur, grille);
                winner = grille.checkWinner(joueur);
                joueur = switchTurn(joueur);
            } else {
                int[] coup = getScoreCoupOptimiser(grille);
                ihm.afficherColonechoisitParIA((coup[1] + 1));  // on affiche que l'IA joue
                playPawn(coup[1], joueur, grille);
                winner = grille.checkWinner(joueur);
                joueur = switchTurn(joueur);
            }
        }
        ihm.displayPuissance4Game(grille);
        incrementScore(joueur);
        displayEndGame(joueur);
    }

    public int[] getScoreCoup() {
        int[] rep;
        char IA = 'O';
        char joueur = 'X';

        if (check4Pions(IA, grille, true) >= 0) {
            rep = new int[]{7, check4Pions(IA, grille, true)};
        } else if (check4Pions(joueur, grille, true) >= 0) {
            rep = new int[]{6, check4Pions(joueur, grille, true)};
        } else if (check3Pions(IA, grille, true) >= 0) {
            rep = new int[]{5, check3Pions(IA, grille, true)};
        } else if (check3Pions(joueur, grille, true) >= 0) {
            rep = new int[]{4, check3Pions(joueur, grille, true)};
        } else if (check2Pions(IA, grille, true) >= 0) {
            rep = new int[]{3, check2Pions(IA, grille, true)};
        } else if (check2Pions(joueur, grille, true) >= 0) {
            rep = new int[]{2, check2Pions(joueur, grille, true)};
        } else {
            int coup = (int) (Math.random() * grille.getNbColonne());
            rep = new int[]{1, coup};
        }

        return rep;
    }

    public int[] getScoreCoupOptimiser(Grille grille) {
        int[] rep;
        char IA = 'O';
        char joueur = 'X';
        Grille grilleTempOpt = new Grille();

        grilleTempOpt.copyGrille(grille);
        ; // on copy la grille actuel

//        ---------------
//| | | | | | | |
//---------------
//| | | | | | | |
//---------------
//| | | | | | | |
//---------------
//| | | | | | | |
//---------------
//| | | | | | | |
//---------------
//| | | |X| | | |
//---------------
//| | |X|x|O|O|o|
//---------------
// 1 2 3 4 5 6 7


        // SCORE 7
        if (check4Pions(IA, grille, true) >= 0) {
            rep = new int[]{7, check4Pions(IA, grille, true)};
        }

        // SCORE 6
        else if (check4Pions(joueur, grille, true) >= 0) {
            rep = new int[]{6, check4Pions(joueur, grille, true)};
        }

        // SCORE 5
        else if (check3Pions(IA, grille, true) >= 0) {

            playPawn(check3Pions(IA, grilleTempOpt, true), IA, grilleTempOpt);

            if (check4Pions(IA, grilleTempOpt, false) >= 0) {
                rep = new int[]{5, check3Pions(IA, grille, true)};
            } else {

                // SCORE 4
                if (check3Pions(joueur, grille, true) >= 0) {
                    rep = new int[]{4, check3Pions(joueur, grille, true)};
                }

                // SCORE 3
                else if (check2Pions(IA, grille, true) >= 0) {
                    playPawn(check2Pions(IA, grilleTempOpt, true), IA, grilleTempOpt);

                    if (check3Pions(IA, grilleTempOpt, false) >= 0) {
                        int value = check3Pions(IA, grilleTempOpt, false);

                        for (int i = grilleTempOpt.getNbColonne() - 1; i >= 0; i--) {
                            grilleTempOpt.setCase(i, value, IA);

                            if (check4Pions(IA, grilleTempOpt, false) >= 0) {
                                rep = new int[]{3, check2Pions(IA, grille, true)};
                                break;
                            }


                            grilleTempOpt.setCase(i, value, ' ');
                        }
                    }

                    // SCORE 2
                    if (check2Pions(joueur, grille, true) >= 0) {
                        rep = new int[]{2, check2Pions(joueur, grille, true)};
                    }

                    // SCORE 1
                    else {
                        int coup = (int) (Math.random() * grille.getNbColonne());
                        rep = new int[]{1, coup};
                    }
                }

                // SCORE 2
                else if (check2Pions(joueur, grille, true) >= 0) {
                    rep = new int[]{2, check2Pions(joueur, grille, true)};
                }

                // SCORE 1
                else {
                    int coup = (int) (Math.random() * grille.getNbColonne());
                    rep = new int[]{1, coup};
                }
            }

        }

        // SCORE 4
        else if (check3Pions(joueur, grille, true) >= 0) {
            rep = new int[]{4, check3Pions(joueur, grille, true)};
        }

        // SCORE 3
        else if (check2Pions(IA, grille, true) >= 0) {

            playPawn(check2Pions(IA, grilleTempOpt, true), IA, grilleTempOpt);

            if (check3Pions(IA, grilleTempOpt, false) >= 0) {
                int value = check3Pions(IA, grilleTempOpt, false);

                for (int i = grilleTempOpt.getNbColonne() - 1; i >= 0; i--) {
                    grilleTempOpt.setCase(i, value, IA);

                    if (check4Pions(IA, grilleTempOpt, false) >= 0) {
                        rep = new int[]{3, check2Pions(IA, grille, true)};
                        break;
                    }


                    grilleTempOpt.setCase(i, value, ' ');
                }
            }

            // SCORE 2
            if (check2Pions(joueur, grille, true) >= 0) {
                rep = new int[]{2, check2Pions(joueur, grille, true)};
            }

            // SCORE 1
            else {
                int coup = (int) (Math.random() * grille.getNbColonne());
                rep = new int[]{1, coup};
            }
        }

        // SCORE 2
        else if (check2Pions(joueur, grille, true) >= 0) {
            rep = new int[]{2, check2Pions(joueur, grille, true)};
        }

        // SCORE 1
        else {
            int coup = (int) (Math.random() * grille.getNbColonne());
            rep = new int[]{1, coup};
        }

        return rep;
    }

    public int check4Pions(char pions, Grille grille, boolean gravite) {
        boolean alignement = false;
        char vide = ' ';
        int colTemp = -1;


        // Vérifie 4 pions sur la meme ligne
        for (int row = grille.getNbColonne() - 1; row >= 0; row--) {
            for (int col = 0; col < grille.getNbColonne() - 3; col++) {

                if (grille.getCase(row, col) == vide &&
                        grille.getCase(row, col + 1) == pions &&
                        grille.getCase(row, col + 2) == pions &&
                        grille.getCase(row, col + 3) == pions) {    // condition 1 pour la ligne


                    if (row < 6) {  // Vérifie la gravité
                        if (grille.getCase(row + 1, col) != vide) {
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    } else {
                        alignement = true;
                        colTemp = col;
                        break;
                    }

                    if (!gravite) { // on recuper la col sans la grvaite
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }

                if (grille.getCase(row, col) == pions &&
                        grille.getCase(row, col + 1) == vide &&
                        grille.getCase(row, col + 2) == pions &&
                        grille.getCase(row, col + 3) == pions) {    // condition 2 pour la ligne

                    if (row < 6) {  // Vérifie la gravité
                        if (grille.getCase(row + 1, col + 1) != vide) {
                            alignement = true;
                            colTemp = col + 1;
                            break;
                        }
                    } else {
                        alignement = true;
                        colTemp = col + 1;
                        break;
                    }

                    if (!gravite) { // on recuper la col sans la grvaite
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }

                if (grille.getCase(row, col) == pions &&
                        grille.getCase(row, col + 1) == pions &&
                        grille.getCase(row, col + 2) == vide &&
                        grille.getCase(row, col + 3) == pions) {    // condition 3 pour la ligne

                    if (row < 6) {  // Vérifie la gravité
                        if (grille.getCase(row + 1, col + 2) != vide) {
                            alignement = true;
                            colTemp = col + 2;
                            break;
                        }
                    } else {
                        alignement = true;
                        colTemp = col + 2;
                        break;
                    }

                    if (!gravite) { // on recuper la col sans la grvaite
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }

                if (grille.getCase(row, col) == pions &&
                        grille.getCase(row, col + 1) == pions &&
                        grille.getCase(row, col + 2) == pions &&
                        grille.getCase(row, col + 3) == vide) { // // condition 4 pour la ligne

                    if (row < 6) {  // Vérifie la gravité
                        if (grille.getCase(row + 1, col + 3) != vide) {

                            alignement = true;
                            colTemp = col + 3;
                            break;
                        }
                    } else {
                        alignement = true;
                        colTemp = col + 3;
                        break;
                    }

                    if (!gravite) { // on recuper la col sans la grvaite
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }
            }
        }


        // Vérifie 4 pions sur la meme colone
        if (!alignement) {
            for (int row = grille.getNbColonne() - 1; row >= 3; row--) {
                for (int col = 0; col < grille.getNbColonne(); col++) {
                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row - 1, col) == pions &&
                            grille.getCase(row - 2, col) == pions &&
                            grille.getCase(row - 3, col) == vide) { // // condition pour la colonne
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }
            }
        }


        // // Vérifie la diagonale pour 4 pions qui vas de haut gauche a bas droite
        if (!alignement) {
            for (int row = 3; row >= grille.getNbColonne() - 1; row++) {
                for (int col = 0; col < grille.getNbColonne() - 3; col++) {

                    if (grille.getCase(row, col) == vide &&
                            grille.getCase(row + 1, col + 1) == pions &&
                            grille.getCase(row + 2, col + 2) == pions &&
                            grille.getCase(row + 3, col + 3) == pions) {    // condition 1 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 1, col) != vide) {

                                alignement = true;
                                colTemp = col;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col;
                            break;
                        }

                        if (!gravite) { // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row + 1, col + 1) == vide &&
                            grille.getCase(row + 2, col + 2) == pions &&
                            grille.getCase(row + 3, col + 3) == pions) {    // condition 2 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 2, col + 1) != vide) {

                                alignement = true;
                                colTemp = col + 1;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col + 1;
                            break;
                        }

                        if (!gravite) { // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row + 1, col + 1) == pions &&
                            grille.getCase(row + 2, col + 2) == vide &&
                            grille.getCase(row + 3, col + 3) == pions) {    // condition 3 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 3, col + 2) != vide) {

                                alignement = true;
                                colTemp = col + 2;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col + 2;
                            break;
                        }

                        if (!gravite) { // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row + 1, col + 1) == pions &&
                            grille.getCase(row + 2, col + 2) == pions &&
                            grille.getCase(row + 3, col + 3) == vide) {     // condition 4 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 4, col + 3) != vide) {

                                alignement = true;
                                colTemp = col + 3;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col + 3;
                            break;
                        }

                        if (!gravite) { // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }
                }
            }
        }


        // Vérifie la diagonale pour 3 pions qui vas de bas gauche a haut droite
        if (!alignement) {
            for (int row = grille.getNbColonne() - 1; row >= 3; row--) {
                for (int col = 0; col < grille.getNbColonne() - 3; col++) {

                    if (grille.getCase(row, col) == vide &&
                            grille.getCase(row - 1, col + 1) == pions &&
                            grille.getCase(row - 2, col + 2) == pions &&
                            grille.getCase(row - 3, col + 3) == pions) {    // condition 1 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 1, col) != vide) {

                                alignement = true;
                                colTemp = col;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col;
                            break;
                        }

                        if (!gravite) { // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row - 1, col + 1) == vide &&
                            grille.getCase(row - 2, col + 2) == pions &&
                            grille.getCase(row - 3, col + 3) == pions) {    // condition 2 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 2, col + 1) != vide) {

                                alignement = true;
                                colTemp = col + 1;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col + 1;
                            break;
                        }

                        if (!gravite) { // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row - 1, col + 1) == pions &&
                            grille.getCase(row - 2, col + 2) == vide &&
                            grille.getCase(row - 3, col + 3) == pions) {    // condition 3 pour la diagonale

                        if (row <= 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 1, col + 2) != vide) {

                                alignement = true;
                                colTemp = col + 2;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col + 2;
                            break;
                        }

                        if (!gravite) { // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row - 1, col + 1) == pions &&
                            grille.getCase(row - 2, col + 2) == pions &&
                            grille.getCase(row - 3, col + 3) == vide) {     // condition 4 pour la diagonale

                        if (grille.getCase(row - 2, col + 3) != vide) {     // Vérifie la gravité

                            alignement = true;
                            colTemp = col + 3;
                            break;
                        }

                        if (!gravite) { // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }
                }
            }
        }

        return colTemp;
    }

    public int check3Pions(char pions, Grille grille, boolean gravite) {
        boolean alignement = false;
        char vide = ' ';
        int colTemp = -1;

        // Vérifie 3 pions sur la meme ligne
        for (int row = grille.getNbColonne() - 1; row >= 0; row--) {
            for (int col = 0; col < grille.getNbColonne() - 3; col++) {

                if (grille.getCase(row, col) == vide &&
                        grille.getCase(row, col + 1) == pions &&
                        grille.getCase(row, col + 2) == pions) {    // condition 1 pour la ligne

                    if (row < 6) {  // Vérifie la gravité
                        if (grille.getCase(row - 1, col) != vide) {
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    } else {
                        alignement = true;
                        colTemp = col;
                        break;
                    }

                    if (!gravite) { // on recuper la col sans la grvaite
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }

                if (grille.getCase(row, col) == pions &&
                        grille.getCase(row, col + 1) == vide &&
                        grille.getCase(row, col + 2) == pions) {    // condition 2 pour la ligne

                    if (row < 6) {  // Vérifie la gravité
                        if (grille.getCase(row - 1, col + 1) != vide) {
                            alignement = true;
                            colTemp = col + 1;
                            break;
                        }
                    } else {
                        alignement = true;
                        colTemp = col + 1;
                        break;
                    }
                    if (!gravite) {  // on recuper la col sans la grvaite
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }

                if (grille.getCase(row, col) == pions &&
                        grille.getCase(row, col + 1) == pions &&
                        grille.getCase(row, col + 2) == vide) {     // condition 3 pour la ligne

                    if (row < 6) {  // Vérifie la gravité
                        if (grille.getCase(row - 1, col + 2) != vide) {
                            alignement = true;
                            colTemp = col + 2;
                            break;
                        }
                    } else {
                        alignement = true;
                        colTemp = col + 2;
                        break;
                    }
                    if (!gravite) {  // on recuper la col sans la grvaite
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }
            }
        }


        // Vérifie 3 pions sur la meme colone
        if (!alignement) {
            for (int row = grille.getNbColonne() - 1; row >= 3; row--) {
                for (int col = 0; col < grille.getNbColonne(); col++) {
                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row - 1, col) == pions &&
                            grille.getCase(row - 2, col) == vide) { // condition pour la colone

                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }
            }
        }


        // Vérifie la diagonale pour 3 pions qui vas de haut gauche a bas droite
        if (!alignement) {
            for (int row = 3; row >= grille.getNbColonne() - 1; row++) {
                for (int col = 0; col < grille.getNbColonne() - 3; col++) {

                    if (grille.getCase(row, col) == vide &&
                            grille.getCase(row + 1, col + 1) == pions &&
                            grille.getCase(row + 2, col + 2) == pions) {    // condition 1 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 1, col) != vide) {

                                alignement = true;
                                colTemp = col;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                        if (!gravite) {  // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row + 1, col + 1) == vide &&
                            grille.getCase(row + 2, col + 2) == pions) {    // condition 2 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 2, col + 1) != vide) {

                                alignement = true;
                                colTemp = col + 1;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col + 1;
                            break;
                        }
                        if (!gravite) {  // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row + 1, col + 1) == pions &&
                            grille.getCase(row + 2, col + 2) == vide) {     // condition 3 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 3, col + 2) != vide) {

                                alignement = true;
                                colTemp = col + 2;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col + 2;
                            break;
                        }
                        if (!gravite) {  // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                }
            }
        }


        // Vérifie la diagonale pour 3 pions qui vas de bas gauche a haut droite
        if (!alignement) {
            for (int row = grille.getNbColonne() - 1; row >= 3; row--) {
                for (int col = 0; col < grille.getNbColonne() - 3; col++) {

                    if (grille.getCase(row, col) == vide &&
                            grille.getCase(row - 1, col + 1) == pions &&
                            grille.getCase(row - 2, col + 2) == pions) {    // condition 1 pour la diagonal

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 1, col) != vide) {

                                alignement = true;
                                colTemp = col;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                        if (!gravite) {  // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row - 1, col + 1) == vide &&
                            grille.getCase(row - 2, col + 2) == pions) {    // condition 2 pour la diagonal

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row, col + 1) != vide) {

                                alignement = true;
                                colTemp = col + 1;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col + 1;
                            break;
                        }
                        if (!gravite) {  // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row - 1, col + 1) == pions &&
                            grille.getCase(row - 2, col + 2) == vide) {     // condition 3 pour la diagonal


                        if (grille.getCase(row - 1, col + 2) != vide) {      // Vérifie la gravité
                            alignement = true;
                            colTemp = col + 2;
                            break;
                        }
                        if (!gravite) {  // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }
                }
            }
        }

        return colTemp;
    }

    public int check2Pions(char pions, Grille grille, boolean gravite) {
        boolean alignement = false;
        char vide = ' ';
        int colTemp = -1;

        // Vérifie 2 pions sur la meme ligne
        for (int row = grille.getNbColonne() - 1; row >= 0; row--) {
            for (int col = 0; col < grille.getNbColonne() - 3; col++) {

                if (grille.getCase(row, col) == vide &&
                        grille.getCase(row, col + 1) == pions) {    // condition 1 pour la ligne

                    if (row < 6) {  // Vérifie la gravité
                        if (grille.getCase(row - 1, col) != vide) {
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    } else {
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                    if (!gravite) {  // on recuper la col sans la grvaite
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }

                if (grille.getCase(row, col) == pions &&
                        grille.getCase(row, col + 1) == vide) {    // condition 2 pour la ligne

                    if (row < 6) {  // Vérifie la gravité
                        if (grille.getCase(row - 1, col + 1) != vide) {
                            alignement = true;
                            colTemp = col + 1;
                            break;
                        }
                    } else {
                        alignement = true;
                        colTemp = col + 1;
                        break;
                    }
                    if (!gravite) {  // on recuper la col sans la grvaite
                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }
            }
        }


        // Vérifie 2 pions sur la meme colone
        if (!alignement) {
            for (int row = grille.getNbColonne() - 1; row >= 3; row--) {
                for (int col = 0; col < grille.getNbColonne(); col++) {
                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row - 1, col) == vide) { // condition pour la colone

                        alignement = true;
                        colTemp = col;
                        break;
                    }
                }
            }
        }


        // Vérifie la diagonale pour 2 pions qui vas de haut gauche a bas droite
        if (!alignement) {
            for (int row = 3; row >= grille.getNbColonne() - 1; row++) {
                for (int col = 0; col < grille.getNbColonne() - 3; col++) {

                    if (grille.getCase(row, col) == vide &&
                            grille.getCase(row + 1, col + 1) == pions) {    // condition 1 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 1, col) != vide) {

                                alignement = true;
                                colTemp = col;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                        if (!gravite) {  // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row + 1, col + 1) == vide) {    // condition 2 pour la diagonale

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 2, col + 1) != vide) {

                                alignement = true;
                                colTemp = col + 1;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col + 1;
                            break;
                        }
                        if (!gravite) {  // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }
                }
            }
        }


        // Vérifie la diagonale pour 2 pions qui vas de bas gauche a haut droite
        if (!alignement) {
            for (int row = grille.getNbColonne() - 1; row >= 3; row--) {
                for (int col = 0; col < grille.getNbColonne() - 3; col++) {

                    if (grille.getCase(row, col) == vide &&
                            grille.getCase(row - 1, col + 1) == pions) {    // condition 1 pour la diagonal

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row + 1, col) != vide) {

                                alignement = true;
                                colTemp = col;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                        if (!gravite) {  // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }

                    if (grille.getCase(row, col) == pions &&
                            grille.getCase(row - 1, col + 1) == vide) {    // condition 2 pour la diagonal

                        if (row < 6) {  // Vérifie la gravité
                            if (grille.getCase(row, col + 1) != vide) {

                                alignement = true;
                                colTemp = col + 1;
                                break;
                            }
                        } else {
                            alignement = true;
                            colTemp = col + 1;
                            break;
                        }
                        if (!gravite) {  // on recuper la col sans la grvaite
                            alignement = true;
                            colTemp = col;
                            break;
                        }
                    }
                }
            }
        }

        return colTemp;
    }

    public int checkRotationIA() {
        char IA = 'O';
        int rotation = -1;
        Grille grilleTestDroite = new Grille();
        Grille grilleTestGauche = new Grille();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                grilleTestDroite.setCase(i, j, grille.getCase(i, j));
                grilleTestGauche.setCase(i, j, grille.getCase(i, j));
            }
        }

        // on test la rotation droite sur une grille temporaire
        grilleTestDroite.rotateDroite();
        if (grilleTestDroite.checkWinner(IA)) {     // Si l'IA gagne en tournant la grille on renvoie 1
            rotation = 1;
        }

        // on test la rotation gauche sur une grille temporaire
        if (rotation < 0) {     // on ne rentre pas dans la boucle si la rotaion de droite fait gagner l'IA
            grilleTestGauche.rotateGauche();
            if (grilleTestGauche.checkWinner(IA)) {     // Si l'IA gagne en tournant la grille on renvoie 2
                rotation = 2;
            }
        }

        return rotation;
    }

    private void incrementScore(char joueur) {
        if (joueur == 'X') {
            myPlayer2.gagnePartie();
        } else {
            myPlayer1.gagnePartie();
        }
    }

    private int askColumn() {
        boolean coupValide;
        int coup;

        do {
            coup = ihm.getCoup(); // Récupère le coup du joueur
            CoupGrille coupJoue = new CoupGrille(coup); // Créer un coup à partir du coup du joueur

            coupValide = grille.validerCoup(coupJoue); // Vérifie si le coup est valide
        } while (!coupValide);
        return coup;
    }

    private void playPawn(int coup, char joueur, Grille grille) {
        for (int row = grille.getNbColonne() - 1; row >= 0; row--) {
            if (grille.getCase(row, coup) == ' ') { // Vérifie si la case est vide
                grille.setCase(row, coup, joueur); // Remplit la case
                break;
            }
        }
    }

    private char switchTurn(char joueur) {
        if (joueur == 'X') {
            joueur = 'O';
        } else {
            joueur = 'X';
        }
        return joueur;
    }

    public void currentPlayer(char joueur) {
        if (joueur == 'X') {
            ihm.currentPlayer(myPlayer1, joueur);
        } else {
            ihm.currentPlayer(myPlayer2, joueur);
        }
    }

    public void playRotation(char joueur, int compteur) {
        remainRotate(joueur, compteur);
        int rotation = ihm.askRotation(); // Demande au joueur 1 ce qu'il veut faire
        if (rotation == 1) { // 1 => rotation vers la droite
            grille.rotateDroite();
        } else if (rotation == 2) { // 2 => rotation vers la gauche
            grille.rotateGauche();
        }
    }

    public void remainRotate(char joueur, int compteur) {
        if (joueur == 'X') {
            ihm.rotateRemaining(compteur, myPlayer1);
        } else {
            ihm.rotateRemaining(compteur, myPlayer2);
        }
    }

    public void displayEndGame(char joueur) {
        if (joueur == 'X') {
            ihm.afficherGagnantPartie(myPlayer2);
        } else if (joueur == 'O') {
            ihm.afficherGagnantPartie(myPlayer1);
        } else {
            ihm.afficherEgalitePartie();
        }
        ihm.afficherNbPartieGagnees(myPlayer1, myPlayer2);
    }

    public int askContrainte() {
        int rep = 0;
        String contrainte = ihm.askContraintePuissance4();
        if (contrainte.equals("o")) {
            rep = 1;
        } else if (contrainte.equals("n")) {
            rep = 2;
        }
        return rep;
    }

}