package bd6.data;

import bd6.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Manager extends User {
    protected String fullName;

    protected Manager(String id, String password, String fullName) {
        this.id = id;
        this.password = password;
        this.fullName = fullName;
    }

    public static Manager get(String id, String password) throws SQLException {
        String query = "SELECT * FROM managers WHERE id = ? AND password = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, id);
        statement.setString(2, password);
        statement.execute();

        ResultSet row = statement.getResultSet();

        if (row.next()) {
            String fullName = row.getString("full_name");

            statement.close();
            return new Manager(id, password, fullName);
        } else {
            statement.close();
            return null;
        }
    }

    public static Manager get(String id) throws SQLException {
        String query = "SELECT * FROM managers WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, id);

        if (statement.execute()) {
            ResultSet row = statement.getResultSet();
            String password = row.getString("password");
            String fullName = row.getString("full_name");

            statement.close();
            return new Manager(id, password, fullName);
        } else {
            statement.close();
            return null;
        }
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public UserKind getKind() {
        return UserKind.MANAGER;
    }
}
