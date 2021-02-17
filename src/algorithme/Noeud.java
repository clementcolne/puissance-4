package algorithme;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Clément Colné
 */

public class Noeud {

    private Noeud noeudParent = null;
    private List<Noeud> lesFils;
    private double mu;
    private double nbSimulations;
    private int nbVictoires;
    private int valeurTotale = 0;
    private final double c = Math.sqrt(2);
    private int signe;
    private Etat etat;
    private int coup;
    private boolean joueur;

    public Noeud(Noeud noeudParent, int coup) {
        if(noeudParent != null && coup != -1) {
            etat = new Etat(noeudParent.getEtat());

            //jouerCoup(etat, coup);

            this.coup = coup;
            joueur = !noeudParent.joueur;
        }
        this.noeudParent = noeudParent;
        lesFils = new ArrayList<>();

        // POUR MCTS:
        nbVictoires = 0;
        nbSimulations = 0;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setSigne(int signe) {
        this.signe = signe;
    }

    public void ajouterFils(Noeud fils) {
        lesFils.add(fils);
    }

    public int getNbFils() {
        return lesFils.size();
    }

    public Noeud getFilsAt(int i){
        return lesFils.get(i);
    }

    public void incrementerNbSimulations(){
        nbSimulations++;
    }

    public double getNbSimulations() {
        return nbSimulations;
    }

    public Noeud getParent() {
        return noeudParent;
    }

    public double valeurTotale() {
        return valeurTotale;
    }

    public int getSigne() {
        return signe;
    }

    public double computeBValeur() {
        return noeudParent.getSigne() * (valeurTotale()/nbSimulations) + c * Math.sqrt(Math.log(noeudParent.getNbSimulations())/nbSimulations);
    }

    public double rollout() {
        Noeud courant = this;
        while (true){
            if (courant.getNbFils() == 0) {
                return nbSimulations;
            }
            Random random = new Random();
            courant = getFilsAt(random.nextInt(getNbFils()));
        }
    }

}
