package bd6.gui.customs;

import bd6.Main;
import bd6.data.Customs;
import bd6.data.Package;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class PackagesTableModel extends AbstractTableModel {
    protected static final int DISPLAYED_COLUMN_COUNT = 3;
    protected ArrayList<Package> packages;

    public PackagesTableModel() {
        this(false);
    }

    public PackagesTableModel(boolean checkedPackages) {
        if (!(Main.currentUser instanceof Customs)) {
            String message = "You must be a customs officer to see that.";

            System.err.println("Fatal Error: User is not a customs officer.");
            JOptionPane.showMessageDialog(null, message, "Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        String country = ((Customs) Main.currentUser).getCountry();

        try {
            packages = Package.getByCountryId(country, checkedPackages);
        } catch (SQLException exception) {
            String message = "An error occurred while fetching the package list.";

            System.err.println("Fatal Error:");
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, message, "Database Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
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
            return packages.get(rowIndex).getPacker().getFullName();

        case 2:
            return packages.get(rowIndex).getAddress();

        default:
            return packages.get(rowIndex);
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return "Package ID";

        case 1:
            return "Packer's Name";

        case 2:
            return "Package Destination Address";

        default:
            return "Error";
        }
    }
}
