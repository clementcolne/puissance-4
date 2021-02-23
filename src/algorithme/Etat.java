package algorithme;

import puissance4.Plateau;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clément Colné
 */
public class Etat {

    private final Plateau p;
    private boolean joueur;

    /**
     * Constructeur d'un état, prenant une configuration de jeu
     * @param p le plateau donnant la configuration du jeu
     */
    public Etat(Plateau p) {
        this.p = p;
    }

    /**
     * Constructeur copie d'un Etat
     * @param e l'état qu'il faut copier
     */
    public Etat(Etat e) {
        p = new Plateau(e.getP());
    }

    /**
     * Permet de choisir quel joueur joue ce tour
     * @param joueur le joueur qui joue actuellement
     */
    public void setJoueur(boolean joueur) {
        this.joueur = joueur;
    }

    /**
     * Permet de savoir quel joueur est en train de jouer
     * @return le joueur qui joue actuellement
     */
    public boolean getJoueur() {
        return joueur;
    }

    /**
     * Permet de récupérer le plateau lié à l'état
     * @return le plateau lié à l'état
     */
    public Plateau getP() {
        return p;
    }

    /**
     * Méthode qui place un jeton dans la colonne demandée
     * @param coup la colonne dans laquelle le jeton doit être inséré
     */
    public void jouerCoup(int coup) {
        char c = getJoueur() ? 'X' : 'O';
        p.insereJeton(c, coup);

        // à l'autre joueur de jouer
        setJoueur(!getJoueur());
    }

    /**
     * La liste des coups possibles dans le plateau
     * @return la liste des coups possibles dans le plateau
     */
    public List<Integer> getCoupsPossibles() {
        List<Integer> res = new ArrayList<>();
        for(int i = 0 ; i < 7 ; i++) {
            if(!p.isFullCol(i)) {
                res.add(i);
            }
        }
        return res;
    }
}