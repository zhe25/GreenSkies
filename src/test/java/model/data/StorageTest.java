package model.data;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.*;

/**
 * JUnit test cases for Storage class.
 *
 * @since 22/08/2020
 * @version 1.0
 */
public class StorageTest {

  private Storage storage;

  @Before
  public void setUp() {
    storage = new Storage();
  }

  /** Create a list of airlines to use in tests */
  private List<DataType> createAirlineList() {
    List<DataType> testAirlines = new ArrayList<>();
    testAirlines.add(new Airline(3478, "Mex-Jet", "N", "", "MJT", "MEJETS", "Mexico", false));
    testAirlines.add(
        new Airline(1711, "Centre-Avia", "N", "J7", "CVC", "AVIACENTRE", "Russia", false));
    return testAirlines;
  }

  /** Create a list of airports to use in tests */
  private List<DataType> createAirportList() {
    List<DataType> testAirports = new ArrayList<>();
    testAirports.add(
        new Airport(
            11,
            "Akureyri Airport",
            "Akureyri",
            "Iceland",
            "AEY",
            "BIAR",
            65.66000366210938,
            -18.07270050048828,
            6,
            0,
            "N",
            "Atlantic/Reykjavik"));
    testAirports.add(
        new Airport(
            59,
            "Fort Resolution",
            "Fort Resolution",
            "Canada",
            "YFR",
            "CYFR",
            61.180832,
            -113.689722,
            526,
            -7,
            "A",
            "America/Edmonton"));
    return testAirports;
  }

  /** Create a list of routes to use in tests */
  private List<DataType> createRouteList() {
    List<DataType> testRoutes = new ArrayList<>();
    testRoutes.add(
        new Route("FM", 4609, "CTU", 3395, "SHA", 3391, "", 0, "757 737 738".split(" ")));
    testRoutes.add(new Route("MH", 3378, "MYY", 3266, "BTU", 3262, "Y", 0, "AT7".split(" ")));
    return testRoutes;
  }

  /** Test that the comparedRoutes are set to the correct value when setComparedRoutes is called */
  @Test
  public void setComparedRoutesTest() {
    Pair<String, String> testComparedRoutes = new Pair("KZN - AER", "AEY - YFR");
    storage.setComparedRoutes(new Pair("KZN - AER", "AEY - YFR"));
    assertEquals(storage.getComparedRoutes(), testComparedRoutes);
  }

  /**
   * Test that AnalyseEmissionResult is set to the correct value when setAnalyseEmissionResult is
   * called
   */
  @Test
  public void setAnalyseEmissionResultTest() {
    List<Double> testingList = new ArrayList<>();
    double emission = 1010.590;
    testingList.add(emission);
    storage.setAnalyseEmissionResult(emission);
    assertEquals(storage.getAnalyseEmissionResult(), testingList);
  }

  @Test
  public void addToHistoryTest() {
    int lengthofHistory = storage.getHistory().size();
    String[] rubbish1 = new String['A'];
    Route testroute = new Route("sss", 123, "AFF", 999, "PPP", 977, null, 0, rubbish1);
    storage.addToHistory(testroute);
    assertEquals(storage.getHistory().size(), lengthofHistory + 1);
  }

  @Test
  public void removeFromHistorySrcAirports() {
    storage.addToHistorySrcAirports("KZN");
    storage.addToHistorySrcAirports("KZN");
    storage.removeFromHistorySrcAirports("KZN");
    assertEquals((Integer) 1, storage.getHistorySrcAirports().get("KZN"));
  }

  @Test
  public void removeFromHistoryDestAirportsTest() {

    storage.addToHistoryDestAirports("AEK");
    storage.addToHistoryDestAirports("AEK");
    storage.removeFromHistoryDestAirports("AEK");
    assertEquals((Integer) 1, storage.getHistoryDestAirports().get("AEK"));
  }

  /**
   * Test that AnalyseDistanceResult is set to the correct value when setAnalyseDistanceResult is
   * called
   */
  @Test
  public void setAnalyseDistanceResultTest() {
    List<Double> testingList = new ArrayList<>();
    double emission = 12345.4311;
    testingList.add(emission);
    storage.setAnalyseDistanceResult(emission);
    assertEquals(storage.getAnalyseDistanceResult(), testingList);
  }

