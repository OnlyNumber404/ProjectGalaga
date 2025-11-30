package Text;
import javax.swing.*;

public class MakeText extends JFrame {

    public MakeText() {
        setTitle("Text Display Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        JLabel label = new JLabel("This is a static text label.");
        panel.add(label);
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MakeText();
    }
}
