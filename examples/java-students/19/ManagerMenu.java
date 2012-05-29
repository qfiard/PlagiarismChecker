import java.io.IOException;
import java.sql.*;
import java.util.Scanner;


public class ManagerMenu{

	String accountType = "manager";
	ConnectionExport connecte;
	Scanner in = new Scanner(System.in);
	
	
    public ManagerMenu(ConnectionExport c){
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
    public boolean connection(String userType){
    	System.out.print("\033c");   // Clear screen
    	System.out.println("Enter your " + accountType + " login:");
    	String login = readString();
    	System.out.println("Enter your " + accountType + " password:");
    	String password = readString();
    	try {
			return connecte.connection(userType, login, password);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return false;
    }
    
    public int updatePrice(ResultSet res) throws Exception{
    	System.out.println("\n\n\n Do you want to update a product's price?");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Yes");
		System.out.println("0 - No, back to menu");
		System.out.println("-------------------------------------------------------------");
		int choice = readInt();
		switch(choice){
		case 1:
			System.out.println("\n\n\nWhich product would you like to update? (Enter the id)");
			String id = readString();
			ResultSet exist = connecte.exists(" * ", " products ", " WHERE id_product='"+id+"' ");
			while(exist==null){
				System.out.print("Sorry, it seems the id doesn't exists, enter one again : ");
				id = readString();
				exist = connecte.exists(" * ", " products ", " WHERE id_product='"+id+"' ");
			}
			connecte.setProduct(" price ", " price+(price*increase_rate/100) ", " WHERE  id_product='"+id+"'");
			System.out.println("Price updated");
			pressReturn(choice);
			break;
		case 0:
			break;
		default:
			System.out.println("\nError, please enter one of the number in the list");
		}
    	return choice;
    }
    
    public int firePacker(ResultSet res) throws Exception{
    	if (res!=null && res.next()){
		    printPackers(res);
			System.out.println("\n\n\n Do you really want to fire someone? ");
			System.out.println("-------------------------------------------------------------");
			System.out.println("1 - Yes");
			System.out.println("0 - No, go back to menu");
			System.out.println("-------------------------------------------------------------");
			int choice = readInt();
			switch(choice){
			case 1:
		    	System.out.println("\n\n\nWhich packer would you like to fire? (Enter his id)");
		    	String id = readString();
				ResultSet exist = connecte.exists(" * ", " packers ", " WHERE id_packer='"+id+"' ");
		    	while(exist==null){
					System.out.print("Sorry, it seems the id doesn't exists, enter one again : ");
					id = readString();
					exist = connecte.exists(" * ", " packers ", " WHERE id_packer='"+id+"' ");
				}
		    	connecte.setPackerFired(id);
		    	System.out.println("Packer fired");
				pressReturn(choice);
				break;
			case 0:
				break;
			default:
				System.out.println("\nError, please enter one of the number in the list");
			}	
			return choice;

    	} else {
    		System.out.println("There is no packer");
    		return 0;
    	}
    }
    
    public int fireDeliverer(ResultSet res) throws Exception{
    	if (res!=null && res.next()){
    		printDeliverers(res);
			System.out.println("\n\n\n Do you really want to fire someone? ");
			System.out.println("-------------------------------------------------------------");
			System.out.println("1 - Yes");
			System.out.println("0 - No, go back to menu");
			System.out.println("-------------------------------------------------------------");
			int choice = readInt();
			switch(choice){
			case 1:
		    	System.out.println("\n\n\nWhich deliverer would you like to fire? (Enter his id)");
		    	String id = readString();
		    	ResultSet exist = connecte.exists(" * ", " deliverers ", " WHERE id_deliverer='"+id+"' ");
		    	while (exist==null){
		    		System.out.print("Sorry, it seems the id doesn't exists, enter one again : ");
					id = readString();
					exist = connecte.exists(" * ", " deliverers ", " WHERE id_deliverer='"+id+"' ");
		    	}
		    	connecte.setDelivererFired(id);
		    	System.out.println("Deliverer fired");
				pressReturn(choice);
				break;
			case 0:
				break;
			default:
				System.out.println("\nError, please enter one of the number in the list");
			}	
			return choice;
		} else {
    		System.out.println("There is no deliverer");
    		return 0;
    	}
    }
    
    public int firePackerPackages(ResultSet res) throws Exception{
    	if (res!=null && res.next()){
		    printPackages(res);
			System.out.println("\n\n\n Do you really want to fire someone? ");
			System.out.println("-------------------------------------------------------------");
			System.out.println("1 - Yes");
			System.out.println("0 - No, go back to menu");
			System.out.println("-------------------------------------------------------------");
			int choice = readInt();
			switch(choice){
			case 1:
		    	System.out.println("\n\n\nWhich packer would you like to fire? (Enter his id)");
		    	String id = readString();
				ResultSet exist = connecte.exists(" * ", " packers ", " WHERE id_packer='"+id+"' ");
		    	while(exist==null){
					System.out.print("Sorry, it seems the id doesn't exists, enter one again : ");
					id = readString();
					exist = connecte.exists(" * ", " packers ", " WHERE id_packer='"+id+"' ");
				}
		    	if (exist.getBoolean("fired")){
		    		System.out.println("This packer has alredy been fired!");
		    		pressReturn(choice);
		    	} else {
		    		connecte.setPackerFired(id);
		    		System.out.println("Packer fired");
		    		pressReturn(choice);
		    	}
		    	break;
			case 0:
				break;
			default:
				System.out.println("\nError, please enter one of the number in the list");
			}	
			return choice;

    	} else {
    		System.out.println("There is no packer");
    		return 0;
    	}
    }
    
    public void printPackages(ResultSet res) throws Exception {
    	print("PACKER ID", 15);
    	print("FIRED", 10);
    	print("LASTNAME", 15);
    	print("PACKAGE ID", 15);
    	print("START PACKING", 15);
    	print("DONE PACKING", 15);
    	print("DELAY", 15);
    	System.out.println();
    	do{
    		print(String.valueOf(res.getString("id_packer")), 15);
    		print(String.valueOf(res.getBoolean("fired")), 10);
    		print(String.valueOf(res.getString("lastname")), 15);
    		print(String.valueOf(res.getString("id_package")), 15);
    		print(String.valueOf(res.getDate("start_date")), 15);
    		print(String.valueOf(res.getDate("packed_date")), 15);
    		print(String.valueOf(res.getInt("delay")), 15);
    		System.out.println();
    	}while (res!= null && res.next());
    }
    
    public void printTopClients(ResultSet res, int x) throws Exception{
    	Boolean next = res.next();
    	if (res!=null && next){
	    	print("ID CLIENT", 20);
	    	print("ORDERS AMOUNT", 15);
	    	print("PHONE NUMBER", 15);
	    	print("COMPANY", 15);
	    	print("SUFFIXE", 10);
	    	print("ADDRESS", 30);
	    	print("POSTAL CODE", 20);
	    	print("CITY", 15);
	    	print("COUNTRY", 15);
	    	print("LOGIN", 15);
	    	System.out.println();
		    for ( int i=0; i<x; i++){
		    	if (res != null && next){
			    	print(String.valueOf(res.getString("id_client")), 20);
			    	print(String.valueOf(res.getInt("orders_amount")), 15);
			    	print(String.valueOf(res.getString("phone_num")), 15);
			    	print(String.valueOf(res.getString("company")), 15);
			    	print(String.valueOf(res.getString("suffixe")), 10);
			    	print(String.valueOf(res.getString("address")), 30);
			    	print(String.valueOf(res.getString("postal_code")), 20);
			    	print(String.valueOf(res.getString("city")), 15);
			    	print(String.valueOf(res.getString("country")), 15);
			    	print(String.valueOf(res.getString("login")), 15);
			    	System.out.println();
			    	next = res.next();
		    	}
	    	}
    	} else {
    		System.out.println("There is no client");
    	}
    }
    
    public void printClients(ResultSet res) throws Exception{
    	if (res!=null && res.next()){
	    	print("ID CLIENT", 20);
	    	print("PHONE NUMBER", 15);
	    	print("COMPANY", 15);
	    	print("SUFFIXE", 10);
	    	print("ADDRESS", 30);
	    	print("POSTAL CODE", 20);
	    	print("CITY", 15);
	    	print("COUNTRY", 15);
	    	print("LOGIN", 15);
	    	print("ORDERS AMOUNT", 15);
	    	System.out.println();
	    	do{
		    	print(String.valueOf(res.getString("id_client")), 20);
		    	print(String.valueOf(res.getString("phone_num")), 15);
		    	print(String.valueOf(res.getString("company")), 15);
		    	print(String.valueOf(res.getString("suffixe")), 10);
		    	print(String.valueOf(res.getString("address")), 30);
		    	print(String.valueOf(res.getString("postal_code")), 20);
		    	print(String.valueOf(res.getString("city")), 15);
		    	print(String.valueOf(res.getString("country")), 15);
		    	print(String.valueOf(res.getString("login")), 15);
		    	print(String.valueOf(res.getInt("orders_amount")), 15);
		    	System.out.println();
	    	}while ( res!=null && res.next());
    	} else {
    		System.out.println("There is no client");
    	}
    }
    
    public void printPackers(ResultSet res) throws Exception{
    	print("ID PACKER", 15); 
    	print("LASTENAME", 20);
    	print("LOGIN", 20);
    	print("ERROR RATE", 15);
    	System.out.println();
    	do{
	    	print(String.valueOf(res.getString("id_packer")), 15);
	    	print(String.valueOf(res.getString("lastname")), 20);
	    	print(String.valueOf(res.getString("login")), 20);
	    	print(String.valueOf(res.getInt("error_rate")), 15);
	    	System.out.println();
    	}while (res!=null && res.next());
    }

    public void printDeliverers(ResultSet res) throws Exception{
    	print("ID DELIVERER", 15); 
    	print("LASTENAME", 20);
    	print("LOGIN", 20);
    	System.out.println();
    	do{
	    	print(String.valueOf(res.getString("id_deliverer")), 15);
	    	print(String.valueOf(res.getString("lastname")), 20);
	    	print(String.valueOf(res.getString("login")), 20);
	    	System.out.println();
    	}while (res!=null && res.next());
    }
    
    public void printTopProducts(ResultSet res, int x) throws Exception{
    	Boolean next = res.next();
    	if( res!=null && next ){
	    	print("ID PRODUCT", 18);
	    	print("PRICE", 15);
	    	print("INCREASE RATE", 19);
	    	print("WEIGHT", 10);
	    	print("STOCK", 10);
	    	print("NUMBER BY BOX", 15);
	    	print("BOXES BY PALLET", 20);
	    	print("SALES NUMBER", 15);
	    	print("QUALIFIANT", 10);
	    	System.out.println();
	    	for (int i=0; i<x; i++){
	    		if ( res!=null && next){
		    		print(String.valueOf(res.getString("id_product")), 18);
		    		print(String.valueOf(res.getDouble("price")), 15);
		    		print(String.valueOf(res.getDouble("increase_rate")), 19);
		    		print(String.valueOf(res.getInt("weight")), 10);
		    		print(String.valueOf(res.getInt("stock")), 10);
		    		print(String.valueOf(res.getInt("num_in_box")), 15);
		    		print(String.valueOf(res.getInt("boxes_in_pallet")), 20);
		    		print(String.valueOf(res.getInt("sales_num")), 15);
		    		print(String.valueOf(res.getString("qualifiant")),10);
		    		System.out.println();
		    		next = res.next();
	    		}
	    	}
    	} else {
    		System.out.println("There is no product");
    	}
    }
    
    public void printProducts(ResultSet res) throws Exception{
    	if (res!=null && res.next()){
	    	print("ID PRODUCT", 18);
	    	print("PRICE", 15);
	    	print("INCREASE RATE", 19);
	    	print("WEIGHT", 10);
	    	print("STOCK", 10);
	    	print("NUMBER BY BOX", 15);
	    	print("BOXES BY PALLET", 20);
	    	print("SALES NUMBER", 15);
	    	print("QUALIFIANT", 10);
	    	System.out.println();
	    	do{
	    		print(String.valueOf(res.getString("id_product")), 18);
	    		print(String.valueOf(res.getDouble("price")), 15);
	    		print(String.valueOf(res.getDouble("increase_rate")), 19);
	    		print(String.valueOf(res.getInt("weight")), 10);
	    		print(String.valueOf(res.getInt("stock")), 10);
	    		print(String.valueOf(res.getInt("num_in_box")), 15);
	    		print(String.valueOf(res.getInt("boxes_in_pallet")), 20);
	    		print(String.valueOf(res.getInt("sales_num")), 15);
	    		print(String.valueOf(res.getString("qualifiant")),10);
	    		System.out.println();
	    	}while (res!=null && res.next());
    	} else {
    		System.out.println("There is no product");
    	}
    }
    
    /** Imprime le menu a l'ecran.*/
    public int printMenu() throws Exception{
		int c = -1; // user's choice
	
		System.out.print("\033c"); //clean screen
	
		// -------------------
		// Print manager's menu
		// -------------------
		System.out.println(" What do you want to do? :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Print packers");
		System.out.println("2 - Fire an packer");
		System.out.println("3 - Print packages by packer");
		System.out.println("4 - Print deliverers");
		System.out.println("5 - Fire a deliverer");
		System.out.println("6 - Print clients");
		System.out.println("7 - Print top clients");
		System.out.println("8 - Print products");
		System.out.println("9 - Print top products.");
		System.out.println("10 - Change products' price.");
		System.out.println("0 - End");
		System.out.println("-------------------------------------------------------------");
	
		// ----------------------------
		// Read manager's choice
		// ----------------------------
		
		c = readInt();
		
		System.out.print("\033c"); //clean screen
		
		// -------------------------------
		// Execute manager's choice
		// -------------------------------
		
		try {
			int top, cpt =-1;
			ResultSet contenu=null;
			switch(c){
			case 1 : 
			    System.out.println("Print packers\n");	
			    contenu = connecte.printPackers(" WHERE fired=false");
		    	if (contenu!=null && contenu.next()){
				    printPackers(contenu);
		    	} else {
		    		System.out.println("There is no packer");
		    	}
			    break;
			case 2 :
				while (cpt!=0 ){
					System.out.println("\033c");
				    System.out.println("Packers you can fire :\n");
				    contenu = connecte.printPackers(" WHERE fired=false");
				    cpt = firePacker(contenu);
				}
			    break;
			case 3 :
				while (cpt!=0){
					System.out.print("\033c");
					System.out.println("Packages by a packer\n");
					contenu = connecte.printPackages("*", ",packers WHERE packages.id_packer=packers.id_packer ORDER BY (packages.id_packer)");
			    	cpt = firePackerPackages(contenu);
				}
			    break;
			case 4 :
			    System.out.println("Print deliverers\n");	
			    contenu = connecte.printDeliverers(" WHERE fired=false");
		    	if (contenu!=null && contenu.next()){
				    printDeliverers(contenu);
		    	} else {
		    		System.out.println("There is no deliverer");
		    	}
			    break;
			case 5 :
				while (cpt!=0 ){
					System.out.println("\033c");
				    System.out.println("Deliverers you can fire :\n");
				    contenu = connecte.printDeliverers(" WHERE fired=false");
				    cpt = fireDeliverer(contenu);
				}
			    break;
			case 6 :
			    System.out.println("Print clients\n");
			    contenu = connecte.printClients("");
			    printClients(contenu);
			    break;
			case 7 :
				System.out.println("How many clients would you like to print ?");
				top = readInt();
				System.out.print("\033c");
			    System.out.println("Top "+ top+" clients\n");
			    contenu = connecte.printClients(" ORDER BY orders_amount DESC");
			    printTopClients(contenu, top);
			    break;
			case 8 :
				System.out.println("Products' list:\n");
				contenu = connecte.printProducts("");
				printProducts(contenu);
				break;
			case 9 :
				System.out.println("How many products would you like to print ?");
				top = readInt();
				System.out.print("\033c");
			    System.out.println("Top "+ top+" products\n");
			    contenu = connecte.printProducts(" ORDER BY sales_num DESC");
			    printTopProducts(contenu, top);
			    break;
			
			case 10 :
				while (cpt!=0){
					System.out.print("\033c");
				    System.out.println("Change price \n");
				    contenu = connecte.printProducts("");
				    printProducts(contenu);
				    cpt = updatePrice(contenu);
				}
			    break;
			case 0 : 
			    System.out.println("\nEnd of your manager connection");
			    break;
			    
			default : 
			    System.out.println("\nERROR! Please enter one of the number in the list");
			}
		    }
		catch (SQLException e) {
		    System.err.println(e.getMessage());
		}
		return c;
    }

}
