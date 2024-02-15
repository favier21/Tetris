package tetris.gui;
import tetris.pieces.*;
import tetris.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.Math;

public class ReserveGUI extends JPanel implements MouseListener{

    protected PlateauGUI plateauGUI;
    protected PlateauPuzzle plateau;
    protected Piece piece;
    private HashMap<String,Color> lettreCouleur;

    public ReserveGUI(PlateauGUI plateauGUI,HashMap<String,Color> lettreCouleur) {
        this.plateauGUI = plateauGUI;
        this.plateau = plateauGUI.plateau;
        this.lettreCouleur = lettreCouleur;
        this.plateauGUI.setSelectPiece(piece);
    }

    public Piece getPiece() {
        return this.piece;
    }


    @Override
    public void paintComponent(Graphics g) {
        // Peindre la piece, dans une grille de 5x5
        // On définie la largeur et la hauteur d'une case
        int largeurCase = this.getWidth()/5;
        int hauteurCase = this.getHeight()/5;
        int x = Math.floorDiv(5, 2) - Math.floorDiv(this.piece.getHauteur(), 2);
        int y = Math.floorDiv(5, 2) - Math.floorDiv(this.piece.getLargeur(), 2);
        for(int i=0; i<this.piece.getHauteur(); i++){
            for(int j=0; j<this.piece.getLargeur(); j++){
                if (this.piece.getCase(i,j) == 1) {
                    g.setColor(this.lettreCouleur.get(this.piece.getType()));
                    g.fillRect((y+j)*largeurCase, (x+i)*hauteurCase, largeurCase, hauteurCase);
                    // On créer le contour des case en gris clair
                    g.setColor(Color.lightGray);
                    g.drawRect((y+j)*largeurCase, (x+i)*hauteurCase, largeurCase, hauteurCase);
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (this.piece!=this.plateauGUI.getSelectPiece() || this.plateauGUI.getSelectPiece() != null) {
            this.plateauGUI.setSelectPiece(this.piece);
        }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

}