package Week_9;

<<<<<<< HEAD
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

import Week_8_Lab.*;


public class MainGUI {
    private JFrame frame;
    private JPanel Gameplaypanel;
    private Triangle triangle; // objek pemain (pesawat segitiga)

    private EnemyManager enemyManager; // mengatur musuhnya

    // flag input keyboard
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    // game loop timer (dipakai untuk update & repaint)
    private javax.swing.Timer gameTimer;


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
        frame.setResizable(false);;
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
                // custom drawing setiap repaint
                super.paintComponent(g);
                // gambar pemain (triangle)
                if (triangle != null) {
                    triangle.UpdateTrianglePoints(); // pastikan titik segitiga ter-update
                    g.setColor(triangle.GetShapeColor());
                    g.fillPolygon(triangle.GetXPoints(), triangle.GetYPoints(), triangle.TRIANGLE_POINTS);
                }
                // gambar semua musuh lewat enemyManager
                if (enemyManager != null) {
                    enemyManager.drawAll(g);
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
                    // kosong sekarang, bisa ditambahin buat menembak nanti
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

            // update musuh
            if (enemyManager != null) {
                enemyManager.update(Gameplaypanel.getWidth());

                // cek collision antara player polygon dan semua enemynya
                if (triangle != null) {
                    Polygon playerPoly = new Polygon(triangle.GetXPoints(), triangle.GetYPoints(), triangle.TRIANGLE_POINTS);
                    if (enemyManager.checkCollisionWithPolygon(playerPoly)) {
                        // game over kalau ada yang nabrak player
                        gameTimer.stop();
                        System.out.println("Game Over: musuh nabrak pesawatmu");
                    }
                    // tambahan: kalo ada musuh yang sampai ke level y player  game over
                    if (enemyManager.anyReachedY(triangle.GetYCoord())) {
                        gameTimer.stop();
                        System.out.println("Game Over: musuh sudah mencapai level ");
                    }
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

        // inisialisasi objek pemain/player (triangle)
        triangle = new Triangle();
        triangle.SetWidth(40);
        triangle.SetHeight(40);
        triangle.SetShapeColor(Color.green);
        triangle.SetXCoord((panelwidth - triangle.GetWidth()) / 2); // mulai di tengah layar
        triangle.SetYCoord(panelheight - 100); // posisi Y agak di atas bawah panel
        triangle.UpdateTrianglePoints(); // set titik polygon

        // inisialisasi musuh
        int leftMargin = 20; // jarak kiri dari edge
        int topY = 20; // posisi Y paling atas musuh
        int count = 5; // jumlah musuh di baris pertama (bisa disesuaikan)
        enemyManager = new EnemyManager(count, leftMargin, topY, Triangle.BASE_SIZE);

        // minta fokus ke panel supaya keyboard bisa dipakai tanpa klik lagi
        Gameplaypanel.requestFocusInWindow();
    }
=======
import Week_8_Lab.*;
import java.awt.EventQueue;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainGUI {

	private JFrame frame;
	private JPanel Gameplaypanel;
	private Triangle triangle;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel MainPanel = new JPanel();
		frame.getContentPane().add(MainPanel, BorderLayout.CENTER);
		MainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setPreferredSize(new Dimension(10, 50));
		MainPanel.add(ButtonPanel, BorderLayout.SOUTH);
		ButtonPanel.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("START");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int panelwidth = Gameplaypanel.getWidth();
				int panelheight = Gameplaypanel.getHeight();
				triangle = new Triangle();
				triangle.setHeight(40);
				triangle.setWidth(40);
				triangle.setShapeColor(Color.green);
				triangle.setXcoor((panelwidth - triangle.getWidth())/2);
				triangle.setYcoor(panelheight - 100);
				
				Gameplaypanel.repaint();
				
				Gameplaypanel.requestFocusInWindow();
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setPreferredSize(new Dimension(80, 40));
		ButtonPanel.add(btnNewButton);
		
		Gameplaypanel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (triangle != null) {
					triangle.UpdateTrianglePoints();
					g.setColor(triangle.GetShapeColor());
					g.fillPolygon(triangle.getXpoints(),triangle.getYpoints(),triangle.TRIANGLE_POINTS);
					
				}
			}
		};
		Gameplaypanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (triangle != null) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						triangle.setXcoor(triangle.getXcoor()-10);
						System.out.println("Key pressed: " + e.getKeyCode());
						Gameplaypanel.repaint();
						break;
					case KeyEvent.VK_RIGHT:
						triangle.setXcoor(triangle.getXcoor()+10);
						System.out.println("Key pressed: " + e.getKeyCode());
						Gameplaypanel.repaint();
						break;				
					}
				}
			}
			  
		});
		Gameplaypanel.setBackground(new Color(255, 255, 255));
		Gameplaypanel.setFocusable(true);
		MainPanel.add(Gameplaypanel, BorderLayout.CENTER);
	}
>>>>>>> 4c58fa10b1a9ab1426bab0e5100c17f9376270e0
}
