package Week_8_Lab;

import java.awt.Color;

public class Triangle extends Shape
{
	public static final int TRIANGLE_POINTS = 3;
	
	private int[] xPoints;
	private int[] yPoints;
	
	public Triangle()
	{
		//Set Triangle's Color: Red
		SetColor(Color.RED);
		
		//Set Triangle's Size
		SetHeight(Shape.BASE_SIZE);
		SetWidth(Shape.BASE_SIZE);
		
		//Triangle Shape is defined by 3 points:
		//1.Lower Left (x1,y1), 
		//2.Lower Right (x2,y2),
		//3.Upper Center (x3,y3).
		//Initialize the points to zeros first
		xPoints = new int[TRIANGLE_POINTS];
		yPoints = new int[TRIANGLE_POINTS];
	}
	
	public void UpdateTrianglePoints()
	{
		//1. set the 1st point (x1,y1)
		xPoints[0] = this.GetXCoord();
		yPoints[0] = this.GetYCoord()+this.GetHeight();
		
		//2. set the 2nd point (x2,y2)
		xPoints[1] = this.GetXCoord()+this.GetWidth();
		yPoints[1] = this.GetYCoord()+this.GetHeight();
		
		//3. set the 3rd point (x3,y3)
		xPoints[2] = this.GetXCoord()+(this.GetWidth()/2);
		yPoints[2] = this.GetYCoord();
	}
	
	public int[] GetXPoints()
	{
		return this.xPoints;
	}
	
	public int[] GetYPoints()
	{
		return this.yPoints;
	}
	
	public void SetXCoord(int xCoordParam)
	{
		super.SetXCoord(xCoordParam);
		this.UpdateTrianglePoints();
	} 
	
	public void SetYCoord(int yCoordParam)
	{
		super.SetYCoord(yCoordParam);
		this.UpdateTrianglePoints();
	} 
}