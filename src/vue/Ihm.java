package vue;

import modele.*;

import java.util.Scanner;

public class Ihm {

    Scanner sc = new Scanner(System.in);

    // ------------------------------------- COMMUN ---------------------------------------------

    public int questionJeux(){
        int reponse = 0;
        while (reponse != 1 && reponse != 2) {
            System.out.println("A quel jeux voulez-vous jouer ?");
            System.out.println("1. Jeux de nim");
            System.out.println("2. Puissance 4");
            if (sc.hasNextInt()) {
                reponse = sc.nextInt();
            } else {
                sc.next();
            }
        }
        return reponse;
    }

    public int questionIA() {
        int reponse = 0;
        while (reponse != 1 && reponse != 2) {
            System.out.println("Voulez-vous jouer à deux joueurs ou contre l'ordinateur ?");
            System.out.println("1. Deux joueurs");
            System.out.println("2. Contre l'ordinateur");
            if (sc.hasNextInt()) {
                reponse = sc.nextInt();
            } else {
                sc.next();
            }
        }
        return reponse;
    }

    public void questionJoueur1() {
        System.out.println("Quel est le nom du joueur 1 ?");
    }

    public void questionJoueur2() {
        System.out.println("Quel est le nom du joueur 2 ?");
        System.out.println("Veuillez entrer un nom");
    }

    public String demandeJoueur(String playerName) {
        playerName = sc.nextLine();
        while (playerName.equals("")) {
            System.out.println("Veuillez entrer un nom");
            playerName = sc.nextLine();
        }
        return playerName;
    }

    public void textGagnantTotal(Joueur joueur) {
        System.out.println("Le gagnant du jeu est " + joueur.getNom());
    }

    public void textEgaliteTotal() {
        System.out.println("ex aequo");
    }

    public void afficheGagnantTotal(Joueur myPlayer1, Joueur myPlayer2) {
        int nbVictoireJ1 = myPlayer1.getNbPartiesGagnees();
        int nbVictoireJ2 = myPlayer2.getNbPartiesGagnees();
        if (nbVictoireJ1 > nbVictoireJ2) {
            textGagnantTotal(myPlayer1);
        } else if (nbVictoireJ1 < nbVictoireJ2) {
            textGagnantTotal(myPlayer2);
        } else {
            textEgaliteTotal();
        }
    }

    public void texteQuestionRejouer() {
        System.out.println("Voulez-vous rejouer ? (o/n)");
    }

    public String questionRejouer() {
        return sc.nextLine();
    }

    public void afficherGagnantPartie(Joueur myPlayer1) {
        System.out.println("Le gagnant de la partie est " + myPlayer1.getNom());
    }

    public void afficherEgalitePartie() {
        System.out.println("Ex aequo");
    }

    public void afficherNbPartieGagnees(Joueur myPlayer1, Joueur myPlayer2) {
        System.out.println("Nombre de victoires pour " + myPlayer1.getNom() + " : " + myPlayer1.getNbPartiesGagnees() + "\n"
                + "Nombre de victoires pour " + myPlayer2.getNom() + " : " + myPlayer2.getNbPartiesGagnees() + "\n");
    }

    public void entreEntier() {
        System.out.println("Veuillez entrer un nombre entier");
    }

    public void closeApp() {
        sc.close();
    }


    // ------------------------------------- NIM ---------------------------------------------

    public String askContrainteNim(){
        System.out.println("Contrainte : Choisir un nombre maximum d'allumette à retirer par tour");
        System.out.println("Voulez-vous jouer avec cette contrainte ? (o/n)");
        String reponse = sc.nextLine();
        while (true) {

            if (reponse.equals("o") || reponse.equals("n")) {
                break;
            }
            else {
                System.out.println("Veuillez entrer o ou n");
                reponse = sc.nextLine();
            }
        }
        return reponse;
    }

    public int askNbTas() {
        int nbTas = -1;

        while(true) {
            System.out.println("Combien de tas voulez-vous ?");
            if (sc.hasNextInt()) {
                nbTas = sc.nextInt();
                if (nbTas <= 0) {
                    sc.next();
                } else {
                    break;
                }
            } else {
                System.out.println("Veuillez entrer un nombre entier positif");
                sc.next();
            }
        }

        return nbTas;
    }

    public void displayNimGame(Tas tas) {
        System.out.println(tas.toString());
    }

