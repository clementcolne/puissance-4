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
                    Random random = new Random();
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
                // on choisit le fils qui maximise la bValeur (autrement appelée bValue)
                courant = courant.getFilsPrefere();
            }


            //System.out.println(courant.getNbFils());
            if (courant.getNbFils() != 0) {
                meilleurCoup = courant.getFilsMaxVal().getCoup();
            }else{
                meilleurCoup = courant.getCoup();
            }

            fin = System.currentTimeMillis();
            iter++;
            //System.exit(1);
        }while((fin - debut) < 3000);
        for (int i = 0; i < courant.getNbFils(); i++){
            System.out.println(i + " : " + courant.getFilsAt(i).getValeurTotale());
        }
        jouerCoup(meilleurCoup, e);
    }

    public int rollout() {
        Plateau p = new Plateau(courant.getEtat().getP());
        List<Integer> nbPossibilites = courant.getEtat().getCoupsPossibles();
        Random rand = new Random();
        int coup = rand.nextInt(nbPossibilites.size());
        char c = courant.getJoueur() ? 'X' : 'O';
        p.insereJeton(c, coup);
        Noeud noeudFils = new Noeud(courant, coup);
        nbPossibilites = noeudFils.getEtat().getCoupsPossibles();
        while(nbPossibilites.size() > 0) {
            coup = nbPossibilites.get(rand.nextInt(nbPossibilites.size()));
            c = noeudFils.getJoueur() ? 'X' : 'O';
            p.insereJeton(c, coup);
            noeudFils = new Noeud(noeudFils, coup);
            nbPossibilites = noeudFils.getEtat().getCoupsPossibles();
            if (game.estVictoire(p) == FinDePartie.ORDI_GAGNE){
                return 1;
            }
            if (game.estVictoire(p) == FinDePartie.HUMAIN_GAGNE || game.estVictoire(p) == FinDePartie.MATCHNUL){
                return 0;
            }
        }
        return game.estVictoire(p) == FinDePartie.ORDI_GAGNE ? 1 : 0;
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
