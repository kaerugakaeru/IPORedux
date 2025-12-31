import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Donjon {
    private static Timer timer;
    private static Jeu jeu;
    private static FenetreJeu fenetre;

    public static void main(String[] args) {        
        jeu = new Jeu("laby1.txt");
        fenetre = jeu.getFenetre();
        demarrerBoucleJeu();
                    
        System.out.println("Jeu Redux demarre avec succes!");
        System.out.println("Fichier labyrinthe: " + "laby1.txt");
        System.out.println("Controles: Cliquez et glissez pour deplacer la bille");
    }

    private static void demarrerBoucleJeu() {
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jeu != null && jeu.isJeuEnCours()) {
                    jeu.tour();
                }
            }
        });
        
        timer.setInitialDelay(0);
        timer.start();
    }

    public static void arreterTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
}