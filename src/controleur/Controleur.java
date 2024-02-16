package controleur;

import modele.Joueur;
import vue.Ihm;

public abstract class Controleur {
    Ihm ihm;

    String nomJoueur1 = "";
    String nomJoueur2 = "";

    Joueur myPlayer1;
    Joueur myPlayer2;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    abstract public void setup(String[] nomsJoueurs);

    abstract public int askContrainte();
    abstract public void playRound();
    abstract public void playRoundIA();
    abstract public void playContrainte();
    abstract public void playContrainteIA();

    public void play(int choix) {
        if (choix == 1) {
            playSansIA();
        }
        if (choix == 2) {
            playAvecIA();
        }
    }

    public void playSansIA() {

        // demande les noms
        String nomJoueur1Local = "";
        String nomJoueur2Local = "";

        ihm.questionJoueur1();
        while (nomJoueur1Local.isEmpty()){
            nomJoueur1Local = ihm.demandeJoueur(nomJoueur1Local);
        }
        ihm.questionJoueur2();
        while (nomJoueur2Local.isEmpty()){
            nomJoueur2Local = ihm.demandeJoueur(nomJoueur2Local);

        }
        String[] nomsJoueurs = {nomJoueur1Local, nomJoueur2Local};

        // demande les contraintes
        int contrainte = askContrainte();

        // $ setup les plateaux
        setup(nomsJoueurs);

        // $ jouer un round
        if (contrainte == 1) {
            playContrainte();
        } else {
            playRound();
        }

        replay();

    }

    public void playAvecIA() {

        // demande les noms
        String nomJoueur1Local = "";
        String nomJoueur2Local = "IA";

        ihm.questionJoueur1();
        while (nomJoueur1Local.isEmpty()){
            nomJoueur1Local = ihm.demandeJoueur(nomJoueur1Local);
        }
        String[] nomsJoueurs = {nomJoueur1Local, nomJoueur2Local};

        // demande les contraintes
        int contrainte = askContrainte();

        // $ setup les plateaux
        setup(nomsJoueurs);

        // $ jouer un round
        if (contrainte == 1) {
            playContrainteIA();
        } else {
            playRoundIA();
        }

        replayIA();
    }

    public void replay(){
        // demander si on veut rejouer
        while (true) { // Tant que le joueur n'a pas choisi de quitter
            String reponse = ihm.questionRejouer(); // Demande si le joueur veut rejouer
            if (reponse.equals("o")) { // Si oui
                int contrainte = askContrainte();
                if (contrainte == 1) {
                    playContrainte();
                } else {
                    playRound();
                }
            } else if (reponse.equals("n")) { // Si non
                ihm.afficheGagnantTotal(myPlayer1, myPlayer2); // Affiche le gagnant total
                ihm.closeApp(); // Ferme l'application
                break;
            } else {
                ihm.texteQuestionRejouer(); // Si le joueur ne saisit pas o ou n
            }

        }
    }

    public void replayIA(){
        // demander si on veut rejouer
        while (true) { // Tant que le joueur n'a pas choisi de quitter
            String reponse = ihm.questionRejouer(); // Demande si le joueur veut rejouer
            if (reponse.equals("o")) { // Si oui
                int contrainte = askContrainte();
                if (contrainte == 1) {
                    playContrainteIA();
                } else {
                    playRoundIA();
                }
            } else if (reponse.equals("n")) { // Si non
                ihm.afficheGagnantTotal(myPlayer1, myPlayer2); // Affiche le gagnant total
                ihm.closeApp(); // Ferme l'application
                break;
            } else {
                ihm.texteQuestionRejouer(); // Si le joueur ne saisit pas o ou n
            }

        }
    }

}
