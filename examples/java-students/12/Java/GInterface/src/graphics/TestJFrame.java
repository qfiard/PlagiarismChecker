package graphics;

import javax.swing.SwingUtilities;

public class TestJFrame {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// On crée une nouvelle instance de notre JDialog
				ConnectionWindow fenetre = new ConnectionWindow();
				// On la rend visible
				fenetre.setVisible(true);
			}
		});
	}
}