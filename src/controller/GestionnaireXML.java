/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Class permetant de parser des documents XML
 * @author Yannick Lagger
 */
public class GestionnaireXML {
    
    private static SAXBuilder sxb = new SAXBuilder();
    private static Document document = null;
    static Element racine;
    /**
     * Fonction permettant de parser un documents XML
     * @param documentAOuvrir 
     */
    public static void documentAParser(String documentAOuvrir){
        try {
            document = sxb.build(new File(documentAOuvrir));
            racine = document.getRootElement();           
        }
        catch(JDOMException | IOException e){
            JOptionPane.showMessageDialog(null, GestionnaireLangues.rendGestionnaireLangues().getLocalizedText("erreur_document"));
        }
    }
}
