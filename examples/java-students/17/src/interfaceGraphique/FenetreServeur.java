package interfaceGraphique;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class FenetreServeur extends JFrame implements ActionListener{
	static final long serialVersionUID=1;
	public boolean submit;
	public JPanel conteneur = new JPanel();	

	public JLabel hostJ1 = new JLabel("Adresse (Joueur 1):");
	public JTextField hoststrJ1 = new JTextField();
	public JLabel hostJ2 = new JLabel("Adresse (Joueur 2):");
	public JTextField hoststrJ2 = new JTextField();
	public JLabel portJ1 = new JLabel("Port (Joueur 1):");
	public JTextField portstrJ1 = new JTextField();
	public JLabel portJ2 = new JLabel("Port (Joueur 2):");
	public JTextField portstrJ2 = new JTextField();

	public JButton envoyer=new JButton("<html><p color=\"green\">Envoyer</p></html>");


	public FenetreServeur(){

		this.submit=false;
		this.setTitle("Informations de Connexion");
		this.setSize(310, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		conteneur.setBackground(Color.white);
		conteneur.setLayout(new BorderLayout());

		JPanel top = new JPanel();

		//			Font police = new Font("Arial", Font.BOLD, 14);
		//			//portJ1.setFont(police);
		//			portJ2.setPreferredSize(new Dimension(150, 30));
		//			//portJ1.setFont(police);
		//			portJ2.setPreferredSize(new Dimension(150, 30));
		//			//pseudostr.setForeground(Color.PINK);

		hoststrJ1.setPreferredSize(new Dimension(150, 30));
		portstrJ1.setPreferredSize(new Dimension(150, 30));
		hoststrJ1.setForeground(Color.RED);
		portstrJ1.setForeground(Color.RED);

		hoststrJ2.setPreferredSize(new Dimension(150, 30));
		portstrJ2.setPreferredSize(new Dimension(150, 30));
		hoststrJ2.setForeground(Color.BLUE);
		portstrJ2.setForeground(Color.BLUE);

		top.add(hostJ1);
		top.add(hoststrJ1);
		top.add(portJ1);
		top.add(portstrJ1);

		top.add(hostJ2);
		top.add(hoststrJ2);				
		top.add(portJ2);
		top.add(portstrJ2);

		envoyer.addActionListener(this);		

		conteneur.add(top);
		conteneur.add(envoyer, BorderLayout.SOUTH);
		this.setContentPane(conteneur);
		this.setVisible(true);            
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == envoyer){
			if(!hoststrJ1.getText().equals("") && !hoststrJ2.getText().equals("") && !portstrJ1.getText().equals("") && !portstrJ2.getText().equals("")){
				submit=true;
				System.out.println("AdreseJ1:"+hoststrJ1.getText()+"\n PortJ1 :"+portstrJ1.getText()+"\n AdresseJ2:"+hoststrJ1.getText()+"\n PortJ2:"+portstrJ1.getText());
				//this.dispose();
			}
			this.dispose();
		}
	}	

	public String getHoteJ1(){
		return this.hoststrJ1.getText();
	}

	public String getHoteJ2(){
		return this.hoststrJ2.getText();
	}

	public int getPortJ1(){
		return Integer.parseInt(this.portstrJ1.getText());
	}

	public int getPortJ2(){
		return Integer.parseInt(this.portstrJ2.getText());
	}


	public static void main(String[]args){
		FenetreServeur para=new FenetreServeur();

		//para.dispose();
	}
}



