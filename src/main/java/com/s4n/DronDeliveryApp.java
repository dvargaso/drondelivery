package com.s4n;

import com.s4n.facade.DronDeliveryFacade;
import com.s4n.facade.impl.DronDeliveryFacadeImpl;
import com.s4n.io.*;
import com.s4n.io.impl.DronReportWriterImpl;
import com.s4n.io.impl.FileNameProviderImpl;
import com.s4n.io.impl.FileReaderImpl;
import com.s4n.service.DeliveryService;
import com.s4n.service.impl.DeliveryServiceImpl;
import com.s4n.util.Configuration;
import com.s4n.validation.RouteValidator;
import com.s4n.validation.impl.RouteValidatorImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
public class DronDeliveryApp {

	private final static int AVAILABLE_DRONES = Integer.parseInt(Configuration.getProperty("dron.count"));

	public static void main(String[] args) {

		log.info("Applicacion de entregas con drones iniciada");
		FileReader reader = new FileReaderImpl();
		DronReportWriter reportWriter = new DronReportWriterImpl();
		DeliveryService deliveryService = new DeliveryServiceImpl();
		Executor threadPool = Executors.newFixedThreadPool(AVAILABLE_DRONES);
		RouteValidator routeValidator = new RouteValidatorImpl();
		FileNameProvider fileNameProvider = new FileNameProviderImpl();

		DronDeliveryFacade facade = new DronDeliveryFacadeImpl(reader, reportWriter, deliveryService, AVAILABLE_DRONES,
				threadPool, routeValidator, fileNameProvider);

		log.info("Iniciando entregas con drones");
		facade.performDeliveries();
		log.info("Entregas con drones finalizadas");

	}


}
