import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class inter_princ extends JFrame{

    ConnexionBDD conn;

    Panel_princ contenu;

    public inter_princ (ConnexionBDD co){
        super();

        this.contenu = new Panel_co(this);
        //crée notre objet requete
        this.conn = co;

        setSize(800,500); // taille de la fenêtre
        setLocationRelativeTo(null); // centre la fenêtre
        setResizable(false); // interdit de redimentionner la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fermeture si clic sur croix
        Color color = new Color(0,153,102);
        setBackground(color);
        setContentPane(contenu.getContenu()); // insere le contenue de la fenêtre
        setVisible(true); //rend la fenêtre visible
    }

    public void connexion_co(boolean err){
        Container cp = this.getContentPane();
        cp.removeAll();
        contenu = new Panel_co(this,err);
        cp.add(contenu.getContenu());
        this.setContentPane(cp);
    }

    public void connexion_client(String login){
        Container cp = this.getContentPane();
        cp.removeAll();
        contenu = new Panel_client(this,login);
        cp.add(contenu.getContenu());
        this.setContentPane(cp);
    }

    public void connexion_transporteur(String login){
        Container cp = this.getContentPane();
        cp.removeAll();
        contenu = new Panel_tran(this);
        cp.add(contenu.getContenu());
        this.setContentPane(cp);
    }

    public void connexion_emballeur(String login){
        Container cp = this.getContentPane();
        cp.removeAll();
        contenu = new Panel_emb(this);
        cp.add(contenu.getContenu());
        this.setContentPane(cp);
    }

    public void connexion_douane(String login){
        Container cp = this.getContentPane();
        cp.removeAll();
        contenu = new Panel_douane(this,login);
        cp.add(contenu.getContenu());
        this.setContentPane(cp);
    }

    public void connexion_gerant(String login){
        Container cp = this.getContentPane();
        cp.removeAll();
        contenu = new Panel_ger(this,login);
        cp.add(contenu.getContenu());
        this.setContentPane(cp);
    }

    public static void main (String [] args) throws SQLException,ClassNotFoundException {
        //prend en argument le nom bdd et utilisateur et mdp_bdd
        if(args.length == 3){
            String nom_bdd = args[0];
            String user = args[1];
            String mdp_bdd = args[2];
            //crée notre interface et la lance
            ConnexionBDD co = new ConnexionBDD(nom_bdd, user, mdp_bdd);
            inter_princ fenetre = new inter_princ(co);
        }
    }
}
