package com.s4n;

import com.s4n.facade.DronDeliveryFacadeImpl;
import com.s4n.io.DronReportWriter;
import com.s4n.io.FileReader;
import com.s4n.service.DeliveryService;
import com.s4n.service.DeliveryServiceImpl;


public class DronDeliveryApp {

	private final static int AVAILABLE_DRONES = 20;

	public static void main(String[] args) {

		FileReader reader = new FileReader();
		DronReportWriter reportWriter = new DronReportWriter();
		DeliveryService deliveryService = new DeliveryServiceImpl();

		DronDeliveryFacadeImpl facade = new DronDeliveryFacadeImpl(reader, deliveryService, AVAILABLE_DRONES,
				reportWriter);


		facade.performDeliveries();

	}

	private void startComponents() {

	}


}
