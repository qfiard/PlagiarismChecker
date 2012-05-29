import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;


public abstract class Users {
	
	protected ConnectionProjet conn;
	protected String login;
	protected String mdp;
	protected int id;
	protected PrintColor out = new PrintColor();
	
	public abstract boolean gestion() throws SQLException;
	public abstract int listeChoix();
	
	public Users(String log,String mdp,ConnectionProjet connexion) {
		this.conn = connexion;
		this.login = log;
		this.mdp = mdp;
		try {
			this.id = connexion.recupId(login);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public int lireEntier(String w,String c) {
		int tmp = -1;
		Scanner sc = new Scanner(System.in);
		while(true) {
			try { 
				out.ecrire(w,c);
				out.nLine();
				tmp = Integer.parseInt(sc.nextLine());
				if (tmp < 0) continue;
				break;
			}
			catch(Exception e) {
				out.ecrire("Erreur: "+e,"violet");
				continue;
			}
		}
		return tmp;
	}
	
	public int lireEntier() {
		int tmp = -1;
		while(true) {
			try { 
				tmp = Integer.parseInt(new Scanner(System.in).nextLine());
				if (tmp < 0) continue;
				break;
			}
			catch(Exception e) {
				out.ecrire("Erreur: "+e,"violet");
				continue;
			}
		}
		return tmp;
	}
	
	public double lireDouble() {
		double tmp = -1;
		while(true) {
			try { 
				tmp = Double.parseDouble(new Scanner(System.in).nextLine());
				if (tmp < 0) continue;
				break;
			}
			catch(Exception e) {
				out.ecrire("Erreur: "+e,"violet");
				continue;
			}
		}
		return tmp;
	}
	
	public double lireDouble(String w,String c) {
		double tmp = -1;
		while(true) {
			out.ecrire(w,c);
			out.nLine();
			try { 
				tmp = Double.parseDouble(new Scanner(System.in).nextLine());
				if (tmp < 0) continue;
				break;
			}
			catch(Exception e) {
				out.ecrire("Erreur: "+e,"violet");
				continue;
			}
		}
		return tmp;
	}
	
	public static void toucheContinue() {
		System.out.println("Appyuez sur Entree pour continuer...");
		System.console().readLine();
	}
	
	public boolean fin() {
		toucheContinue();
		out.clear();
		out.ecrire("Voulez vous continuer ou bien quitter ? (C/Q) ?","rouge");
		out.nLine();
		if (System.console().readLine().equals("C")) return true;
		else return false;
	}
	
	public String lireDate() {
		String w = "";
		
		while (!Pattern.matches("^[0-9]{4}-[0-1]{1}[0-9]{1}-[0-3]{1}[0-9]{1}$", w)) {
			System.out.println("Entrez une date. Format: [YYYY-MM-DD]");
			w = System.console().readLine();
		}
		return w;
	}
	
}
