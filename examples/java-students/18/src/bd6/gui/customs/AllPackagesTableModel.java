package bd6.gui.customs;

import bd6.Main;
import bd6.data.Customs;
import bd6.data.Package;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class AllPackagesTableModel extends AbstractTableModel {
    protected static final int DISPLAYED_COLUMN_COUNT = 4;
    protected ArrayList<Package> packages;

    public AllPackagesTableModel() {
        if (!(Main.currentUser instanceof Customs)) {
            String message = "Error, you must be a customs officer to see that.";

            System.err.println("Fatal Error: User is not a customs officer.");
            JOptionPane.showMessageDialog(null, message, "Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        String country = ((Customs) Main.currentUser).getCountry();

        try {
            packages = Package.get();
        } catch (SQLException exception) {
            String message = "Error while fetching the package list.";
            System.err.println("Fatal Error:");
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, message, "Database Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return "Package ID";

        case 1:
            return "Address";

        case 2:
            return "Country";

        case 3:
            return "Sent Date";

        default:
            return "Error";
        }
    }

    @Override
    public int getRowCount() {
        return packages.size();
    }

    @Override
    public int getColumnCount() {
        return DISPLAYED_COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return packages.get(rowIndex).getId();

        case 1:
            return packages.get(rowIndex).getAddress();

        case 2:
            return packages.get(rowIndex).getCountry();

        case 3:
            Date date = packages.get(rowIndex).getSentDate();

            if (date == null)
                return "Not sent yet";
            else
                return date;

        default:
            return packages.get(rowIndex);
        }
    }
}
