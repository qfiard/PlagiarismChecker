package bd6.data;

import bd6.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Packer extends User {
    protected int errorRate;
    protected String fullName;

    protected Packer(String id, String password, int errorRate, String fullName) {
        this.id = id;
        this.password = password;
        this.errorRate = errorRate;
        this.fullName = fullName;
    }

    public static ArrayList<User> get() throws SQLException {
        String query = "SELECT * FROM packers";
        Statement statement = Main.connection.createStatement();
        ResultSet rows = statement.executeQuery(query);
        ArrayList<User> buffer = new ArrayList<User>();

        while (rows.next()) {
            String id = rows.getString("id");
            String password = rows.getString("password");
            int errorRate = rows.getInt("error_rate");
            String fullName = rows.getString("full_name");
            Packer packer = new Packer(id, password, errorRate, fullName);

            buffer.add(packer);
        }

        statement.close();
        return buffer;
    }

    public static Packer get(String id) throws SQLException {
        String query = "SELECT * FROM packers WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, id);
        statement.execute();

        ResultSet row = statement.getResultSet();

        if (row.next()) {
            String password = row.getString("password");
            int errorRate = row.getInt("error_rate");
            String fullName = row.getString("full_name");

            statement.close();
            return new Packer(id, password, errorRate, fullName);
        } else {
            statement.close();
            return null;
        }
    }

    public int getErrorRate() {
        return errorRate;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public UserKind getKind() {
        return UserKind.PACKER;
    }

    public void incrementErrorRate() {
        if (errorRate < 100)
            errorRate++;
    }

    public void decrementErrorRate() {

        errorRate--;
    }

    public void commit() throws SQLException {
        String query = "UPDATE users SET rate = ? WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setInt(1, errorRate);
        statement.setString(2, id);
        statement.execute();
        statement.close();
    }
}
