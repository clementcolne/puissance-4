package algorithme;

import puissance4.Plateau;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clément Colné
 */
public class Etat {

    private Plateau p;
    private boolean joueur;

    public Etat(Plateau p) {
        this.p = p;
    }

    public Etat(Etat e) {
        p = e.getP();
    }

    public void setJoueur(boolean joueur) {
        this.joueur = joueur;
    }

    public boolean getJoueur() {
        return joueur;
    }

    public Plateau getP() {
        return p;
    }

    public void jouerCoup(char c, int coup) {
        Plateau pCopie = new Plateau(p);
        pCopie.insereJeton(c, coup);
    }

    public List<Integer> getCoupsPossibles() {
        List<Integer> res = new ArrayList<>();
        for(int i = 0 ; i < 7 ; i++) {
            if(!p.isFullCol(i)) {
                System.out.println(i);
                res.add(i);
            }
        }
        return res;
    }
}