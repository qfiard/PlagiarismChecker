package bd6.gui.client;

import bd6.data.Item;
import bd6.util.Pair;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class NewOrderContentsTableModel extends AbstractTableModel {
    protected static final int DISPLAYED_COLUMN_COUNT = 3;
    protected ArrayList<Pair<Item, Integer>> items;

    public NewOrderContentsTableModel() {
        items = new ArrayList<Pair<Item, Integer>>();
    }

    public void addItem(Item item) {
        items.add(new Pair<Item, Integer>(item, 1));
        fireTableRowsInserted(items.size() - 1, items.size() - 1);
    }

    public void removeItem(int index) {
        items.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public int getTotalValue() {
        int total = 0;

        for (Pair<Item, Integer> pair : items)
            total += pair.first.getPrice() * pair.second;

        return total;
    }

    public ArrayList<Pair<Item, Integer>> getItems() {
        return items;
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
            return "Description";

        case 1:
            return "Price (EUR)";

        case 2:
            return "Quantity";

        default:
            return "Error";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return String.class;

        case 1:
        case 2:
            return Integer.class;

        default:
            return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2;
    }
}
