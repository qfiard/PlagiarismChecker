import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;


public class IntTextField extends JTextField{
	
	public IntTextField(){
		this.setText("");
		this.setPreferredSize(new Dimension(150,20));
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				char c = evt.getKeyChar();
				//le caractere est numerique
				if (c >= '0' && c <= '9') {
					// System.out.println(evt);
				} else {
					//suppression du caractere
					evt.consume();
				}
			}
		}); 
	}

}
