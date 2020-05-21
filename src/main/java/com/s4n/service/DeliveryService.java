package com.s4n.service;

import com.s4n.domain.Position;

public interface DeliveryService {
	Position followRoute(Position currentPosition, String route);
}
