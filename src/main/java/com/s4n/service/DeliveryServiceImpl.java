package com.s4n.service;

import com.s4n.domain.Position;
import lombok.extern.slf4j.Slf4j;

import static com.s4n.domain.Direction.*;
import static com.s4n.domain.Direction.SUR;

@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

	@Override
	public Position followRoute(Position currentPosition, String route) {
		route.chars().forEach(ch -> {
			switch (ch) {
				case 68:
					turnRight(currentPosition); //Derecha
					break;
				case 73:
					turnLeft(currentPosition); //Izquierda
					break;
				case 65:
					advance(currentPosition); //Adelante
					break;
			}
		});
		return currentPosition;
	}

	private void advance(Position position) {
		switch (position.getDirection()) {
			case NORTE:
				position.setY(position.getY() + 1);
				break;
			case SUR:
				position.setY(position.getY() - 1);
				break;
			case ORIENTE:
				position.setX(position.getX() + 1);
				break;
			case OCCIDENTE:
				position.setX(position.getX() - 1);
				break;
		}
	}

	private void turnRight(Position position) {
		switch (position.getDirection()) {
			case NORTE:
				position.setDirection(ORIENTE);
				break;
			case SUR:
				position.setDirection(OCCIDENTE);
				break;
			case ORIENTE:
				position.setDirection(SUR);
				break;
			case OCCIDENTE:
				position.setDirection(NORTE);
				break;
		}
	}

	private void turnLeft(Position position) {
		switch (position.getDirection()) {
			case NORTE:
				position.setDirection(OCCIDENTE);
				break;
			case SUR:
				position.setDirection(ORIENTE);
				break;
			case ORIENTE:
				position.setDirection(NORTE);
				break;
			case OCCIDENTE:
				position.setDirection(SUR);
				break;
		}
	}
}
