package service_layer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParseEurofxrefData {



	private ExchangeRatesDataRetriever fileDataRetriever;


	@BeforeEach
	public void setup() {
		fileDataRetriever = new FileDataRetriever();
	}


	/**
	 * Testing FileDataRetriever.retrieveData
	 * Confirming that the method will read the file and parseEurofx it.
	 */
	@Test
	public void loadDataTest() {
		 assertNotNull(fileDataRetriever.retrieveData());
	}

}
