import java.sql.*;
import java.math.*;
import java.lang.*;
public class GenereType {

    //genere une lettre aleatoire de l'alphabet
    public static char genereLettre ( ) {
	double d = 25*Math.random();
	int nbr = (int) d;
	char rep;
	switch (nbr) {
	case 0 : rep = 'z'; break;
	case 1: rep = 'a'; break;
	case 2: rep = 'b'; break;
	case 3: rep = 'c'; break;
	case 4: rep = 'd'; break;
	case 5: rep = 'e'; break;
	case 6: rep = 'f'; break;
	case 7: rep = 'g'; break;
	case 8: rep = 'h'; break;
	case 9: rep = 'i'; break;
	case 10: rep = 'j'; break;
	case 11: rep = 'k'; break;
	case 12: rep = 'l'; break;
	case 13: rep = 'm'; break;
	case 14: rep = 'n'; break;
	case 15: rep = 'o'; break;
	case 16: rep = 'p'; break;
	case 17: rep = 'q'; break;
	case 18: rep = 'r'; break;
	case 19: rep = 's'; break;
	case 20: rep = 't'; break;
	case 21: rep = 'u'; break;
	case 22: rep = 'v'; break;
	case 23: rep = 'w'; break;
	case 24: rep = 'x'; break;
	case 25: rep = 'y'; break;
	default: rep = '&';break;
	}
	return (rep);
    }

    //genere un mot d'un nombre de lettres specifie
    public static String genereMot ( int nbr ) {
	String mot = "";
	for ( int i = 0; i<nbr; i++) {
	    mot += genereLettre ();
	}
	return ("'"+mot+"'");
    }
    
    //genere un nombre entre 0 et 9
    public static int genereNbr (){
	double d = 10*Math.random();
	int x = (int) d;
	return ( x ); 
    }
    
    //genere un identifiant d'une longueur specifiee
    public static String genereId (int x) {
	long dec = 1;
	String rep="";
	for (int i =0; i<x; i++) {
	    rep += genereNbr();
	}
	return (rep);
    }
		
    public static String genereEtatColi () {
	double d  = 3*Math.random();
	int res = (int) d;
	String rep = ""; 
	switch (res) {
	case 0: rep = "'100 % expedie'"; break;
	case 1: rep = "'partiellement expedie'"; break;
	case 2: rep = "'non expedie'"; break;
	default: rep = "ya un sushi"; break;
	}
	return (rep);
    }

    public static String genereEtatProduit () {
	double d  = 2*Math.random();
	int res = (int) d;
	String rep = ""; 
	switch (res) {
	case 0: rep = "'fragile'"; break;
	case 1: rep = "'dangereux'"; break;
	default: rep = "ya un sushi"; break;
	}
	return (rep);
    }

    public static int genereStock () {
	double d  = 2*Math.random();
	int res = (int) d;
	return (res);
    }
	    
    //genere une date aleatoire dans les 50 dernieres années
    public static String genereDate () {
	double d = 29*Math.random();
	int jour = 1 + ((int) d);
	d = 11*Math.random();
	int mois = 1 + ((int) d);
	d = 50*Math.random(); 
	int annee = 2012 - ((int) d);
	String rep = Integer.toString(jour)+"-"+Integer.toString(mois)+"-"+Integer.toString(annee);
	return rep;
    }
}