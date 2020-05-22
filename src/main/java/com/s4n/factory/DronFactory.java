package com.s4n.factory;

import com.s4n.domain.Direction;
import com.s4n.domain.Dron;
import com.s4n.domain.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * This is class is a factory for Drones
 */
public class DronFactory {

	/**
	 * This method creates drones in the initial position in the grid
	 * @param routes
	 * @return
	 */
	public static Dron createNewDron(List<String> routes) {
		synchronized ("Position:0|0") {
			Position initialPosition = new Position(0, 0, Direction.NORTE);
			return new Dron(initialPosition, new ArrayList<>(), routes);
		}
	}
}
