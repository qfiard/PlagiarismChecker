import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class PrintColor {
	
	private Map<String,String> colors = new HashMap<String,String>();
	
	public PrintColor() {
		colors.put("rouge", "31m");
		colors.put("vert", "32m");
		colors.put("jaune","33m");
		colors.put("bleu","34m");
		colors.put("violet","35m");
		colors.put("turquoise","36m");
	}
	
	public void ecrire(String w,String c) {
		if ((c=colors.get(c)) == null) {
			ecrire(w);
			return;
		}
		else {
			System.out.print("\033["+ c + w+" \033[0m");
		}
	}
	
	public void ecrire(String w, String c, boolean b) { // vrai pour gras et faux pour souligné
		String s = "\033[1m"+w;
		ecrire(s,c);
	}
	
	public void ecrire(String w) {
		System.out.print(w);
	}
	
	public void nLine() {
		System.out.print("\n");
	}
	
	public void clear() {
		System.out.print("\033c");
	}
	
	public void ecrireSpace() {
		System.out.print(" ");
	}
	
	/*public void afficheRes(ResultSet res) throws SQLException {
		//On affiche les colonnes
		String w;
		ResultSetMetaData resMD = res.getMetaData();
		nLine();
		int taille = 40;
		int t=0;
		for (int i=1;i<=resMD.getColumnCount();i++) {
			ecrire(resMD.getColumnName(i).toUpperCase(),"bleu");
		}
		for (int j=0;j<t;j++) ecrireSpace();
		nLine();
		nLine();
		while(res.next()) {
			for (int i=1; i<=resMD.getColumnCount(); i++) {
				ecrire(res.getString(i),"rouge");
			}
			nLine();
		}
		res.close();
	}*/
	
	public void afficheRes(ResultSet res) throws SQLException {
		ResultSetMetaData resMD = res.getMetaData();
		int base = 30;
		int svg = resMD.getColumnCount();
		int svg_tot = 0;
		int[] t = new int[svg+1];
		String flush = "";
		LinkedList<String[]> afficher = new LinkedList<String[]>();
		Map<String,Integer> taille= new HashMap<String, Integer>(); 
		taille.put("id", 10);
		taille.put("actif", 6);
		taille.put("code_p", 10);
		taille.put("cout", 10);
		taille.put("date_livraison", 14);
		taille.put("date_emb", 10);
		taille.put("depenses", 11);
		taille.put("etat", 7);
		taille.put("genre", 5);
		taille.put("id_client", 9);
		taille.put("id_prod", 8);
		taille.put("id_colis", 8);
		taille.put("id_palette", 10);
		taille.put("id_employe", 10);
		taille.put("id_comm", 8);
		taille.put("id_emb", 8);
		taille.put("last_day", 10);
		taille.put("nb_colis", 8);
		taille.put("nb_jours", 8);
		taille.put("nb_refus", 8);
		taille.put("nom", 20);
		taille.put("nom_client", 11);
		taille.put("nom_prod", 16);
		taille.put("pays", 20);
		taille.put("poids_g", 10);
		taille.put("poids_total", 11);
		taille.put("prenom", 20);
		taille.put("prix_total", 10);
		taille.put("prix_unitaire", 10);
		taille.put("quantite", 8);
		taille.put("role", 12);
		taille.put("societe", 20);
		taille.put("stock", 10);
		taille.put("telephone", 20);
		taille.put("ville", 20);
		for (int i=1;i<=resMD.getColumnCount();i++) {
			if (taille.get(resMD.getColumnName(i)) == null){
				t[i] = base;
				svg_tot += base+1;
			}
			else {
				t[i] = taille.get(resMD.getColumnName(i));
				svg_tot += taille.get(resMD.getColumnName(i))+1;
			}
		}
		
		String[] t_str;
		int i,j,k,m;
		int l = 0;
		t_str = new String[svg+1];
		for (i=1;i<=svg;i++) {
			j = t[i]-resMD.getColumnName(i).length();
			t_str[i] = resMD.getColumnName(i).toUpperCase();
			for (k=0;k<j;k++) {
				t_str[i] += " ";
			}
		}
		afficher.add(t_str);
		while(l !=0 || res.next()) {
			m = 0;
			t_str = new String[svg+1];
			for (i=1;i<=svg;i++) {
				if (res.getString(i) != null)
					j = t[i]-(res.getString(i).length()-l*t[i]);
				else
					j = t[i];
				if (j>=t[i]) {
					t_str[i] = "";
					for (k=0;k<t[i];k++) {
						t_str[i] += " ";
					}
				}
				else if (j>=0 && l==0) {
					t_str[i] = res.getString(i);
					for (k=0;k<j;k++) {
						t_str[i] += " ";
					}
				} else if(j>=0 && l!=0) {
					if (res.getString(i) == null)
						t_str[i] = "";
					else
						t_str[i] = res.getString(i).substring(t[i]*l, res.getString(i).length());
					for (k=0;k<j;k++) {
						t_str[i] += " ";
					}
				} else {
					t_str[i] = res.getString(i).substring(t[i]*l, t[i]*(l+1));
					m++;
				}
			}
			if (m!=0)
				l++;
			else
				l=0;
			afficher.add(t_str);
		}
		t_str = afficher.pollFirst();
		for (i=1;i<svg;i++) {
			flush += t_str[i] + "|";
		}
		flush += t_str[i];
		ecrire(flush,"bleu");
		nLine();
		flush = "";
		for (i=1;i<=svg_tot-1;i++) {
			flush += "*";
		}
		ecrire(flush,"bleu");
		nLine();
		flush = "";
		while((t_str = afficher.pollFirst()) != null) {
			for (i=1;i<svg;i++) {
				flush += t_str[i] + "|";
			}
			flush += t_str[i];
			ecrire(flush, "rouge");
			nLine();
			flush = "";
		}
	}
}
