/**
 * Classe Menu qui gere l'etat du jeu quand on est dans le 
 * menu.
 * 
 * 
 * 
 * @author Smail MEDJADI 
 * @version 1.0
 * @date	20/01/2016
 */

package jeu;


import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {

	  public static final int ID = 1;
	  private Image background;
	  private StateBasedGame game;

	  @Override
	  public void init(GameContainer container, StateBasedGame game) throws SlickException {
	    this.game = game;
	    this.background = new Image("ressources/background0.png");
	  }

	  /**
	   * Contenons nous d'afficher l'image de fond. 
	   * Le text est plac� approximativement au centre.
	   */
	  @Override
	  public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	    background.draw(0, 0, container.getWidth(), container.getHeight());
	  }

	  /**
	  * Passer � l��cran de jeu � l'appui de n'importe quel touche.
	  */
	  @Override
	  public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	  }
	  
	  @Override
	  public void keyPressed(int key, char c) {
		  if (key == Input.KEY_ENTER) {
			  game.enterState(FenetreJeu.ID);
	      }
	  }
		  
	  @Override
	  public void keyReleased(int key, char c) {
		  if (key == Input.KEY_ESCAPE){
			  System.exit(0);
	      }
	  }

	  /**
	   * L'identifiant permet d'identifier les diff�rentes boucles.
	   * Pour passer de l'une � l'autre.
	   */
	  @Override
	  public int getID() {
	    return ID;
	  }
	}