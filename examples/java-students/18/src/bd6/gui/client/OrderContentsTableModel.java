package bd6.gui.client;

import bd6.data.Item;
import bd6.util.Pair;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class OrderContentsTableModel extends AbstractTableModel {
    protected static final int DISPLAYED_COLUMN_COUNT = 3;
    protected List<Pair<Item, Integer>> contents;

    public OrderContentsTableModel(List<Pair<Item, Integer>> contents) {
        if (contents == null)
            throw new IllegalArgumentException(new NullPointerException());

        this.contents = contents;
    }

    @Override
    public int getRowCount() {
        return contents.size();
    }

    @Override
    public int getColumnCount() {
        return DISPLAYED_COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return contents.get(rowIndex).first.getDescription();

        case 1:
            return contents.get(rowIndex).first.getPrice();

        case 2:
            return contents.get(rowIndex).second;

        default:
            return contents.get(rowIndex);
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return "Description";

        case 1:
            return "Unit Price (EUR)";

        case 2:
            return "Quantity";

        default:
            return "Error";
        }
    }
}
