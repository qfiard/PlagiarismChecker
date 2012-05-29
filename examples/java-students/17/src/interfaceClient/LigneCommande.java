package interfaceClient;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import donnees.ConnexionBD;
import donnees.Constantes;

public class LigneCommande extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	
	private JButton derouler;
	private boolean pressed = false;
	
	private JPanel panelColis = new JPanel();
	
	public LigneCommande(int idCommande){
		
		ConnexionBD conn = new ConnexionBD();
		Statement st = conn.createStatement();
		
		try {
			ResultSet rs = st.executeQuery("SELECT id_colis FROM " + Constantes.base_colis + " WHERE id_commande='" +
					idCommande + "';");
			
			/*if(rs.absolute(4)){
				String idduclient = rs.getString(1);
			}*/
			
			JLabel id = new JLabel("ID: " + idCommande);
			
			ImageIcon iconeDerouler = new ImageIcon();
			try {
				BufferedImage img = ImageIO.read(new File("src/ic_menu_info_details.png"));
				Image resize = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
				iconeDerouler.setImage(resize);					
			} catch (IOException e) {
				e.printStackTrace();
			}
			derouler = new JButton(iconeDerouler);
			derouler.addActionListener(this);
			derouler.setContentAreaFilled(false);
			derouler.setBorderPainted(false);
			
			while(rs.next()){
				panelColis.add(new LigneColis(rs.getInt(1)));
			}
			
			panelColis.setVisible(false);
			this.add(panelColis);
			
			rs.close();
			
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if(source == derouler){
			pressed = !pressed;
			
			if(pressed){	//afficher les colis
				panelColis.setVisible(true);
			}
			else{	//cacher les colis
				panelColis.setVisible(false);
			}
		}
		
	}
	
}
