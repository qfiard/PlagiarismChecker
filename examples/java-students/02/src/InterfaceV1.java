import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class InterfaceV1 implements ActionListener{

	private static final String OK = " ";
	private static final String UK = " ";
	private static final String AK = " ";
	 
	private JFrame frame = new JFrame();
	private LienBD link;
	
	public InterfaceV1(LienBD l){
		this.link=l;
		frame.setTitle("Interface Simple");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400); 
		frame.setLocationRelativeTo(null);

		
        JComponent buttonPane = createButton();
        
        frame.add(buttonPane,BorderLayout.CENTER);
        frame.setVisible(true);
	}
	
	
	public JComponent createButton(){
		//JPanel p = new JPanel(new GridLayout(6,0));//lignes / colonnes
        JPanel p = new JPanel(new FlowLayout());
		JButton okButton = new JButton("Liste des produits");
        JButton akButton = new JButton("Liste des utilisateurs par type");
      	JButton ukButton = new JButton("Liste des commandes (init nulle)");
      	JLabel intro = new JLabel("Liste Fonctions Disponibles :");
      	JLabel blanc = new JLabel("                            ");
      	JLabel blanc1 = new JLabel("                            ");

        okButton.setActionCommand(OK);
        okButton.addActionListener(this);
        
        ukButton.setActionCommand(UK);
        ukButton.addActionListener(this);
        
        akButton.setActionCommand(AK);
        akButton.addActionListener(this);
        
      
        
        
        p.add(intro,BorderLayout.CENTER);
        p.add(blanc,BorderLayout.CENTER);
        p.add(blanc1,BorderLayout.CENTER);
        p.add(okButton,BorderLayout.CENTER);
        p.add(akButton,BorderLayout.CENTER);
        p.add(ukButton,BorderLayout.CENTER);
        
         return p;
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 String cmd = e.getActionCommand();
	        System.out.println(cmd);
			if (OK.equals(cmd)) 
			{  
		  	       
				JOptionPane.showMessageDialog(frame,
							"Veuillez patientez... Chargement de la Liste.");
				try {
					Liste_Produit t1 = new Liste_Produit(this.link);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	 
			} 
			else if (UK.equals(cmd))
			{
				
				JOptionPane.showMessageDialog(frame,
						"Veuillez patientez... Chargement de la Liste.");
				   try {
					   Liste_Utilisateur t1 = new Liste_Utilisateur(this.link);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}// objet liste commande controlees
			}
			else 
			{
				JOptionPane.showMessageDialog(frame,
				"Veuillez patientez... Chargement de la Liste.");
				try {
					Liste_commandes t1 = new Liste_commandes(this.link);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}// objet liste commande controlees
			}
		
	}



	
	
	
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		 InterfaceV1 i = new InterfaceV1(new LienBD("rouache","helloworld"));

	}

}
	