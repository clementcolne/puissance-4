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

    /**
     * Constructeur de l'IA en fonction d'une partie
     * @param game la partie à laquelle l'IA est liée
     */
    public IA(Game game) {
        this.game = game;
    }

    /**
     * Algorithme de jeu de l'IA dans un état de jeu donné
     * @param e l'état de jeu donné
     */
    public void jouerMCTS(Etat e) {
        courant = new Noeud(null, -1);
        courant.setEtat(e);
        courant.setJoueur(e.getJoueur());
        List<Integer> coups;

        long fin;
        int meilleurCoup;
        long debut = System.currentTimeMillis();
        Random random = new Random();
        ProgressBarRotating progressBarRotating = new ProgressBarRotating();
        progressBarRotating.start();
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

        }while((fin - debut) < 3000);
        progressBarRotating.showProgress = false;


        while(courant.getParent() != null) {
            courant = courant.getParent();
        }

        if (courant.getNbFils() != 0) {
            // On a ici le critère max dans la séléction du noeud fils
            meilleurCoup = courant.getFilsMaxVal().getCoup();
            // On a ici le critère robuste dans la séléction du noeud fils
            //meilleurCoup = courant.getFilsRobusteVal().getCoup();
        }else{
            meilleurCoup = courant.getCoup();
        }
        System.out.println("Nombre de simulations totales : " + courant.getNbSimulations());
        System.out.println("Nombre de parties gagnantes simulées : " + courant.getValeurTotale());
        System.out.println("% de parties gagnantes simulées : " + courant.getValeurTotale()/courant.getNbSimulations() *100);
        e.jouerCoup(meilleurCoup);
    }

    /**
     * Fonction de simulation de jeu utilisée par l'IA
     * @return la valeur du dernier noeud visité, en fonction de si c'est une victoire ou non
     */
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


}


