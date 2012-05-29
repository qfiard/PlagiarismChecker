package bd6.data;

import bd6.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Client extends User {
    protected String company;
    protected String address;
    protected String city;
    protected String zipCode;
    protected String country;
    protected String phoneNumber;

    protected Client(String id, String password, String company, String address,
                     String city, String zipCode, String country,
                     String phoneNumber) {
        this.id = id;
        this.password = password;
        this.company = company;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
    }

    public static ArrayList<User> get() throws SQLException {
        String query = "SELECT * FROM clients";
        Statement statement = Main.connection.createStatement();
        ResultSet rows = statement.executeQuery(query);
        ArrayList<User> buffer = new ArrayList<User>();

        while (rows.next()) {
            String id = rows.getString("id");
            String password = rows.getString("password");
            String company = rows.getString("company");
            String address = rows.getString("address");
            String city = rows.getString("city");
            String zipCode = rows.getString("zip_code");
            String country = rows.getString("country");
            String phoneNumber = rows.getString("phone_number");
            Client client = new Client(id, password, company, address, city,
                                       zipCode, country, phoneNumber);

            buffer.add(client);
        }

        statement.close();
        return buffer;
    }

    public static Client get(String id) throws SQLException {
        String query = "SELECT * FROM clients WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, id);
        statement.execute();

        ResultSet row = statement.getResultSet();

        if (row.next()) {
            String password = row.getString("password");
            String company = row.getString("company");
            String address = row.getString("address");
            String city = row.getString("city");
            String zipCode = row.getString("zip_code");
            String country = row.getString("country");
            String phoneNumber = row.getString("phone_number");

            statement.close();
            return new Client(id, password, company, address, city, zipCode,
                              country, phoneNumber);
        } else {
            statement.close();
            return null;
        }
    }

    public static Client get(String id, String password) throws SQLException {
        String query = "SELECT * FROM clients WHERE id = ? AND password = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, id);
        statement.setString(2, password);
        statement.execute();

        ResultSet row = statement.getResultSet();

        if (row.next()) {
            String company = row.getString("company");
            String address = row.getString("address");
            String city = row.getString("city");
            String zipCode = row.getString("zip_code");
            String country = row.getString("country");
            String phoneNumber = row.getString("phone_number");

            statement.close();
            return new Client(id, password, company, address, city, zipCode,
                              country, phoneNumber);
        } else {
            statement.close();
            return null;
        }
    }

    @Override
    public UserKind getKind() {
        return UserKind.CLIENT;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
