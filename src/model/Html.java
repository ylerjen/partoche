/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yannick Lagger
 */
public class Html extends Balise{
    
    /*
     * @Param Balise - la balise a inséré à l'intérieur de HTML (N'accepte que Head ou Body)
     * @Exception - renvoi une exception en cas d'élément incorrect
     */
    public void add(Balise balise){
        if((balise instanceof Head) || (balise instanceof Body)) {
            this.child.add(balise);
        }
        else throw new RuntimeException("Un élément incorrect a été ajouté à html");
    }
}