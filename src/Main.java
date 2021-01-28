/**
 * @author Clément Colné
 */
public class Main {

    public static void main(String[] args) {
        Plateau p = new Plateau();
        p.display();
        System.out.println(p.play('X', 0));
        p.display();
        System.out.println(p.play('X', 0));
        p.display();
        System.out.println(p.play('X', 0));
        p.display();
        System.out.println(p.play('X', 0));
        p.display();
    }

}
