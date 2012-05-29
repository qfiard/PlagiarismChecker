import java.io.IOException;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;


public class ClientsMenu{

	String accountType = "client";
	ConnectionExport connecte;
	Scanner in = new Scanner(System.in);
	String clientID;
	

    public ClientsMenu(ConnectionExport c){
    	connecte = c;
    }
    
    // ----------------
    // Read a string
    // ----------------
    public String readString(){
    	try{
    	    return in.next();
    	}
    	catch(Exception e){
    		System.out.println("You may have done a mistake, we can't read your entry properly. (There was no string type in there)");
    		System.out.print("Try again please: ");
    		return readString();
    	}
    }

    // ----------------
    // Read an int
    // ----------------
    public int readInt(){
		try{
		    return in.nextInt();   // Read user's choice 
		}
		catch(Exception e){
		    in.nextLine();
		    System.out.println("You may have done a mistake, we can't read your entry properly. (There was no int type in there)");
		    System.out.print("Try again please: ");
		    return readInt();
		}
    }
    
    //-----------------
    // Print a string
    //-----------------
    public void print(String s, int i) {
    	System.out.print(s);
        for (i -= s.length(); i >= 0; i --)
        System.out.print(" ");
    }

    
    public void pressReturn(int choice) throws IOException{
	    if (choice != 0){
			System.out.println("\nPress Return key.");
			System.in.read();
		}    	
    }
    
    // ----------------
    // Connect to account type
    // ----------------
    public boolean connection(String userType) throws SQLException{
    	Boolean result = false;
    	System.out.print("\033c");   // Clear screen
    	System.out.println("Enter your " + accountType + " login:");
    	String login = readString();
    	System.out.println("Enter your " + accountType + " password:");
    	String password = readString();

			result = connecte.connection(userType, login, password);
			this.clientID = connecte.findUserId(userType, login, password);

		return result;
    }
    
    public String createRandomId(String table) throws SQLException{
    	ResultSet exist;
    	String result;
    	int n;
    	Random r = new Random();
    	do {
    		result = "";
	    	for (int i=0; i<10; i++){
	    		 n = r.nextInt(36);
	    		result += (String.valueOf(Character.forDigit(n,36))).toUpperCase();
	    	}
	    	exist = connecte.exists(" * ", table, " WHERE id_"+table.substring(0, table.length()-1)+"='"+result+"'");
    	} while ( exist!=null && exist.next());
    	return result;
    }
    
    public String getPackageId(char qualifiant) throws SQLException{
    	String pack_id = createRandomId("packages");
    	connecte.addPackage(pack_id, clientID, qualifiant, "NOT PACKED");
    	return pack_id;
    }
    
    public int makeOrder() throws SQLException{
		System.out.println("\n\n\n");
    	System.out.println(" Do you want to make another order ? ");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Yes, it would be lovely.");
		System.out.println("0 - No, thanks, back to my menu.");
		System.out.println("-------------------------------------------------------------");
		int choice=readInt();
		switch(choice){
		case 1:
			System.out.print("Enter the id of your future product: ");
			String id_prod = readString();
			connecte.begin();
			ResultSet product = connecte.printProducts(" WHERE id_product='"+id_prod+"' FOR UPDATE");
			product.next(); 
			System.out.print("Enter the quantity desired: ");
			int quantity = readInt();
			int stock = product.getInt("stock");
			while ( quantity > stock ){
				product = connecte.printProducts(" WHERE id_product='"+id_prod+"'");
				product.next();
				stock = product.getInt("stock");
				System.out.println("Someone may have order it or you may have seen the wrong line, in stock : " + stock);
				System.out.print("We don't have enough to satisfy, please enter a new amount : ");
				quantity = readInt();
			}
			if (stock > 0){
				String package_id = this.getPackageId((product.getString("qualifiant")).toCharArray()[0]);
				int price = (quantity * product.getInt("price"));
				String order_id = this.createRandomId("orders");
				connecte.addOrder(order_id, id_prod,quantity,clientID, price, package_id);
				connecte.setProduct(" stock ", " stock - "+quantity+" ", " WHERE id_product='"+id_prod+"' ");
				connecte.setProduct(" sales_num ", " sales_num + "+quantity+" ", " WHERE id_product='"+id_prod+"' ");
				connecte.setClient(" orders_amount ", " orders_amount + 1", clientID);
			} else {
				System.out.println("Sorry there is no more stock");
			}
			connecte.commit();
			System.out.println("Order done!\n");
			break;
		case 0:
			break;
		default:
			System.out.println("\nError, please enter one of the number in the list");
		}
		return choice;
    }
    
    public int printProducts(ResultSet res) throws SQLException{
    	if (res!=null && res.next()){
	    	print("ID PRODUCT", 17);
	    	print("DESCRIPTION", 85);
	    	print("PRICE", 15);
	    	print("WEIGHT", 10);
	    	print("STOCK", 10); 
	    	print("QUALIFIANT", 10);
	    	System.out.println();
	    	do{
	    		print(String.valueOf(res.getString("id_product")), 17);
	    		print(String.valueOf(res.getString("product_description")), 85);
	    		print(String.valueOf(res.getInt("price")), 15);
	    		print(String.valueOf(res.getInt("weight")), 10);
	    		print(String.valueOf(res.getInt("stock")), 10);
	    		print(String.valueOf(res.getString("qualifiant")),10);
	    		System.out.println();
	    	}while (res != null && res.next());
		    return makeOrder();
    	} else {
    		System.out.println("There is no product");
    		return 0;
    	}
    }
    
