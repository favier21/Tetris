package tetris.pieces;
import java.util.Random;
public class PieceBuilder {

    private int x;
    private int y;
    private int[][] grille;
    private String type;


    public PieceBuilder(){
        this.x = 0;
        this.y = 0;
        this.grille = new int[][] {};
        this.type = "empt";
    }

    public PieceBuilder avecgrille(String nom) {
        //cette fontion utilise une des grilles prefaites pour créer une piece
        switch( nom.toLowerCase() ) {
            case "i":
                this.grille = new int[][] { {1},
                                            {1},
                                            {1},
                                            {1}};
                this.type = "i";
                break;
            case "j":
                this.grille = new int[][] { {0,1},
                                            {0,1},
                                            {1,1}};
                this.type = "j";
                break;
            case "t":
                this.grille = new int[][] { {1,1,1},
                                            {0,1,0},
                                            {0,1,0},
                                            {0,1,0}};
                this.type = "t";
                break;

            case "l":
                this.grille = new int[][] { {1,0},
                                            {1,0},
                                            {1,1}};
                this.type = "l";
                break;
            case "u":
                this.grille = new int[][] { {1,0,1},
                                            {1,0,1},
                                            {1,1,1}};
                this.type = "u";
                break;
            case "o":
                this.grille = new int[][] { {1,1},
                                            {1,1}};
                this.type = "o";
                break;
            case "s":
                this.grille = new int[][] { {0,1,1},
                                            {1,1,0}};
                this.type = "s";
                break;
            case "z":
                this.grille = new int[][] { {1,1,0},
                                            {0,1,1}};
                this.type = "z";
                break;
            default:
                return null;
                
        }
        return this;
    }
    public PieceBuilder avecgrille(int[][] grille) {
        //cette fontion utilise une grille personelle pour créer une piece
        this.grille = grille;
        return this;
    }

    public PieceBuilder avecgrilleAleatoire() {
        //cette fontion utilise une grille aléatoire parmis celles prefaite pour créer une piece
        String[] choix = new String[] {"i","j","t","l","o","z","s","u",};
        return this.avecgrilleAleatoire(choix);
    }
    public PieceBuilder avecgrilleAleatoire(String[] choix) {
        //cette fontion utilise une grille aléatoire parmis celles en parametre(celle-ci ) pour créer une piece
        Random rand = new Random();
        int rindex =  rand.nextInt(choix.length-1);
        return this.avecgrille(choix[rindex]);
    }

    public PieceBuilder avecposition(int x,int y) {
        this.x = x;
        this.y = y;
        return this;
    }


    public Piece build(){
        return new PieceQlq(this.x,this.y,this.grille,this.type);
    }

}