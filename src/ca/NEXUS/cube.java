package ca.NEXUS;

import javafx.geometry.Point3D;

public class cube implements Comparable<cube>
{
	String colour;
	int size;
	Point3D location;
		
	public cube(double x, double y, double z, int size, String colour)
	{
		this.size = size;
		this.colour = colour;
		setLocation(x, y, z);
	}//end of constructor
	
	public void setLocation( double inchX, double inchY, double inchZ )
	{
		this.location = new Point3D((inchToPixel(144 - (inchX + 2.75))), (inchToPixel(144 - (inchY - 2.75))) - autonCreator.picOffset, inchZ);
	}//end of setLocation()
	
	private int inchToPixel(double inch)
	{
		double ppi = 1800 / autonCreator.fieldLabel.getWidth();
		final int dpi = (int)(Math.ceil(ppi * autonCreator.scalePercent));
		//int dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println(dpi);
		return (int)((inch * dpi) / 25.4);
	}//end of inchToPixel()
	
	public double getX()
	{
		return this.location.getX();
	}//end of getX()
	
	public double getY()
	{
		return this.location.getY();
	}//end of getY()
	
	public double getZ()
	{
		return this.location.getZ();
	}

	public String getColour()
	{
		return this.colour;
	}//end of getY()
	
	public int getSize()
	{
		return this.size;
	}//end of getSize()

	public int compareTo(cube other) {
		if(this.location.getZ()>other.location.getZ()) {
			return -1;
		}
		else if(this.location.getZ()<other.location.getZ()) {
			return 1;
		}
		else {
			if (this.location.getX() != other.location.getX()) {
				return (int) this.location.getX() - (int) other.location.getX();
			} else if (this.location.getY() != other.location.getY()) {
				return (int) this.location.getY() - (int) other.location.getY();
			}
			return 1;
		}
	}
}//end of cube object
