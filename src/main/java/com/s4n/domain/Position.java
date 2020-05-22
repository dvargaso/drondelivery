package com.s4n.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class represents a position with orientation in a grid
 *
 */
@Data
@AllArgsConstructor
public class Position {
	private int x;
	private int y;
	private Direction direction;

}
