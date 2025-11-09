package Week_9;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import Week_8_Lab.*;
import javax.swing.JPanel;

public class EnemyManager {

    private LinkedList<Shape> listOfEnemy;

    private Drawer drawer;

    private javax.swing.JPanel panel;
    // timer buat ngatur waktu spawn & gerak
    private Timer timer;

    private Random rand;


    private int baseSize;
    private int leftMargin;
    private int topY;


    private int rows = 3;
    private int cols = 5;
    private int totalEnemy = rows * cols;


    private int spawnedCount = 0;
    private int shapesInRow = 0;
    private int lastRowY;

    // arah gerak musuh
    private boolean movingRight = true;
    private boolean movingLeft = false;
    private int shift = 10; // seberapa jauh geser tiap langkah

    // jenis musuh yang bisa keluar
    private static final String[] shapes = {"Square", "Rectangle", "Triangle", "Circle"};

    public EnemyManager(int count, int leftMargin, int topY, int baseSize, javax.swing.JPanel panel) {
        this.leftMargin = leftMargin;
        this.topY = topY;
        this.baseSize = baseSize;
        this.panel = panel;
        this.rand = new Random();
        this.listOfEnemy = new LinkedList<>();
        this.drawer = new Drawer(listOfEnemy, panel, false);
        this.timer = new Timer();

        // spawn musuh satu per satu tiap 2 detik
        this.timer.schedule(new SpawnEnemy(), 0, 2000);
        this.timer.schedule(new MoveEnemy(), 0, 50);
    }


    private class SpawnEnemy extends TimerTask {
        @Override
        public void run() {
            synchronized (listOfEnemy) {
                Node<Shape> curr = listOfEnemy.GetLinkedListHead();
                
                // kalau belum ada musuh sama sekali - spawn pertama
                if (curr == null) {
                    int randint = rand.nextInt(4);
                    drawer.AddShape(shapes[randint]);
                    spawnedCount++;
                    shapesInRow = 1;
                    lastRowY = topY;
                    panel.repaint();
                    return;
                }

                // kalau udah penuh semua (15 musuh), stop spawn
                if (spawnedCount >= totalEnemy) return;

                // hitung posisi terakhir & berapa musuh yang udah ada
                int enemy_count = 0;
                int lastX = listOfEnemy.GetLinkedListHead().GetNodeData().GetXCoord();
                int lastY = -1;
                int countRow = 1;

                // cari posisi y paling bawah (baris terakhir)
                curr = listOfEnemy.GetLinkedListHead();
                while (curr != null) {
                    if (lastY < curr.GetNodeData().GetYCoord()) {
                        lastY = curr.GetNodeData().GetYCoord();
                    }
                    curr = curr.GetNext();
                    enemy_count++;
                }

                // hitung berapa shape di baris terakhir
                curr = listOfEnemy.GetLinkedListHead();
                while (curr.GetNext() != null) {
                    if (curr.GetNodeData().GetYCoord() == lastY) {
                        countRow++;
                    }
                    curr = curr.GetNext();
                }

                // kalau baris terakhir belum penuh, tambahkan di baris yang sama
                if (countRow < 5 && 
                    curr.GetNodeData().GetXCoord() + curr.GetNodeData().GetWidth() + Shape.BASE_SIZE < panel.getSize().width - 30 && 
                    enemy_count < totalEnemy) {
                    
                    int randint = rand.nextInt(4);
                    drawer.AddShape(shapes[randint]);
                    spawnedCount++;
                }
                // kalau baris udah penuh (5 shape) - buat baris baru di bawahnya
                else if (countRow == 5 && 
                         curr.GetNodeData().GetXCoord() + curr.GetNodeData().GetWidth() + Shape.BASE_SIZE < panel.getSize().width - 30 && 
                         enemy_count < totalEnemy) {
                    
                    int randint = rand.nextInt(4);
                    Shape newShape = null;
                    
                    // pilih bentuk musuh random
                    switch(shapes[randint]) {
                        case "Square": newShape = new Square(); break;
                        case "Rectangle": newShape = new Rectangle(); break;
                        case "Triangle": newShape = new Triangle(); break;
                        case "Circle": newShape = new Circle(); break;
                    }
                    
                    // posisikan tepat di bawah baris sebelumnya musuhnya
                    newShape.SetXCoord(lastX);
                    newShape.SetYCoord(lastY + newShape.GetHeight() + Shape.BASE_SIZE);
                    
                    Node<Shape> newNode = new Node<>(newShape);
                    listOfEnemy.AddNode(newNode);
                    spawnedCount++;
                }
            }
            panel.repaint(); // refresh panel biar musuh baru kelihatan
        }
    }


