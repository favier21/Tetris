package tetris.gui;
import tetris.pieces.*;
import tetris.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class SelectPieceGUI extends JPanel{
    
    private Piece selectPiece;
    private HashMap<String,Color> lettreCouleur;

    public SelectPieceGUI(HashMap<String,Color> lettreCouleur) {
        this.lettreCouleur = lettreCouleur;
        this.selectPiece = null;
        this.setBackground(Color.black);
    }

    public void setSelectPiece(Piece p) {
        this.selectPiece = p;
    }

    @Override
    public void paintComponent(Graphics g) {
        // On peint la piece dans une grille de 5x5
        // On définie la largeur et la hauteur d'une case
        int largeurCase = this.getWidth()/5;
        int hauteurCase = this.getHeight()/5;
        // On peint d'abord la grille
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                g.setColor(Color.black);
                g.fillRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
                // On créer le contour des case en gris clair
                g.setColor(Color.lightGray);
                g.drawRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
            }
        }
        // Si il y a une pièce de sélectionné
        if (this.selectPiece != null) {
            // on définit ou placé la pièce dans la grille pour quelle soit centré
            int x = Math.floorDiv(5, 2) - Math.floorDiv(this.selectPiece.getHauteur(), 2);
            int y = Math.floorDiv(5, 2) - Math.floorDiv(this.selectPiece.getLargeur(), 2);
            paintPiece(g, x, y);
        }
    }

    /*
    Fonction qui prend en entrée une pièce et la peint en commençant au coordonnées (x,y)
    */
    public void paintPiece(Graphics g,int x, int y) {
        // On définie la largeur et la hauteur d'une case
        int largeurCase = this.getWidth()/5;
        int hauteurCase = this.getHeight()/5;
        for (int i = 0; i < this.selectPiece.getHauteur(); i++) {
            for (int j = 0; j < this.selectPiece.getLargeur(); j++) {
                if (this.selectPiece.getCase(i,j) == 1) {
                    g.setColor(this.lettreCouleur.get(this.selectPiece.getType()));
                    g.fillRect((y+j)*largeurCase, (x+i)*hauteurCase, largeurCase, hauteurCase);
                    // On créer le contour des case en gris clair
                    g.setColor(Color.lightGray);
                    g.drawRect((y+j)*largeurCase, (x+i)*hauteurCase, largeurCase, hauteurCase);
                }
            }
        }
    }
}