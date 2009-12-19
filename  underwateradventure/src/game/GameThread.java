package game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import objects.PhysicalObject;
import objects.Player;

public class GameThread extends Applet implements Runnable, KeyListener{
	Thread gameloop;
    //use this as a double buffer
    BufferedImage backbuffer;
    //the main drawing object for the back buffer
    Graphics2D graphics;
    Player player = new Player(this);
    
    Random randomX=new Random();
    Random randomY=new Random();
    int life=500;
    boolean shotTaken=false;
    boolean shotReleased = true;

    //octopuses? octopi?
    int OCTOPI = 5;
    PhysicalObject octopi[] = new PhysicalObject[OCTOPI];                                              
    //torpedoes
    int TORPEDOES = 50;
    PhysicalObject[] torpedoes = new PhysicalObject[TORPEDOES];
    int currentTorpedo = 0;
    
    int appletSizeX = 1000;
    int appletSizeY = 400;
    AffineTransform ident = new AffineTransform();
    boolean showBounds;
    int change;
    
    int currentFrame=0;
    public void init()
	{
    	//applet size setup
    	setSize(appletSizeX,appletSizeY);
    	addKeyListener(this);
    	backbuffer = new BufferedImage(appletSizeX, appletSizeY, BufferedImage.TYPE_INT_RGB);
		graphics = backbuffer.createGraphics();
		
		//player initializiation
		player.setGraphics(graphics);
		player.load("sub.png");
		player.setX(appletSizeX/2);
		player.setY(appletSizeY/2);
		
		 //torpedos initialization
        for (int i = 0; i<torpedoes.length; i++) {
            torpedoes[i] = new PhysicalObject(this);
            torpedoes[i].setGraphics(graphics);
            torpedoes[i].load("torpedo.png");
            torpedoes[i].setX(appletSizeX+100);
            torpedoes[i].setY(appletSizeY+100);       
        }	
		//octopus initializtion
		for(int i = 0; i<octopi.length; i++){
			octopi[i] = new PhysicalObject(this);
			octopi[i].setGraphics(graphics);
			octopi[i].load("octopi2.png");
			octopi[i].setX(randomX.nextInt(1000));
			octopi[i].setY(randomY.nextInt(200)+appletSizeY);  
			for(int j=0; j<i; j++){
				while(collision(octopi[i].getBounds(), player.getBounds())||collision(octopi[i].getBounds(), octopi[j].getBounds())){
					octopi[i].setX(randomX.nextInt(1000));
					octopi[i].setY(randomY.nextInt(200)+appletSizeY);
				}
			}
		}	
	}
    public void update(Graphics g){
    	
    	graphics.setTransform(ident);
    	Color C = new Color(0,0,255);
		graphics.setColor(C);
    	graphics.fillRect(0, 0, getSize().width, getSize().height);
    	player.updatePosition();
    	graphics.setColor(Color.WHITE);
    	graphics.drawString("Life: "+life, 925, 390);
    	player.draw();
    	player.transform();
    	player.load("sub.png");
    	
    	updateOctopi();
    	//decreases speed after movement
    	player.decreaseSpeed();
    	drawBounds();
    	graphics.setColor(Color.ORANGE);
    	drawTorpedoes();
    	collisions();
    	if(life<0){
    		gameOver();
    	}
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
        while (t == gameloop&&life>=0) {
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
    public void updateOctopi(){
    	for(int i = 0; i<octopi.length; i++){
    	octopi[i].setY(octopi[i].getY()-1);
	    if(currentFrame<100){
	    	octopi[i].load("octopi2.png");
	    }
	    else{
	    	octopi[i].load("octopi.png");
	    }
	    octopi[i].transform();
	    octopi[i].draw();
		currentFrame++;
		if(currentFrame==200){
			currentFrame=0;
		}
		
		if(octopi[i].getY()==-50){
			octopi[i].setY(appletSizeY+100);
			octopi[i].setX(randomX.nextInt(1000));
			for(int j=0; j<i; j++){
				while(collision(octopi[i].getBounds(), octopi[j].getBounds())){
					octopi[i].setX(randomX.nextInt(1000));
				}
			}
		  }
    	}
	
    }
    //detects all collisions
    public void collisions(){
    	//torpedo collisions
    	for(int i = 0; i<torpedoes.length; i++)
    	{
    		if(torpedoes[i].getX()>appletSizeX)
    		{
    			torpedoes[i].setAlive(false);
    			torpedoes[i].setvelX(0);
    		}
    	//checks every octopi for collisions
    	for(int j = 0; j<octopi.length; j++){
    		if(collision(octopi[j].getBounds(),torpedoes[i].getBounds())){
    			octopi[j].setY(500);
    			octopi[j].setX(randomX.nextInt(1000));
    			torpedoes[i].setAlive(false);
    			torpedoes[i].setvelX(0);
    			torpedoes[i].setX(appletSizeX+100);
    		   }
    	    }
    	}
    	for(int i=0; i<octopi.length; i++){
    		if(collision(octopi[i].getBounds(),player.getBounds())){
    			life=life-1;
    			player.load("hurtsub.png");
    		}	
    	}
      }	
    
    private boolean collison() {
		// TODO Auto-generated method stub
		return false;
	}
	public void explosionAnimation(){
    	
    }
    public boolean collision(Rectangle objectOne, Rectangle objectTwo ){
    	
    	if( objectOne.intersects(objectTwo)){
    		return true;
    	}
    	
    	return false;
    }
    
    public void drawTorpedoes() {
        for (int i = 0; i < TORPEDOES; i++) {
           if (torpedoes[i].isAlive()) {
                //draw the torps
        		torpedoes[i].incX(torpedoes[i].getvelX());
                torpedoes[i].transform();
                torpedoes[i].draw();
            }
        }
    }
    
    public void gameOver(){
    	graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        graphics.setColor(Color.RED);
        graphics.drawString("GAME OVER", getSize().width/2, getSize().height/2);
    }
    public void drawBounds(){
    	if(showBounds){
        	player.drawBounds();
        	for(int i=0; i<octopi.length; i++){
        		octopi[i].drawBounds();
        	}
        	for(int i = 0; i<torpedoes.length; i++){
        			torpedoes[i].drawBounds();
        		}
    	}
    }
    
	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		int key = k.getKeyCode();

		if(KeyEvent.VK_LEFT == key){
            player.keyLeft();
            player.setThrust(true);
		}
		if(KeyEvent.VK_UP == key){
            player.keyUp();
            player.setThrust(true);
		}
		if(KeyEvent.VK_DOWN == key){
            player.keyDown();
			player.setThrust(true);
		}
		if(KeyEvent.VK_RIGHT == key){
            player.keyRight();
            player.setThrust(true);
		}
		if(KeyEvent.VK_B == key)
			showBounds = !showBounds;
		
		if(KeyEvent.VK_SPACE==key){
			if(shotReleased){
			//fire a bullet
            currentTorpedo++;
            if (currentTorpedo > TORPEDOES - 1) currentTorpedo = 0;
            torpedoes[currentTorpedo].setX(player.getX()+player.getWidth());
            torpedoes[currentTorpedo].setY(player.getY()+player.getHeight()/2);
            torpedoes[currentTorpedo].setvelX(8);
            torpedoes[currentTorpedo].setAlive(true);
			shotTaken=true;
			shotReleased = false;
			}
		}

	}
	@Override
	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub
	int key = k.getKeyCode();
		
		
		if(KeyEvent.VK_LEFT == key)
		{
			player.setThrust(false);
		}	
		if(KeyEvent.VK_UP == key)
		{
			player.setThrust(false);
		}
		if(KeyEvent.VK_DOWN == key)
		{
			player.setThrust(false);
		}
		if(KeyEvent.VK_RIGHT == key)
		{		
			player.setThrust(false);
		}
		if(KeyEvent.VK_SPACE == key)
			shotReleased = true;
	}
	@Override
	public void keyTyped(KeyEvent k) {
		
	}

}
