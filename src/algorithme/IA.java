package algorithme;

import puissance4.Plateau;

/**
 * @author Clément Colné
 */

public class IA {

    private Noeud courant;

    public IA() {
    }

    public void jouerMCTS(Etat e) {
        int[] coups = new int[7];
        int meilleurCoup;

        courant = new Noeud(null, -1);
        courant.setEtat(e);
        coups = e.getCoupsPossibles();
        int k = 0;
        Noeud enfant;
        while (k < coups.length && coups[k] != -1) {
            courant.ajouterFils(new Noeud(courant, coups[k]));
            k++;
        }
        meilleurCoup = coups[0]; // TODO : a modifier
        jouerCoup(meilleurCoup, e);

    }

    public boolean jouerCoup(int coup, Etat e) {
        Plateau p = e.getP();

        boolean insered = p.insereJeton('O', coup);
        if(!insered) {
            return false;
        }

        // à l'autre joueur de jouer
        e.setJoueur(!e.getJoueur());

        return true;
    }

    public void setRacine(Noeud racine) {
        courant = racine;
    }

}
