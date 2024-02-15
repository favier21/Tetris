package tetris.gui;
import tetris.pieces.*;
import tetris.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class PlateauGUI extends JPanel implements MouseListener{

    protected PlateauPuzzle plateau;
    protected SelectPieceGUI panelSelectPiece;
    private HashMap<String,Color> lettreCouleur;
    private Piece selectPiece;
    private Controleur control;

    public PlateauGUI(PlateauPuzzle plateau,SelectPieceGUI panelSelectPiece, Controleur control, HashMap<String,Color> lettreCouleur) {
        this.plateau = plateau;
        this.panelSelectPiece = panelSelectPiece;
        this.control = control;
        this.lettreCouleur = lettreCouleur;
        this.selectPiece = null;
        this.addMouseListener(this);
    }

    public void setSelectPiece(Piece p) {
        this.selectPiece = p;
    }

    public Piece getSelectPiece() {
        return this.selectPiece;
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

    /*
    Fonction qui prend en entrée une pièce et la peint sur le plateau
    */
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
    
    /*
    Fonction qui prend en entrée des coordonnées de la grille, et renvoie la pièce qui se situe aux coordonnées
    @return la pièce qui correspond aux coordonnées
    @return null si il y a pas de pièce aux coordonnées
    */
    public Piece coordGrilleToPiece(int x,int y) {
        for (Piece p : this.plateau.getListePieces()) {
            if (x >= p.getPosition()[0] && x < p.getPosition()[0] + p.getHauteur()) {
                if (y >= p.getPosition()[1] && y < p.getPosition()[1] + p.getLargeur()) {
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int[][] grille = this.plateau.exportMatrice();
        int largeurCase = this.getWidth()/grille[0].length;
        int hauteurCase = this.getHeight()/grille.length;
        // On récupère les coordonnées de la case sur la quelle on a cliqué
        int yGrille = e.getX() / largeurCase; 
        int xGrille = e.getY() / hauteurCase;
        //System.out.println("Je clique la : "+ e.getX() + " "+e.getY());
        //System.out.println("Je clique sur la case : "+ xGrille + " "+yGrille);
        if (this.selectPiece == null) {
            this.selectPiece = coordGrilleToPiece(xGrille, yGrille);
            this.panelSelectPiece.setSelectPiece(this.selectPiece);
            this.panelSelectPiece.paintComponent(this.panelSelectPiece.getGraphics());
        }
        else if (this.selectPiece != null) {
            // Clique droit pour une rotation dans le sens horaire 
            if (e.getButton() == 2) {
                this.control.rotatePiece(this.selectPiece,true);
                this.panelSelectPiece.paintComponent(this.panelSelectPiece.getGraphics());
            }
            //clique molette pour une rotation antihoraire
            else if (e.getButton() == 3) {
                this.control.rotatePiece(this.selectPiece,false);
                this.panelSelectPiece.paintComponent(this.panelSelectPiece.getGraphics());
            }
            else {
                this.control.actionPiece(this.selectPiece,xGrille,yGrille); 
                this.selectPiece = null;
                this.panelSelectPiece.setSelectPiece(null);
                this.panelSelectPiece.paintComponent(this.panelSelectPiece.getGraphics());
            }  
        }
        //System.out.println(this.selectPiece);
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}