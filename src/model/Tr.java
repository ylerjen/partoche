/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yannick Lagger
 */
public class Tr extends Balise{
    /*
     * @Param Balise - la balise a inséré à l'intérieur de Tr (N'accepte que Td)
     * @Exception - renvoi une exception en cas d'élément incorrect
     */
    public void add(Balise comp){
        if((comp instanceof Td)) 
            this.child.add(comp);
        else 
            throw new RuntimeException("Un élément incorrect a été ajouté au Body");
    }
}
