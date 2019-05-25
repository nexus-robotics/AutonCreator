package ca.NEXUS;

import java.awt.*;

public class cube
{
	String colour;
	int size;
	Point location = new Point(-30, -30);
	
	public cube(int size)
	{
		this.size = size;
	}//end of constructor
	
	public void setColour(String colour)
	{
		this.colour = colour;
	}//end of setColour
	
	public void setLocation(double inchX, double inchY)
	{
		this.location.setLocation(inchToPixel(144 - inchX), inchToPixel(144 - inchY));
	}//end of setLocation()
	
	private int inchToPixel(double inch)
	{
		return (int)(Math.ceil(inch * (autonCreator.fieldImage_resized.getWidth(null) / 144)));
	}//end of inchToPixel()
	
	public double getX()
	{
		return this.location.getX();
	}//end of getX()
	
	public double getY()
	{
		return this.location.getY();
	}//end of getY()
	
	public int getSize()
	{
		return this.size;
	}//end of getSize()
}//end of cube object
