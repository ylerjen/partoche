package model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.JToolTip;

/**
 * Classe qui étend la classe JTextPane afin de réecrire la méthode créant les ToolTips, ceci pour permettre l'affichage d'une Pop-up contenant
 * l'image de l'accord survolé
 * @author gregoryangeloz
 */
public class PartocheTextPane extends JTextPane implements Serializable{
    
    /**
     * Constructeur
     */
    public PartocheTextPane() {
        super();
    }
        
    @Override
    /**
     * Permet la création d'une pop-up contenant l'image de l'accord actuellement survolé
     * @return JToolTip le ToolTip qui s'affichera
     */
    public JToolTip createToolTip() {
        
        BufferedImage myPicture = null;
        Image img = null;
        try {
            myPicture = ImageIO.read(new File(System.getProperty("java.io.tmpdir")+"chordImg.png"));
            img = new ImageIcon(myPicture).getImage();              
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return new MyToolTip(img);
    }
    
    
    /**
     * Class permettant de créer un tooltip personnalisé qui contient une image
     */
    public class MyToolTip extends JToolTip {
                Image image;
                public MyToolTip(Image image) {
                        super();
                        this.image = image;
                        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));			
                }
                /**
                 * Dessine l'image de l'accord
                 * @param g 
                 */
                public void paintComponent(Graphics g) {
                        g.drawImage(image, 0, 0, null);
                }
        }
    
}
