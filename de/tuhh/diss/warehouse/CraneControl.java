package de.tuhh.diss.warehouse;

import de.tuhh.diss.warehouse.sim.StorageElement;
import de.tuhh.diss.warehouse.sim.PhysicalCrane;
import de.tuhh.diss.io.SimpleIO;
import de.tuhh.diss.warehouse.sim.CraneException;

public class CraneControl {

	// Define crane as a type of PhysicalCrane
	public PhysicalCrane crane;

	public CraneControl(PhysicalCrane cr) {
		crane = cr;
	}

	// Drive the crane to the desired position of (x,y)
	public void driveTo(int x, int y) {
		driveToX(x);
		driveToY(y);
	}

	// Drive the crane to the desired position of (x)
	public void driveToX(int x) {
		int initialX = crane.getPositionX();
		int targetX = x;
		
		// When the target x-position is further than the crane
		if (initialX < targetX) {
			crane.forward();
			while (true) {
				if (initialX == targetX) {
					crane.stopX();
					break;
				}

				else {
					stallTestX();
					initialX = crane.getPositionX();
				}
			}
		}
		
		// When the target x-position is behind than the crane
		else if (initialX > targetX) {
			crane.backward();
			while (true) {
				if (initialX == targetX) {
					crane.stopX();
					break;
				}

				else {
					stallTestX();
					initialX = crane.getPositionX();
				}
			}
		}

		else {
			crane.stopX();
		}
	}

	// Drive the crane to the desired position of (y)
	public void driveToY(int y) {
		int initialY = crane.getPositionY();
		int targetY = y;
		
		// When the target y-position is higher than the crane
		if (initialY < targetY) {
			crane.up();
			while (true) {
				if (initialY == targetY) {
					crane.stopY();
					break;
				}

				else {
					stallTestY();
					initialY = crane.getPositionY();
				}
			}
		}
		
		// When the target y-position is lower than the crane
		else if (initialY > targetY) {
			crane.down();
			while (true) {
				if (initialY == targetY) {
					crane.stopY();
					break;
				}

				else {
					stallTestY();
					initialY = crane.getPositionY();
				}
			}
		} 
		
		else {
			crane.stopY();
		}
	}

	// Drive the crane to the loading/unloading bay
	public void driveToLoadingPosition() {
		int LoadPosX = crane.getLoadingPosX();
		int LoadPosY = crane.getLoadingPosY();

		driveTo(LoadPosX, LoadPosY);
	}

	public void stallTestX() {
		// Exception to detect whether the crane is stalled in x-direction movement
		if (crane.isStalledX() == true) {
			throw new CraneException("Crane is currently stalled at x = " + crane.getPositionX());
		}
	}

	public void stallTestY() {
		// Exception to detect whether the crane is stalled in y-direction movement
		if (crane.isStalledY() == true) {
			throw new CraneException("Crane is currently stalled at y = " + crane.getPositionY());
		}
	}

	public void storePacket(int x, int y, StorageElement packet) {
		if (packet == null) {
			throw new CraneException("CraneControl.storePacket - there is no packet at the moment.");
		}

		// Drive to the loading/unloading bay, then load packet into the crane
		driveToLoadingPosition();
		try {
			crane.loadElement(packet);
		}
		catch (CraneException loadNA) {
			SimpleIO.println("Crane is occupied -OR- operation cannot be performed in this position");
		}
		

		// Drive to the storage space, then store the packet
		driveTo(x, y);
		try {
			crane.storeElement();
		}
		catch (CraneException storageNA) {
			SimpleIO.println("Storage is occupied -OR- The crane is empty.");
			
		}
	}

	public StorageElement retrievePacket(int x, int y) {
		// Drive to the storage space, then load the packet into the crane
		driveTo(x, y);
		try {
			crane.retrieveElement();
		}
		catch (CraneException retrieveNA) {
			SimpleIO.println("There is no packet stored here -OR- The crane is unavailable.");
		}

		// Drive to the loading/unloading bay, then unload packet off the crane
		driveToLoadingPosition();	
		return crane.unloadElement();
	}

	public void shutdown() {
		crane.shutdown();
	}

}
