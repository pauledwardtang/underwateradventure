import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class UnderwaterAdventure extends Applet {
	

	public void init()
	{
	setSize(650, 700);
	}
	
	public void paint (Graphics g)
	{
		g.drawString("Hey Guys! Ready for some underwater adventure?", 100, 250);
		g.drawString("Woooooo!!  Hi Mark!", 100, 300);
		g.drawString("Woooooo!!  Hi Nat!", 100, 350);
		g.drawString("Working yet??", 100, 400);
		g.setColor(Color.ORANGE);
		g.fillRect(100,100,100,100);
		g.setColor(Color.BLUE);
		g.fillRect(200, 100, 100, 100);
		g.setXORMode(Color.ORANGE);
		g.drawString("GO GATORS!!!", 170, 150);
	}
}
