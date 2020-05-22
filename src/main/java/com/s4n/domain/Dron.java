package com.s4n.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Dron {

	private Position currentPosition;
	private List<Position> deliveries;
	private List<String> routes;

}
