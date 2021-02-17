import puissance4.Game;
import puissance4.InputJoueur;
import puissance4.Plateau;

import java.util.Scanner;

/**
 * @author Clément Colné
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Qui commence (0 : humain, 1 : ordinateur) ?");
        Scanner scanner = new Scanner(System.in);
        int joueur = scanner.nextInt();
        while (joueur != 0 && joueur != 1){
            System.out.println("La réponse doit être 1 ou 0.");
            System.out.println("Qui commence (0 : humain, 1 : ordinateur) ?");
            joueur = scanner.nextInt();
        }
        Plateau p = new Plateau();
        InputJoueur inputJoueur = new InputJoueur();
        Game game = new Game(p, inputJoueur, joueur);
        game.pla?y();
    }

}
