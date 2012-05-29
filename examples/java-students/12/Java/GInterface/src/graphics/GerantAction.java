package graphics;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class GerantAction extends AbstractAction {
	private static final long serialVersionUID = -8874263368273380218L;
	RequestDB bdd;
	ConnectedWindow connected;
	String texte;
	ResultSet rs;
	JTable data;
	MyTableModel donnees;
	int nbL;

	public GerantAction(RequestDB bdd, JTable data, MyTableModel donnees,
			String texte) {
		super(texte);
		this.texte = texte;
		this.bdd = bdd;
		this.data = data;
		this.donnees = donnees;
	}

	public void actionPerformed(ActionEvent e) {
		if (texte.equals("Clients")) {
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
			// System.out.println(nbL);
			String entetes[] = { "Num", "Adresse", "Ville", "CP", "Pays",
					"Tel", "Société" };
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

		} else if (texte.equals("Emballeurs")) {
			try {
				rs = bdd.getEmba();
				rs.last();
				// on récupère le numéro de la ligne
				nbL = rs.getRow();
				// on repace le curseur avant la première ligne
				rs.beforeFirst();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			// System.out.println(nbL);
			String entetes[] = { "Num", "Nom", "Prénom", "Err" };
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

		} else if (texte.equals("Transporteurs")) {
			try {
				rs = bdd.getTrans();
				rs.last();
				// on récupère le numéro de la ligne
				nbL = rs.getRow();
				// on repace le curseur avant la première ligne
				rs.beforeFirst();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			// System.out.println(nbL);
			String entetes[] = { "SCAC", "Nom" };
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

		} else if (texte.equals("Produits")) {
			try {
				rs = bdd.getProd();
				rs.last();
				// on récupère le numéro de la ligne
				nbL = rs.getRow();
				// on repace le curseur avant la première ligne
				rs.beforeFirst();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			// System.out.println(nbL);
			String entetes[] = { "Num", "Description", "Poids", "Dispo",
					"Prix", "Vendu" };
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

		} else if (texte.equals("Commandes")) {
			try {
				rs = bdd.getCmd();
				rs.last();
				// on récupère le numéro de la ligne
				nbL = rs.getRow();
				// on repace le curseur avant la première ligne
				rs.beforeFirst();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			// System.out.println(nbL);
			String entetes[] = { "Num", "Quantité", "Adresse liv", "Pays",
					"Date cmd", "Date liv", "Total", "Traité" };
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

		} else if (texte.equals("Colis")) {
			try {
				rs = bdd.getColis();
				rs.last();
				// on récupère le numéro de la ligne
				nbL = rs.getRow();
				// on repace le curseur avant la première ligne
				rs.beforeFirst();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			// System.out.println(nbL);
			String entetes[] = { "Num", "Date emballage", "Vérifié",
					"Qualificatif", "Expédié", };
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

		}
	}
}