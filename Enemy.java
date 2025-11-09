package Week_9;
import Week_8_Lab.*;

public class Enemy {
    
    private Shape shape;
    
    public Enemy(Shape shape) {
    	this.shape = shape;
    }
    
    public Shape getShape() {
    	return shape;
    }
    
    
    // ambil psosisi musuh
    public int getX() {
    	return shape.GetXCoord();
    }
    public int getY() {
    	return shape.GetYCoord();
    }
    public int getWidth() {
    	return shape.GetWidth();
    }
    public int getHeight() {
    	return shape.GetHeight();
    }
    
    //buat gerakan musuh
    //dx = geser ke kanan (+) atau ke kiri(-)
    //dy = geser ke atas (+) atau ke bawah(-)
    public void move (int dx,int dy) {
    	shape.SetXCoord(shape.GetXCoord()+ dx);
    	shape.SetYCoord(shape.GetYCoord()+ dy);
    }
    
}