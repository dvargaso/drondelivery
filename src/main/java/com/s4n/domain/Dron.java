package com.s4n.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * This class represents a dron with it's current state the current state
 */
@Data
@AllArgsConstructor
public class Dron {

	private Position currentPosition;
	private List<Position> deliveries;
	private List<String> routes;

}
