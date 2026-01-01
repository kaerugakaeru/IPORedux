import java.lang.Math;

public class Bille {
    private double x, y;
    private double vx, vy;
    private double rayon = 0.3;
    private Jeu jeu;

    public Bille(double x, double y, Jeu jeu) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.jeu = jeu;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public double getRayon() { return rayon; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVitesse(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void avance() {
        // avancer avec la vitesse
        this.x += this.vx;
        this.y += this.vy;

        // collisions avec les bords
        gererCollisions();

        // appliquer les frottements
        appliquerFrottement(0.005);
    }

    public void appliquerFrottement(double f) {
        double v = vitesseAbsolue();
        if (v > 0) {
            double dx = vx / v;
            double dy = vy / v;
            double nouvelleVitesse = Math.max(0, v - f);
            vx = dx * nouvelleVitesse;
            vy = dy * nouvelleVitesse;
        }
    }

    public void accelerer(double sx, double sy, double facteurAcceleration) {
        vx += sx * facteurAcceleration;
        vy += sy * facteurAcceleration;
        
        // limiter la vitesse maximale (0.02 rec)
        double vMax = 0.2;
        double v = vitesseAbsolue();
        if (v > vMax) {
            vx = (vx / v) * vMax;
            vy = (vy / v) * vMax;
        }
    }

    public void gererCollisions() {
        Terrain terrain = jeu.getTerrain();
        int colonne = (int) Math.floor(x);
        int ligne = (int) Math.floor(y);
        
        // vérifier les collisions avec les murs
        // collision avec mur gauche
        if (x - rayon < colonne && colonne > 0) {
            Case caseGauche = terrain.getCase(ligne, colonne-1);
            if (caseGauche != null && caseGauche instanceof CaseIntraversable && vx < 0) {
                vx = -vx * 0.8; // Rebond avec perte d'énergie
                x = colonne + rayon; // Corriger la position
            }
        }
        
        // collision avec mur droite
        if (x + rayon > colonne + 1 && colonne < terrain.getLargeur() - 1) {
            Case caseDroite = terrain.getCase(ligne, colonne+1);
            if (caseDroite != null && caseDroite instanceof CaseIntraversable && vx > 0) {
                vx = -vx * 0.8; //meme
                x = colonne + 1 - rayon;//meme
            }
        }
        
        // Collision avec mur haut
        if (y - rayon < ligne && ligne > 0) {
            Case caseHaut = terrain.getCase(ligne-1, colonne);
            if (caseHaut != null && caseHaut instanceof CaseIntraversable && vy < 0) {
                vy = -vy * 0.8;//meme
                y = ligne + rayon;//meme
            }
        }
        
        // Collision avec mur bas
        if (y + rayon > ligne + 1 && ligne < terrain.getHauteur() - 1) {
            Case caseBas = terrain.getCase(ligne+1, colonne);
            if (caseBas != null && caseBas instanceof CaseIntraversable && vy > 0) {
                vy = -vy * 0.8;//meme
                y = ligne + 1 - rayon;//meme
            }
        }
        // verifier que la bille est bien dans les limites du terrain
        x = Math.max(rayon, Math.min(x, terrain.getLargeur() - rayon));
        y = Math.max(rayon, Math.min(y, terrain.getHauteur() - rayon));
    }

    public double vitesseAbsolue() {
        return Math.sqrt(vx * vx + vy * vy);
    }

    public String toString() {
        return String.format("Bille(%.2f,%.2f) v=(%.3f,%.3f)", x, y, vx, vy);
    }
}