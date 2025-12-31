public class Jeu {
    private Terrain terrain;
    private Bille bille;
    private FenetreJeu fenetre;
    private boolean jeuEnCours = true;

    public Jeu(String f) {
            this.terrain = new Terrain(f, this);
            // position initiale au centre du labyrinthe
            this.bille = new Bille(1.5, 1.5, this);
            this.fenetre = new FenetreJeu(this);
            // positionner la bille dans la case initiale
            terrain.mettreAJourBille(bille);
    }

    public void tour() {
        if (jeuEnCours) {
            bille.avance();
            terrain.mettreAJourBille(bille);
            fenetre.repaint();
            
            verifierSortie();
        }
    }

    private void verifierSortie() {
        int x = (int) Math.floor(bille.getX());
        int y = (int) Math.floor(bille.getY());
        
        if (x >= 0 && x < terrain.getLargeur() && 
            y >= 0 && y < terrain.getHauteur()) {
            Case caseActuelle = terrain.getCase(y, x);
            if (caseActuelle instanceof Sortie) {
                victoire();
            }
        }
    }

    public void victoire() {
        if (jeuEnCours) {
            jeuEnCours = false;
            Donjon.arreterTimer();
            fenetre.afficherVictoire();
        }
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Bille getBille() {
        return bille;
    }

    public FenetreJeu getFenetre() {
        return fenetre;
    }

    public boolean isJeuEnCours() {
        return jeuEnCours;
    }
}