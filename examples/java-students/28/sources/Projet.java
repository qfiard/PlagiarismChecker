

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projet;

import java.io.*;
import java.sql.*;
import java.util.Scanner;


/**
 *
 * @author mounia
 */
public class Projet {

    /**
     * @param args the command line arguments
     */
    
     
    static Scanner in = new Scanner(System.in);
    public static Connexionbd connecte;
    public static InterfaceConnexion c ;
    
     /**Imprime les recommandations d'usage a l'ecran.*/
    public static void usage() {
        System.out.println("Veuillez entrer votre nom identifiant pour Postgres.");
        System.out.println("usage : java Projet <nom d'utilisateur> <mot de passe>");
        System.exit(1);
    }
    
    public static void main(String[] args) {
       
       
        // ---------------------------
        // Verification des parametres
        // ---------------------------
        
        if (args.length == 0 || args.length > 2)
            usage();
        
        
        try 
            {
                // -------------------
                // Connexion a la base
                // --------------------
                if (args.length == 1 ){ 
                    connecte = new Connexionbd(args[0], "");
                    //affichage de l'interface de connexion
                    c = new InterfaceConnexion();
                    c.setTitle("Connexion");
                    c.setVisible(true);
                }
                else if(args.length == 2) {
                    connecte=new Connexionbd(args[0],args[1]);
                    //affichage de l'interface de connexion
                    c = new InterfaceConnexion();
                    c.setTitle("Connexion");
                    c.setVisible(true);
                }
                

                
            }
        catch(Exception e)
            {
                e.printStackTrace();
            }
  }
    
     /*// -------------------------
                // fermeture de la connexion
                // -------------------------
                connecte.close();
                in.close();
 
       */
}   
    
   
   
  