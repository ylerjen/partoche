/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yannick Lagger
 */
public class Body extends Balise{
    
    /*
     * @Param Balise - la balise a inséré à l'intérieur de Body (N'accepte que P, H1, H2, Table)
     * @Exception - renvoi une exception en cas d'élément incorrect
     */
    public void add(Balise comp){
        if((comp instanceof P) || (comp instanceof H1) || (comp instanceof H2) || (comp instanceof Table)) 
            this.child.add(comp);
        else 
            throw new RuntimeException("Un élément incorrect a été ajouté au Body");
    }
}
