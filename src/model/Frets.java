package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Une frets correspond au manche d'une guitare et possède un tuning (accordage) de base. 
 * A partir de ce tuning on peut situer la position des accords sur le manche.
 * @author Yann LERJEN
 */
public class Frets {

    private int nbFrets;
    private Note[][] manche;
    private Note[] tuning;
    private Accord accord;
    private ArrayList<int[]> positionsAccord;

    /**
     * Permet d'avoir les frets de guitare avec un tuning de base passé en paramêtre.
     * @param cordes est un tableau de notes. Le plus petit index correspond à la plus petite corde
     * @param accord est l'accord que vous voulez placer sur le manche.
     */
    public Frets(Note[] cordes, Accord accord) {
        if (cordes != null && cordes.length > 0 && accord != null) {
            this.tuning = cordes.clone();
            this.manche = new Note[cordes.length][24];
            this.accord = accord;
            this.nbFrets = 24;
            this.positionsAccord = this.chooseChord();
        } else {
            throw new RuntimeException("Le tuning et l'accord de base doivent être spécifiés !");
        }
    }

    /**
     * Permet d'avoir les frets de guitare avec le tuning standard : E,A,D,G,B,E
     * et un accord de C basique
     */
    public Frets() {
        this(new Note[]{Note.E, Note.A, Note.D, Note.G, Note.B, Note.E}, new Accord(Note.C));
    }

    /**
     * Permet d'avoir les frets de guitare avec le tuning standard : E,A,D,G,B,E
     * et un accord passé en paramêtre
     * @param definedChord est un accord défini pour la construction des positions sur le manche
     */
    public Frets(Accord definedChord) {
        this(new Note[]{Note.E, Note.A, Note.D, Note.G, Note.B, Note.E}, definedChord);
    }

    /**
     * Fonction permettant de changer l'accord défini sur le manche de la guitare.
     * @param accord est le nouvel accord à définir sur le manche
     */
    public void setAccord(Accord accord) {
        this.manche = new Note[6][24];
        this.accord = accord;
        this.positionsAccord = this.chooseChord();
    }
    
    /**
     * Permet de récupérer l'accordage de la guitare
     * @return un tableau de note qui correspond à l'accordage de chaque cordes
     */
    public Note[] getTuning(){
        return this.tuning.clone();
    }

    private void findPositions() {
        if (this.accord != null && this.tuning != null) {
            Collection<Note> lesNotes = this.accord.rendNotes().values();

            for (int idxCorde = 0; idxCorde < this.tuning.length; idxCorde++) {
                for (int idxFret = 0; idxFret < this.nbFrets; idxFret++) {
                    Note noteDeCase = this.tuning[idxCorde].rendNoteTransposee(idxFret);
                    if (this.accord.rendNotes().containsValue(noteDeCase)) {
                        this.manche[idxCorde][idxFret] = noteDeCase;
                    }
                }
            }
            System.out.println(this.toString());
        }
    }

    private ArrayList<int[]> chooseChord() {
        //initialiser la liste des accords possibles et le tableau de position
        ArrayList<int[]> lesAccordsPossibles = new ArrayList<>();

        //on recherche les notes sur le manche
        this.findPositions();

        //On récupère l'accord de base ouvert s'il existe en non barré
        int[] accordOuvertChoisi = {-1, -1, -1, -1, -1, -1};
        boolean toniquePassee=false;
        for (int idxCorde = 0; idxCorde < 6; idxCorde++) {
            for (int idxFret = 0; idxFret < 4; idxFret++) {
                if (this.manche[idxCorde][idxFret] != null) {
                    if(this.manche[idxCorde][idxFret]==this.accord.getTonique())
                        toniquePassee=true;
                    if(toniquePassee)
                        accordOuvertChoisi[idxCorde] = idxFret;
                    break;
                }
            }
        }
        if(toniquePassee)
            lesAccordsPossibles.add(accordOuvertChoisi);

        //On récupère les accords barrés

        //Pour chaque corde de référence de l'accord (corde 1 à 3)
        for (int idxCordeRef = 0; idxCordeRef < 4; idxCordeRef++) {
            //Pour chaque fret de la corde de référence
            for (int idxFretRef = 0; idxFretRef < this.nbFrets; idxFretRef++) {
                //si la note de la fret vaut la tonique de l'accord
                if (this.manche[idxCordeRef][idxFretRef] == this.accord.getTonique()) {

                    int[] accordChoisi = {-1, -1, -1, -1, -1, -1};
                    //on prend toutes les cordes
                    for (int idxCordeAccord = idxCordeRef; idxCordeAccord < 6; idxCordeAccord++) {
                        //sur une largeur de 4 frets
                        for (int idxFretAccord = idxFretRef; idxFretAccord < idxFretRef + 4; idxFretAccord++) {
                            //Si une note de l'accord est sur ce fret, on le prend pour 
                            if (idxFretAccord < this.nbFrets && this.manche[idxCordeAccord][idxFretAccord] != null) {                                
                                accordChoisi[idxCordeAccord] = idxFretAccord;
                                break;
                            }
                        }
                    }
                    if(this.accord.getBass()!=null){
                        
                    }
                    lesAccordsPossibles.add(accordChoisi);
                }
            }
        }
        return lesAccordsPossibles;
    }

