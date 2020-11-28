import javax.swing.JFrame;

public class Board extends JFrame {
    Board() {
        this.add(new Snake());
        this.setTitle("Snake Game By Dian's Genk");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}