package bd6.data;

import bd6.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Container {
    protected int id;
    protected String country;

    protected Container(int id, String country) {
        this.id = id;
        this.country = country;
    }

    public static Container get(int id) throws SQLException {
        String query = "SELECT * FROM containers WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setInt(1, id);
        statement.execute();

        ResultSet row = statement.getResultSet();

        if (row.next()) {
            String country = row.getString("country");
            Container container = new Container(id, country);

            statement.close();
            return container;
        } else {
            statement.close();
            return null;
        }
    }
}