  /**
   * Test that the hashmap holding the history Dest airpots is set to a given value when
   * addToHistoryDestAirports is called
   */
  @Test
  public void addToHistoryDestAirportsTest() {
    HashMap<String, Integer> testMap = new HashMap<>();
    testMap.put("TestDestAirport", 1);
    storage.addToHistoryDestAirports("TestDestAirport");
    assertEquals(storage.getHistoryDestAirports(), testMap);
  }

  /**
   * Test that the hashmap holding the history src airpots is set to a given value when
   * addToHistorySrcAirports is called
   */
  @Test
  public void addToHistorySrcAirportsTest() {
    HashMap<String, Integer> testMap1 = new HashMap<>();
    testMap1.put("TestSrcAirport", 1);
    storage.addToHistoryDestAirports("TestSrcAirport");
    assertEquals(storage.getHistoryDestAirports(), testMap1);
  }

  /**
   * Test that airlines is set to given values when setData is called with airlines as the datatype.
   */
  @Test
  public void setDataAirlineUpdatedTest() {
    List<DataType> testAirlines = createAirlineList();
    storage.setData(testAirlines, "Airline", "airlinetest.csv");
    assertEquals(testAirlines, storage.getAirlines());
  }

  /** Test that airport is not changed when setData is called with airlines as the datatype. */
  @Test
  public void setDataAirportUnchangedTest() {
    List<DataType> testAirlines = createAirlineList();
    storage.setData(testAirlines, "Airline", "airporttest.csv");
    assertArrayEquals((new ArrayList<Airport>()).toArray(), storage.getAirports().toArray());
  }

  /** Test that routes is not changed when setData is called with airlines as the datatype. */
  @Test
  public void setDataRouteUnchangedTest() {
    List<DataType> testAirlines = createAirlineList();
    storage.setData(testAirlines, "Airline", "routetest.csv");
    assertArrayEquals((new ArrayList<Route>()).toArray(), storage.getRoutes().toArray());
  }

  /**
   * Test that airports is set to given values when setData is called with airports as the datatype.
   */
  @Test
  public void setDataAirportTest() {
    List<DataType> testAirports = createAirportList();
    storage.setData(testAirports, "Airport", "airporttest.csv");
    assertEquals(testAirports, storage.getAirports());
  }

  /** Test that routes is set to given values when setData is called with routes as the datatype. */
  @Test
  public void setDataRouteTest() {
    List<DataType> testRoutes = createRouteList();
    storage.setData(testRoutes, "Route", "routetest.csv");
    assertEquals(testRoutes, storage.getRoutes());
  }

  /** Test that an exception is raised when setData is called with an incorrect datatype. */
  @Test
  public void setDataInvalidDataTypeTest() {
    List<DataType> testData = new ArrayList<>();
    try {
      storage.setData(testData, "Potato", "potatotest.csv");
      fail();
    } catch (IllegalArgumentException ignored) {

    }
  }

  /**
   * Test that an empty list is returned when getAirlineFileNames is called and no data has been
   * added to storage.
   */
  @Test
  public void getAirlineFileNamesNoneAddedTest() {
    assertTrue(storage.getAirlineFileNames().isEmpty());
  }

  /**
   * Test when a file of another data type has been added, that filename is not returned by
   * getAirlineFileNames.
   */
  @Test
  public void getAirlineFileNamesOtherAddedTest() {
    storage.setData(createRouteList(), "Route", "routestest.csv");
    assertTrue(storage.getAirlineFileNames().isEmpty());
  }

  /**
   * Test that a list containing one filename is returned when only one Airline file has been added.
   */
  @Test
  public void getAirlineFileNamesOneFileTest() {
    storage.setData(createAirlineList(), "Airline", "airlinetest.csv");
    String[] expected = {"airlinetest.csv"};
    assertArrayEquals(expected, storage.getAirlineFileNames().toArray());
  }

