package bd6.gui.manager;

import bd6.data.Item;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ItemTableModel extends AbstractTableModel {
    protected static final int DISPLAYED_COLUMN_COUNT = 2;
    protected Item[] items;

    public ItemTableModel() {
        try {
            items = Item.get();
        } catch (SQLException exception) {
            String message = "Error while fetching the item list.";

            System.err.println("Fatal Error:");
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, message, "Database Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    @Override
    public int getRowCount() {
        return items.length;
    }

    @Override
    public int getColumnCount() {
        return DISPLAYED_COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return items[rowIndex].getDescription();

        case 1:
            return items[rowIndex].getPrice();

        default:
            return items[rowIndex];
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return "Description";

        case 1:
            return "Unit Price (EUR)";

        default:
            return "Error";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return String.class;

        case 1:
            return Integer.class;

        default:
            return Item.class;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue != null && aValue instanceof Integer) {
            items[rowIndex].setPrice((Integer) aValue);

            try {
                items[rowIndex].commit();
            } catch (SQLException exception) {
                String message = "Error while updating the item's price.";

                System.err.println("Fatal Error:");
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, message, "Database Error",
                                              JOptionPane.ERROR_MESSAGE);
            }

            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }
}