    public void printOrders(ResultSet res) throws SQLException{
    	if (res != null && res.next()){
	    	print("ID ORDER", 15);
	    	print("ID PRODUCT", 17);
	    	print("PRODUCT DESCRIPTION", 60);
	    	print("QUANTITY", 10); 
	    	print("TOTAL PRICE", 15);
	    	print("STATUT", 15);
	    	System.out.println();
	    	do {
	    		print(String.valueOf(res.getString("id_order")), 15);
	    		print(String.valueOf(res.getString("id_product")), 17);
	    		print(String.valueOf(res.getString("product_description")), 60);
	    		print(String.valueOf(res.getInt("quantity")), 10);
	    		print(String.valueOf(res.getInt("price")), 15);
	    		print(String.valueOf(res.getString("statut")), 15);
	    		System.out.println();
	    	}while (res != null && res.next());
    	} else {
    		System.out.println("Sorry, you don't have any orders. Check our products to make one!");
    	}
    }
    
    public int changeData(){
    	System.out.println("\n\n\n");
    	System.out.println(" Do you want to change one of the information ? ");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Phone number");
		System.out.println("2 - Login");
		System.out.println("3 - Password");
		System.out.println("0 - No, thanks, back to my menu.");
		System.out.println("-------------------------------------------------------------");
		int choice=readInt();
		try{
			switch(choice){
			case 1:
				System.out.print("Enter the new phone number: ");
				connecte.setClient("phone_num", "'"+String.valueOf(readInt())+"'", clientID);
				System.out.println("Update done!");
				break;
			case 2:
				System.out.print("Enter the new login: ");
				connecte.setClient("login", "'"+readString()+"'", clientID);
				System.out.println("Update done!");
				break;
			case 3:
				System.out.print("Enter the new password: ");
				connecte.setClient("password", "'"+readString()+"'", clientID);
				System.out.println("Update done!");
				break;
			case 0:
				break;
			default:
				System.out.println("\nError, please enter one of the number in the list");
			}
		} catch (SQLException e){
			System.out.println(e.toString());
		}
		return choice;
    }
    
    public int printData(ResultSet res) throws SQLException{
    	System.out.print("\033c");
    	if ( res!=null && res.next()){
	    	print(" ID CLIENT : ", 20);
	    	print(String.valueOf(res.getString("id_client")), 40);
	    	print("\n PHONE NUMBER : ", 20);
	    	print(String.valueOf(res.getString("phone_num")), 40);
	    	print("\n COMPANY : ", 20);
	    	print(String.valueOf(res.getString("company")), 40);
	    	print("\n SUFFIXE : ", 20);
	    	print(String.valueOf(res.getString("suffixe")), 40);
	    	print("\n ADDRESS : ", 20);
	    	print(String.valueOf(res.getString("address")), 40);
	    	print("\n POSTAL CODE : ", 20);
	    	print(String.valueOf(res.getString("postal_code")), 40);
	    	print("\n CITY : ", 20);
	    	print(String.valueOf(res.getString("city")), 40);
	    	print("\n COUNTRY : ", 20);
	    	print(String.valueOf(res.getString("country")), 40);
	    	print("\n LOGIN : ", 20);
	    	print(String.valueOf(res.getString("login")), 40);
	    	print("\n PASSWORD : ", 20);
	    	print(String.valueOf(res.getString("password")), 40);
	    	print("\n TOTAL ORDERS : ", 20);
	    	print(String.valueOf(res.getInt("orders_amount")), 40);
	    	System.out.println();
	    	return changeData();
    	} else {
    		System.out.println("There is no data");
    		return 0;
    	}
    }
    
    /** Imprime le menu a l'ecran.
	 * @throws IOException */
	public int printMenu() throws IOException {
		int choice = -1; // user's choice	
		System.out.print("\033c"); //clean screen
		// -------------------
		// Print client's menu
		// -------------------
		System.out.println("\n Your client ID: " +clientID);
		System.out.println("\n Choose your action :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Check the products");
		System.out.println("2 - Follow my orders");
		System.out.println("3 - Show my personal data");
		System.out.println("0 - End");
		System.out.println("-------------------------------------------------------------");
		// ----------------------------
		// Read client's choice
		// ----------------------------
		choice = readInt();	
		System.out.print("\033c"); //clean the screen
		// -------------------------------
		// Execute client's choice
		// -------------------------------
		try {
			int cpt =-1;
			ResultSet contenu=null;
			switch(choice){
			case 1 : 
				while (cpt != 0){
					System.out.print("\033c");
				    System.out.println("In stock products :\n");
				    contenu = connecte.printProducts(" WHERE stock>0");
				    cpt = printProducts(contenu);
				    pressReturn(cpt);
				}
			    break;
			case 2 :
			    System.out.println("Your orders :\n");
			    contenu = connecte.printOrders(" NATURAL JOIN packages, products WHERE orders.id_product=products.id_product  AND orders.id_client='"+clientID+"'");
			    printOrders(contenu);
			    break;
			case 3 :
				while (cpt != 0){
				    System.out.println("Your personal data :");	
				    contenu = connecte.printClients(" WHERE id_client='"+clientID+"'");
				    cpt = printData(contenu);
				    pressReturn(cpt);
				}
			    break;			    
			case 0 : 
			    System.out.println("\nEnd of your client connection");
			    break;
			    
			default : 
			    System.out.println("\nERROR! Please enter one of the number in the list");
			}
		    }
		catch (SQLException e) {
		    System.err.println(e.getMessage());
		}
		return choice;
	}

}
