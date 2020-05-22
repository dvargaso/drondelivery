package com.s4n;

import com.s4n.facade.DronDeliveryFacadeImpl;
import com.s4n.io.DronReportWriter;
import com.s4n.io.FileNameProvider;
import com.s4n.io.impl.DronReportWriterImpl;
import com.s4n.io.impl.FileNameProviderImpl;
import com.s4n.io.impl.FileReader;
import com.s4n.service.DeliveryService;
import com.s4n.service.DeliveryServiceImpl;
import com.s4n.validation.DronValidatorImpl;
import com.s4n.validation.RouteValidator;


public class DronDeliveryApp {

	private final static int AVAILABLE_DRONES = 20;

	public static void main(String[] args) {

		FileReader reader = new FileReader();
		DronReportWriter reportWriter = new DronReportWriterImpl();
		DeliveryService deliveryService = new DeliveryServiceImpl();
		RouteValidator routeValidator = new DronValidatorImpl();
		FileNameProvider fileNameProvider = new FileNameProviderImpl();

		DronDeliveryFacadeImpl facade = new DronDeliveryFacadeImpl(reader, deliveryService, AVAILABLE_DRONES,
				reportWriter, routeValidator, fileNameProvider);


		facade.performDeliveries();

	}

	private void startComponents() {

	}


}
