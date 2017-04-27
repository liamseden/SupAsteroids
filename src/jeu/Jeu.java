/**
 * Classe principale qui definit tout les elements
 * d'une partie pour créer le jeu.
 *  tout est instancie dans cette classe, les mobiles,
 *  les compteurs, les arrieres-plan.
 * @author Smail MEDJADI 
 * @version 1.0
 * @date	20/01/2016
 */
package jeu;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import org.newdawn.slick.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import elements.Avion;
import elements.Gif;
import elements.ArrierePlan;
import elements.Hud;
import elements.Meteorite;
import elements.Mobile;
import elements.Roquette;
import elements.VaisseauEnnemi;
import elements.Bonus;

public class Jeu  extends BasicGame {
    /** Position du mobile */
	public static int ENNEMIS_TUES = 0;
	public static int NB_VAISSEAUX = 0;
	public static int NB_METEORITES = 0;
	
	/**Bonus */
	public static int NB_MISSILES = 1;
	
    /** Liste de tous les objets */
    public ArrayList <Mobile> mobiles ;
    public ArrayList <Mobile> new_mobiles ;
    public ArrayList <Mobile> deleted_mobiles ;
    
	/** Images */
    public ArrierePlan level = null;
    
    /** HUD */
    public Hud hud = new Hud ();
    
    /** Images GameOver */
    public Image gameover ;  
    
    /** Animation carburant joueur*/
    public Gif carburant ;
    
    /** Musique du jeu */
	public Audio background_music;       
	public Audio shotgun_2;

	/** Differents compteurs */
    public long compteur_lancement_roquettes = 0;
    public long compteur_bonus = 0;
    public long compteur_projection_meteorites = 0;
    public int nombre_meteorites_ecran = 0;
    public int niveau = 0 ;
    public float vie = 1.0f ;
    public float facteur_vitesse = 1;
    
