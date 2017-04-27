/**
 * Classe permettant de creer l'objet qui sert
 * de jauge de vie, on l'appelle un HUD.
 * 
 * Y a aussi 2 champs non exploités qui sont ceux 
 * du MANA et de l'XP.
 * 
 * 
 *
 * @author Smail Medjadi
 * @version 1.0
 * @date	24/01/2016
 */

package elements;
import org.newdawn.slick.Image;
import org.newdawn.slick.*;

public class Hud {
	private Image playerbars;

	private static final int P_BAR_X = 10;
	private static final int P_BAR_Y = 10;
	
	private static final int BAR_X = 84 + P_BAR_X;
	
	private static final int LIFE_BAR_Y = 4 + P_BAR_Y;
	private static final int MANA_BAR_Y = 24 + P_BAR_Y;
	private static final int XP_BAR_Y = 44 + P_BAR_Y;
	
	private static final int BAR_WIDTH = 80;
	private static final int BAR_HEIGHT = 16;
	
	private static final Color LIFE_COLOR = new Color(0, 0, 255);
	
	private static float vie = 1.0f;

	/**
	 * Methode d'initialisation de l'image du HUD. 
	 * 
	 */
	public void init() throws SlickException {
		this.playerbars = new Image("ressources/player-bar.png");
	}
	
	/**
	 * Methode permettant d'afficher le HUD.
	 */
	public void render(Graphics g) {
	  g.setColor(LIFE_COLOR);
	  g.fillRect(BAR_X, LIFE_BAR_Y, vie* BAR_WIDTH, BAR_HEIGHT);
	  g.fillRect(BAR_X, MANA_BAR_Y, vie* BAR_WIDTH, BAR_HEIGHT);
	  g.fillRect(BAR_X, XP_BAR_Y,   vie* BAR_WIDTH, BAR_HEIGHT);
	  g.drawImage(this.playerbars, P_BAR_X, P_BAR_Y);
	  g.resetTransform();
	}
	/**
	 * Methode permettant de diminuer la vie et ainsi changer l'etat du HUD.
	 */	
	public void diminuerVie (){
		vie -= 0.1f;
	}
	/**
	 * Methode permettant d'ajouter la vie et ainsi changer l'etat du HUD.
	 */	
	public void ajouterVie (){
		if ( vie < 1 )
			vie += 0.1f;
	}
	/**
	 * methode pour obtenir la valeur "vie"
	 * @return la valeur vie
	 */
	public float getVie (){
		return vie;
	}
	/**
	 * methode permettant de recharger la vie à 10
	 */
	public void reanimer (){
		 vie = 1.0f;
	}
}
