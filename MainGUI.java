package Week_9;

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
}
