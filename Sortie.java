public class Sortie extends CaseTraversable {
    private Jeu jeu;

    public Sortie(int l, int c, Jeu jeu) {
        super(l, c);
        this.jeu = jeu;
    }

    @Override
    public void enter(Bille b) {
        super.enter(b);
        // victoire
        if (jeu != null) {
            jeu.victoire();
        }
    }

    @Override
    public String toString() {
        if (getContenu() != null) {
            return "(B)";
        }
        return "( )";
    }
}