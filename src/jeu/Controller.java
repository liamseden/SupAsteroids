/**
 * La classe Controller est la classe qui gere les deux etats 
 * du jeu ( Menu et Jeu en cours)
 * 
 * La classe permet d'instancier un container et de lancer 
 * le menu afin de donner la main à l'utilisateur en interaction 
 * grace au clavier.
 * 
 * 
 * @author Smail MEDJADI 
 * @version 1.0
 * @date	20/01/2016
 */
package jeu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Controller extends StateBasedGame {
	
	static AppGameContainer container ;
	
	/**
	 * Methode principale du programme qui permet de lancer un container.
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		container = new AppGameContainer(new Controller(), 800, 600, false);
		container.setDisplayMode((int)(container.getScreenWidth()*0.7), (int)(container.getScreenHeight()*0.7),false);
		container.start();
		}

	/**
	 * Constructeur
	 * 
	 */
	public Controller() {
		super("Asteroids");
	}
	 /**
	  * Methode qui permet d'initialiser les 2 objets et de les rajouters aux états 
	  * du jeu.
	  */
	  @Override
	  public void initStatesList(GameContainer container) throws SlickException {
		  addState(new Menu());
		  addState(new FenetreJeu());
	  }
}
