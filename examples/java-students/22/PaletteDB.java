import java.sql.*;

public class PaletteDB
{

    /* listPackage(): liste les colis d'une palette*/
    public static void listPackage(QueryDB q, String idPalette)
    {
	String rq="SELECT id_colis FROM Palette WHERE id_palette=" + idPalette +";";
	q.updateDB(rq);
    }


    /* addPalette(): ajoute une palette dans la base */
    public static void addPalette(QueryDB q, String idPalette)
    {
	String rq = "INSERT INTO palette (id_palette) VALUES ("+ idPalette +");";
	q.updateDB(rq);
    }

    /* listPalette() : fonction qui liste toutes les palettes d'un conteneur */ 
    public static void listPalette(QueryDB q, String id_conteneur)
    {
	String rq = "select * from palette natural join conteneur where "+
	    "id_conteneur="+id_conteneur+";";
	q.requestDB(rq);
    }
}