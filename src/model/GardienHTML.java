
package model;

import java.util.LinkedList;
import java.util.List;
import model.CreateurHTML.Memento;

/**
 * Permet de conserver un Etat précédent du HTML affiché via Memento
 * @author gregoryangeloz
 */
public class GardienHTML{
        
    private List<Memento> list;

    /**
     * Constructeur
     * initialise la Liste de Memento
     */
    public GardienHTML() {
        this.list = new LinkedList<>();
    }
    
    /**
     * Ajoute un memento à la liste
     * @param html l'objet String à ajouter
     */
    public void addMemento(Memento html) {
        this.list.add(html);
    }
    
    /**
     * Permet de récupérer un memento correspondant à l'index fournit
     * @param index l'index du memento à retourner
     * @return un objet String
     */
    public Memento getMemento(int index) {
        return this.list.get(index);
    }
    
    /**
     * Remet à zéro la liste de Memento      
     * A Utiliser lorsque le HTML vient à être modifié, afin de récupérer le bon.
     */
    public void resetMemento() {
        this.list.clear();
    }
    
    /**
     * Retourne la taille de la liste de Memento actuelle
     * @return la taille de la liste de memento
     */
    public int getListSize() {
        return this.list.size();
    }
    

    
    
}
