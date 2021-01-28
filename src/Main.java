import java.util.Scanner;

/**
 * @author Clément Colné
 */
public class Main {

    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        //int joueur = scanner.nextInt();
        Plateau p = new Plateau();
        Game game = new Game();
        InputJoueur inputJoueur = new InputJoueur(game);

        for (int i = 0; i < 7; i++) {
            System.out.println(p.insereJeton('X', inputJoueur.demanderCoup()));
            p.display();
        }
        /*p.display();
        System.out.println(p.play('X', 0));
        p.display();
        System.out.println(p.play('X', 0));
        p.display();
        System.out.println(p.play('X', 0));
        p.display();
        System.out.println(p.play('X', 0));
        p.display();*/
    }

}
