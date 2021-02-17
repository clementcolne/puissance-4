package puissance4;

/**
 * @author Clément Colné
 */
public class Plateau {

    private char[][] plateau;
    // nombre de colonnes dans le plateau
    private static final int colonnes = 7;
    // nombre de lignes dans le plateau
    private static final int lignes = 6;

    public Plateau() {
        init();
    }

    public Plateau(Plateau p) {
        plateau = p.getPlateau();
    }

    /**
     * Retourne une copie du tableau 2 dimensions plateau
     * @return une copie du tableau 2 dimensions plateau
     */
    public char[][] getPlateau() {
        char[][] res = new char[colonnes][lignes];
        for(int i = 0 ; i < colonnes ; i++) {
            for(int j = 0 ; j < lignes ; j++) {
                res[i][j] = plateau[i][j];
            }
        }
        return res;
    }

    /**
     * Initialise le plateau de jeu
     */
    public void init() {
        plateau = new char[colonnes][lignes]; // création du plateau
        // par défaut, les cases du tableau sont des espaces vides
        for(int i = 0 ; i < colonnes ; i++) {
            for(int j = 0 ; j < lignes ; j++) {
                plateau[i][j] = ' ';
            }
        }
    }

    /**
     * Retourne vrai si la colonne col est pleine
     * @param col colonne
     * @return vrai si la colonne col est pleine
     */
    public boolean isFullCol(int col) {
        return plateau[col][0] != ' ';
    }

    /**
     * Place le jeton dans la colonne
     * @param jeton jeton d'un joueur
     * @param col colonne où insérer le jeton
     */
    public boolean insereJeton(char jeton, int col) {
        if(col < 0) {
            return false;
        }
        if(plateau[col][0] != ' ') {
            // la colonne demandée est déjà pleine
            System.out.println("Colonne " + col + " déjà pleine");
            System.out.println("------------------------------");
            return false;
        }

        // lig = la ligne où on va insérer le jeton, au premier coup, tout en bas
        int lig = lignes - 1;
        for(int j = 0 ; j < lignes - 1 ; j++) {
            if(plateau[col][j + 1] != ' ') {
                // la ligne d'en dessous contient déjà un jeton, on s'arrête
                lig = j;
                j = lignes;
            }
        }
        // on insère le jeton
        plateau[col][lig] = jeton;
        System.out.println("Colonne " + col + " pas déjà pleine, insertion à la ligne " + lig);
        System.out.println("------------------------------");
        return true;
    }

    /**
     * Retourne la case demandée
     * @param col colonne de la case
     * @param lig ligne de la case
     * @return la case demandée
     */
    public char getCase(int col, int lig) {
        return plateau[col][lig];
    }

    public int getColonnes() {
        return colonnes;
    }

    public int getLignes() {
        return lignes;
    }

    /**
     * Affiche le plateau de jeu
     */
    public void display() {
        int i, j;

        for(i = 0 ; i < colonnes ; i++)
            System.out.print(" " + i + " |");
        System.out.println("\n----------------------------");

        for(i = 0; i < lignes ; i++) {
            for(j = 0 ; j < colonnes ; j++) {
                System.out.print(" " + plateau[j][i] + " |");
            }
            System.out.println("\n----------------------------");
        }
    }

}
