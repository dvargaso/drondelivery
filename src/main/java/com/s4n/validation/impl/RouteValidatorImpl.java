package com.s4n.validation.impl;

import com.s4n.validation.RouteValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

import static java.lang.String.format;

@Slf4j
public class RouteValidatorImpl implements RouteValidator {

	private static final Pattern ROUTE_PATTERN = Pattern.compile("[A|a|D|d|I|i]+");

	@Override
	public void validateRawRoute(String row) throws IllegalArgumentException {
		if (!ROUTE_PATTERN.matcher(row).matches()) {
			String errorMsg = format("La ruta %s no cumple con el formato esperado", row);
			log.warn(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
	}
}
