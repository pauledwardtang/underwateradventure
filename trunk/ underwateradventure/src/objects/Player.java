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
		setY(y+1);
	}
	public void keyDown(){
		setY(y-1);
	}

	public void keyLeft(){
		setX(x-1);
	}
	public void keyRight(){
		setX(x-1);
	}
	
}
