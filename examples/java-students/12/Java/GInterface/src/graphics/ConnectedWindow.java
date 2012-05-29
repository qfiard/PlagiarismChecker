package graphics;

import java.awt.*;
import javax.swing.*;

import java.sql.*;

public class ConnectedWindow extends JFrame {
	private static final long serialVersionUID = 6978270229314195891L;
	public ConnectionWindow fenetre;
	public String fonction;
	public int id;
	public RequestDB bdd;
	ResultSet rs;
	int nbL;

	public ConnectedWindow(ConnectionWindow fenetre, int id, String fonction) {
		super();
		this.fenetre = fenetre;
		this.fonction = fonction;
		this.id = id;
		build();
	}

	private void build() {
		// Connection à la BDD
		try {
			bdd = new RequestDB();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		// On donne un titre à l'application
		setTitle(fonction);
		// On donne une taille à notre fenêtre
		setSize(800, 600);
		// On centre la fenêtre sur l'écran
		setLocationRelativeTo(null);
		// On autorise la redimensionnement de la fenêtre
		setResizable(true);
		// On dit à l'application de se fermer lors du clic sur la croix
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(buildContentPane());

		if (fonction.equals("douane")) {
			try {
				rs = bdd.getArrived(bdd.getCountry(id));
				rs.last();
				// on récupère le numéro de la ligne
				nbL = rs.getRow();
				// on repace le curseur avant la première ligne
				rs.beforeFirst();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			String entetes[] = { "Num", "Adresse", "Date cmd", "Date liv",
					"Total" };

			// System.out.println(nbL);
			String donnees[][] = new String[nbL][entetes.length];

			try {
				int j = 0;
				while (rs != null && rs.next()) {
					for (int i = 0; i < entetes.length; i++)
						donnees[j][i] = rs.getString(entetes[i]);
					j++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			MyTableModel absdonnees = new MyTableModel(entetes, donnees);

			JTable data = new JTable(absdonnees);

			JScrollPane asc = new JScrollPane(data);
			asc.setViewportView(data);

			JMenuBar menuBar = new JMenuBar();

			JMenu menu1 = new JMenu("Commande");
			JMenuItem arriv = new JMenuItem(new DouaneAction(bdd, data,
					absdonnees, id, "Arrivées"));
			menu1.add(arriv);
			JMenuItem control = new JMenuItem(new DouaneAction(bdd, data,
					absdonnees, id, "Contrôlées"));
			menu1.add(control);
			JMenuItem noncon = new JMenuItem(new DouaneAction(bdd, data,
					absdonnees, id, "Non contrôlées"));
			menu1.add(noncon);
			JMenuItem recher = new JMenuItem(new DouaneAction(bdd, data,
					absdonnees, id, "Recherche"));
			menu1.add(recher);
			JMenuItem stat = new JMenuItem(new DouaneAction(bdd, data,
					absdonnees, id, "Statistiques"));
			menu1.add(stat);
			menuBar.add(menu1);

			JMenu menu2 = new JMenu("Options");
			JMenuItem deco = new JMenuItem(new OptionsAction(this, fenetre,
					bdd, "Déconnection"));
			menu2.add(deco);
			JMenuItem quit = new JMenuItem(new OptionsAction(this, fenetre,
					bdd, "Quitter"));
			menu2.add(quit);
			menuBar.add(menu2);

			JMenu menu3 = new JMenu("?");
			JMenuItem apropo = new JMenuItem(
					new AproposAction(this, "À propos"));
			menu3.add(apropo);
			menuBar.add(menu3);

			setJMenuBar(menuBar);

			getContentPane().add(data.getTableHeader(), BorderLayout.NORTH);
			getContentPane().add(asc, BorderLayout.CENTER);

		} else if (fonction.equals("gerant")) {
			try {
				rs = bdd.getClient();
				rs.last();
				// on récupère le numéro de la ligne
				nbL = rs.getRow();
				// on repace le curseur avant la première ligne
				rs.beforeFirst();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			String entetes[] = { "Num", "Adresse", "Ville", "CP", "Pays",
					"Tel", "Société" };
			// System.out.println(nbL);
			String donnees[][] = new String[nbL][entetes.length];

			try {
				int j = 0;
				while (rs != null && rs.next()) {
					for (int i = 0; i < entetes.length; i++)
						donnees[j][i] = rs.getString(i + 1);
					j++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			MyTableModel absdonnees = new MyTableModel(entetes, donnees);

			JTable data = new JTable(absdonnees);

			JScrollPane asc = new JScrollPane(data);
			// asc.setViewportView(data);

			JMenuBar menuBar = new JMenuBar();

			JMenu menu1 = new JMenu("Voir");
			JMenuItem client = new JMenuItem(new GerantAction(bdd, data,
					absdonnees, "Clients"));
			menu1.add(client);
			JMenuItem emball = new JMenuItem(new GerantAction(bdd, data,
					absdonnees, "Emballeurs"));
			menu1.add(emball);
			JMenuItem transport = new JMenuItem(new GerantAction(bdd, data,
					absdonnees, "Transporteurs"));
			menu1.add(transport);
			menuBar.add(menu1);

			JMenu menu2 = new JMenu("Etat");
			JMenuItem prod = new JMenuItem(new GerantAction(bdd, data,
					absdonnees, "Produits"));
			menu2.add(prod);
			JMenuItem cmd = new JMenuItem(new GerantAction(bdd, data,
					absdonnees, "Commandes"));
			menu2.add(cmd);
			JMenuItem colis = new JMenuItem(new GerantAction(bdd, data,
					absdonnees, "Colis"));
			menu2.add(colis);
			menuBar.add(menu2);

			JMenu menu3 = new JMenu("Options");
			JMenuItem deco = new JMenuItem(new OptionsAction(this, fenetre,
					bdd, "Déconnection"));
			menu3.add(deco);
			JMenuItem quit = new JMenuItem(new OptionsAction(this, fenetre,
					bdd, "Quitter"));
			menu3.add(quit);
			menuBar.add(menu3);

			JMenu menu4 = new JMenu("?");
			JMenuItem apropo = new JMenuItem(
					new AproposAction(this, "À propos"));
			menu4.add(apropo);
			menuBar.add(menu4);

			setJMenuBar(menuBar);

			getContentPane().add(data.getTableHeader(), BorderLayout.NORTH);
			getContentPane().add(asc, BorderLayout.CENTER);
		}
	}

	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);

		return panel;
	}
}
