package de.tuhh.diss.warehouse;

import de.tuhh.diss.warehouse.WarehouseManagement;
import de.tuhh.diss.warehouse.sim.*;

public class WarehouseTest {
	
	private WarehouseManagement warehouseManagement = null;
	
	public WarehouseTest(WarehouseManagement warehouseManagement){
		this.warehouseManagement = warehouseManagement;
	}

	/**  
	 * Run all the tests (fill and empty the warehouse)
	 * @param none
	 * @return none
	 */
	public void test () {
		fillWarehouse();
		emptyWarehouse();
	}
	
	/**  
	 * Creates 10000 random packets and tries to store them
	 * @param none
	 * @return none
	 */
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
	/**  
	 * retrieves all packets from the warehouse
	 * @param none
	 * @return none
	 */
	public void emptyWarehouse(){
		Packet[] array = warehouseManagement.getPackets();
		for (int i=0; i<array.length; i++){
			try {
				warehouseManagement.retrievePacket(array[i].getId());
			} catch (StorageException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**  
	 * generates a random packet
	 * @param none
	 * @return none
	 */
	public Packet generatePacket(){
		String description = "autogenerated Packet";
		int width = 1+ (int) (Math.random()*4);
		int height = 1+ (int) (Math.random()*4);
		int depth = 1+ (int) (Math.random()*1);
		return new Packet(width, height, depth, description);
	}
	
	
}
