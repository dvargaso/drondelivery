package com.s4n.facade;

import com.s4n.domain.Dron;
import com.s4n.domain.Position;
import com.s4n.facade.impl.DronDeliveryFacadeImpl;
import com.s4n.factory.DronFactory;
import com.s4n.io.FileNameProvider;
import com.s4n.io.FileReader;
import com.s4n.io.impl.DronReportWriterImpl;
import com.s4n.service.DeliveryService;
import com.s4n.util.Configuration;
import com.s4n.validation.impl.RouteValidatorImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.s4n.domain.Direction.NORTE;
import static com.s4n.domain.Direction.ORIENTE;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DronDeliveryFacadeTest {

	@Mock
	private FileReader reader;

	@Mock
	private DronReportWriterImpl reportWriter;
	@Mock
	private DeliveryService deliveryService;

	@Mock
	private RouteValidatorImpl dronValidator;

	@Mock
	private FileNameProvider fileNameProvider;

	@Captor
	private ArgumentCaptor<String> fileNameCaptor;

	@Captor
	private ArgumentCaptor<String> inputPathCaptor;

	@Captor
	private ArgumentCaptor<String> outputPathCaptor;

	@Captor
	private ArgumentCaptor<String> messageArgumentCaptor;

	@Captor
	private ArgumentCaptor<List<Position>> positionArgumentCaptor;


	private int dronCount;
	private Executor threadPool;
	private DronDeliveryFacade deliveryFacade;

	private String inputPath1;
	private String inputPath2;
	private String outputPath1;
	private String outputPath2;

	private String route1;
	private String route2;
	private Dron dron1;
	private Dron dron2;
	private Position position1;
	private Position position2;


	@BeforeEach
	void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		dronCount = 1;
		threadPool = Executors.newFixedThreadPool(dronCount);
		deliveryFacade = new DronDeliveryFacadeImpl(reader, reportWriter, deliveryService, dronCount, threadPool,
				dronValidator, fileNameProvider);

		inputPath1 = "in01.txt";
		inputPath2 = "in02.txt";

		outputPath1 = "out01.txt";
		outputPath2 = "out02.txt";

		route1 = "A";
		route2 = "ADA";

		dron1 = DronFactory.createNewDron(asList(route1));
		dron2 = DronFactory.createNewDron(asList(route2));

		position1 = new Position(0, 1, NORTE);
		position2 = new Position(1, 1, ORIENTE);
		setUpDefaultMockBehaviours();


	}

	@SneakyThrows
	private void executeAndWait() {
		deliveryFacade.performDeliveries();
		Thread.sleep(1000L);
	}

	private void setUpDefaultMockBehaviours() throws IOException {
		//Setup one sample behavior
		doReturn(asList(inputPath1)).when(fileNameProvider).resolveInputFileNames(dronCount);
		doReturn(inputPath1).when(fileNameProvider).provideInputPath(inputPath1);
		doReturn(outputPath1).when(fileNameProvider).provideOutputPath(inputPath1);
		doReturn(asList(route1)).when(reader).readFile(inputPath1);
		doNothing().when(dronValidator).validateRawRoute(anyString());
		doReturn(position1).when(deliveryService).followRoute(dron1.getCurrentPosition(), route1);
		doNothing().when(reportWriter).writeToFile(outputPath1, asList(position1), null);
	}

	private void setUpMockBehaviorForSecondDrone()throws IOException{
		doReturn(inputPath2).when(fileNameProvider).provideInputPath(inputPath2);
		doReturn(outputPath2).when(fileNameProvider).provideOutputPath(inputPath2);
		doReturn(asList(route2)).when(reader).readFile(inputPath2);
		doReturn(position2).when(deliveryService).followRoute(dron2.getCurrentPosition(), route2);
		doNothing().when(reportWriter).writeToFile(outputPath2, asList(position2), null);
	}

	@Test
	void testPerformDeliveriesSingleDron_happyPath() throws IOException {

		executeAndWait();

		assertBasicPaths();

		verify(reportWriter, times(dronCount)).writeToFile(outputPath1, asList(position1), null);

	}

	@Test
	void testPerformDeliveriesMultipleDrones_happyPath()throws IOException  {
		//Setup behavior for second drone
		dronCount = 2;
		threadPool = Executors.newFixedThreadPool(dronCount);
		deliveryFacade = new DronDeliveryFacadeImpl(reader, reportWriter, deliveryService, dronCount, threadPool,
				dronValidator, fileNameProvider);

		doReturn(asList(inputPath1, inputPath2)).when(fileNameProvider).resolveInputFileNames(dronCount);

		setUpMockBehaviorForSecondDrone();

		executeAndWait();

		verify(fileNameProvider).resolveInputFileNames(dronCount);
		verify(fileNameProvider, times(dronCount)).provideInputPath(fileNameCaptor.capture());
		assertTrue(fileNameCaptor.getAllValues().contains(inputPath1));
		assertTrue(fileNameCaptor.getAllValues().contains(inputPath2));
		verify(fileNameProvider, times(dronCount)).provideOutputPath(inputPathCaptor.capture());
		assertTrue(inputPathCaptor.getAllValues().contains(inputPath1));
		assertTrue(inputPathCaptor.getAllValues().contains(inputPath2));
		verify(reportWriter, times(dronCount)).writeToFile(outputPathCaptor.capture(), positionArgumentCaptor.capture()
				, isNull());
		assertTrue(outputPathCaptor.getAllValues().contains(outputPath1));
		assertTrue(outputPathCaptor.getAllValues().contains(outputPath2));

		assertTrue(positionArgumentCaptor.getAllValues().contains(asList(position1)));
		assertTrue(positionArgumentCaptor.getAllValues().contains(asList(position2)));

	}

	@Test
	void testDronWithEmptyRoutes_shouldFailGracefully() throws IOException, InterruptedException {
		String errorMsg = "El archivo in01.txt no contiene rutas";
		doReturn(Collections.EMPTY_LIST).when(reader).readFile(inputPath1);
		doNothing().when(reportWriter).writeToFile(outputPath1, null, errorMsg);

		executeAndWait();

		assertBasicPaths();

		verify(reportWriter, times(dronCount)).writeToFile(outputPath1, null, errorMsg);


	}

	@Test
	void testCantOpenRouteFiles_shouldFailGracefully() throws IOException, InterruptedException {
		String errorMsg = "Error leyendo ruta in01.txt";
		doThrow(new IOException()).when(reader).readFile(inputPath1);
		doNothing().when(reportWriter).writeToFile(outputPath1, null, errorMsg);

		executeAndWait();

		assertBasicPaths();

		verify(reportWriter, times(dronCount)).writeToFile(outputPath1, null, errorMsg);

	}


	void testOutOfBoundsDelivery_shouldFailGracefully() throws IOException {
		String errorMsg = "Fuera del rango de 10 cuadras. No se puede entregar";
		int range = Integer.parseInt(Configuration.getProperty("dron.range.in.blocks"));
		String routeToOutOFBounds = "OutOfBounds";


		Position outOfBoundsPosition = new Position(0, (range + 1), NORTE);
		doReturn(outOfBoundsPosition).when(deliveryService).followRoute(dron1.getCurrentPosition(), routeToOutOFBounds);
		doReturn(asList(routeToOutOFBounds)).when(reader).readFile(inputPath1);
		doNothing().when(reportWriter).writeToFile(outputPath1, Collections.EMPTY_LIST, errorMsg);

		executeAndWait();

		assertBasicPaths();

		verify(reportWriter, times(dronCount)).writeToFile(outputPath1, Collections.EMPTY_LIST, errorMsg);

	}

	@Test
	void testMoreRoutesThanDrones_shoulUseAllAvailableDrones() throws IOException {
		setUpMockBehaviorForSecondDrone();

		executeAndWait();

		assertBasicPaths();

		verify(reportWriter, times(dronCount)).writeToFile(outputPath1, asList(position1), null);

	}

	private void assertBasicPaths() {
		verify(fileNameProvider).resolveInputFileNames(dronCount);
		verify(fileNameProvider, times(dronCount)).provideInputPath(fileNameCaptor.capture());
		assertEquals(inputPath1, fileNameCaptor.getValue());
		verify(fileNameProvider, times(dronCount)).provideOutputPath(inputPathCaptor.capture());
		assertEquals(inputPath1, inputPathCaptor.getValue());
	}


}
