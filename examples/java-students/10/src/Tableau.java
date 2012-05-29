
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Tableau extends JFrame {
    public Tableau(String title, String[] entetes,String[][] donnees) {
        super();
        
        setTitle(title);
        
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JTable tableau = new JTable(donnees, entetes);
        
        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
        
        pack();
    }
}
