package com.s4n.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position
{
	private int x;
	private int y;
	private Direction direction;


	public String getGridPosition() {
		return "Position{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
