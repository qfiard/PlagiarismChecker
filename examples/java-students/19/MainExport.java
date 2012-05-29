import java.io.IOException;
import java.util.Scanner;

public class MainExport {

	static Scanner in = new Scanner(System.in);
	static ConnectionExport connecte;

    // Print how to use Export's class
    public static void howto() {
        System.out.println("Please enter your login to Postgres.");
        System.out.println("how to : java Export <Username>");
        System.exit(1);
    }

    public static void pressReturn(int choice) throws IOException{
	    if (choice != 0){
			System.out.println("\nPress Return key.");
			System.in.read();
		}    	
    }
    
    // ------------------------
    // Print Users' choice Menu
    // ------------------------
    public static int printMenu(){
    	int t=-1;
    	System.out.print("\033c");   // Clear screen
    	System.out.println("Choose your statut :");
    	System.out.println("-------------------------------------------------------------");
        System.out.println("1 - Admin");
    	System.out.println("2 - Manager");
    	System.out.println("3 - Packers");
    	System.out.println("4 - Deliverers");
    	System.out.println("5 - Clients");
    	System.out.println("6 - Customs");
        System.out.println("0 - End");
    	System.out.println("-------------------------------------------------------------");
    	
		try{
		    t = in.nextInt();   // Read user's choice 
		}
		catch(Exception e){
		    in.nextLine();
		    e.printStackTrace();
		    return -1;
		}
		
    	try {
    		int cpt = -1;
    		switch(t){
            case 1 :
                AdminMenu admin = new AdminMenu(connecte);
                while (cpt !=0){
                	cpt = admin.printMenu();
        			pressReturn(cpt);
                }
                break;
    		case 2 :
    			ManagerMenu manager = new ManagerMenu(connecte);
    			if (manager.connection("manager")){
                    while (cpt !=0){
                    	cpt = manager.printMenu();
            			pressReturn(cpt);
                    }
    			}
    			break;
    		case 3 :
    			PackersMenu packer = new PackersMenu(connecte);
    			if (packer.connection("packers")){
                    while (cpt !=0){
                    	cpt = packer.printMenu();
            			pressReturn(cpt);
                    }
    			}
    			break;
    		case 4 :
    			DeliverersMenu deliverer = new DeliverersMenu(connecte);
    			if (deliverer.connection("deliverers")){
                    while (cpt !=0){
                    	cpt = deliverer.printMenu();
            			pressReturn(cpt);
                    }
    			}
    			break;
    		case 5 :
    			ClientsMenu client = new ClientsMenu(connecte);
    			if (client.connection("clients")){
                    while (cpt !=0){
                    	cpt = client.printMenu();
            			pressReturn(cpt);
                    }
    			}
    			break;
    		case 6 :
    			CustomsMenu customs = new CustomsMenu(connecte);
    			if (customs.connection("customs")){
                    while (cpt !=0){
                    	cpt = customs.printMenu();
            			pressReturn(cpt);
                    }
    			}
    			break;
    		case 0 :
    			System.out.print("\033c");
                System.out.println("\n END \nGOODBYE \n");
    			break;
    		default :
    			System.out.println("\nERROR! Please enter one of the number in the list");
    		}
    	} catch (Exception e){
    		System.out.println(e.toString());
    	}
    	return t;
    }
    
    
    // Start the connection to sql and waiting instructions
    public static void main(String[] args) {
    	// ---------------------------
    	// Checking parameters
    	// ---------------------------
    	if (args.length != 1)
    	    howto();
    	try {
    		// -------------------
    		// Connection to sql
    		// --------------------
    		String password = PasswordField.readPassword("Please enter your password to connect to Postgres: ");
    		connecte = new ConnectionExport(args[0], password);	
    		
    		// ---------------------------------------
    		// Print menu. 0 to quit
    		// ---------------------------------------
    		int c = -1;
    		while(c != 0){
    		    c = printMenu();
    			pressReturn(c);
    		}
    		// -------------------------
    		// Close connection
    		// -------------------------
    		connecte.close();
    		in.close();
    	}
    	catch(Exception e)  {
    		e.printStackTrace();
    	}
    }
}
