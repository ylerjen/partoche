
import model.Accord;
import model.Frets;
/**
 *
 * @author Yann LERJEN
 */
public class Tests {

    public static void main(String[] args) {

        Accord unAccord= Accord.createChordByName("A7");
        
//        unAccord.setBass(Note.B);
//        unAccord.setSeptiemeMinor();
        Frets manche = new Frets(unAccord);
//        FenetreDessin cadre = new FenetreDessin();

//        manche.setAccord(unAccord);
        manche.exportImg();
        
        
//
//        unAccord.setSeptiemeMajor();
//        printChordInConsole(unAccord);
//
//        unAccord.setSeptiemeMinor();
//        printChordInConsole(unAccord);
//
//        unAccord.setSeptiemeDiminue();
//        printChordInConsole(unAccord);
//
//        unAccord.setSeptiemeMinor();
//        printChordInConsole(unAccord);
//
//        unAccord.setSeptiemeMajor();
//        printChordInConsole(unAccord);
//
//        unAccord.setSeptiemeDiminue();
//        printChordInConsole(unAccord);

    }

}
