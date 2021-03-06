/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGraphics2D;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.text.StyledDocument;
import model.A;
import model.Body;
import model.Chanson;
import model.Chansons;
import model.H1;
import model.H2;
import model.Head;
import model.Html;
import model.P;
import model.Span;
import model.Table;
import model.Td;
import model.Title;
import model.Tr;

/**
 * Class permetant la géstion des fichiers
 * @author Yannick Lagger
 */
public class GestionnaireFichiers {
    
    private static String pathEnregistrement="";
    private static String pathEnregistrementSongBook="";
    private static String premierSplit[];
    private static String deuxiemeSplit[];
    /**
     * instanciation du FileChooser
     */
    public final static JFileChooser myFileChooser = new javax.swing.JFileChooser(".");
    /**
     * Fonction permettant de retourner le chemin d'enregistrement des fichiers
     * @return String pathEnregistrement: le chemin d'enregistrement
     */
    public static String rendPathEnregistrement(){
        return pathEnregistrement;
    }
    /**
     * Fonction permettant de modifier le chemin d'enrgistrement de fichier
     * @param String nouveauPath: Le nouveau chemin d'enregistrement de fichier 
     */
    public static void setPathEnregistrement(String nouveauPath){
        pathEnregistrement=nouveauPath;
    }
    /**
     * Fonction permettant de retourner le chemin d'enregistrement des fichiers SongBook
     * @return String pathEnregistrement: le chemin d'enregistrement du SongBook
     */
    public static String rendPathEnregistrementSongBook(){
        return pathEnregistrementSongBook;
    }
    /**
     * Fonction permettant de modifier le chemin d'enrgistrement des fichiers SongBook
     * @param String nouveauPath: Le nouveau chemin d'enregistrement de fichier SongBook
     */
    public static void setPathEnregistrementSongBook(String nouveauPath){
        pathEnregistrementSongBook=nouveauPath;
    }
    /**
     * Fonction qui retourne une AbstractAction permettant d'ouvrire des fichier
     * @param gestPartitions: C'est le GestionnairePartion
     * @return AbstractAction: permettant d'ouvrire des fichier HTML ou data
     */
    public static AbstractAction openFileAction(GestionnairePartitions gestPartitions){
        final GestionnairePartitions gestPartitions2 = gestPartitions;
        return new AbstractAction() {

            @Override
            /**
             * Action permettant l'ouverture des fichier
             */
            public void actionPerformed(ActionEvent e) {
//                FileFilter java = new FiltreSimple("Fichiers Java", ".java"); //TODO ceci n'a rien a faire l�...
//                FileFilter classes = new FiltreSimple("Fichiers Class", ".class");
//                FileFilter jar = new FiltreSimple("Fichiers JAR", ".jar");
                GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
//                GestionnaireFichiers.myFileChooser.addChoosableFileFilter(java);
//                GestionnaireFichiers.myFileChooser.addChoosableFileFilter(classes);
//                GestionnaireFichiers.myFileChooser.addChoosableFileFilter(jar);
                int result=GestionnaireFichiers.myFileChooser.showOpenDialog(null);
                //On ouvre le fichier que si la personne a choisi [OK] et non [Cancel] ou fermé la fenêtre [X]
                if (result==0) {
                    File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
                
                    String completeFileName = selection.getAbsolutePath();
                    GestionnaireFichiers.loadFile(completeFileName, gestPartitions2);
                    gestPartitions2.getGardienHTML().resetMemento();
                }
            }
        };
    }
    
