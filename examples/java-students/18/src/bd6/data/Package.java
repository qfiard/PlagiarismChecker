package bd6.data;

import bd6.Main;
import bd6.util.Pair;
import java.sql.*;
import java.util.ArrayList;

public class Package {
    protected int id;
    protected ItemCategory kind;
    protected ArrayList<Pair<Item, Integer>> contents;
    protected String address;
    protected String country;
    protected Container container;
    protected Packer packer;
    protected Date sentDate;
    protected Date controlDate;
    protected String controlComment;
    protected ControlStatus controlStatus;

    protected Package(int id, ItemCategory kind, ArrayList<Pair<Item, Integer>> contents,
                      String address, String country, Container container,
                      Packer packer, Date sentDate, Date controlDate,
                      String controlComment, ControlStatus controlStatus) {
        this.id = id;
        this.kind = kind;
        this.contents = contents;
        this.address = address;
        this.country = country;
        this.container = container;
        this.packer = packer;
        this.sentDate = sentDate;
        this.controlDate = controlDate;
        this.controlComment = controlComment;
        this.controlStatus = controlStatus;
    }

    public int getId() {
        return id;
    }

    public ItemCategory getKind() {
        return kind;
    }

    public ArrayList<Pair<Item, Integer>> getContents() {
        return contents;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public Container getContainer() {
        return container;
    }

    public Packer getPacker() {
        return packer;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public Date getControlDate() {
        return controlDate;
    }

    public String getControlComment() {
        return controlComment;
    }

    public ControlStatus getControlStatus() {
        return controlStatus;
    }

    public int getValue() {
        int total = 0;

        for (Pair<Item, Integer> item : contents)
            total += item.first.getPrice() * item.second;

        return total;
    }

    public static ArrayList<Package> get() throws SQLException {
        String query = "SELECT * FROM packages";
        Statement statement = Main.connection.createStatement();
        ResultSet rows = statement.executeQuery(query);
        ArrayList<Package> buffer = new ArrayList<Package>();

        while (rows.next()) {
            int id = rows.getInt("id");
            ItemCategory kind = ItemCategory.valueOf(rows.getString("kind"));
            ArrayList<Pair<Item, Integer>> contents = Item.getByPackageId(id);
            String address = rows.getString("address");
            String country = rows.getString("country");
            Container container = Container.get(rows.getInt("container"));
            Packer packer = Packer.get(rows.getString("packer"));
            Date sentDate = rows.getDate("sent_date");
            Date controlDate = rows.getDate("control_date");
            String controlComment = rows.getString("control_comment");
            ControlStatus controlStatus = rows.getString("control_status") == null
                                          ? ControlStatus.UNDETERMINED
                                          : ControlStatus.valueOf(rows.getString("control_status"));
            Package pkg = new Package(id, kind, contents, address, country,
                                      container, packer, sentDate, controlDate,
                                      controlComment, controlStatus);

            buffer.add(pkg);
        }

        statement.close();
        return buffer;
    }

    public static Package get(int id) throws SQLException {
        String query = "SELECT * FROM packages WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setInt(1, id);
        statement.execute();

        ResultSet row = statement.getResultSet();

        if (row.next()) {
            ItemCategory kind = ItemCategory.valueOf(row.getString("kind"));
            ArrayList<Pair<Item, Integer>> contents = Item.getByPackageId(id);
            String address = row.getString("address");
            String country = row.getString("country");
            Container container = Container.get(row.getInt("container"));
            Packer packer = Packer.get(row.getString("packer"));
            Date sentDate = row.getDate("sent_date");
            Date controlDate = row.getDate("control_date");
            String controlComment = row.getString("control_comment");
            ControlStatus controlStatus = row.getString("control_status") == null
                                          ? ControlStatus.UNDETERMINED
                                          : ControlStatus.valueOf(row.getString("control_status"));
            Package pkg = new Package(id, kind, contents, address, country,
                                      container, packer, sentDate, controlDate,
                                      controlComment, controlStatus);

            statement.close();
            return pkg;
        } else {
            statement.close();
            return null;
        }
    }

    public static ArrayList<Package> getByCountryName(String country) throws SQLException {
        String query = "SELECT * FROM packages WHERE country = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, country);
        statement.execute();

        ResultSet rows = statement.getResultSet();
        ArrayList<Package> buffer = new ArrayList<Package>();

        while (rows.next()) {
            int id = rows.getInt("id");
            ItemCategory kind = ItemCategory.valueOf(rows.getString("kind"));
            ArrayList<Pair<Item, Integer>> contents = Item.getByPackageId(id);
            String address = rows.getString("address");
            Container container = Container.get(rows.getInt("container"));
            Packer packer = Packer.get(rows.getString("packer"));
            Date sentDate = rows.getDate("sent_date");
            Date controlDate = rows.getDate("control_date");
            String controlComment = rows.getString("control_comment");
            ControlStatus controlStatus = rows.getString("control_status") == null
                                          ? ControlStatus.UNDETERMINED
                                          : ControlStatus.valueOf(rows.getString("control_status"));
            Package pkg = new Package(id, kind, contents, address, country,
                                      container, packer, sentDate, controlDate,
                                      controlComment, controlStatus);

            buffer.add(pkg);
        }

        statement.close();
        return buffer;
    }

    public static ArrayList<Package> getByCountryId(String country, boolean checkedPackages) throws SQLException {
        String query;

        if (checkedPackages)
            query = "SELECT * FROM packages WHERE country = ? "
                    + "AND control_date IS NOT NULL";
        else
            query = "SELECT * FROM packages WHERE country = ? "
                    + "AND control_date IS NULL";

        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, country);
        statement.execute();

        ResultSet rows = statement.getResultSet();
        ArrayList<Package> buffer = new ArrayList<Package>();

        while (rows.next()) {
            int id = rows.getInt("id");
            ItemCategory kind = ItemCategory.valueOf(rows.getString("kind"));
            ArrayList<Pair<Item, Integer>> contents = Item.getByPackageId(id);
            String address = rows.getString("address");
            Container container = Container.get(rows.getInt("container"));
            Packer packer = Packer.get(rows.getString("packer"));
            Date sentDate = rows.getDate("sent_date");
            Date controlDate = rows.getDate("control_date");
            String controlComment = rows.getString("control_comment");
            ControlStatus controlStatus = rows.getString("control_status") == null
                                          ? ControlStatus.UNDETERMINED
                                          : ControlStatus.valueOf(rows.getString("control_status"));
            Package pkg = new Package(id, kind, contents, address, country,
                                      container, packer, sentDate, controlDate,
                                      controlComment, controlStatus);

            buffer.add(pkg);
        }

        statement.close();
        return buffer;
    }

    public void setControlDate(Date controlDate) {
        if (controlDate == null)
            throw new IllegalArgumentException(new NullPointerException());

        this.controlDate = controlDate;
    }

    public void setControlComment(String controlComment) {
        if (controlComment == null)
            throw new IllegalArgumentException(new NullPointerException());

        this.controlComment = controlComment;
    }

    public void setControlStatus(ControlStatus controlStatus) {
        if (controlStatus == null)
            throw new IllegalArgumentException(new NullPointerException());

        this.controlStatus = controlStatus;
    }

    public void commit() throws SQLException {
        String query = "UPDATE packages SET sent_date = ?, control_date = ?, "
                       + "control_comment = ?, control_status = ? WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setDate(1, sentDate);
        statement.setDate(2, controlDate);
        statement.setString(3, controlComment);
        statement.setString(4, controlStatus.toString());
        statement.setInt(5, id);
        statement.execute();
        statement.close();
    }
}
