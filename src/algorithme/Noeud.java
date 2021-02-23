package algorithme;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Clément Colné
 */

public class Noeud {

    private final Noeud noeudParent;
    private final List<Noeud> lesFils;
    private double nbSimulations;
    private int valeurTotale = 0;
    private final double c = Math.sqrt(2);
    private Etat etat;
    private final int coup;
    private boolean joueur;

    /**
     * Constructeur d'un noeud de l'arbre de recherche Monte Carlo
     * @param noeudParent le noeud parents du noeud actuel
     * @param coup le coup joué menant à ce noeud
     */
    public Noeud(Noeud noeudParent, int coup) {
        nbSimulations = 0;
        if(noeudParent != null && coup != -1) {
            etat = new Etat(noeudParent.getEtat());

            this.coup = coup;
            joueur = !noeudParent.joueur;
            etat.jouerCoup(coup);
        }else{
            this.coup = -1;
            this.etat = null;
        }
        this.noeudParent = noeudParent;
        lesFils = new ArrayList<>();
    }

    /**
     * Permet de savoir à quel fils l'héritage va aller, en fonction de sa b-valeur
     * @return le noeud fils possédant la plus grande b-valeur
     */
    public Noeud getFilsPrefere() {
        Noeud res = lesFils.get(0);
        double highestBVal = Double.NEGATIVE_INFINITY;

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

    /**
     * Permet de définir qui est el joueur sur ce noeud
     * @param joueur le joueur dans ce noeud
     */
    public void setJoueur(boolean joueur) {
        this.joueur = joueur;
    }

    /**
     * Permet de définir l'état et la configuration du plateau du noeud
     * @param e l'état lié au noeud
     */
    public void setEtat(Etat e) {
        this.etat = e;
    }

    /**
     * Permet d'obtenir l'état lié au noeud
     * @return l'état lié au noeud
     */
    public Etat getEtat() {
        return etat;
    }

    /**
     * Ajoute un noeud à la liste des noeuds fils
     * @param fils le noeud à ajouter aux fils
     */
    public void ajouterFils(Noeud fils) {
        lesFils.add(fils);
    }

    /**
     * Permet de connaître le nombre de fils du noeud
     * @return le nombre de fils du noeud
     */
    public int getNbFils() {
        return lesFils.size();
    }

    /**
     * Permet de récupérer le noeud fils situé à l'index donné
     * @param i l'index auquel on veut récupérer le noeud fils
     * @return le noeud fils situé à l'index donné
     */
    public Noeud getFilsAt(int i){
        return lesFils.get(i);
    }

    /**
     * Incrémente le nombre de simulation du noeud
     */
    public void incrementerNbSimulations(){
        nbSimulations++;
    }

    /**
     * Permet d'obtenir le nombre de simulation du noeud
     * @return le nombre de simulation du noeud
     */
    public double getNbSimulations() {
        return nbSimulations;
    }

    /**
     * Permet de récupérer le parent du noeud actuel
     * @return le parent du noeud actuel
     */
    public Noeud getParent() {
        return noeudParent;
    }

    /**
     * Permet d'obtenir la valeur totale, c'est-à-dire le nombre de victoires, d'un noeud
     * @return la valeur totale d'un noeud
     */
    public int getValeurTotale() {
        return valeurTotale;
    }

    /**
     * Permet de définir la valeur totale, c'est-à-dire le nombre de victoires, d'un noeud
     * @param valeurTotale nouvelle valeur totale d'un noeud
     */
    public void setValeurTotale(int valeurTotale) {
        this.valeurTotale = valeurTotale;
    }

    /**
     * Permet de récupérer le noeud fils ayant la plus grande valeur totale
     * @return le noeud fils avec la plus grande valeur totale
     */
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

    /**
     * Calcul de la b-valeur du noeud
     * @return la b-valeur du noeud
     */
    public double computeBValeur() {
        return (valeurTotale/nbSimulations) + c * Math.sqrt(Math.log(noeudParent.getNbSimulations())/nbSimulations);
    }

    /**
     * Permet de récupérer le coup associé au noeud
     * @return le coup associé au noeud
     */
    public int getCoup() {
        return coup;
    }
}
