package interfaceClient;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class JTreeComponent extends JTree{

	public JTreeComponent(){
		
		DefaultMutableTreeNode commande = new DefaultMutableTreeNode("ID: SO78210");
		
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null);
		
		frame.add(new JTreeComponent());
		
		frame.setVisible(true);
	}
	
}
