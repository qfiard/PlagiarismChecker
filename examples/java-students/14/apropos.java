
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
 
public class apropos extends JFrame implements ActionListener{
        
	private static final long serialVersionUID = 1L;

        private JLabel l_dev_by = new JLabel("Logiciel devellopé par :");
        private JLabel l_marco_pierro = new JLabel("Pierre Dargham et Marc Richard");
        

        
        public void actionPerformed(ActionEvent arg0) {
                
                this.setTitle("A Propos");
                this.setSize(400, 310);

                this.setLocationRelativeTo(null);
                //this.setResizable(false);
                this.setAlwaysOnTop(true);
                this.setBackground(Color.white);
                this.setLayout(new BorderLayout());


                JPanel pan = new JPanel();
                pan.add(l_dev_by);
                pan.add(l_marco_pierro);

                pan.add(new JLabel(new ImageIcon("n.gif")));

                this.setContentPane(pan);
                this.setVisible(true);
                
        }
}

