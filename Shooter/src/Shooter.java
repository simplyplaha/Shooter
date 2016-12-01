
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rahul
 */
public class Shooter extends Canvas implements Runnable, KeyListener{
    
    private static Shooter INSTANCE;
    
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    
    private BufferStrategy bs = null;
    private Graphics graphics = null;
    private Thread thread;
    
    private boolean running = false; //Do not run until everything is loaded
    
    private Player player1;
    private Player player2;
    private boolean gameOver=false;
    private int winner;
    private List<Bullet> bullets = new CopyOnWriteArrayList<>();
    
    public Shooter(){
        JFrame frame= new JFrame("Shooter"); //Set window title to "Shooter"
        frame.setSize(WIDTH,HEIGHT); //Creates 600x600 window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Allows window to close when X is clicked
        frame.setResizable(false); //Do not allow to resize
        
        new Cache();
        player1 = new Player(10, 150, 20, 90, Cache.player1); 
        player2 = new Player(570, 150, 20, 90, Cache.player2);
        
        frame.add(this); //Add Canvas to frame object
        thread = new Thread(this); //send Shooter object
        
        frame.setVisible(true); //Make window visable 
        
        addKeyListener(this); //adds key listener to Shooter
    }
    public void paint (Graphics g){
        if (bs==null){
            createBufferStrategy(2); //we want 2 buffers
            bs = getBufferStrategy();
            graphics = bs.getDrawGraphics();
            
            thread.start(); //starts running
            running = true;
        }
    } 
    @Override
    public void run() {
        //Game loop
        while (running){
            update();
            render();
            
            bs.show();
            
            Thread.currentThread();
            
            try {
                Thread.sleep(10); //sleep for 10 miliseconds
            } catch(InterruptedException e){
                
            }
                
            }
        }
     
    public void update(){
        if (!gameOver) {
            player1.update();
            player2.update();

            for (Bullet bullet: bullets){
                bullet.update();
            }
        }
        if (player1.health<=0){
            gameOver = true; //player 2 wins
            winner=2;
        }
        
        if (player2.health<=0){
            gameOver = true; //player 1 wins
            winner=1;
        }
}
    public void render(){
        graphics.clearRect(0, 0, WIDTH, WIDTH);
        
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
       
        graphics.setColor(Color.red);
        graphics.drawString("Health: "+player1.health,30,50); //diplay player 1 health in red
        graphics.setColor(Color.red);
        graphics.drawString("Health: "+player2.health,520,50); //display player 2 health in red
        graphics.setColor(Color.red);
        graphics.drawString("Rectangular Shooter",260,20); //display name of the game
        //graphics.drawString("Bullets: "+bullets.size(),320,50);
        
        if (gameOver){ //prints the winner of the game on the screen
            graphics.drawString("Game Over!",260,60);
            graphics.drawString("Player "+winner+ " has won",260,75);
            graphics.drawString("Press N to start a new game.",260,90);
        }
        
        player1.draw(graphics);
        player2.draw(graphics);
        
        for (Bullet bullet: bullets){
            bullet.draw(graphics);
        }
}
    
    public static void main(String[] args){
       INSTANCE = new Shooter();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver){
            if (e.getKeyCode()==KeyEvent.VK_W){ //player 1 moves up when W is pressed
                player1.up=true;
            }else if (e.getKeyCode()==KeyEvent.VK_D){  //player 1 moves down when S is pressed
                player1.down=true;
            }

            if (e.getKeyCode()==KeyEvent.VK_UP){ //player 2 moves up when Up Arrow is pressed
                player2.up=true;
            }else if (e.getKeyCode()==KeyEvent.VK_DOWN){  //player 1 moves down when Down Arrow is pressed
                player2.down=true;
            }
        } else{
            if (e.getKeyCode() == KeyEvent.VK_N){
                resetGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_W){ //player 1 stops moveing up when W is released
            player1.up=false;
        }else if (e.getKeyCode()==KeyEvent.VK_D){  //player 1 stops moveing down when S is released
            player1.down=false;
        }

        if (e.getKeyCode()==KeyEvent.VK_UP){ //player 2 stops moveing up when Up Arrow is released
            player2.up=false;
        }else if (e.getKeyCode()==KeyEvent.VK_DOWN){  //player 2 stops moveing down when Down Arrow is released
            player2.down=false;
        }

        //Player 1 bullet
       if (!gameOver){
            if (e.getKeyCode()==KeyEvent.VK_SPACE){
                Bullet bullet = new Bullet(player1.x+player1.width,player1.y+player1.height/2, 4, 4, Cache.bullet); //create bullet when Q is released
                bullet.deltaX = 4; //player 1 bullet moves left to right
                bullets.add(bullet); //add bullet to array
            }

            //Player 2 bullet
            if (e.getKeyCode()==KeyEvent.VK_ENTER){
                Bullet bullet = new Bullet(player2.x-4,player2.y+player2.height/2, 4, 4, Cache.bullet); //create bullet when Emter is released
                bullet.deltaX = -4; //player 2 bullet moves right to left
                bullets.add(bullet); //add bullet to array
            }
        }
    }
    
    private void resetGame(){ //resets the whole game
        player1.reset(); //reset player 1 position
        player2.reset(); //reset player 2 position
        bullets.clear(); //clear bullets arrayList
        gameOver = false;        
    }
    
        public Player getPlayer1(){
            return player1;
        }
        
        public Player getPlayer2(){
            return player2;
        }
        
        public List<Bullet> getBullets(){
            return bullets;
        }
        
        public static Shooter getInstance(){
            return INSTANCE;
        }
       
    }


   
