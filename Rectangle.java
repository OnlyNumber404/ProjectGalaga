package Week_8_Lab;

import java.awt.Color;

public class Rectangle extends Shape
{
	public Rectangle()
	{
		//Set Rectangle's Color: Yellow
		SetColor(Color.YELLOW);
		
		//Set Rectangle's Size
		SetHeight(Shape.BASE_SIZE);
		SetWidth(2*Shape.BASE_SIZE);
	}
}
