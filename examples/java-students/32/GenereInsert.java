import java.sql.*;
import java.math.*;
import java.lang.*;
import java.util.*;
public class GenereInsert {

    // Permet de remplacer les lettres par des chiffres pour pouvoir les utilisees dans le switch de genereTypes
    public static int toNbr (String lettre) {
	if ( lettre.equals("m"))
	    return (1);
	else if (lettre.equals("n"))
	    return (2);
	else if (lettre.equals("i"))
	    return (3);
	else if (lettre.equals("c"))
	    return (4);
	else if (lettre.equals("p"))
	    return (5);
	else if (lettre.equals("b"))
	    return (6);
	else if (lettre.equals("d"))
	    return (7);
	else if (lettre.equals("s"))
	    return (8);
	else if (lettre.equals("x"))
	    return (9);
	else
	    return(0);
    }

    // Utilise le fichier GenereType.java pour generer les types sql demendes dans la requete 'req'
    public static String genereTypes (String req) {
	String rep = "";
	String [] dispatch = req.split(",");
	int nbr = dispatch.length;
	String temp = "";
	for (int i=0; i<nbr; i++) {
	    int t = toNbr(dispatch[i].substring(0,1));
	    switch ( t ) {
	    case 1: 
		temp = GenereType.genereMot(Integer.parseInt(dispatch[i].substring(1,dispatch[i].length())));
		break;
	    case 2:
		temp = ""+GenereType.genereNbr();
		break;
	    case 3:
		temp = GenereType.genereId(Integer.parseInt(dispatch[i].substring(1,dispatch[i].length())));
		break;
	    case 4:
		temp = GenereType.genereEtatColi();
		break;
	    case 5:
		temp = GenereType.genereEtatProduit();
		break;
	    case 6:
		temp = "false";
		break;
	    case 7:
		temp = GenereType.genereDate();
		break;
	    case 8:
		temp = ""+GenereType.genereStock();
		break;
	    case 9:
		temp = dispatch[i].substring(1,dispatch[i].length());
		break;
	    default:
		temp = "probleme de type: "+t;
		break;
	    }
	    rep += ","+temp;
	}
	rep = rep.substring(1,rep.length());
	return (rep);
    }

    // Genere la requete sql d'insertion (insert to) proprement dite en fonction de la table 'table'
    public static String genereInsert (String table,String req) {
	String insert = "insert into "+table+"("+genereTypes (req)+");";
	return (insert);
    }

    public static String genereConteneur () {
	return genereInsert ("conteneur","i8");
    }

    public static String genereTransporteur () {
	return genereInsert ("transporeur","i8,m30,m20,m15,m20,m20");
    }
    
    public static String genereEmballeur () {
	return genereInsert ("emballeur","i8,m30,m20,m15,m20,m20");
    }

    public static String genereGerant () {
	return genereInsert ("gerant","m30,m20,m20,m20");
    }

    public static String genereDouane () {
	return genereInsert ("douane","m20,m20,m20");
    }

    public static String genereClient () {
	return genereInsert ("client","i8,m20,m20,m20,m20");
    }

    public static String generePalette () {
	return genereInsert ("palette","i8,i8");
    }

    public static String genereColi () {
	return genereInsert ("colis","i8,i8,m20,i8,i8,m20,d,d,m20,c,b");
    }

    public static String genereProduit (String id) {
	return genereInsert ("produit","i8,m20,n,p,s,x"+id);
    }

    public static void main (String [] args) {
	
    }

    
}