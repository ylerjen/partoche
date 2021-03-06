/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Yannick Lagger
 */
public abstract class Balise {
    //Le nom de la balise reprend le nom de la classe en minuscule
    protected String baliseName = this.getClass().getSimpleName().toLowerCase();
    protected String contenuBalise = "";
    protected String attribut = "";
    protected ArrayList child = new ArrayList();   

    public Balise(){}
    public Balise(String contenuBalise){this.contenuBalise = contenuBalise;}
    public Balise(String contenuBalise, String attribut){
        this.contenuBalise = contenuBalise;
        this.attribut = attribut;
    }

    public void add(Balise balise){
        this.child.add(balise);
    }
    public void remove(int i){
        this.child.remove(i);
    }
    public String toString(){
        String str = "";
        if (!this.attribut.equals("")){
            str += "<" + this.baliseName +" "+ this.attribut +">";
        }else{
            str += "<" + this.baliseName +">";
        }
        str +=  this.contenuBalise;
        
        //Boucle les balises contenue dans cette balise
            for(Object balise : this.child){
                str += ((Balise)balise).toString(); 
            }
        
        str += "</" + baliseName + ">";
        return  str;
    }  
}
