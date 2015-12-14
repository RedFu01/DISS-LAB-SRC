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
	
	public WarehouseManagement(PhysicalWarehouse warehouse){
		this.warehouse = warehouse;
		this.craneControl = new CraneControl(this.warehouse.getCrane());
	}
	
	public WarehouseManagement(){
		this.warehouse = new PhysicalWarehouse();
		this.craneControl = new CraneControl(this.warehouse.getCrane());
	}
	
	private Slot findSlotByPacketId(int id){
		StoragePlace[] storagePlaces = this.warehouse.getStoragePlacesAsArray();
		for(int i=0; i< storagePlaces.length; i++){
			if(((Slot)storagePlaces[i]).getContainedPacket().getId() == id){
				return (Slot) storagePlaces[i];
			}
		}
		return null;
	}
	
	private int getVolumeOffset(Packet packet, Slot slot){
		return slot.getVolume() - packet.getVolume();
	}
	
	private Slot getBestStoragePlace(Packet packet){
		StoragePlace[] storagePlaces = this.warehouse.getStoragePlacesAsArray();
		int currentVolumeOffset = 1000;
		Slot slot = null;
		for( int i=0; i< storagePlaces.length; i++){
			Slot testSlot = new Slot(storagePlaces[i].getNumber(), storagePlaces[i].getWidth(),storagePlaces[i].getHeight(),storagePlaces[i].getDepth(),storagePlaces[i].getPositionX(),storagePlaces[i].getPositionY());
			if(packet.fitsInSlot(testSlot) && getVolumeOffset(packet, testSlot) < currentVolumeOffset){
				slot = testSlot;
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
		StoragePlace[] storagePlaces = this.warehouse.getStoragePlacesAsArray();
		List<Packet> packetList = new ArrayList<Packet>();
		
		for(int i=0; i< storagePlaces.length; i++){
			if(((Slot)storagePlaces[i]).getContainedPacket() != null){
				packetList.add(((Slot)storagePlaces[i]).getContainedPacket());
			}
		}
		
		
		return (Packet[]) packetList.toArray();
	}
	
	public void shutdown() {
		this.craneControl.shutdown();
	}
}
