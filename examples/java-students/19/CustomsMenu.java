import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class CustomsMenu{

	String accountType = "customs";
	ConnectionExport connecte;
	Scanner in = new Scanner(System.in);
	String customCountry;
	

    public CustomsMenu(ConnectionExport c){
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
    	boolean result=false;
    	System.out.print("\033c");   // Clean screen
    	System.out.println("Enter your " + accountType + " login:");
    	String login = readString();
    	System.out.println("Enter your " + accountType + " password:");
    	String password = readString();
			result = connecte.connection(userType, login, password);
			this.customCountry = connecte.findUserCountry(userType, login, password);
		return result;
    }
    
    /**
     * Send an order back
     * @param res
     * @return
     * @throws Exception
     */
    public int sendBack(ResultSet res) throws Exception{
    	int choice =-1;
    	System.out.println("\n\n\n Choose your action :");
		System.out.println("-------------------------------------------------------------");
    	System.out.println("1 - Send an order back");
    	System.out.println("0 - Back to my menu");
    	choice = readInt();
    	switch(choice){
    	case 1:
    		System.out.println("Which orders would you like to send back ? (Enter id_order)");
    		String orderID = readString();
    		ResultSet exist = connecte.exists(" * ", " orders NATURAL JOIN packages ", " WHERE id_order='"+orderID+"' AND statut='DELIVERING' ");
	    	if (exist==null ){
	    		System.out.print("Sorry, it seems the id doesn't exist or has already been delivered or isn't delivering yet. \nEnter another id : ");
	    	} else {
		    	String packID = exist.getString("id_package");
				connecte.sendPackageBack(packID);
				System.out.println("Sent back !");
	    	}
	    	pressReturn(choice);
    		break;
    	case 0:
    		break;
    	default:
    		System.out.println("\nError, please enter one of the number in the list");
    	}
    	return choice;
    }

    /**
     * Send orders from containers back
     * @param packID
     * @return
     * @throws Exception
     */
    public int sendBackFromContainer(String packID) throws Exception{
    	int choice = -1;
    	System.out.println("\n\n\n Choose your action :");
		System.out.println("-------------------------------------------------------------");
    	System.out.println("1 - Send an order back");
		System.out.println("2 - Print all the orders to my country");
		System.out.println("3 - Back to my containers");
		System.out.println("4 - Back to my pallets");
		System.out.println("5 - Back to my packages");
    	System.out.println("0 - Back to my menu");
    	choice = readInt();
    	switch(choice){
    	case 1:
    		System.out.println("Which orders would you like to send back ? (Enter id_order)");
    		String orderID =readString();
    		ResultSet exist = connecte.exists(" * ", " orders NATURAL JOIN packages ", " WHERE id_order='"+orderID+"' AND statut='DELIVERING' ");
	    	if(exist==null){
	    		System.out.print("Sorry, it seems the id doesn't exist or has already been delivered or isn't delivering yet.");
	    	} else {
	    		connecte.sendPackageBack(packID);
	    		System.out.println("Sent back");
	    	}
			pressReturn(choice);
    		break;
    	case 2:
			while (choice != 0){
				choice = printAllOrders();
			}
    	case 3:
    		break;
    	case 4:
    		break;
    	case 5:
    		break;
    	case 0:
    		break;
    	default:
    		System.out.println("\nError, please enter one of the number in the list");
    	}
    	return choice;
    }
    
    /**
     * Propose actions on packages
     * @return
     * @throws Exception
     */
    public int choicePackageContents(String palletID) throws Exception{
    	int choice = -1;
    	System.out.println("\n\n\n Choose your action :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Print a specific package and it contents");
		System.out.println("2 - Print all the orders to my country");
		System.out.println("3 - Back to my containers");
		System.out.println("4 - Back to my pallets");
		System.out.println("0 - Back to my menu");
		choice = readInt();
    	switch(choice){
    	case 1:
			System.out.println("Which package would you like to print ? (Enter id_package)");
			String packageID = readString();
			ResultSet exist = connecte.exists(" * ", " packages NATURAL JOIN clients ", " WHERE id_package='"+packageID+"' AND country='"+customCountry+"' AND id_pallet='"+palletID+"' ");
			if ( exist == null ){
				System.out.print("Sorry, the ID isn't available");
				pressReturn(choice);
			} else {
				while ((choice != 0) && (choice != 3) && (choice != 4) && (choice != 5)){
					choice = printOrdersFromPackage(packageID);
				}
			}
    		break;
    	case 2:
			while (choice != 0){
				choice = printAllOrders();
			}
    	case 3:
    		break;
    	case 0:
    		break;
    	default:
    		System.out.println("\nError, please enter one of the number in the list");
    	}
    	return choice;
    }
    
    /**
     * Propose actions on pallets
     * @param containerID
     * @return
     * @throws Exception
     */
    public int choicePalletContents(String containerID) throws Exception{
    	int choice = -1;
    	System.out.println("\n\n\n Choose your action :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Print a specific pallet and it contents");
		System.out.println("2 - Print all the orders to my country");
		System.out.println("3 - Back to my containers");
		System.out.println("0 - Back to my menu");
		choice = readInt();
    	switch(choice){
    	case 1:
			System.out.println("Which package would you like to print ? (Enter id_package)");
			String palletID = readString();
			ResultSet exist = connecte.exists(" * ", " pallets NATURAL JOIN clients ", " WHERE id_pallet='"+palletID+"' AND country='"+customCountry+"' AND id_container='"+containerID+"' ");
			if ( exist == null ){
				System.out.print("Sorry, the ID isn't available");
				pressReturn(choice);
			} else {
				while ((choice != 3) && (choice != 0) && (choice != 4)){
					choice = printPackages(palletID);
				}
			}
    		break;
    	case 2:
			while (choice != 0){
				choice = printAllOrders();
			}
    	case 3:
    		break;
    	case 0:
    		break;
    	default:
    		System.out.println("\nError, please enter one of the number in the list");
    	}
    	return choice;
    }
    
    /**
     * Propose actions on containers
     * @return
     * @throws Exception
     */
    public int choiceContainerContents() throws Exception{
    	int choice = -1;
    	System.out.println("\n\n\n Choose your action :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Print a specific container and it contents");
		System.out.println("2 - Print the orders to my country");
		System.out.println("0 - Back to my menu");
		choice = readInt();
		switch (choice){
		case 1:
			System.out.println("Which container would you like to print ? (Enter id_container)");
			String containerID = readString();
			ResultSet exist = connecte.exists(" * ", " containers NATURAL JOIN clients ", " WHERE id_container='"+containerID+"' AND country='"+customCountry+"' ");
			if ( exist == null ){
				System.out.print("Sorry, the ID isn't available");
				pressReturn(choice);
			} else {
				while ((choice != 0) && (choice != 3)){
					choice = printPallets(containerID);
				}
			}
			break;
		case 2:
			while (choice != 0){
				choice = printAllOrders();
			}
		case 0:
			break;
		default:
			System.out.println("\nError, please enter one of the number in the list");
		}
		return choice;
    }
    
    /**
     * Print orders
     * @param res
     * @throws Exception
     */
    public void printOrder(ResultSet res) throws Exception{
    	if (res!=null && res.next()){
	    	print("\n ID ORDER : ", 40);
    		print(String.valueOf(res.getString("id_order")),40);
    		print("\n STATUT : ", 40);
    		print(String.valueOf(res.getString("statut")), 40);
	    	print("\n ID PRODUCT : ", 40);
    		print(String.valueOf(res.getString("id_product")), 40);
	    	print("\n PRODUCT DESCRIPTION : ", 40);
    		print(String.valueOf(res.getString("product_description")), 40);
	    	print("\n PRODUCT WEIGHT : ", 40);
    		print(String.valueOf(res.getInt("weight")),40);
	    	print("\n QUANTITY : ", 40); 
    		print(String.valueOf(res.getInt("quantity")), 40);
	    	print("\n TOTAL PRICE : ", 40);
    		print(String.valueOf(res.getInt("price")), 40);
	    	print("\n QUALIFIANT : ", 40);
    		print(String.valueOf(res.getString("qualifiant")), 40);
    		System.out.println();
    	} else {
    		System.out.println("Sorry there is no order with this ID");
    	}
    	
    }
    
    /**
     * Print orders for a specified product
     * @param res
     * @throws Exception
     */
    public void printOrdersFromProduct(ResultSet res) throws Exception{
    	if (res!=null && res.next()){
    		print("ID ORDER", 15);
    		print("STATUT", 10);
    		print("ID PRODUCT", 15);
    		print("PRODUCT DESCRIPTION", 40);
	    	print("PRODUCT WEIGHT", 15);
	    	print("QUANTITY", 10); 
	    	print("TOTAL PRICE", 15);
	    	print("QUALIFIANT", 10);
	    	System.out.println();
	    	do{
	    		print(String.valueOf(res.getString("id_order")),15);
	    		print(String.valueOf(res.getString("statut")), 10);
	    		print(String.valueOf(res.getString("id_product")), 15);
	    		print(String.valueOf(res.getString("product_description")), 40);
	    		print(String.valueOf(res.getInt("weight")),15);
	    		print(String.valueOf(res.getInt("quantity")), 10);
	    		print(String.valueOf(res.getInt("price")), 15);
	    		print(String.valueOf(res.getString("qualifiant")), 10);
	    		System.out.println();
	    	}while (res != null && res.next());
    	} else {
    		System.out.println("Sorry this product hasn't been ordered to your country");
    	}
    }
    
    /**
     * Print all orders
     * @return
     * @throws Exception
     */
    public int printAllOrders() throws Exception{
    	System.out.print("\033c");
    	System.out.println("Orders destinated to your country :");
    	String query = ",packages,products,clients ";
    	query += " WHERE orders.id_product=products.id_product AND orders.id_client=packages.id_client AND packages.id_client=clients.id_client AND orders.id_package=packages.id_package ";
    	query += " AND clients.country='"+customCountry+"' ";
    	ResultSet res = connecte.printOrders(query);
	    if ( res!=null && res.next()){
	    	print("ID ORDER", 13);
	    	print("ID PRODUCT", 18);
	    	print("PRODUCT DESCRIPTION", 85);
	    	print("WEIGHT", 8);
	    	print("QUANTITY", 8); 
	    	print("PRICE", 8);
	    	print("QUAL", 8);
	    	print("STATUT", 10);
	    	print("CHECKED DATE", 15);
	    	System.out.println();
	    	do{
	    		print(String.valueOf(res.getString("id_order")),13);
	    		print(String.valueOf(res.getString("id_product")), 18);
	    		print(String.valueOf(res.getString("product_description")), 85);
	    		print(String.valueOf(res.getInt("weight")),8);
	    		print(String.valueOf(res.getInt("quantity")), 8);
	    		print(String.valueOf(res.getInt("price")), 8);
	    		print(String.valueOf(res.getString("qualifiant")), 8);
	    		print(String.valueOf(res.getString("statut")), 10);
	    		print(String.valueOf(res.getDate("customs_date")), 15);
	    		System.out.println();
	    	} while (res!=null && res.next());
	    	return sendBack(res);
	    } else {
	    	System.out.println("Sorry there is no order");
	    	return 0;
	    }
    }
    
    /**
     * Print all orders delivered but unchecked
     * @param res
     * @throws Exception
     */
    public void printDeliveredNotCheckedOrders(ResultSet res) throws Exception{
    	if (res!=null && res.next()){
	    	print("ID ORDER", 13);
	    	print("ID PRODUCT", 18);
	    	print("PRODUCT DESCRIPTION", 85);
	    	print("WEIGHT", 8);
	    	print("QUANTITY", 8); 
	    	print("PRICE", 8);
	    	print("QUAL", 8);
	    	System.out.println();
	    	do{
	    		print(String.valueOf(res.getString("id_order")),13);
	    		print(String.valueOf(res.getString("id_product")), 18);
	    		print(String.valueOf(res.getString("product_description")), 85);
	    		print(String.valueOf(res.getInt("weight")),8);
	    		print(String.valueOf(res.getInt("quantity")), 8);
	    		print(String.valueOf(res.getInt("price")), 8);
	    		print(String.valueOf(res.getString("qualifiant")), 8);
	    		System.out.println();
	    	}while (res != null && res.next());
    	} else {
    		System.out.println("Sorry there is no order not checked");
    	}
    }
    
    /**
     * Print all orders delivered and checked
     * @param res
     * @throws Exception
     */
    public void printDeliveredCheckedOrders(ResultSet res) throws Exception{
    	if (res!=null && res.next()){
	    	print("ID ORDER", 13);
	    	print("ID PRODUCT", 18);
	    	print("PRODUCT DESCRIPTION", 85);
	    	print("WEIGHT", 8);
	    	print("QUANTITY", 8); 
	    	print("PRICE", 8);
	    	print("QUAL", 8);
	    	print("CHECKED DATE", 15);
	    	System.out.println();
	    	do{
	    		print(String.valueOf(res.getString("id_order")),13);
	    		print(String.valueOf(res.getString("id_product")), 18);
	    		print(String.valueOf(res.getString("product_description")), 85);
	    		print(String.valueOf(res.getInt("weight")),8);
	    		print(String.valueOf(res.getInt("quantity")), 8);
	    		print(String.valueOf(res.getInt("price")), 8);
	    		print(String.valueOf(res.getString("qualifiant")), 8);
	    		print(String.valueOf(res.getDate("customs_date")), 15);
	    		System.out.println();
	    	}while (res != null && res.next());
    	} else {
    		System.out.println("Sorry there is no checked order");
    	}
    }
    
    /**
     * Print orders from the specified package
     * @param packageID
     * @return
     * @throws Exception
     */
    public int printOrdersFromPackage(String packageID) throws Exception{
    	System.out.print("\033c");
    	System.out.print("Orders destinated to your country ");
    	String query =",products,packages ";
    	query += " WHERE orders.id_product=products.id_product AND orders.id_package=packages.id_package AND orders.id_client=packages.id_client ";
    	if (!packageID.equals("")){
    		query += " AND orders.id_package='"+packageID+"' ";
    		System.out.println("from package #"+packageID);
    	} else {
    		System.out.println();
    	}
    	ResultSet res = connecte.printOrders(query);
    	if (res!=null && res.next() ){
	    	print("ID ORDER", 13);
	    	print("STATUT", 10);
	    	print("ID PRODUCT", 18);
	    	print("PRODUCT DESCRIPTION", 85);
	    	print("WEIGHT", 8);
	    	print("QUANTITY", 8); 
	    	print("PRICE", 8);
	    	print("QUALIFIANT", 8);
	    	System.out.println();
	    	do{
	    		print(String.valueOf(res.getString("id_order")),13);
	    		print(String.valueOf(res.getString("statut")), 10);
	    		print(String.valueOf(res.getString("id_product")), 18);
	    		print(String.valueOf(res.getString("product_description")), 85);
	    		print(String.valueOf(res.getInt("weight")),8);
	    		print(String.valueOf(res.getInt("quantity")), 8);
	    		print(String.valueOf(res.getInt("price")), 8);
	    		print(String.valueOf(res.getString("qualifiant")), 8);
	    		System.out.println();
	    	}while (res != null && res.next());
	    	return sendBackFromContainer(packageID);
    	} else {
    		System.out.println("There is no order in your package \n");
    		return 4;
    	}
    }
    
    /**
     * Print packages from the specified container
     * @param containerID
     * @return
     * @throws Exception
     */
    public int printPackages(String palletID) throws Exception{
    	ResultSet res = connecte.printPackages("*", " WHERE id_pallet='"+palletID+"'");
    	System.out.print("\033c");
    	System.out.println("Packages in pallet number "+palletID+" :\n");
    	if (res!=null && res.next()){
	    	print("ID PACKAGE", 15);
	    	print("QUALIFIANT", 10);
	    	System.out.println();
	    	do{
	    		print(String.valueOf(res.getString("id_package")),15);
	    		print(String.valueOf(res.getString("qualifiant")), 10);
	    		System.out.println();
	    	}while (res != null && res.next());
	    	return choicePackageContents(palletID);
    	} else {
    		System.out.println("Your container is empty \n");
    		return 3;
    	}
    }

    public int printPallets(String containerID) throws Exception{
    	ResultSet res = connecte.printPallets("*", " WHERE id_container='"+containerID+"'");
    	System.out.print("\033c");
    	System.out.println("Pallets in container number "+containerID+" :\n");
    	if (res!=null && res.next()){
	    	print("ID PALLET", 15);
	    	print("Nb PACKAGES", 10);
	    	System.out.println();
	    	do{
	    		print(String.valueOf(res.getString("id_pallet")),15);
	    		print(String.valueOf(res.getString("nb_packages")), 10);
	    		System.out.println();
	    	}while (res != null && res.next());
	    	return choicePalletContents(containerID);
    	} else {
    		System.out.println("Your container is empty \n");
    		return 3;
    	}
    }
    
    /**
     * Print containers
     * @param res
     * @return
     * @throws Exception
     */
    public int printContainers(ResultSet res) throws Exception{
    	System.out.print("\033c");
		System.out.println("Containers to your country:\n");
		if (res!=null && res.next()){
	    	print("ID CONTAINER", 15);
	    	print("Nb PALLETS", 15);
	    	System.out.println();
	    	do{
	    		print(String.valueOf(res.getString("id_container")), 15);
	    		print(String.valueOf(res.getInt("nb_pallets")), 15);
	    		System.out.println();
	    	}while(res!=null & res.next());
	    	return choiceContainerContents();
		} else {
			System.out.println("We have no container in destination to "+customCountry+"\n");
			return 0;
		}
    }
    
    /**
     * Print customs' main menu while user hasn't decide to end the session
     * @return
     * @throws Exception
     */
    public int printMenu() throws Exception {
		int choice = -1; // user's choice
	
		System.out.print("\033c"); //clean screen
		
		// -------------------
		// Print Custom's menu
		// -------------------
		System.out.println(" What do you want to do? :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Print containers destinated to your country");
		System.out.println("2 - Print all orders destinated to your country");
		System.out.println("3 - Print orders destinated to your country and already checked");
		System.out.println("4 - Print orders destinated to your country, sent but not not checked");
		System.out.println("5 - Find order using its ID");
		System.out.println("6 - Find order(s) using the product ordered");
		System.out.println("0 - End");
		System.out.println("-------------------------------------------------------------");
	
		// ----------------------------
		// Read customs' choice
		// ----------------------------
		
		choice = readInt();
		
		System.out.print("\033c"); //clean screen
		
		// -------------------------------
		// Execute customs' choice
		// -------------------------------
		
		try {
			int cpt = -1;
			String query;
			ResultSet contenu;
			switch(choice){
			case 1 :
				while (cpt!=0){
					contenu = connecte.printContainers(" * "," NATURAL JOIN clients WHERE country='"+customCountry+"'");
					cpt=printContainers(contenu);
					pressReturn(cpt);
				}
			    break;

			case 2 :
				while (cpt != 0){
					cpt = printAllOrders();
					pressReturn(cpt);
				}
			    break;
			case 3 :
		    	System.out.print("\033c");
		    	System.out.println("Orders delivered to your country and already checked :\n");
		    	query = " ,packages,products,clients ";
		    	query += " WHERE orders.id_product=products.id_product AND orders.id_client=packages.id_client AND packages.id_client=clients.id_client AND orders.id_package=packages.id_package ";
		    	query += " AND clients.country='"+customCountry+"' AND packages.statut='DELIVERED' AND packages.customs_date IS NOT NULL  ";  
		    	contenu = connecte.printOrders(query);
		    	printDeliveredCheckedOrders(contenu);
				break;
			case 4 :
		    	System.out.print("\033c");
		    	System.out.println("Orders delivered to your country and not checked :\n");
		    	query = " ,packages,products,clients ";
		    	query += " WHERE orders.id_product=products.id_product AND orders.id_client=packages.id_client AND packages.id_client=clients.id_client AND orders.id_package=packages.id_package ";
		    	query += " AND clients.country='"+customCountry+"' AND packages.statut='DELIVERED' AND packages.customs_date IS NULL";
		    	contenu = connecte.printOrders(query);
		    	printDeliveredNotCheckedOrders(contenu);
				break;
			case 5:
				System.out.println("Enter the order's id you are looking for :");
				query = " AND orders.id_product=products.id_product AND orders.id_client=packages.id_client AND packages.id_client=clients.id_client AND orders.id_package=packages.id_package ";
				contenu = connecte.printOrders(",products,clients WHERE orders.id_order='"+readString()+"' AND clients.country='"+customCountry+"' "+query);
				printOrder(contenu);
				break;
			case 6:
				System.out.println("Enter the product's id you are looking for : ");
				contenu = connecte.printOrders(",products,clients,packages WHERE orders.id_product='"+readString()+"' AND products.id_product=orders.id_product AND orders.id_client=clients.id_client AND orders.id_client=packages.id_client AND country='"+customCountry+"'");
				printOrdersFromProduct(contenu);
				break;
			case 0 : 
			    System.out.println("\nEnd of your custom connection");
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
