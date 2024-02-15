package tetris;
import tetris.pieces.*;
import tetris.Strategy.Context;
import tetris.Strategy.StrategyEmpile;
import tetris.Strategy.StrategyRandom;
import tetris.gui.*;


public class Controleur {

    protected PlateauPuzzle plateauJ1;
    protected PlateauPuzzle plateauAdversaire;
    protected TetrisGUI tetrisGUI;
    protected PlateauGUI panelJ1;
    protected PlateauAdversaireGUI panelAdversaire;
    protected Context adversaire;
    private int nbCoupMax;
    private Boolean validation;
    

    public Controleur() {
        this.plateauJ1 = new PlateauPuzzle(10,20);
        this.plateauAdversaire = new PlateauPuzzle(10,20);
        this.tetrisGUI = new TetrisGUI(this.plateauJ1,this.plateauAdversaire,this);
        this.panelJ1 = this.tetrisGUI.getPanelJ1();
        this.panelAdversaire = this.tetrisGUI.getPanelJ2();
        this.nbCoupMax = 20;
        this.validation = false;
        updateSituation();

        /* 
        Lorsque l'on ajoute des pièces aux plateaux il faut créer deux fois la même pièce
        et ajouté une sur chaque plateau sinon lors d'une modification sur un plateau, l'autre aussi sera modifié
        
        Les pièces si dessous sont temporaires pour tester
        */ 
        Piece pieceI = new PieceBuilder().avecgrille("i").avecposition(1,1).build();
		Piece pieceL = new PieceBuilder().avecgrille("l").avecposition(1,2).build();
		Piece pieceT = new PieceBuilder().avecgrille("t").avecposition(5,5).build();
		Piece pieceU = new PieceBuilder().avecgrille("t").avecposition(15,3).build();
        this.plateauJ1.addPieces(pieceI);
		this.plateauJ1.addPieces(pieceL);
		this.plateauJ1.addPieces(pieceT);
		this.plateauJ1.addPieces(pieceU);

        Piece pieceI2 = new PieceBuilder().avecgrille("i").avecposition(1,1).build();
		Piece pieceL2 = new PieceBuilder().avecgrille("l").avecposition(1,2).build();
		Piece pieceT2 = new PieceBuilder().avecgrille("t").avecposition(5,5).build();
		Piece pieceU2 = new PieceBuilder().avecgrille("t").avecposition(15,3).build();
        this.plateauAdversaire.addPieces(pieceI2);
		this.plateauAdversaire.addPieces(pieceL2);
		this.plateauAdversaire.addPieces(pieceT2);
		this.plateauAdversaire.addPieces(pieceU2);
        this.adversaire = new Context();
        

        this.adversaire.setStrategy(new StrategyRandom());
    }

    /*
    Fonction qui change l'état de this.validation ce qui signifie que le joueur à cliqué sur le bouton terminé et que la partie est finit
    */
    public void changeStateValidation() {
        this.validation = !this.validation;
        endGame();
    }

    /*
    Fonction qui effectue soit un placement soit un mouvement dans le plateau du joueur 1 en fontion
    de la pièce entrée en argument et actualise l'affichage si nécéssaire
    */
    public void actionPiece(Piece p,int x,int y) {
        Boolean didSomething = false;
        if (this.plateauJ1.movePieces(p,x,y)) {
            this.panelJ1.paintComponent(this.panelJ1.getGraphics());
            didSomething = true;
        }
        if (didSomething == true) {
            this.nbCoupMax--;
            actionPieceAdversaire();
        }
    }

    /* 
    Fonction qui effectue une action de l'ordinateur actualise la vue et vérifie si la partie est finit
    */
    public void actionPieceAdversaire() {
        this.adversaire.executeStrategy(plateauAdversaire);
        this.panelAdversaire.paintComponent(this.panelAdversaire.getGraphics());
        updateSituation();
        endGame();
        
    }

    /*
    Fonction qui effectue une rotation sur une pièce du plateau du joueur 1 et actualise l'affichage si nécéssaire
    */
    public void rotatePiece(Piece p,Boolean rotation) {
        Boolean didSomething = false;
        if (this.plateauJ1.turnPieces(p,rotation)) {
            this.panelJ1.paintComponent(this.panelJ1.getGraphics());
            didSomething = true;
        }
        if (didSomething == true) {
            this.nbCoupMax--;
            actionPieceAdversaire();
        }
    }

    /* 
    Fonction qui vérifie si la partie est terminé
    */
    public void endGame() {
        if (this.validation || this.nbCoupMax <= 0) { // Le joueur a cliqué sur le bouton validation ou a dépassé le nombre de coup max, la partie est finit
            int pointsJ1 = plateauJ1.taillePlusPetitRect();
            int pointsAdversaire = plateauAdversaire.taillePlusPetitRect();
            String situation = "Vous avez "+pointsJ1+" et votre adversaire a "+pointsAdversaire+".";
            if ( pointsJ1 < pointsAdversaire ) {
                // le joueur à gagné 
                situation += "Vous avez gagné. Félicitations !";
            }
            else if (pointsJ1 == pointsAdversaire) {
                // égalité
                situation += "Vous êtes à égalité. Dommage.";
            }
            else {
                // l'ordinateur à gagné
                situation += "Vous avez perdu...";
            }
            // On affiche la situation finale
            this.tetrisGUI.printSituation(situation);
            // On affiche le bouton quitter car c'est enervant de devoir appuyer sur la croix à la longue
            this.tetrisGUI.showExit();
            // On retire la possibilité de cliqué sur la vue une fois la partie terminé  
            this.tetrisGUI.removeEvent();
        }
        // Si la partie n'est pas terminé ou ne fait rien
    }

    /* 
    Fonction qui actualise l'affichage de la situation actuel de la vue
    */
    public void updateSituation() {
        this.tetrisGUI.printSituation("Il reste vous "+this.nbCoupMax+" actions!");
    }
}