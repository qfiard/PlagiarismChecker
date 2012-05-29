 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.*;


public class Douane extends JFrame implements ActionListener{

	private JFrame controllingFrame = new JFrame();
	//private JTable table = new JTable();
	private JPanel panel = new JPanel();
	private String colis = " ";
	private String conteneur = "Liste des Conteneurs" ;
	private String palette = " Palettes" ;
	private String produits = " Produits" ;
	LienBD link;
	String login;
	
	public Douane(LienBD l, String log){
 		this.setTitle("Interface pour la douane");
 		this.setSize(500,500);
 		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		this.setLocationRelativeTo(null);
 		this.setContentPane(panel);
 		this.setVisible(true);
        JComponent buttonPane = createButton();
        this.setContentPane(buttonPane);
 		this.link = l;
 		this.login=log;
 	}
	

 
		 
	 
	
	public JComponent createButton(){
		JPanel p = new JPanel(new FlowLayout());
		
		//JButton colisButton = new JButton("Verifier un colis");
		//ce bouton n a pas ete implemente mais le sera pour la soutenance.
		
        JButton conteneurButton = new JButton("Liste Conteneurs / Pays");
       	JButton produitsButton = new JButton("Liste Produits / Cout");
      	
      	
       // colisButton.setActionCommand(colis);
      //  colisButton.addActionListener(this);
        
        conteneurButton.setActionCommand(conteneur);
        conteneurButton.addActionListener(this);
          
        produitsButton.setActionCommand(produits);
        produitsButton.addActionListener(this);
        
      
        
        
      //  p.add(colisButton,BorderLayout.NORTH);
        p.add(conteneurButton,BorderLayout.CENTER);
        p.add(produitsButton,BorderLayout.SOUTH);
        
        
        return p;
    
	}
 
	 
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
        System.out.println("Etat courant : "+cmd);
        
        
		if (conteneur.equals(cmd)) {
		
			JOptionPane.showMessageDialog(controllingFrame,
								"Veuillez patientez... Chargement de la Liste.");
		   try {
			 Liste_Conteneur l = new Liste_Conteneur(this.link,this.login);
		   } catch (ClassNotFoundException e1) {
				e1.printStackTrace();
		   } catch (SQLException e1) {
			   e1.printStackTrace();
		   }// objet liste commande controlees
		} 
		else if (produits.equals(cmd)){
 			
			JOptionPane.showMessageDialog(controllingFrame,
					"Veuillez patientez... Chargement de la Liste.");
			try {
				 Liste_Produit l = new Liste_Produit(this.link);
			} catch (SQLException e1) {
 				e1.printStackTrace();
			}// objet liste commande controlees
		}
		 
    }
		
}