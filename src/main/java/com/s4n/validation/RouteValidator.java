package com.s4n.validation;

public interface RouteValidator {
	void validateRawRoute(String row) throws IllegalArgumentException;
}
