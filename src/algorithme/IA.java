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
    private final Game game;

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
        int meilleurCoup = 3;
        long debut = System.currentTimeMillis();
        Random random = new Random();
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
                    //courant = courant.getFilsAt(0);
                    courant = courant.getFilsAt(random.nextInt(courant.getNbFils()));
                }
                int valeur = rollout();
                while(courant.getParent() != null) {
                    courant.incrementerNbSimulations();
                    courant.setValeurTotale((int) (courant.getValeurTotale() + valeur));
                    courant = courant.getParent();
                }
                courant.incrementerNbSimulations();
                courant.setValeurTotale((int) (courant.getValeurTotale() + valeur));
            }else{
                // le noeud n'est pas une feuille
                // on choisit le fils qui maximise la bValeur (autrement aussi également appelée bValue)
                courant = courant.getFilsPrefere();
            }

            fin = System.currentTimeMillis();
            iter++;
        }while((fin - debut) < 3000);


        while(courant.getParent() != null) {
            courant = courant.getParent();
        }

        if (courant.getNbFils() != 0) {
            meilleurCoup = courant.getFilsMaxVal().getCoup();
        }else{
            meilleurCoup = courant.getCoup();
        }
        e.jouerCoup(meilleurCoup);
    }

    public int rollout() {
        Plateau p = new Plateau(courant.getEtat().getP());
        List<Integer> nbPossibilites;
        Random rand = new Random();
        int coup;
        Etat e = new Etat(courant.getEtat());

        while(true) {
            if (game.estVictoire(p) == FinDePartie.ORDI_GAGNE){
                return 1;
            }
            if (game.estVictoire(p) == FinDePartie.HUMAIN_GAGNE || game.estVictoire(p) == FinDePartie.MATCHNUL){
                return 0;
            }
            nbPossibilites = e.getCoupsPossibles();
            coup = nbPossibilites.get(rand.nextInt(nbPossibilites.size()));
            e.jouerCoup(coup);
            p = e.getP();
        }
    }

    public void setRacine(Noeud racine) {
        courant = racine;
    }

}
