import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//Gerant peut changer le prix du produit grace a cette classe
public class ChangePrix extends JFrame {
	CreationBase connecteBD;
	
	private JPanel container = new JPanel();
	private JPanel container1 = new JPanel();
    
	
	public JTextField produit=new JTextField();
	public JTextField prix=new JTextField();
	
    
	private JLabel label_produit = new JLabel("Numero du produit : ");
	private JLabel label_prix = new JLabel("Nouveau prix du produit :");
	
	JButton b=new JButton("OK");
	JOptionPane jop;
	
	public ChangePrix(final CreationBase connecteBD){
		this.connecteBD=connecteBD;
		this.setTitle("Changer prix du produit");
        this.setSize(450,130);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        produit.setPreferredSize(new Dimension(150,25));
        prix.setPreferredSize(new Dimension(150,25));
        
        container.add(label_produit);
        container.add(produit);
        container.add(label_prix);
        container.add(prix);
        container.setLayout(new GridLayout(2,2));
        
        container1.add(b);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(container,BorderLayout.NORTH);
        this.getContentPane().add(container1,BorderLayout.SOUTH);
        this.setVisible(true);
        
        b.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				try {
					if(connecteBD.produit_existe("produit",produit.getText()) == 1){
						connecteBD.changer_prixproduit(produit.getText(),prix.getText());
						jop = new JOptionPane();
						jop.showMessageDialog(null, "Reussir le changement", "Bravo", JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
					}
					else{
						jop = new JOptionPane();
						jop.showMessageDialog(null, "Produit n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
			}				
		});
        
	}
}
