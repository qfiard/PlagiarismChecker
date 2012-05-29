package interfaceDouane;

import javax.swing.table.AbstractTableModel;

public class maTable extends AbstractTableModel{
	private Object[][] rows = new Object[0][0];
	private String[] columnHeader = null;
	
	private static final long serialVersionUID = 1L;

	public boolean isCellEditable(int iRowIndex, int iColumnIndex)
	{
		return false;
	}
	
	public void setData(Object[][] newData){
		rows = newData;
		super.fireTableDataChanged();
	}

	public String getColumnName(int column) {
		return this.columnHeader[column];
		}
	
	@Override
	public int getColumnCount() {
		return this.columnHeader.length;
	}
	@Override
	public int getRowCount() {
		return this.rows.length;
	}
	@Override
	public Object getValueAt(int arg0, int arg1) {
		return rows[arg0][arg1];
	}
	
	public void setHeader(String[] newHeaders){
		columnHeader = newHeaders;
		super.fireTableStructureChanged();
	}
	

	public void clear(){
		 for(int i = this.getRowCount(); i > 0; --i){
			 for(int j = this.getColumnCount(); j>0; --j){
				 this.setValueAt("null", i , j); 
			 }
		 }
		        
		}
	
}
