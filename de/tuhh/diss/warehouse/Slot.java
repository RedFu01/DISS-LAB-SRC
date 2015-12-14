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
	
	public int getNumber() {
		return this.number; 
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

	public int getPositionX() {
		return this.positionX;  
	}
	
	public int getPositionY() {
		return this.positionY;  
	}
	
	public int getVolume(){
		return this.width * this.height* this.depth;
	}

	public int getId() {
		return this.id;
	}
	
	public void setContainedPacket(Packet packet){
		this.containedPacket = packet;
	}
	public Packet getContainedPacket(){
		return this.containedPacket;
	}
}
