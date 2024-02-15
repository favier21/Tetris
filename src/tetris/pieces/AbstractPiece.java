package tetris.pieces;
public abstract class AbstractPiece implements Piece{
    
    public int[][] grille;// Grille représetnant les cases occupées par la pièce. Des 1 représenteront les cases pleines
    
    private int[] centre; // coordonnées du centre de la pièce par rapport à la grille de la pièce
    
    private int[] position; // coordonnées de la pièce par rapport au reste de la grille
    
    private String type;// le type de la piece(un i, un l ect)

    public AbstractPiece(int x,int y,int[][] grille,String type) {
        //pas de test sur l'existance de grille car les pieces sont predefinis
        this.position = new int[] {x,y};
        this.grille = grille;
        this.centre = new int[] {grille[0].length/2,grille.length/2};
        this.type = type;
    }
    public void rotation(){rotation(true);}
    public void rotation(boolean horaire){
    /*
        fonction qui retourne la piece dans le sens horaire ou antihoraire

        Inputs:
            - bool: true: sens horaire, false: antihoraire
    */
        int[][] rotation = new int[this.getLargeur()][this.getHauteur()];
        
        int n = this.getHauteur();
        int m = this.getLargeur();
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                if(horaire)
                    rotation[y][n-x-1] = this.grille[x][y];
                else
                    rotation[m-y-1][x] = this.grille[x][y];

            }
        }
        this.grille = rotation;
    }

    
    public int getLargeur(){
        //renvoi la largeur actuelle de la piece
        return this.grille[0].length;
    }
    public int getHauteur(){
        //renvoi la hauteur actuelle de la piece
        return this.grille.length;
    }
    public String getType(){
        //renvoi le type de la piece
        return this.type;
    }
    
    public boolean estVide(int x,int y){
        /*
        renvoie si la case x,y estr vide
        Inputs:
            - x,y: coordonees d'une case dans la piece
        */
        return this.getCase(x, y) == 0;
    }
    
    public int[] getCentre(){
        /*
        renvoi la position du centre par rapport à la grille
        Outputs:
            - Int[]: x à la position 0 et y à 1
        */
        return this.centre;
    }

    public int[] getPosition() {
        /*
        renvoi la position du coin en haut à gauche par rapport à la grille (utile dans la classe PlateauPuzzle)
        Outputs:
            - Int[]: x à la position 0 et y à 1
        */
        return this.position;
    }

    public int getCase(int x,int y){
        if(x < this.getHauteur()  && y < this.getLargeur())
            return this.grille[x][y];
        return -1;
    }
    public int[] getPositionCentre() {
        /*
        renvoi la position du centre dans le plateau
        Outputs:
            - Int[]: x à la position 0 et y à 1
        */
        int x = this.position[0] + this.centre[0];
        int y = this.position[1] + this.centre[1];
        return new int[]{x,y};
    }
    public void setPosition(int x,int y){
        /*
        redefinis la position de la piece
        Inputs:
            - x,y: coordonees d'une case dans la piece
        */
        this.position = new int[] {x,y};
    }
    

    @Override
    public String toString() {
        String res = "";
        for(int[] ligne : this.grille){
            for(int colone : ligne)
                res = res + colone;
            res += "\n";
        }
        
        return res;
    }
    
    
        
}
