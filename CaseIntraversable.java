public class CaseIntraversable extends Case {
    public CaseIntraversable(int l, int c) {
        super(l, c);
    }

    @Override
    public boolean estLibre() {
        return false;
    }

    @Override
    public void enter(Bille b) {
        // rien car mur intraversable
    }

    @Override
    public void leave(Bille b) {
        // rien 
    }

    @Override
    public void touch(Bille b) {
        // géré le rebond fait ailleurs
    }

    @Override
    public String toString() {
        return "###";
    }
}