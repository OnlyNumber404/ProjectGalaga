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

    // ambil posisi x musuh
    public int getX() { return shape.GetXCoord(); }
    // ambil posisi y musuh
    public int getY() { return shape.GetYCoord(); }
    // ambil lebar musuh
    public int getWidth() { return shape.GetWidth(); }
    // ambil tinggi musuh
    public int getHeight() { return shape.GetHeight(); }

    // buat gerakin musuh
    // dx = geser ke kanan (+) atau kiri (-)
    // dy = geser ke bawah (+) atau atas (-)
    public void move(int dx, int dy) {
        shape.SetXCoord(shape.GetXCoord() + dx);
        shape.SetYCoord(shape.GetYCoord() + dy);
    }
}
