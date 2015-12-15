package de.tuhh.diss.warehouse;
import de.tuhh.diss.io.SimpleIO;

/*
 * when the statement below is changed, check the corresponding statement in printPackets() in the bottom iof the code!!
 */
//import de.tuhh.diss.warehouse.WarehouseManagement;
import de.tuhh.diss.warehouse.WarehouseManagement;
import de.tuhh.diss.warehouse.sim.*;

public class WarehouseApp {
	
	/*setting up a new WarehouseManagement*/
	private static WarehouseManagement warehouseManagement = new WarehouseManagement();
	

	public static void main (String[] args) {
		String choice = ""; 							//for main menu maneuvering
		boolean exit = false;							//for shutdown of the program
		int width = 0, height = 0, depth = 0, id=-1;	// variables for input 
		String description = "";
		
		WarehouseTest test = new WarehouseTest(warehouseManagement);
		//test.test();
		
		/*Display of the Header*/ 		
		SimpleIO.println("Size: 16x5");
		SimpleIO.println("PhysicalCrane simulator started, v1.0");
		SimpleIO.println("");
		SimpleIO.println("Welcome to TUHH/DISS Warehouse Managment");
		SimpleIO.println("");
		
		/*
		 *  As long as the choice is not "0", the Menu will restart after finishing
		 *  one case.  
		 */
		while (exit==false) {
			MainMenuText();
			choice=chooseInput();
			
			switch (choice) {
			
				case "1":
				/*
				 * STORING A PACKET:
				 * set up a new packet, with description width depth and height
				 * exception handling
				 */
					SimpleIO.println("*** Store a packet ***");
					SimpleIO.println("Set up a new packet...");
					
					description = setDescription();
					width = setWidth();
					height = setHeight();
					depth = setDepth();
					
					try {
						SimpleIO.println("Crane is in motion...working");
						warehouseManagement.storePacket(width, height, depth, description);
						SimpleIO.println("Crane has stopped... waiting for input");
					} catch (StorageException e) {
						SimpleIO.println("Storage Error (packet too big!)");
					}
					
					break;
				
				case "2": 
				/*
				 * RETRIEVING A PACKET: 
				 * -Display a list of all stored packets
				 * -choose packet to be retrieved, if "0"-> abort
				 * -retrieve packet
				 * 
				 */
					SimpleIO.println("*** Retrieve a packet ***");
					SimpleIO.println("Available packets: ");
					printPackets();
				
					SimpleIO.println("*** Enter ID of the packet to be retrieved (0 = abort)");
					id=getId();
					if (id!=0){
						try {
							SimpleIO.println("Crane is in motion...working");
							warehouseManagement.retrievePacket(id);
							SimpleIO.println("Crane has stopped... waiting for input");
						} catch (StorageException e) {
							SimpleIO.println("Retrieve Error");
						}
						SimpleIO.println();
					}else{
						SimpleIO.println("Retrieve packet aborted!");
					}
					
					break;
				case "test": //top secret option to test :P
					test.test();
					break;
					
				case "0":
				/* 
				 * end of program: call of the shutdown-method and leave the while-loop by setting exit=true.
				 */
					exit = true;
					warehouseManagement.shutdown();
					break;
				}
		}
		SimpleIO.println("going to sleep....zzzZZzzz");
	}
	
	/*
	 * ---------------------------------------------------------------------------------
	 * functions for user-input:
	 * chooseInput(): gets the input for the main menu with exception handling
	 * readString()
	 * readDouble()
	 * MainMenuText(): display of main menu
	 * setDescription()
	 * setWidth()
	 * setHeight()
	 * setDepth()
	 * getId()
	 * printPackets():
	 * -------------------------------------------------------------------------------- 
	 */
	
	public static String chooseInput(){
		String choice = SimpleIO.readString();
		SimpleIO.println("Your choice: '"+choice+"'");
		
		/*
		 * security, that the input is correct
		 * as long as the String "choice" is not in the range of available menu points, it will be stuck in a loop
		 */
		if (choice.equals("1") || choice.equals("2") || choice.equals("0") || choice.equals("test")){
		} else{
			SimpleIO.println("Please make a valid choice! ('1' or '2' or '0')");
			chooseInput();
		}
		return(choice);
	}

	public String ReadString(){
		return(SimpleIO.readString());
	}
	
	public double readDouble(){
		return SimpleIO.readDouble();
	}
	
	public static void MainMenuText(){
		SimpleIO.println("*** Main Menu ***");
		SimpleIO.println("1: Store a packet in the warehouse");
		SimpleIO.println("2: Retrieve a packet from the warehouse");
		SimpleIO.println("0: Quit programm");
		SimpleIO.println("");
	}
	
	
	/*
	 * Methods for the input of description, width, height and depth for setting up a new packet
	 * 
	 * in setWidth(), setDepth(), setHeight();
	 * exception handling by recursive call of method in if-statement
	 */
	public static String setDescription(){
		SimpleIO.print("Description: ");
		return(SimpleIO.readString());
	}
	public static int setWidth(){
		SimpleIO.print("Width: ");
		int width=0;
		
		// check for non-integer input and negative values
		try { 
			width = SimpleIO.readInt();
		} catch (Exception e) {
			SimpleIO.print("Insert a valid integer-value!");
			setWidth();
		}
		if (width<0){
			SimpleIO.print("Insert a positive value!");
			setWidth();
		}
		return(width);
	}

	public static int setHeight(){
		SimpleIO.print("Height: ");
		int height=0;
		
		// check for non-integer input and negative values
		try {
			height = SimpleIO.readInt();
		} catch (Exception e) {
			SimpleIO.print("Insert a valid integer-value!");
			setHeight();
		}
		if (height<0){
			SimpleIO.println("Insert a positive value!");
			setHeight();
		}
		return(height);
	}
	
	public static int setDepth(){
		SimpleIO.print("Depth: ");
		int depth=0;
		
		// check for non-integer input and negative values
		try {
			depth = SimpleIO.readInt();
		} catch (Exception e) {
			SimpleIO.print("Insert a valid integer-value!");
			setDepth();
		}
		if (depth<0){
			SimpleIO.println("Insert a positive value!");
			setDepth();
		}
		return(depth);
	}
	
	/*
	 * input of ID for retrieving a packet in menu point "2". 
	 */
	public static int getId(){
		int id = SimpleIO.readInt();
		if (id<0){
			SimpleIO.print("Enter a valid ID!");
			getId();
		}
		return(id);
	}
	
	/*
	 * Printing a list of stored packets,
	 */
	public static void printPackets(){
		
		//for testing with the provided "test.warehouse"
		//de.tuhh.diss.warehouse.test.Packet[] array = warehouseManagement.getPackets();
		
		//for use of own warehouse
		Packet[] array = warehouseManagement.getPackets();
		for (int i=0; i<array.length; i++){
			SimpleIO.println("ID:"+array[i].getId()+"  description:"+array[i].getDescription());
		}
		SimpleIO.println();
	}
}
