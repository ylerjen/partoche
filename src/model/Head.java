/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yannick Lagger
 */
public class Head extends Balise {
    
    /*
     * @Param Balise - la balise a inséré à l'intérieur de Head (N'accepte que Title)
     * @Exception - renvoi une exception en cas d'élément incorrect
     */
    public void add(Balise comp){
        if((comp instanceof Title)) {
                this.child.add(comp);
        }
        else throw new RuntimeException("Un élément incorrect a été ajouté à Head");
    }
}
