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
    private double nbSimulations = 0.;
    private int valeurTotale = 0;
    private final double c = Math.sqrt(2);
    private final int signe;

    public Noeud(Noeud noeudParent, int signe) {
        this.noeudParent = noeudParent;
        lesFils = new ArrayList<>();
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
        int valeur = 0;
        for(Noeud fils : lesFils) {
            valeur += fils.valeurTotale();
        }
        valeurTotale += valeur;
        return valeurTotale;
    }

    public int getSigne() {
        return signe;
    }

    public double computeBValeur() {
        return noeudParent.getSigne() * valeurTotale() + c * Math.sqrt(Math.log(noeudParent.getNbSimulations())/nbSimulations);
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
