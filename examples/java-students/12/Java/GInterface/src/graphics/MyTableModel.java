package graphics;

import javax.swing.table.*;

class MyTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5089569469704565824L;
	private String donnees[][];
	private String entetes[];

	MyTableModel(String entetes[], String donnees[][]) {
		this.donnees = donnees;
		this.entetes = entetes;
	}

	public int getColumnCount() {
		return entetes.length;
	}

	public int getRowCount() {
		return donnees.length;
	}

	public String getColumnName(int col) {
		return entetes[col];
	}

	public void setColumnName(String value, int col) {
		entetes[col] = value;
		fireTableStructureChanged();
	}

	public String getValueAt(int row, int col) {
		return donnees[row][col];
	}

	public void setValueAt(String value, int row, int col) {
		donnees[row][col] = (value == null) ? "" : value;
		fireTableCellUpdated(row, col);
	}
}