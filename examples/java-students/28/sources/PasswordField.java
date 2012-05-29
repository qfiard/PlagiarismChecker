/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

/**
 *
 * @author mounia
 */
import java.io.*;

public class PasswordField {

   /**
    *@param prompt The prompt to display to the user
    *@return The password as entered by the user
    */
   public static String readPassword (String prompt) {
       String password = "";
       try {
           password = new String(System.console().readPassword("%s", prompt));
       }
       catch (Exception ioe) {
       }
       return password;
   }
            
}

