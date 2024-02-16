package vue;

import controleur.ControleurNim;
import controleur.ControleurPuissance4;

public class Main {
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        ControleurNim controleurNim = new ControleurNim(ihm);
        ControleurPuissance4 controleurPuissance4 = new ControleurPuissance4(ihm);
        if (ihm.questionJeux() == 1){
            int choix = ihm.questionIA();
            controleurNim.play(choix);
        } else {
            int choix = ihm.questionIA();
            controleurPuissance4.play(choix);
        }
    }
}
