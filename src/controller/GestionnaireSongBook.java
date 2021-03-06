/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import org.jdom.Element;
import view.InterfaceGraphique;
import view.SongBook;

/**
 * Class permetant de gérer les SongBooks
 * @author Yannick Lagger
 */
public class GestionnaireSongBook {
    private SongBook songBook;
    /** Pour Singelton  */
    private static GestionnaireSongBook GestionnaireSongBookUnique = null;
    private final Action ACTION_NEW_BOOK = new AbstractAction() {
            /**
             * Action permettant de créée un nouveau SongBook
             */
            @Override
            public void actionPerformed(ActionEvent arg0) {
                songBook.getListModel().removeAllElements();
                songBook.setVisible(true);
            }
        };
    
    private final Action ACTION_OPEN_BOOK = new AbstractAction() {
            /**
             * Action permettant d'ouvrir un songBook
             */
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ouvrirSongBook();
                songBook.setVisible(true);
            }
        };
    
    /**
     * Constuit le gestionnaire de SongBook
     */
    public GestionnaireSongBook(){
        songBook = new SongBook(InterfaceGraphique.getFrames()[0], true);
    }
    
    /**
     * Fonction permettant d'ouvrir un fichier
     */
    public void ouvrirSongBook(){
        GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
        int resultat = GestionnaireFichiers.myFileChooser.showOpenDialog(null);
        if(resultat==0){
            File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
            String completeFileName = selection.getAbsolutePath();  
            ouvrirFichier(completeFileName);
        }
    }
    
    /**
     * Permet d'acceder au GestionnaireSongBook unique
     * Pattern Singelton
     * @return une instance unique du GestionnaireSongBook
     */
    public static GestionnaireSongBook rendGestionnaireSongBookUnique() {
        if (GestionnaireSongBook.GestionnaireSongBookUnique == null)
            GestionnaireSongBook.GestionnaireSongBookUnique = new GestionnaireSongBook();
        return GestionnaireSongBook.GestionnaireSongBookUnique;
    }
    
    /**
     * Retourne l'action nouveau SongBook
     * @return Action: l'action nouveau SongBook
     */
    public Action rendActionNew(){
        return this.ACTION_NEW_BOOK;
    }
    
    /**
     * Retourne l'action ouvrire un SongBook
     * @return Action : ouvrire un SongBook
     */
    public Action rendActionOpen(){
        return this.ACTION_OPEN_BOOK;
    }
    
    /**
     * Fonction permettant d'ouvrir un fichier SongBook
     * @param chemin: Le chemin d'accès à l'enrgregistrement du SongBook
     */
    public void ouvrirFichier(String chemin){
        this.songBook.getListModel().removeAllElements();
        GestionnaireXML.documentAParser(chemin);
        System.out.println();
        if (GestionnaireXML.racine.getName().toString().equals("chansons")){
            List list = GestionnaireXML.racine.getChildren("chanson");
            Iterator i = list.iterator();
            while(i.hasNext()){
                Element courant = (Element)i.next();
                this.songBook.getListModel().addElement(courant.getText());
            }
            this.songBook.verifieListe();
        } else if (GestionnaireXML.racine!=null){
            JOptionPane.showMessageDialog(null, GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("erreur_songBook"));
        }
    }
}
