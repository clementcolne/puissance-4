package algorithme;

public class ProgressBarRotating extends Thread {
    boolean showProgress = true;
    final String anim = "_.·'‾'·.";

    public void run() {
        int x = 0;
        while (showProgress) {
            synchronized (anim) {
                System.out.print("\r[ L'ordinateur réflechit " + anim.charAt(x++ % anim.length()) + " ] ");
            }
            try {
                Thread.sleep(250);
            } catch (Exception ignored) {
            }
        }
    }
}
