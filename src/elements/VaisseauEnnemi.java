/**
 * Classe VaisseauEnnemi qui herite de Mobile.
 * elle represent les vaisseaux ennemis qui 
 * ont comme fonctionnalit� principale de pouvoir 
 * tirer des roquettes.
 * 
 *
 * @author Smail Medjadi
 * @version 1.0
 * @date	24/01/2016
 */

package elements;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import jeu.Jeu;

public class VaisseauEnnemi extends Mobile{
    private boolean direction = true ;
    private ArrayList <Roquette> roq = new ArrayList<Roquette>(10);
    private int compteur ;
    
    /**
     * Constructeur qui genere un vaisseau et actualise son contour 
     * ainsi que sa position.
     * 
     */
    public VaisseauEnnemi(float x, float y) throws SlickException {
	    super( x ,y,"ressources/plane_1.png");        
        float [] contourAJour = {(this.getImage().getWidth())+getX()-40,getY(),
            this.getImage().getWidth()+getX(),(getY()+getImage().getHeight()/2),
            (this.getImage().getWidth())+getX()-40,getY()+getImage().getHeight(),
            getX(),getY() + getImage().getHeight()/2+20};
        setContour(new Polygon (contourAJour));
        
    }
    /**
     * Mettre a jour la position et le comportement de VaisseauEnnemi
     */
    public void update(GameContainer gc, int delta, Jeu game)throws SlickException{
      
    		float delta_sec = delta * 0.001f;
            int vitesse = 0 ;
            /***********************************************************/
            /** Deplacement Vaisseau  **********************************/
            /***********************************************************/
            
            //Verification des bornes pour balancer l'avion dans le cadre 
            direction =this.balancerVerticalement(gc,direction);             

            //Gerer le deplacement de l'avion :
            if ( direction ){
            	setY( getY() + (delta * 0.001f*250));
            }else 
            	if ( !direction ){
            		setY( getY() - (delta * 0.001f*250));
        	}
            
            
            
            // deplacement du mobile, � 100 pixels/s
            // dans une direction dependant des touches enfoncees         
            setX( getX() - vitesse * delta_sec);

            //Mise a jour de la position du vaisseau ennemi
            getContour().setLocation(getX(),getY());
            
            /***********************************************************/
            /**Deplacement Missiles*************************************/
            /***********************************************************/
            //Lancement de missiles a des intervalles aleatoires
            compteur += delta;
            Random rand = new Random ();
            if ( compteur >= (rand.nextInt(3000)+150)){
                    roq.add(roq.size(),new Roquette(this.getX()-1 , this.getY()-7,2));
                    compteur = 0;
            }
            
            //Actualiser la position des missiles 
            int i;
            Roquette r;
            for ( i = 0 ; i< roq.size() ; i++){
                r = roq.get(i);
                if (r != null){
                    r.update(gc, delta);
                    if (r.roquetteHorsCadre(gc)){
                       roq.remove(i);
                    }
                }  
            }
            /***********************************************************/
            /**Gestion de collisions         ***************************/
            /***********************************************************/
            
            //Destruction si collision entre vaisseau ennemi et vaisseau joueur
            if (game.mobiles.get(0).detruireMobile(gc,this)){
            	game.deleted_mobiles.add(this);
            	game.vie-=0.1f;
            	Jeu.ENNEMIS_TUES++;
            	Jeu.NB_VAISSEAUX--;
            	game.hud.diminuerVie();
            }
            
            else {
                for (int  j=0 ; j<this.getListeRoquettes().size(); j++ ){
                    r = this.getListeRoquettes().get(j);
                    //Le cas ou un missile ennemi touche l'avion du joueur
                    if (game.mobiles.get(0).detruireMobile(gc, r)){
                    	game.vie-=0.1f;
                    	game.hud.diminuerVie();
                        this.getListeRoquettes().remove(j);
                    }
                }
            }
         	
            //Collision entre roquette du joueur et un objet VaisseauEnnemi
            for (Mobile m : game.mobiles ){
            	if ( m instanceof Roquette ){
            		if (this.detruireMobile(gc,m)){
	                	game.deleted_mobiles.add(this);
	                	game.deleted_mobiles.add(m);
	                	Jeu.NB_VAISSEAUX--;
	                	Jeu.ENNEMIS_TUES ++;
	                    break;
	                }
            	}
            }
        }

    
    /* ici va le code qui modifie les valeurs de x et de y en fonction de delta */
    /* (delta étant le temps passé depuis le dernier appel à cette méthode) */
    public void render(GameContainer gc, Graphics g)
            throws SlickException
    {
        int i ;
        for ( i = 0 ; i< roq.size() ; i++){
            Roquette r = roq.get(i);
            if (r != null){
                r.render(gc, g);
            }
        }
        //g.draw(contour);
        getImage().draw(getX(),getY()) ;
    }
    
    /**
     * 
     * @return 
     */
    
    /**
     * Obtenir la liste de Roquette du VaisseauEnnemi
     * @return la liste de Roquette
     */
    public ArrayList<Roquette> getListeRoquettes (){
    	return roq;
    }
}