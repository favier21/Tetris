
package tetris.Strategy;

import java.util.ArrayList;
import java.util.Random;

import tetris.PlateauPuzzle;
import tetris.pieces.Piece;

/**
 * StrategyEmpile
 */
public class StrategyEmpile implements Strategy{

    protected PlateauPuzzle plateau;
    protected ArrayList<Piece> piecesDisponibles;
    protected Random random;

    public StrategyEmpile(){
        this.piecesDisponibles = null;
        this.random = new Random();
    }

public void reinitChoix(PlateauPuzzle plateau){
    this.piecesDisponibles = plateau.copyPieces();
}

public Piece renvoiPieceRandom(PlateauPuzzle plateau){
    if(this.piecesDisponibles == null){
        this.reinitChoix(plateau);
    }
    int taille = this.piecesDisponibles.size();
    if(taille == 0){
        this.reinitChoix(plateau);
        taille = this.piecesDisponibles.size();
    }
    int rand = this.random.nextInt(taille);
    Piece p = this.piecesDisponibles.get(rand);
    this.piecesDisponibles.remove(rand);
    
return p;
}

public void execute(PlateauPuzzle plateau){
        Piece p = this.renvoiPieceRandom(plateau);
        for(int i = 0; i < plateau.getHauteur(); i++){ // Parcourt en partant du coin en haut à gauche
            for(int j = 0; j < plateau.getLargeur(); j++){
                if(plateau.movePieces(p,i,j)){
                    if(this.random.nextBoolean()){ // L'IA va tourner les pièces de temps en temps
                        for(int x = 0; x < this.random.nextInt(4); x++){
                            // le plateau ne fait rien si la piece ne peut pas tourner
                            plateau.turnPieces(p, false);
                        }
                        
                    }
                    return;
                }
            }
        }

    }
}