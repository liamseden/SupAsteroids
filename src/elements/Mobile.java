/**
 * La classe mobile, gui gere tout les objets 
 * mobiles du jeu et leurs aspects, notamment 
 * les déplacement, l'image et son affichage 
 * ainsi que son contour.
 * 
 * 
 *
 * @author Smail Medjadi
 * @version 1.0
 * @date	24/01/2016
 */

package elements;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import jeu.Jeu;


public class Mobile {
    private float x;
    private float y;
    private Image image = null ;
    private int categorie ;
    private Shape contour = null;
    
    /**
     * Constructeur principal pour creer un mobile avec 
     * une position donnée en parametre et le chemin 
     * de l'image.
     */
    public Mobile(float x, float y ,String chemin) throws SlickException {
        image = new Image(chemin);
        this.x = x;
        this.y = y;
    }
    /**
     * Constructeur principal pour creer un mobile avec 
     * une position donnée en parametre et le chemin 
     * de l'image et la categorie.
     * 
     */    
    public Mobile(float x, float y ,String chemin, int cat) throws SlickException {
        image = new Image(chemin);
        this.x = x;
        this.y = y;
        this.categorie = cat ;
    }    

    /**
     * Methode pour mettre a jour les deplacements des mobiles
     * et leur comportement.
     * 
     */
    public void update(GameContainer gc, int delta)
        throws SlickException
    {   
        /* ici va le code qui modifie les valeurs de x et de y en fonction de delta */
    }

    /**
     * Methode pour mettre a jour les deplacements des mobiles
     * et leur comportement en interagissant avec la classe Jeu.
     * 
     */
    
	public void update ( GameContainer gc , int delta , Jeu game) throws SlickException{	
	}

    /**
     * Methode pour afficher un mobile.
     * 
     */
    public void render(GameContainer gc, Graphics g)
            throws SlickException
    {
        image.draw(x,y) ;
    }
    /**
     * 
     * @return 
     */
    
    /**
     *  Obtenir la position X d'un mobile 
     * @return la position verticale du mobile
     */
    public float getX (){
        return x;
    }

    /**
     * Obtenir l'image du mobile
     * @return l'image du mobile.
     */
    public Image getImage() {
        return image;
    }
    
    /**
     * Methode permettant d'obtenir l'ordonnée du mobile
     * @return l'ordonnée du mobile.
     */
    public float getY (){
        return y;
    }
    
    
    /**
     * Methode permettant d'obtenir la categorie 
     * d'un mobile quelconque.
     * @return la categorie du mobile 
     */
    public int getCat (){
        return categorie;
    }
    
    /**
     * Obtenir le contour du mobile
     * @return contour du mobile 
     */
    
    public Shape getContour(){
    	return contour;
    }

    /**
     * Mettre un nouveau contour 
     * @param newContour le nouveau contour
     */
    public void setContour (Shape newContour ){
    	contour = newContour;
    }
    
    
    /**
     * definir une nouvelle abcisse pour le mobile 
     * @param x nouvelle abcisse pour le mobile
     */
    public void setX ( float x){
        this.x = x;
    }

    /**
     * definir une nouvelle ordonnée pour le mobile 
     * @param yy nouvelle ordonnée pour le mobile
     */
    public void setY ( float yy){
        this.y = yy;
    }
    
    /**
     * Definir une categorie pour le mobile 
     * @param nCat categorie pour le mobile 
     */
    public void setCat ( int nCat ){
        categorie = nCat ;
    }
    
    /**
     * definir une image pour le mobile 
     * @param newImage nouvelle image du mobile
     */
    public void setImage( Image newImage){
        this.image= newImage;
    }

    /**
     * verifie si le mobile est hors cadre.
     * 
     */
    public boolean mobileHorsCadre (GameContainer gc){
         if (this.getX() > gc.getWidth() ||this.getX() < 0 ){
            return true;
        }
        return false;
    }
    
    /**
     * verifie si le mobile est hors cadre.
     * 
     */
    public void verifierPositionMobile(GameContainer container) {
        if ( y > container.getHeight()-image.getHeight())
            y =  container.getHeight()-(image.getHeight());
        else 
            if ( y < 0 )
                y = 0 ;
        
        if ( x > container.getWidth()-image.getWidth())
            x =  container.getWidth()-(image.getWidth());
        else 
            if ( x < 0 )
                x = 0 ;
    }
    /**
     * Methode permet de balancer le mobile sur la verticale
     * 
     */
    public boolean balancerVerticalement (GameContainer container, boolean etat_precedent) {
        if ( getY() > container.getHeight()-getImage().getHeight()){
    		return false;
    	}
    	else{
    		if ( getY() < 0 ){
    			return true;
    		}
    	}
        return etat_precedent ;
    }
    /**
     * Methode permettant de voir si on detruit le mobile ou pas.
     * 
     */
    public boolean detruireMobile (GameContainer container , Mobile met ){
        if (getContour().intersects(met.getContour())){
            return true;
        }
        return false;
    }
}

