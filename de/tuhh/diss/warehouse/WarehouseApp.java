package de.tuhh.diss.warehouse;
import de.tuhh.diss.io.SimpleIO;
//import java.util.Arrays;

//import de.tuhh.diss.warehouse.WarehouseManagement;
import de.tuhh.diss.warehouse.test.WarehouseManagement;

//import sun.awt.image.PNGImageDecoder.Chromaticities;
//import sun.java2d.pipe.SpanShapeRenderer.Simple;
import de.tuhh.diss.warehouse.sim.*;
public class WarehouseApp {
	private static WarehouseManagement warehouseManagement = new WarehouseManagement();

	public static void main (String[] args) {
		String choice = "";
		boolean exit = false;
		int width = 0, height = 0, depth = 0, id=-1;
		String description = "";
		/*
		 * setting up a new WarehouseManagement
		 */
		
		// Display of the Header of the Menu 		
		SimpleIO.println("Size: 16x5");
		SimpleIO.println("PhysicalCrane simulator started, v1.0");
		SimpleIO.println("");
		SimpleIO.println("Welcome to TUHH/DISS Warehouse Managment");
		SimpleIO.println("");
		
		/*
		 *  As long as the choice is not "0", the 
		 */
		
		while (exit==false) {
			MainMenuText();
			choice=chooseInput();
			switch (choice) {
			case "1":
				SimpleIO.println("*** Store a packet ***");
				SimpleIO.println("Set up a new packet...");
				//Packet.width = readDouble();
				description = setDescription();
				width = setWidth();
				height = setHeight();
				depth = setDepth();
				try {
					warehouseManagement.storePacket(width, height, depth, description);
				} catch (StorageException e) {
					SimpleIO.print("Storage Error");
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
						warehouseManagement.retrievePacket(id);
					} catch (StorageException e) {
						SimpleIO.print("Storage Error");
						e.printStackTrace();
					}
					SimpleIO.println();
				}else{
					SimpleIO.println("Retrieve packet aborted!");
				}
				break;
			case "0":
				exit = true;
				warehouseManagement.shutdown();
				break;
			}
		}
	}
	
	/*
	 * 
	 */
	public static String chooseInput(){
		String choice = SimpleIO.readString();
		SimpleIO.println("Your choice: '"+choice+"'");
		
		/*
		 * security, that the input is correct
		 * as long as the String "choice" is not in the range of available menu points, it will be stuck in a loop
		 */
		if (choice.equals("1") || choice.equals("2") || choice.equals("0")){
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
	 * Methods for the input of width height and depth for setting up a new packet
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
			SimpleIO.print("Insert a positive value!");
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
			SimpleIO.print("Insert a positive value!");
			setDepth();
		}
		return(depth);
	}
	public static int getId(){
		int id = SimpleIO.readInt();
		if (id<0){
			SimpleIO.print("Enter a valid ID!");
			getId();
		}
		return(id);
	}
	public static void printPackets(){
		de.tuhh.diss.warehouse.test.Packet[] array = warehouseManagement.getPackets();
		//Packet[] array = warehouseManagement.getPackets();
		for (int i=0; i<array.length; i++){
			SimpleIO.println("ID:"+array[i].getId()+"  description:"+array[i].getDescription());
		}
		SimpleIO.println();
	}
}
