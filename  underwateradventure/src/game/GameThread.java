package game;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import objects.Player;

public class GameThread extends Applet implements Runnable, KeyListener{
	Thread gameloop;
    //use this as a double buffer
    BufferedImage backbuffer;
    //the main drawing object for the back buffer
    Graphics2D graphics;
    Player player = new Player(this);
    int appletSizeX = 600;
    int appletSizeY = 600;
    public void init()
	{
    	setSize(600,600);
    	backbuffer = new BufferedImage(appletSizeX, appletSizeY, BufferedImage.TYPE_INT_RGB);
		graphics = backbuffer.createGraphics();
		player.setGraphics(graphics);
		player.setX(appletSizeX/2);
		player.setY(appletSizeY/2);
		player.load("sub.png");
	}
    public void update(Graphics g){
    	player.draw();
    	player.transform();
		paint(g);
	}
	
	//sets up buffered image
	public void paint(Graphics g)
	{	
		 g.drawImage(backbuffer, 0, 0, this);
	}
	public void start() {
        gameloop = new Thread(this);
        gameloop.start();
    }
  
	//thread event
	public void run() {
        //acquire the current thread
        Thread t = Thread.currentThread();
    
        //keep going as long as the thread is alive
        while (t == gameloop) {
            try {
                Thread.sleep(20);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            //update the game loop
            repaint();
        }
    }
      
	//stop thread
      public void stop() {
          gameloop = null;
      }
	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		int key = k.getKeyCode();
		
		switch (key){
		case KeyEvent.VK_LEFT:
            player.keyLeft();
            break;

        case KeyEvent.VK_RIGHT:
        	player.keyRight();
            break;

        case KeyEvent.VK_UP:
        	player.keyRight();
            break;
        
        case KeyEvent.VK_DOWN:
        	player.keyDown();
        	break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
