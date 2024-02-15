package tetris.gui;
import tetris.pieces.*;
import tetris.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class PlateauAdversaireGUI extends JPanel{

    private PlateauPuzzle plateau;
    private HashMap<String,Color> lettreCouleur;

    public PlateauAdversaireGUI(PlateauPuzzle plateau, HashMap<String,Color> lettreCouleur) {
        this.plateau = plateau;
        this.lettreCouleur = lettreCouleur;
    }

    @Override
    public void paintComponent(Graphics g){
        int[][] grille = this.plateau.exportMatrice();
        // On définie la largeur et la hauteur d'une case
        int largeurCase = this.getWidth()/grille[0].length;
        int hauteurCase = this.getHeight()/grille.length;
        for(int i=0; i< grille.length; i++){
            for(int j=0; j< grille[0].length; j++){
                g.setColor(Color.black);
                g.fillRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
                // On créer le contour des case en gris clair
                g.setColor(Color.lightGray);
                g.drawRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
            }
        }
        ArrayList<Piece> listePieces = this.plateau.getListePieces();
        for (Piece p : listePieces) {
            paintPiece(g,p);
        }
    }

    public void paintPiece(Graphics g,Piece p) {
        int[][] grille = this.plateau.exportMatrice();
        // On définie la largeur et la hauteur d'une case
        int largeurCase = this.getWidth()/grille[0].length;
        int hauteurCase = this.getHeight()/grille.length;
        String type = p.getType();
        int x = p.getPosition()[0];
        int y = p.getPosition()[1];
        for (int i = 0; i < p.getHauteur(); i++) {
            for (int j = 0; j < p.getLargeur(); j++) {
                if(p.getCase(i,j) == 1) {
                    g.setColor(this.lettreCouleur.get(type));
                    g.fillRect((y+j)*largeurCase, (x+i)*hauteurCase, largeurCase, hauteurCase);
                    // On créer le contour des case en gris clair
                    g.setColor(Color.lightGray);
                    g.drawRect((y+j)*largeurCase, (x+i)*hauteurCase, largeurCase, hauteurCase);
                }
            }
        }
    }
    
}
