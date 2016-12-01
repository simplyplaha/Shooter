
import java.awt.Graphics;
import java.awt.Image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rahul
 */
public class Bullet extends GameObject{
    
    public int deltaX;
    
    public Bullet(int x, int y, int width, int height, Image img){
      super(x,y,width,height,img);
}

    @Override
    public void update() {
       x = x+ deltaX; //update the bullet cords
       rect.x+=deltaX; //update hitbox
       
       if (Shooter.getInstance().getPlayer1().rect.contains(this.rect)){
            Shooter.getInstance().getPlayer1().health--; //subtract player 1's health
           removeBullet(); //remove bullet object if it makes contact with player 2 
       } else if(Shooter.getInstance().getPlayer2().rect.contains(this.rect)){
           Shooter.getInstance().getPlayer2().health--; //subtract player 2's health 
           removeBullet(); //remove bullet object if it makes contact with player 2
       }
       
       
           if (x>Shooter.getInstance().getWidth()+15){
               removeBullet(); //remove offscreen bullet
           }else if(x<-15){
               removeBullet(); //remove offscreen bullet
           }
       }
    

    @Override
    public void draw(Graphics g) {
         g.drawImage(img,x,y,width,height,null); 
    }
    
    private void removeBullet(){
        Shooter.getInstance().getBullets().remove(this); //remove bullet object
    }
}
