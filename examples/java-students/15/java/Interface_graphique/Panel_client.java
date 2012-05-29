import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Panel_client  extends Panel_princ implements ActionListener{
    String login;
    //objet swing de notre Panel connexion
    JButton deconnexion;
    JButton valide_choix;
    JComboBox choix;

    public Panel_client(inter_princ jfr,String log){
        this.frame = jfr;
        this.login = log;
        this.contenu = this.buildJP();
    }

    public JPanel buildJP(){
        JPanel panel = new JPanel();
        
        //construit le panel des info
        JPanel info = buildJP_info();
        info.setBounds(10,10,100,200);
        panel.add(info);

        //construit le panel des choix d'action
        JPanel choix_action = buildJP_choixA();
        //choix_action.setBounds(10,400,100,200);
        panel.add(choix_action);

        //construit le panel action
        

        return panel;
    }

    public JPanel buildJP_info(){
        HashMap<String,String> info_client = this.frame.conn.infosPersonne(this.login);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,1));
        
        JLabel type = new JLabel("CLIENT");
        panel.add(type);
        JLabel login = new JLabel("Login : "+this.login);
        panel.add(login);
        JLabel nom = new JLabel("Nom :"+info_client.get("nom"));
        panel.add(nom);

        this.deconnexion = new JButton("deconnexion");
        deconnexion.addActionListener(this);
        panel.add(deconnexion);

        return panel;
    }

    public JPanel buildJP_choixA(){
        JPanel panel = new JPanel();
        
        String[] choix_action = new String[5];
        choix_action[0] = "Lister les employés/client";
        choix_action[1] = "Changer le prix d'un produit";
        choix_action[2] = "Voir les Produits les pLus vendus";
        choix_action[3] = "Voir les clients les plus depensiés";
        choix_action[4] = "Voir les employés les moins actif";
        
        choix = new JComboBox(choix_action);
        panel.add(choix);
        
        valide_choix = new JButton("valider");
        valide_choix.addActionListener(this);
        panel.add(valide_choix);

        return panel;
    }

    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == deconnexion){
            this.frame.connexion_co(true);
        }
    }
}
