package puissance4;

import algorithme.Etat;
import algorithme.IA;
import algorithme.Noeud;

public class Game {

    private final Plateau plateau;
    private final InputJoueur inputJoueur;
    private boolean tourJoueur;
    private final IA ia;

    /**
     * Constructeur de game
     * @param p le plateau de jeu
     * @param inputJoueur le coup joué par le joueur
     * @param joueur le joueur qui joue ce tour
     */
    public Game(Plateau p, InputJoueur inputJoueur, int joueur){

        // humain représenté par une croix

        this.inputJoueur = inputJoueur;
        plateau = p;
        if (joueur == 0){
            tourJoueur = true;
        }else{
            tourJoueur = false;
        }
        this.ia = new IA(this);
    }

    /**
     * Permet de changer le tour des joueurs
     */
    public void changeTourJoueur(){
        tourJoueur = !tourJoueur;
    }

    /**
     * Permet de jouer un coup dans le plateau
     * @return la colonne donnée par le joueur
     */
    public int jouerCoup() {
        return inputJoueur.demanderCoup();
    }

    /**
     * Permet de connaître le joueur qui joue actuellement
     * @return le joueur actuellement en train de jouer
     */
    public boolean isTourJoueur() {
        return tourJoueur;
    }

    /**
     * Vérifie si il y a une suite de 4 jeton de même couleur dans le plateau
     * @return vrai si il existe une suite de 4 jetons dans le plateau, faux sinon
     */
    public FinDePartie estVictoire() {
        int i, j, k, l, n = 0;
        for(i = 0 ; i < plateau.getColonnes() ; i++) {
            for(j = 0 ; j < plateau.getLignes() ; j++) {
                if (plateau.getCase(i, j) != ' ') {
                    n++;	// nb coups joués

                    // lignes
                    k = 0;
                    while(k < 4 && i + k < plateau.getColonnes() && plateau.getCase(i+k, j) == plateau.getCase(i, j))
                        k++;
                    if(k == 4)
                        return plateau.getCase(i, j) == 'O'? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;

                    // colonnes
                    k = 0;
                    while(k < 4 && j + k < plateau.getLignes() && plateau.getCase(i, j+k) == plateau.getCase(i, j))
                        k++;
                    if(k == 4)
                        return plateau.getCase(i, j) == 'O'? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;

                    // diagonales
                    k = 0;
                    l = 0;
                    while(k < 4 && l < plateau.getLignes() && i + k < plateau.getColonnes() && j + l < plateau.getLignes() && plateau.getCase(i+k, j+l) == plateau.getCase(i, j)) {
                        k++;
                        l++;
                    }
                    if(k == 4)
                        return plateau.getCase(i, j) == 'O'? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;

                    k = 0;
                    l = 0;
                    while(k < 4 && l < plateau.getLignes() && i + k < plateau.getColonnes() && j - l >= 0 && plateau.getCase(i+k, j-l) == plateau.getCase(i, j)) {
                        k++;
                        l++;
                    }
                    if(k == 4)
                        return plateau.getCase(i, j) == 'O'? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;
                }
            }
        }

        // et sinon tester le match nul
        if (n == plateau.getColonnes()* plateau.getLignes())
            return FinDePartie.MATCHNUL;

        return FinDePartie.NON;
    }

    /**
     * Vérifie si il y a une suite de 4 jeton de même couleur dans un plateau donné
     * @param plateau le plateau dans lequel on doit vérifier la condition de victoire
     * @return vrai si il existe une suite de 4 jetons dans le plateau, faux sinon
     */
    public FinDePartie estVictoire(Plateau plateau) {
        int i, j, k, l, n = 0;
        for(i = 0 ; i < plateau.getColonnes() ; i++) {
            for(j = 0 ; j < plateau.getLignes() ; j++) {
                if (plateau.getCase(i, j) != ' ') {
                    n++;	// nb coups joués

                    // lignes
                    k = 0;
                    while(k < 4 && i + k < plateau.getColonnes() && plateau.getCase(i+k, j) == plateau.getCase(i, j))
                        k++;
                    if(k == 4)
                        return plateau.getCase(i, j) == 'O'? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;

                    // colonnes
                    k = 0;
                    while(k < 4 && j + k < plateau.getLignes() && plateau.getCase(i, j+k) == plateau.getCase(i, j))
                        k++;
                    if(k == 4)
                        return plateau.getCase(i, j) == 'O'? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;

                    // diagonales
                    k = 0;
                    l = 0;
                    while(k < 4 && l < plateau.getLignes() && i + k < plateau.getColonnes() && j + l < plateau.getLignes() && plateau.getCase(i+k, j+l) == plateau.getCase(i, j)) {
                        k++;
                        l++;
                    }
                    if(k == 4)
                        return plateau.getCase(i, j) == 'O'? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;

                    k = 0;
                    l = 0;
                    while(k < 4 && l < plateau.getLignes() && i + k < plateau.getColonnes() && j - l >= 0 && plateau.getCase(i+k, j-l) == plateau.getCase(i, j)) {
                        k++;
                        l++;
                    }
                    if(k == 4)
                        return plateau.getCase(i, j) == 'O'? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;
                }
            }
        }

        // et sinon tester le match nul
        if (n == plateau.getColonnes()* plateau.getLignes())
            return FinDePartie.MATCHNUL;

        return FinDePartie.NON;
    }

    /**
     * Méthode de lancement de la partie
     */
    public void play() {
        FinDePartie finDePartie = FinDePartie.NON;
        while (finDePartie == FinDePartie.NON){
            boolean coupValide = false;
            if (isTourJoueur()) {
                while (!coupValide){
                    coupValide = plateau.insereJeton('X', jouerCoup());
                }
            }else{
                Etat e = new Etat(plateau);
                e.setJoueur(tourJoueur);
                ia.jouerMCTS(e);
            }
            finDePartie = estVictoire();
            plateau.display();
            changeTourJoueur();
        }

        if (finDePartie == FinDePartie.HUMAIN_GAGNE){
            System.out.println("PUISSANCE 4 ! L'humain a gagné !");
        }else if (finDePartie == FinDePartie.ORDI_GAGNE){
            System.out.println("PUISSANCE 4 ! L'ordi a gagné !");
        }else{
            System.out.println("Match nul !");
        }
    }
}