    private class MoveEnemy extends TimerTask {
        @Override
        public void run() {
            synchronized (listOfEnemy) {
                Node<Shape> curr = listOfEnemy.GetLinkedListHead();
                
                if (curr == null) return;

                // cari posisi musuh paling kiri dan kanan
                int maxX = -1;
                int minX = Integer.MAX_VALUE;

                while (curr != null) {
                    int rightEdge = curr.GetNodeData().GetXCoord() + curr.GetNodeData().GetWidth();
                    int leftEdge = curr.GetNodeData().GetXCoord();
                    
                    if (rightEdge > maxX) maxX = rightEdge;
                    if (leftEdge < minX) minX = leftEdge;
                    
                    curr = curr.GetNext();
                }

                // kalau nyentuh tepi kanan, ubah arah ke kiri + turun satu baris
                if (movingRight && maxX + shift >= panel.getSize().width) {
                    movingRight = false;
                    movingLeft = true;
                    
                    curr = listOfEnemy.GetLinkedListHead();
                    while (curr != null) {
                        curr.GetNodeData().SetYCoord(curr.GetNodeData().GetYCoord() + shift);
                        curr = curr.GetNext();
                    }
                }
                // kalau nyentuh tepi kiri, ubah arah ke kanan + turun juga
                else if (movingLeft && minX - shift <= 0) {
                    movingLeft = false;
                    movingRight = true;
                    
                    curr = listOfEnemy.GetLinkedListHead();
                    while (curr != null) {
                        curr.GetNodeData().SetYCoord(curr.GetNodeData().GetYCoord() + shift);
                        curr = curr.GetNext();
                    }
                }
                // kalau belum nyentuh sisi mana pun → lanjut jalan
                else {
                    curr = listOfEnemy.GetLinkedListHead();
                    while (curr != null) {
                        if (movingRight)
                            curr.GetNodeData().SetXCoord(curr.GetNodeData().GetXCoord() + shift);
                        else if (movingLeft)
                            curr.GetNodeData().SetXCoord(curr.GetNodeData().GetXCoord() - shift);
                        curr = curr.GetNext();
                    }
                }
            }
            panel.repaint();
        }
    }


    public void drawAll(java.awt.Graphics g) {
        synchronized (listOfEnemy) {
            Node<Shape> curr = listOfEnemy.GetLinkedListHead();
            while (curr != null) {
                drawer.DrawShape(g, curr);
                curr = curr.GetNext();
            }
        }
    }

    /**
     * Cek apakah ada musuh yang nabrak pesawat
     */
    public boolean checkCollisionWithPolygon(java.awt.Polygon playerPoly) {
        if (playerPoly == null) return false;
        Node<Shape> curr = listOfEnemy.GetLinkedListHead();
        while (curr != null) {
            Shape s = curr.GetNodeData();
            int sx = s.GetXCoord();
            int sy = s.GetYCoord();
            int sw = s.GetWidth();
            int sh = s.GetHeight();

            // kalau area pesawat & musuh bersinggungan - return true
            if (playerPoly.intersects(sx, sy, sw, sh)) return true;

            curr = curr.GetNext();
        }
        return false;
    }

    /**
     * Cek apakah ada musuh yang udah sampai level Y pesawat
     */
    public boolean anyReachedY(int yThreshold) {
        Node<Shape> curr = listOfEnemy.GetLinkedListHead();
        while (curr != null) {
            Shape s = curr.GetNodeData();
            if (s.GetYCoord() + s.GetHeight() >= yThreshold) return true;
            curr = curr.GetNext();
        }
        return false;
    }
    //untuk mencari shape apa itu
    public LinkedList<Shape> getEnemyList(){
    	return listOfEnemy;
    }
    
    // untuk menghentikan semua enemy
    public void stopAll() {
    	if (timer != null) {
    		timer.cancel();
    		timer.purge();
    	}
    }
}