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

import Week_8_Lab.*;

public class MainGUI {

    private JFrame frame;

    private JPanel Gameplaypanel;

    private Triangle triangle;

    private EnemyManager enemyManager;

    // flag buat deteksi tombol kiri / kanan ditekan
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    // timer utama buat loop game (update dan repaint)
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
        initialize();
    }


    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false); // biar ukuran window gak bisa diubah
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
                startGame(); 
            }
        });
        btnNewButton.setFont(btnNewButton.getFont().deriveFont(30f));
        btnNewButton.setBackground(new Color(192, 192, 192));
        btnNewButton.setPreferredSize(new Dimension(80, 40));
        ButtonPanel.add(btnNewButton);

        // panel tempat gambar pesawat dan musuh
        Gameplaypanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // gambar pesawat 
                if (triangle != null) {
                    triangle.UpdateTrianglePoints(); // update posisi tiap frame
                    g.setColor(triangle.GetShapeColor());
                    g.fillPolygon(triangle.GetXPoints(), triangle.GetYPoints(), triangle.TRIANGLE_POINTS);
                }
                // gambar semua musuh lewat EnemyManager
                if (enemyManager != null) {
                    enemyManager.drawAll(g);
                }
            }
        };

        // listener keyboard buat gerak kiri/kanan
        Gameplaypanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
                else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    // tombol spasi nanti bisa buat nembak(denny
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // reset flag kalau tombol dilepas
                if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
            }
        });

        // setting tampilan panel game
        Gameplaypanel.setBackground(new Color(255, 255, 255)); //
        Gameplaypanel.setFocusable(true); // wajib biar key listener aktif
        MainPanel.add(Gameplaypanel, BorderLayout.CENTER);

        // timer utama buat update gerakan dan cek collision
        gameTimer = new javax.swing.Timer(16, ev -> { 
            // gerakin pesawat berdasarkan tombol
            if (triangle != null) {
                int panelW = Gameplaypanel.getWidth();
                int speed = 4;
                int newX = triangle.GetXCoord();
                if (leftPressed) newX -= speed;
                if (rightPressed) newX += speed;

                // batasin supaya pesawat gak keluar layar
                newX = Math.max(0, Math.min(newX, panelW - triangle.GetWidth()));
                if (newX != triangle.GetXCoord()) {
                    triangle.SetXCoord(newX);
                }
            }

            // cek tabrakan antara musuh dan pesawat
            if (triangle != null && enemyManager != null) {
                Polygon playerPoly = new Polygon(triangle.GetXPoints(), triangle.GetYPoints(), triangle.TRIANGLE_POINTS);

                // kalau musuh nabrak pesawat
                if (enemyManager.checkCollisionWithPolygon(playerPoly)) {
                    gameTimer.stop();
                    System.out.println(" Game Over: musuh nabrak pesawatmu!");
                }

                // kalau musuh udah nyampe posisi pesawat
                if (enemyManager.anyReachedY(triangle.GetYCoord())) {
                    gameTimer.stop();
                    System.out.println(" Game Over: musuh udah sampai ke bawah!");
                }
            }

            // refresh tampilan setiap frame
            Gameplaypanel.repaint();
        });
        gameTimer.start();
    }


    private void startGame() {
        int panelwidth = Gameplaypanel.getWidth();
        int panelheight = Gameplaypanel.getHeight();

        // buat pesawat 
        triangle = new Triangle();
        triangle.SetWidth(40);
        triangle.SetHeight(40);
        triangle.SetShapeColor(Color.green);
        triangle.SetXCoord((panelwidth - triangle.GetWidth()) / 2);
        triangle.SetYCoord(panelheight - 100);
        triangle.UpdateTrianglePoints();

        // set posisi awal musuh
        int leftMargin = 20;
        int topY = 20;

        // panggil enemy manager buat generate dan gerakin musuh
        enemyManager = new EnemyManager(15, leftMargin, topY, Triangle.BASE_SIZE, Gameplaypanel);

        // kasih fokus ke panel biar keyboard langsung berfungsi
        Gameplaypanel.requestFocusInWindow();
    }
}
