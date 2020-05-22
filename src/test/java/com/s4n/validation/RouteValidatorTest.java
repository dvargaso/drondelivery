package com.s4n.validation;

import com.s4n.validation.impl.RouteValidatorImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;


public class RouteValidatorTest {

	private RouteValidator routeValidator = new RouteValidatorImpl();

	@ParameterizedTest
	@ValueSource(strings = { " ", "", "AAS", "A-I-D-A","rutaADDA" })
	void invalidRoute(String invalidRoute) {
		Exception  exception = assertThrows(IllegalArgumentException.class,
				() -> routeValidator.validateRawRoute(invalidRoute));
		assertEquals(format("La ruta %s no cumple con el formato esperado",invalidRoute),exception.getMessage());

	}

	@ParameterizedTest
	@ValueSource(strings = { "AAAA", "ADDI", "III","DDDD", "ADA", "aia" })
	void testValidRoutes(String validRoute){
		assertDoesNotThrow(() ->routeValidator.validateRawRoute(validRoute));
	}


}
