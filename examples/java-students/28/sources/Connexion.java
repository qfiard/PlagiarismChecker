/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.*;
import java.sql.*;

/**
 *
 * @author mounia
 */
public class Connexion {
    
    static PreparedStatement controleutil; 
    public static String res;
    public static ResultSet rs;
    public static String info ;
    
    public static void gestion(String l, String p) throws SQLException{
        
        String selectype="SELECT type, pays FROM utilisateur WHERE login=? AND mdp=?";
        controleutil=Projet.connecte.conn.prepareStatement(selectype);
        controleutil.setString(1,l);
        controleutil.setString(2,p);
        if(!controleutil.execute()){
            info="Identifiants incorrects";
            //Connexionbd.close();
        }
        else{
            rs=controleutil.getResultSet();
            rs.next();
            int type = rs.getInt("type");
            switch(type){
                case 10 : InterfaceEmballeur e=new InterfaceEmballeur();
                    Projet.c.setVisible(false);
                    info = rs.getString("num");
                    e.setTitle("Emballeur numero : " +info);
                    e.setVisible(true);
                    break;                    
                case 20 : InterfaceClient c = new InterfaceClient();
                    Projet.c.setVisible(false);
                    c.setVisible(true);
                    c.setTitle("Interface Client");
                    info= rs.getString("num");                    
                    break;
                case 40 : info=l;
                    InterfaceTransporteur t=new InterfaceTransporteur();
                    Projet.c.setVisible(false);
                    System.out.println("JE SUIS LA");
                    t.setTitle("Interface Transporteur");
                    t.setVisible(true);
                    break;
                case 50 : InterfaceDouane d= new InterfaceDouane();
                    Projet.c.setVisible(false);
                    info = rs.getString("pays");
                    d.setTitle("Douane de "+info);
                    d.setVisible(true);                     
                    
                    //System.out.println(info);
                    break;
                case 60 : InterfaceGerant g=new InterfaceGerant();
                    Projet.c.setVisible(false);
                    g.setVisible(true);
                    g.setTitle("Interface Gérant");
                    info= rs.getString("num");                    
                    break;                 
                    
            }      
        }        
    }
}
