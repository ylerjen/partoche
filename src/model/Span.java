/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yannick Lagger
 */
public class Span extends Balise{
    /*
     * @Param String - Le texte à mettre entre <p></p>
     */
    public Span(String contenuBalise) {
        super(contenuBalise);
    }
    
    /*
     * @Param Balise - la balise a inséré à l'intérieur de Span (N'accepte rien pour le moment)
     * @Exception - renvoi une exception en cas d'élément incorrect
     */
    public void add(Balise comp){
        throw new RuntimeException("Pour l'instant on ne peut rien mettre dans P");
    }    
}
