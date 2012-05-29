package bd6.gui.manager;

import bd6.data.Client;
import bd6.data.Packer;
import bd6.data.User;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class PeopleTableModel extends AbstractTableModel {
    protected static final int DISPLAYED_COLUMN_COUNT = 2;
    protected ArrayList<User> users;

    public PeopleTableModel() {
        try {
            ArrayList<User> clients = Client.get();
            ArrayList<User> packers = Packer.get();

            users = new ArrayList<User>();
            users.addAll(clients);
            users.addAll(packers);
        } catch (SQLException exception) {
            String message = "An error occurred while fetching the user list.";

            System.err.println("Fatal Error:");
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, message, "Database Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return DISPLAYED_COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
        case 0:
            if (users.get(rowIndex) instanceof Client)
                return ((Client) users.get(rowIndex)).getCompany();
            else
                return ((Packer) users.get(rowIndex)).getFullName();

        case 1:
            return users.get(rowIndex).getKind();

        default:
            return users.get(rowIndex);
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return "User Name";

        case 1:
            return "Role";

        default:
            return "Error";
        }
    }
}
