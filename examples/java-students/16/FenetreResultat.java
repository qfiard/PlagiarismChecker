import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class FenetreResultat extends JFrame{
	JButton jb = new JButton("Retour");
	JFrame FenetreAvant;

	public FenetreResultat(TablePanel tp, JFrame f){
		this.setTitle("Resultats");
		this.add( tp, BorderLayout.CENTER );
		this.add(jb, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );		
		this.FenetreAvant = f;
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				FenetreAvant.setVisible(true);
				FenetreResultat.this.setVisible(false);
				setVisible(false);
			}					
		});
		
		this.setSize( 640, 480 );
		this.setVisible( true );
	}
}
