
package model;

/**
 * Permet de créer un objet contenant l'etat du HTML que l'on souhaite conserver
 * @author gregoryangeloz
 */
public class CreateurHTML {
    
    private String html;
    
    /**
     * Constructeur, instancie un nouveau CreateurHTML
     */
    public CreateurHTML() {        
    }
    
    /**
     * Permet de setter l'html que l'on souhaite conserver
     * @param html 
     */
    public void setHTML(String html) {
        this.html = html;
    }
                
    /**
     * Sauvegarde l'etat dans un Memento
     * @return le Memento
     */
    public Memento sauverDansMemento() {
        return new Memento(this.html);
    }
    /**
     * Permet de retrouver un etat précédent depuis un Memento donné
     * @param memento Le memento sur lequel on se base pour restaurer l'état précédent
     */
    public void restaurerMemento(Memento memento) {
        this.html = memento.getHTML();
    }
    
    /**
     * Retourne le html à sauvegarder
     * @return le html 
     */
    public String getHTML() {
        return this.html;
    }
    
    /**
     * Classe interne qui contient la sauvegarde de l'état
     * de l'html
     */
    public class Memento {
        private String html;
                
        private Memento(String html) {
            this.html = html;
        }
        
        private String getHTML() {
            return this.html;
        }                
    }
}
