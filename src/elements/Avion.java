package elements;
/**
 *	Classe principale pour le type Avion.
 *  c'est le vaisseau principal du joueur, ces principales caracteristiques sont
 *  sa vitesse de déplacement, sa portée de missiles, son etat ( la vie ) ainsi
 *  la possibilite de se deplacer partout.
 * 
 *  Cette classe herite de la classe Mobile.
 * 
 * 
 * @author Smail MEDJADI 
 * @version 1.0  
 * @date	23/01/2016
 */
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import jeu.Jeu;

public class Avion extends Mobile {
   
	/**
	 * Constructeur pour l'objet avion, initialise l'avion à la position 320x240
	 * et charge l'image correspondante et initialise son contour sous forme de polygone. 
	 * 
	 */
    public Avion () throws SlickException{
        super(320,240,"ressources/plane.png");
        float [] tab = {getX() + getImage().getWidth() ,getY() + getImage().getHeight()/2,
        		getX()+getImage().getWidth()/2  ,getY()+getImage().getHeight()*2/3+5,
        		getX()+getImage().getWidth()/3  ,getY()+getImage().getHeight(),
        		getX(),getY()+getImage().getHeight()*3/4,
        		getX(), getY()+getImage().getHeight()/4,
    			getX()+getImage().getWidth()/3  ,getY(),
    			getX()+getImage().getWidth()/2  ,getY()+getImage().getHeight()/3-5,
	    };
        setContour(new Polygon (tab));
    }

    public void update(GameContainer gc, int delta , Jeu game)
        throws SlickException
    {
        float delta_sec = delta * 0.001f;
        Input input = gc.getInput();
        //Vitesse 
        int vitesse = 400 ;
        // rotation du mobile de 10° par seconde
        getImage().rotate(0*delta_sec);

        // déplacement du mobile, à 100 pixels/s
        // dans une direction dépendant des touches enfoncées
        
        if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN)
               || input.isKeyDown(Input.KEY_LEFT)|| input.isKeyDown(Input.KEY_RIGHT)){
            //son.play();
        }
           
        //setX( getX() - vitesse * delta_sec);

        if (input.isKeyDown(Input.KEY_UP)) {
            setY( getY() - vitesse * delta_sec);
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            setY( getY() + vitesse * delta_sec);
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            setX( getX() + vitesse * delta_sec);
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            setX( getX() - vitesse * delta_sec);
        }
        //Tourner dans le sens des aiguilles de la montre
        if (input.isKeyDown(Input.KEY_D)) {
            getImage().rotate(-120*delta_sec);
        }
        
        //Tourner dans le sens des aiguilles de la montre
        if (input.isKeyDown(Input.KEY_Q)) {
            getImage().rotate(+120*delta_sec);
        }
        
        //Mette à jour la position des shapes
        getContour().setLocation(getX(),getY());
        /* ici va le code qui modifie les valeurs de x et de y en fonction de delta */
        /* (delta étant le temps passé depuis le dernier appel à cette méthode) */
        verifierPositionMobile(gc);
        
    }

    public void render(GameContainer gc, Graphics g)
            throws SlickException
    {
        getImage().draw(getX(),getY()) ;
    }
   
   
    /**
     * Initialiser un avion a la position 320x240.
     */
    public void setPositionParDefaut (){
        setX(320);
        setY(240);
    }
    
    /**
     * Methode pour verifier la position de l'avion afin 
     * de l'empecher de sortir du cadre.
     */
    public void verifierPositionMobile(GameContainer container) {
        if ( getY() > container.getHeight()-getImage().getHeight())
            setY(  container.getHeight()-(getImage().getHeight()) );
        else 
            if ( getY() < 0 )
                setY(0) ;
        
        if ( getX() > container.getWidth()-getImage().getWidth())
            setX( container.getWidth()-(getImage().getWidth()));
        else 
            if ( getX() < 0 )
                setX(0) ;
    }

}

