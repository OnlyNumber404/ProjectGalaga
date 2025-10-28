package Week_9;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;


	public class EnemyManager {
	    private final List<Enemy> enemies = new ArrayList<>();
	    // direction: +1 = bergerak ke kanant, -1 = bergerak kekiri
	    private int dir = 1;
	    private int speed = 10 ; // px per frame (speed musuh)
	    private final int spacing; 
	    private final int dropAmount; // untuk bergerak turun disetiap kotak(musuh)
	    private final int leftMargin; // starting x
	    private final int topY; // starting y

	    public EnemyManager(int count, int leftMargin, int topY, int spacing) {
	        this.spacing = spacing;
	        this.dropAmount = spacing; // turun sebesar base_size
	        this.leftMargin = leftMargin;
	        this.topY = topY;
	        initRow(count);
	    }

	    private void initRow(int count) {
	        enemies.clear();
	        int size = spacing; // kotak musuh ukuran base_size
	        for (int i = 0; i < count; i++) {
	        	int x = leftMargin + i * (size + spacing);; // jarak antar = size (spacing)
	            int y = topY;
	            enemies.add(new Enemy(x,  y, size, Color.BLUE));
	        }
	    }

	    public void drawAll(Graphics g) {
	        for (Enemy e : enemies) e.draw(g);
	    }

	    // update positions untuk detect boundary
	    public void update(int panelWidth) {
	        if (enemies.isEmpty()) return;

	        // cek calon posisi ujung kanan/ kiri bila semua digeser
	        int delta = dir * speed;
	        // cari minX and maxX dari antara enemies
	        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
	        for (Enemy e : enemies) {
	            minX = Math.min(minX, e.getX());
	            maxX = Math.max(maxX, e.getX() + e.getWidth());
	        }

	        // jika bergerak ke kanan dan ujung kanan setelah digeser melebihi panel sehingga turun & balik arah
	        if (dir > 0 && (maxX + delta) >= panelWidth) {
	            // turunnya
	            for (Enemy e : enemies) e.translate(0, dropAmount);
	            dir = -1;
	        } else if (dir < 0 && (minX + delta) <= 0) {
	            // turun dan balik arah ke kanan
	            for (Enemy e : enemies) e.translate(0, dropAmount);
	            dir = 1;
	        } else {
	            // normal bergerak horizontal
	            for (Enemy e : enemies) e.translate(delta, 0);
	        }
	    }

	    // collision triangle polygon
	    public boolean checkCollisionWithPolygon(Polygon poly) {
	        if (poly == null) return false;
	        for (Enemy e : enemies) {
	            if (poly.intersects(e.getBounds())) return true;
	        }
	        return false;
	    }

	    public boolean anyReachedY(int yThreshold) {
	        for (Enemy e : enemies) {
	            if (e.getY() + e.getHeight() >= yThreshold) return true;
	        }
	        return false;
	    }

	    public List<Enemy> getEnemies() { return enemies; }
	}


