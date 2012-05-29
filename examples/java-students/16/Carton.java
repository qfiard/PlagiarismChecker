
public class Carton {
	String ID_carton;
	String ID_produit;
	int quantite_produit;
	String qualifiant;
	String ID_palette;
	
	public Carton(String ID_produit, int quantite_produit, String qualifiant,String ID_pal){
		Tables t = new Tables(null);
		this.ID_carton = t.randomID(10);
		this.ID_produit = ID_produit;
		this.quantite_produit = quantite_produit;
		this.qualifiant = qualifiant;
		this.ID_palette = ID_pal;
	}
	
	
}
