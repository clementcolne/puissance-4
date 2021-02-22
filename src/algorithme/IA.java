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
        //TODO : L'IA réfléchit pas j'en ai marre elle est trop con aleeeeeed
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

                    // Si on prend le premier fils, l'algo rentreras toujours dans la même branche
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
                // on choisit le fils qui maximise la bValeur (autrement aussi appelée bValue)
                courant = courant.getFilsPrefere();
            }

            fin = System.currentTimeMillis();
            iter++;
            //System.exit(1);
        }while((fin - debut) < 3000);


        while(courant.getParent() != null) {
            courant = courant.getParent();
        }

        for (int i = 0; i < courant.getNbFils(); i++){
            System.out.println(courant.getFilsAt(i).getNbSimulations());
            System.out.println(i + " : " + courant.getFilsAt(i).getValeurTotale());
        }

        //System.out.println(courant.getNbFils());
        if (courant.getNbFils() != 0) {
            meilleurCoup = courant.getFilsMaxVal().getCoup();
        }else{
            meilleurCoup = courant.getCoup();
        }
        jouerCoup(meilleurCoup, e);
    }

    public int rollout() {
        Plateau p = new Plateau(courant.getEtat().getP());
        List<Integer> nbPossibilites;
        Random rand = new Random();
        int coup;
        char c;
        Etat e = new Etat(courant.getEtat());

        while(true) {
            if (game.estVictoire(p) == FinDePartie.ORDI_GAGNE){
                return 1;
            }
            if (game.estVictoire(p) == FinDePartie.HUMAIN_GAGNE || game.estVictoire(p) == FinDePartie.MATCHNUL){
                p.display();
                return 0;
            }
            nbPossibilites = e.getCoupsPossibles();
            coup = nbPossibilites.get(rand.nextInt(nbPossibilites.size()));
            c = (e.getJoueur() ? 'X' : 'O');
            jouerCoup(coup, e);
            p = e.getP();
        }
    }

    public boolean jouerCoup(int coup, Etat e) {
        Plateau p = e.getP();

        char c = e.getJoueur() ? 'X' : 'O';
        boolean insered = p.insereJeton(c, coup);
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
