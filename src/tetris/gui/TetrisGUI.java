package tetris.gui;
import tetris.pieces.*;
import tetris.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TetrisGUI implements ActionListener {

    protected JFrame frame;
    protected PlateauPuzzle plateauJ1;
    protected PlateauPuzzle plateauJ2;
    protected PlateauGUI panelJ1;
    protected SelectPieceGUI panelSelectPiece;
    protected PlateauAdversaireGUI panelJ2;
    protected JLabel situation;
    protected JButton validationButton;
    protected JButton quitterButton;
    private Controleur control;

    public TetrisGUI(PlateauPuzzle plateauJ1,PlateauPuzzle plateauJ2, Controleur control) {
        HashMap<String,Color> lettreCouleur = createDictLetterColor();
        this.plateauJ1 = plateauJ1;
        this.plateauJ2 = plateauJ2;
        this.control = control;

        // Frame
        this.frame = new JFrame("Tetris");
        this.frame.setSize(1400,1000);
        this.frame.setLayout(new FlowLayout());
        this.frame.getContentPane().setBackground(Color.darkGray);
        this.frame.setLayout(new FlowLayout(0,20,20));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel pièce sélectionné
        this.panelSelectPiece = new SelectPieceGUI(lettreCouleur);
        this.panelSelectPiece.setPreferredSize(new Dimension(this.frame.getWidth()/5,this.frame.getHeight()/4));

        // Panel Joueur 1
        this.panelJ1 = new PlateauGUI(this.plateauJ1,this.panelSelectPiece,this.control,lettreCouleur);
        this.panelJ1.setPreferredSize(new Dimension(this.frame.getWidth()/3,this.frame.getHeight()/2)); 
        this.frame.add(this.panelJ1);
        this.frame.add(panelSelectPiece);

        // Panel Joueur 2
        this.panelJ2 = new PlateauAdversaireGUI(this.plateauJ2,lettreCouleur);
        this.panelJ2.setPreferredSize(new Dimension(this.frame.getWidth()/3,this.frame.getHeight()/2));
        this.frame.add(this.panelJ2);

        // Label situation actuel
        this.situation = new JLabel("",SwingConstants.CENTER);
        this.situation .setPreferredSize(new Dimension(this.frame.getWidth()/3,100));
        this.situation .setForeground(Color.black);
        this.frame.add(this.situation);

        // Bouton validation
        this.validationButton = new JButton("Terminé");
        this.validationButton.setPreferredSize(new Dimension(this.frame.getWidth()/3,100));
        this.validationButton.addActionListener(this);
        this.frame.add(this.validationButton);

        // Bouton Quitter
        this.quitterButton = new JButton("Quitter");
        this.quitterButton.setPreferredSize(new Dimension(this.frame.getWidth()/3,100));
        this.quitterButton.addActionListener(this);

        this.frame.setVisible(true);

    }

    public void showExit(){
        this.frame.add(this.quitterButton);
    }

    public PlateauGUI getPanelJ1() {
        return this.panelJ1;
    }

    public PlateauAdversaireGUI getPanelJ2() {
        return this.panelJ2;
    }

    public SelectPieceGUI getPanelSelectPiece() {
        return this.panelSelectPiece;
    }

    /*
    Fonction qui affiche un texte dans un Jlabel, elle sert pour afficher la situation du jeu au joueur
    */
    public void printSituation(String text) {
        this.situation.setText(text);
        this.situation .updateUI();
    }

    /*
    Fonction qui supprime tous les événements de clique sur la vue
    */
    public void removeEvent() {
        this.validationButton.removeActionListener(this);
        this.panelJ1.removeMouseListener(this.panelJ1);
    }

    /*
    Fonction qui créé un dictionnaire, en associant une lettre au format String et une Couleur au format Color
    Pour personnaliser ou ajouter les couleurs de chaque type pièces il faut changer les valeurs dans cette fonction 
    */
    public HashMap<String,Color> createDictLetterColor() {
        HashMap<String,Color> res = new HashMap<String,Color>();
        res.put("i",Color.cyan);
        res.put("j",Color.pink);
        res.put("t",Color.magenta);
        res.put("l",Color.orange);
        res.put("o",Color.yellow);
        res.put("s",Color.red);
        res.put("z",Color.green);
        res.put("u",Color.blue);
        return res;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == this.validationButton.getText()) {
            this.control.changeStateValidation();
        }
        else if (e.getActionCommand() == this.quitterButton.getText()) {
            System.exit(0);
        }
    }
}