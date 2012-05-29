import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Panel_emb  extends Panel_princ implements ActionListener{
    boolean connection_F=true;

    //objet swing de notre Panel connexion

    public Panel_emb(inter_princ jfr){
        this.contenu = this.buildJP();
        this.frame = jfr;
    }

    public JPanel buildJP(){
        JPanel panel = new JPanel();
        return panel;
    }

    public void actionPerformed(ActionEvent e){
    }
}
