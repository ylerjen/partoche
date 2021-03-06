/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yannick Lagger
 */
public class Td extends Balise{
    /*
     * @Param String - Le texte à mettre entre <td></td>
     */
    public Td(String contenuBalise, String attribut) {
        super(contenuBalise, attribut);
    }
    
    /*
     * @Param Balise - la balise a inséré à l'intérieur de Td (N'accepte que a)
     * @Exception - renvoi une exception en cas d'élément incorrect
     */
    public void add(Balise comp){
        if((comp instanceof A || comp instanceof Span)) 
            this.child.add(comp);
        else 
            throw new RuntimeException("Un élément incorrect a été ajouté au Body");
    }
}
