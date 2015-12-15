package de.tuhh.diss.warehouse;

import java.util.ArrayList;
import java.util.List;

import de.tuhh.diss.warehouse.sim.HighBayWarehouse;
import de.tuhh.diss.warehouse.sim.PhysicalWarehouse;
import de.tuhh.diss.warehouse.sim.StorageException;
import de.tuhh.diss.warehouse.sim.StoragePlace;

public class WarehouseManagement implements HighBayWarehouse {
	private CraneControl craneControl;
	private PhysicalWarehouse warehouse;
	private Slot[] slots;
	
	public WarehouseManagement(PhysicalWarehouse warehouse){
		this.warehouse = warehouse;
		this.craneControl = new CraneControl(this.warehouse.getCrane());
		this.initialize();
	}
	
	public WarehouseManagement(){
		this.warehouse = new PhysicalWarehouse();
		this.initialize();
	}
	
	/**  
	 * Initializes the slots array (do this to prevent several casting). Also use this function to do more initialization
	 * @param none
	 * @return none
	 */
	private void initialize(){
		StoragePlace[] storagePlaces = this.warehouse.getStoragePlacesAsArray();
		List<Slot> slotList = new ArrayList<Slot>();
		for(int i=0;i<storagePlaces.length;i++){
			Slot tempSlot = new Slot(storagePlaces[i].getNumber(),storagePlaces[i].getWidth(),storagePlaces[i].getHeight(),storagePlaces[i].getDepth(),storagePlaces[i].getPositionX(),storagePlaces[i].getPositionY());
			slotList.add(tempSlot);
		}
		this.slots = (Slot[]) slotList.toArray(new Slot[slotList.size()]);
	}
	
	/**  
	 * Loops through the slots array and checks if the contained packet has the given id
	 * @param a packetId
	 * @return returns a slot
	 */
	private Slot findSlotByPacketId(int id){
		for( int i=0; i< this.slots.length; i++){
			if(this.slots[i].getContainedPacket() != null && this.slots[i].getId() == id){
				return this.slots[i];
			}
		}
		
		return null;
	}
	
	/**  
	 * Calculates the volumeOffset between a slot and a packet
	 * @param packet and slot to compare
	 * @return returns the offset as integer
	 */
	private int getVolumeOffset(Packet packet, Slot slot){
		return slot.getVolume() - packet.getVolume();
	}
	
	/**  
	 * Looks for an empty storageplace with minimal wasted space
	 * @param the packet to store
	 * @return returns a slot
	 */
	public Slot getBestStoragePlace(Packet packet){
		int currentVolumeOffset = 1000;
		Slot slot = null;
		for( int i=0; i< this.slots.length; i++){
			if(packet.fitsInSlot(this.slots[i]) && getVolumeOffset(packet, this.slots[i]) < currentVolumeOffset){
				slot = this.slots[i];
			}
		}
		return slot;
	}
	
	/**  
	 * Function to store a packet
	 * @param measures and description of the new packet
	 * @return returns the id of the slot where the packet is stored
	 */
	public int storePacket(int width, int height, int depth, String description) throws StorageException {
		Packet packet = new Packet(width, height, depth, description);
		Slot slot = getBestStoragePlace(packet);
		
		slot.setContainedPacket(packet);
		this.craneControl.storePacket(slot.getPositionX(), slot.getPositionY(), packet);
		
		return slot.getId();
				
	}
	
	/**  
	 * retrieves a packet from the warehouse
	 * @param id of the needed packet
	 * @return none
	 */
	public void retrievePacket(int id) throws StorageException {
		Slot slot = findSlotByPacketId(id);
		slot.setContainedPacket(null);
		this.craneControl.retrievePacket(slot.getPositionX(),slot.getPositionY());
	}
	
	/**  
	 * Function to get a full list of all packets
	 * @param none
	 * @return returns a array of packets
	 */
	public Packet[] getPackets() {
		List<Packet> packetList = new ArrayList<Packet>();
		
		for(int i=0; i< this.slots.length; i++){
			if(this.slots[i].getContainedPacket() != null){
				packetList.add(this.slots[i].getContainedPacket());
			}
		}
		
		return (Packet[]) packetList.toArray();
	}
	
	/**  
	 * Pass the shutdown command to the cranecontrol
	 * @param none
	 * @return none
	 */
	public void shutdown() {
		this.craneControl.shutdown();
	}
}
