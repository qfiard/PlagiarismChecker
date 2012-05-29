package bd6.data;

import bd6.Main;
import bd6.util.Pair;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Item {
    protected String id;
    protected String description;
    protected int itemsPerPackage;
    protected int packagesPerPallet;
    protected ItemCategory category;
    protected int price;
    protected int priceRiseRate;
    protected int weight;

    protected Item(String id, String description, int itemsPerPackage,
                   int packagesPerPallet, ItemCategory category, int price,
                   int priceRiseRate, int weight) {
        this.id = id;
        this.description = description;
        this.itemsPerPackage = itemsPerPackage;
        this.packagesPerPallet = packagesPerPallet;
        this.category = category;
        this.price = price;
        this.priceRiseRate = priceRiseRate;
        this.weight = weight;
    }

    public static Item[] get() throws SQLException {
        String query = "SELECT * FROM items ORDER BY price";
        Statement statement = Main.connection.createStatement();
        ResultSet rows = statement.executeQuery(query);
        LinkedList<Item> buffer = new LinkedList<Item>();

        while (rows.next()) {
            String id = rows.getString("id");
            String description = rows.getString("description");
            int itemsPerPackage = rows.getInt("items_per_package");
            int packagesPerPallet = rows.getInt("packages_per_pallet");
            ItemCategory category = ItemCategory.valueOf(rows.getString("category"));
            int price = rows.getInt("price");
            int priceRiseRate = rows.getInt("price_rise_rate");
            int weight = rows.getInt("weight");
            Item item = new Item(id, description, itemsPerPackage,
                                 packagesPerPallet, category, price,
                                 priceRiseRate, weight);

            buffer.add(item);
        }

        statement.close();
        return buffer.toArray(new Item[0]);
    }

    public static String[] getTopSells() throws SQLException {
        String query = "SELECT description FROM items JOIN order_contents ON "
                       + "item_id = id GROUP BY description ORDER BY "
                       + "COUNT(item_id) DESC LIMIT 10";
        Statement statement = Main.connection.createStatement();
        ResultSet rows = statement.executeQuery(query);
        String[] items = new String[10];

        for (int i = 0; i < 10; ++i)
            if (rows.next())
                items[i] = (i + 1) + ". " + rows.getString("description");
            else
                items[i] = "";

        return items;
    }

    public static List<Pair<Item, Integer>> getByOrderId(int orderId) throws SQLException {
        String query = "SELECT * FROM items JOIN order_contents ON "
                       + "order_id = ? AND id = item_id";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setInt(1, orderId);
        statement.execute();

        LinkedList<Pair<Item, Integer>> buffer = new LinkedList<Pair<Item, Integer>>();
        ResultSet rows = statement.getResultSet();

        while (rows.next()) {
            String id = rows.getString("id");
            String description = rows.getString("description");
            int itemsPerPackage = rows.getInt("items_per_package");
            int packagesPerPallet = rows.getInt("packages_per_pallet");
            ItemCategory category = ItemCategory.valueOf(rows.getString("category"));
            int price = rows.getInt("price");
            int priceRiseRate = rows.getInt("price_rise_rate");
            int weight = rows.getInt("weight");
            int quantity = rows.getInt("quantity");
            Item item = new Item(id, description, itemsPerPackage,
                                 packagesPerPallet, category, price,
                                 priceRiseRate, weight);

            buffer.add(new Pair<Item, Integer>(item, quantity));
        }

        statement.close();
        return buffer;
    }

    public static ArrayList<Pair<Item, Integer>> getByPackageId(int packageId) throws SQLException {
        String query = "SELECT * FROM items JOIN order_contents ON "
                       + "package_id = ? AND id = item_id";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setInt(1, packageId);
        statement.execute();

        ArrayList<Pair<Item, Integer>> buffer = new ArrayList<Pair<Item, Integer>>();
        ResultSet rows = statement.getResultSet();

        while (rows.next()) {
            String id = rows.getString("id");
            String description = rows.getString("description");
            int itemsPerPackage = rows.getInt("items_per_package");
            int packagesPerPallet = rows.getInt("packages_per_pallet");
            ItemCategory category = ItemCategory.valueOf(rows.getString("category"));
            int price = rows.getInt("price");
            int priceRiseRate = rows.getInt("price_rise_rate");
            int weight = rows.getInt("weight");
            int quantity = rows.getInt("quantity");
            Item item = new Item(id, description, itemsPerPackage,
                                 packagesPerPallet, category, price,
                                 priceRiseRate, weight);

            buffer.add(new Pair<Item, Integer>(item, quantity));
        }

        statement.close();
        return buffer;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getItemsPerPackage() {
        return itemsPerPackage;
    }

    public int getPackagesPerPallet() {
        return packagesPerPallet;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getPriceRiseRate() {
        return priceRiseRate;
    }

    public int getWeight() {
        return weight;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void commit() throws SQLException {
        String query = "UPDATE items SET price = ? WHERE id = ?";
        PreparedStatement statement = Main.connection.prepareStatement(query);

        statement.setInt(1, price);
        statement.setString(2, id);
        statement.execute();
        statement.close();
    }
}
