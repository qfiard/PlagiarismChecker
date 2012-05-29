package bd6.data;

import bd6.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customs extends User {
    protected String country;
    protected int checkedPackageRate;

    protected Customs(String id, String password, String country,
                      int checkedPackageRate) {
        this.id = id;
        this.password = password;
        this.country = country;
        this.checkedPackageRate = checkedPackageRate;
    }

    public static Customs get(String id, String password) throws SQLException {
        String query = "SELECT * FROM customs WHERE id = ? AND password = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, id);
        statement.setString(2, password);
        statement.execute();

        ResultSet row = statement.getResultSet();

        if (row.next()) {
            String country = row.getString("country");
            int checkedPackageRate = row.getInt("checked_package_rate");

            statement.close();
            return new Customs(id, password, country, checkedPackageRate);
        } else {
            statement.close();
            return null;
        }
    }

    public static Customs get(String id) throws SQLException {
        String query = "SELECT * FROM managers WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, id);

        if (statement.execute()) {
            ResultSet row = statement.getResultSet();
            String password = row.getString("password");
            String country = row.getString("country");
            int checkedPackageRate = row.getInt("checked_package_rate");

            statement.close();
            return new Customs(id, password, country, checkedPackageRate);
        } else {
            statement.close();
            return null;
        }
    }

    public String getCountry() {
        return country;
    }

    public int getCheckedPackageRate() {
        return checkedPackageRate;
    }

    @Override
    public UserKind getKind() {
        return UserKind.CUSTOMS;
    }
}
