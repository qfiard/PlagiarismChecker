package Share;

import java.util.HashMap;
import java.util.Map;

public class Tools {
	
	/**
	 * Permet de savoir si les entrées sont identiques
	 * @param input1 mot de passe
	 * @param input2 confirmation mot de passe
	 * @return <code>true</code> si identique
	 */
	public static boolean isSamePassword(char[] input1, char[] input2) {
		if (input1.length != input2.length) {
			return false;
		}
		for (int i = 0; i < input1.length; ++i) {
			if (input1[i] != input2[i]) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Retourne la moyenne d'objet par date d'un String de la forme "111+22082012%222+23082012"
	 * @param s La chaine de caractere au format souhaité
	 * @return La moyenne sous forme de double
	 */
	public double getAverageList(String s) {
		double a=0, div=0;
		String [] tab= s.split("%");
		String []temp= new String [1];
		String buff="";
		for (int i=0; i<tab.length; i++) {
			if (!(buff.contains(tab[i].split("+")[1]))) {
				div++;
				buff+=tab[i]+" ";
				temp[0]=tab[i];
				for (int j=i; j<tab.length; j++) {
					if (tab[j].split("+")[1]==temp[0])
						a++;
				}
			}
		}
		return a/div;
		
	}
	
	/**
	 * Permet d'obtenir une chaine de caractère à partir d'un tableau de caractères
	 * @param tab le tableau de caractères
	 * @return la concaténation du tableau de caractères
	 */
	public static String string_of_tabChar(char[] tab) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < tab.length; ++i) {
			buffer.append(tab[i]);
		}
		return buffer.toString();
	}
	
	
	/**
	 * Verifie si le tableau tab contient la chaine de caractere data
	 * @param tab Le tableau a verifier
	 * @param data La chaine a comparer
	 * @return true si le tableau contient data, false sinon
	 */
	public static boolean contains(String[] tab, String data) {
		for (int i=0; i<tab.length;i++) {
			if (tab[i].equals(data))
				return true;
		}
		return false;
	}
	
	/**
	 * Modifie une ligne
	 * @param str de type idProduit+JJMMAAAA%idProduit+JJMMAAAA%..
	 * @param id_product l'identifiant de la commande à modifier
	 * @param newDate La nouvelle date 
	 * @return la ligne modifié 
	 */
	public static String changeDate(String str, int id_product, String newDate) {
		String[] buffer = str.split("%");
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < buffer.length; ++i) {
			String[] idAndDate = buffer[i].split("+");
			if (Integer.parseInt(idAndDate[0]) == id_product)
				idAndDate[1] = newDate;
			res.append(idAndDate[0] + idAndDate[1]);
			res.append("%");
		}
		return res.toString();
	}
	
	/**
	 * On cherche le client dépensie
	 * @param data contien les lign
	 * @return tableau de CoupleInt avec l'id et le montant 
	 */
	public static CoupleInt[] bestCustomer(HashMap<Integer, String> data) {
		CoupleInt[] res = new CoupleInt[5];
		for (int i = 0; i < res.length; ++i) {
			res[i] = new CoupleInt(-1, -1);
		}
		for (Map.Entry<Integer, String> entry : data.entrySet()) {
			int total = totalCustomer(entry.getValue().split("%"));
			if (total > res[4].getB()) {
				res[4].setA(entry.getKey());
				res[4].setB(total);
			}
			sort(res);
		}
		return null;
		
	}
	
	private static int totalCustomer(String[] t) {
		int res = 0;
		
		
		return res;
	}
	
	private static void sort(CoupleInt[] tab) {
		int[] res = new int[tab.length];
		for (int i = 0; i < tab.length; ++i) 
			res[i] = tab[i].getB();
		int[] t = res;
		int i = 0;
		while (!isSort(t)) {
			int max = max(t);
			res[i++] = max;
			t = withoutMax(t, max);
		}
	}
	
	private static int max(int[] t) {
		int max = -1;
		for (int i = 0; i < t.length; ++i) {
			if (max < t[i]) {
				max = t[i];
			}
		}
		return max;
	}
	
	/**
	 * Permet de retourner un tableau sans le maximum
	 * @param t le tableau de base
	 * @param max la valeur qu'on ne souhaite plus
	 * @return un tableau sans le max
	 */
	private static int[] withoutMax(int[] t, int max) {
		int[] res = new int[t.length - 1];
		int i = 0;
		for (int j = 0; j < t.length; ++j) 
			if (t[i] != max)
				res[j++] = t[i];
		return res;
	}
	
	private static boolean isSort(int[] tab) {
		for (int i = 0; i < tab.length - 1; ++i) 
			if (tab[i] > tab[i+1])
				return false;
		return true;
	}

}