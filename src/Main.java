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
        FinDePartie finDePartie = FinDePartie.NON;
        while (finDePartie == FinDePartie.NON){
            boolean coupValide = false;
            if (game.isTourJoueur()) {
                while (!coupValide){
                    coupValide = p.insereJeton('X', game.jouerCoup());
                }
            }else{
                while (!coupValide){
                    //coupValide = p.insereJeton('O', game.jouerCoupIA());
                    coupValide = p.insereJeton('O', game.jouerCoup());
                }
            }
            p.display();
            finDePartie = game.estVictoire();
            game.changeTourJoueur();
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
