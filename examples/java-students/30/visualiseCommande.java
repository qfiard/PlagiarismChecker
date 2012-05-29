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


public class visualiseCommande extends JFrame {
	connectionBDD connecte;//ou static
	
	private JPanel container = new JPanel();
	private JPanel confirmecont = new JPanel();
	
	public JTextField NumeroCommande=new JTextField();
	private JLabel label_num_cmd = new JLabel("    Numero de la commande :");
	
	public JTextField destination = new JTextField();
	private JLabel label_destination = new JLabel("    Destination :");

	public JTextField contenu = new JTextField();
	private JLabel label_contenu = new JLabel("    Contenu :");
	
	JButton confirme = new JButton("Affiche la Requete");
	
	
	
	public visualiseCommande(final connectionBDD connecte){

		this.connecte = connecte;
		this.setTitle("Recherche les detail d'une commande : ");
        this.setSize(350,150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        NumeroCommande.setPreferredSize(new Dimension(180,25));
        

        
        container.add(label_num_cmd);
        container.add(NumeroCommande);
        
        container.setLayout(new GridLayout(3,2));
        
        confirmecont.add(confirme);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(container,BorderLayout.NORTH);
        this.getContentPane().add(confirmecont,BorderLayout.SOUTH);
        this.setVisible(true);
        
        confirme.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				//setVisible(false);
				dispose();
			}				
		});
        
	}
}
