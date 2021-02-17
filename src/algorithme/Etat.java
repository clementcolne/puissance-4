package algorithme;

import puissance4.Plateau;

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

    public int[] getCoupsPossibles() {
        int[] res = new int[7];
        for(int i = 0 ; i < 7 ; i++) {
            if(p.isFullCol(i)) {
                res[i] = -1;
            }else{
                res[i] = i;
            }
        }
        return res;
    }
}