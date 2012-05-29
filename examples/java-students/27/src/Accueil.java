import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Accueil extends JFrame implements ActionListener{
	
	
	private JPanel container = new JPanel();
	private JLabel label = new JLabel("Qui etes vous ?");
	private JComboBox combo = new JComboBox();
	
	private JButton valider = new JButton("Valider");
	private JButton effacer = new JButton("Effacer");
	
	private JTextField champ1 = new JTextField();
	private JPasswordField champ2 = new JPasswordField();
	
	public String login="pays";
	public String table="douane";
	
	
	public Accueil() {
		
		this.setTitle("Projet BDD 2012");
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	
		contenu();
	
		this.setContentPane(container);
		this.setVisible(true);
	}
	
	public void contenu() {
		container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        
        // création des differents utilisateurs
        // et création de jcombobox
        String[] tab = {"Douane", "Gérant", "Client", "Emballeur", "Transporteur"};
        combo = new JComboBox(tab);
        
        combo.setPreferredSize(new Dimension(140, 20));
        combo.setForeground(Color.blue);
        combo.addActionListener(this);
        
        JPanel top = new JPanel();
        top.add(label);
        top.add(combo);
        
        // création des champs textes
        JPanel centre = new JPanel();
        JPanel centre1 = new JPanel();
        JPanel centre2 = new JPanel();
        JLabel text1 = new JLabel("Identifiant :");
        JLabel text2 = new JLabel("Mot de passe :");
        
        champ1.setPreferredSize(new Dimension(150, 25));
        champ2.setPreferredSize(new Dimension(150, 25));
        
        centre1.add(text1);
        centre1.add(champ1);
        centre2.add(text2);
        centre2.add(champ2);
        
        centre.add(centre1);
        centre.add(centre2);
        
        // création de la partie bouton
        JPanel south = new JPanel();
        
        valider.addActionListener(this);
        effacer.addActionListener(new EffacerListener());
        
        south.add(valider);
        south.add(effacer);
        
        container.add(top, BorderLayout.NORTH);
        container.add(centre, BorderLayout.CENTER);
        container.add(south, BorderLayout.SOUTH);
	}
	
	
		public void actionPerformed(ActionEvent e) {
			String select=combo.getSelectedItem().toString();
			
			if(e.getSource()==combo){
				
				if(select=="Douane"){
					login="pays";
					table="douane";
				}
				else if(select=="Gérant"){
					login="login";
					table="gerant";
				}
				else if(select=="Client"){
					login="login";
					table="client";
				}
				else if(select=="Emballeur"){
					login="refemballeur";
					table="emballeur";
				}
				else if(select=="Transporteur"){
					login="reftransp";
					table="transporteur";
				}
			}
			
			
			
			
			else if(e.getSource()==valider){
				if (champ1.getText().equals("") || champ2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Un champ n'a pas été rempli", "Attention", JOptionPane.WARNING_MESSAGE);
				}
				
				else {
					System.out.println("TEXT : champ1 " + champ1.getText());
					System.out.println("PASS : champ2 " + champ2.getText());
					System.out.println("COMBO : combo " + combo.getSelectedItem());
					
					try {
						
						ResultSet result  = Main.state.executeQuery
								("SELECT mdp FROM "+table+
										" WHERE " +login+ "='"+champ1.getText()+"'");
						
						
						
						if(result.next()) {
							String mdp = result.getString("mdp");
							
							System.out.println(mdp);
							
							if (!mdp.equals(champ2.getText())) {
								JOptionPane.showMessageDialog(null, "Mot de passe incorrect", "Erreur MDP", JOptionPane.ERROR_MESSAGE);
							}
							else {
								this.execute(champ1.getText(),table);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Login ou Mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						result.close();
					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
					}				
				}
				}
			}
			
	
	
	
		
	public void execute(String ref, String Table){
		this.dispose();
		
		if(Table=="douane"){
			
			Douane d = new Douane (ref);
		}
		else if(Table=="gerant"){
			
			Gerant g = new Gerant();	
			
		}
	}
		
	class EffacerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			champ1.setText(null);
			champ2.setText(null);
			combo.setSelectedIndex(0);
		}
	}
	
	

}
