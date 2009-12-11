import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class UnderwaterAdventure extends Applet {
	

	public void init()
	{
	setSize(500, 500);
	}
	
	public void paint (Graphics g)
	{
		g.drawString("Hey Guys! Ready for some underwater adventure?", 100, 250);
		g.drawString("Woooooo!!  Hi Mark!", 100, 300);
	}
}
