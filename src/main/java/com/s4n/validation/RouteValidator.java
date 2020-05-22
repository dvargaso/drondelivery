package com.s4n.validation;

/**
 * Validator for raw input data
 */
public interface RouteValidator {
	/**
	 * This method validates that a route has a valid format
	 * @param row
	 * @throws IllegalArgumentException
	 */
	void validateRawRoute(String row) throws IllegalArgumentException;
}
