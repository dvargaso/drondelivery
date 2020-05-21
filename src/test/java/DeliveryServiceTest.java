import com.s4n.domain.Dron;
import com.s4n.domain.Position;
import com.s4n.service.DeliveryService;
import com.s4n.service.DeliveryServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.stream.IntStream;

import static com.s4n.domain.Direction.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeliveryServiceTest {


	private DeliveryService deliveryService;

	@BeforeAll
	void setup() {
		deliveryService = new DeliveryServiceImpl();
	}

	/**
	 * Este test demuestra que los resultados calculados con los datos de ejemplo en en enunciado, son incorrectos
	 */
	@Test
	void testHappyPathWithS4nSampleData() {
		//Arrange
		Dron dron = new Dron(asList("AAAAIAA", "DDDAIAD", "AAIADAD"));

		Position expectedDelivery1 = new Position(-2, 4, OCCIDENTE);
		Position expectedDelivery2 = new Position(-1, 3, SUR);
		Position expectedDelivery3 = new Position(0, 0, OCCIDENTE);

		//Act && Assert
		assertExpectedPositions(dron.getRoutes(), asList(expectedDelivery1, expectedDelivery2, expectedDelivery3),
				dron.getCurrentPosition());
	}


	@Test
	void testTurnRight() {
		Dron dron = new Dron(asList("D", "D", "D", "D"));
		Position expectedDelivery1 = new Position(0, 0, ORIENTE);
		Position expectedDelivery2 = new Position(0, 0, SUR);
		Position expectedDelivery3 = new Position(0, 0, OCCIDENTE);
		Position expectedDelivery4 = new Position(0, 0, NORTE);

		//Act && Assert
		assertExpectedPositions(dron.getRoutes(), asList(expectedDelivery1, expectedDelivery2, expectedDelivery3,
				expectedDelivery4), dron.getCurrentPosition());

	}

	@Test
	void testTurnLeft() {
		Dron dron = new Dron(asList("I", "I", "I", "I"));
		Position expectedDelivery1 = new Position(0, 0, OCCIDENTE);
		Position expectedDelivery2 = new Position(0, 0, SUR);
		Position expectedDelivery3 = new Position(0, 0, ORIENTE);
		Position expectedDelivery4 = new Position(0, 0, NORTE);

		//Act && Assert
		assertExpectedPositions(dron.getRoutes(), asList(expectedDelivery1, expectedDelivery2, expectedDelivery3,
				expectedDelivery4), dron.getCurrentPosition());

	}

	@Test
	void testAdvanceNorth() {
		Dron dron = new Dron(asList("A"));
		//"DA", "DDA", "DDDA", "DDDDA
		Position expectedDelivery1 = new Position(0, 1, NORTE);

		//Act && Assert
		assertExpectedPositions(dron.getRoutes(), asList(expectedDelivery1), dron.getCurrentPosition());

	}

	@Test
	void testAdvanceSouth() {
		Dron dron = new Dron(asList("DA"));
		Position expectedDelivery1 = new Position(1, 0, ORIENTE);

		//Act && Assert
		assertExpectedPositions(dron.getRoutes(), asList(expectedDelivery1), dron.getCurrentPosition());

	}

	@Test
	void testAdvanceEast() {
		Dron dron = new Dron(asList("DDA"));
		Position expectedDelivery1 = new Position(0, -1, SUR);

		//Act && Assert
		assertExpectedPositions(dron.getRoutes(), asList(expectedDelivery1), dron.getCurrentPosition());

	}

	@Test
	void testAdvanceWest() {
		Dron dron = new Dron(asList("DDDA"));
		Position expectedDelivery1 = new Position(-1, 0, OCCIDENTE);

		//Act && Assert
		assertExpectedPositions(dron.getRoutes(), asList(expectedDelivery1), dron.getCurrentPosition());

	}

	public void assertExpectedPositions(List<String> routes, List<Position> expectedDeliveries,
										Position currentPosition) {
		IntStream.range(0, routes.size()).forEach(i -> {
			Position actualDelivery = deliveryService.followRoute(currentPosition, routes.get(i));
			assertAll("delivery",
					() -> assertEquals(expectedDeliveries.get(i).getX(), actualDelivery.getX()),
					() -> assertEquals(expectedDeliveries.get(i).getY(), actualDelivery.getY()),
					() -> assertEquals(expectedDeliveries.get(i).getDirection(), actualDelivery.getDirection())
			);
		});
	}
}
