package puissance4;

import java.util.Random;
import java.util.Scanner;

public class InputJoueur {

    private int colonne;

    public InputJoueur(){
    }

    /**
     * Méthode qui demande la colonne dans laquelle le joueur veut placer le jeton
     * @return la colonne dans laquelle le joueur place le jeton
     */
    public int demanderCoup(){
        int j;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quelle colonne ? ");
        colonne = scanner.nextInt();
        while (colonne < 0 || colonne > 6){
            System.out.println("Les colonnes doivent être comprises entre 0 et 6.");
            System.out.println("Quelle colonne ? ");
            colonne = scanner.nextInt();
        }
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public int getColonne() {
        return colonne;
    }

    public int IA() {
        Random random = new Random();
        return random.nextInt(7);
    }
}
