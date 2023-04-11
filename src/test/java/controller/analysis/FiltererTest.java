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
import java.util.HashMap;
import static org.junit.Assert.*;

/**
 * Unit test for Filterer class.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class FiltererTest {

  private Storage storage;
  private Loader loader;
  private Filterer filterer;

  @Before
  public void setUp() throws SQLException {
    storage = new Storage();
    loader = new Loader(storage);
    filterer = new Filterer();
    try {
      loader.loadFile(
          "../seng202_project/src/test/java/TestFiles/FiltererAirportsTest.csv", "Airport");
      loader.loadFile(
          "../seng202_project/src/test/java/TestFiles/FiltererAirlinesTest.csv", "Airline");
      loader.loadFile("../seng202_project/src/test/java/TestFiles/FiltererRoutesTest.csv", "Route");
    } catch (FileSystemException | FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  // ----------------------------------------- Testing for filtering Airports

  /**
   * Verify that when filterAirports is called with a filter term that matches one entry in the
   * file, an ArrayList containing just that entry is returned.
   */
  @Test
  public void filterAirportsOneTermOneRecordTest() {
    ArrayList<Airport> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "Goroka");
    expectedResults.add(
        new Airport(
            1,
            "Goroka",
            "Goroka",
            "Papua New Guinea",
            "GKA",
            "AYGA",
            -6.081689,
            145.391881,
            5282,
            10,
            "U",
            "Pacific/Port_Moresby"));
    filterer.filterAirports(testFilterTerms, storage.getAirports());
    ArrayList<Airport> results = (ArrayList<Airport>) filterer.getFilteredAirports();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirports is called with a name and country filter terms that match one
   * entry in the file, an ArrayList containing just that entry is returned.
   */
  @Test
  public void filterAirportsTwoTermsOneRecordTest() {
    ArrayList<Airport> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "Goroka");
    testFilterTerms.put("Country", "Papua New Guinea");
    expectedResults.add(
        new Airport(
            1,
            "Goroka",
            "Goroka",
            "Papua New Guinea",
            "GKA",
            "AYGA",
            -6.081689,
            145.391881,
            5282,
            10,
            "U",
            "Pacific/Port_Moresby"));
    filterer.filterAirports(testFilterTerms, storage.getAirports());
    ArrayList<Airport> results = (ArrayList<Airport>) filterer.getFilteredAirports();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirports is called with a name, country and IATA filter terms that match
   * one entry in the file, an ArrayList containing just that entry is returned.
   */
  @Test
  public void filterAirportsThreeTermsOneRecordTest() {
    ArrayList<Airport> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "Goroka");
    testFilterTerms.put("Country", "Papua New Guinea");
    testFilterTerms.put("IATA", "GKA");
    expectedResults.add(
        new Airport(
            1,
            "Goroka",
            "Goroka",
            "Papua New Guinea",
            "GKA",
            "AYGA",
            -6.081689,
            145.391881,
            5282,
            10,
            "U",
            "Pacific/Port_Moresby"));
    filterer.filterAirports(testFilterTerms, storage.getAirports());
    ArrayList<Airport> results = (ArrayList<Airport>) filterer.getFilteredAirports();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirports is called with a filter term that matches multiple entries in
   * the file, an ArrayList containing all of those entries is returned. This tests data which does
   * not contain a subset of the filter terms i.e. all terms appear in each matching record.
   */
  @Test
  public void filterAirportsOneTermManyRecordsTest() {
    ArrayList<Airport> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Country", "Greenland");
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
    filterer.filterAirports(testFilterTerms, storage.getAirports());
    ArrayList<Airport> results = (ArrayList<Airport>) filterer.getFilteredAirports();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirports is called with two filter terms that match multiple entries in
   * the file, an ArrayList containing all of those entries is returned. This tests data which does
   * not contain a subset of the filter terms i.e. all terms appear in each matching record.
   */
  @Test
  public void filterAirportsTwoTermsManyRecordsTest() {
    ArrayList<Airport> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "Akureyri");
    testFilterTerms.put("Country", "Iceland");
    expectedResults.add(
        new Airport(
            11,
            "Akureyri",
            "Akureyri",
            "Iceland",
            "AEY",
            "BIAR",
            65.659994,
            -18.072703,
            6,
            0,
            "N",
            "Atlantic/Reykjavik"));
    expectedResults.add(
        new Airport(
            12,
            "Akureyri",
            "Egilsstadir",
            "Iceland",
            "EGS",
            "BIEG",
            65.283333,
            -14.401389,
            76,
            0,
            "N",
            "Atlantic/Reykjavik"));
    expectedResults.add(
        new Airport(
            13,
            "Akureyri",
            "Hofn",
            "Iceland",
            "HFN",
            "BIHN",
            64.295556,
            -15.227222,
            24,
            0,
            "N",
            "Atlantic/Reykjavik"));
    expectedResults.add(
        new Airport(
            14,
            "Akureyri",
            "Husavik",
            "Iceland",
            "HZK",
            "BIHU",
            65.952328,
            -17.425978,
            48,
            0,
            "N",
            "Atlantic/Reykjavik"));
    filterer.filterAirports(testFilterTerms, storage.getAirports());
    ArrayList<Airport> results = (ArrayList<Airport>) filterer.getFilteredAirports();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirports is called with three filter terms that matches multiple entries
   * in the file, an ArrayList containing all of those entries is returned. This tests data which
   * does not contain a subset of the filter terms i.e. all terms appear in each matching record.
   */
  @Test
  public void filterAirportsThreeTermsManyRecordsTest() {
    ArrayList<Airport> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "Sault Ste Marie");
    testFilterTerms.put("Country", "Canada");
    testFilterTerms.put("IATA", "YAM");
    expectedResults.add(
        new Airport(
            21,
            "Sault Ste Marie",
            "Sault Sainte Marie",
            "Canada",
            "YAM",
            "CYAM",
            46.485001,
            -84.509445,
            630,
            -5,
            "A",
            "America/Toronto"));
    expectedResults.add(
        new Airport(
            22,
            "Sault Ste Marie",
            "Winnipeg",
            "Canada",
            "YAM",
            "CYAV",
            50.056389,
            -97.0325,
            760,
            -6,
            "A",
            "America/Winnipeg"));
    filterer.filterAirports(testFilterTerms, storage.getAirports());
    ArrayList<Airport> results = (ArrayList<Airport>) filterer.getFilteredAirports();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirports is called with two filter terms and there are two records in
   * the data storage where the first record's name attribute matches the filter's name and the
   * second record's country matches the filter's country, then no records are returned and a
   * RuntimeException exception gets thrown.
   */
  @Test
  public void filterAirportsTwoTermsNoRecordsTest() {
    try {
      HashMap<String, String> testFilterTerms = new HashMap<>();
      testFilterTerms.put("Name", "Santa Maria Pub Cpt G Allan Hancock Airport");
      testFilterTerms.put("Country", "USA");
      filterer.filterAirports(testFilterTerms, storage.getAirports());
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that when filterAirports is called with two filter terms and there is only one record in
   * the data storage where the first record's name attribute matches the filter's name and the
   * second record's country matches the filter's country, then just this record is returned in an
   * ArrayList.
   */
  @Test
  public void filterAirportsTwoTermsTwoIncorrectOneRecordTest() {
    ArrayList<Airport> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "Mangaia Island Airport");
    testFilterTerms.put("Country", "Cook Islands");
    filterer.filterAirports(testFilterTerms, storage.getAirports());
    ArrayList<Airport> results = (ArrayList<Airport>) filterer.getFilteredAirports();
    expectedResults.add(
        new Airport(
            5864,
            "Mangaia Island Airport",
            "Mangaia Island",
            "Cook Islands",
            "MGS",
            "NCMG",
            -21.8956,
            -157.905,
            45,
            -10,
            "U",
            "Pacific/Rarotonga"));
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  // ----------------------------------------- Testing for filtering Airlines

  /**
   * Verify that when filterAirlines is called with a filter term that matches one entry in the
   * file, an ArrayList containing just that entry is returned.
   */
  @Test
  public void filterAirlinesOneTermOneRecordTest() {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "Private flight");
    expectedResults.add(new Airline(1, "Private flight", "\\N", "-", "N/A", "", "", true));
    filterer.filterAirlines(testFilterTerms, storage.getAirlines());
    ArrayList<Airline> results = (ArrayList<Airline>) filterer.getFilteredAirlines();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirlines is called with a name and country filter terms that matches one
   * entry in the file, an ArrayList containing just that entry is returned.
   */
  @Test
  public void filterAirlinesTwoTermsOneRecordTest() {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "1Time Airline");
    testFilterTerms.put("Country", "South Africa");
    expectedResults.add(
        new Airline(3, "1Time Airline", "\\N", "1T", "RNX", "NEXTIME", "South Africa", true));
    filterer.filterAirlines(testFilterTerms, storage.getAirlines());
    ArrayList<Airline> results = (ArrayList<Airline>) filterer.getFilteredAirlines();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirlines is called with a name, country and IATA filter terms that
   * matches one entry in the file an ArrayList containing just that entry is returned.
   */
  @Test
  public void filterAirlinesThreeTermsOneRecordTest() {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "40-Mile Air");
    testFilterTerms.put("Country", "United States");
    testFilterTerms.put("IATA", "Q5");
    expectedResults.add(
        new Airline(10, "40-Mile Air", "\\N", "Q5", "MLA", "MILE-AIR", "United States", true));
    filterer.filterAirlines(testFilterTerms, storage.getAirlines());
    ArrayList<Airline> results = (ArrayList<Airline>) filterer.getFilteredAirlines();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirlines is called with a filter term that matches multiple entries in
   * the file, an arrayList containing all of those entries is returned. This tests data which does
   * not contain a subset of the filter terms i.e. all terms appear in each matching record.
   */
  @Test
  public void filterAirlinesOneTermManyRecordsTest() {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Country", "Russia");
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
    expectedResults.add(new Airline(52, "Hot Air", "\\N", "", "TFU", "", "Russia", false));
    filterer.filterAirlines(testFilterTerms, storage.getAirlines());
    ArrayList<Airline> results = (ArrayList<Airline>) filterer.getFilteredAirlines();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirlines is called with two filter terms that match multiple entries in
   * the file, an ArrayList containing all of those entries is returned. This tests data which does
   * not contain a subset of the filter terms i.e. all terms appear in each matching record.
   */
  @Test
  public void filterAirlinesTwoTermsManyRecordsTest() {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "ABX Air");
    testFilterTerms.put("Country", "United States");
    expectedResults.add(
        new Airline(50, "ABX Air", "\\N", "GB", "ABX", "ABEX", "United States", false));
    expectedResults.add(
        new Airline(53, "ABX Air", "\\N", "HB", "ABX", "ABEX", "United States", false));
    filterer.filterAirlines(testFilterTerms, storage.getAirlines());
    ArrayList<Airline> results = (ArrayList<Airline>) filterer.getFilteredAirlines();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirlines is called with three filter terms that matches multiple entries
   * in the file, an ArrayList containing all of those entries is returned. This tests data which
   * does not contain a subset of the filter terms i.e. all terms appear in each matching record.
   */
  @Test
  public void filterAirlinesThreeTermsManyRecordsTest() {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "Antrak Air");
    testFilterTerms.put("Country", "Ghana");
    testFilterTerms.put("ICAO", "ABV");
    expectedResults.add(
        new Airline(48, "Antrak Air", "\\N", "04", "ABV", "ANTRAK", "Ghana", false));
    expectedResults.add(
        new Airline(55, "Antrak Air", "\\N", "06", "ABV", "ANTRAK", "Ghana", false));
    expectedResults.add(
        new Airline(56, "Antrak Air", "\\N", "05", "ABV", "ANTRAK", "Ghana", false));
    filterer.filterAirlines(testFilterTerms, storage.getAirlines());
    ArrayList<Airline> results = (ArrayList<Airline>) filterer.getFilteredAirlines();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterAirlines is called with one filter term and there are no records which
   * match this term, then no records are returned and a RuntimeException exception gets thrown.
   */
  @Test
  public void filterAirlinesOneTermNoRecordsTest() {
    try {
      HashMap<String, String> testFilterTerms = new HashMap<>();
      testFilterTerms.put("Name", "England International");
      filterer.filterAirlines(testFilterTerms, storage.getAirlines());
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that when filterAirlines is called with two filter terms and there are no records which
   * match both of these terms, then no records are returned and a RuntimeException exception gets
   * thrown.
   */
  @Test
  public void filterAirlinesTwoTermsNoRecordsTest() {
    try {
      HashMap<String, String> testFilterTerms = new HashMap<>();
      testFilterTerms.put("Name", "Aero Biniza");
      testFilterTerms.put("Country", "Canada");
      filterer.filterAirlines(testFilterTerms, storage.getAirlines());
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that when filterAirlines is called with two filter terms and there is only one record in
   * the data storage where the first record's name attribute matches the filter's name and the
   * second record's country matches the filter's country, then just this record is returned in an
   * ArrayList.
   */
  @Test
  public void filterAirlinesTwoTermsTwoIncorrectOneRecordTest() {
    ArrayList<Airline> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Name", "Aerial First");
    testFilterTerms.put("Country", "Finland");
    filterer.filterAirlines(testFilterTerms, storage.getAirlines());
    ArrayList<Airline> results = (ArrayList<Airline>) filterer.getFilteredAirlines();
    expectedResults.add(
        new Airline(54, "Aerial First", "\\N", "", "ABF", "SKYWINGS", "Finland", false));
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  // ----------------------------------------- Testing for filtering Routes

  /**
   * Verify that when filterRoutes is called with a filter term that matches one entry in the file,
   * an ArrayList containing just that entry is returned.
   */
  @Test
  public void filterRoutesOneTermOneRecordTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Airline", "2H");
    expectedResults.add(new Route("2H", 1652, "IKT", 2937, "ODO", 8944, "", 0, "AN4".split(" ")));
    filterer.filterRoutes(testFilterTerms, storage.getRoutes());
    ArrayList<Route> results = (ArrayList<Route>) filterer.getFilteredRoutes();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterRoutes is called with source and destination filter terms that matches
   * one entry in the file, an ArrayList containing just that entry is returned.
   */
  @Test
  public void filterRoutesTwoTermsOneRecordTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Source", "SVX");
    testFilterTerms.put("Destination", "OVC");
    expectedResults.add(new Route("2W", 411, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" ")));
    filterer.filterRoutes(testFilterTerms, storage.getRoutes());
    ArrayList<Route> results = (ArrayList<Route>) filterer.getFilteredRoutes();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterRoutes is called with an airline, source and destination filter terms
   * that matches one entry in the file an ArrayList containing just that entry is returned.
   */
  @Test
  public void filterRoutesThreeTermsOneRecordTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Airline", "2V");
    testFilterTerms.put("Source", "SVY");
    testFilterTerms.put("Destination", "OVB");
    expectedResults.add(new Route("2V", 412, "SVY", 2975, "OVB", 4078, "", 0, "CR2".split(" ")));
    filterer.filterRoutes(testFilterTerms, storage.getRoutes());
    ArrayList<Route> results = (ArrayList<Route>) filterer.getFilteredRoutes();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterRoutes is called with a filter term that matches multiple entries in the
   * file, an arrayList containing all of those entries is returned. This tests data which does not
   * contain a subset of the filter terms i.e. all terms appear in each matching record.
   */
  @Test
  public void filterRoutesOneTermManyRecordsTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Destination", "KZN");
    expectedResults.add(new Route("2B", 410, "AER", 2965, "KZN", 2991, "", 0, "CR2".split(" ")));
    expectedResults.add(new Route("2B", 410, "ASF", 2966, "KZN", 2990, "", 0, "CR2".split(" ")));
    expectedResults.add(new Route("2B", 410, "ASF", 2966, "KZN", 2962, "", 0, "CR2".split(" ")));
    filterer.filterRoutes(testFilterTerms, storage.getRoutes());
    ArrayList<Route> results = (ArrayList<Route>) filterer.getFilteredRoutes();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterRoutes is called with two filter terms that match multiple entries in
   * the file, an ArrayList containing all of those entries is returned. This tests data which does
   * not contain a subset of the filter terms i.e. all terms appear in each matching record.
   */
  @Test
  public void filterRoutesTwoTermsManyRecordsTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Source", "CEK");
    testFilterTerms.put("Destination", "NBC");
    expectedResults.add(new Route("2B", 410, "CEK", 2968, "NBC", 4078, "", 0, "CR2".split(" ")));
    expectedResults.add(new Route("2B", 410, "CEK", 4029, "NBC", 6969, "", 0, "CR2".split(" ")));
    expectedResults.add(new Route("2B", 410, "CEK", 4029, "NBC", 6160, "", 0, "CR2".split(" ")));
    filterer.filterRoutes(testFilterTerms, storage.getRoutes());
    ArrayList<Route> results = (ArrayList<Route>) filterer.getFilteredRoutes();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterRoutes is called with three filter terms that matches multiple entries
   * in the file, an ArrayList containing all of those entries is returned. This tests data which
   * does not contain a subset of the filter terms i.e. all terms appear in each matching record.
   */
  @Test
  public void filterRoutesThreeTermsManyRecordsTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Airline", "2A");
    testFilterTerms.put("Source", "SVH");
    testFilterTerms.put("Destination", "OPP");
    expectedResults.add(new Route("2A", 413, "SVH", 2976, "OPP", 4079, "", 0, "CR2".split(" ")));
    expectedResults.add(new Route("2A", 413, "SVH", 2976, "OPP", 4080, "", 0, "CR2".split(" ")));
    filterer.filterRoutes(testFilterTerms, storage.getRoutes());
    ArrayList<Route> results = (ArrayList<Route>) filterer.getFilteredRoutes();
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }

  /**
   * Verify that when filterRoutes is called with one filter term and there are no records which
   * match this term, then no records are returned and a RuntimeException exception gets thrown.
   */
  @Test
  public void filterRoutesOneTermNoRecordsTest() {
    try {
      HashMap<String, String> testFilterTerms = new HashMap<>();
      testFilterTerms.put("Airline", "2M");
      filterer.filterRoutes(testFilterTerms, storage.getRoutes());
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that when filterRoutes is called with two filter terms and there are no records which
   * match both of these terms, then no records are returned and a RuntimeException exception gets
   * thrown.
   */
  @Test
  public void filterRoutesTwoTermsNoRecordsTest() {
    try {
      HashMap<String, String> testFilterTerms = new HashMap<>();
      testFilterTerms.put("Airline", "2K");
      testFilterTerms.put("Destination", "OVC");
      filterer.filterRoutes(testFilterTerms, storage.getRoutes());
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that when filterRoutes is called with two filter terms and there is only one record in
   * the data storage where the first record's airline attribute matches the filter's airline and
   * the second record's source matches the filter's source, then just this record is returned in an
   * ArrayList.
   */
  @Test
  public void filterRoutesTwoTermsTwoIncorrectOneRecordTest() {
    ArrayList<Route> expectedResults = new ArrayList<>();
    HashMap<String, String> testFilterTerms = new HashMap<>();
    testFilterTerms.put("Airline", "2K");
    testFilterTerms.put("Source", "HTA");
    filterer.filterRoutes(testFilterTerms, storage.getRoutes());
    ArrayList<Route> results = (ArrayList<Route>) filterer.getFilteredRoutes();
    expectedResults.add(new Route("2K", 1654, "HTA", 2935, "IKT", 2937, "", 0, "AN4".split(" ")));
    assertArrayEquals(expectedResults.toArray(), results.toArray());
  }
}
