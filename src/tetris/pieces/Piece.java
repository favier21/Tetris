package tetris.pieces;


public interface Piece {
    
    public void rotation();
    public void rotation(boolean horaire);
    
    public int getLargeur();

    public int getHauteur();

    public String getType();

    public int getCase(int x,int y);
    
    public boolean estVide(int x,int y);
    
    public int[] getCentre();

    public int[] getPosition();

    public void setPosition(int x,int y);

 
}
