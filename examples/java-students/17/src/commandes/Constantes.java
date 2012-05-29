package donnees;

import java.sql.Statement;

public class Constantes {
	
	public final static String base_client = "clients";
	public final static String base_emballeur = "emballeurs";
	public final static String base_identifiant = "identifiants";
	
	public final static String base_commande = "commandes";
	public final static String base_contenuCommande = "contenuCommande";
	
	public final static String base_conteneur = "conteneurs";
	public final static String base_palette = "palettes";
	
	public final static String base_colis = "colis";
	public final static String base_contenuColis = "contenuColis";
	public final static String base_emballage = "emballage";
	
	public final static String base_produit = "produits";		
	public final static String base_douane = "douanes";
	public final static String base_declaration = "declarations";		
	public final static String base_transporteur = "transporteurs";
	public final static String base_gerant = "gerant";
	
	public static Statement st;
	
	public static String fichierData = "src/data.csv";
	public static String fichierCreation = "src/Creation.sql";
	public static String fichierSuppression = "src/Suppression.sql";
	
}
