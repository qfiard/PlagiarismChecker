import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Panel_co  extends Panel_princ implements ActionListener{
    boolean connection_F = true;

    //objet swing de notre Panel connexion
    JTextField login_JT;
    JTextField mdp_JT;
    JButton bouton ;

    public Panel_co(inter_princ jfr){
        this.contenu = this.buildJP();
        this.frame = jfr;
    }

    public Panel_co(inter_princ jfr,boolean erreur){
        this.contenu = this.buildJP();
        this.frame = jfr;
        this.connection_F = erreur;
    }

    public JPanel buildJP(){
        JPanel panel_co = new JPanel();
        panel_co.setLayout(new GridLayout(3,1));

        JPanel panel_NO = new JPanel();
        panel_NO.setLayout(new FlowLayout());
        JLabel user = new JLabel("Utilisateur :");
        login_JT = new JTextField();
        login_JT.setColumns(10);
        panel_NO.add(user);
        panel_NO.add(login_JT);


        JPanel panel_CEN = new JPanel();
        panel_CEN.setLayout(new FlowLayout());
        JLabel mdp_text = new JLabel("Mot de Passe :");
        mdp_JT = new JTextField();
        mdp_JT.setColumns(10);
        panel_CEN.add(mdp_text);
        panel_CEN.add(mdp_JT);

        JPanel panel_SU = new JPanel();
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        if(!connection_F){
            JLabel label = new JLabel("Login ou mot de passe incorrect");
            label.setForeground(Color.red);
            p.add(label, BorderLayout.NORTH);
        }
        bouton = new JButton("connexion");
        bouton.addActionListener(this);
        p.add(bouton, BorderLayout.SOUTH);
        panel_SU.add(p);


        panel_co.add(panel_NO);
        panel_co.add(panel_CEN);
        panel_co.add(panel_SU);

        JPanel panel = new JPanel();
        //panel_co.setBounds(300,150,200,150);
        panel.add(panel_co);

        Color color = new Color(0,153,102);
        panel.setBackground(color);
        return panel;
    }

    public void actionPerformed(ActionEvent e){
        String login = login_JT.getText(); 
        String mdp = mdp_JT.getText();
        String type = this.frame.conn.connecteUtilisateur(login,mdp);
        if(type != null){
                if (type.equals("client")) {
                    this.frame.connexion_client(login);
                }
                else if (type.equals("transporteur")) {
                    this.frame.connexion_transporteur(login);
                }
                else if (type.equals("emballeur")) {
                    this.frame.connexion_emballeur(login);
                }
                else if (type.equals("gerant")) {
                    this.frame.connexion_gerant(login);
                }
                else if (type.equals("douane")) {
                    this.frame.connexion_douane(login);
                }
        }
        else{
            this.frame.connexion_co(false);
        }

    }

    public void connexion_interdit(){
        this.connection_F = false;
    }
}
