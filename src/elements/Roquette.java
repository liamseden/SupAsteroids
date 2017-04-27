/**
 * Classe pour creer les objets de type Roquette.
 * Une roquette correspond au missile lance par 
 * un vaisseau ( ennemi ou joueur).
 * 
 * La classe herite de la classe Mobile.
 * 
 *
 * @author Smail Medjadi
 * @version 1.0
 * @date	24/01/2016
 */

package elements;


import org.newdawn.slick.geom.Rectangle;

import jeu.Jeu;

import org.newdawn.slick.*;

public class Roquette extends Mobile {
    /**
     *	 Constructeur qui permet de generer une roquette a la position (x,y) 
     *  
     */
    public Roquette(float x, float y ) throws SlickException {
        super( x ,y,"ressources/rocket_1.png", 1);
        setContour (new Rectangle (getX()+10,getY()+10,getImage().getWidth()-10,getImage().getHeight()-45 ));
    }
    
    /**
     *	Constructeur qui permet de generer une roquette de 
     *	categorie "cat" a la position (x,y) 
     *  
     */
    public Roquette(float x, float y , int cat ) throws SlickException {
        super( x ,y,"ressources/rocket_"+cat+".png", cat );
        setContour (new Rectangle (getX()+10,getY()+10,getImage().getWidth()-10,getImage().getHeight()-45 ));
    }
    

    /**
     * Methode qui met a jour la position de Roquette selon la categorie.
     */
    public void update(GameContainer gc, int delta)
        throws SlickException
    {
        switch ( getCat()){
            case 1 : 
                this.setX( (delta * 0.001f*500) +  this.getX());        
            break;
            case 2 : 
                this.setX( this.getX() - (delta * 0.001f*500));
            break;
        }
        
        
        getContour().setLocation(getX()+10,getY()+20);

        /* ici va le code qui modifie les valeurs de x et de y en fonction de delta */
        /* (delta étant le temps passé depuis le dernier appel à cette méthode) */
    }

    /**
     * Methode qui met a jour la position de Roquette selon la categorie.
     */
    
    public void update(GameContainer gc, int delta , Jeu game )
            throws SlickException
    {
    	//Deplacement des roquettes 
        switch ( getCat()){
            case 1 : 
                this.setX( (delta * 0.001f*500) +  this.getX());        
            break;
            case 2 : 
                this.setX( this.getX() - (delta * 0.001f*500));
            break;
        }
        //Mise a jour du contour
        getContour().setLocation(getX()+10,getY()+20);
        
        //Gestion des roquettes (Mise a jour de la position et suppression si elle sort du cadre).
        if (this.roquetteHorsCadre(gc)){
            game.deleted_mobiles.add(this);
        }
                
        
        /* ici va le code qui modifie les valeurs de x et de y en fonction de delta */
        /* (delta étant le temps passé depuis le dernier appel à cette méthode) */
    }

    /**
     * Methode pour afficher une roquette
     */
    public void render(GameContainer gc, Graphics g)
            throws SlickException
    {
        getImage().draw(getX(),getY());      
    }
    
    /**
     * Methode qui permet de verifier si la roquette est hors cadre 
     */
    public boolean roquetteHorsCadre (GameContainer gc){
        return mobileHorsCadre(gc);
    }
}
