import java.util.Scanner;

public class InputJoueur {

    private final Game game;
    private int ligne, colonne;

    public InputJoueur(Game game){
        this.game = game;
    }

    public int demanderCoup(){
        int j;
        Scanner scanner = new Scanner(System.in);
        System.out.println(" quelle colonne ? ");
        j = scanner.nextInt();

        return j;
    }


}
