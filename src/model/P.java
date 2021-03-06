/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yannick Lagger
 */
public class P extends Balise{
    /*
     * @Param String - Le texte à mettre entre <p></p>
     */
    public P(String contenuBalise) {
        super(contenuBalise);
    }
    
    /*
     * @Param Balise - la balise a inséré à l'intérieur de P (accepte uniquement les span)
     * @Exception - renvoi une exception en cas d'élément incorrect
     */
    public void add(Balise comp){
        if((comp instanceof A || comp instanceof Span)) 
            this.child.add(comp);
        else 
            throw new RuntimeException("Un élément incorrect a été ajouté au Body");
    }
}
