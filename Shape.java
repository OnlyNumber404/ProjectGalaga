package Week_8_Lab;

import java.awt.*; //to use java.awt.color class

//This is the base/parent class for all shapes: 
//Rectangle, Square, Triangle, Circle
public class Shape 
{
	public static int BASE_SIZE = 20;
	
	private int xCoord = 0;
	private int yCoord = 0;
	private int width = 0;
	private int height = 0;
	private Color shapeColor = null;
	
	public Shape()
	{
		this.shapeColor = Color.WHITE;
	}
	
	public int GetXCoord()
	{
		return this.xCoord;
	}
	
	public void SetXCoord(int xCoordParam)
	{
		this.xCoord = xCoordParam;
	}
	
	public int GetYCoord()
	{
		return this.yCoord;
	}
	
	public void SetYCoord(int yCoordParam)
	{
		this.yCoord = yCoordParam;
	}
	
	public int GetWidth()
	{
		return this.width;
	}
	
	public void SetWidth(int widthParam)
	{
		this.width = widthParam;
	}
	
	public int GetHeight()
	{
		return this.height;
	}
	
	public void SetHeight(int heightParam)
	{
		this.height = heightParam;
	}
	
	public Color GetColor()
	{
		return this.shapeColor;
	}
	
	public void SetColor(Color colorParam)
	{
		this.shapeColor = colorParam;
	}
}
