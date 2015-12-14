package de.tuhh.diss.warehouse;

import de.tuhh.diss.warehouse.sim.StorageElement;
import de.tuhh.diss.warehouse.sim.PhysicalCrane;
import de.tuhh.diss.warehouse.sim.CraneException;

public class CraneControl {

	// Define an instance variable crane as a type of PhysicalCrane
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

		if (initialX < targetX) {
			while (initialX < targetX) {
				crane.forward();
				stallTestX();
			}
		}

		if (initialX > targetX) {
			while (initialX > targetX) {
				crane.backward();
				stallTestX();
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

		if (initialY < targetY) {
			while (initialY < targetY) {
				crane.up();
				stallTestY();
			}
		}

		if (initialY > targetY) {
			while (initialY > targetY) {
				crane.down();
				stallTestY();
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
			throw new CraneException(
					"CraneControl.storePacket - there is no packet in the crane at the moment.");
		}

		// Drive to the loading/unloading bay, then load packet into the crane
		driveToLoadingPosition();
		crane.loadElement(packet);

		// Drive to the storage space, then store the packet
		driveTo(x, y);
		crane.storeElement();

	}

	public StorageElement retrievePacket(int x, int y) {
		// Drive to the storage space, then load the packet into the crane
		driveTo(x, y);
		crane.retrieveElement();

		// Drive to the loading/unloading bay, then unload packet off the crane
		driveToLoadingPosition();
		return crane.unloadElement();
	}

	public void shutdown() {
		crane.shutdown();
	}
	
}
