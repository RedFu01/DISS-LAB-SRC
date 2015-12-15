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
	private Packet[] packets;
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
	
	private void initialize(){
		StoragePlace[] storagePlaces = this.warehouse.getStoragePlacesAsArray();
		List<Slot> slotList = new ArrayList<Slot>();
		for(int i=0;i<storagePlaces.length;i++){
			Slot tempSlot = new Slot(storagePlaces[i].getNumber(),storagePlaces[i].getWidth(),storagePlaces[i].getHeight(),storagePlaces[i].getDepth(),storagePlaces[i].getPositionX(),storagePlaces[i].getPositionY());
			slotList.add(tempSlot);
		}
		this.slots = (Slot[]) slotList.toArray();
	}
	
	private Slot findSlotByPacketId(int id){
		for( int i=0; i< this.slots.length; i++){
			if(this.slots[i].getContainedPacket() != null && this.slots[i].getId() == id){
				return this.slots[i];
			}
		}
		
		return null;
	}
	
	private int getVolumeOffset(Packet packet, Slot slot){
		return slot.getVolume() - packet.getVolume();
	}
	
	private Slot getBestStoragePlace(Packet packet){
		int currentVolumeOffset = 1000;
		Slot slot = null;
		for( int i=0; i< this.slots.length; i++){
			if(packet.fitsInSlot(this.slots[i]) && getVolumeOffset(packet, this.slots[i]) < currentVolumeOffset){
				slot = this.slots[i];
			}
		}
		return slot;
	}
	
	public int storePacket(int width, int height, int depth, String description) throws StorageException {
		Packet packet = new Packet(width, height, depth, description);
		Slot slot = getBestStoragePlace(packet);
		
		slot.setContainedPacket(packet);
		this.craneControl.storePacket(slot.getPositionX(), slot.getPositionY(), packet);
		
		return slot.getId();
				
	}
	
	public void retrievePacket(int id) throws StorageException {
		Slot slot = findSlotByPacketId(id);
		this.craneControl.retrievePacket(slot.getPositionX(),slot.getPositionY());
	}
	
	public Packet[] getPackets() {
		List<Packet> packetList = new ArrayList<Packet>();
		
		for(int i=0; i< this.slots.length; i++){
			if(this.slots[i].getContainedPacket() != null){
				packetList.add(this.slots[i].getContainedPacket());
			}
		}
		
		
		return (Packet[]) packetList.toArray();
	}
	
	public void shutdown() {
		this.craneControl.shutdown();
	}
}