    /**
     * Fonction qui retourne une AbstractAction permettant d'enregistrer des fichiers
     * @param gestPartitions: C'est le GestionnairePartion
     * @return AbstractAction: permettant de sauvegarder les données en .data
     */
    public static AbstractAction saveFileAction(GestionnairePartitions gestPartitions){
        final GestionnairePartitions gestPartitions2 = gestPartitions;
        return new AbstractAction() {

            @Override
            /**
             * l'action permettant de sauvegarder
             */
            public void actionPerformed(ActionEvent e) {
                if (GestionnaireFichiers.rendPathEnregistrement().equals("")) {
                    GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
                    int resultat=GestionnaireFichiers.myFileChooser.showSaveDialog(null);
                    if(resultat==0){
                        File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
                        String completeFileName = selection.getAbsolutePath() + ".data";
                        GestionnaireFichiers.setPathEnregistrement(completeFileName);
                        GestionnaireFichiers.saveFile(gestPartitions2);
                    }
                } else {
                    GestionnaireFichiers.saveFile(gestPartitions2);
                }
            }
        };
    }
    /**
     * Fonction qui retourne une AbstractAction permettant d'enregistrer des fichiers sous (demande le nouvel endroit d'enregistrement)
     * @param gestPartitions: C'est le GestionnairePartion
     * @return AbstractAction: permettant de sauvegarder les données en .data à une nouvelle place
     */
    public static AbstractAction save_asFileAction(GestionnairePartitions gestPartitions){
        final GestionnairePartitions gestPartitions2 = gestPartitions;
        return new AbstractAction() {

            @Override
            /**
             * l'action permettant de sauvegarder sous
             */
            public void actionPerformed(ActionEvent e) {
                GestionnaireFichiers.myFileChooser.setSelectedFile(new java.io.File(""));
                int resultat = GestionnaireFichiers.myFileChooser.showSaveDialog(null);
                if(resultat==0){
                    File selection = GestionnaireFichiers.myFileChooser.getSelectedFile();
                    String completeFileName = selection.getAbsolutePath() + ".data";
                    GestionnaireFichiers.setPathEnregistrement(completeFileName);
                    GestionnaireFichiers.saveFile(gestPartitions2);
                }
            }
        };
    }
 
