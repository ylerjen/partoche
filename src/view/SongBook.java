/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SongBook.java
 *
 * Created on 22 févr. 2012, 13:01:16
 */
package view;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.Base64;
import controller.GestionnaireFichiers;
import controller.GestionnaireLangues;
import controller.GestionnaireSongBook;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import model.PartocheTextPane;

/**
 * Class créant l'ihm du SongBook ainsi que les actions correspondantes
 * @author Yannick Lagger
 */
public class SongBook extends javax.swing.JDialog {
    
    /** Creates new form SongBook */
    public SongBook(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.listModel = new DefaultListModel();
        initComponents();
        verifieListe();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList(this.listModel);
        champLien = new javax.swing.JTextField();
        labelChanson = new javax.swing.JLabel();
        parcourir = new javax.swing.JButton();
        ajouter = new javax.swing.JButton();
        boutonSupprimer = new javax.swing.JButton();
        bouttonUp = new javax.swing.JButton();
        bouttonDown = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFichier = new javax.swing.JMenu();
        menuNouveau = new javax.swing.JMenuItem();
        menuOuvrir = new javax.swing.JMenuItem();
        menuEnregistrer = new javax.swing.JMenuItem();
        menuEnregistrerSous = new javax.swing.JMenuItem();
        menuExport = new javax.swing.JMenu();
        menuItemPdf = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SongBook");

        jScrollPane1.setViewportView(jList1);

        champLien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                champLienActionPerformed(evt);
            }
        });

        labelChanson.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("label_song"));

        parcourir.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("bouton_parcourir"));
        parcourir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parcourirActionPerformed(evt);
            }
        });

        ajouter.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("bouton_ajouter"));
        ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterActionPerformed(evt);
            }
        });

        boutonSupprimer.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("bouton_supprimer"));
        boutonSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonSupprimerActionPerformed(evt);
            }
        });

        bouttonUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ressources/images/up.png"))); // NOI18N
        bouttonUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bouttonUpActionPerformed(evt);
            }
        });

        bouttonDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ressources/images/down.png"))); // NOI18N
        bouttonDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bouttonDownActionPerformed(evt);
            }
        });

        menuFichier.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_file"));

        menuNouveau.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_new"));
        menuNouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNouveauActionPerformed(evt);
            }
        });
        menuFichier.add(menuNouveau);

        menuOuvrir.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_open"));
        menuOuvrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOuvrirActionPerformed(evt);
            }
        });
        menuFichier.add(menuOuvrir);

        menuEnregistrer.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_save"));
        menuEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEnregistrerActionPerformed(evt);
            }
        });
        menuFichier.add(menuEnregistrer);

        menuEnregistrerSous.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_saveAs"));
        menuEnregistrerSous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEnregistrerSousActionPerformed(evt);
            }
        });
        menuFichier.add(menuEnregistrerSous);

        menuBar.add(menuFichier);

        menuExport.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("menu_export"));

        menuItemPdf.setText(GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("export_PDF"));
        menuItemPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPdfActionPerformed(evt);
            }
        });
        menuExport.add(menuItemPdf);

        menuBar.add(menuExport);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelChanson, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(champLien, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(parcourir)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(ajouter)
                        .addComponent(boutonSupprimer))
                    .addComponent(bouttonUp, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bouttonDown, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bouttonUp, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(boutonSupprimer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bouttonDown, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(champLien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelChanson)
                    .addComponent(parcourir)
                    .addComponent(ajouter))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void champLienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_champLienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_champLienActionPerformed

    /**
     * Action permettant de chercher un lien de chanson que l'on souhaite ajouter au SongBook
     * @param evt 
     */
    private void parcourirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parcourirActionPerformed
        GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
        int resultat = GestionnaireFichiers.myFileChooser.showOpenDialog(null);
        if(resultat==0){
            File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
            String completeFileName =  selection.getAbsolutePath();
            champLien.setText(completeFileName);
        }
    }//GEN-LAST:event_parcourirActionPerformed

    /**
     * Action permettant d'ajouter le lien au SongBook
     * @param evt 
     */
    private void ajouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajouterActionPerformed
        if (champLien.getText().substring(champLien.getText().length()-4).equals("data")){
            listModel.addElement(champLien.getText());
            champLien.setText("");
            verifieListe();
        } else {
            JOptionPane.showMessageDialog(null, GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("erreur_partochFile"));
            champLien.setText("");
        }
        
    }//GEN-LAST:event_ajouterActionPerformed

    /**
     * Action permetant d'enregistrer le songBook sous un nouveau lien
     * @param evt 
     */
    private void menuEnregistrerSousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEnregistrerSousActionPerformed
        GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
        int resultat = GestionnaireFichiers.myFileChooser.showSaveDialog(null);
        if(resultat==0){
            File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
            String completeFileName =  selection.getAbsolutePath()+".xml";
            GestionnaireFichiers.setPathEnregistrementSongBook(completeFileName);
            GestionnaireFichiers.saveSongBook(this.listModel);  
        }
    }//GEN-LAST:event_menuEnregistrerSousActionPerformed

    /**
     * Action permetant d'enregistrer un SongBook
     * @param evt 
     */
    private void menuEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEnregistrerActionPerformed
        if (GestionnaireFichiers.rendPathEnregistrementSongBook().equals("")){
            GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
            int resultat = GestionnaireFichiers.myFileChooser.showSaveDialog(null);
            if(resultat==0){
                File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
                String completeFileName =  selection.getAbsolutePath()+".xml";
                GestionnaireFichiers.setPathEnregistrementSongBook(completeFileName);
                GestionnaireFichiers.saveSongBook(this.listModel);
            }
        } else{
            GestionnaireFichiers.saveSongBook(this.listModel);
        }
    }//GEN-LAST:event_menuEnregistrerActionPerformed

    /**
     * Action permettant de supprimer une chanson du SongBook
     * @param evt 
     */
    private void boutonSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonSupprimerActionPerformed
        this.listModel.remove(this.jList1.getSelectedIndex());
        verifieListe();
        
    }//GEN-LAST:event_boutonSupprimerActionPerformed

    /**
     * Action du bouton permetant de changer d'emplacement d'une chanson vers le haut 
     * @param evt 
     */
    private void bouttonUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bouttonUpActionPerformed
        if (this.jList1.getSelectedIndex()!=0 && this.jList1.getSelectedIndex()!=-1){
            int nouvellePosition = this.jList1.getSelectedIndex()-1;
            this.listModel.insertElementAt(this.listModel.getElementAt(this.jList1.getSelectedIndex()), nouvellePosition);
            this.listModel.removeElementAt(this.jList1.getSelectedIndex());
            this.jList1.setSelectedIndex(nouvellePosition);
        }
    }//GEN-LAST:event_bouttonUpActionPerformed

    /**
     * Action du bouton permetant de changer d'emplacement d'une chanson vers le bas
     * @param evt 
     */
    private void bouttonDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bouttonDownActionPerformed
        if (this.jList1.getSelectedIndex() != this.listModel.getSize()-1 && this.jList1.getSelectedIndex()!=-1){
            int nouvellePosition = this.jList1.getSelectedIndex()+2;
            this.listModel.insertElementAt(this.listModel.getElementAt(this.jList1.getSelectedIndex()), nouvellePosition);
            this.listModel.removeElementAt(this.jList1.getSelectedIndex());
            this.jList1.setSelectedIndex(nouvellePosition-1);
        }
    }//GEN-LAST:event_bouttonDownActionPerformed

    /**
     * Action permetant d'ouvrir un SongBook
     * @param evt 
     */
    private void menuOuvrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOuvrirActionPerformed
        GestionnaireSongBook.rendGestionnaireSongBookUnique().ouvrirSongBook();
        verifieListe();
    }//GEN-LAST:event_menuOuvrirActionPerformed

    /**
     * Action permetant d'ouvrir un nouveau SongBook
     * @param evt 
     */
    private void menuNouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNouveauActionPerformed
        this.listModel.removeAllElements();
    }//GEN-LAST:event_menuNouveauActionPerformed

    /**
     * Action permettant de faire des exports en PDF (Attention les styles ne sont pas pris en considération)
     * @param evt 
     */
    private void menuItemPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPdfActionPerformed
        String completeFileName = "";
        GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
        int resultat = GestionnaireFichiers.myFileChooser.showSaveDialog(null);
        if(resultat==0){
            File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
            completeFileName =  selection.getAbsolutePath()+".pdf";
        }
        
        PartocheTextPane textPane = new PartocheTextPane();
        HTMLEditorKit kit = new HTMLEditorKit();
        textPane.setEditorKit(kit);
        
        try{
            Document pdfDocument = new Document();
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(pdfDocument, baos);
            pdfDocument.open();
            for (int index=0; index<listModel.size(); index++){
                try {
                    FileInputStream flotCommunicationIn = new FileInputStream(listModel.get(index).toString());
                    ObjectInputStream flotTraitementIn = new ObjectInputStream(flotCommunicationIn);
                    textPane.setDocument((StyledDocument)flotTraitementIn.readObject());
                    flotTraitementIn.close();
                    FileWriter out2 = new FileWriter("src/tmp/tmp.html");
                    out2.write(textPane.getText());
                    out2.close();
                    Reader htmlreader = new BufferedReader(new InputStreamReader(
                                    new FileInputStream("src/tmp/tmp.html")
                                   ));
                    StyleSheet styles = new StyleSheet();
                    styles.loadTagStyle("body", "font", "Bitstream Vera Sans");
                    ArrayList arrayElementList = HTMLWorker.parseToList(htmlreader, styles);
                    for (int i = 0; i < arrayElementList.size(); ++i) {
                        Element e = (Element) arrayElementList.get(i);
                        pdfDocument.add(e);
                    }
                    pdfDocument.newPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pdfDocument.close();
            byte[] bs = baos.toByteArray();
            String pdfBase64 = Base64.encodeBytes(bs); //output
            File pdfFile = new File(completeFileName);
            FileOutputStream out = new FileOutputStream(pdfFile);
            out.write(bs);
            out.close();
        } catch (Exception e){}
    }//GEN-LAST:event_menuItemPdfActionPerformed

    /**
     * Fonction qui vérifie si la liste de chanson et vide pour activer et désactiver le bouton supprimer.
     * La fonction vérifie également s'il y a plus d'une chanson pour activer ou désactiver les bouton monter
     * et décen
     * 
     */
    public void verifieListe(){
        if (this.listModel.getSize()==0){ 
            this.boutonSupprimer.setEnabled(false);
        }else{
            this.boutonSupprimer.setEnabled(true);
        }
        if (this.listModel.getSize()>1){
            this.bouttonUp.setEnabled(true);
            this.bouttonDown.setEnabled(true);
        } else{
            this.bouttonUp.setEnabled(false);
            this.bouttonDown.setEnabled(false);
        }
    }
    
    public DefaultListModel getListModel(){
        return this.listModel;
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SongBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SongBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SongBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SongBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                SongBook dialog = new SongBook(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ajouter;
    private javax.swing.JButton boutonSupprimer;
    private javax.swing.JButton bouttonDown;
    private javax.swing.JButton bouttonUp;
    private javax.swing.JTextField champLien;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelChanson;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuEnregistrer;
    private javax.swing.JMenuItem menuEnregistrerSous;
    private javax.swing.JMenu menuExport;
    private javax.swing.JMenu menuFichier;
    private javax.swing.JMenuItem menuItemPdf;
    private javax.swing.JMenuItem menuNouveau;
    private javax.swing.JMenuItem menuOuvrir;
    private javax.swing.JButton parcourir;
    // End of variables declaration//GEN-END:variables
    private DefaultListModel listModel;
}