  /**
   * Test that a list containing all added filenames is returned when multiple Airline files have
   * been added.
   */
  @Test
  public void getAirlineFileNamesMultipleTest() {
    storage.setData(createAirlineList(), "Airline", "apple.csv");
    storage.setData(createAirlineList(), "Airline", "banana.csv");
    storage.setData(createAirlineList(), "Airline", "cherry.csv");
    String[] expected = {"apple.csv", "banana.csv", "cherry.csv"};
    List<String> actual = storage.getAirlineFileNames();
    Collections.sort(actual);
    assertArrayEquals(expected, actual.toArray());
  }

  /**
   * Test that setCurrentAirlineFile changes the value of currentAirlineFile when called with the
   * name of an Airline file which has been stored.
   */
  @Test
  public void setCurrentAirlineFileNameStoredTest() {
    storage.setData(createAirlineList(), "Airline", "airlinetest.csv");
    storage.setData(createAirlineList(), "Airline", "airlinetest2.csv");
    storage.setCurrentAirlineFile("airlinetest.csv");
    assertEquals("airlinetest.csv", storage.getCurrentAirlineFile());
  }

  /**
   * Test that setCurrentAirlineFile does not change the value of currentAirlineFile if it is called
   * with a name that is not stored in airlineFiles.
   */
  @Test
  public void setCurrentAirlienFileNameNotStoredTest() {
    storage.setData(createAirlineList(), "Airline", "a.csv");
    storage.setCurrentAirlineFile("airlinetest.csv");
    assertEquals("a.csv", storage.getCurrentAirlineFile());
  }

  /** Test getAirlines returns an empty list when currentAirlineFile is set to null. */
  @Test
  public void getAirlinesNullTest() {
    storage.setCurrentAirlineFile(null);
    assertTrue(storage.getAirlines().isEmpty());
  }

  /**
   * Test getAirlines returns a list of airlines in the current file when currentAirlineFile is not
   * set to null.
   */
  @Test
  public void getAirlinesNotNullTest() {
    storage.setData(createAirlineList(), "Airline", "airlines.csv");
    storage.setCurrentAirlineFile("airlines.csv");
    assertArrayEquals(createAirlineList().toArray(), storage.getAirlines().toArray());
  }

  /**
   * Test that an empty list is returned when getAirportFileNames is called and no data has been
   * added to storage.
   */
  @Test
  public void getAirportFileNamesNoneAddedTest() {
    assertTrue(storage.getAirportFileNames().isEmpty());
  }

  /**
   * Test when a file of another data type has been added, that filename is not returned by
   * getAirportFileNames.
   */
  @Test
  public void getAirportFileNamesOtherAddedTest() {
    storage.setData(createRouteList(), "Route", "routestest.csv");
    assertTrue(storage.getAirportFileNames().isEmpty());
  }

  /**
   * Test that a list containing one filename is returned when only one Airport file has been added.
   */
  @Test
  public void getAirportFileNamesOneFileTest() {
    storage.setData(createAirportList(), "Airport", "airporttest.csv");
    String[] expected = {"airporttest.csv"};
    assertArrayEquals(expected, storage.getAirportFileNames().toArray());
  }

  /**
   * Test that a list containing all added filenames is returned when multiple Airport files have
   * been added.
   */
  @Test
  public void getAirportFileNamesMultipleTest() {
    storage.setData(createAirportList(), "Airport", "apple.csv");
    storage.setData(createAirportList(), "Airport", "banana.csv");
    storage.setData(createAirportList(), "Airport", "cherry.csv");
    String[] expected = {"apple.csv", "banana.csv", "cherry.csv"};
    List<String> actual = storage.getAirportFileNames();
    Collections.sort(actual);
    assertArrayEquals(expected, actual.toArray());
  }

  /**
   * Test that setCurrentAirportFile changes the value of currentAirportFile when called with the
   * name of an Airport file which has been stored.
   */
  @Test
  public void setCurrentAirportFileNameStoredTest() {
    storage.setData(createAirportList(), "Airport", "airporttest.csv");
    storage.setData(createAirportList(), "Airport", "airporttest2.csv");
    storage.setCurrentAirportFile("airporttest.csv");
    assertEquals("airporttest.csv", storage.getCurrentAirportFile());
  }

  /**
   * Test that setCurrentAirportFile does not change the current airport file when called with a
   * filename that has not been stored.
   */
  @Test
  public void setCurrentAirportFileNameNotStoredTest() {
    storage.setData(createAirportList(), "Airport", "a.csv");
    storage.setCurrentAirportFile("airporttest.csv");
    assertEquals("a.csv", storage.getCurrentAirportFile());
  }

