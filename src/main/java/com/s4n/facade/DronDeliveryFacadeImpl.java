package com.s4n.facade;

import com.s4n.domain.Dron;
import com.s4n.domain.Position;
import com.s4n.io.DronReportWriter;
import com.s4n.io.FileNameProvider;
import com.s4n.io.impl.FileReader;
import com.s4n.service.DeliveryService;
import com.s4n.validation.RouteValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@AllArgsConstructor
public class DronDeliveryFacadeImpl implements DronDeliveryFacade {

	public static final String DRONE_ROUTE_FILE_PREFIX = "in";
	public static final String TXT_FILE_POSFIX = ".txt";
	public static final String DRONE_OUTPUT_FILE_PREFIX = "out";


	private FileReader reader;
	private DeliveryService deliveryService;
	private int dronCount;
	private DronReportWriter reportWriter;
	private RouteValidator routeValidator;
	private FileNameProvider fileNameProvider;


	@Override
	public void performDeliveries() {

		List<String> inputRoutes = filterRoutesByAvailableDrones(fileNameProvider.resolveInputFileNames(dronCount));
		inputRoutes.stream().forEach(routesPath -> {
			String input = fileNameProvider.provideInputPath(routesPath);
			String output = fileNameProvider.provideOutputPath(routesPath);
			List<Position> deliveries = null;

			try {
				Dron dron = new Dron(reader.readFile(input));
				if (CollectionUtils.isEmpty(dron.getRoutes())) {
					throw new IllegalArgumentException(format("El archivo %s no contiene rutas", input));
				}

				deliveries = dron.getDeliveries();
				dron.getRoutes().stream().forEach(route -> {
					routeValidator.validateRawRoute(route);
					Position delivery = deliveryService.followRoute(dron.getCurrentPosition(), route);
					checkDeliveryRange(delivery);
					dron.getDeliveries().add(new Position(delivery.getX(), delivery.getY(), delivery.getDirection()));
				});

				reportWriter.writeToFile(output, dron.getDeliveries(), null);

			} catch (IOException ioe) {
			} catch (IllegalArgumentException iae) {

			}
		});


	}

	private void checkDeliveryRange(Position position) {
		int range = 10;
		if (position.getX() > range || position.getY() > range) {
			throw new IllegalArgumentException(format("Fuera del rango de %s cuadras. No se puede entregar", range));
		}
	}


	private List<String> filterRoutesByAvailableDrones(List<String> inputRoutes) {
		if (inputRoutes.size() > dronCount) {
			return inputRoutes.subList(0, dronCount - 1);
		} else {
			return inputRoutes;
		}
	}


}
