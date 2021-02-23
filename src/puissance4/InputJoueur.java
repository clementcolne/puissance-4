package puissance4;

import java.util.Random;
import java.util.Scanner;

public class InputJoueur {

    /**
     * Constructeur de l'input du joueur
     */
    public InputJoueur(){
    }

    /**
     * Méthode qui demande la colonne dans laquelle le joueur veut placer le jeton
     * @return la colonne dans laquelle le joueur place le jeton
     */
    public int demanderCoup(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quelle colonne ? ");
        int colonne = scanner.nextInt();
        while (colonne < 0 || colonne > 6){
            System.out.println("Les colonnes doivent être comprises entre 0 et 6.");
            System.out.println("Quelle colonne ? ");
            colonne = scanner.nextInt();
        }
        return colonne;
    }
}
