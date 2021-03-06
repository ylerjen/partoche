package model;

import java.util.HashMap;

/**
 * Un accord est un ensemble de note qui s'accorde selon des règles définies.
 * Les notes de l'accord de base sont la tonique, la tierce et la quinte. A partir
 * de là, il est possible d'ajouter des notes ou d'en modifier afin que l'accord
 * devienne mineur, septième, etc...
 * Comment calculer les accords?
 * http://www.le-guitariste.com/accords/accords.html
 * http://joseph.dubreuil.free.fr/guitare/index.php/accords-guitare/138?task=view
 * http://www.partoch.com/cours/cours_guitare,142,Comprendre+et+fabriquer+les+accords.html
 * 
 * @author Yann LERJEN
 */
public class Accord {

    /** Constante correspondant à la tonique de l'accord */
    public final static int NOTE_TONIQUE = 0;
    /** Constante correspondant à la tierce de l'accord */
    public final static int NOTE_TIERCE = 3;
    /** Constante correspondant à la quarte de l'accord */
    public final static int NOTE_QUARTE = 4;
    /** Constante correspondant à la quinte de l'accord */
    public final static int NOTE_QUINTE = 5;
    /** Constante correspondant à la sixieme de l'accord */
    public final static int NOTE_SIXIEME = 6;
    /** Constante correspondant à la septieme de l'accord */
    public final static int NOTE_SEPTIEME = 7;
    /** Constante correspondant à la neuvieme de l'accord */
    public final static int NOTE_NEUVIEME = 9;
    /** Constante correspondant à la note de basse de l'accord */
    public final static int NOTE_BASS = 10;
    /** Constante correspondant à un accord qui n'est pas septieme */
    public final static int SEPTIEME_NONE = -1;
    /** Constante correspondant à un accord septieme mineur */
    public final static int SEPTIEME_MINEUR = 0;
    /** Constante correspondant à un accord septieme diminue */
//    public final static int SEPTIEME_DIMINUE = 1;
    /** Constante correspondant à un accord septieme majeur */
    public final static int SEPTIEME_MAJEUR = 2;
    private HashMap<Integer, Note> notes;
    private boolean isChordMinor;
    private int isSeptieme;
    private boolean isSixieme;

    /**
     * Permet de créer un accord à partir du nom de celui-ci
     * @return l'accord créé ou null si aucun accord n'a pu être créé avec le
     * nom passé en paramêtre.
     */
    public static Accord createChordByName(String nomAccord) {
        Accord accordCree = null;
        //On exécute la suite uniquement si un nom d'accord est passé en paramêtre
        if (nomAccord != null & nomAccord.length() > 0) {
            //On récupère la tonique de la note
            String toniqueAccord = "";
            if (nomAccord.length() > 1 && nomAccord.substring(1, 2).equals("#")) {
                toniqueAccord = nomAccord.substring(0, 2);
                nomAccord = nomAccord.substring(2);
            } else {
                toniqueAccord = nomAccord.substring(0, 1);
                nomAccord = nomAccord.substring(1);
            }
            try {
                //On créé l'accord avec la tonique trouvée
                Note tonique = Note.getNoteByName(toniqueAccord);
                accordCree = new Accord(tonique);

                //On transforme l'accord s'il est mineur
                if (nomAccord.length() > 0 && nomAccord.substring(0, 1).equals("m")) {
                    accordCree.setMinor();
                    nomAccord = nomAccord.substring(1);
                }

                //On transforme l'accord s'il est sixième ou septième
                if (nomAccord.length() > 0 && nomAccord.substring(0, 1).equals("6")) {
                    accordCree.setSixieme();
                    nomAccord = nomAccord.substring(1);
                } else if (nomAccord.length() > 0 && nomAccord.substring(0, 1).equals("7")) {
                    nomAccord = nomAccord.substring(1);
                    if (nomAccord.length() > 0 && nomAccord.substring(0, 1).equals("M")) {
                        accordCree.setSeptiemeMajor();
                        nomAccord = nomAccord.substring(1);
//                    } else if (nomAccord.length() > 2 && nomAccord.substring(0, 3).equals("dim")) {
//                        accordCree.setSeptiemeDiminue();
//                        nomAccord = nomAccord.substring(3);
                    } else if (nomAccord.length() > 0 && nomAccord.substring(0, 1).equals("m")) {
                        accordCree.setSeptieme();
                        nomAccord = nomAccord.substring(1);
                    } else {
                        accordCree.setSeptieme();
                    }
                }
                //On modifie l'accord s'il a une note basse
                if (nomAccord.length() > 0 && nomAccord.substring(0, 1).equals("/")) {
                    nomAccord = nomAccord.substring(1);
                    String basseAccord = "";
                    if (nomAccord.length() > 1 && nomAccord.substring(1, 2).equals("#")) {
                        basseAccord = nomAccord.substring(0, 2);
                        nomAccord = nomAccord.substring(2);
                    } else {
                        basseAccord = nomAccord.substring(0, 1);
                    }
                    accordCree.setBass(Note.getNoteByName(basseAccord));
                }
            } catch (Exception e) {
                accordCree = null;
            }
        }
        return accordCree;
    }

