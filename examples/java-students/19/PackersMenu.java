import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Calendar;


public class PackersMenu{

	String accountType = "packer";
	ConnectionExport connecte;
	Scanner in = new Scanner(System.in);
	String packerID;
	

    public PackersMenu(ConnectionExport c){
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
    	    e.printStackTrace();
    	    return null;
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
		    e.printStackTrace();
		    return -1;
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
    	try{
			result = connecte.connection(userType, login, password);
			this.packerID = connecte.findUserId(userType, login, password);
			return result;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return false;
    }


    public Date date(){
    	Calendar c = Calendar.getInstance();
    	Date f = new Date(c.getTime().getTime());
    	return f;
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
    
    public String addPalletContainer(String packageID, String clientID) throws SQLException{
 	   String palletID, containerID;
 	   ResultSet pallets = connecte.exists(" * ", " pallets ", " WHERE nb_packages<max_packages AND id_client='"+clientID+"' ");
 	   if (pallets!=null && pallets.next()){
 		   palletID = String.valueOf(pallets.getString("id_pallet"));
 		   containerID = String.valueOf(pallets.getString("id_container"));
 		   connecte.setPallet(" nb_packages ", " nb_packages + 1 ", palletID);
 	   } else {
 		   palletID = createRandomId("pallets");
 		   connecte.addPallet(palletID, clientID, 1);
 		   containerID = getContainerId(clientID);
 		   connecte.setPallet(" id_container ", "'"+containerID+"'", palletID);
 	   }
 	   connecte.setPackage(" id_container ", "'"+containerID+"'", packageID);
 	   connecte.setPackage(" id_pallet ", "'"+palletID+"'", packageID);
 	   return palletID;
    }

    
    public String getContainerId(String clientID) throws SQLException {
 	   String containerID;
 	   ResultSet containers = connecte.exists(" * ", " containers ", " WHERE nb_pallets<max_pallets AND id_client='"+clientID+"' ");
 	   if (containers!=null && containers.next()){
 		   containerID = String.valueOf(containers.getString("id_container"));
 		   connecte.setContainer(" nb_pallets ", " nb_pallets + 1 ", containerID);
 	   } else {
 		   containerID = createRandomId("containers");
 		   connecte.addContainer(containerID, clientID, 1);
 	   }
 	   return containerID;
    }
    
    public void printOrders(ResultSet contenu)throws SQLException{
    	print("ID_ORDER", 13);
	    print("ID_PRODUCT", 20);
	    print("QUANTITY", 10);
	    print("QUALIFIANT", 10);
	    print("START_PACK", 13);
	    print("ID_PACKAGE", 15);
	    System.out.println();
	    while (contenu != null && contenu.next()) {
			print( String.valueOf(contenu.getString("id_order")), 13);
			print( String.valueOf(contenu.getString("id_product")), 20);
			print( String.valueOf(contenu.getString("quantity")), 10);
			print( String.valueOf(contenu.getString("qualifiant")), 10);
			print( String.valueOf(contenu.getDate("start_date")), 13);
			print( String.valueOf(contenu.getString("id_package")), 15);
			System.out.println();
	    };
    }

    public String printPackages(ResultSet contenu)throws SQLException{
		print("ID_PACKAGE", 17);
		print("ID_PACKER",16);
		print("START_PACK", 17);
		print("PACKED_DATE",18);
	    print("QUALIFIANT", 17);
	    print("STATUT", 25);
	    System.out.println();
	    while (contenu != null && contenu.next()) {
			print( String.valueOf(contenu.getString("id_package")), 17);
			print( String.valueOf(contenu.getString("id_packer")), 16);
			print( String.valueOf(contenu.getDate("start_date")), 17);
			print( String.valueOf(contenu.getDate("packed_date")), 18);
			print( String.valueOf(contenu.getString("qualifiant")), 17);
			print( String.valueOf(contenu.getString("statut")), 25);
			System.out.println();
	    };
	    return String.valueOf(contenu.getString("id_client"));
    }

    public int printMenu() {
		int c = -1;
	
		System.out.print("\033c"); 
		// -------------------
		// Print packer's menu
		// -------------------
		System.out.println(" What do you want to do? :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Print all orders to pack");
		System.out.println("2 - Print packed packages");
		System.out.println("3 - Search for an order");
		System.out.println("4 - Set start packing date for package");
		System.out.println("5 - Set end packing date for package");
		System.out.println("0 - End");
		System.out.println("-------------------------------------------------------------");
		c = readInt();
		System.out.print("\033c"); // clear screen
		
		try 
		    {
				switch(c){
					case 1 : 
					    System.out.println("Printing orders to pack");
					    ResultSet contenu = connecte.printOrders(" NATURAL JOIN packages WHERE statut='NOT PACKED' ");
					    System.out.println("-------------------------------------------------------------");
					    printOrders(contenu);
					    System.out.println("-------------------------------------------------------------");
					    break;

					case 2 : 
					    System.out.println("Printing packages 'Ready to be delivered' ");
					    contenu = connecte.printPackages("*","WHERE statut='READY' ");
					    System.out.println("-------------------------------------------------------------");
					    printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
					    break;

					case 3 :
					    System.out.println("Enter an id_order for more informations :");
    					contenu = connecte.printOrders(" NATURAL JOIN packages WHERE id_order='"+ readString() +"'");
					    System.out.println("-------------------------------------------------------------");
						printOrders(contenu);
					    System.out.println("-------------------------------------------------------------");
					    break;

				    case 4 :
						System.out.println("Printing packages to prepare...");
					    contenu = connecte.printPackages("*","WHERE statut='NOT PACKED' AND id_packer IS NULL ");
    					System.out.println("-------------------------------------------------------------");
    					printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
					    System.out.println();
					    System.out.println("Enter id_package of the package that you want to pack :");
					    String packageID = readString();
						contenu = connecte.printPackages("*","WHERE id_package='"+ packageID +"'");
						System.out.println("-------------------------------------------------------------");
						printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
						System.out.println();
						System.out.println("Confirm start packing date on : " + date().toString() + " by "+packerID+" ?");	
						System.out.println("-------------------------------------------------------------");
						System.out.println("1 - Yes");
						System.out.println("2 - No");
						System.out.println("-------------------------------------------------------------");
					    connecte.setPackerStart(readInt(),packageID,packerID,date());
						System.out.println("-------------------------------------------------------------");
						printPackages(connecte.printPackages("*","WHERE id_package='"+ packageID +"'"));
					    System.out.println("-------------------------------------------------------------");
					    break;


					case 5 :
						System.out.println("Printing packages to finish..");
					    contenu = connecte.printPackages("*","WHERE statut='NOT PACKED' AND start_date IS NOT NULL AND id_packer='"+packerID+"' ");
    					System.out.println("-------------------------------------------------------------");
    					printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
					    System.out.println();
					    System.out.println("Enter id_package of the package that you finished packing :");
					    packageID = readString();
						contenu = connecte.printPackages("*","WHERE id_package='"+ packageID +"'");
						System.out.println("-------------------------------------------------------------");
						String clientID = printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
						System.out.println();
						System.out.println("Confirm packed date is done on : " + date().toString());	
						System.out.println("-------------------------------------------------------------");
						System.out.println("1 - Yes");
						System.out.println("2 - No");
						System.out.println("-------------------------------------------------------------");
					    connecte.setPackerEnd(readInt(),packageID,date());
					    addPalletContainer(packageID, clientID);
						System.out.println("-------------------------------------------------------------");
						printPackages(connecte.printPackages("*","WHERE id_package='"+ packageID +"'"));
					    System.out.println("-------------------------------------------------------------");
					    break;
			    	case 0 : 
					    System.out.println("Disconnected from Packers session.");
			   			break;
			    
					default : 
					    System.out.println("ERROR!");
					    break;
				}
		    }
		catch (SQLException e) {
		    System.err.println(e.getMessage());
		}
		return c;
    }

}
