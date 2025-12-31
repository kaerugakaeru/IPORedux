public class CaseOrdinaire extends CaseTraversable {
    public CaseOrdinaire(int l, int c) {
        super(l, c);
    }

    public CaseOrdinaire(int l, int c, Bille b) {
        super(l, c, b);
    }

    @Override
    public String toString() {
        if (getContenu() != null) {
            return "(B)";
        }
        return "   ";
    }
}