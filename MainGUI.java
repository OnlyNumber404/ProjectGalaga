package Week_9;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;

public class MainGUI {

	private JFrame frame;

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
		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setPreferredSize(new Dimension(80, 40));
		ButtonPanel.add(btnNewButton);
		
		JPanel Gameplaypanel = new JPanel();
		Gameplaypanel.setBackground(new Color(255, 255, 255));
		MainPanel.add(Gameplaypanel, BorderLayout.CENTER);
	}

}
