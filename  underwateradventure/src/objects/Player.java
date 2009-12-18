package objects;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Player extends PhysicalObject {
	
	
	public Player(Applet a) {
		super(a);
		//load("sub.png");
	}
	
	public void keyUp(){
		setY(y-10);
	}
	public void keyDown(){
		setY(y+10);
	}

	public void keyLeft(){
		setX(x-10);
	}
	public void keyRight(){
		setX(x+10);
	}
	
}