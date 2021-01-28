import java.util.Scanner;

/**
 * @author Clément Colné
 */
public class Main {

    public static void main(String[] args) {
        Plateau p = new Plateau();
        Game game = new Game(p);
        InputJoueur inputJoueur = new InputJoueur(game);

        for (int i = 0 ; i < 150 ; i++) {
            System.out.println(p.insereJeton('X', inputJoueur.demanderCoup()));
            p.display();
            if(game.estVictoire()) {
                System.out.println("PUISSANCE 4");
                System.exit(1);
            }
        }
    }

}
