import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;


public class ResultSetTableModel extends AbstractTableModel{

	ResultSet resultSet;
	private ResultSetMetaData resultSetMetaData;
	
	public ResultSetTableModel( ResultSet resultSet ){
		this.resultSet = resultSet;

		try {
			this.resultSetMetaData = resultSet.getMetaData();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getColumnCount(){
		try{
			return resultSetMetaData.getColumnCount();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}


	public int getRowCount(){
		try{
			resultSet.last();
			return resultSet.getRow();
		} 
		catch (SQLException e){
			e.printStackTrace();
			return 0;
		}
	}


	public Object getValueAt(int rowIndex, int columnIndex) {
		try{
			resultSet.absolute( rowIndex + 1 );
			return resultSet.getObject(columnIndex + 1 );
		} 
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}


	public String getColumnName( int column ){
		try {
			return resultSetMetaData.getColumnName( column + 1 );
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
}