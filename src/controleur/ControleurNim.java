package controleur;

import modele.CoupInvalideException;
import modele.CoupNim;
import modele.Joueur;
import modele.Tas;

import vue.Ihm;

public class ControleurNim extends Controleur {

    private int numTasJouerParIA = 0;
    private int numAllumettesJouerParIA = 0;

    public ControleurNim(Ihm ihm) {
        super(ihm);
    }

    public void setup(String[] nomsJoueurs) {
        nomJoueur1 = nomsJoueurs[0];
        nomJoueur2 = nomsJoueurs[1];

        myPlayer1 = new Joueur(nomJoueur1);
        myPlayer2 = new Joueur(nomJoueur2);
    }

    public void playRound() {
        int nbTas = ihm.askNbTas();
        Tas tas = new Tas(nbTas);
        ihm.initNimGame(tas);
        Joueur currentPlayer = myPlayer1;
        Joueur nextPlayer = myPlayer2;
        Joueur playerIntermediaire;
        while (!tas.partieTerminee()) {
            ihm.displayNimGame(tas);
            while (true) {
                try {
                    int numeroTas = ihm.getTas(currentPlayer);
                    int nbAllumettes = ihm.getAllumettes();
                    CoupNim coup = new CoupNim(numeroTas, nbAllumettes);
                    try {
                        tas.gererCoup(coup);
                        if (tas.partieTerminee()) {
                            currentPlayer.gagnePartie();
                        }
                        break;
                    } catch (CoupInvalideException ignored) {

                    }
                } catch (NumberFormatException e) {
                    ihm.entreEntier();
                }
            }
            playerIntermediaire = currentPlayer;
            currentPlayer = nextPlayer;
            nextPlayer = playerIntermediaire;
        }
        displayEndGame(currentPlayer);
    }

    public void playRoundIA() {
        int nbTas = ihm.askNbTas();
        Tas tas = new Tas(nbTas);
        ihm.initNimGame(tas);
        Joueur currentPlayer = myPlayer1;
        Joueur nextPlayer = myPlayer2;
        Joueur playerIntermediaire;
        while (!tas.partieTerminee()) {
            ihm.displayNimGame(tas);
            while (true) {
                if (currentPlayer.equals(myPlayer1)) {
                    try {
                        CoupNim coup = jouerCoup(currentPlayer);
                        try {
                            tas.gererCoup(coup);
                            if (tas.partieTerminee()) {
                                currentPlayer.gagnePartie();
                            }
                            break;
                        } catch (CoupInvalideException ignored) {}
                    } catch (NumberFormatException e) {
                        ihm.entreEntier();
                    }
                } else {
                    int[][] listeCoups = calculCoupsPossibles(tas);
                    CoupNim coupChoisit = choisirCoup(listeCoups);

                    try {
                        tas.gererCoup(coupChoisit);
                        if (tas.partieTerminee()) {
                            currentPlayer.gagnePartie();
                        }
                        break;
                    } catch (CoupInvalideException ignored) {}
                }
            }

            if (currentPlayer.equals(myPlayer2)) {
                ihm.afficherIACoup(numTasJouerParIA,numAllumettesJouerParIA); // on affiche ce que L'IA joue
            }

            playerIntermediaire = currentPlayer;
            currentPlayer = nextPlayer;
            nextPlayer = playerIntermediaire;
        }
        displayEndGame(currentPlayer);
    }

