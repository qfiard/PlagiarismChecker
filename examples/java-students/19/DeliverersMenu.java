import java.sql.*;
import java.util.Scanner;


public class DeliverersMenu{

	String accountType = "deliverer";
	ConnectionExport connecte;
	Scanner in = new Scanner(System.in);
	String deliverID;
	 
    public DeliverersMenu(ConnectionExport c){
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
    public boolean connection(String userType){
    	Boolean result = false;
    	System.out.print("\033c");   // Clear screen
    	System.out.println("Enter your " + accountType + " login:");
    	String login = readString();
    	System.out.println("Enter your " + accountType + " password:");
    	String password = readString();
    	try {
			result = connecte.connection(userType, login, password);
			this.deliverID = connecte.findUserId(userType, login, password);
			return result;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return false;
    }

    
    
    public void printPackages(ResultSet contenu)throws SQLException{
    	print("ID_PACKAGE", 12);
	    print("ID_CLIENT", 12);
	    print("ID_DELIVERER", 12);
	    print("SENT_DATE", 16);
	    print("CUSTOMS_DATE", 19);
	    print("DELAY", 12);
	    print("QUALIFIANT", 17);
	    print("STATUT", 25);
	    System.out.println();
	    while (contenu != null && contenu.next()) {
			print( String.valueOf(contenu.getString("id_package")), 12);
			print( String.valueOf(contenu.getString("id_client")), 12);
			print( String.valueOf(contenu.getString("id_deliverer")), 12);
			print( String.valueOf(contenu.getDate("sent_date")), 16);
			print( String.valueOf(contenu.getDate("customs_date")), 19 );
			print( String.valueOf(contenu.getInt("delay")), 12);
			print( String.valueOf(contenu.getString("qualifiant")), 17);
			print( String.valueOf(contenu.getString("statut")), 25);
			System.out.println();
	    };
    }

    /** Print the menu.*/
    public int printMenu() {
		int c = -1; // user's choice
	
		System.out.print("\033c"); // clear screen
	
		// -------------------
		// Print deliverer's menu
		// -------------------
		System.out.println(" What do you want to do? :");
		System.out.println("-------------------------------------------------------------");
		System.out.println("1 - Print all packages");
		System.out.println("2 - Modify packages ready to be to delivered");
		System.out.println("3 - Modify delivering packages");
		System.out.println("4 - Print sent packages");
		System.out.println("5 - Search for a package");
		System.out.println("0 - End");
		System.out.println("-------------------------------------------------------------");
	
		// ----------------------------
		// Read deliverer's choice
		// ----------------------------
		
		c = readInt();
		
		System.out.print("\033c"); // clear screen
		
		// -------------------------------
		// Execute deliverer's choice
		// -------------------------------
		
		try 
		    {
				switch(c){
					case 1 : 
					    System.out.println("Printing all packages");
					    ResultSet contenu = connecte.printPackages("*","");
    					System.out.println("-------------------------------------------------------------");
					    printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
					    break;

					case 2 : 
					    System.out.println("Printing packages to deliver");
					    contenu = connecte.printPackages("*","WHERE statut='READY' ");
    					System.out.println("-------------------------------------------------------------");
					    printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
					    System.out.println();
					    System.out.println("Enter id_package of the package that you want to change statut :");
					    String packageID = readString();
					    contenu = connecte.printPackages("*","WHERE id_package='"+ packageID +"'");
						System.out.println("-------------------------------------------------------------");
						printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
						System.out.println();
						System.out.println("Change statut to :\n");	
						System.out.println("-------------------------------------------------------------");
						System.out.println("1 - Delivering...");
						System.out.println("2 - Delivered");
						System.out.println("0 - Exit");
						System.out.println("-------------------------------------------------------------");
					    connecte.setDeliverStatut(readInt(),packageID,deliverID);
					    System.out.println("-------------------------------------------------------------");
						printPackages(connecte.printPackages("*","WHERE id_package='"+ packageID +"'"));
					    System.out.println("-------------------------------------------------------------");
					    break;

					case 3 : 
					    System.out.println("Printing packages in delivering...");
					    contenu = connecte.printPackages("*","WHERE statut='DELIVERING' ");
    					System.out.println("-------------------------------------------------------------");
					    printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
					   	System.out.println();
					    System.out.println("Enter id_package of the package that you want to change statut :");
					    packageID = readString();
					    contenu = connecte.printPackages("*","WHERE id_package='"+ packageID +"'");
						System.out.println("-------------------------------------------------------------");
						printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
						System.out.println();
						System.out.println("Change statut to :\n");	
						System.out.println("-------------------------------------------------------------");
						System.out.println("1 - * Do Nothing * ");
						System.out.println("2 - Delivered");
						System.out.println("0 - Exit");
						System.out.println("-------------------------------------------------------------");
					    connecte.setDeliverStatut(readInt(),packageID,deliverID);
					    System.out.println("-------------------------------------------------------------");
						printPackages(connecte.printPackages("*","WHERE id_package='"+ packageID +"'"));
					    System.out.println("-------------------------------------------------------------");
					  
					    break;

					case 4 : 
					    System.out.println("Printing packages delivered");
					    contenu = connecte.printPackages("*","WHERE statut='DELIVERED' ");
    					System.out.println("-------------------------------------------------------------");
					    printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
					    break;

					case 5 :
					    System.out.println("Enter an id_package for more informations :");						 
					    contenu = connecte.printPackages("*","WHERE id_package='"+readString()+"'" );
					    System.out.println("-------------------------------------------------------------");
					    printPackages(contenu);
					    System.out.println("-------------------------------------------------------------");
					    break;

					case 0 : 
					    System.out.println("Disconnected from Deliverers session.");
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
