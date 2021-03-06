package model;

/**
 * Une note de musique, selon la gamme de sol, est défini avec Do, Ré, Mi, Fa, Sol,
 * La, Si, Do (cela recommence à l'infini). Elle contient également des demi-tons 
 * définis à l'aide de dièze ou de bémol. Ici, seul le dièze est supporté.
 * 
 * Cette enum contient toutes ces notes et permet l'accès à son demi-ton, son nom
 * Français ainsi que son nom Anglais.
 * 
 * @author Yann LERJEN
 */
public enum Note {
    /** La note Do (C) vaut 0 en demi-ton car elle est utilisée comme note de référence */
    C(0,"Do","C"),
    /** La note Do dièze (C#) vaut 1 en demi-ton par rapport à la note de référence */
    Cd(1,"Do#","C#"),
    /** La note Ré (D) vaut 2 en demi-ton par rapport à la note de référence */
    D(2,"Ré","D"),
    /** La note Ré dièze (D#) vaut 3 en demi-ton par rapport à la note de référence */
    Dd(3,"Ré#","D#"),
    /** La note Mi (E) vaut 4 en demi-ton par rapport à la note de référence */
    E(4,"Mi","E"),
    /** La note Fa (F) vaut 5 en demi-ton par rapport à la note de référence */
    F(5,"Fa","F"),
    /** La note Fa dièze (F#) vaut 6 en demi-ton par rapport à la note de référence */
    Fd(6,"Fa#","F#"),
    /** La note Sol (G) vaut 7 en demi-ton par rapport à la note de référence */
    G(7,"Sol","G"),
    /** La note Sol dièze (G#) vaut 8 en demi-ton par rapport à la note de référence */
    Gd(8,"Sol#","G#"),
    /** La note La (A#) vaut 9 en demi-ton par rapport à la note de référence */
    A(9,"La","A"),
    /** La note La dièze (A#) vaut 10 en demi-ton par rapport à la note de référence */
    Ad(10,"La#","A#"),
    /** La note Si (B) vaut 11 en demi-ton par rapport à la note de référence */
    B(11,"Si","B");
    
    
    
    private String nameFR;
    private String nameUS;
    private int ton;

    //Le constructeur suivant ne peut être accédé que par une énum
    //d'où le fait qu'il ne PEUT PAS être en public !!!
    private Note(int ton, String nomFR, String nomUS) {
        this.ton=ton;
        this.nameFR = nomFR;
        this.nameUS = nomUS;
    }

    /**
     * Permet de connaitre le nom de la note au format US (C,D,E)
     * @return le nom de la note au format US.
     */
    public String getNameUS() {
        return this.nameUS;
    }
    
    /**
     * Permet de connaitre le nom de la note au format FR (do,ré,mi,...)
     * @return le nom de la note au format FR
     */
    public  String getNameFR(){
        return this.nameFR;
    }

    /**
     * Permet de récupérer le ton de la note d'après la référence C qui vaut 0
     * puis incrément de 1 par demi-ton.
     * @return le ton de la note entre 0 et 11
     */
    public int getTon() {
        return this.ton;
    }
    
    /**
     * Permet de transposer la note courante selon l'écart mentionné
     * @param ecart est l'ecart de la transposition. Doit être négatif pour baisser
     * le ton ou positif pour l'augmenter
     * @return la note transposée, ou null s'il y a eu un problème lors de la transposition
     */
    public Note rendNoteTransposee(int ecart) {
        Note note = null;        
        int newTon = this.getTon()+ecart;
        
        while(newTon<0 || newTon>11){
            if(newTon>11)
                newTon-=12;
            if(newTon<0)
                newTon+=12;
        }
        for (Note noteTemp : Note.values()) {
            if(noteTemp.getTon()==newTon){
                note = noteTemp;
                break;
            }
        }        
        return note;
    }
    
    /**
     * Permet de transposer une note passée en paramêtre d'après l'écart mentionné
     * @param laNote la note a transposer. Si elle vaut null, la méthode retournera null
     * @param ecart est l'ecart de la transposition. Doit être négatif pour baisser
     * le ton ou positif pour l'augmenter
     * @return la note transposée, ou null s'il y a eu un problème lors de la transposition
     */
    public static Note rendNoteTransposee(Note laNote,int ecart) {
        Note noteTransposee=null;
        if(laNote!=null)
            noteTransposee=laNote.rendNoteTransposee(ecart);
        return noteTransposee;            
    }
    
    /**
     * Permet de connaitre le nombre de demiton en valeur absolue séparant la note avec une autre
     * @param laNote est la note a comparé avec la note courante
     * @return le nombre de demiton de différence en valeur absolue 
     */
    public int getSemiTonDifferenceWith(Note laNote){
        int diff=this.getTon()-laNote.getTon();
        if(diff<0)diff*=-1;
        return diff;
    }
    
    /**
     * Permet de connaitre le nombre de demiton en valeur absolue séparant 2 notes
     * @param note1 est la note de référence
     * @param note2 est la note comparée
     * @return le nombre de demiton de différence en valeur absolue
     */
    public static int getSemiTonDifferenceBetween(Note note1, Note note2){
        return note1.getSemiTonDifferenceWith(note2);
    }
    
    /**
     * Override de la méthode toString hérité de Object afin de retourner le nom de la note
     * @return le nom de la note au format US
     */
    public String toString(){
        return this.getNameUS();
    }
    
    
    
    /**
     * Permet de récupérer une note par son nom en français (do,ré,mi...)
     * @param nameFr qui est le nom en français
     * @return la Note correspondante ou null si aucune ne correspond
     */
    public static Note getNoteByNameFR(String nameFr){
        try{
            return Note.valueOf(nameFr);
        }
        catch(Exception e){
            return null;
        }
    }
    
    /**
     * Permet de récupérer une note par son nom en anglais (C,D,E...)
     * @param nameUs qui est le nom en anglais
     * @return la Note correspondante ou null si aucune ne correspond
     */
    public static Note getNoteByNameUS(String nameUs){
        if(nameUs.indexOf('#')>=0)
            nameUs=nameUs.replaceAll("#", "d");
        try{
            return Note.valueOf(nameUs);
        }
        catch(Exception e){
            return null;
        }
    }
    
    /**
     * Permet de récupérer une note par son nom anglais ou français
     * @param name qui est le nom en anglais ou français
     * @return la Note correspondant ou null si aucune ne correspond
     */
    public static Note getNoteByName(String name){
        Note laNote=Note.getNoteByNameUS(name);
        if(laNote==null)
            laNote=Note.getNoteByNameFR(name);
        return laNote;
    }
}