    /**
     * Permet la sauvegarde des modifications apportées au fichier de partitions en .data
     * @param pane le JEditorPane affichant le fichier de partition
     */
    public static void saveFile(GestionnairePartitions gestPartition) {
        try {
            ObjectOutputStream flotTraitementOut = null;
            FileOutputStream flotCommunicationOut = new FileOutputStream(pathEnregistrement);
            flotTraitementOut = new ObjectOutputStream(flotCommunicationOut);
            flotTraitementOut.writeObject(gestPartition.getJEditorPane().getDocument());
            flotTraitementOut.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Permet la sauvegarde des SongBook
     * @param list la liste de toutes les chansons
     */
    public static void saveSongBook(DefaultListModel list){
        Chansons chansons = new Chansons();
        for (int index=0; index<list.getSize(); index++){
            Chanson chanson = new Chanson(list.get(index).toString());
            chansons.add(chanson);
        }
        
        try {
            FileWriter out = new FileWriter(pathEnregistrementSongBook);
            out.write(chansons.toString());
            out.close();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Permet de charger un fichier de partitions pour l'afficher dans le JEditorPane (ouverture des fichier .html ou .data)
     * @param pane le JEditorPane dans lequel le fichier chargé sera affiché
     */
    public static void loadFile(String fichierPartition, GestionnairePartitions gestPartition) {
        if (fichierPartition.substring(fichierPartition.length()-4).equals("data")){
            try {
            FileInputStream flotCommunicationIn = new FileInputStream(fichierPartition);
            ObjectInputStream flotTraitementIn = new ObjectInputStream(flotCommunicationIn);
            gestPartition.getJEditorPane().setDocument((StyledDocument)flotTraitementIn.readObject());
            flotTraitementIn.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            gestPartition.getJEditorPane().setContentType("text/html");
            Html html = new Html();
            Head head = new Head();
            html.add(head);
            Body body = new Body();
            html.add(body);

            try {
                FileReader in = new FileReader(fichierPartition);
                BufferedReader br = new BufferedReader(in);
                String ligne;
                String titre=null;

                while ((ligne = br.readLine()) != null) {
                    char characterSpecial=' ';

                    if (!ligne.trim().equals("")){
                        for (int index=0; index<ligne.trim().length(); index++){
                            if (ligne.trim().charAt(index)=='[' || ligne.trim().charAt(index)=='{' || ligne.trim().charAt(index)=='#'){
                                characterSpecial=ligne.trim().charAt(index);
                                break;
                            }
                        }

                        if (ligne.trim().length()>=3 && ligne.trim().substring(0, 3).equals("{t:")){
                            titre=ligne.substring(3).split("}")[0];
                            Title title = new Title(titre);
                            H1 h1 = new H1(titre);
                            head.add(title);
                            body.add(h1);
                        }

                        if (ligne.trim().length()>=3 && ligne.trim().substring(0, 4).equals("{st:")){
                            H2 h2 = new H2(ligne.substring(4).split("}")[0]);
                            body.add(h2);
                        }

                        if (characterSpecial==' '){
                            P p1 = new P("");
                            Span span = new Span(ligne.trim());
                            p1.add(span);
                            body.add(p1);
                        }
                        if (characterSpecial=='['){
                            premierSplit = ligne.trim().split("\\[");
                            Table table = new Table();
                            body.add(table);
                            Tr trAccord = new Tr();
                            table.add(trAccord);
                            Tr trParole = new Tr();
                            table.add(trParole);

                            if (characterSpecial=='[' && ligne.trim().charAt(0)!='['){
                                boolean dejaPasse=false;
                                for (int index=1; index<premierSplit.length; index++){
                                    if (dejaPasse==false){
                                        dejaPasse=true;
                                        Td tdAccord1 = new Td("", "class='chords'");
                                        trAccord.add(tdAccord1);
                                        deuxiemeSplit = premierSplit[index].split("]");
                                        Td tdAccord2 = new Td("", "class='chords'");
                                        A a1 = new A(deuxiemeSplit[0], "href='"+deuxiemeSplit[0]+"'");
                                        tdAccord2.add(a1);
                                        trAccord.add(tdAccord2);
                                    }else {
                                        deuxiemeSplit = premierSplit[index].split("]");
                                        Td tdAccord3 = new Td("", "class='chords'");
                                        A a2 = new A(deuxiemeSplit[0], "href='"+deuxiemeSplit[0]+"'");
                                        tdAccord3.add(a2);
                                        trAccord.add(tdAccord3);
                                    }
                                }
                                dejaPasse=false;

                                for (int index=1; index<premierSplit.length; index++){
                                    if (dejaPasse==false){
                                        dejaPasse=true;
                                        Td tdParole1 = new Td("", "class='lyrics'");
                                        Span span1 = new Span(premierSplit[0]);
                                        tdParole1.add(span1);
                                        trParole.add(tdParole1);
                                        deuxiemeSplit = premierSplit[index].split("]");
                                        Td tdParole2 = new Td("", "class='lyrics'");
                                        Span span2 = new Span(deuxiemeSplit[1]);
                                        tdParole2.add(span2);
                                        trParole.add(tdParole2);

                                    }else {
                                        deuxiemeSplit = premierSplit[index].split("]");
                                        Td tdParole3 = new Td("", "class='lyrics'");
                                        Span span3 = new Span(deuxiemeSplit[1]);
                                        tdParole3.add(span3);
                                        trParole.add(tdParole3);
                                    }
                                }
                            } else if (characterSpecial=='[' && ligne.trim().charAt(0)=='['){
                                for (int index=1; index<premierSplit.length; index++){
                                    deuxiemeSplit = premierSplit[index].split("]");
                                    Td tdAccord4 = new Td("", "class='chords'");
                                    A a = new A(deuxiemeSplit[0], "href='"+deuxiemeSplit[0]+"'");
                                    tdAccord4.add(a);
                                    trAccord.add(tdAccord4);
                                }
                                for (int index=1; index<premierSplit.length; index++){
                                    deuxiemeSplit = premierSplit[index].split("]");
                                    try{
                                        Td tdParole4 = new Td("", "class='lyrics'");
                                        Span span4 = new Span(deuxiemeSplit[1]);
                                        tdParole4.add(span4);
                                        trParole.add(tdParole4);
                                    } catch (Exception e){
                                        Td tdParole5 = new Td("", "class='lyrics'");
                                        trParole.add(tdParole5);
                                    }
                                }
                            }
                        }
                    }
                }

                if (titre==null){
                    Title title = new Title("Auteur inconnu");
                    head.add(title);
                }

                pathEnregistrement="";            
                gestPartition.getJEditorPane().setText(html.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Fonction permettant l'export en pdf. Actuellement il est possible de n'imprimer en pdf que la première page
     * @param gestPartition: C'est le GestionnairePartion
     * @param path: C'est le chemin d'enregistrement du fichier pdf
     */
    // Le code en commentaire permet de faire des export pdf sur plusieurs page mais sans garder le style
    public static void exportPDF(GestionnairePartitions gestPartition, String path){
        try{
//           Document pdfDocument = new Document();
//           Reader htmlreader = new BufferedReader(new InputStreamReader(
//                                    new FileInputStream("src/tmp/tmp.html")
//                                   ));
//           ByteArrayOutputStream baos = new ByteArrayOutputStream();
//           PdfWriter.getInstance(pdfDocument, baos);
//           pdfDocument.open();
//           StyleSheet styles = new StyleSheet();
//           styles.loadTagStyle("body", "font", "Bitstream Vera Sans");
//           ArrayList arrayElementList = HTMLWorker.parseToList(htmlreader, styles);
//           for (int i = 0; i < arrayElementList.size(); ++i) {
//               Element e = (Element) arrayElementList.get(i);
//               pdfDocument.add(e);
//           }
//           pdfDocument.close();
//           byte[] bs = baos.toByteArray();
//           String pdfBase64 = Base64.encodeBytes(bs); //output
//           File pdfFile = new File("test.pdf");
//           FileOutputStream out = new FileOutputStream(pdfFile);
//           out.write(bs);
//           out.close();
            
            com.lowagie.text.Document document1 = new com.lowagie.text.Document(PageSize.A4, 80, 50, 30, 65);
            PdfWriter writer = PdfWriter.getInstance(document1, new FileOutputStream(path));
            document1.open();
            
            PdfContentByte cb = writer.getDirectContent();
            DefaultFontMapper mapper = new DefaultFontMapper();
            
            PdfGraphics2D g2 = (PdfGraphics2D)cb.createGraphics(612, 792, mapper);
            gestPartition.getJEditorPane().print(g2);
            g2.dispose();
            document1.close();
            writer.close();
        }catch (Exception e){
                System.err.print( e.getMessage());
        }    
        
    }
    
    /**
     * Fonction permettant l'export du panel en ChordPro
     * @param gestPartition: C'est le GestionnairePartion
     * @param path : C'est le chemin d'enregistrement du fichier ChordPro
     */
    public static void exportChordPro(GestionnairePartitions gestPartition, String path){
        exportHTML(gestPartition, "src/tmp/tmp.html");
        boolean h1=false;
        boolean h2=false;
        boolean table=false;
        boolean tr=false;
        boolean tdChord=false;
        boolean tdLyrics=false;
        boolean p=false;
        ArrayList<String> note = new ArrayList<>();
        ArrayList<String> parole = new ArrayList<>();
        try {
            FileReader in = new FileReader("src/tmp/tmp.html");
            BufferedReader br = new BufferedReader(in);
            String ligne;
            try {
                FileWriter fw = new FileWriter(path);
                BufferedWriter bw = new BufferedWriter(fw);
                while ((ligne = br.readLine()) != null) {
                    if (ligne.trim().length()>=4 && ligne.trim().substring(0, 4).equals("<h1>")){
                        h1=true;
                    } else if (ligne.trim().length()>=5 && ligne.trim().substring(0, 5).equals("</h1>")){
                        h1=false;
                    } else if(h1==true){
                        bw.write("{t:");
                        bw.write(ligne.trim());
                        bw.write("}");
                        bw.newLine();
                    } else if (ligne.trim().length()>=4 && ligne.trim().substring(0, 4).equals("<h2>")){
                        h2=true;
                    } else if (ligne.trim().length()>=5 && ligne.trim().substring(0, 5).equals("</h2>")){
                        h2=false;
                    } else if(h2==true){
                        bw.write("{st:");
                        bw.write(ligne.trim());
                        bw.write("}");
                        bw.newLine();
                    } else if (ligne.trim().length()>=3 && ligne.trim().substring(0, 3).equals("<p>")){
                        p=true;   
                    } else if (ligne.trim().length()>=4 && ligne.trim().substring(0, 4).equals("</p>")){
                        p=false;
                    } else if (p==true){
                        if(!ligne.trim().equals("")) {                                                   
                            String pASubstringuer[] =ligne.trim().split(">");
                            System.out.println(pASubstringuer[1]);
                            String pFinal[] = pASubstringuer[1].split("<");
                            System.out.println(pFinal[0]);
                            bw.write(pFinal[0]);
                            bw.newLine();
                        }
                    } else if (ligne.trim().length()>=7 && ligne.trim().substring(0, 7).equals("<table>")){
                        table=true;
                    } else if (ligne.trim().length()>=8 && ligne.trim().substring(0, 8).equals("</table>")){
                        table=false;
                        for (int index=0; index<note.size(); index++){
                            if (!note.get(index).equals("")){
                                bw.write("[");
                                bw.write(note.get(index));
                                bw.write("]");
                                bw.write(parole.get(index));
                                bw.write(" ");
                            } else {
                                bw.write(parole.get(index));
                                bw.write(" ");
                            }
                        }
                        bw.newLine();
                        note.removeAll(note);
                        parole.removeAll(parole);
                    } else if (table==true){
                        if (ligne.trim().length()>=4 && ligne.trim().substring(0, 4).equals("<tr>")){
                            tr=true;
                        } else if (ligne.trim().length()>=5 && ligne.trim().substring(0, 5).equals("</tr>")){
                            tr=false;
                        } else if(tr==true){
                            if (ligne.trim().length()>=19 && ligne.trim().substring(0, 19).equals("<td class=\"chords\">")){
                                tdChord=true;
                            } else if (ligne.trim().length()>=5 && ligne.trim().substring(0, 5).equals("</td>") && tdChord==true){
                                tdChord=false;
                            } else if (tdChord==true){
                                if (ligne.trim().length()==0){
                                    note.add("");
                                } else {
                                    String noteASubstringuer[] =ligne.trim().split(">");
                                    String noteFinal[] = noteASubstringuer[1].split("<");
                                    note.add(noteFinal[0]);
                                }
                            } else if (ligne.trim().length()>=19 && ligne.trim().substring(0, 19).equals("<td class=\"lyrics\">")){
                                tdLyrics=true;
                            } else if (ligne.trim().length()>=5 && ligne.trim().substring(0, 5).equals("</td>") && tdLyrics==true){
                                tdLyrics=false;
                            } else if (tdLyrics==true){
                                if (ligne.trim().length()==0){
                                    parole.add("");
                                } else {
                                    String paroleASubstringuer[] =ligne.trim().split(">");
                                    String paroleFinal[] = paroleASubstringuer[1].split("<");
                                    parole.add(paroleFinal[0]);
                                }
                            }
                        }
                    }
                }
                bw.close();
            }catch (Exception e){      
                System.out.println("Erreure");
            }           
        } catch (Exception e){}       
    }
    
    /**
     * Fonction permettant de faire un export en HTML. Attention les modifications de styles ne sont pas
     * pris en compte
     * @param path: Le chemin ou on souhaite enregistrer le documents
     * @param gestPartition: Le gestionnaire de partition qui contient notament le JEditorPane
     */
    public static void exportHTML(GestionnairePartitions gestPartition, String path){
        try {
            FileWriter out = new FileWriter(path);
            gestPartition.getHTMLEditorKit().write(out, gestPartition.getJEditorPane().getDocument(), 0, gestPartition.getJEditorPane().getDocument().getLength());
            out.close();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }        
}