    private int getRefFretFromChoosenChord(int[] accord) {
        int refFret = 1000;
        if (accord != null && accord.length > 0) {
            //On recherche la position la plus basse des 6 cordes
            for (int idxCorde = 0; idxCorde < accord.length; idxCorde++) {
                if (accord[idxCorde] < refFret && accord[idxCorde]>=0) {
                    refFret = accord[idxCorde];
                }
            }
        }
        return (refFret == 1000) ? -1 : refFret;
    }

    /**
     * Méthode retournant une string formatée pour l'affichage, de l'état du
     * manche de guitare (tuning, note avec position)
     * @return une string formatée de l'état du manche
     */
    public String toString() {
        String tune = "\nTuning : ";
        for (Note notes : this.tuning) {
            tune += notes.toString() + ";";
        }
        tune += "\nAccord: "+this.accord;

        for (int idxCorde = this.tuning.length; idxCorde >= 0; idxCorde--) {
            tune += "\n";
            for (int idxFret = 0; idxFret < this.nbFrets; idxFret++) {
                if (idxCorde != this.tuning.length) {
                    if (idxFret == 1) {
                        tune += "|";
                    }
                    Note laNote = this.manche[idxCorde][idxFret];
                    if (laNote != null) {
                        String nomNote = "-" + laNote.toString();
                        while (nomNote.length() < 3) {
                            nomNote += "-";
                        }
                        tune += nomNote;
                    } else {
                        tune += "---";
                    }

                    tune += "|";
                } else {
                    String fretId = " " + idxFret + " ";
                    while (fretId.length() < 4) {
                        fretId += " ";
                    }
                    tune += fretId;
                }
            }
        }

        return tune;
    }
    
    /**
     * Permet d'exporter l'image png de l'accord en cours dans le dossier temporaire de l'OS.
     * L'image aura comme nom par défaut "chordImg"
     * @return le chemin vers l'image enregistrée
     */
    public String exportImg(){
        return this.exportImg(null);
    }

