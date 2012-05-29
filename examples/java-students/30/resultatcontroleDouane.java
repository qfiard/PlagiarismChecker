import java.io.*;
import java.sql.*;
import java.util.Scanner;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class resultatcontroleDouane extends JFrame {
	connectionBDD connecte;//ou static
	
	private JPanel container = new JPanel();
	private JPanel confirmecont = new JPanel();
	
	public JTextField NumeroColis = new JTextField();
	private JLabel label_colis = new JLabel("    Numero du colis :");
	
	public JTextField resultat = new JTextField();
	private JLabel label_resultat = new JLabel("    Resultat :");
	
	JButton confirme = new JButton("Affiche la Requete");
	
	
	
	public resultatcontroleDouane(final connectionBDD connecte){

		this.connecte = connecte;
		this.setTitle("Recherche parmi les commandes : ");
        this.setSize(350,150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        NumeroColis.setPreferredSize(new Dimension(180,25));
        resultat.setPreferredSize(new Dimension(180,25));

        
        container.add(label_colis);
        container.add(NumeroColis);
        
        container.add(label_resultat);
        container.add(resultat);
        container.setLayout(new GridLayout(3,2));
        
        confirmecont.add(confirme);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(container,BorderLayout.NORTH);
        this.getContentPane().add(confirmecont,BorderLayout.SOUTH);
        this.setVisible(true);
        
        confirme.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				String res = connecte.(NumeroCommande.getText());
				connectionDouane.this.affiche_resultat.setText(res);
				dispose();
			}				
		});
        
	}
	
	
}
