package com.s4n.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Dron {

	private Position currentPosition;
	private List<Position> deliveries;
	private List<String> routes;


	public Dron(List<String> routes){
		this.setCurrentPosition(new Position(0, 0, Direction.NORTE));
		this.setDeliveries(new ArrayList<>());
		this.setRoutes(routes);
	}


}
