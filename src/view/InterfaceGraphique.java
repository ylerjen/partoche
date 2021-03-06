package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
 * InterfaceGraphique est la fenêtre IHM du programme partoche. Comme elle est sensé être unique,
 * un singleton reglemente l'accès à son constructeur. A la fermeture de cette fenêtre, le programme s'arrête.
 * @author Yann
 */
public class InterfaceGraphique extends JFrame {

    private static InterfaceGraphique ihm = null;

    /**
     * Methode permettant de créer un objet interfaceGraphique. Si l'objet a déjà été créé
     * une fois, on le retourne plutôt que de créer un nouveau.
     * @param menu est la bar de menu à insérer dans la fenêtre
     * @param panelBouton est le panneau d'action situé à gauche et contenant les boutons
     * @param panelPartition est le panneau où sera affiché la partition
     * @param panelFooter est le pied de page du programme
     * @param barreOutils est la barre d'outils située sous le menu
     * @return un objet InterfaceGraphique unique (singleton)
     */
    public static InterfaceGraphique InterfaceGraphique(JMenuBar menu, JScrollPane panelBouton, JScrollPane panelPartition, JPanel panelFooter, JToolBar barreOutils) {
        if (null == ihm) {
            ihm = new InterfaceGraphique(menu, panelBouton, panelPartition, panelFooter, barreOutils);
        }
        return ihm;
    }

    private InterfaceGraphique(JMenuBar menu, JScrollPane panelBouton, JScrollPane panelPartition, JPanel panelFooter, JToolBar barreOutils) {
        super("Partoche !");
        miseEnPage(menu, panelBouton, panelPartition, panelFooter, barreOutils);
        UIManager.put("ToolTip.background", new ColorUIResource(Color.white));
        this.pack();
        this.setVisible(true);
    }

    private void miseEnPage(JMenuBar menu, JScrollPane panelBouton, JScrollPane panelPartition, JPanel panelFooter, JToolBar barreOutils) {
        this.setJMenuBar(menu);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(panelBouton, BorderLayout.WEST);
        this.getContentPane().add(panelPartition, BorderLayout.CENTER);
        this.getContentPane().add(panelFooter, BorderLayout.SOUTH);
        this.getContentPane().add(barreOutils, BorderLayout.NORTH);
    }

    /**
     * Afin de controler ce qui ce passe lorsque l'utilisateur ferme la fenêtre,
     * nous devons redéfinir cette méthode.
     * @param e est l'évenement passé comme le résultat de l'action de l'utilisateur
     */
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            int exit = JOptionPane.showConfirmDialog(this, "Etes-vous sûr de vouloir quitter ?");
            if (exit == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            super.processWindowEvent(e);
        }
    }
}
