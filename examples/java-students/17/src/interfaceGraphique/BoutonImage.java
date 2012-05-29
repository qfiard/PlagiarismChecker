package interfaceGraphique;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class BoutonImage extends JButton{

	private static final long serialVersionUID = 1L;
	
	public BoutonImage(String texte){
		super(texte);
		this.setContentAreaFilled(false);		
		this.setBorderPainted(false);
	}
	
	public BoutonImage(Icon image){
		super(image);
		this.setContentAreaFilled(false);		
		this.setBorderPainted(false);
	}
	
	public BoutonImage(String texte, Icon image){
		super(texte, image);
		
		Dimension size = getPreferredSize();
	}
	
	public BoutonImage(String texte, Icon image, boolean transparent){
		
		this(texte, image);
		
		this.setHorizontalTextPosition(SwingConstants.RIGHT);
		this.setVerticalTextPosition(SwingConstants.CENTER);

		
	}
	
	
}
