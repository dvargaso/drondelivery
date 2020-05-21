package com.s4n.facade;

import com.s4n.domain.Dron;
import com.s4n.domain.Position;
import com.s4n.io.DronReportWriter;
import com.s4n.io.FileReader;
import com.s4n.service.DeliveryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@AllArgsConstructor
public class DronDeliveryFacadeImpl implements DronDeliveryFacade {

	public static final String DRONE_ROUTE_FILE_PREFIX = "in";
	public static final String TXT_FILE_POSFIX = ".txt";
	public static final String DRONE_OUTPUT_FILE_PREFIX = "out";



	private FileReader reader;
	private DeliveryService deliveryService;
	private int maxDronCount;
	private DronReportWriter reportWriter;


	@Override
	public List<Dron> performDeliveries() {
		List<Dron> drones = new ArrayList<>();
		List<String> inputRoutes = processInputFileNames(maxDronCount);
		inputRoutes.stream().forEach(routesPath -> {
			String input = "deliveryData/input/" + routesPath;
			String output = "deliveryData/output/" + routesPath.replace(DRONE_ROUTE_FILE_PREFIX, DRONE_OUTPUT_FILE_PREFIX);
			try {
				Dron dron = new Dron(reader.readFile(input));
				dron.getRoutes().forEach(route -> {
					Position delivery = deliveryService.followRoute(dron.getCurrentPosition(), route);
					dron.getDeliveries().add(new Position(delivery.getX(), delivery.getY(), delivery.getDirection()));
				});
				reportWriter.writeToFile(output, dron.getDeliveries());
				drones.add(dron);
			} catch (IOException e) {
				//TODO handle error
			}


		});

		return drones;

	}



	private List<String> processInputFileNames(int count) {
		List<String> fileNameList = new ArrayList<>();
		IntStream.rangeClosed(1, count).forEach(i -> {
			String number = (i < 10) ? ("0" + i) : String.valueOf(i);
			fileNameList.add(DRONE_ROUTE_FILE_PREFIX + number + TXT_FILE_POSFIX);
		});

		return fileNameList;
	}


}
