/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yannick Lagger
 */
public class A extends Balise{
    
    public A(String contenuBalise, String attribut) {
        super(contenuBalise, attribut);
    }
    
    /*
     * @Param Balise - la balise a inséré à l'intérieur de A (N'accepte rien pour le moment)
     * @Exception - renvoi une exception en cas d'élément incorrect
     */
    public void add(Balise comp){
        throw new RuntimeException("Pour l'instant on ne peut rien mettre dans P");
    }
}
