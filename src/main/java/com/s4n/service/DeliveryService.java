package com.s4n.service;

import com.s4n.domain.Position;

/**
 * This class provides methods to change the dron location
 */
public interface DeliveryService {
	/**
	 * this method updates the position according to the route to follow
	 * @param currentPosition
	 * @param route
	 * @return
	 */
	Position followRoute(Position currentPosition, String route);
}
