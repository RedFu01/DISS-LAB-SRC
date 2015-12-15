package de.tuhh.diss.warehouse;

import de.tuhh.diss.warehouse.sim.StoragePlace;

public class Slot implements StoragePlace {	
	
	private int number = 0;
	private int width = 0;
	private int height = 0;
	private int depth = 0;
	private int positionX = 0;
	private int positionY = 0;
	private int id;
	private Packet containedPacket = null;
	
	public Slot(int number, int width, int height, int depth, int positionX, int positionY){
		this.number = number;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public Slot(int number, int width, int height, int depth, int positionX, int positionY, Packet containedPacket){
		this.number = number;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.positionX = positionX;
		this.positionY = positionY;
		this.containedPacket = containedPacket;
	}
	
	/**  
	 * Getter function for the slot number
	 * @param none
	 * @return returns the slot number as integer
	 */
	public int getNumber() {
		return this.number; 
	}

	/**  
	 * Getter function for the width
	 * @param none
	 * @return returns the width as integer
	 */
	public int getWidth() {
		return this.width; 
	}

	/**  
	 * Getter function for the height
	 * @param none
	 * @return returns the height as integer
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**  
	 * Getter function for the depth
	 * @param none
	 * @return returns the depth as integer
	 */
	public int getDepth() {
		return this.depth;  
	}

	/**  
	 * Getter function for the x value of the position vector
	 * @param none
	 * @return returns the x position as integer
	 */
	public int getPositionX() {
		return this.positionX;  
	}
	
	/**  
	 * Getter function for the y value of the position vector
	 * @param none
	 * @return returns the y position as integer
	 */
	public int getPositionY() {
		return this.positionY;  
	}
	
	/**  
	 * Helper function to calculate the volume of a slot
	 * @param none
	 * @return returns the volume as integer
	 */
	public int getVolume(){
		return this.width * this.height* this.depth;
	}

	/**  
	 * Getter function for the id
	 * @param none
	 * @return returns the id as integer
	 */
	public int getId() {
		return this.id;
	}
	
	/**  
	 * Setter function for the contained Packet of a slot
	 * @param takes a packet
	 * @return none
	 */
	public void setContainedPacket(Packet packet){
		this.containedPacket = packet;
	}
	
	/**  
	 * Getter function for the contained packet
	 * @param none
	 * @return returns the contained packet without removing it
	 */
	public Packet getContainedPacket(){
		return this.containedPacket;
	}
}
