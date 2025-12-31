public class CaseTraversable extends Case {
    private Bille contenu;

    public CaseTraversable(int l, int c) {
        super(l, c);
        this.contenu = null;
    }

    public CaseTraversable(int l, int c, Bille con) {
        super(l, c);
        this.contenu = con;
    }

    public Bille getContenu() {
        return this.contenu;
    }

    public void vide() {
        this.contenu = null;
    }

    public void entre(Bille e) {
        this.contenu = e;
    }

    @Override
    public boolean estLibre() {
        return (getContenu() == null);
    }

    @Override
    public void enter(Bille b) {
        this.contenu = b;
    }

    @Override
    public void leave(Bille b) {
        if (this.contenu == b) {
            this.contenu = null;
        }
    }

    @Override
    public void touch(Bille b) {
        // rien faire pour les cases traversables ordinaires
    }

    @Override
    public String toString() {
        if (contenu != null) {
            return "(B)";
        }
        return "   ";
    }
}