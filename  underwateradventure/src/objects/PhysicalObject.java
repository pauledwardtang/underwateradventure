package objects;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class PhysicalObject {
	
	
	protected double x,y;
	protected double height,width;
	protected Image image;
	protected Applet applet;
	protected AffineTransform at;
	protected Graphics2D graphics;
	protected double velX, velY;
	protected double moveAngle, faceAngle;
	protected double scaleX,scaleY = 1;
	
	
	public PhysicalObject(Applet a){
		applet = a;
	}
	//getters and setters
	public double getX(){
		return x;
	}
	public void setX(double x){
		this.x = x; 
	}
	public double getY(){
		return y;
	}
	public void setY(double y){
		this.y = y;
	}
	public double getvelX(){
		return velX;
	}
	public void setvelX(double velX){
		this.velX = velX;
	}
	public double getvelY(){
		return velY;
	}
	public void setvelY(double velY){
		this.velY = velY;
	}
	public double getFaceAngle(){
		return faceAngle;
	}
	public void setFaceAngle(double angle){
		faceAngle = angle;
	}
	//draws image
	public void draw(){
		graphics.drawImage(image, at, applet);
	}
	/*public double getWidth(){
		return image.getWidth(applet);
	}
	public double getHeight(){
		return image.getHeight(applet);
	}*/
	public double getWidth(){
		return width;
	}
	public double getHeight(){
		return height;
	}
	public void setWidth(double width){
		this.width=width;
	}
	public void setHeight(double height){
		this.height=height;
	}
	/*public double getCenterX()
	{
		return getX() + getWidth() /2;
	}
	public double getCenterY()
	{
		return getY() + getHeight() /2;
	}*/
	//binding rectangle
	public Rectangle getBounds(){
		Rectangle r;
        //r = new Rectangle((int)getCenterX(), (int) getCenterY(), (int) getWidth(), (int) getHeight());
		r = new Rectangle(((int)this.getX()), ((int) this.getY()), ((int) this.getWidth()), ((int) this.getHeight()));
		return r;
	}
	//draws binding rectangle
	public void drawBounds(){
		Rectangle r;
        r = new Rectangle((int)getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        graphics.draw(r);
	}
	//scale image size
	public void setScale(){
		at.scale(scaleX, scaleY);
	}
	public void setImage(Image image)
	{
		this.image = image;
	}
	public Image getImage(){
		return image;
	}
	protected URL getURL(String filename)
	{
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		}
		catch(Exception e) {}
		return url;
	}
	public void transform() {
	    at.setToIdentity();
	    at.translate((int)getX() + getWidth()/2, (int)getY() + getHeight()/2);
	    //at.rotate(Math.toRadians(getFaceAngle()));
	    //at.scale(scaleX, scaleY);
	    at.translate(-getWidth()/2, -getHeight()/2);
	    }
	public void load(String filename)
	{
		setImage(applet.getImage(getURL(filename)));
		//double x = applet.getSize().width/2 - getWidth()/2;
		//double y = applet.getSize().height/2 - getHeight()/2;
		at = AffineTransform.getTranslateInstance(x, y);
	}
	
	//set reference to drawing object
	public void setGraphics(Graphics2D g)
	{
		graphics = g;
	}
}
