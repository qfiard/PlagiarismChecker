package bd6.data;

import bd6.Main;
import bd6.util.Pair;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

public class Order {
    protected int id;
    protected Client owner;
    protected List<Pair<Item, Integer>> contents;
    protected OrderStatus status;
    protected Date shippingDate;

    public Order(Client owner, List<Pair<Item, Integer>> contents, Date shippingDate) {
        this(Integer.MIN_VALUE, owner, contents, OrderStatus.UNCONFIRMED, shippingDate);
    }

    protected Order(int id, Client owner, List<Pair<Item, Integer>> contents,
                    OrderStatus status, Date shippingDate) {
        this.id = id;
        this.owner = owner;
        this.contents = contents;
        this.status = status;
        this.shippingDate = shippingDate;
    }

    public int getId() {
        return id;
    }

    public Client getOwner() {
        return owner;
    }

    public List<Pair<Item, Integer>> getContents() {
        return contents;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getTotalValue() {
        int total = 0;

        for (Pair<Item, Integer> pair : contents)
            total += pair.first.getPrice() * pair.second;

        return total;
    }

    public static Order get(int id) throws SQLException {
        String query = "SELECT * FROM orders WHERE owner = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setInt(1, id);
        statement.execute();

        ResultSet row = statement.getResultSet();

        if (row.next()) {
            Client owner = Client.get(row.getString("owner"));
            List<Pair<Item, Integer>> contents = Item.getByOrderId(id);
            OrderStatus status = OrderStatus.valueOf(row.getString("status"));
            Date shippingDate = row.getDate("shipping_date");
            Order order = new Order(id, owner, contents, status, shippingDate);

            statement.close();
            return order;
        } else {
            statement.close();
            return null;
        }
    }

    public static ArrayList<Order> getByClientId(String clientId) throws SQLException {
        String query = "SELECT * FROM orders WHERE owner = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setString(1, clientId);
        statement.execute();

        ArrayList<Order> buffer = new ArrayList<Order>();
        Client owner = Client.get(clientId);
        ResultSet rows = statement.getResultSet();

        while (rows.next()) {
            int id = rows.getInt("id");
            List<Pair<Item, Integer>> contents = Item.getByOrderId(id);
            OrderStatus status = OrderStatus.valueOf(rows.getString("status"));
            Date shippingDate = rows.getDate("shipping_date");
            Order order = new Order(id, owner, contents, status, shippingDate);

            buffer.add(order);
        }

        statement.close();
        return buffer;
    }

    public static List<Order> get() throws SQLException {
        String query = "SELECT * FROM orders";
        Statement statement = Main.connection.createStatement();
        ResultSet rows = statement.executeQuery(query);
        LinkedList<Order> buffer = new LinkedList<Order>();

        while (rows.next()) {
            int id = rows.getInt("id");
            Client owner = Client.get(rows.getString("owner"));
            List<Pair<Item, Integer>> contents = Item.getByOrderId(id);
            OrderStatus status = OrderStatus.valueOf(rows.getString("status"));
            Date shippingDate = rows.getDate("shipping_date");
            Order order = new Order(id, owner, contents, status, shippingDate);

            buffer.add(order);
        }

        statement.close();
        return buffer;
    }

    public void commit() throws SQLException {
        if (id == Integer.MIN_VALUE) {
            String orderQuery = "INSERT INTO orders VALUES (DEFAULT, ?, ?, ?) RETURNING id";
            StringBuilder orderContentsQuery = new StringBuilder("INSERT INTO order_contents VALUES ");
            LinkedList<String> contentsBuffer = new LinkedList<String>();
            PreparedStatement orderStatement = Main.connection.prepareStatement(orderQuery);

            orderStatement.setString(1, owner.getId());
            orderStatement.setString(2, OrderStatus.UNCONFIRMED.toString());
            orderStatement.setDate(3, shippingDate);
            orderStatement.execute();

            ResultSet row = orderStatement.getResultSet();

            row.next();
            id = row.getInt("id");
            orderStatement.close();

            for (Pair<Item, Integer> pair : contents) {
                String s = "('" + pair.first.getId() + "', '" + id + "', NULL, '"
                           + pair.second + "')";

                contentsBuffer.add(s);
            }

            Iterator<String> i = contentsBuffer.iterator();

            while (i.hasNext()) {
                orderContentsQuery.append(i.next());

                if (i.hasNext())
                    orderContentsQuery.append(", ");
            }

            Statement orderContentsStatement = Main.connection.createStatement();

            orderContentsStatement.execute(orderContentsQuery.toString());
            orderContentsStatement.close();
        }
    }
}
