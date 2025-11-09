package Week_9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Text.MakeText;
import Week_8_Lab.*;
import Sound.SoundPlayer;

public class MainGUI {
    private JFrame frame;
    private JPanel Gameplaypanel;
    private Triangle triangle; // objek pemain (pesawat segitiga)
//    private Rectangle rectangle; // objek peluru (Laser) one
    private LinkedList<Rectangle> lasers = new LinkedList<>();
    private EnemyManager enemyManager; // mengatur musuhnya

    // flag input keyboard
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    // game loop timer (dipakai untuk update & repaint)
    private javax.swing.Timer gameTimer;
    //untuk score
    private int score;
    //untuk text Game Over
    private boolean lose = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainGUI window = new MainGUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public MainGUI() {
// panggil inisialisasi GUInya
        initialize();
    }

    /**
     * Initialize
     *
     *  atur layout, tombol START, panel game, listener, dan timer.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);//agar ukuran frame tetap (tidak bisa berubah)
        frame.setBounds(100, 100, 450, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel MainPanel = new JPanel();
        frame.getContentPane().add(MainPanel, BorderLayout.CENTER);
        MainPanel.setLayout(new BorderLayout(0, 0));

        JPanel ButtonPanel = new JPanel();
        ButtonPanel.setPreferredSize(new Dimension(10, 60));
        MainPanel.add(ButtonPanel, BorderLayout.SOUTH);
        ButtonPanel.setLayout(new BorderLayout(0, 0));

        JButton btnNewButton = new JButton("START");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // pas tombol diklik: mulai game
                startGame();
            }
        });
        btnNewButton.setFont(btnNewButton.getFont().deriveFont(30f));
        btnNewButton.setBackground(new Color(192, 192, 192));
        btnNewButton.setPreferredSize(new Dimension(80, 40));
        ButtonPanel.add(btnNewButton);

        Gameplaypanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // gambar pemain (triangle)
                if (triangle != null) {
                    triangle.UpdateTrianglePoints(); //update posisi triangle
                    g.setColor(triangle.GetColor());
                    g.fillPolygon(triangle.GetXPoints(), triangle.GetYPoints(), triangle.TRIANGLE_POINTS);
                }
                //gamabar semua peluru dari linked List
                Node<Rectangle> curr = lasers.GetLinkedListHead();
                while (curr != null) {
                	Rectangle laser = curr.GetNodeData();
                	g.setColor(laser.GetColor());
                	g.fillRect(laser.GetXCoord(),laser.GetYCoord(), laser.GetWidth(), laser.GetHeight());
                	curr = curr.GetNext();
                }
                // gambar semua musuh lewat enemyManager
                if (enemyManager != null) {
                    enemyManager.drawAll(g);
                }
                // untuk score
                g.setColor(Color.black);
                g.setFont(g.getFont().deriveFont(20f));
                g.drawString("Score: " + score, 20, 30);
                
                //untuk Game Over
                if (lose) {
                	int x = (100);
                	int y = (Gameplaypanel.getSize().height/2);
                	g.setColor(Color.black);
                	g.setFont(g.getFont().deriveFont(40f));
                	g.drawString("GAME OVER",x ,y);
                }
            }
        };

        // keylistener keyboard untuk kontrol kiri/kanan
        Gameplaypanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // cek tombol yang ditekan, set flag biar per-frame update gerak
                if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
                else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                	SoundPlayer.playSound("src/laser-312360.wav");
                	makelaser();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // reset flag pas tombol dilepas
                if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
            }
        });

        Gameplaypanel.setBackground(new Color(255, 255, 255));
        Gameplaypanel.setFocusable(true); // biar bisa nerima input keyboard
        MainPanel.add(Gameplaypanel, BorderLayout.CENTER);
        gameTimer = new javax.swing.Timer(16, ev -> {
            // update posisi pemain berdasarkan keyboard
            if (triangle != null) {
                int panelW = Gameplaypanel.getWidth();
                int speed = 4; // kecepatan pemain (pesawat) kanamn -kiri nya
                int newX = triangle.GetXCoord();
                if (leftPressed) newX -= speed;
                if (rightPressed) newX += speed;

                // batasin agar pemain(peswat) gak keluar panel
                newX = Math.max(0, Math.min(newX, panelW - triangle.GetWidth()));
                if (newX != triangle.GetXCoord()) {
                    triangle.SetXCoord(newX);
                }
            }
            
            //update posisi peluru
            Node<Rectangle> curr = lasers.GetLinkedListHead();
            Node<Rectangle> prev = null;
            while (curr != null) {
            	Rectangle laser = curr.GetNodeData();
            	int newY = laser.GetYCoord()-10;
            	laser.SetYCoord(newY);
            	
            	if (newY < 0) {
            		if (prev == null) {
            			lasers = removeHead(lasers);
            			curr = lasers.GetLinkedListHead();
            			System.out.println("Remove laser");
            			continue;
            			} else {
            				prev.SetNext(curr.GetNext());
            		}
            	}else {
            		prev = curr;
            		curr = curr.GetNext();
            	}
            }
            
            Node<Rectangle> laserNode = lasers.GetLinkedListHead();
            while (laserNode != null) {
            	Rectangle laser = laserNode.GetNodeData();
            	
            	Node<Shape> enemyNode = enemyManager.getEnemyList().GetLinkedListHead();
            	boolean hit = false;
            	
            	while (enemyNode != null) {
            		Shape enemy = enemyNode.GetNodeData();
            		
            		if (isColl(laser,enemy)) {
            			lasers.RemoveNodeByData(laser);
            			enemyManager.getEnemyList().RemoveNodeByData(enemy);
            			SoundPlayer.playSound("src/080884_bullet-hit-39872.wav");
            			score += 1;
            			System.out.println("Enemy Hit");
            			hit = true;
            			break;
            		}
            		enemyNode = enemyNode.GetNext();
            	}
            	if (hit) break;
            	laserNode = laserNode.GetNext();
            }

            // cek tabrakan anatar musuh dan pesawat
            if (triangle != null && enemyManager != null) {
            	Polygon playerPoly = new Polygon(triangle.GetXPoints(),triangle.GetYPoints(),triangle.TRIANGLE_POINTS);
            	
            	// kalo musuh nabarak pesawat
            	if (enemyManager.checkCollisionWithPolygon(playerPoly)) {
            		gameTimer.stop();
            		enemyManager.stopAll();
            		lose = true;
            		System.out.println("Game OVER");
            	}
            	//kalo musuh udah sampai posisi pesawat
            	if (enemyManager.anyReachedY(triangle.GetYCoord())) {
            		gameTimer.stop();
            		enemyManager.stopAll();
            		lose = true;
            		System.out.println("Game OVER");
            	}
            }
            // repaint panel tiap frame
            Gameplaypanel.repaint();
        });
        gameTimer.start();
    }

    private void startGame() {
        // dipanggil pas tombol START diklik
        int panelwidth = Gameplaypanel.getWidth();
        int panelheight = Gameplaypanel.getHeight();

        // inisialisasi objek pemain/player 
        triangle = new Triangle();
        triangle.SetWidth(20);
        triangle.SetHeight(20);
        triangle.SetColor(Color.green);
        triangle.SetXCoord((panelwidth - triangle.GetWidth()) / 2); // mulai di tengah layar
        triangle.SetYCoord(panelheight - 100); // posisi Y agak di atas bawah panel
        triangle.UpdateTrianglePoints(); // set titik polygon
        
        // inisialisasi musuh
        int leftMargin = 20;
        int topY = 20;
        enemyManager = new EnemyManager(15, leftMargin, topY, Triangle.BASE_SIZE,Gameplaypanel);

        // minta fokus ke panel
        Gameplaypanel.requestFocusInWindow();
    }
    
    private void makelaser() {
    	int panelwidth = Gameplaypanel.getWidth();
    	int panelheight = Gameplaypanel.getWidth();
    	
    	// inisialisasi
    	Rectangle laser = new Rectangle();
    	laser.SetWidth(5);
    	laser.SetHeight(30);
    	laser.SetColor(Color.red);
    	laser.SetXCoord(triangle.GetXCoord() + (triangle.GetWidth()/2)-3);
    	laser.SetYCoord(triangle.GetYCoord() - 40);
    	lasers.AddNode(new Node<>(laser));
    }
    
    private LinkedList<Rectangle> removeHead(LinkedList<Rectangle> list){
    	Node<Rectangle> head = list.GetLinkedListHead();
    	if (head != null) {
    		list = new LinkedList<>();
    		Node<Rectangle> next = head.GetNext();
    		while (next != null) {
    			list.AddNode(new Node<>(next.GetNodeData()));
    			next = next.GetNext();
    		}
    	}
    	return list;
    }
    //coll antara laser dan enemy
    private boolean isColl(Rectangle laser,Shape enemy) {
    	//ambil enemy hit box
    	int ex = enemy.GetXCoord();
    	int ey = enemy.GetYCoord();
    	int ew = enemy.GetWidth();
    	int eh = enemy.GetHeight();
    	
    	//ambil laser hit box
    	int lx = laser.GetXCoord();
    	int ly = laser.GetYCoord();
    	int lw = laser.GetWidth();
    	int lh = laser.GetHeight();
    	
    	int margin = 5;
    	
    	return lx < ex + ew - margin && lx + lw > ex + margin && ly < ey + eh - margin && ly + lh > ey + margin; 
    }
}
