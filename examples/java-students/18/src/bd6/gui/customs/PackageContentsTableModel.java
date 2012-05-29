package bd6.gui.customs;

import bd6.data.Item;
import bd6.util.Pair;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class PackageContentsTableModel extends AbstractTableModel {
    protected static final int DISPLAYED_COLUMN_COUNT = 3;
    protected ArrayList<Pair<Item, Integer>> items;

    public PackageContentsTableModel(ArrayList<Pair<Item, Integer>> items) {
        if (items == null)
            throw new IllegalArgumentException(new NullPointerException());

        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return DISPLAYED_COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return items.get(rowIndex).first.getDescription();

        case 1:
            return items.get(rowIndex).first.getPrice();

        case 2:
            return items.get(rowIndex).second;

        default:
            return items.get(rowIndex);
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return "Item Description";

        case 1:
            return "Unit Price (EUR)";

        case 2:
            return "Quantity";

        default:
            return "Error";
        }
    }
}
