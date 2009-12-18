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
    
    PhysicalObject octopi_array[]={	new PhysicalObject(this),
    								new PhysicalObject(this),
    								new PhysicalObject(this),
    								new PhysicalObject(this),
    								new PhysicalObject(this),
    								new PhysicalObject(this),
    								new PhysicalObject(this),
    							   };	
    
    PhysicalObject torpedo=new PhysicalObject(this);
 
    int appletSizeX = 1000;
    int appletSizeY = 400;
    AffineTransform ident = new AffineTransform();
    boolean showBounds;
    int change;
    
    int currentFrame=0;
    public void init()
	{
    	
    	setSize(appletSizeX,appletSizeY);
    	addKeyListener(this);
    	backbuffer = new BufferedImage(appletSizeX, appletSizeY, BufferedImage.TYPE_INT_RGB);
		graphics = backbuffer.createGraphics();
		player.setGraphics(graphics);
		player.load("sub.png");
		player.setX(200);
		player.setY(175);
		player.setWidth(127);
		player.setHeight(75);
		
		torpedo.setGraphics(graphics);
		torpedo.load("torpedo.png");
		torpedo.setWidth(44);
		torpedo.setHeight(16);
		torpedo.setX(player.getX()+110);
		torpedo.setY(player.getY()+45);
		
	
		
		octopi_array[0].setGraphics(graphics);
		octopi_array[0].load("octopi2.png");
		octopi_array[0].setWidth(88);
		octopi_array[0].setHeight(92);
		octopi_array[0].setX(100);
		octopi_array[0].setY(randomY.nextInt(400));		
		
    	for(int i=1; i<octopi_array.length; i++){
			octopi_array[i].setGraphics(graphics);
			octopi_array[i].load("octopi2.png");
			octopi_array[i].setWidth(88);
			octopi_array[i].setHeight(92);
			octopi_array[i].setX(randomX.nextInt(1000));
			octopi_array[i].setY(randomY.nextInt(400));
			for(int j=0; j<i; j++){
				while(collision(octopi_array[i].getBounds(), player.getBounds())||collision(octopi_array[i].getBounds(), octopi_array[j].getBounds())){
					octopi_array[i].setX(randomX.nextInt(1000));
					octopi_array[i].setY(randomY.nextInt(400));
					
				}
			}
			
    	}
    	
    
		
		
	}
    public void update(Graphics g){
    	
    	graphics.setTransform(ident);
    	
    	Color C = new Color(0,0,255);
		graphics.setColor(C);
    	graphics.fillRect(0, 0, getSize().width, getSize().height);
    	graphics.setColor(Color.WHITE);
    	graphics.drawString("Life: "+life, 925, 390);
    	player.draw();
    	player.transform();
    	player.load("sub.png");
    	
    	for(int i=0; i<octopi_array.length; i++){
    		transformOctopi(octopi_array[i].getX(), octopi_array[i].getY(), i);
    	}

    	for(int i=0; i<octopi_array.length; i++){
    		if(collision(octopi_array[i].getBounds(),player.getBounds())){
    			life=life-1;
    			player.load("hurtsub.png");
    			
    		}
    		
    	}
    	
    
    	
    	graphics.setColor(Color.ORANGE);
    	if(showBounds){
    		player.drawBounds();
    		for(int i=0; i<octopi_array.length; i++){
    			octopi_array[i].drawBounds();
    		}
    	}
    	
    	if(shotTaken==true){
    		drawShot(torpedo.getX(), torpedo.getY());
    	}
    	
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
    public void transformOctopi(double x, double y,  int i){
    	octopi_array[i].setX(x);
    	octopi_array[i].setY(y-1);
	    if(currentFrame<100){
	    	octopi_array[i].load("octopi2.png");
	    }
	    else{
	    	octopi_array[i].load("octopi.png");
	    }
    	
	    
	    octopi_array[i].draw();
	    octopi_array[i].transform();
		currentFrame++;
		if(currentFrame==200){
			currentFrame=0;
		}
		
		if(octopi_array[i].getY()==-50){
			octopi_array[i].setY(450);
			octopi_array[i].setX(randomX.nextInt(1000));
			for(int j=0; j<i; j++){
				while(collision(octopi_array[i].getBounds(), octopi_array[j].getBounds())){
					octopi_array[i].setX(randomX.nextInt(1000));
				}
			}
		}
			
	
    }
    
    public void drawShot(double x, double y){
    	if(torpedo.getX()>1000){
    		shotTaken=false;
    	}
    	else if(collision(octopi_array[0].getBounds(),torpedo.getBounds())){
    		octopi_array[0].setY(500);
    		shotTaken=false;
    		
    		explosionAnimation();
    	}
    	else if(collision(octopi_array[1].getBounds(),torpedo.getBounds())){
    		octopi_array[1].setY(500);
    		shotTaken=false;
    		explosionAnimation();
    	}
    	else if(collision(octopi_array[2].getBounds(),torpedo.getBounds())){
    		octopi_array[2].setY(500);
    		shotTaken=false;
    		explosionAnimation();
    	}
    	else if(collision(octopi_array[3].getBounds(),torpedo.getBounds())){
    		octopi_array[3].setY(500);
    		shotTaken=false;
    		explosionAnimation();
    	}
    	else if(collision(octopi_array[4].getBounds(),torpedo.getBounds())){
    		octopi_array[4].setY(500);
    		shotTaken=false;
    		explosionAnimation();
    	}
    	else if(collision(octopi_array[5].getBounds(),torpedo.getBounds())){
    		octopi_array[5].setY(500);
    		shotTaken=false;
    		explosionAnimation();
    	}
    	else if(collision(octopi_array[6].getBounds(),torpedo.getBounds())){
    		octopi_array[6].setY(500);
    		shotTaken=false;
    		explosionAnimation();
    	}
    	else{
    		torpedo.setX(x+8);
        	torpedo.setY(y);
    	}
    	torpedo.draw();
    	torpedo.transform();
    	
    	
    	
    }
    public void explosionAnimation(){
    	
    }
    public boolean collision(Rectangle objectOne, Rectangle objectTwo ){
    	
    	if( objectOne.intersects(objectTwo)){
    		return true;
    	}
    	
    	return false;
    }
    
   
    
    public void gameOver(){
    	graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        graphics.setColor(Color.RED);
        graphics.drawString("GAME OVER", getSize().width/2, getSize().height/2);
    }
    
    
	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		int key = k.getKeyCode();
		
		
		if(KeyEvent.VK_LEFT == key)
            player.keyLeft();

		if(KeyEvent.VK_UP == key)
            player.keyUp();

		if(KeyEvent.VK_DOWN == key)
            player.keyDown();
		
		if(KeyEvent.VK_RIGHT == key)
            player.keyRight();
		
		if(KeyEvent.VK_B == key)
			showBounds = !showBounds;
		
		if(KeyEvent.VK_SPACE==key){
			torpedo.setY(player.getY()+45);
			torpedo.setX(player.getX()+110);
			shotTaken=true;
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
