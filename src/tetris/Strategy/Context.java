package tetris.Strategy;

import tetris.PlateauPuzzle;

public class Context {

    private Strategy strat;

    public Context(){
        this.strat = null;
    }

    public void setStrategy(Strategy newStrategy){
        this.strat = newStrategy;
    }

    public void executeStrategy(PlateauPuzzle plateau){
        this.strat.execute(plateau);
    }
}
