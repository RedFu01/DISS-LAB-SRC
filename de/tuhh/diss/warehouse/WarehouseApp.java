package de.tuhh.diss.warehouse;
import de.tuhh.diss.io.SimpleIO;

import de.tuhh.diss.warehouse.test.*;
import sun.awt.image.PNGImageDecoder.Chromaticities;
import de.tuhh.diss.warehouse.sim.*;
public class WarehouseApp {
	
	public static void main (String[] args) {
		String choice = "";
		boolean exit = false;
		double width = 0, height = 0, depth = 0;
		String DESCRIPTION = "";
		
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
				SimpleIO.println("Set a new packet up...");
				//Packet.width = readDouble();
				DESCRIPTION = setDescription();
				width = setWidth();
				height = setHeight();
				depth = setDepth();
				Packet packet1 = new Packet();/*ID, DESCRIPTION, SLOTNUMBER, WIDTH, HEIGHT, DEPTH*/
			case "2":
				SimpleIO.println("*** Retrieve a packet ***");

				SimpleIO.println("Available packets: ");
				SimpleIO.println("*** Enter ID of the packet to be retrieved (0 = abort)");
			case "0":
				exit = true;
				break;
			}
		}
		SimpleIO.println("System ends.");
		SimpleIO.println("PhysicalCrane was shut down.");
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
	public static double setWidth(){
		SimpleIO.print("Width: ");
		return(SimpleIO.readDouble());
	}
	public static double setHeight(){
		SimpleIO.print("Height: ");
		return(SimpleIO.readDouble());
	}
	public static double setDepth(){
		SimpleIO.print("Depth: ");
		return(SimpleIO.readDouble());
	}
}
