package controller.analysis;

import model.data.Airline;
import model.data.Airport;
import model.data.Route;
import model.data.Storage;
import model.loader.Loader;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Unit test for Searcher class.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class SearcherTest {

  private Storage storage;
  private Loader loader;

  @Before
  public void setUp() throws SQLException {
    storage = new Storage();
    loader = new Loader(storage);
    try {
      loader.loadFile("../seng202_project/src/test/java/TestFiles/SearcherRoutesTest.csv", "Route");
      loader.loadFile(
          "../seng202_project/src/test/java/TestFiles/SearcherAirportsTest.csv", "Airport");
      loader.loadFile(
          "../seng202_project/src/test/java/TestFiles/SearcherAirlinesTest.csv", "Airline");
    } catch (FileSystemException | FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Verify that when searchRoutes is called with a search term that matches no terms in the file, a
   * RuntimeException is thrown.
   */
  @Test
  public void searchRoutesNoMatchTest() {
    try {
      Searcher.searchRoutes("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "Airline", storage.getRoutes());
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that when searchRoutes is called with a search term that matches one entry in the file,
   * an arrayList containing just that entry is returned.
   */
  @Test
  public void searchRoutesOneMatchTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    expectedResults.add(new Route("2B", 410, "GYD", 2922, "NBC", 6969, "", 0, "CR2".split(" ")));
    ArrayList<Route> results = Searcher.searchRoutes("GYD", "Source", storage.getRoutes());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when searchRoutes is called with a search term that matches multiple entries in the
   * file, an arrayList containing all those entries in the same order they appear in the file is
   * returned.
   */
  @Test
  public void searchRoutesMultipleMatchTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    expectedResults.add(new Route("2B", 410, "DME", 4029, "NBC", 6969, "", 0, "CR2".split(" ")));
    expectedResults.add(new Route("2B", 410, "GYD", 2922, "NBC", 6969, "", 0, "CR2".split(" ")));
    expectedResults.add(new Route("2B", 410, "LED", 2948, "NBC", 6969, "", 0, "CR2".split(" ")));
    expectedResults.add(new Route("2B", 410, "SVX", 2975, "NBC", 6969, "", 0, "CR2".split(" ")));
    ArrayList<Route> results = Searcher.searchRoutes("NBC", "Destination", storage.getRoutes());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when an illegal search type is passed into searchRoutes it raises an
   * IllegalArgumentException.
   */
  @Test
  public void searchRoutesIllegalTypeTest() {
    try {
      Searcher.searchRoutes("NBC", "Country", storage.getRoutes());
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that searchRoutes can return the correct entry when that entry is towards the end of a
   * file over 10,000 lines long.
   */
  @Test
  public void searchRoutesLongFileTest() {
    storage = new Storage();
    loader = new Loader(storage);
    try {
      loader.loadFile("../seng202_project/src/test/java/TestFiles/routes.csv", "Route");
    } catch (Exception e) {
      fail();
    }
    ArrayList<Route> expectedResults = new ArrayList<>();
    expectedResults.add(new Route("YO", 16150, "HME", 238, "LGW", 502, "Y", 0, "320".split(" ")));
    expectedResults.add(new Route("YO", 16150, "LGW", 502, "HME", 238, "Y", 0, "320".split(" ")));
    expectedResults.add(new Route("YO", 16150, "MCM", 4264, "NCE", 1354, "", 0, "NDE".split(" ")));
    expectedResults.add(new Route("YO", 16150, "NCE", 1354, "MCM", 4264, "", 0, "NDE".split(" ")));
    ArrayList<Route> results = Searcher.searchRoutes("YO", "Airline", storage.getRoutes());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when searchRoutes is called with a searchTerm which matches a term in the file but
   * is in a different, an arrayList containing the matching entry is returned.
   */
  @Test
  public void searchRoutesCaseSensitiveTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    expectedResults.add(new Route("2B", 410, "GYD", 2922, "NBC", 6969, "", 0, "CR2".split(" ")));
    ArrayList<Route> results = Searcher.searchRoutes("gyd", "Source", storage.getRoutes());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when searchAirports is called with a search term that matches no terms in the file,
   * a RuntimeException is thrown.
   */
  @Test
  public void searchAirportsNoMatchTest() {
    try {
      Searcher.searchAirports("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ICAO", storage.getAirports());
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that when searchAirports is called with a search term that matches one entry in the
   * file, an arrayList containing just that entry is returned.
   */
  @Test
  public void searchAirportsOneMatchTest() {
    ArrayList<Airport> expectedResults = new ArrayList<>();
    expectedResults.add(
        new Airport(
            6,
            "Wewak Intl",
            "Wewak",
            "Papua New Guinea",
            "WWK",
            "AYWK",
            -3.583828,
            143.669186,
            19,
            10,
            "U",
            "Pacific/Port_Moresby"));
    ArrayList<Airport> results = Searcher.searchAirports("WWK", "IATA", storage.getAirports());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when searchAirports is called with a search term that matches multiple entries in
   * the file, an arrayList containing all those entries in the same order they appear in the file
   * is returned.
   */
  @Test
  public void searchAirportsMultipleMatchTest() {
    ArrayList<Airport> expectedResults = new ArrayList<>();
    expectedResults.add(
        new Airport(
            7,
            "Narsarsuaq",
            "Narssarssuaq",
            "Greenland",
            "UAK",
            "BGBW",
            61.160517,
            -45.425978,
            112,
            -3,
            "E",
            "America/Godthab"));
    expectedResults.add(
        new Airport(
            8,
            "Nuuk",
            "Godthaab",
            "Greenland",
            "GOH",
            "BGGH",
            64.190922,
            -51.678064,
            283,
            -3,
            "E",
            "America/Godthab"));
    expectedResults.add(
        new Airport(
            9,
            "Sondre Stromfjord",
            "Sondrestrom",
            "Greenland",
            "SFJ",
            "BGSF",
            67.016969,
            -50.689325,
            165,
            -3,
            "E",
            "America/Godthab"));
    expectedResults.add(
        new Airport(
            10,
            "Thule Air Base",
            "Thule",
            "Greenland",
            "THU",
            "BGTL",
            76.531203,
            -68.703161,
            251,
            -4,
            "E",
            "America/Thule"));
    ArrayList<Airport> results =
        Searcher.searchAirports("Greenland", "Country", storage.getAirports());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when an illegal search type is passed into searchAirports it raises an
   * IllegalArgumentException.
   */
  @Test
  public void searchAirportsIllegalTypeTest() {
    try {
      Searcher.searchAirports("Greenland", "Airline", storage.getAirports());
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that searchAirports can return the correct entry when that entry is towards the end of a
   * file over 8,000 lines long.
   */
  @Test
  public void searchAirportsLongFileTest() {
    storage = new Storage();
    loader = new Loader(storage);
    try {
      loader.loadFile("../seng202_project/src/test/java/TestFiles/airports.csv", "Airport");
    } catch (Exception e) {
      fail();
    }
    ArrayList<Airport> expectedResults = new ArrayList<>();
    expectedResults.add(
        new Airport(
            9428,
            "Bessemer",
            "Bessemer",
            "United States",
            "EKY",
            "KEKY",
            33.1876,
            -86.5558,
            700,
            -6,
            "A",
            "America/Chicago"));
    ArrayList<Airport> results = Searcher.searchAirports("Bessemer", "Name", storage.getAirports());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when searchAirports is called with a searchTerm which matches a term in the file
   * but is in a different, an arrayList containing the matching entry is returned.
   */
  @Test
  public void searchAirportsCaseSensitiveTest() {
    ArrayList<Airport> expectedResults = new ArrayList<>();
    expectedResults.add(
        new Airport(
            25,
            "Tofino",
            "Tofino",
            "Canada",
            "YAZ",
            "CYAZ",
            49.082222,
            -125.7725,
            80,
            -8,
            "A",
            "America/Vancouver"));
    ArrayList<Airport> results = Searcher.searchAirports("TOFINO", "City", storage.getAirports());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when searchAirlines is called with a search term that matches no terms in the file,
   * a RuntimeException is thrown.
   */
  @Test
  public void searchAirlinesNoMatchTest() {
    try {
      Searcher.searchAirlines("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "IATA", storage.getAirlines());
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that when searchAirlines is called with a search term that matches one entry in the
   * file, an arrayList containing just that entry is returned.
   */
  @Test
  public void searchAirlinesOneMatchTest() {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    expectedResults.add(
        new Airline(29, "Askari Aviation", "\\N", "4K", "AAS", "AL-AAS", "Pakistan", true));
    ArrayList<Airline> results =
        Searcher.searchAirlines("Askari Aviation", "Name", storage.getAirlines());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when searchAirlines is called with a search term that matches multiple entries in
   * the file, an arrayList containing all those entries in the same order they appear in the file
   * is returned.
   */
  @Test
  public void searchAirlinesMultipleMatchTest() {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    expectedResults.add(new Airline(5, "213 Flight Unit", "\\N", "", "TFU", "", "Russia", false));
    expectedResults.add(
        new Airline(
            6,
            "223 Flight Unit State Airline",
            "\\N",
            "",
            "CHD",
            "CHKALOVSK-AVIA",
            "Russia",
            false));
    expectedResults.add(
        new Airline(7, "224th Flight Unit", "\\N", "", "TTF", "CARGO UNIT", "Russia", false));
    expectedResults.add(
        new Airline(41, "Abakan-Avia", "\\N", "", "ABG", "ABAKAN-AVIA", "Russia", false));
    ArrayList<Airline> results =
        Searcher.searchAirlines("Russia", "Country", storage.getAirlines());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when an illegal search type is passed into searchAirlines it raises an
   * IllegalArgumentException.
   */
  @Test
  public void searchAirlinesIllegalTypeTest() {
    try {
      Searcher.searchAirlines("Russia", "Airport", storage.getAirlines());
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that searchAirlines can return the correct entry when that entry is towards the end of a
   * file over 6,000 lines long.
   */
  @Test
  public void searchAirlinesLongFileTest() {
    storage = new Storage();
    loader = new Loader(storage);
    try {
      String message =
          loader.loadFile("../seng202_project/src/test/java/TestFiles/airlines.csv", "Airline");
    } catch (Exception e) {
      fail();
    }
    List<Airline> airlines = storage.getAirlines();
    ArrayList<Airline> expectedResults = new ArrayList<>();
    expectedResults.add(
        new Airline(21179, "Thai Vietjet Air", "", "", "TVJ", "THAIVIET JET", "Thailand", true));
    ArrayList<Airline> results = Searcher.searchAirlines("TVJ", "ICAO", storage.getAirlines());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when searchAirlines is called with a searchTerm which matches a term in the file
   * but is in a different, an arrayList containing the matching entry is returned.
   */
  @Test
  public void searchAirlinesCaseSensitiveTest() {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    expectedResults.add(
        new Airline(29, "Askari Aviation", "\\N", "4K", "AAS", "AL-AAS", "Pakistan", true));
    ArrayList<Airline> results =
        Searcher.searchAirlines("askari aviation", "Name", storage.getAirlines());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that searchAirlines returns the expected results when a string is converted into a
   * boolean by searching for active status.
   */
  @Test
  public void searchAirlinesActiveStatusTest()
      throws FileNotFoundException, FileSystemException, SQLException {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    expectedResults.add(
        new Airline(3, "1Time Airline", "\\N", "1T", "RNX", "NEXTIME", "South Africa", true));
    expectedResults.add(
        new Airline(10, "40-Mile Air", "\\N", "Q5", "MLA", "MILE-AIR", "United States", true));
    loader.loadFile(
        "../seng202_project/src/test/java/TestFiles/SearcherAirlinesActiveStatusTest.csv",
        "Airline");
    ArrayList<Airline> results =
        Searcher.searchAirlines("True", "Active status", storage.getAirlines());
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }
}
