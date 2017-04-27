/**
 * Classe qui definit l'etat de la fenetre en mode jeu en 
 * cours, elle est reconnue par son ID ( 2 ), la classe hérite 
 * de BasicGameState qui est une Classe spéciale de Slick2D 
 * pour créer des états de jeu.
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

public class FenetreJeu extends BasicGameState {
  
	public static final int ID = 2;
	private StateBasedGame game;
	Jeu partie = new Jeu ();

		@Override
	  /**
	   * methode servant a initialiser l'environnement de jeu
	   *
	   */
	  public void init(GameContainer container, StateBasedGame game) throws SlickException {
	      //Initialisation de la taille de la fenetre 
		  this.game = game;
	      ((AppGameContainer)container).
	      		setDisplayMode((int)(container.getScreenWidth()*0.7), (int)(container.getScreenHeight()*0.7),false);
	      partie.init(container);
	  }
  /**
   * 
   *
   */
	  @Override
	  public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	    // affichage (cf le�on pr�c�dente)
		  partie.render(container, g);
	  }
	
	  @Override
	  public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	    // mise � jour (cf le�on pr�c�dente)
		  partie.update(container, delta);
	  }
	
	  @Override
	  public void keyReleased(int key, char c) {
		  if (key == Input.KEY_F2) {
			  game.enterState(Menu.ID);
		    
		  }
		  if (key == Input.KEY_ENTER ){
			  partie.background_music.playAsMusic(1.0f, 1.0f, true);
	      }
	
		  partie.keyReleased(key, c);
	
	  }
	
	  @Override
	  public void keyPressed(int key, char c) {
		  partie.keyPressed(key, c);
	  }
  
	  	@Override
  		public int getID() {
		  	return ID;
	  	}
	}
