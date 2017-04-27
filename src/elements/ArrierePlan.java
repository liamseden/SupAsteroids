/**
 * Classe principale pour les objets de type ArrierePlan.
 * 
 * ça permet de créer des arriere plan pour le jeu et de le changer 
 * au fil de l'avancement du jeu. 
 * Chaque arriere plan correspond a un niveau precis du jeu. 
 * 
 * 
 * @author Smail MEDJADI 
 * @version 1.0
 * @date	21/11/2015
 */

package elements;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ArrierePlan {
    /** Images */
    private Image ciel_image;
    private Image ciel_image_flipped;
    private float ciel_X = 0;
    private float ciel_flipped_X = 0;
    /**
     * Constructeur principal, avec le niveau on choisit l'image 
     * correspondante a l'arriere-plan.
     * 
     */
    public ArrierePlan (int niveau) throws SlickException{
    	this.ciel_image = new Image("ressources/land"+niveau+".png");
    	this.ciel_image_flipped = ciel_image.getFlippedCopy(true, false);
        
    }
    
    /**
     * Methode pour redimensionner l'arriere plan et le mettre 
     * a l'echelle du conteneur.
     * 
     */
    public void redimensionnerETPositionner ( GameContainer container ){
    	ciel_image = ciel_image.getScaledCopy(container.getWidth(), container.getHeight());
        ciel_image_flipped = ciel_image_flipped.getScaledCopy(container.getWidth(), container.getHeight());
        
        ciel_flipped_X = ciel_image.getWidth();
    }


    public void update(GameContainer gc, int delta)
            throws SlickException
    {  
    	float delta_sec = delta * 0.001f;    
        // deplacement du fond d'ecran, a� une vitesse de 130 pixels par seconde vers la gauche
        ciel_X = ciel_X - 100 * delta_sec;
        if (ciel_X < -ciel_image.getWidth()) ciel_X = ciel_flipped_X + ciel_image_flipped.getWidth() ;
        ciel_flipped_X = ciel_flipped_X - 100 * delta_sec;
        if (ciel_flipped_X < -ciel_image_flipped.getWidth()) ciel_flipped_X = ciel_X + ciel_image.getWidth() ;
        
        /* ici va le code qui modifie les valeurs de x et de y en fonction de delta */
        /* (delta etant le temps passe depuis le dernier appel a� cette methode) */
    }

    public void render(GameContainer gc, Graphics g)
            throws SlickException
    {
    	g.drawImage(ciel_image, ciel_X, 0);
        g.drawImage(ciel_image_flipped, ciel_flipped_X, 0);
    }
    
    
    
    public float  getCielX(){
    	return this.ciel_X;
    }
    
    public float  getCielFlippedX(){
    	return this.ciel_flipped_X;
    }
    
    public Image getCiel (){
    	return ciel_image;
    }

    public Image getCielFlipped (){
    	return ciel_image_flipped;
    }
    
    public void setCielX ( float x){
    	ciel_X = x;
    }
    public void setCielFlippedX ( float x){
    	ciel_flipped_X = x;
    }
    
    public void setCiel ( Image image ){
    	ciel_image = image ; 
    	this.ciel_image_flipped = ciel_image.getFlippedCopy(true, false);
    	
    }
    
    
}