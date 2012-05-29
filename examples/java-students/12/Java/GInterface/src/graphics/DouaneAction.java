package graphics;

import javax.swing.*;

import java.awt.event.*;
import java.sql.*;

public class DouaneAction extends AbstractAction {
	private static final long serialVersionUID = -8874263368273380218L;
	RequestDB bdd;
	ConnectedWindow connected;
	String texte;
	ResultSet rs;
	JTable data;
	MyTableModel donnees;
	int id, nbL;

	public DouaneAction(RequestDB bdd, JTable data, MyTableModel donnees,
			int id, String texte) {
		super(texte);
		this.texte = texte;
		this.bdd = bdd;
		this.data = data;
		this.id = id;
		this.donnees = donnees;
	}

	public void actionPerformed(ActionEvent e) {
		if (texte.equals("Arrivées")) {
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
			// System.out.println(nbL);
			String entetes[] = { "Num", "Adresse", "Date cmd", "Date liv",
					"Total", "Vérifié" };
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

			MyTableModel test = new MyTableModel(entetes, donnees);
			data.setModel(test);

		} else if (texte.equals("Contrôlées")) {
			try {
				rs = bdd.getControled(bdd.getCountry(id));
				rs.last();
				// on récupère le numéro de la ligne
				nbL = rs.getRow();
				// on repace le curseur avant la première ligne
				rs.beforeFirst();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// System.out.println(nbL);
			String entetes[] = { "Num", "Adresse", "Date cmd", "Date liv",
					"Total", "Vérifié" };
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

			MyTableModel test = new MyTableModel(entetes, donnees);
			data.setModel(test);

		} else if (texte.equals("Non contrôlées")) {
			try {
				rs = bdd.getNotControled(bdd.getCountry(id));
				rs.last();
				// on récupère le numéro de la ligne
				nbL = rs.getRow();
				// on repace le curseur avant la première ligne
				rs.beforeFirst();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			// System.out.println(nbL);
			String entetes[] = { "Num", "Adresse", "Date cmd", "Date liv",
					"Total", "Vérifié" };
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

			MyTableModel test = new MyTableModel(entetes, donnees);
			data.setModel(test);

		} else if (texte.equals("Recherche")) {
			String cmd = (String) JOptionPane.showInputDialog(null,
					"Entrez un numéro de commande");

			try {
				rs = bdd.getSearch(cmd);
				rs.last();
				// on récupère le numéro de la ligne
				nbL = rs.getRow();
				// on repace le curseur avant la première ligne
				rs.beforeFirst();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			// System.out.println(nbL);
			String entetes[] = { "Num", "Quantité", "Adresse", "Date cmd",
					"Date liv", "Total", "Vérifié" };
			String donnees[][] = new String[nbL][entetes.length];

			try {
				int j = 0;
				while (rs.next()) {
					for (int i = 0; i < entetes.length; i++)
						donnees[j][i] = rs.getString(i + 1);
					j++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			MyTableModel test = new MyTableModel(entetes, donnees);
			data.setModel(test);

		} else if (texte.equals("Statistiques")) {
			String pays = "";
			String nbCmd = "";
			String nbCmdCon = "";
			int stat;

			try {
				pays = bdd.getCountry(id);
				nbCmd = bdd.getNbCmd(pays);
				nbCmdCon = bdd.getNbControl(pays);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			if (Integer.parseInt(nbCmdCon) == 0)
				stat = 0;
			else
				stat = (int) Integer.parseInt(nbCmd)
						/ Integer.parseInt(nbCmdCon);

			JOptionPane.showMessageDialog(null, "Il y a " + stat
					+ "% des commandes vérifiées en provenance de " + pays);
		}
	}
}