    /** Mode (  1 = Story ; 2 = Arcade )  */
    public int  MODE = 1 ;
	
    
    /** Test de passage au niveau bonus */
    private boolean passage  = false;
    /**
     * Construction de l'objet JeuBasique
     */
    public Jeu() {
        // appel au constructeur de la classe parente : BasicGame
        super("Jeu basique");
    }

    
    /**
     * Initialisation du jeu
     */
    public void init(GameContainer container) throws SlickException {
    	// On demande au conteneur d'essayer d'afficher 100 images par secondes
        container.setTargetFrameRate(100);
        
        // On demande au conteneur d'afficher le nombre d'images par secondes reel
        container.setShowFPS(true);
        
        // Affichage en plein ecran : non
        container.setFullscreen(false);
        
        // Creation de la liste des mobiles 
        mobiles = new ArrayList <Mobile> () ;
        new_mobiles = new ArrayList <Mobile> ();
        deleted_mobiles = new ArrayList <Mobile> ();
        
        //On cree l'avion du joueur et on l'ajoute a la position "0"        
        mobiles.add(0,new Avion ());
        
        // creation du vaisseau ennemi
        mobiles.add(new VaisseauEnnemi(720 , 320));

        //La musique en arriere-plan
        try {background_music  = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sound/back2.ogg"));}
        catch (IOException e) {e.printStackTrace();}
        
        //On cree un l'effet son de tir d'un missile de l'avion joueur  
        try {shotgun_2  = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sound/shotgun.wav"));}
        catch (IOException e) {e.printStackTrace();}
        
        /* On charge les images depuis le disque
         * On redimensionne le papier peint a la taille de la fenetre
           Et on en cree une copie retournee verticalement
         */
        level = new ArrierePlan (niveau%10);
        level.redimensionnerETPositionner(container);
        gameover = new Image ("ressources/gameover.png");
        
        //HUD ( indication du niveau de vie en rouge)
        this.hud.init();
        
        //Animation du carburant brule par l'avion
		carburant = new Gif(1);
        
		//Initialisation des attributs du jeu
        nombre_meteorites_ecran = 10;
        ENNEMIS_TUES = 0;
        niveau = 0 ;
        compteur_lancement_roquettes = 0;
        
    }

    /**
     * Affichage du jeu
     * La methode render est appelee regulierement par le framework Slick
     * Elle reçoit automatiquement deux parametres : le "container" (qui represente la fenetre
     * dans laquelle le jeu apparaît), et un "contexte graphique" qui represente la surface à l'interieur
     * de cette fenetre. C'est ce contexte graphique que l'on utilise pour tracer les objets.
     */
    
    public void render(GameContainer container, Graphics g) throws SlickException {
    	//Initialisation de l'environnement (Nouvelle partie) 
    	if ( vie < 0.0f){
    		gameover.draw(0, 0, container.getWidth(), container.getHeight());
    		background_music.stop();
	        this.init(container);
    	} 
   
   
    	//En cours de partie
    	else {
	    	// affichage du papier peint
	    	level.render(container, g);
	    	
	    	//Affichage des elements mobiles 
	    	for ( Mobile m: mobiles ){
	    		m.render(container, g);
	    	}

	    	// Animation du de l'animation du carburant du mobile
        	carburant.render(container, g, mobiles.get(0).getX()-118,mobiles.get(0).getY()-25);    	        	
        	
        	//Affichage de l'avion du joueur
        	mobiles.get(0).render(container, g);

	        //Affichage du nombre de victimes 
	        g.drawString(ENNEMIS_TUES+" ennemis tues !", 0, container.getHeight()-20);	        
	        g.drawString("Niveau : "+niveau, 0, container.getHeight()-40);
	        
	        //Affichage du HUD
	        hud.render(g);
        }
    }

    /**
     * Mise-à-jour des elements du jeu
     * La methode update est appelee regulierement par le framework Slick (avant chaque appel
     * à la methode render).
     * Elle recoit automatiquement deux parametres : le "container" (qui represente la fenetre
     * dans laquelle le jeu apparait), et un "delta". Ce "delta" represente le nombre de millisecondes
     * qui se sont ecoulees depuis la derniere mise-à-jour.
     */
    public void update(GameContainer container, int delta) throws SlickException {
    	// recuperation d'un objet "input" depuis le "containeur"
    	Input input = container.getInput();
        carburant.getAnimation().update(delta);
        
        //Empecher le mobile de sortir du conteneur
        mobiles.get(0).verifierPositionMobile(container);    
        
        // mise a jour des listes
        removeMobilesDetruits ();
        addNewMobiles();

        // Methode de mise a jour du niveau
        this.updateNiveauetScore(container, delta);
        
        //Mettre a jour tous les objets Mobile
        for ( Mobile m : mobiles){
    		m.update(container,delta,this);
    	}
        
        /*************************** Collisions *********************************/
        //entre une METEORITE et une ROQUETTE 
        
        for ( Mobile met : mobiles ){ 
        	if ( met instanceof Meteorite ){
	            for ( Mobile roq : mobiles ){
	            	if ( (roq instanceof Roquette) && (met.detruireMobile(container, roq))){
	                	deleted_mobiles.add(met);
	            		deleted_mobiles.add(roq);
	                    ENNEMIS_TUES ++;
	    	            NB_METEORITES--;
	    	            break;
	            	}
	            }	
        	}	
    	}
        	
        //entre le MOBILE et une METEORITE
        for ( Mobile met : mobiles ){ 
        	if ( met instanceof Meteorite ){
        		if ( mobiles.get(0).detruireMobile(container, met)){
	            	vie-=0.1f;
	            	hud.diminuerVie();
	            	deleted_mobiles.add(met);
		            NB_METEORITES--;
        		}
        	}
    	}

        /****************************Lancement de ROQUETTES***********************************/
        compteur_lancement_roquettes += delta;
        if (input.isKeyDown(Input.KEY_SPACE)){
            if ( compteur_lancement_roquettes >= 180 - this.facteur_vitesse  && NB_MISSILES > 0 ){
            	//Lancement d'un thread pour jouer la musique du tir roquette
            	shotgun_2.playAsSoundEffect(1.0f, 0.05f, false);
            	//Tir selon le nombre de missiles du joueur
            	switch ( NB_MISSILES ) {
            	case 1 : 
            		new_mobiles.add(new Roquette(mobiles.get(0).getX()+1 , mobiles.get(0).getY() + 7));
            	 	break;
            	case 2 : 
            		new_mobiles.add(new Roquette(mobiles.get(0).getX()+1 , mobiles.get(0).getY() + 7 + 10 ));
            		new_mobiles.add(new Roquette(mobiles.get(0).getX()+1 , mobiles.get(0).getY() + 7 - 10 )); 
            	 	break;
        	 	default : break;
            	}	
                compteur_lancement_roquettes = 0;
            }
        }
 
        /***********************La projection de meteorites ******************************/
        Random rand = new Random();
        compteur_projection_meteorites += delta;
        
        // de nouvelles meteorites apparaissent a chaque montee de niveau
        if (compteur_projection_meteorites > rand.nextInt(300)+500 ){
	        if ( NB_METEORITES < nombre_meteorites_ecran){
	            new_mobiles.add(new Meteorite (container.getWidth(),
	                    (float) (rand.nextInt(container.getHeight()-128)),rand.nextInt(niveau+1)+2));
	            NB_METEORITES++;
	        }
        }
        
        
        /****************************Apparition des bonus ****************************************/
        compteur_bonus += delta ;
        if ( compteur_bonus > 10000 ){
        	new_mobiles.add( new Bonus (container.getWidth(),
                    (float) (rand.nextInt(container.getHeight()-128)),rand.nextInt((7))+1));	
        	compteur_bonus = 0 ;
        }
    }
    
    
    /**
     * Methode servant a mettre a jour le niveau de jeu de la partie courante et aussi mettre 
     * a jour l'arriere-plan.
     * 
     * @param container
     * @param delta
     * @throws SlickException
     */
    
    public void updateNiveauetScore(GameContainer container, int delta) throws SlickException {
        level.update(container, delta);            
        //Gestion des niveaux
        switch ( niveau ) {
        case 0 :case 1 :case 2 :case 3:case 4: case 5: case 6: case 7: case 8: case 9:
			if ( ENNEMIS_TUES > (100*(niveau+1))){ 
				niveau++;
				if ( NB_VAISSEAUX <= 0 + ( niveau / 4 ) ){
					new_mobiles.add(new VaisseauEnnemi(720 , 320));
			 		NB_VAISSEAUX++;
			    }	
				level.getCiel().destroy();
				level.getCielFlipped().destroy();
			 	level = new ArrierePlan ( (niveau % 10));
			    level.redimensionnerETPositionner(container);  
			}
        break;
        
        case 10 : 
        //Level bonus
        	if ( !passage ){
	        	passage = true;
        		nombre_meteorites_ecran += 5;
	        	level.getCiel().destroy();
	        	level.getCielFlipped().destroy();
	        	level = new ArrierePlan(100);
	            level.redimensionnerETPositionner(container);
	            
	            for ( int i = 0 ; i<3; i++ ){
	            	new_mobiles.add(new VaisseauEnnemi(1000 , 120 + i*200));
	            }
        	}
        break;
        default : break;
        }
    }
    
    /**
     * Une autre facon de reperer lorsqu'une touche est enfoncee : la methode keyPressed
     * appelee automatiquement par Slick a chaque touche enfoncee
     */
    
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            System.exit(0);
        }
	  	  if (key == Input.KEY_C) {
	  		  if ( vie <= 0.0f){
		          //Recommencer la partie
		  		  vie = 1f;
		  		  passage = false;
		  		  NB_MISSILES = 1 ; ENNEMIS_TUES = 0 ;
		  		  NB_VAISSEAUX = 0 ; NB_METEORITES = 0 ;
		  		  hud.reanimer();
				  background_music.playAsMusic(1.0f, 1.0f, true);
	  		  }
	  	  }
    }
    
    /**
     * Methode qui supprime les mobiles detruits de la liste.
     */
    public void removeMobilesDetruits (){
		mobiles.removeAll(deleted_mobiles);
		deleted_mobiles.clear();
    	
    }
    public void addMobile(Mobile m) {
        new_mobiles.add(m);
	}
	
	public void removeMobile(Mobile m) {
        deleted_mobiles.add(m);
	}
	
	public void addNewMobiles()
	{
        mobiles.addAll(new_mobiles);
        new_mobiles.clear();
	}
}