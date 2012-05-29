package interfaceDouane;



import interfaceClient.JSelectionButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
//import javax.swing.table.DefaultTableModel;

import donnees.ConnexionBD;
import donnees.Constantes;

public class FenetreResControle2 extends JPanel{
//public class FenetreResControle2 extends JFrame{

	/**
	 * 
	 */

	Random r = new Random();

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollJT;
	private JTable jt;
	private JPanel panelTable;

	private ImageIcon icon = new ImageIcon("src/ic_menu_refresh.png");
	private JButton refresh = new JButton(icon);

	private String[] columnNames = { "ID_colis", "ID_commande", "ID_produit", "quantite", "Statut",  "Resultat" };
	private Object[][] donnees;
	private LinkedList<Object[]> save = new LinkedList<Object[]>();
	private maTable model;

	private LinkedList<Integer> idsColis = new LinkedList<Integer>();

	private int nbTotalColis, nbColisVerif;

	private String requete = "SELECT  id_colis FROM " + Constantes.base_colis + " natural join " + Constantes.base_commande + " WHERE etat = 'partiellement-expediee' ORDER BY id_colis";
	private String pays;
	

	public FenetreResControle2(String idDouane, String dest){
		
		this.pays = dest;
		
		ConnexionBD bd = new ConnexionBD();
		Statement st = bd.createStatement();

		try {

			ResultSet tauxD = st.executeQuery("SELECT taux_colis_verifie FROM " + Constantes.base_douane	+  " WHERE id_douane = '" +idDouane +"';");

			if(tauxD.next()){
				int taux = tauxD.getInt(1);

				if(taux != 0){

					generationCom(dest, taux);

				}
				else{
					String message = "Pas de controle pour cette douane";
					JOptionPane.showMessageDialog(null, message);
					
					
					ResultSet rs = st.executeQuery("SELECT id_commande FROM " + Constantes.base_commande + " natural join " + Constantes.base_client 
								+ " WHERE pays = '" + pays + "' AND etat = 'partiellement-expediee';");
					
					if(rs.next()){
						rs.previous();
						ConnexionBD bd2 = new ConnexionBD();
						Statement stmt = bd2.createStatement();
						while(rs.next()){
							int id_commande = rs.getInt(1);
							System.out.println(id_commande);
							int res =  stmt.executeUpdate("UPDATE colis SET statut='controle' WHERE id_commande = " + id_commande + ";");
							if(res == 0){
								System.out.println("na pas marche");
							}
							
						}
					}
					else{
						System.out.println("pas trouver");
					}
				}


			}
			else{
				String message = "Aucune commande receptionnee";
				JOptionPane.showMessageDialog(null, message);
			}

			tauxD.close();
			st.close();
			bd.close();


		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void generationCom(String pays, int taux){
		
		//System.out.println("pays " + pays);
		//System.out.println("taux " + taux);

		ConnexionBD  bd = new ConnexionBD();
		Statement st = bd.createStatement();

		ResultSet rs;
		try {
			rs = st.executeQuery(requete);

			if(rs.next()){
				rs.previous();
				while(rs.next()){
					idsColis.add(rs.getInt(1));
				}


				nbTotalColis = idsColis.size();
				System.out.println("nbTotalColis " + nbTotalColis);
				nbColisVerif = (taux * nbTotalColis)/100;
				System.out.println("nbColisVerif " + nbColisVerif);

				for(int i =0; i<nbColisVerif; i++){

					//int indiceColis = r.nextInt(nbTotalColis);
					int indiceColis = r.nextInt(idsColis.size());
					System.out.println("indiceColis "  + indiceColis);
					int idColis = idsColis.remove(indiceColis);
					System.out.println("idColis " + idColis );

					ConnexionBD bd2 = new ConnexionBD();
					Statement stmt = bd2.createStatement();

					ResultSet rs2 = stmt.executeQuery("SELECT id_colis, id_commande, id_produit, quantite,  statut FROM " + Constantes.base_colis
							+  " natural join " + Constantes.base_contenuCommande + " WHERE id_colis = " + idColis + " ORDER BY id_colis ;");

					if(rs2.next()){
						//Object[] data = new Object[6];
						Object[] data = new Object[6];

						data[0] = rs2.getInt(1);
						
						data[1] = rs2.getInt(2);
						
						data[2] = rs2.getString(3);
						
						data[3] = rs2.getInt(4);
						
						data[4] = rs2.getString(5);
					
						data[5] =new JButton("Modifier");
						
						
						
						save.add(data);
					}
					/*else{
						System.out.println("prout");
					}*/
					

					rs2.close();
					stmt.close();
					bd2.close();

				}

			}

			ajouterTable(save);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}



	public void ajouterTable(LinkedList<Object[]> save){

		System.out.println("size save " + save.size());

		donnees = new Object[save.size()][];

		for(int i=0; i<donnees.length; i++){
			donnees[i] = save.get(i);
		}
		
		System.out.println("OK");

		/*model = new maTable();
		model.setHeader(columnNames);
		model.setData(donnees);

		jt = new JTable(model);*/
		
		
		jt=new JTable(donnees, columnNames);
		
		//jt = new JTable(tableModel);
		//System.out.println("columnNAmes 6 " + columnNames[6]);
		jt.getColumn(columnNames[5]).setCellRenderer(new JSelectionButton());
		jt.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e){
				int col= jt.columnAtPoint(e.getPoint());


				if(col != 5){
					return;
				}

				int row = jt.rowAtPoint(e.getPoint());


				//JButton bouton = (JButton)jt.getValueAt(row, 5);
				Integer i = (Integer)(jt.getValueAt(row, 0));
				//System.out.print
				new FenetreDecl(i.intValue());						
			}
		});

		/*panelTable = new JPanel();
		panelTable.setLayout(new BorderLayout());
		panelTable.add(jt.getTableHeader(), BorderLayout.PAGE_START);
		
		//System.out.println(jt.getTableHeader());
		//scrollJT = new JScrollPane(jt);
		panelTable.add(jt, BorderLayout.CENTER);	

		panelTable.revalidate();
		panelTable.setPreferredSize(new Dimension(600,300));*/

		//table+bouton
		//this.setLayout(new BorderLayout());
		//this.setContentPane(jt);
		//this.setContentPane(new JScrollPane(jt));
		this.add(new JScrollPane(jt));
		this.setVisible(true);

	}
	
	/*public static void main(String[] args){
		FenetreResControle2 fs = new FenetreResControle2("1");
	}*/



}