    /**
     * Permet d'exporter l'image png de l'accord en cours dans le dossier temporaire de l'OS.
     * @param fileName est le nom sans l'extension qu'aura l'image créé sur le disque. Si
     * fileName vaut null ou est vide, le nom par défaut "chordImg" sera choisi.
     * @return le chemin vers l'image enregistrée
     */
    public String exportImg(String fileName) {
        String filePath="";
        if(fileName==null || fileName.length()<=0)
            fileName="chordImg";
        BufferedImage bi=this.getImg();
        try {
            filePath=System.getProperty("java.io.tmpdir")+fileName+".png";
            //System.out.println(filePath);
            ImageIO.write(bi, "png", new File(filePath));
        } catch (Exception e) {
//            e.printStackTrace();
            filePath=null;
        }
        return filePath;
    }
    /**
     * Permet de retourner l'image dessinée mais en mémoire
     * @return l'image dessinée en mémoire
     */
    public BufferedImage getImg() {
        ZoneDessin zoneDessin = new ZoneDessin(50, 68);
        BufferedImage bi = new BufferedImage(zoneDessin.getWidth(), zoneDessin.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        zoneDessin.paint(g);
        g.dispose();
        zoneDessin.dispose();
        return bi;
    }

    private class ZoneDessin extends JFrame {

        private FretDessinable dessin;

        private ZoneDessin(int tailleX, int tailleY) {
            super();
            Point2Dim orig = new Point2Dim(10, 30);
            this.dessin = new FretDessinable(orig, tailleX, tailleY);
            this.dessin.setBackground(Color.WHITE);
            this.dessin.setPreferredSize(new Dimension((int) (tailleX + (orig.x * 2)), (int) (tailleY + (orig.y +10))));            
            this.setUndecorated(true);
//            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.add(this.dessin);
            this.pack();
            this.setVisible(true);
            this.dessin.dessineToi(this.dessin.getGraphics());
        }

        private class FretDessinable extends JPanel {

            private Point2Dim origine;
            private int largeur;
            private int hauteur;
            private Point2Dim[] cordesPosition;
            private Point2Dim[] fretsPosition;

            private FretDessinable(Point2Dim origine, int width, int height) {
                super();
                if (origine != null && width > 0 && height > 0) {
                    this.origine = origine;
                    this.largeur = width;
                    this.hauteur = height;
                } else {
                    throw new RuntimeException("La fret doit être plus haute et plus large que 0");
                }
            }

            private int getHauteurFret() {
                return this.hauteur / 4;
            }

            private int getLargeurEntreCorde() {
                return this.largeur / 5;
            }

            /**
             * Surcharge de la méthode paintComponent pour dessiner notre accord
             * @param g est l'élément Graphics avec lequel dessiner
             */
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                this.dessineToi(g);
            }

            private void dessineToi(Graphics g) {
                this.drawFret(g);
                this.drawChordNameAndPosition(g);
                this.drawChord(g);
            }

            private void drawFret(Graphics g) {
                //haut de la fret en plus épais. +1 car le fill ne fait pas le contour
                g.fillRect((int) this.origine.x+5, (int) this.origine.y, this.largeur + 1, -8);
                //Dessin du manche
                g.drawRect((int) this.origine.x+5, (int) this.origine.y, this.largeur, this.hauteur);
                //Dessin des cordes
                for (int cordeIdx = 1; cordeIdx < 6; cordeIdx++) {
                    g.drawLine((int) this.origine.x+5 + (this.getLargeurEntreCorde() * cordeIdx), (int) this.origine.y, (int) this.origine.x +5+ (this.getLargeurEntreCorde() * cordeIdx), (int) this.origine.y + (this.hauteur));
                }
                //Dessin des 5 frets utilisées
                for (int fretIdx = 1; fretIdx < 5; fretIdx++) {
                    g.drawLine((int) this.origine.x+5, (int) this.origine.y + (this.getHauteurFret() * fretIdx), (int) this.origine.x+5 + this.largeur, (int) this.origine.y + (this.getHauteurFret() * fretIdx));
                }
            }

            private void drawChord(Graphics g) {
                int rayonPoint = 5;
                if (positionsAccord == null) {
                    positionsAccord = chooseChord();
                }
                int[] accordChoisi = (positionsAccord.size() > 0) ? positionsAccord.get(0) : null;
                if (accordChoisi != null) {
                    int refFret = getRefFretFromChoosenChord(accordChoisi);
                    //On dessine les positions sur le manche
                    for (int idxCorde = 0; idxCorde < accordChoisi.length; idxCorde++) {
                        if(accordChoisi[idxCorde]==-1)
                            this.drawCross(g, idxCorde);
                        else if(accordChoisi[idxCorde]==refFret)
                            this.drawEmptyOval(g, idxCorde);
                        else if (accordChoisi[idxCorde] != refFret) {
                            int coordX = (int) (this.origine.x+5 + (idxCorde * (this.getLargeurEntreCorde())));
                            int demiHauteurFret = this.getHauteurFret() / 2;
                            int coordY = (int) (this.origine.y + (this.getHauteurFret() * (accordChoisi[idxCorde] - refFret)) - demiHauteurFret);
                            g.fillOval(coordX - rayonPoint, coordY - rayonPoint, 2 * rayonPoint, 2 * rayonPoint);
                        }
                        
                    }
                }
            }
            
            /**
             * Dessine la croix pour les cordes qui ne sont pas jouée dans un accord
             * @param g est l'élément graphics
             * @param cordeNo est le numéro de la corde
             */
            private void drawCross(Graphics g, int cordeNo){
                int diametreCroix=5;
                Point2Dim positionCroix=new Point2Dim(this.origine.x+5 + 2 + (this.getLargeurEntreCorde()*cordeNo)-(this.getLargeurEntreCorde()/2), this.origine.y-10);
                g.drawLine((int)positionCroix.x, (int)positionCroix.y, (int)(positionCroix.x+diametreCroix), (int)(positionCroix.y+diametreCroix));                
                g.drawLine((int)positionCroix.x, (int)(positionCroix.y+diametreCroix), (int)(positionCroix.x+diametreCroix), (int)(positionCroix.y));
            }

            /**
             * Dessine l'oval vide lorsque la corde jouée est jouée à vide
             * @param g est l'élément graphics
             * @param cordeNo est le no de la corde concernée (de 0 à 5)
             */
            private void drawEmptyOval(Graphics g, int cordeNo) {
                int diametreRond=6;
                Point2Dim positionRond=new Point2Dim(this.origine.x+5 +2+ (this.getLargeurEntreCorde()*cordeNo)-(this.getLargeurEntreCorde()/2), this.origine.y-10);
                g.drawOval((int)positionRond.x,(int)positionRond.y,diametreRond,diametreRond);
            }

            /**
             * Ecrit le nom de l'accord au dessus de celui-ci, ainsi que le numéro de la fret oú commence l'accord
             * @param g est l'élément graphics
             */
            private void drawChordNameAndPosition(Graphics g) {
                //Définition de la police d'écriture
                Font defaultFont = g.getFont();
                Font myFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
                g.setFont(myFont);

                //Centrage du nom de l'accord par rapport au dessin de l'accord
                int stringLen = (int) g.getFontMetrics().getStringBounds(Frets.this.accord.toString(), g).getWidth();
                int start = (this.getWidth() / 2) - (stringLen / 2);
                g.drawString(Frets.this.accord.toString(), start, 15);

                //Ecriture du No de Fret
                g.setFont(defaultFont);
                int[] accordChoisi = (positionsAccord.size() > 0) ? positionsAccord.get(0) : null;
                if (accordChoisi != null && getRefFretFromChoosenChord(accordChoisi) != 0) {
                    g.drawString(Integer.toString(getRefFretFromChoosenChord(accordChoisi)), (int) (this.origine.x - 10), (int) this.origine.y+5);
                }

                //Réinitialisation du font
                g.setFont(defaultFont);
            }
        }
    }
}