    /**
     * Construit un accord simple basé sur la tonique passée en paramêtre
     * @param tonique est la tonique de l'accord, donc la note donnant le nom à l'accord
     */
    public Accord(Note tonique) {
        if (tonique == null) {
            throw new RuntimeException("Il faut spécifier la tonique de l'accord");
        }

        this.notes = new HashMap<>();

        this.isChordMinor = false;
        this.isSixieme = false;
        this.isSeptieme = SEPTIEME_NONE;
        this.setTonique(tonique);
        this.setTierce(this.defineTierceFromTonique());
        this.setQuinte(this.defineQuinteFromTonique());
    }

    /**
     * Permet de récupérer la tonique de l'accord
     * @return tonique est la note correspondant à la tonique ou null s'il n'y en a pas
     */
    public Note getTonique() {
        Note laTonique = this.notes.get(Accord.NOTE_TONIQUE);
        return laTonique;
    }

    private void setTonique(Note tonique) {
        if (tonique != null) {
            this.notes.put(Accord.NOTE_TONIQUE, tonique);
        }
    }

    /**
     * Permet de récupérer la tierce de l'accord
     * @return la note correspondant à la tierce ou null s'il n'y en a pas
     */
    public Note getTierce() {
        return this.notes.get(Accord.NOTE_TIERCE);
    }

    private void setTierce(Note tierce) {
        if (tierce != null) {
            this.notes.put(Accord.NOTE_TIERCE, tierce);
        }
    }

    private Note defineTierceFromTonique() {
        Note laTierce = null;
        if (this.getTonique() != null) {
            laTierce = this.getTonique().rendNoteTransposee(4);
        }
        return laTierce;
    }

    /**
     * Permet de récupérer la quinte de l'accord
     * @return la note correspondant à la quinte ou null s'il n'y en a pas
     */
    public Note getQuinte() {
        return this.notes.get(Accord.NOTE_QUINTE);
    }

    private void setQuinte(Note quinte) {
        if (quinte != null) {
            this.notes.put(Accord.NOTE_QUINTE, quinte);
        }
    }

    private Note defineQuinteFromTonique() {
        Note laQuinte = null;
        if (this.getTonique() != null) {
            laQuinte = this.getTonique().rendNoteTransposee(7);
        }
        return laQuinte;
    }

    /**
     * Permet de récupérer la sixte de l'accord
     * @return la note correspondant à la sixte ou null s'il n'y en a pas
     */
    public Note getSixte() {
        return this.notes.get(Accord.NOTE_SIXIEME);
    }

    private void setSixte(Note sixieme) {
        if (sixieme != null) {
            this.notes.put(Accord.NOTE_SIXIEME, sixieme);
        }
    }

    /**
     * Transforme l'accord de base en accord sixième
     */
    public void setSixieme() {
        if (this.isSeptieme() != SEPTIEME_NONE) {
            this.removeSeptieme();
        }
        this.setSixte(this.defineSixteFromTonique());
        this.isSixieme = true;
    }

    private Note defineSixteFromTonique() {
        Note laSixte = null;
        if (this.getTonique() != null) {
            laSixte = this.getTonique().rendNoteTransposee(9);
        }
        return laSixte;
    }

    /**
     * Transforme l'accord sixième en accord de base en enlevant la Sixte
     */
    public void removeSixieme() {
        this.notes.remove(NOTE_SIXIEME);
        this.isSixieme = false;
    }

    /**
     * Permet de savoir si l'accord est un accord sixième
     * @return TRUE si c'est un accord sixième, FALSE sinon
     */
    public boolean isSixieme() {
        return this.isSixieme;
    }

    /**
     * Transforme l'accord en accord septième mineur
     */
    public void setSeptiemeMinor() {
        this.setSeptieme();
    }

