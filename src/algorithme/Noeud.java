package algorithme;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Clément Colné
 */

public class Noeud {

    private Noeud noeudParent = null;
    private List<Noeud> lesFils;
    private double nbSimulations = 0;
    private int valeurTotale = 0;
    private final double c = Math.sqrt(2);
    private int signe;
    private Etat etat;
    private int coup;
    private boolean joueur;

    public Noeud(Noeud noeudParent, int coup) {
        if(noeudParent != null && coup != -1) {
            etat = new Etat(noeudParent.getEtat());

            this.coup = coup;
            joueur = !noeudParent.joueur;
            char c = joueur ? 'X' : 'O';
            etat.jouerCoup(c, coup);
        }else{
            this.coup = -1;
            this.etat = null;
        }
        signe = joueur ? -1 : 1;
        this.noeudParent = noeudParent;
        lesFils = new ArrayList<>();
    }

    public Noeud getFilsPrefere() {
        Noeud res = lesFils.get(0);
        double highestBVal = lesFils.get(0).computeBValeur();

        for(Noeud fils : lesFils) {
            if(fils.getNbSimulations() == 0) {
                return fils;
            }
            if(fils.computeBValeur() > highestBVal) {
                highestBVal = fils.computeBValeur();
                res = fils;
            }
        }

        return res;
    }

    public void setJoueur(boolean joueur) {
        this.joueur = joueur;
    }

    public boolean getJoueur() {
        return joueur;
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

    public double getValeurTotale() {
        return valeurTotale;
    }

    public void setValeurTotale(int valeurTotale) {
        this.valeurTotale = valeurTotale;
    }

    public int getIndexFils(Noeud fils) {
        return lesFils.indexOf(fils);
    }

    public Noeud getFilsMaxVal() {
        Noeud res = lesFils.get(0);
        double highestVal = res.getValeurTotale();

        for(Noeud fils : lesFils) {
            if(fils.getValeurTotale() > highestVal) {
                highestVal = fils.getValeurTotale();
                res = fils;
            }
        }

        return res;
    }

    public int getSigne() {
        return signe;
    }

    public double computeBValeur() {
        //return noeudParent.getSigne() * (getValeurTotale()/nbSimulations) + c * Math.sqrt(Math.log(noeudParent.getNbSimulations())/nbSimulations);
        return (getValeurTotale()/nbSimulations) + c * Math.sqrt(Math.log(noeudParent.getNbSimulations())/nbSimulations);
    }

    public int getCoup() {
        return coup;
    }
}
