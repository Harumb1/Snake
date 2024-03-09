import javax.swing.*;

public class GameFrame extends JFrame {
        GameFrame(){
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.add(new GamePanel());
        /*/
        GamePanel panel = new GamePanel();
        this.add(panel);
        */
            //Same as Main.java
            this.setTitle("Snake");
            this.setResizable(false);
            this.pack();
            //Packs all the elements
            this.setVisible(true);
            this.setLocationRelativeTo(null);


        }

    }

