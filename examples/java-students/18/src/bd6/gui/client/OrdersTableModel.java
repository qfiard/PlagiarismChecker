package bd6.gui.client;

import bd6.Main;
import bd6.data.Order;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class OrdersTableModel extends AbstractTableModel {
    protected final static int DISPLAYED_COLUMN_COUNT = 2;
    protected ArrayList<Order> orders;

    public OrdersTableModel() {
        try {
            orders = Order.getByClientId(Main.currentUser.getId());
        } catch (SQLException exception) {
            String message = "An error occurred while fetching the order list.";

            System.err.println("Fatal Error:");
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, message, "Database Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return DISPLAYED_COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return orders.get(rowIndex).getId();

        case 1:
            return orders.get(rowIndex).getStatus();

        default:
            return orders.get(rowIndex);
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return "Order ID";

        case 1:
            return "Status";

        default:
            return "Error";
        }
    }
}
