
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rahul
 */
public class Cache {
    
    private static final String IMAGE_DIR = "images/";
    public static Image player1;
    public static Image player2;
    public static Image bullet;
    
    public Cache(){
        load();
    }
    
    public void load(){
        Cache.player1 = loadImage("player1.png"); //load image for player 1
        Cache.player2 = loadImage("player2.png"); //load image for player 2
        Cache.bullet = loadImage("bullet.png");   //load image of bullet
    }
    
    public Image loadImage(String img){
        try {
           return ImageIO.read(new File(IMAGE_DIR + img)); //return buffered image back
        } catch (IOException ex) {
           System.err.println("Caught IOException: " + ex.getMessage());
           return null;
        }
    }
}
