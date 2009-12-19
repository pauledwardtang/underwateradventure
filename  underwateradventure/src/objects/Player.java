package objects;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends PhysicalObject {
	 boolean thrust;
	int imageHeight = 70;
	int imageWidth = 122;
	public Player(Applet a) {
		super(a);
		//load("sub.png");
	}
	
	public void keyUp(){
		if(getvelY()<5);
			incVelY(-.2);
	}
	public void keyDown(){
		if(getvelY()>-5)
			incVelY(.2);
	}

	public void keyLeft(){
		if(getvelX()>-5)
			incVelX(-.2);
	}
	public void keyRight(){
		if(getvelX()<5)
			incVelX(.2);
	}
	public void updatePosition(){
		if(x < 0 )
		{
			x = 0;
			velX = -(velX*.5);
		}
		if(x + imageWidth > applet.getWidth() )
		{
			x = applet.getWidth() - imageWidth ;
			velX = -(velX*.5);
		}

		if(y < 0 )
		{
			y = 0;
			velY = -(velY*.5);
		}
		if(y+imageHeight > applet.getHeight())
		{
			y = applet.getHeight() - imageHeight;
			velY = -(velY*.5);
		}
		this.incY(this.getvelY());
		this.incX(this.getvelX());
		//decreaseSpeed();
	}
	//slows sub overtime
	public void decreaseSpeed(){
		if(!thrust){
		if(velX>0)
			velX -= .05;
		else if(velX<0)
			velX += .05;
		else if(velX < .05 || velX> .05)
			velX = 0;
		if(velY>0)
			velY -= .05;
		else if(velY<0)
			velY += .05;
		else if(velY < .05 || velY> .05)
			velY = 0;
		}
	}
	public void fireWeapon(){
		PhysicalObject torpedo = new PhysicalObject(applet);
		torpedo.setGraphics(graphics);
		torpedo.setY(getY()+45);
		torpedo.setX(getX()+110);
		
	}
	public void setThrust(boolean b)
	{
		thrust = b;
	}
	
}
