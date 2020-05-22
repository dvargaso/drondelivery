package com.s4n.facade.impl;

import com.s4n.domain.Dron;
import com.s4n.domain.Position;
import com.s4n.facade.DronDeliveryFacade;
import com.s4n.factory.DronFactory;
import com.s4n.io.DronReportWriter;
import com.s4n.io.FileNameProvider;
import com.s4n.io.FileReader;
import com.s4n.service.DeliveryService;
import com.s4n.util.Configuration;
import com.s4n.validation.RouteValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import static java.lang.String.format;


@Slf4j
@AllArgsConstructor
public class DronDeliveryFacadeImpl implements DronDeliveryFacade {

	private FileReader reader;
	private DronReportWriter reportWriter;
	private DeliveryService deliveryService;
	private int dronCount;
	private Executor threadPool;
	private RouteValidator routeValidator;
	private FileNameProvider fileNameProvider;


	@Override
	public void performDeliveries() {
		List<String> inputRoutes = filterRoutesByAvailableDrones(fileNameProvider.resolveInputFileNames(dronCount));

		threadPool.execute(
				() -> inputRoutes.parallelStream().forEach(routePath -> {
					processDronDeliveries(routePath);
				}));
	}


	private void processDronDeliveries(String routePath) {

		String input = fileNameProvider.provideInputPath(routePath);
		String output = fileNameProvider.provideOutputPath(routePath);
		List<Position> deliveries = null;

		try {
			Dron dron =  DronFactory.createNewDron(reader.readFile(input));
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
			String message = format("Error leyendo ruta %s", routePath);
			log.warn(message);
			reportWriter.writeToFile(output, null, message);
		} catch (IllegalArgumentException iae) {
			log.warn(iae.getMessage());
			reportWriter.writeToFile(output, deliveries, iae.getMessage());
		}

	}

	private void checkDeliveryRange(Position position) {
		int range = Integer.parseInt(Configuration.getProperty("dron.range.in.blocks"));
		if (position.getX() > range || position.getY() > range) {
			throw new IllegalArgumentException(format("Fuera del rango de %s cuadras. No se puede entregar", range));
		}
	}


	private List<String> filterRoutesByAvailableDrones(List<String> inputRoutes) {
		if (inputRoutes.size() > dronCount) {
			log.warn("Hay mas rutas que drones disponibles, solo se procesaran las primeras {} rutas", dronCount);
			return inputRoutes.subList(0, dronCount - 1);
		} else {
			return inputRoutes;
		}
	}


}

