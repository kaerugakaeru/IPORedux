import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FenetreJeu extends JPanel {
    private Jeu jeu;
    private int tailleCase = 50;
    private JFrame frame;
    private int derniereSourisX, derniereSourisY;
    private boolean sourisPressee = false;

    public FenetreJeu(Jeu jeu) {
        this.jeu = jeu;
        
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(jeu.getTerrain().getLargeur() * tailleCase, jeu.getTerrain().getHauteur() * tailleCase));
        setupControlesSouris();
        setupFenetre();
    }

    private void setupControlesSouris() {
        // mouseListener pour détecter les clics
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (jeu.isJeuEnCours()) {
                    sourisPressee = true;
                    derniereSourisX = e.getX();
                    derniereSourisY = e.getY();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                sourisPressee = false;
            }
        });

        // mouseMotionListeneur pour détecter les déplacements
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (sourisPressee && jeu.isJeuEnCours()) {
                    int dx = e.getX() - derniereSourisX;
                    int dy = e.getY() - derniereSourisY;
                    
                    // facteur accélération
                    double facteur = 0.001;
                    jeu.getBille().accelerer(dx, dy, facteur);
                    
                    derniereSourisX = e.getX();
                    derniereSourisY = e.getY();
                    
                    repaint();
                }
            }
        });

        // Focus pour que les contrôles fonctionnent
        //setFocusable(true);
        //requestFocusInWindow();
    }

    private void setupFenetre() {
        frame = new JFrame("Redux - Projet ORABI Zahra && NDIAYE Adama Fahbine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dessinerTerrain(g);
        dessinerBille(g);
        
        // Afficher les instructions
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Cliquez et glissez pour deplacer la bille", 10, 20);
        
        // Afficher la vitesse
        double vitesse = jeu.getBille().vitesseAbsolue();
        g.drawString(String.format("Vitesse: %.3f", vitesse), 10, 40);
    }

    private void dessinerTerrain(Graphics g) {
        Terrain terrain = jeu.getTerrain();
        
        for (int i = 0; i < terrain.getHauteur(); i++) {
            for (int j = 0; j < terrain.getLargeur(); j++) {
                Case c = terrain.getCase(i, j);
                Color couleur;
                
                if (c instanceof CaseIntraversable) {
                    couleur = Color.BLACK;
                } else if (c instanceof Sortie) {
                    couleur = Color.GREEN;
                } else {
                    couleur = Color.LIGHT_GRAY;
                }
                
                g.setColor(couleur);
                g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                
                // Bordures des cases
                g.setColor(Color.DARK_GRAY);
                g.drawRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
            }
        }
    }

    private void dessinerBille(Graphics g) {
        Bille bille = jeu.getBille();
        
        // Convertir coordonnées monde → écran
        int x = (int) (bille.getX() * tailleCase);
        int y = (int) (bille.getY() * tailleCase);
        int rayonPixels = (int) (bille.getRayon() * tailleCase);
        
        // Dessiner la bille (centre aux coordonnées, rayon ajusté)
        g.setColor(Color.RED);
        g.fillOval(x - rayonPixels, y - rayonPixels, 
                   rayonPixels * 2, rayonPixels * 2);
        
        // Contour de la bille
        g.setColor(Color.DARK_GRAY);
        g.drawOval(x - rayonPixels, y - rayonPixels, 
                   rayonPixels * 2, rayonPixels * 2);
        
        // Indicateur de direction (si la bille bouge)
        double vitesse = bille.vitesseAbsolue();
        if (vitesse > 0.01) {
            g.setColor(Color.YELLOW);
            int x2 = x + (int)(bille.getVx() * tailleCase * 5);
            int y2 = y + (int)(bille.getVy() * tailleCase * 5);
            g.drawLine(x, y, x2, y2);
        }
    }

    public void afficherVictoire() {
        JOptionPane.showMessageDialog(frame,
            "Bravo! Vous avez atteint la sortie!\n",
            "Victoire!", JOptionPane.INFORMATION_MESSAGE);
    }
}