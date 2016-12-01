
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
public class Player extends GameObject{
    
    private static final int SPEED = 5;
    private static final int STARTING_HEALTH=8;
    
    public boolean up;
    public boolean down;
    public int health;
    public int startX;
    public int startY;
    
  public Player(int x, int y, int width, int height, Image img){
      super(x,y,width,height,img);
      
      this.startX=x; //save x start position
      this.startY=y; //save y start position
      
      this.health = STARTING_HEALTH; //starting health for each player
  }

    @Override
    public void update() {
       if (up && y>0){ //make sure players do not move over the boundary
           y = y-SPEED; //move physical rectangle up
           rect.y-=SPEED; //move invisible rectangle hit bot up
       }
       
       if (down && (this.y+this.height<600)){ //make sure players do not move over the boundary
           y = y+SPEED;  ////move physical rectangle down
           rect.y+=SPEED; //move invisible rectangle hit bot down
       }
    }

    @Override
    public void draw(Graphics g) {
       g.drawImage(img,x,y,width,height,null); 
    }
    
    public void reset(){ //reset both players to starting position
        this.x=startX;
        this.y=startY;
        this.rect.x=startX; //reset hitbox
        this.rect.y=startY; //reset hitbox
        this.health=STARTING_HEALTH; //reset health to starting health
    }
}
