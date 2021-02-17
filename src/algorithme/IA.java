package algorithme;

import puissance4.FinDePartie;
import puissance4.Game;
import puissance4.Plateau;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Clément Colné
 */

public class IA {

    private Noeud courant;
    private Game game;

    public IA(Game game) {
        this.game = game;
    }

    public void jouerMCTS(Etat e) {
        courant = new Noeud(null, -1);
        courant.setEtat(e);
        courant.setJoueur(e.getJoueur());
        List<Integer> coups = new ArrayList<>();

        int iter = 0;
        long fin;
        int meilleurCoup;
        long debut = System.currentTimeMillis();
        do {
            // algorithme MCTS
            if(courant.getNbFils() == 0) {
                // le noeud est une feuille
                if(courant.getNbSimulations() != 0) {
                    coups = e.getCoupsPossibles();
                    int k = 0;
                    while (k < coups.size() && coups.get(k) != -1) {
                        courant.ajouterFils(new Noeud(courant, coups.get(k)));
                        k++;
                    }
                    courant = courant.getFilsAt(0);
                }
                int valeur = rollout();
                while(courant.getParent() != null) {
                    courant.incrementerNbSimulations();
                    courant.setValeurTotale((int) (courant.getValeurTotale() + valeur));
                    courant = courant.getParent();
                }
            }else{
                // le noeud n'est pas une feuille
                // on choisit le fils qui maximise la bValeur (autrement appelée bValue)
                courant = courant.getFilsPrefere();
            }

            Noeud filsPref = courant.getFilsMaxVal();
            meilleurCoup = coups.get(courant.getIndexFils(filsPref));
            fin = System.currentTimeMillis();
            iter++;
        }while((fin - debut) < 3000);
        jouerCoup(meilleurCoup, e);
    }

    public int rollout() {
        Plateau p = new Plateau(courant.getEtat().getP());
        List<Integer> nbPossibilites = courant.getEtat().getCoupsPossibles();
        Random rand = new Random();
        int coup = rand.nextInt(nbPossibilites.size());
        char c = courant.getJoueur() ? 'X' : '0';
        p.insereJeton(c, coup);
        p.display();
        Noeud tmp = new Noeud(courant, coup);
        nbPossibilites = tmp.getEtat().getCoupsPossibles();
        while(nbPossibilites.size() > 0) {
            System.out.println(nbPossibilites);
            coup = rand.nextInt(nbPossibilites.size());
            c = courant.getJoueur() ? 'X' : '0';
            p.insereJeton(c, coup);
            tmp = new Noeud(tmp, coup);
            nbPossibilites = tmp.getEtat().getCoupsPossibles();
            p.display();
            System.exit(1);
        }
        return game.estVictoire() == FinDePartie.ORDI_GAGNE ? 1 : 0;
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