    public void initNimGame(Tas tas) {
        tas.initialiser();
    }

    public int getTas(Joueur joueur) {
        int numTas = -1;

        while(true) {
            System.out.println(joueur.getNom() + ", quel tas voulez-vous choisir?");
            if (sc.hasNextInt()) {
                numTas = sc.nextInt();
                if (numTas <= 0) {
                    sc.next();
                } else {
                    break;
                }
            } else {
                System.out.println("Veuillez entrer un nombre entier positif");
                sc.next();
            }
        }

        return numTas;
    }

    public int getAllumettes() {
        int numAllumette = -1;

        while(true) {
            System.out.println("Combien d'allumettes voulez-vous retirer ?");
            if (sc.hasNextInt()) {
                numAllumette = sc.nextInt();
                if (numAllumette <= 0) {
                    sc.next();
                } else {
                    break;
                }
            } else {
                System.out.println("Veuillez entrer un nombre entier positif");
                sc.next();
            }
        }

        return numAllumette;
    }

    public int askMaxAllumettes() {
        int maxAllumettes = 0;
        while (true){
            System.out.println("Combien d'allumettes max voulez-vous pouvoir retirer par tour ?");
            if (sc.hasNextInt()) {
                maxAllumettes = sc.nextInt();
                return maxAllumettes;
            }
            else {
                System.out.println("Veuillez entrer un nombre entier");
                sc.next();
            }
        }
    }

    public void afficherIACoup(int tas, int nbAllumettes) {
        System.out.println("L'IA joue le tas : " + tas + " et retire " + nbAllumettes + " allumettes");
    }

    public void afficheErreurMaxAllumettes(int maxAllumettes) {
        System.out.println("Veuillez entrer un nombre entier inférieur ou égal à " + maxAllumettes);
    }

    // ------------------------------------- PUISSANCE 4 ---------------------------------------------

    public String askContraintePuissance4(){
        System.out.println("Contrainte : Au début de chaque tour, possibilité entre jouer un pion ou tournez le plateau à 90 degrés à droite ou à gauche");
        System.out.println("Voulez-vous jouer avec cette contrainte ? (o/n)");
        String reponse = sc.nextLine();
        while (true) {
            System.out.println("Veuillez entrer o ou n");
            if (reponse.equals("o") || reponse.equals("n")) {
                break;
            }
            else {
                reponse = sc.nextLine();
            }
        }
        return reponse;
    }

    public void displayPuissance4Game(Grille grille) {
        System.out.println(grille.toString());
    }

    public void textPlayerTurn(){
        System.out.println("Pour tourner la grille \"o\" sinon jouer un coup \"j\" ");

    }

    public String askPlayerTurn() {
        String reponse = sc.nextLine();
        return reponse;
    }

    public int askRotation(){
        System.out.println("Voulez-vous tourner la grille à droite ou à gauche ?");
        System.out.println("1 - Droite");
        System.out.println("2 - Gauche");
        int rotation = 0;
         while (true) {
            if (sc.hasNextInt()) {
                rotation = sc.nextInt();
                return rotation;
            }
            else {
                System.out.println("Veuillez entrer 1 ou 2");
                sc.next();
            }
         }
    }

    public void rotateRemaining(int rotation, Joueur joueur){
        System.out.println(joueur.getNom() + " ne possède plus que " + rotation + " rotations sur 4 possibles.");
    }

    public void afficherPionJ1(Joueur joueur) {
        System.out.println(joueur.getNom() + " jouera avec les X");
    }

    public void afficherPionJ2(Joueur joueur) {
        System.out.println(joueur.getNom() + " jouera avec les O");
    }

    public void currentPlayer(Joueur joueur, char pion) {
        System.out.println("C'est au tour de " + joueur.getNom() + " de jouer avec le pion " + pion);
    }

    public int getCoup() {
        boolean testCoup = false;
        int coup = -1;
        while (coup < 1 || coup > 7) {
            System.out.println("Veuillez choisir un entier entre 1 et 7");
            if (sc.hasNextInt()) {
                coup = sc.nextInt();
            } else {
                sc.next();
            }
        }
        return coup-1;
    }

    public void afficherColonechoisitParIA(int col) {
        System.out.println("L'IA joue la colone : " + col);
    }

    public void displayError() {
        System.out.println("Veuillez entrer o ou j");
    }


}
