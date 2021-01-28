import java.util.Scanner;

public class Game {

    private Plateau plateau;
    private final InputJoueur inputJoueur;
    private boolean tourJoueur;

    public Game(Plateau p, InputJoueur inputJoueur, int joueur){
        this.inputJoueur = inputJoueur;
        plateau = p;
        if (joueur == 0){
            tourJoueur = true;
        }else{
            tourJoueur = false;
        }
    }

    public void changeTourJoueur(){
        tourJoueur = !tourJoueur;
    }

    public InputJoueur getInputJoueur() {
        return inputJoueur;
    }

    public int jouerCoup() {
        return inputJoueur.demanderCoup();
    }

    public boolean isTourJoueur() {
        return tourJoueur;
    }

    public int jouerCoupIA() {
        return inputJoueur.IA();
    }

    /**
     * Vérifie si il y a une suite de 4 jeton de même couleur dans le plateau
     * @return vrai si il existe une suite de 4 jetons dans le plateau, faux sinon
     */
    public boolean estVictoire() {
        boolean is4jetons;
        char firstJeton;
        // on regarde si il existe une suite de 4 même jetons en colonne
        for(int i = 0 ; i < plateau.getColonnes() ; i++) {
            for(int j = plateau.getLignes() - 1 ; j > 3 ; j--) {
                // on initialise le premier jeton de la suite
                firstJeton = plateau.getCase(i, j);
                // variable qui permet d'identifier si il y a 4 jetons d'affilée de même couleur ou non
                is4jetons = true;
                // pour chaque colonne, on regarde les 3 premières lignes
                // on vérifie pour chacune de ces 3 premières lignes si il existe une suite de 4 jetons de même couleur
                for(int k = j ; k >= j - 3 ; k--) {
                    if(plateau.getCase(i, k) == ' ') {
                        // il y a une case vide, alors il ne peut pas y avoir 4 jetons de même couleur consécutifs
                        is4jetons = false;
                    }
                    if(firstJeton != plateau.getCase(i, k)) {
                        // il n'y a pas 4 jetons de même couleur dans la suite, ou il y a une case vide on s'arrête
                        is4jetons = false;
                    }
                }
                if(is4jetons) {
                    // si cette condition est vérifiée, alors il existe une suite de 4 jetons, puissance 4 !!
                    return true;
                }
            }
        }

        return false;
    }
}
