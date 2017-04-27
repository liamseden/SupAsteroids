/**
 * Classe principale pour les objets de type meteorite.
 * Permet de creer plusieurs types de meteorites qu'on peut 
 * choisir en utilisant leur categorie.
 * 
 * On distingue plusieurs methodes de deplacement de meteorites
 * selon la categorie.
 * 
 * Chaque meteorite possede sa propre vitesse et sa propre forme.
 * Un meteorite est detruit lorsqu'il entre en collision avec une
 * roquette ou avec un vaisseau du joueur.
 * 
 * 
 * 
 * @author Smail MEDJADI 
 * @version 1.0
 * @date	21/11/2015
 */


package elements;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;

import jeu.Jeu;

public class Meteorite extends Mobile {
	
    private boolean direction = true ;
    private Gif halo ;
    
    /**
     * Constructeur qui cree une meteorite de categorie 2 si aucune categorie 
     * n'est choisie. 
     * avec les positions x et y.
     * 
     */
    public Meteorite(float x, float y) throws SlickException {
        super(x,y,"ressources/meteorite_2.png");
        setContour(new Circle ( x , y , getImage().getWidth()/2 - 10 ));
    }
    
    /**
     *  Constructeur le plus utilise pour creer les meteorites,
     *  permet de choisir la categorie de la meteorite a creer 
     *  ainsi que sa position dans l'espace.
     *
     */
    public Meteorite(float x, float y, int cat) throws SlickException {
        super(x,y,"ressources/meteorite_"+cat+".png");
        setCat (cat);
        
        switch ( cat){
            case 2 :
                setContour( new Circle ( x , y , getImage().getWidth()/2 - 10 ));
            break;
            
            case 3 :case 5 :case 7 :case 9 : case 12 :
                setContour( new Circle ( x , y , getImage().getWidth() - 100 ));
                halo = new Gif (7);
            break;
            
            case 4 :case 6 :case 8 : case 10 :case 11 :
                setContour( new Circle ( x , y , (getImage().getWidth()-55)/2 ));
                halo = new Gif (6);
            break;
                
            default : 
                setContour( new Circle ( x , y , getImage().getWidth()/2 ));
            break;
        }
    }

    /**
     * Methode qui permet de mettre a jour la position et la 
     * vitesse des meteorites selon leur categorie 
     */
    public void update(GameContainer gc, int delta, Jeu game )
            throws SlickException
        {
        //Methode pour déterminer la direction de la meteorite
		direction =this.balancerVerticalement(gc,direction);    	 
		
        //Mettre a jour les meteorites en fonction de leur categorie
        switch (getCat()){
            case 2 :
                getImage().rotate(10*delta* 0.001f*20);
                getContour().setLocation(getX()+10,getY());
            break;

            case 3 :case 5 :case 7 :case 9 : case 12 :
            	halo.update(gc, delta);
            	if (!direction) 
            		setY( getY() -  (delta * 0.001f*350));
                else     
                	setY( getY() +  (delta * 0.001f*350));
                getContour().setLocation(getX(),getY());
            break;
            
            case 4 :case 6 :case 8 : case 10 :case 11 :
            	halo.update(gc, delta);
            	if ( this.getX() < 2*gc.getWidth()/3){
	            	if (!direction) 
	                	setY( getY() -  (delta * 0.001f*250));
	                else     
	                	setY( getY() +  (delta * 0.001f*250));
            	}
            	
                getContour().setLocation(getX()+14,getY());
	                
	        break;
        }
        //Deplacement de meteorites selon la categorie ( plus la categorie est grande, plus la vitesse l'est)
        setX( getX() -  (delta * 0.001f*getCat()*80));

        
        //Mise a jour des position des Meteorites et suppression en cas de sortie du cadre
        if (this.meteoriteHorsCadre(gc)){
            game.deleted_mobiles.add(this);
            Jeu.NB_METEORITES--;
         }
    }
    
    /**
     * Methode permettant d'afficher les meteorites et leurs contours en forme
     * d'animation, les animations different selon la categorie.
     *
     */
    public void render(GameContainer gc, Graphics g)
            throws SlickException
    {
    	// Affichage des contours animés 
    	switch ( getCat()){
    	
        case 3 :case 5 :case 7 :case 9 : case 12 :
			halo.render(gc, g, getX()+20, getY());
    	break;
    	
        case 4 :case 6 :case 8 : case 10 :case 11 :

        	halo.render(gc, g, getX()+50, getY());
    	break;    	
        }
    	// Affichage de la meteorite
        getImage().draw(getX(),getY()) ;
    }
    /**
     * Methode qui retourne true si la meteorite est sortie 
     * du container, false sinon.
     */
    
    public boolean meteoriteHorsCadre (GameContainer gc){
        return mobileHorsCadre(gc);
    }
}