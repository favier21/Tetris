package tetris;
//import java.lang.Thread;
import java.util.*;
import tetris.pieces.*;

public class PlateauPuzzle {
    
    protected int largeur;
    protected int hauteur;
    protected ArrayList<Piece> listePieces;

    public PlateauPuzzle(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.listePieces = new ArrayList<Piece>();
    }

    public int getLargeur() {
        return this.largeur;
    }

    public int getHauteur() {
        return this.hauteur;
    }

    public ArrayList<Piece> getListePieces() {
        return this.listePieces;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public void setListePieces(ArrayList<Piece> listePieces) {
        this.listePieces = listePieces;
    }
    
    /*
    Ajoute une piece au plateau si possible
    - Vérifie qu'il n'y a pas de colision
    - Ajoute la pieces p à this.listePieces
    @return true si la pièce a bien été ajouté au plateau
    @return false si la pièces n'a pas été ajouté au plateau
    */
    public boolean addPieces(Piece p) {
        //on verifie si p peut etre place dans le plateau
        if(p.getPosition()[0] + p.getHauteur() >= this.hauteur || p.getPosition()[0] < 0|| p.getPosition()[1]<0 || p.getPosition()[1] + p.getLargeur() >= this.largeur)
            return false;
        // On vérifie si la p n'entre en colision avec aucune pièces du plateau
        for (Piece p1 : this.listePieces) {
            if (checkColision(p,p1)) { // Si il y a une colision
                return false; // on retourne false car on ne peut pas ajouter la pièce
            }
        }
        // Si on passe la boucle, il n'y donc aucune colision 
        // On ajoute la pièce au plateau et on retourne true
        this.listePieces.add(p);
        return true;
    }

    
    /*
    Vérifie si il y a une colision entre deux pièces, p1 et p2
    @return true si il y a une colision
    @return false si il y a pas de colision
    */
    public boolean checkColision(Piece p1,Piece p2) {
        // On vérifie si il n'y a pas d'intersection entre les deux retangles qui englobent les deux pièces
        int[][] intersection = intersectionRectanglePiece(p1,p2);
        if (intersection == null) {
            return false;
        }
        // On vérifie si aucunes des cases deux l'intersections et occupés par deux pièces
        if (!checkCaseIntersectionPiece(intersection,p1,p2)) {
            return false;
        }
        // Il y a une collision
        return true;
    }

    /*
    Vérifie si il y a une intersection entre deux pièces
    @return true si il y a une intersection
    @return false si il n'y a pas d'intersection
    */
    public boolean checkIntersection(Piece p1, Piece p2) {
        // Les 8 déclarations servent en réalité à rien, mais c'est plus clair!
        int xP1 = p1.getPosition()[0];
        int yP1 =  p1.getPosition()[1];
        int xP2 = p2.getPosition()[0];
        int yP2 =  p2.getPosition()[1];
        int largeurP1 = p1.getLargeur();
        int hauteurP1 = p1.getHauteur();
        int largeurP2 = p2.getLargeur();
        int hauteurP2 = p2.getHauteur();
        //System.out.println("P1 x y ," + xP1 + " " +yP1);
        //System.out.println("P2 x y ," + xP2 + " " +yP2);
        //System.out.println("P1 largeur hauteur , " + largeurP1 + " " +hauteurP1);
        //System.out.println("P2 largeur hauteur , " + largeurP2 + " " +hauteurP2);
        if ((xP2 <= xP1 && xP1 <= xP2 + hauteurP2) || (xP1 <= xP2 && xP2 <= xP1 + hauteurP1)) { // On a une intersection sur la hauteur
            if ((yP2 <= yP1 && yP1 <= yP2 + largeurP2) || (yP1 <= yP2 && yP2 <= yP1 + largeurP1)) { // On a une intersection sur la largeur
                return true;
            }
            return false;
        }
        return false;
    }

    /*
    Renvoie l'intersection entre les deux rectangles qui englobent deux pièces p1 et p2
    @return Si il y a une intersection la renvoie
    @return Si il n'y a pas d'intersection renvoie null
    */
    public int[][] intersectionRectanglePiece(Piece p1, Piece p2) {
        if (!checkIntersection(p1, p2)) {
            return null;
        }
        int xDebutIntersection = Math.max(p1.getPosition()[0],p2.getPosition()[0]);
        int yDebutIntersection = Math.max(p1.getPosition()[1],p2.getPosition()[1]);
        int xFinIntersection = Math.min(p1.getPosition()[0] + p1.getHauteur()-1,p2.getPosition()[0] + p2.getHauteur()-1);
        int yFinIntersection = Math.min(p1.getPosition()[1] + p1.getLargeur()-1,p2.getPosition()[1] + p2.getLargeur()-1);
        int[] debutIntersection = new int[] {xDebutIntersection,yDebutIntersection};
        int[] finIntersection = new int[] {xFinIntersection,yFinIntersection};
        int[][] res = new int[][] {debutIntersection,finIntersection};
        return res;
    }

    /*
    Vérifie si les cases d'une intersection sont occupés par une ou deux pièces, p1 et p2
    @return true si il y a au moins une cases occupés par deux pièces
    @return false si aucunes des cases n'est occupés par deux pièces
    */
    public boolean checkCaseIntersectionPiece(int[][] intersection, Piece p1, Piece p2) {
        //System.out.println(intersection[0][0]+","+intersection[0][1]);
        //System.out.println(intersection[1][0]+","+intersection[1][1]);
        for (int i = intersection[0][0];i <= intersection[1][0];i++) {
            for (int j = intersection[0][1];j<= intersection[1][1];j++) {
                if (!p1.estVide(i-p1.getPosition()[0],j-p1.getPosition()[1]) && !p2.estVide(i-p2.getPosition()[0],j-p2.getPosition()[1])) {
                    // il y a une cases occupés par deux pièces, on renvoie true
                    return true;
                }
            }
        }
        // aucunes cases de l'intersection des pièces n'est occupé par deux pièces, on renvoie false
        return false;
    }
    /*
    Déplace la pièces dans le plateau, la pièces p1 et déplacer au coordonnées x,y si possible
    @return true si le déplacement a été effectué
    @return false si le déplacement n'a pas pus être effectué
    */
    public boolean movePieces(Piece p, int x, int y) {
        if(x+p.getHauteur() >= this.hauteur || x < 0|| y<0 || y+p.getLargeur() >= this.largeur)
            return false;
        int[] position = p.getPosition();
        p.setPosition(x,y);
        for (Piece p1 : this.listePieces) {
            if(p != p1){
                if (checkColision(p,p1)) { // Si il y a une colision
                    p.setPosition(position[0],position[1]);
                    return false; // on retourne false car on ne peut pas bouger la pièce
                }
            }
        }
        return true;
    }
    
    /*
    Tourne la pièces de 90° dans le plateau, la pièces p1 et tourner dans la direction si possible
    direction false : rotation sens antihoraire
    direction true : rotation sens horaire
    @return true si la rotation a été effectué
    @return false si la rotation n'a pas put être effectué
    */
    public boolean turnPieces(Piece p, boolean direction) {
        if(p.getPosition()[0] + p.getLargeur() >= this.hauteur || p.getPosition()[0] < 0|| p.getPosition()[1]<0 || p.getPosition()[1] + p.getHauteur() >= this.largeur)
            return false;
        p.rotation(direction);
        for (Piece p1 : this.listePieces) {
            if(p != p1){
                if (checkColision(p,p1)) { // Si il y a une colision
                    p.rotation(!direction);
                    return false; // on retourne false car on ne peut pas tourner la pièce
                }
            }
        }
        return true; // la rotation a été effectuée, on retourne true
    }
            
    /*
    Affiche l'état actuel de plateau de jeu sous forme de grille
    */
    public int[][] exportMatrice() {
        //default value of 0 for arrays of integral types is guaranteed by the language spec
        int[][] grid = new int[this.hauteur][this.largeur];

        for (Piece p : this.listePieces) {
            int x = p.getPosition()[0];
            int y = p.getPosition()[1];

            for (int i = 0; i < p.getHauteur(); i++) {
                for (int j = 0; j < p.getLargeur(); j++) {
                    if(p.getCase(i,j) == 1)
                        grid[x + i][y + j] = 1;
                }
            }
        }

        return grid;
    }

    public int[][] grilleVide(){
        /**
         * Grille remplie de 0, pouvant servir pour calculer les rectangles, ainsi que pour l'IA
         */
        int[][] grille = new int[this.hauteur][this.largeur];
        for(int i = 0; i < this.hauteur; i++){
            for(int j = 0; j < this.largeur; j++){
                grille[i][j] = 0;
            }
        }
        return grille;
    }


    /**Renvoie la ligne à l'id selectionnée
     * @requires un entier positif, inferieur à la hauteur de la grille
     * @return une liste d'entiers
     */
    public int[] returnLigne(int ligne){
        int[][] grille = this.exportMatrice();
        return grille[ligne];
    }

    public int[] returnColumn(int column){
        int[] colonne = new int[this.hauteur]; 
        int[][] grille = this.exportMatrice();
        for(int i = 0; i < this.hauteur; i++){
            colonne[i] = grille[i][column]; // Parcourt case par case de la colonne voulue
        }
        return colonne;
    }

    /**
     * CETTE METHODE SERT JUSTE À AFFICHER LA GRILLE VIDE POUR LES TESTS DE PLUSGRANDRECT, LORSQU'ELLE
     * MaRCHERA BIEN ON N'EN AURA NORMALEMENT PLUS BESOIN   
     */
    public void afficheGrille(int[][] grille){
        final String[] CASES = {" ", "#"};
        for(int i = 0; i < this.hauteur; i++){
            String chaine = "";
            for(int j = 0; j < this.largeur; j++){
                chaine += "."+CASES[grille[i][j]];
            }
            System.out.println(chaine);
        }
    }

    public int plusGrandRect(){
        /**
         * Calcule l'aire du plus grand rectangle présent sur la grille
         * 
         * Nous faisons une copie vide de la grille, et recherchons dans la matrice une ligne de 1. Il prend la plus grande
         * ligne non mise dans la copie de la grille, et descend pour compter si il y a la même chose  juste en dessous
         */
        int[][] grille = this.exportMatrice();
        int[][] vide = this.grilleVide();
        int taille = 0; // Taille du rectangle
        int tmp = 0; // Utile pour la comparaison
        int debutX = -1; // Début x du rectangle temporaire
        int debutY = -1; // Debut y du rectangle temporaire
        int finX = -1; // fin x du rectangle temporaire
        while (!Arrays.deepEquals(grille,vide)) { // 1
            
            System.out.println("---------Boucle 1 infinie---------");
            afficheGrille(grille);
            System.out.println("#######################");
            afficheGrille(vide);
            for(int i = 0; i < this.hauteur; i++){ // 2
                if(debutX != -1){ // Si c'est un rectangle en cours. 3
                    if(grille[i][debutX] == 0){ // Fin d'un rectangle. 4
                        //System.out.println("En bas d'un rectangle");
                        int petitl = (i - debutY);
                        int grandL = (finX - debutX) +1;
                        tmp = petitl * grandL;
                        //System.out.println("l : "+petitl+", L : "+grandL);
                        //System.out.println("l * L = "+tmp);
                        // On update grille vide pour éviter de parcourir deux fois le même rectangle
                        for(int mi = debutY; mi < i; mi++){ // 5
                            for(int mj = debutX; mj <= finX; mj++){ // Rien à voir avec Micheal Jackson. 6
                                vide[mi][mj] = 1;
                            } // 6
                        } // 5
                        if(tmp > taille){ // 5
                            taille = tmp;
                        } // 5
                        debutX = -1;
                        debutY = -1;
                    }else{ // Si deux rectangles se croisent 4
                        boolean croisement = false;
                        int j = debutX;
                        while(!croisement && j < finX){
                            croisement = grille[i][j] == 0;
                            j++;
                        }if(croisement){ // J'ai copié collé ce qu'il y a plus haut. Sûrement mieux à faire, mais je n'ai pas trouvé pour l'instant
                            //System.out.println("Deux rectangles croisés à la ligne " + i);
                            int petitl = (i - debutY);
                            int grandL = (finX - debutX) +1;
                            tmp = petitl * grandL;
                            //System.out.println("l : "+petitl+", L : "+grandL);
                            //System.out.println("l * L = "+tmp);
                            // On update grille vide pour éviter de parcourir deux fois le même rectangle
                            for(int mi = debutY; mi < i; mi++){ // 5
                                for(int mj = debutX; mj <= finX; mj++){ // Rien à voir avec Micheal Jackson. 6
                                    vide[mi][mj] = 1;
                                } // 6
                            } // 5
                            if(tmp > taille){ // 5
                                taille = tmp;
                            } // 5
                            debutX = -1;
                            debutY = -1;
                        }
                    } 
                } // 3
                
                else{ // 3
                    // Nous n'avons pas encore de rectangle
                    int j = 0;
                    while(j < this.largeur-1){ // S'il n'y a pas de nouveaux rectangles. 4
                        j++;
                        if(grille[i][j] == 1){ // 5
                            if(debutX == -1){ // Une case peut-être dans plusieurs rectangles. 6
                                debutX = j;
                                finX = j; // On sait jamais si on a un rectangle de 1 x 1
                                debutY = i;
                            } // 6
                            if(((j + 1 < this.largeur && grille[i][j + 1] == 0) ||
                                 j + 1 == this.largeur) && vide[i][j] == 0){ //Fin d'un rectangle pas encore parcouru. 6
                                    finX = j;
                                    j = this.largeur;    
                                } // 6
                                else if (((j + 1 < this.largeur && grille[i][j + 1] == 0) ||
                                 j + 1 == this.largeur) && this.ligneDejaEnregistree(vide,i,debutX,j)){ // rectangle déjà là
                                    debutX = -1;
                                 }
                        } // 5
                    } // 4
                } // 3
            } // 2
            
        } // 1
       /* 
        System.out.println("################FIN :####################");
        afficheGrille(grille);
        System.out.println("#######################");
        afficheGrille(vide);*/
        return taille;
    }

    public boolean ligneDejaEnregistree(int[][] grilleVide, int y, int debutX, int finX){
        for(int i = 0; i < finX; i++){
            if(grilleVide[y][i] == 0){
                return false;
            }
        }
        return true;
    }

    public int taillePlusPetitRect() {
        int[][] matrice = this.exportMatrice();
        int hauteur = matrice.length;
        int largeur = matrice[0].length;

        int minX = largeur;  // Initialisation avec une valeur maximale
        int minY = hauteur;  // Initialisation avec une valeur maximale
        int maxX = -1;       // Initialisation avec une valeur minimale
        int maxY = -1;       // Initialisation avec une valeur minimale

        // Parcours de la matrice pour trouver les coordonnées du rectangle englobant tous les 1
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (matrice[i][j] == 1) {
                    // Mise à jour des coordonnées
                    minX = Math.min(minX, j);
                    minY = Math.min(minY, i);
                    maxX = Math.max(maxX, j);
                    maxY = Math.max(maxY, i);
                }
            }
        }
        
        // Vérification s'il y a des 1 dans la matrice (sinon, renvoie null)
        if (minX == largeur && minY == hauteur && maxX == -1 && maxY == -1) {
            return 0;  // Pas de 1 dans la matrice
        } else {
            // Renvoie les coordonnées du rectangle englobant tous les 1
            int result = (maxX-minX+1)*(maxY-minY+1);
            return result;
        }
    }
    public ArrayList<Piece> copyPieces(){
        return (ArrayList<Piece>) this.listePieces.clone();
    }

    @Override
    public String toString() {
        int[][]m = this.exportMatrice();
        String str = "";
        for (int[] ligne : m) {
            for (int elt : ligne) {
                if(elt == 0)
                    str += " . ";
                else
                    str += " # ";
            }
            str += "\n";
        }
        return str;
    }
}
