
package tetris.Strategy;

import java.util.Random;

import tetris.PlateauPuzzle;
import tetris.pieces.Piece;

/**
 * StrategyEmpile
 */
public class StrategyRandom implements Strategy{

    protected Random random;

    public StrategyRandom(){
        this.random = new Random();
    }


public Piece getPieceRandom(PlateauPuzzle plateau){
    
    int taille = plateau.getListePieces().size();

    int rand = this.random.nextInt(taille);
    Piece p = plateau.getListePieces().get(rand);
    
return p;
}

public void execute(PlateauPuzzle plateau){
        Piece p = this.getPieceRandom(plateau);

        for(int i = 0; i < 100 ; i++){
            if(plateau.movePieces(p,this.random.nextInt(plateau.getHauteur()),this.random.nextInt(plateau.getLargeur()))){
                for(int x = 0; x < this.random.nextInt(4); x++){
                    plateau.turnPieces(p, false);
                }
                return;
                
            }
        }

    }
}