  /** Test getAirports returns an empty list when currentAirportFile is set to null. */
  @Test
  public void getAirportsNullTest() {
    storage.setCurrentAirportFile(null);
    assertTrue(storage.getAirports().isEmpty());
  }

  /**
   * Test getAirports returns a list of airports in the current file when currentAirportFile is not
   * set to null.
   */
  @Test
  public void getAirportsNotNullTest() {
    storage.setData(createAirportList(), "Airport", "airports.csv");
    storage.setCurrentAirportFile("airports.csv");
    assertArrayEquals(createAirportList().toArray(), storage.getAirports().toArray());
  }

  /**
   * Test that an empty list is returned when getRouteFileNames is called and no data has been added
   * to storage.
   */
  @Test
  public void getRouteFileNamesNoneAddedTest() {
    assertTrue(storage.getRouteFileNames().isEmpty());
  }

  /**
   * Test when a file of another data type has been added, that filename is not returned by
   * getRouteFileNames.
   */
  @Test
  public void getRouteFileNamesOtherAddedTest() {
    storage.setData(createAirportList(), "Airport", "airportstest.csv");
    assertTrue(storage.getRouteFileNames().isEmpty());
  }

  /**
   * Test that a list containing one filename is returned when only one Route file has been added.
   */
  @Test
  public void getRouteFileNamesOneFileTest() {
    storage.setData(createRouteList(), "Route", "routetest.csv");
    String[] expected = {"routetest.csv"};
    assertArrayEquals(expected, storage.getRouteFileNames().toArray());
  }

  /**
   * Test that a list containing all added filenames is returned when multiple Route files have been
   * added.
   */
  @Test
  public void getRouteFileNamesMultipleTest() {
    storage.setData(createRouteList(), "Route", "apple.csv");
    storage.setData(createRouteList(), "Route", "banana.csv");
    storage.setData(createRouteList(), "Route", "cherry.csv");
    String[] expected = {"apple.csv", "banana.csv", "cherry.csv"};
    List<String> actual = storage.getRouteFileNames();
    Collections.sort(actual);
    assertArrayEquals(expected, actual.toArray());
  }

  /**
   * Test that setCurrentRouteFile changes the value of currentRouteFile when called with the name
   * of an Route file which has been stored.
   */
  @Test
  public void setCurrentRouteFileNameStoredTest() {
    storage.setData(createRouteList(), "Route", "routetest.csv");
    storage.setData(createRouteList(), "Route", "routetest2.csv");
    storage.setCurrentRouteFile("routetest.csv");
    assertEquals("routetest.csv", storage.getCurrentRouteFile());
  }

  /**
   * Test that setCurrentRouteFile doesn't change the current route file when called with a filename
   * that has not been stored.
   */
  @Test
  public void setCurrentRouteFileNameNotStoredTest() {
    storage.setData(createRouteList(), "Route", "a.csv");
    storage.setCurrentAirlineFile("routetest.csv");
    assertEquals("a.csv", storage.getCurrentRouteFile());
  }

  /** Test getRoutes returns an empty list when currentRouteFile is set to null. */
  @Test
  public void getRoutesNullTest() {
    storage.setCurrentRouteFile(null);
    assertTrue(storage.getRoutes().isEmpty());
  }

  /**
   * Test getRoutes returns a list of routes in the current file when currentRouteFile is not set to
   * null.
   */
  @Test
  public void getRoutesNotNullTest() {
    storage.setData(createRouteList(), "Route", "routes.csv");
    storage.setCurrentRouteFile("routes.csv");
    assertArrayEquals(createRouteList().toArray(), storage.getRoutes().toArray());
  }

  /**
   * Test that setData changes currentFilename to the given filename when the given filename is not
   * null.
   */
  @Test
  public void setDataFileNameChangeTest() {
    storage.setData(createAirportList(), "Airport", "airportstest.csv");
    storage.setData(createAirportList(), "Airport", "airportstest2.csv");
    assertEquals("airportstest2.csv", storage.getCurrentAirportFile());
  }
}
