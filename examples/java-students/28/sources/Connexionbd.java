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
public class Connexionbd {
    
    public static Connection conn; // la connexion a la base
    static Statement st;


    
    // connection a la base
    public Connexionbd(String login, String motPasse) throws SQLException, ClassNotFoundException{
        // -------------------
        // Connexion a la base
        // --------------------
        
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + login,login, motPasse);
        st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    
    // fermeture de la connexion
    public static void close() throws SQLException{ 
        st.close();
        conn.close();
    }
    
}
