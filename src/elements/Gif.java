/**
 * Classe Gif qui permet de generer des animations
 * a partir d'une suite de frames d'images.
 * @author Smail Medjadi
 * @version 1.0
 * @date	24/01/2016
 */
package elements;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Gif {
	private Animation animation;
	private Image image_animation ;
	private int categorie ;

	
	/**
	 * Constructeur principal, qui construit l'animation correspondante
	 * a la categorie donnee en entree. 
	 * 
	 */
	public Gif ( int categorie ) throws SlickException{
		this.categorie = categorie;
		this.image_animation = new Image ("ressources/fire_0"+categorie+".png");
		switch ( this.categorie ){
			case 1: 
				this.setAnimation(this.image_animation, 8,8, 128,128,64,30);
			break;
			case 2:
				this.setAnimation(this.image_animation, 12, 1 , 96 , 96 , 12 ,30);
				break; 
			case 4:case 5:
				this.setAnimation(this.image_animation, 6, 1 , 75 , 69 , 6 ,30);
			break;
			case 6:
				this.setAnimation(this.image_animation, 8,8, 128,64,64,30);
				break;
			case 7:
				this.setAnimation(this.image_animation, 8,8, 128,40,64,30);
				break;
			default : 
				break;
		}
	}
	
	/**
	 * Affichage de l'animation suivant plusieurs parametres 
	 * 
	 */
	public void render(GameContainer gc, Graphics g, float x , float y) throws SlickException {
    	this.animation.draw(x,y);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		animation.update(delta);
	}
	
	/**
	 * Methode qui permet de transformer l'image en animation, 
	 * le principe est de recuperer des frames et de les defiler
	 * a une vitesse assez elevee. 
	 * 
	 */
	public void setAnimation ( Image i , int nbLignes, int nbColonnes , int spriteWidth , int spriteHeight, int frames, int duration ){
		this.animation = new Animation(false);
		int c = 0;
		for( int y = 0 ; y < nbColonnes; y++){
			for( int x = 0 ; x < nbLignes; x++){
				if( c < frames ){
					animation.addFrame( i.getSubImage( x*spriteWidth , y*spriteHeight , spriteWidth , spriteHeight), duration);
					c++;
				}
			}
		}
	}

	public Animation getAnimation (){
		return animation ;
	}
}
