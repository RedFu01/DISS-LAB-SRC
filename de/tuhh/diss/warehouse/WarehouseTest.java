package de.tuhh.diss.warehouse;
import de.tuhh.diss.io.SimpleIO;

/*
 * when the statement below is changed, check the corresponding statement in printPackets() in the bottom iof the code!!
 */
//import de.tuhh.diss.warehouse.WarehouseManagement;
import de.tuhh.diss.warehouse.WarehouseManagement;
import de.tuhh.diss.warehouse.sim.*;

public class WarehouseTest {
	
	private WarehouseManagement warehouseManagement = null;
	
	public WarehouseTest(WarehouseManagement warehouseManagement){
		this.warehouseManagement = warehouseManagement;
	}

	public void test () {
		fillWarehouse();
		emptyWarehouse();
	}
	public void fillWarehouse(){
			for(int i =0; i< 10000; i++){
				Packet randomPacket = generatePacket();
				Slot possibleSlot = warehouseManagement.getBestStoragePlace(randomPacket);
				if(possibleSlot !=null){
					try {
						warehouseManagement.storePacket(randomPacket.getWidth(), randomPacket.getHeight(), randomPacket.getDepth(), randomPacket.getDescription());
					} catch (StorageException e) {
						//e.printStackTrace();
					}
				}
			}
		
	}
	public void emptyWarehouse(){
		Packet[] array = warehouseManagement.getPackets();
		for (int i=0; i<array.length; i++){
			try {
				warehouseManagement.retrievePacket(array[i].getId());
			} catch (StorageException e) {
				//e.printStackTrace();
			}
		}
	}
	
	public Packet generatePacket(){
		String description = "autogenerated Packet";
		int width = 1+ (int) (Math.random()*3);
		int height = 1+ (int) (Math.random()*3);
		int depth = 1+ (int) (Math.random()*1);
		return new Packet(width, height, depth, description);
	}
	
	
}
