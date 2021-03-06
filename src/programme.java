import controller.GestionnaireIhm;
import controller.GestionnairePartitions;
import view.InterfaceGraphique;

/**
 * Classe contenant le main qui lance le programme partoche.
 * @author Yann
 */
public class programme {

    /*
     * Lance le programme Partoche
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                GestionnairePartitions gestPartitions = new GestionnairePartitions();
                GestionnaireIhm gestPartoche = new GestionnaireIhm(gestPartitions);
                InterfaceGraphique ihm = InterfaceGraphique.InterfaceGraphique(gestPartoche.menuBar, gestPartoche.scrollpaneControl, gestPartitions.getJScrollPane(), gestPartoche.panelFooter,
                                                                gestPartoche.getJToolBar());
            }
        });
    }
}