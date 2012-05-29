package Share;

import java.sql.SQLException;
import java.io.*;


import db.Query;
public class ReadFile {
	private Query q ;
	boolean debug = false;
	int c;
	private StringBuffer boss;
	private StringBuffer customer;
	private StringBuffer customs;
	private StringBuffer packer;
	private StringBuffer transporter;
	private StringBuffer products;

	/**
	 * Permet d'initialiser les attributs nécessaire à la lecture et l'enregistrement du fichier
	 * Lancer ensuite la fonction readAll(String name)
	 * Puis saveAll();
	 */
	public ReadFile() {
		boss = new StringBuffer();
		customer =  new StringBuffer();
		customs = new StringBuffer();
		packer  = new StringBuffer();
		transporter  = new StringBuffer();
		products  = new StringBuffer();
	}
	
	public void readAll(String nameFile) {
		String[] line;
		BufferedReader allLines;
		if (debug) 
			System.out.println("\n************************\n\t\tdebug\n");
		
		try {
			q = new Query("evrard", "BEDE2011");
			allLines = new BufferedReader(new FileReader(nameFile));
			c = 1;
			while ((allLines != null)) {
				String str = allLines.readLine();
				if (str == null) 
					break;
				line = str.split("\\|");
				switch(Integer.parseInt(line[0])) {
				
				case 60 : 
					// gérant 
					if (line.length != 5) 
						new FileException(0, line.length == 1?"":line[1], c);
					boss.append("('" + line[2] + "', '" + line[1] + "', '" + line[3] + "', '" + line[4] + "', '" + 0 + "', null, null, '" + line[3] + "'),");
					if (debug) 
						System.out.println("gérant : " + line[3]);
					break;
				case 30 : 
					//produit
					if (line.length != 10) 
						new FileException(0, line.length == 1?"":line[1], c);
					products.append("('" + line[1] + "', '" + line[2] + "', " + Integer.parseInt(line[3]) +", " + Double.parseDouble(line[6]) + ", '"+line[5]+"', " + Double.parseDouble(line[8]) + ", " + Integer.parseInt(line[9]) + "),");
					if (debug) 
						System.out.println("('" + line[1] + "', '" + line[2] + "', " + Integer.parseInt(line[3]) +", " + Integer.parseInt(line[4])+ "' " + Double.parseDouble(line[6]) + ", " + Double.parseDouble(line[8]) + ", " + Integer.parseInt(line[9]) + "),");
					break;
				case 20 : 
					// Client
					if (line.length != 10) 
						new FileException(1, line.length == 1?"":line[1], c);
					String adress = line[2] + " " + line[4] + " " + line[5] + " " + line[6]; 
					customer.append("('" + line[2] + "', '" + line[3] + "', '" + line[2] + "','"+line[9]+"', " + 1 + ", '" + adress + "', '" + line[8] + "', '" + line[1] +"'),");
					if (debug) 
						System.out.println("('" + line[2] + "', '" + line[3] + "', '" + line[9] + "', '1', '" + adress + "', '" + line[8] + "', '" + line[1] +"'),");
					break;
				case 10 : 
					// emballeur, son nom comme login
					if (line.length != 6) 
						new FileException(2, line.length == 1?"":line[1], c);
					packer.append("('" + line[2] + "', '" + line[3] + "', '" + line[2] + "', '" + line[5] + "','2%"+line[4]+"', NULL, NULL, '" + line[2] + "%" + line[1] +"'),");
					break;
				case 40 :
					// transporteur numero ESWL
					if (line.length != 4)
						new FileException(4, line.length == 1?"":line[1], c);
					transporter.append("('" + line[2] + "', NULL , '" + line[2] + "', '" + line[3] + "', '4', NULL, '" + line[1] +"','"+line[1]+"'),");
					break;
				case 50 : 
					//douane
					if (line.length != 5)
						new FileException(4, line.length == 1?"":line[1], c);
					customs.append("('"+line[1]+"', NULL , '" + line[3] + "', '" + line[4] + "', '3','"+line[1]+"', NULL,'"+line[2]+"'),");
					break;
				default : new FileException(-1, "", c);
				
				}
				++c;
			}
			saveAll();
		} catch (FileNotFoundException e) {
			System.out.println("Lecture du fichier impossible");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Fichier corrompu");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.out.println("Problème java");
			System.exit(1);
		} /*catch (SQLException e) {
			System.out.println("Problème avec la base de donnée");
			System.exit(1);
		} */ catch (NumberFormatException e) {
			new FileException(-1, "Problème avec la ligne " + c + " du fichier\n", c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	
	public void saveAll() throws SQLException {
		q.addUser(boss.toString().substring(0, boss.toString().length() - 1) + ";");
		q.addUser(customer.toString().substring(0, customer.toString().length() - 1) + ";");
		q.addUser(customs.toString().substring(0, customs.toString().length() - 1) + ";");
		q.addUser(packer.toString().substring(0, packer.toString().length() - 1) + ";");
		q.addUser(transporter.toString().substring(0, transporter.toString().length() - 1) + ";");
		//q.addProduct(products.toString().substring(0, products.toString().length() - 1) + ";");
	}
	
}