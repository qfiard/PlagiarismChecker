package interfaceDouane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FenetreErreur extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon icon2 = new ImageIcon("src/ic_menu_close_clear_cancel.png");
	private JButton annuler = new JButton(icon2);
	
	public FenetreErreur(String erreur){
		
		JLabel label = new JLabel(erreur);
		
		this.setTitle("---Message---");
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		annuler.setBorderPainted(false);
		annuler.setContentAreaFilled(false);
		annuler.addActionListener(this);
		
		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.CENTER);
		this.add(annuler, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == annuler){
			this.dispose();
		}
		
	}

}
