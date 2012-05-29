package bd6.gui.client;

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
            String message = "An error occurred while fetching the item list.";

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
            return "Price (EUR)";

        default:
            return "Error";
        }
    }
}
