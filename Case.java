public abstract class Case {
    private final int lig, col;

    public Case(int l, int c) {
        this.lig = l;
        this.col = c;
    }

    public int getLig() { return lig; }
    public int getCol() { return col; }

    public abstract boolean estLibre();
    public abstract void enter(Bille b);
    public abstract void leave(Bille b);
    public abstract void touch(Bille b);
}