package Week_9;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy {
    // posisi musuh (koordinat kiri atas)
    private int x;
    private int y;

    // ukuran musuh (lebar dan tinggi sama, karena bentuknya kotak)
    private int width;
    private int height;

    // warna musuh
    private Color color;

    // konstruktor buat bikin objek musuh baru
    public Enemy(int x, int y, int size, Color color) {
        this.x = x; // posisi awal X
        this.y = y; // posisi awal Y
        this.width = size; // ukuran lebar
        this.height = size; // ukuran tinggi
        this.color = color; // warna kotak musuh
    }

    // fungsi buat gambar musuh di layar
    public void draw(Graphics g) {
        g.setColor(color); // atur warna
        g.fillRect(x, y, width, height); // gambar kotak musuh
    }

    // fungsi buat mindahin posisi musuh (gerak ke kanan/kiri/bawah)
    public void translate(int dx, int dy) {
        this.x += dx; // geser di sumbu X
        this.y += dy; // geser di sumbu Y
    }

    // fungsi buat ambil area kotak musuh (dipakai buat deteksi tabrakan)
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // getter (pengambil data) posisi dan ukuran musuh
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}