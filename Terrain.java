import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Terrain {
    private int hauteur, largeur;
    private Case[][] carte;
    private Jeu jeu;

    public Terrain(String file, Jeu jeu) {
        this.jeu = jeu;
        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            this.hauteur = sc.nextInt();
            this.largeur = sc.nextInt();
            sc.nextLine();
            this.carte = new Case[hauteur][largeur];
            
            for (int l = 0; l < hauteur; l++) {
                String line = sc.nextLine();
                for (int c = 0; c < largeur; c++) {
                    Character ch = line.charAt(c);
                    Case cc;
                    
                    switch (ch) {
                        case '#':
                            cc = new CaseIntraversable(l, c);
                            break;
                        case ' ':
                            cc = new CaseOrdinaire(l, c);
                            break;
                        case 'O':
                            cc = new Sortie(l, c, jeu);
                            break;
                        default:
                            // dans le pire des cas, créer case ordinaire
                            cc = new CaseOrdinaire(l, c);
                            break;
                    }
                    carte[l][c] = cc;
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Si pb, lancer une exception
            throw new RuntimeException("Fichier labyrinthe non trouvé: " + file);
        }
    }

    public int getHauteur() {
        return this.hauteur;
    }

    public int getLargeur() {
        return this.largeur;
    }

    public Case getCase(int lig, int col) {
        if (lig >= 0 && lig < hauteur && col >= 0 && col < largeur) {
            return carte[lig][col];
        }
        return null;
    }

    public void print() {
        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[i].length; j++) {
                System.out.print(carte[i][j].toString());
            }
            System.out.println();
        }
    }

    public void mettreAJourBille(Bille bille) {
        int ancienX = (int) Math.floor(bille.getX() - bille.getVx());
        int ancienY = (int) Math.floor(bille.getY() - bille.getVy());
        int nouveauX = (int) Math.floor(bille.getX());
        int nouveauY = (int) Math.floor(bille.getY());
        
        if (ancienX >= 0 && ancienX < largeur && ancienY >= 0 && ancienY < hauteur &&
            nouveauX >= 0 && nouveauX < largeur && nouveauY >= 0 && nouveauY < hauteur) {
            
            // quitter ancienne case
            if (carte[ancienY][ancienX] instanceof CaseTraversable) {
                ((CaseTraversable) carte[ancienY][ancienX]).leave(bille);
            }
            
            // entrer dans nouvelle case
            if (carte[nouveauY][nouveauX] instanceof CaseTraversable) {
                ((CaseTraversable) carte[nouveauY][nouveauX]).enter(bille);
            }
            
            // ne fait rien, mais je mets comme même 
            carte[nouveauY][nouveauX].touch(bille);
        }
    }
}