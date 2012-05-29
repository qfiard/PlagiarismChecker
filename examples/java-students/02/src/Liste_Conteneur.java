import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class Liste_Conteneur implements ActionListener{

	LienBD link;
	int numConteneur;
	JTextField jtf;
	JLabel lab_numC ;
	JButton valider;
	String login;
	JTable aTable;
	JFrame frame;
	
	public Liste_Conteneur(LienBD l, String log) throws ClassNotFoundException, SQLException{
		frame = new JFrame();
 		frame.setSize(300,300);
 		
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel titre = new JLabel(" Liste des Conteneurs :");
		JComponent buttonPane = createButton();
 		this.link=l;
 		this.login = log;
 		lab_numC = new JLabel("Numero Conteneur");
  		jtf = new JTextField("Veuillez entrer un numero de conteneur...");
  		lab_numC.setLabelFor(jtf);
 		valider = new JButton();
  		
  		
  		try{	
	      	numConteneur = Integer.parseInt(jtf.getText());
      	} catch(NumberFormatException e){
      		System.err.println("Le format n est pas bon.");
      	}
 		
  		String[] entetes = {"Numero ID","Pays"};
 		//System.out.println("Avant le RS");
 		ResultSet rspays = this.link.querySQL("select pays from utilisateur where login=\'"+this.login+"\';");
 		String pays="";
 		if(rspays.next()){
 			 pays = rspays.getString("pays"); 
 		} else {
 			System.out.println("Erreur dans la Base de Donnees.");
 		}
 		
 		ResultSet rs = this.link.querySQL("select num_conteneur from conteneur where destination =\'"+pays+"\';");  
 		//me renvoie la liste des conteneurs correspondant au pays de la douane
 		//System.out.println("Apres le RS");
		rs.last();
		int nbrows=rs.getRow();
		Object [][] conteneurs=new Object[nbrows-1][2];
		rs.first();
		try{
 			for(int i=0;i<nbrows-1;i++){
				if(rs.next()){
					conteneurs[i][0]=rs.getString("num_conteneur");
					conteneurs[i][1]=rs.getString("destination");
				}
 			}
		} catch(Exception e){
		e.printStackTrace();
		System.out.println("SQLException");
		}
 
		
		
		aTable = new JTable(conteneurs,entetes);
		JScrollPane scroll= new JScrollPane(aTable);
		panel.add(titre,BorderLayout.NORTH);
		panel.add(scroll);
		panel.add(jtf,BorderLayout.SOUTH);
		panel.add(buttonPane);		
		
 		frame.setLayout(new BorderLayout());
		frame.setContentPane(panel);
		frame.setVisible(true);
	
	
	}
   
	
	public JComponent createButton(){
		JPanel p = new JPanel(new FlowLayout());
		
		JButton valider = new JButton("Valider");
    
		valider.setActionCommand("valider");
        valider.addActionListener(this);
		
        p.add(valider,BorderLayout.SOUTH);
        return p;
    
	}


	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
        System.out.println("Etat courant : "+cmd);
        
        
		if (valider.equals(cmd)) {
		
			JOptionPane.showMessageDialog(frame,
								"Veuillez patientez... Chargement de la Liste.");
		   try {
			   new Liste_Palette(this.link,this.numConteneur);
 		   } catch (ClassNotFoundException e1) {
				e1.printStackTrace();
		   } catch (SQLException e1) {
			   e1.printStackTrace();
		   }// objet liste commande controlees
		} 
		 
    }
	
}