    public void playContrainte() {
        int maxAllumettes = ihm.askMaxAllumettes();
        int nbTas = ihm.askNbTas();
        Tas tas = new Tas(nbTas);
        ihm.initNimGame(tas);
        Joueur currentPlayer = myPlayer1;
        Joueur nextPlayer = myPlayer2;
        Joueur playerIntermediaire;
        while (!tas.partieTerminee()) {
            ihm.displayNimGame(tas);
            while (true) {
                try {
                    int numeroTas = ihm.getTas(currentPlayer);
                    int nbAllumettes = ihm.getAllumettes();
                    while ( nbAllumettes > maxAllumettes) {
                        ihm.afficheErreurMaxAllumettes(maxAllumettes);
                        nbAllumettes = ihm.getAllumettes();
                    }
                    CoupNim coup = new CoupNim(numeroTas, nbAllumettes);
                    try {
                        tas.gererCoup(coup);
                        if (tas.partieTerminee()) {
                            currentPlayer.gagnePartie();
                        }
                        break;
                    } catch (CoupInvalideException e) {
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    ihm.entreEntier();
                }
            }
            playerIntermediaire = currentPlayer;
            currentPlayer = nextPlayer;
            nextPlayer = playerIntermediaire;
        }
        displayEndGame(currentPlayer);
    }

    public void playContrainteIA() {
        int maxAllumettes = ihm.askMaxAllumettes();
        int nbTas = ihm.askNbTas();
        Tas tas = new Tas(nbTas);
        ihm.initNimGame(tas);
        Joueur currentPlayer = myPlayer1;
        Joueur nextPlayer = myPlayer2;
        Joueur playerIntermediaire;

        while (!tas.partieTerminee()) {
            ihm.displayNimGame(tas);
            while (true) {
                if (currentPlayer.equals(myPlayer1)) {
                    try {
                        CoupNim coup = jouerCoupContrainte(currentPlayer, maxAllumettes);
                        try {
                            tas.gererCoup(coup);
                            if (tas.partieTerminee()) {
                                currentPlayer.gagnePartie();
                            }
                            break;
                        } catch (CoupInvalideException e) {
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e) {
                        ihm.entreEntier();
                    }
                } else {
                    int[][] listeCoups = calculCoupsPossibles(tas);
                    CoupNim coupChoisit = choisirCoupContrainte(listeCoups, maxAllumettes);

                    try {
                        tas.gererCoup(coupChoisit);
                        if (tas.partieTerminee()) {
                            currentPlayer.gagnePartie();
                        }
                        break;
                    } catch (CoupInvalideException ignored) {}
                }
            }

            if (currentPlayer.equals(myPlayer2)) {
                ihm.afficherIACoup(numTasJouerParIA,numAllumettesJouerParIA); // on affiche ce que L'IA joue
            }

            playerIntermediaire = currentPlayer;
            currentPlayer = nextPlayer;
            nextPlayer = playerIntermediaire;
        }
        displayEndGame(currentPlayer);
    }

    public int askContrainte(){
        int rep = 0;
        String contrainte = ihm.askContrainteNim();
        if (contrainte.equals("o")){
            rep = 1;
        } else if (contrainte.equals("n")){
            rep = 2;
        }
        return rep;
    }

    public CoupNim jouerCoup(Joueur currentPlayer) {
        int numeroTas = ihm.getTas(currentPlayer);
        int nbAllumettes = ihm.getAllumettes();
        CoupNim coup = new CoupNim(numeroTas, nbAllumettes);
        return coup;
    }

    public CoupNim jouerCoupContrainte(Joueur currentPlayer, int maxAllumettes) {
        int numeroTas = ihm.getTas(currentPlayer);
        int nbAllumettes = ihm.getAllumettes();
        while (nbAllumettes > maxAllumettes) {
            ihm.afficheErreurMaxAllumettes(maxAllumettes);
            nbAllumettes = ihm.getAllumettes();
        }
        CoupNim coup = new CoupNim(numeroTas, nbAllumettes);
        return coup;
    }

    public int[][] calculCoupsPossibles(Tas tas) {
        int[][] list = new int[tas.getNbTas()][];

        for (int i = 0; i < list.length; i++) {
            int[] list2 = new int[tas.nbAllumettes(i + 1)];
            for (int j = 1; j <= tas.nbAllumettes(i + 1); j++) {
                list2[j - 1] = j;
            }
            list[i] = list2;
        }
        return list;
    }

    public CoupNim choisirCoup(int[][] listeCoups) {
        int numTas = (int) (Math.random() * listeCoups.length); // on selectionne un tas aleatoire
        while (true) {
            if (listeCoups[numTas].length != 0) { // Si la liste au numero de tas choisit est vide
                break;
            } else {
                numTas = (int) (Math.random() * listeCoups.length); // On sélectionne un nouveau tas
            }
        }
        int nbAllumettes = (int) (Math.random() * listeCoups[numTas].length - 1) + 1; // on choisit un nombre d'allumettes dans notre tas

        CoupNim coup = new CoupNim(numTas + 1, nbAllumettes); // Puis on créer un coup avec ces paramètres
        numTasJouerParIA = numTas + 1;
        numAllumettesJouerParIA = nbAllumettes;
        return coup;
    }

    public CoupNim choisirCoupContrainte(int[][] listeCoups, int maxAllumettes) {
        int numTas = (int) (Math.random() * listeCoups.length); // on selectionne un tas aleatoire
        while (true) {
            if (listeCoups[numTas].length != 0) { // Si la liste au numero de tas choisit est vide
                break;
            } else {
                numTas = (int) (Math.random() * listeCoups.length); // On sélectionne un nouveau tas
            }
        }
        int nbAllumettes = (int) (Math.random() * (maxAllumettes + 1)); // on choisit un nombre d'allumettes dans notre tas

        CoupNim coup = new CoupNim(numTas + 1, nbAllumettes); // Puis on créer un coup avec ces paramètres
        numTasJouerParIA = numTas + 1;
        numAllumettesJouerParIA = nbAllumettes;
        return coup;
    }

    public void displayEndGame(Joueur currentPlayer){
        if (currentPlayer == myPlayer2) {
            ihm.afficherGagnantPartie(myPlayer1);
        } else if (currentPlayer == myPlayer1) {
            ihm.afficherGagnantPartie(myPlayer2);
        }
        else {
            ihm.afficherEgalitePartie();
        }
        ihm.afficherNbPartieGagnees(myPlayer1, myPlayer2);
    }

}
