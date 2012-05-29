import java.io.*;
import java.sql.*;
import java.util.Calendar;


public class ConnectionExport {
    Connection conn; // connection to sql
    Statement st;
    PreparedStatement insert;
    PreparedStatement delete;
    PreparedStatement update;
    
    InputStream instream;
    InputStreamReader inreader;
    BufferedReader br;
    String line;
    
    // connection sql
    public ConnectionExport(String login, String motPasse) throws SQLException, ClassNotFoundException{
       // -------------------
	   // Connection sql
	   // --------------------
	   Class.forName("org.postgresql.Driver");
	   conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + login,login, motPasse);
    }

    // close connection
    public void close() throws SQLException{ 
	   conn.close();
    }

    public void createTables() throws SQLException {
        st = conn.createStatement();
        String query= "";
        try {
        	instream = new FileInputStream("sql/createTables.sql");
        	inreader = new InputStreamReader(instream);
        	br = new BufferedReader(inreader);
        	String line;
        	while ((line=br.readLine())!=null){
        		query += line + " ";
        	}
        } catch (Exception e) {
        	System.out.println(e.toString());
        }
        st.executeUpdate(query);
    }	 
                    
    public void deleteTables() throws SQLException {
		st = conn.createStatement();
		st.executeUpdate("DROP TABLE orders; DROP TABLE packages; DROP TABLE pallets; DROP TABLE containers; DROP TABLE products; DROP TABLE customs; DROP TABLE clients; DROP TABLE packers;  DROP TABLE deliverers; DROP TABLE manager; ");
    }
    
    public void insertData() throws SQLException{
		st = conn.createStatement();
        String query= "";
        try {
        	instream = new FileInputStream("sql/fillTables.sql");
        	inreader = new InputStreamReader(instream);
        	br = new BufferedReader(inreader);
        	String line;
        	while ((line=br.readLine())!=null){
        		query += line + " ";
        	}
        } catch (Exception e) {
        	System.out.println(e.toString());
        }
        st.executeUpdate(query);
    }
    
    public void insert() throws IOException, SQLException{
    	int i = 5; 
    	Data d = new Data();
     	d.getData();	
    	String s;
        update = conn.prepareStatement(" INSERT INTO packers (id_packer,lastname,firstname,error_rate,password,login) VALUES (?,?,?,?,?,?) " );
    	while(!d.packers.isEmpty()){
            s = d.packers.remove(0);
            System.out.println(i-- + " packers left to add...");
            update.setString(1, s.split("\\|")[1]);
            update.setString(2, s.split("\\|")[2]);
            update.setString(3, s.split("\\|")[3]);
            update.setInt(4, Integer.parseInt(s.split("\\|")[4]));
            update.setString(5, s.split("\\|")[5]);
            update.setString(6, s.split("\\|")[1]); // login = id_client
        	update.executeUpdate();
    	}
    	i = 100;
        update = conn.prepareStatement(" INSERT INTO clients (id_client,company,suffixe,address,city,postal_code,country,phone_num,password,login) VALUES (?,?,?,?,?,?,?,?,?,?) " );
    	while(!d.clients.isEmpty()){
            s = d.clients.remove(0);
            System.out.println(i-- + " clients left to add...");
            update.setString(1, s.split("\\|")[1]); //id_client
            update.setString(2, s.split("\\|")[2]); // lastname
            update.setString(3, s.split("\\|")[3]); // firstname
            update.setString(4, s.split("\\|")[4]); // address
            update.setString(5, s.split("\\|")[5]); // city
            update.setString(6, s.split("\\|")[6]); // postal_code
            update.setString(7, s.split("\\|")[7]); // country
            update.setString(8, s.split("\\|")[8]); // phone_num
            update.setString(9, s.split("\\|")[9]); // password
            update.setString(10,s.split("\\|")[1]); //id_client
            update.executeUpdate();
    	}
    	i = 2000;
        update = conn.prepareStatement(" INSERT INTO products (id_product,product_description,num_in_box,boxes_in_pallet,qualifiant,price,increase_rate,weight,stock) VALUES (?,?,?,?,?,?,?,?,?) " );
    	while(!d.products.isEmpty()){
            s =   d.products.remove(0);
            System.out.println(i-- + " products left to add...");
            update.setString(1,s.split("\\|")[1]); //id_prod
            update.setString(2, s.split("\\|")[2]); //descrip
            update.setInt(3, Integer.parseInt(s.split("\\|")[3])); // num_in_box
            update.setInt(4, Integer.parseInt(s.split("\\|")[4])); // boxes_in_pallet
            update.setString(5, s.split("\\|")[5]); //qualifiant
            update.setDouble(6, Integer.parseInt(s.split("\\|")[6])); // money
            update.setInt(7, Integer.parseInt(s.split("\\|")[7])); // rate
            update.setInt(8, Integer.parseInt(s.split("\\|")[8])); // weight
            update.setInt(9, Integer.parseInt(s.split("\\|")[9])); // stock
            update.executeUpdate();
    	} 	
    	i = 10;
        update = conn.prepareStatement(" INSERT INTO deliverers (id_deliverer,lastname,password,login) VALUES (?,?,?,?) " );
    	while(!d.deliverers.isEmpty()){
            s = d.deliverers.remove(0);
            System.out.println(i-- + " deliverers left to add...");
            update.setString(1, s.split("\\|")[1]); // id_deliv
            update.setString(2, s.split("\\|")[2]); // lastname
            update.setString(3, s.split("\\|")[3]); // pass
            update.setString(4, s.split("\\|")[1]); // login = id_deliv
            update.executeUpdate();
    	}
    	i=6;
        update = conn.prepareStatement(" INSERT INTO customs (country,checked_packages_rate,login,password) VALUES (?,?,?,?) " );
    	while(!d.customs.isEmpty()){
            s = d.customs.remove(0);
            System.out.println(i-- + " customs left to add...");
            update.setString(1, s.split("\\|")[1]); // country
            update.setInt(2, Integer.parseInt(s.split("\\|")[2])); // rate
            update.setString(3, s.split("\\|")[3]); // login
            update.setString(4, s.split("\\|")[4]); // pass
            update.executeUpdate();
    	}
        update = conn.prepareStatement(" INSERT INTO manager (login,password) VALUES (?,?) " );
        update.setString(1, d.manager.split("\\|")[3]); // login
        update.setString(2, d.manager.split("\\|")[4]); // pass
        System.out.println(i + " 0 manager left to add...");
    	update.executeUpdate();
    }

    public void addPackage(String package_id, String client_id, char qual, String stat) throws SQLException{
    	st = conn.createStatement();
    	st.executeUpdate("INSERT INTO packages (id_package,id_client,qualifiant, statut) VALUES ('"+package_id+"', '"+client_id+"', '"+qual+"', '"+stat+"')");
    }
    
    public void addOrder(String order_id, String product_id, int quantity, String client_id, int price, String package_id) throws SQLException{
    	st = conn.createStatement();
    	String query = "INSERT INTO orders (id_order, id_product, quantity, id_client, price, id_package)";
    	query += " VALUES ('"+order_id+"', '"+product_id+"',"+quantity+" , '"+client_id+"', "+price+", '"+package_id+"')";
    	st.executeUpdate(query);
    }    
    
    public void addContainer(String containerID, String clientID, int nbPallet) throws SQLException{
    	st = conn.createStatement();
    	st.executeUpdate("INSERT INTO containers (id_container, id_client, nb_pallets) VALUES ('"+containerID+"', '"+clientID+"', "+nbPallet+")");
    }
    
    public void addPallet(String palletID, String clientID, int nbPackages) throws SQLException{
    	st = conn.createStatement();
    	st.executeUpdate("INSERT INTO pallets (id_pallet, id_client, nb_packages) VALUES ('"+palletID+"', '"+clientID+"', "+nbPackages+")");
    }
    
    public boolean connection(String userTable, String login, String password) throws SQLException{
    	ResultSet res = exists("*", userTable, " WHERE login='"+login+"' AND password='"+password+"'");
    	if( res!=null ){
    		return true;
    	} else {
    		System.out.println("Failed to log in!");
    		return false;
    	}
    }

    public ResultSet exists(String selection, String table, String condition) throws SQLException{
    	st = conn.createStatement();
    	ResultSet res = st.executeQuery("SELECT "+selection+" FROM "+table+" "+condition);
    	if (res.next()){
    		return res;
    	} else {
    		return null;
    	}
    }
    
    public String findUserId(String userTable, String login, String password) throws SQLException{
    	ResultSet res = exists("id_"+(userTable.substring(0, userTable.length()-1)), userTable, " WHERE login='"+login+"' AND password='"+password+"'");
    	if (res != null){
    		return res.getString(1);
    	} else {
    		System.out.print("Error, no ID ");
    		return null;
    	}
    }
    
    public String findUserCountry(String userTable, String login, String password) throws SQLException{
    	ResultSet res = exists("country", userTable, " WHERE login='"+login+"' AND password='"+password+"'");
    	if (res != null){
    		return res.getString(1);
    	} else {
    		System.out.println("Error, no country");
    		return null;
    	}
    }
    
    public void set(String table, String column, String newExpr, String condition) throws SQLException{
    	st = conn.createStatement();
    	st.executeUpdate("UPDATE "+table+" SET "+column+"="+newExpr+" "+condition);
    }
    
    public void setClient(String column, String newValue, String id) throws SQLException{
    	this.set("clients", column, newValue, " WHERE id_client='"+id+"'");
    }
    
    public void setPackerFired(String id) throws SQLException{
    	this.set("packers", "fired", "true", " WHERE id_packer='"+id+"'");
    }
    
    public void setDelivererFired(String id) throws SQLException{
    	this.set("deliverers", "fired", "true", " WHERE id_deliverer='"+id+"'");
    }
    
    public void setProduct(String column, String newValue, String condition) throws SQLException{
    	this.set("products", column, newValue, condition);
    }

    public void setPackage(String column, String newExpr, String id) throws SQLException{
    	this.set(" packages ", column, newExpr, " WHERE id_package='"+id+"' ");
    }

    public void setContainer(String column, String newExpr, String id) throws SQLException{
    	this.set(" containers ", column, newExpr, " WHERE id_container='"+id+"' ");
    }
    
    public void setPallet(String column, String newExpr, String id) throws SQLException{
    	this.set(" pallets ", column, newExpr, " WHERE id_pallet='"+id+"' ");
    }
    
    //DeliversMenu case 3
    public void setDeliverStatut(int choice, String package_id, String deliverID) throws SQLException{
        switch(choice){
            case 1 :
                String req = " UPDATE packages SET statut='DELIVERING'  WHERE id_package='" + package_id + "'" ;
                st = conn.createStatement();
                st.executeUpdate(req);
                System.out.println("package " + package_id + " is now set to \"Delivering...\" " );
                break;
            case 2 : 
                req = " UPDATE packages SET statut='DELIVERED', id_deliverer='" + deliverID+"',sent_date='"+date() +"'  WHERE id_package='" + package_id + "'" ;
                st = conn.createStatement();
                st.executeUpdate(req);
                System.out.println("package " + package_id + " is delivered by " + deliverID + " on " + date().toString() );
                break;
            case 0 :
                System.out.println("Going back to menu");
                break;

            default :
                System.out.println("ERROR!");                       
                break;
        }
        update.executeUpdate();
    }

    //PackersMenu case 4
    public void setPackerStart(int choice, String package_id, String packerID, Date date) throws SQLException{
        update = conn.prepareStatement(" UPDATE packages SET id_packer=?, start_date=? WHERE id_package='" + package_id + "'" );
        switch(choice){
            case 1 :
                update.setString(1,packerID); 
                update.setDate(2,date); 
                System.out.println("Package " + package_id + " will be packed by " + packerID + " on starting date " + date);

                break;
            case 2 :
                System.out.println("Going back to menu");
                break;

            default :
                System.out.println("ERROR!");                       
                break;
        }
        update.executeUpdate();
    }
    
     //PackersMenu case 5
    public void setPackerEnd(int choice, String package_id, Date date) throws SQLException{
        update = conn.prepareStatement(" UPDATE packages SET packed_date=?, statut=? WHERE id_package='" + package_id + "'" );
        switch(choice){
            case 1 :
                update.setDate(1,date); 
                update.setString(2,"READY"); 
                System.out.println("Package " + package_id + " is packed on " + date + " and is now \"Ready to be delivered\" " );

                break;
            case 2 :
                System.out.println("Going back to menu");
                break;

            default :
                System.out.println("ERROR!");                       
                break;
        }
        update.executeUpdate();
    }

    public void updateDate(String package_id,Date d) throws SQLException{
        update = conn.prepareStatement(" UPDATE packages SET customs_date=? WHERE id_package=?" );
        update.setDate(1,d);
        update.setString(2,package_id); 
    	update.executeUpdate();
    }
    
    public void updateDelay(String package_id) throws SQLException{
    	st = conn.createStatement();
    	st.executeUpdate("UPDATE packages SET delay=7 WHERE id_package='"+package_id+"'");
    }

    public void sendPackageBack(String packID) throws SQLException{
    	this.setPackage(" statut ", " 'NOT PACKED' ", packID);
    	this.setPackage(" customs_date ", "'"+date()+"'", packID);
    	this.setPackage(" delay ", " 7 ", packID);
 /*   	this.setPackage(" id_pallet ", " null ", packID);
    	this.setPackage(" id_container ", " null ", packID);
*/    
    }
    
    public ResultSet printProducts(String condition) throws SQLException{
		st = conn.createStatement();
		return st.executeQuery("SELECT * FROM products "+condition);
    }
    
    public ResultSet printClients(String condition) throws SQLException{
    	st = conn.createStatement();
    	return st.executeQuery("SELECT * FROM clients "+condition);
    }
    
    public ResultSet printPackers(String condition) throws SQLException{
		st = conn.createStatement( );
		return st.executeQuery("SELECT * FROM packers "+condition);
    }
    
    public ResultSet printDeliverers(String condition) throws SQLException{
		st = conn.createStatement();
		return st.executeQuery("SELECT * FROM deliverers "+condition);
    }
    
    //PackersMenu case 1 && 2

    public ResultSet printOrders(String condition) throws SQLException{
    	st = conn.createStatement();
    	return st.executeQuery("SELECT * FROM orders "+condition);
    }
    
    //DeliverersMenu case1 && 2   && PackersMenu case3
    public ResultSet printPackages(String selection, String condition) throws SQLException{
        st = conn.createStatement();
        return st.executeQuery(" SELECT "+selection+" FROM packages "+condition);
    }
    
    public ResultSet printContainers(String selection, String condition) throws SQLException{
        st = conn.createStatement();
        return st.executeQuery(" SELECT "+selection+" FROM containers "+condition);
    }
    
    public ResultSet printPallets(String selection, String condition) throws SQLException{
        st = conn.createStatement();
        return st.executeQuery(" SELECT "+selection+" FROM pallets "+condition);
    }

    public Date date(){
        Calendar c = Calendar.getInstance();
        Date f = new Date(c.getTime().getTime());
        return f;
    }
    
    public void begin() throws SQLException{
    	st = conn.createStatement();
    	st.executeUpdate("BEGIN;");
    }
    
    public void commit() throws SQLException{
    	st = conn.createStatement();
    	st.executeUpdate("COMMIT;");
    }
  
    public String randomId(String table_) throws SQLException{
		st = conn.createStatement();
		String req=("SELECT id_"+table_ +" FROM "+table_+"s ORDER BY RANDOM() LIMIT 1 ");
		ResultSet rs = st.executeQuery(req);
		if (rs.next()) {
			return rs.getString(1);
		} else { 
			return "";
		}
	}
    
    // get country from clients tables
    public String getCountry(String client_id) throws SQLException{
    	st = conn.createStatement();
    	ResultSet rs = st.executeQuery("SELECT country FROM clients WHERE id_client='"+client_id+"'");
		if (rs.next()) {
			return rs.getString(1);
		} else { 
			return "";
		}
    }
    
    public int getCount(String selection, String table, String condition) throws SQLException{
    	st = conn.createStatement();
    	ResultSet res = st.executeQuery("SELECT COUNT("+selection+") FROM "+table+" "+condition);
    	if ( res.next() ){
    		return res.getInt(1);
    	} else {
    		return 0;
    	}
    }
    
    // get numbers of same country from clients table
    public int getCountPackagesCountry(String country_) throws SQLException{
    	st = conn.createStatement();
    	ResultSet rs = st.executeQuery("SELECT COUNT(country) FROM clients,packages WHERE packages.id_client=clients.id_client AND statut='DELIVERED' AND country='"+country_+"'");
		if (rs.next()) {
			return rs.getInt(1);
		} else { 
			return 0;
		}
    }
    
     // get customs rate from a country
    public int getCustomRate(String country_) throws SQLException{
    	st = conn.createStatement();
    	ResultSet rs = st.executeQuery("SELECT checked_packages_rate FROM customs WHERE country='"+country_+"'");
		if (rs.next()) {
			return rs.getInt(1);
		} else { 
			return 0;
		}
    }
    
    public ResultSet getXRandomPackages(String country_, int nb_package) throws SQLException{
		st = conn.createStatement();
		String req=("SELECT * FROM packages,clients WHERE packages.id_client=clients.id_client AND statut='DELIVERED' AND country= '"+country_+"' ORDER BY RANDOM() LIMIT "+ nb_package );
		ResultSet rs = st.executeQuery(req);
		return rs;
    }

    
    public int getErrorRate(String packer_id) throws SQLException{
    	st = conn.createStatement();
    	ResultSet rs = st.executeQuery("SELECT error_rate FROM packers WHERE id_packer='"+packer_id+"'");
		if (rs.next()) {
			return rs.getInt("error_rate");
		} else { 
			return 0;
		}
    }
    
    public int getCountPackagesPacker(String packer_id) throws SQLException{
    	st = conn.createStatement();
    	ResultSet rs = st.executeQuery("SELECT COUNT(id_packer) FROM packages WHERE customs_date IS NOT NULL AND id_packer='"+packer_id+"'");
		if (rs.next()) {
			return rs.getInt(1);
		} else { 
			return 0;
		}
    }
    
    
}
