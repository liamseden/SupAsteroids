/**
 * Classe pour les objets de type Bonus,
 * chaque bonus contient sa propre categorie,
 * si sa categorie est < 5, alors c'est un bonus,
 * sinon c'est un malus.
 * La classe Bonus herite de la classe Mobile.
 * 
 * @author Smail Medjadi
 * @version 1.0
 * @date	24/01/2016
 */
package elements;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;

import jeu.Jeu;

public class Bonus extends Mobile {
	private Gif halo ;
	
	/**
	 * Constructeur pour le bonus, avec la position et la 
	 * categorie donnes en parametre
	 * 
	 */
	public Bonus (float x , float y , int cat ) throws SlickException{
		super (x,y,"ressources/power_"+cat+".png");
        setContour( new Circle ( x , y , getImage().getWidth()/2));
        setCat(cat);
        
        switch ( cat ){
	        case  1 : case 2 : case 3 : case 4 :
	            //le spectre des bonus
	            halo = new Gif (4);	        	
	        	break;
	        case  5 : case 6 : case 7: 
	        	 //le spectre des malus
	            halo = new Gif (5);
	        	break;
	        default : break;       
        }

	}
	
	@Override
    public void update(GameContainer gc, int delta , Jeu game )
            throws SlickException
    {
    	
    	halo.update(gc, delta);
    	
    	//Mettre a jour le deplacement du bonus
		setX( getX() -  (delta * 0.001f*200));
	
		if ( this.getContour().intersects(game.mobiles.get(0).getContour())){
        	//Consommer le bonus 
			game.deleted_mobiles.add(this);
	    	
        	switch ( getCat()){
	    	/****************************BONUS************************/
	    	//Augmentation du nombre de missiles 
	    	case  1 : 
	        	if (Jeu.NB_MISSILES < 2 ){
	        		Jeu.NB_MISSILES ++ ;	
	        	}
	        	break;
	        	
			//Augmentation de la vie
	    	case 2  :
    			if ( game.vie < 1.0f ) {
		        		game.hud.ajouterVie();
		        		game.vie += 0.1f; 
		        	}
		    	break;
				
				
			//Double la vitesse de tir du joueur 
	    	case 3 :
	    		if ( game.facteur_vitesse < 100 ) {
	    			game.facteur_vitesse = 100 ;
	        	}
	    	break;

			//Detruit tout les objets visibles sur l'ecran
	    	case 4 :
	    		game.new_mobiles.add(game.mobiles.get(0));
	    		game.deleted_mobiles.addAll(game.mobiles);
	    		Jeu.NB_METEORITES = 0;
	    		Jeu.NB_VAISSEAUX = 0 ;
	    		
			break;
			
			/*********************MALUS*******************************/
			//Diminuer le nombre de missiles tir�s
	    	case  5 : 
	    		if (Jeu.NB_MISSILES > 0 ){
	        		Jeu.NB_MISSILES -- ;	
	        	}
	    		break;
	    		
			//Diminuer la vie
	    	case 6  :
	    			if ( game.vie >= 0.1f ) {
		        		game.hud.diminuerVie();
		        		game.vie -= 0.1f; 
		        	}
		    	break;
				
			//Diminue la vitesse de tir du joueur 
	    	case 7 :
	    		if ( game.facteur_vitesse >= -100 ) {
	    			game.facteur_vitesse += -100 ;
	        	}
			break;
	    	default:break;
	    	}
    	}
        getContour().setLocation(getX(),getY());
        
    }

    public void render(GameContainer gc, Graphics g)
            throws SlickException
    {
    	halo.render(gc, g, getX()-6, getY()-5);
        getImage().draw(getX(),getY());      
        
        //g.draw(getShape());
    }
	
}
