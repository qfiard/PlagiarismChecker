package interfaceGerant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InfosProduit extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel infos;
	private JButton modifier;
	
	public InfosProduit(){
		
		ResultSet rs = null;
		
		LinkedList<Object[]> save = new LinkedList<Object[]>();		
		Object[][] donnees;
		
		try {
			while(rs.next()){
				Object[] data = new Object[3];
				
				data[0] = rs.getString(1);
				
				save.add(data);
			}

			donnees = new Object[save.size()][];
			
			for(int i=0; i<donnees.length; i++){
				donnees[i] = save.get(i);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == modifier){
			
		}
		
	}

}
