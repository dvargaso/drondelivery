package com.s4n.service.impl;

import com.s4n.domain.Position;
import com.s4n.service.DeliveryService;
import lombok.extern.slf4j.Slf4j;

import static com.s4n.domain.Direction.*;
import static com.s4n.domain.Direction.SUR;

@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

	@Override
	public Position followRoute(Position currentPosition, String route) {
		route.chars().forEach(ch -> {
			switch (ch) {
				case 'D':
					turnRight(currentPosition); //Derecha
					break;
				case 'I':
					turnLeft(currentPosition); //Izquierda
					break;
				case 'A':
					advance(currentPosition); //Adelante
					break;
			}
		});
		return currentPosition;
	}

	private void advance(Position currentPosition) {
		switch (currentPosition.getDirection()) {
			case NORTE:
				lockPositionAndMove(currentPosition.getX(), (currentPosition.getY() + 1), currentPosition);
				break;
			case SUR:
				lockPositionAndMove(currentPosition.getX(), (currentPosition.getY() - 1), currentPosition);
				break;
			case ORIENTE:
				lockPositionAndMove((currentPosition.getX() + 1), currentPosition.getY(), currentPosition);
				break;
			case OCCIDENTE:
				lockPositionAndMove((currentPosition.getX() - 1), currentPosition.getY(), currentPosition);
				break;
		}
	}

	private void lockPositionAndMove(int x, int y, Position currentPosition) {
		String gridPositionLock = "Position:" + x + "|" + y;
		synchronized (gridPositionLock) {
			currentPosition.setX(x);
			currentPosition.setY(y);
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