    /**
     * Transforme l'accord en accord septième majeur
     */
    public void setSeptiemeMajor() {
        if (this.isSeptieme() == -1) {
            this.setSeptieme();
        }

        if (this.isSeptieme() == SEPTIEME_MINEUR) {
            this.notes.put(NOTE_SEPTIEME, this.notes.get(NOTE_SEPTIEME).rendNoteTransposee(1));
//        } else if (this.isSeptieme() == SEPTIEME_DIMINUE) {
//            this.notes.put(NOTE_SEPTIEME, this.notes.get(NOTE_SEPTIEME).rendNoteTransposee(2));
        }
        this.isSeptieme = SEPTIEME_MAJEUR;
    }

    /**
     * Transforme l'accord en septième diminué
     */
//    public void setSeptiemeDiminue() {
//        this.setSeptieme();
//
//        this.setTierce(Note.rendNoteTransposee(this.notes.get(NOTE_TIERCE), -2));
//        this.setQuinte(Note.rendNoteTransposee(this.notes.get(NOTE_QUINTE), -2));
//        this.setSixte(Note.rendNoteTransposee(this.notes.get(NOTE_SIXIEME), -2));
//        this.notes.put(NOTE_SEPTIEME, Note.rendNoteTransposee(this.notes.get(NOTE_SEPTIEME), -2));
//        this.setNeuvieme(Note.rendNoteTransposee(this.notes.get(NOTE_NEUVIEME), -2));
//        this.isSeptieme = SEPTIEME_DIMINUE;
//    }

    /**
     * Permet de récupérer la septième de l'accord
     * @return la note correspondant à la septième ou null s'il n'y en a pas
     */
    public Note getSeptieme() {
        return this.notes.get(Accord.NOTE_SEPTIEME);
    }

    /**
     * Permet de définir manuellement la note septieme de l'accord
     * @param septieme est la note qui sera la note septième de l'accord courant
     */
    private void setSeptieme(Note septieme) {
        if (septieme != null) {
            this.notes.put(NOTE_SEPTIEME, septieme);
        }
    }

    /**
     * Permet de transformer l'accord en accord septième Mineur, quel que soit son etat
     */
    private void setSeptieme() {
        if (this.isSixieme() || this.notes.get(NOTE_SIXIEME) != null) {
            this.removeSixieme();
        }

        if (this.isSeptieme == SEPTIEME_NONE || this.notes.get(NOTE_SEPTIEME) == null) {
            this.notes.put(NOTE_SEPTIEME, this.defineSeptiemeFromTonique(false));
//        } else if (this.isSeptieme() == SEPTIEME_DIMINUE) {
//            this.setTierce(Note.rendNoteTransposee(this.notes.get(NOTE_TIERCE), 2));
////            this.setQuarte(Note.rendNoteTransposee(this.notes.get(NOTE_QUARTE),2));
//            this.setQuinte(Note.rendNoteTransposee(this.notes.get(NOTE_QUINTE), 2));
//            this.setSixte(Note.rendNoteTransposee(this.notes.get(NOTE_SIXIEME), 2));
//            this.notes.put(NOTE_SEPTIEME, Note.rendNoteTransposee(this.notes.get(NOTE_SEPTIEME), 2));
//            this.setNeuvieme(Note.rendNoteTransposee(this.notes.get(NOTE_NEUVIEME), 2));
        } else if (this.isSeptieme() == SEPTIEME_MAJEUR) {
            this.notes.put(NOTE_SEPTIEME, Note.rendNoteTransposee(this.notes.get(NOTE_SEPTIEME), -1));
        }

        this.isSeptieme = SEPTIEME_MINEUR;
    }

    private Note defineSeptiemeFromTonique(boolean septiemeMajor) {
        Note laSeptieme = null;
        int ecart = 10;
        if (septiemeMajor) {
            ecart = 11;
        }
        if (this.getTonique() != null) {
            laSeptieme = this.getTonique().rendNoteTransposee(ecart);
        }
        return laSeptieme;
    }

    /**
     * Permet de transformer un accord septième en accord non septième
     */
    public void removeSeptieme() {
        this.setSeptieme();
        this.notes.remove(NOTE_SEPTIEME);
        this.isSeptieme = SEPTIEME_NONE;
    }

    /**
     * Permet de récupérer la neuvième de l'accord
     * @return la note correspondant à la neuvième ou null s'il n'y en a pas
     */
    public Note getNeuvieme() {
        return this.notes.get(Accord.NOTE_NEUVIEME);
    }

