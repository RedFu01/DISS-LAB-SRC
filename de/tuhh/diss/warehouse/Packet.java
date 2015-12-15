package de.tuhh.diss.warehouse;

import de.tuhh.diss.warehouse.sim.StorageElement;

public class Packet implements StorageElement{
	private int width= 0;
	private int height = 0;
	private int depth = 0;
	private int id = 0;
	private String description = "";
	
	/**  
	 * Empty constructor
	 * @param none
	 * @return returns a packet
	 */
	public Packet(){};
	
	/**  
	 * Constructor to create a packet with its properties
	 * @param measures of the packet and a string to describe it
	 * @return returns a packet
	 */
	public Packet(int width, int height, int depth, String description){
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.description = description;
	}
	
	/**  
	 * Function to check if a packet fits in a slot, if the slot already contains a packet it returns false
	 * @param takes a slot
	 * @return returns a boolean
	 */
	public boolean fitsInSlot(Slot slot){
		if(slot.getContainedPacket() != null){
			return false;
		}
		return (this.width <= slot.getWidth() && this.height <= slot.getHeight() && this.depth <= slot.getDepth());
	}
	
	/**  
	 * Helper function to calculate the volume of a packet
	 * @param none
	 * @return returns the volume as integer
	 */
	public int getVolume(){
		return this.width * this.height* this.depth;
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
	 * Getter function for the Id
	 * @param none
	 * @return returns the id as integer
	 */
	public int getId() {
		return this.id;
	}
	
	/**  
	 * Setter function for the Id
	 * @param the new id as integer
	 * @return none
	 */
	public void setId(int id){
		this.id = id;
	}
	
	/**  
	 * Getter function for the description
	 * @param none
	 * @return returns the description as string
	 */
	public String getDescription() {
		return this.description; 
	}
}
