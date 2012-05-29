package interfaceDouane;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreDecl extends JFrame implements ActionListener{

	private int id;
	private Object[] decl, colis;

	private ImageIcon icon = new ImageIcon("src/ic_menu_forward.png");
	private JButton ok = new JButton("resultat");
	private JLabel message;
	private JPanel jp;

	private static final long serialVersionUID = 1L;

	public FenetreDecl(int ID_colis){

		//System.out.println(ID_colis);

		this.setTitle("---Declaration du colis---");
		this.setSize(250, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.id = ID_colis;

		jp = new JPanel();
		//jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));

		jp.setLayout(new BorderLayout());


		boolean b = estDeclare();
		boolean q = estBonQual();

		if(b && q){
			message = new JLabel("Le colis est bien declare");

		}
		else{
			message = new JLabel("Le colis n'est pas bien declare");

		}

		ok.addActionListener(this);

		jp.add(message, BorderLayout.CENTER);
		jp.add(ok, BorderLayout.SOUTH);

		/*jp.add(message);
		jp.add(ok);*/

		//message.setAlignment(Component.CENTER_ALIGNMENT);
		this.setContentPane(jp);
		this.setVisible(true);
		//System.out.println("hello");

	}

	public boolean estBonQual(){

		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();

		try {
			ResultSet rs = st.executeQuery("SELECT type_emballage FROM " + Constantes.base_emballage + " WHERE id_colis = " + id + ";");
			if(rs.next()){
				int type_embal = rs.getInt(1);

				ResultSet rs2 = st.executeQuery("SELECT qualifiant FROM " + Constantes.base_colis + " WHERE id_colis = " + id + ";");
				if(rs2.next()){
					int qual = rs2.getInt(1);
					if( type_embal == qual){
						return true;
					}
					rs2.close();
				}
				
			}
			rs.close();
			bd.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return false;	

	}


	public boolean estDeclare(){

		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();

		try {
			ResultSet rs = st.executeQuery("SELECT * FROM " + Constantes.base_declaration + " WHERE id_colis = " + id + ";");
			if(rs.next()){
				//rs.previous();
				decl = new Object[3];

				decl[0] = rs.getInt(1);
				decl[1] = rs.getString(2);
				decl[2] = rs.getInt(3);

			}


			//+  "natural join" + Constantes.base_produit 

			ResultSet rs2 = st.executeQuery("SELECT  id_colis,  id_produit, quantite FROM " + Constantes.base_contenuColis + " WHERE id_colis = " + id + ";");

			if(rs2.next()){
				//rs2.previous();
				colis = new Object[3];

				colis[0] = rs2.getInt(1);
				colis[1] = rs2.getString(2);
				colis[2] = rs2.getInt(3);
	

			}


			if(decl[0] == colis[0] && decl[1].equals(decl[1]) && decl[2] == colis [2] ){
				return true;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;
	}



	/*public static void main(String[] args){
		FenetreDecl fd = new FenetreDecl(1);

	}*/


	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ok){
			FenetreModif fm =  new FenetreModif(id);
			this.dispose();
		}

	}

}