    private void setNeuvieme(Note neuvieme) {
        if (neuvieme != null) {
            this.notes.put(Accord.NOTE_NEUVIEME, neuvieme);
        }
    }

    private Note defineNeuviemeFromTonique() {
        Note laNeuvieme = null;
        if (this.getTonique() != null) {
            laNeuvieme = this.getTonique().rendNoteTransposee(15);
        }
        return laNeuvieme;
    }

    /**
     * Permet de récupérer la note Bass de l'accord s'il y'en a
     * @return la note correspondant à la bass ou null s'il n'y en a pas
     */
    public Note getBass() {
        return this.notes.get(Accord.NOTE_BASS);
    }

    /**
     * Permet de spécifier la note basse de l'accord.
     * @param bass est la note qui sera la note de basse de l'accord
     */
    public void setBass(Note bass) {
        if (bass != null) {
            this.notes.put(Accord.NOTE_BASS, bass);
        }
    }

    /**
     * Permet d'enlever la note Basse de l'accord
     */
    public void removeBass() {
        this.notes.remove(NOTE_BASS);
    }

    /**
     * Permet de savoir si l'accord est majeur ou mineur
     * @return TRUE si l'accord est mineur, FALSE s'il est majeur
     */
    public boolean isMinor() {
        return this.isChordMinor;
    }

    /**
     * Transforme l'accord en mineur, s'il est majeur, en enlevant un demi-ton à la tierce.
     */
    public void setMinor() {
        if (!this.isMinor() && this.getTierce() != null) {
            this.setTierce(this.getTierce().rendNoteTransposee(-1));
        }
        this.isChordMinor = true;
    }

    /**
     * Transforme l'accord en majeur, s'il est mineur, en ajoutant un demi-ton à la tierce.
     */
    public void setMajor() {
        if (this.isMinor() && this.getTierce() != null) {
            this.setTierce(this.getTierce().rendNoteTransposee(1));
        }
        this.isChordMinor = false;
    }

    /**
     * Permet de savoir si l'accord est septième et si c'est le cas, lequel (majeur, mineur, diminué)
     * @return 0 si l'accord septième est mineur, 1 s'il est septième diminué, 2 s'il est septième 
     * majeur, et -1 s'il n'est pas septième
     */
    public int isSeptieme() {
        return this.isSeptieme;
    }

    /**
     * Permet de transposer l'accord courant d'après l'écart passé en paramètre
     * @param ecart en demi-ton de l'accord à transposer
     */
    public void transposeAccord(int ecart) {
        this.setTonique(Note.rendNoteTransposee(this.notes.get(NOTE_TONIQUE), ecart));
        this.setTierce(Note.rendNoteTransposee(this.notes.get(NOTE_TIERCE), ecart));
        this.setQuinte(Note.rendNoteTransposee(this.notes.get(NOTE_QUINTE), ecart));
        this.setSixte(Note.rendNoteTransposee(this.notes.get(NOTE_SIXIEME), ecart));
        this.setSeptieme(Note.rendNoteTransposee(this.notes.get(NOTE_SEPTIEME), ecart));
        this.setNeuvieme(Note.rendNoteTransposee(this.notes.get(NOTE_NEUVIEME), ecart));
    }

    /**
     * Permet de retourner la liste des notes formant l'accord courant
     * @return une liste des notes de l'accord selon leur position
     */
    public HashMap rendNotes() {
        return (HashMap) this.notes.clone();
    }

    /**
     * Methode toString permettant d'afficher le nom de l'accord. 
     * @return le nom de l'accord avec ses particularités
     */
    public String toString() {
        String chordName = this.getTonique().getNameUS();
        if (this.isMinor()) {
            chordName += "m";
        }

        if (this.isSixieme()) {
            chordName += "6";
        } else if (this.isSeptieme() != SEPTIEME_NONE) {
            if (this.isSeptieme() == SEPTIEME_MINEUR) {
                chordName += "7";
            } else if (this.isSeptieme() == SEPTIEME_MAJEUR) {
                chordName += "7M";
//            } else if (this.isSeptieme() == SEPTIEME_DIMINUE) {
//                chordName += "7dim";
            }
        }

        if (this.getBass() != null && this.getBass() != this.getTonique()) {
            chordName += "/" + this.getBass().getNameUS();
        }

        return chordName;
    }
}
