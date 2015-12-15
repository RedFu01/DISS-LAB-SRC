package de.tuhh.diss.warehouse;

import de.tuhh.diss.warehouse.sim.StorageElement;

public class Packet implements StorageElement{
	private int width=0;
	private int height =0;
	private int depth =0;
	private int id =0;
	private String description = "";
	
	public Packet(){};
	
	public Packet(int width, int height, int depth, String description){
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.description = description;
	}
	
	public boolean fitsInSlot(Slot slot){
		if(slot.getContainedPacket() != null){
			return false;
		}
		return (this.width <= slot.getWidth() && this.height <= slot.getHeight() && this.depth <= slot.getDepth());
	}
	
	public int getVolume(){
		return this.width * this.height* this.depth;
	}
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getDepth() {
		return this.depth;
	}

	public int getId() {
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public String getDescription() {
		return this.description; 
	}
}
