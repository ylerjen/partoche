package model;

/**
 * Permet de gérer un point en deux dimensions
 */
public class Point2Dim{
    
    /**
     * Coordonnée x du point
     */
    protected double x;
    /**
     * Coordonnée y du point
     */
    protected double y;
    
    /**
     * Crée un nouveau point 2 dimensions de couleur noire
     * @param x Coordonnée x du point
     * @param y Coordonnée y du point
     */
    public Point2Dim(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }
    
    /**
     * Permet de dessiner le point dans le composant swing possédant le "peintre" g
     * @param g Peintre d'un composant swing
     */
    public void dessineToi(java.awt.Graphics g) {
        g.drawLine((int)x-1, (int)y, (int)x+1, (int)y);
        g.drawLine((int)x, (int)y+1, (int)x, (int)y-1);
    }
    
    /**
     * Permet de changer la coordonnée x du point
     * @param nouveauX Nouvelle coordonnée x du point
     */
    public void changeX(double nouveauX) {
        x = nouveauX;
    }
    
    /**
     * Permet de changer la coordonnée y du point
     * @param nouveauY Nouvelle coordonnée y du point
     */
    public void changeY(double nouveauY) {
        y = nouveauY;
    }
    
    /**
     * Rend la coordonnée x du point
     * @return Coordonnée x du point
     */
    public double rendX() {
        return x;
    }
    
    /**
     * Rend la coordonnée y du point
     * @return Coordonnée y du point
     */
    public double rendY() {
        return y;
    }
    
    /**
     * Permet d'afficher correctement un point lors de l'instruction System.out.println(Point2Dim);
     * @return Une chaine contenant (x;y)
     */
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
    
    /**
     * Rend la distance entre les 2 points passés en paramètres
     * @param pt1 1er point
     * @param pt2 2ème point
     * @return Distance entre pt1 et pt2
     */
    public static double rendDistance(Point2Dim pt1, Point2Dim pt2) {
        double deltaX;
        double deltaY;
        deltaX = pt2.rendX() - pt1.rendX();
        deltaY = pt2.rendY() - pt1.rendY();
        return Math.hypot(deltaX,deltaY);
    }
    
    /**
     * Rend la distance entre le point courant et le point passé en paramètre
     * @param pt Point d'arrivée
     * @return La distance entre le point courant et le point passé en paramètre
     */
    public double rendDistance(Point2Dim pt) {
        return Point2Dim.rendDistance(this,pt);
    }
    
}