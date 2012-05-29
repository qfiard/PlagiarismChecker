import java.util.*;
import java.io.*;

class Test{
public static String generateString(Random rng, String characters)
{	//int length = (int)(Math.random()*10)+1;
	int length = 1 + rng.nextInt(10-1);
	char[] text = new char[length];
    for (int i = 0; i < length; i++)
    {
        text[i] = characters.charAt(rng.nextInt(characters.length()));
    }
    return new String(text);
}
public static String num_transporteur(){
	Random r = new Random();
	int n = 10;
	ArrayList<String> listeTransporteur = new ArrayList<String>();
	listeTransporteur.add("11111");listeTransporteur.add("22222");listeTransporteur.add("33333");listeTransporteur.add("44444");
	listeTransporteur.add("55555");listeTransporteur.add("66666");listeTransporteur.add("77777");listeTransporteur.add("88888");
	listeTransporteur.add("99999");listeTransporteur.add("090909");
	int indice = r.nextInt(n);
	String date = listeTransporteur.get(indice);
	return date;
	
}
public static String code_conteneur(){
	Random r = new Random();
	int n = 10;
	ArrayList<String> listeConteneur = new ArrayList<String>();
	listeConteneur.add("1234");listeConteneur.add("2345");listeConteneur.add("3456");listeConteneur.add("4567");
	listeConteneur.add("5678");listeConteneur.add("6789");listeConteneur.add("7890");listeConteneur.add("8901");
	listeConteneur.add("9012");listeConteneur.add("0123");
	int indice = r.nextInt(n);
	String date = listeConteneur.get(indice);
	return date;
}
public static String date(){
	Random r = new Random();
	int n = 3;
	ArrayList<String> listeDate = new ArrayList<String>();
	listeDate.add("2010-05-02");
	listeDate.add("2012-03-05");
	listeDate.add("2011-02-08");
	int indice = r.nextInt(n);
	String date = listeDate.get(indice);
	return date;
}
public static String num_client(){
	Random r = new Random();
	int n = 10;
	ArrayList<String> listeClient = new ArrayList<String>();
	listeClient.add("102030");listeClient.add("112131");listeClient.add("122232");listeClient.add("132333");
	listeClient.add("142434");listeClient.add("152535");listeClient.add("162636");listeClient.add("172737");
	listeClient.add("182838");listeClient.add("192939");
	int indice = r.nextInt(n);
	String num_client = listeClient.get(indice);
	return num_client;
}

public static String caracteristique(){
	Random r = new Random();
	int n = 3;
	ArrayList<String> listeCaracteristique = new ArrayList<String>();
	listeCaracteristique.add("N");
	listeCaracteristique.add("D");
	listeCaracteristique.add("F");
	int indice = r.nextInt(n);
	String carac = listeCaracteristique.get(indice);
	return carac;
}
public static String disponible(){
	Random r = new Random();
	int n = 2;
	ArrayList<String> listeDisponible = new ArrayList<String>();
	listeDisponible.add("DISPONIBLE");
	listeDisponible.add("EPUISE");
	int indice = r.nextInt(n);
	String dispo = listeDisponible.get(indice);
	return dispo;
}
public static String [] generateSQL(String dict,int nbrChaine,String table){
	Random r = new Random();
	
	String chaine = "";
	String [] Tabchaine = new String[nbrChaine];
	for(int i = 0; i < nbrChaine; i++){
		
		
		if (table == "produit"){
		chaine = generateString(r,"0123456789")+","+generateString(r,dict)+","+generateString(r,"0123456789")
				+","+caracteristique()+","+generateString(r,"0123456789")+","+disponible()+","+generateString(r,"0123456789")
				+","+generateString(r,"0123456789");
		Tabchaine[i] = chaine;
		}
		else if (table == "gerant"){
			chaine ="45566"+","+generateString(r,dict)+","+generateString(r,dict)+","+
					generateString(r,dict)+","+generateString(r,dict);
			Tabchaine[i] = chaine;
		}
		else if (table == "employe"){
			chaine = generateString(r,"0123456789")+","+generateString(r,dict)+","+generateString(r,dict)
					+","+generateString(r,dict)+","+generateString(r,"abcdefghijklmnopqrstuvwxyz0123456789")
					+","+"45566";
			Tabchaine[i] = chaine;
		}
		else if (table == "client"){
			chaine = generateString(r,"0123456789")+","+generateString(r,dict)+","+generateString(r,dict)
					+","+generateString(r,"0123456789")+","+generateString(r,"ABCDEFGHIJKLMNOPQRSTUVWXYZ")
					+","+generateString(r,"0123456789")+","+generateString(r,"abcdefghijklmnopqrstuvwxyz")
					+","+generateString(r,"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
			Tabchaine[i] = chaine;
		}
		else if (table == "commande"){
			chaine = generateString(r,"0123456789")+","+"45566"
					+","+generateString(r,"0123456789")+","+generateString(r,"0123456789")+","+generateString(r,"0123456789")+","+date()
					+","+date()+","+date();
			Tabchaine[i] = chaine;
		}
		else if (table == "transporteur"){
			chaine =generateString(r,"0123456789")+","+generateString(r,dict)+","+generateString(r,dict)
					+","+generateString(r,dict)+","+generateString(r,"abcdefghijklmnopqrstuvwxyz0123456789")
					+","+generateString(r,"0123456789")+","+generateString(r,"0123456789")+","+generateString(r,"0123456789");
			Tabchaine[i] = chaine;
		}
		else if (table == "emballeur"){
			chaine =generateString(r,"0123456789")+","+generateString(r,dict)+","+generateString(r,dict)
					+","+generateString(r,dict)+","+generateString(r,"abcdefghijklmnopqrstuvwxyz0123456789")
					+","+generateString(r,"0123456789")+","+generateString(r,"0123456789")+","+generateString(r,"0123456789");
			Tabchaine[i] = chaine;
		}
		else if (table == "emballer"){
			chaine =generateString(r,dict)+","+generateString(r,"0123456789")+","+generateString(r,"0123456789");
			Tabchaine[i] = chaine;
		}
		else if (table == "colis"){
			chaine = generateString(r,"0123456789")+","+date()+","+generateString(r,"0123456789")
					+","+generateString(r,"0123456789")+","+date()
					+","+generateString(r,"0123456789")+","+generateString(r,"0123456789")+","+generateString(r,"0123456789")
					+","+"NONEXP";
			Tabchaine[i] = chaine;
		}
		else if (table == "palette"){
			chaine =generateString(r,"0123456789")+","+date()+","+generateString(r,"0123456789")
					+","+generateString(r,"0123456789")+","+date()
					+","+generateString(r,"0123456789")+","+generateString(r,"0123456789")+","+generateString(r,"0123456789")
					+","+"NONEXP"+","+generateString(r,"0123456789")+","+generateString(r,"0123456789");
			Tabchaine[i] = chaine;
		}
		else if (table == "conteneur"){
			chaine =generateString(r,"0123456789")+","+generateString(r,"0123456789")+","+num_transporteur()+","+generateString(r,"0123456789")+
					","+generateString(r,"0123456789")+","+generateString(r,dict);
			Tabchaine[i] = chaine;
		}
		else if (table == "douane"){
			chaine =code_conteneur()+","+generateString(r,dict)+
					","+generateString(r,"0123456789")+","+generateString(r,"0123456789")
					+","+generateString(r,dict)+","+generateString(r,dict);
			Tabchaine[i] = chaine;
		}
		
	}
	return Tabchaine;
}

public static void affichage(String [] tab){
	for(String valeur : tab){
		System.out.println(valeur);
	}
}
public static void fichier(String path){
	try{
		FileOutputStream fos = new FileOutputStream(path);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
	}
	catch(IOException e){
		e.printStackTrace();
	}
}
public static void main(String [] args){
	
	//Commande
	String [] commande = generateSQL("abcdefghijklmnopqrstuvwxyz",250,"commande");
	fichier("commande.txt");
	affichage(commande);
	
	
	
	
	
	}
}
