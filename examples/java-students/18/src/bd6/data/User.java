package bd6.data;

import bd6.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class User {
    public abstract UserKind getKind();
    protected String id;
    protected String password;

    public static UserKind getKind(String id) throws SQLException {
        String query = "SELECT kind FROM users WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, id);
        statement.execute();

        ResultSet row = statement.getResultSet();

        if (row.next()) {
            UserKind kind = UserKind.valueOf(row.getString("kind"));

            statement.close();
            return kind;
        } else {
            statement.close();
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void fire() throws SQLException {
        String query = "UPDATE users SET kind = 'FIRED' WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, id);
        statement.execute();
        statement.close();
    }
}
