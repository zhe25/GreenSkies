package controller.analysis;

import model.data.*;
import model.loader.Loader;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Unit test for AirportStatsCalculator class.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirportStatsCalculatorTest {

  private Storage storage;
  private Loader loader;
  private AirportStatsCalculator airportStatsCalculator;

  @Before
  public void setUp() {
    storage = new Storage();
    loader = new Loader(storage);
    airportStatsCalculator = new AirportStatsCalculator();
    try {
      loader.loadFile("../seng202_project/src/test/java/TestFiles/GenerateReportTest.csv", "Route");
    } catch (FileSystemException | FileNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // --------------------------------- Testing for updateMostVisitedSrcAirport

  /**
   * Verify that when updateMostVisitedSrcAirport is called when there is one source aiport entry,
   * then the most visited source airport is this entry
   */
  @Test
  public void updateMostVisitedSrcAirportOneTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    String testString = "Wellington Airport";
    expectedResults.add(testString);
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    testSrcAirports.put("Wellington Airport", 100);
    airportStatsCalculator.updateMostVisitedSrcAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getMostVisitedSrcAirports());
  }

  /**
   * Verify that when updateMostVisitedSrcAirport is called when there are two source airport
   * entries, where one is most visited than the other, then the most visit source airport is the
   * one that is most visited out of the two entries.
   */
  @Test
  public void updateMostVisitedSrcAirportTwoTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Wellington Airport");
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    testSrcAirports.put("Wellington Airport", 100);
    testSrcAirports.put("Auckland Airport", 84);
    airportStatsCalculator.updateMostVisitedSrcAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getMostVisitedSrcAirports());
  }

  /**
   * Verify that when updateMostVisitedSrcAirport is called when there are multiple source airports
   * in history i.e. 9 and one of them is of the most visited source airports, then this airport is
   * the only airport added to the most visited source airports array.
   */
  @Test
  public void updateMostVisitedSrcAirportTenDiffOneMostTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Christchurch Airport");
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    testSrcAirports.put("Wellington Airport", 100);
    testSrcAirports.put("Auckland Airport", 84);
    testSrcAirports.put("Christchurch Airport", 147);
    testSrcAirports.put("Queenstown Airport", 44);
    testSrcAirports.put("Nelson Airport", 44);
    testSrcAirports.put("Hamilton Airport", 14);
    testSrcAirports.put("Rotorua Airport", 57);
    testSrcAirports.put("Hawke's Bay Airport", 45);
    testSrcAirports.put("Palmerston North Airport", 4);
    airportStatsCalculator.updateMostVisitedSrcAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getMostVisitedSrcAirports());
  }

  /**
   * Verify that when updateMostVisitedSrcAirport is called when there are multiple source airports
   * in history i.e. 9 and more than one of them e.g. 3 are of the most visited source airports,
   * then those airports are the only airports added to the most visited source airports array.
   */
  @Test
  public void updateMostVisitedSrcAirportTenDiffThreeMostTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Hamilton Airport");
    expectedResults.add("Auckland Airport");
    expectedResults.add("Wellington Airport");
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    testSrcAirports.put("Wellington Airport", 251);
    testSrcAirports.put("Auckland Airport", 251);
    testSrcAirports.put("Christchurch Airport", 147);
    testSrcAirports.put("Queenstown Airport", 44);
    testSrcAirports.put("Nelson Airport", 44);
    testSrcAirports.put("Hamilton Airport", 251);
    testSrcAirports.put("Rotorua Airport", 57);
    testSrcAirports.put("Hawke's Bay Airport", 45);
    testSrcAirports.put("Palmerston North Airport", 4);
    airportStatsCalculator.updateMostVisitedSrcAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getMostVisitedSrcAirports());
  }

  /**
   * Verify that when updateMostVisitedSrcAirport is called when there are no source aiport entries,
   * then the most visited source airport array should be empty.
   */
  @Test
  public void updateMostVisitedSrcAirportNoRoutesTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    airportStatsCalculator.updateMostVisitedSrcAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getMostVisitedSrcAirports());
  }

  // --------------------------------- Testing for updateMostVisitedDestAirport

  /**
   * Verify that when updateMostVisitedDestAirport is called when there is one destionation aiport
   * entry, then the most visit destination airport is this entry.
   */
  @Test
  public void updateMostVisitedDestAirportOneTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    String testString = "Wellington Airport";
    expectedResults.add(testString);
    HashMap<String, Integer> testDestAirports = new HashMap<>();
    testDestAirports.put("Wellington Airport", 100);
    airportStatsCalculator.updateMostVisitedDestAirports(testDestAirports);
    assertEquals(expectedResults, airportStatsCalculator.getMostVisitedDestAirports());
  }

  /**
   * Verify that when updateMostVisitedDestAirport is called when there are two destination aiport
   * entries, where one is most visited than the other, then the most visit destination airport is
   * the one that is most visted out of the two entries.
   */
  @Test
  public void updateMostVisitedDestAirportTwoTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Wellington Airport");
    HashMap<String, Integer> testDestAirports = new HashMap<>();
    testDestAirports.put("Wellington Airport", 100);
    testDestAirports.put("Auckland Airport", 84);
    airportStatsCalculator.updateMostVisitedDestAirports(testDestAirports);
    assertEquals(expectedResults, airportStatsCalculator.getMostVisitedDestAirports());
  }

  /**
   * Verify that when updateMostVisitedDestAirport is called when there are multiple destination
   * airports in history i.e. 9 and one of them is of the most visited destination airports, then
   * this airport is the only airport added to the most visited destination airports array.
   */
  @Test
  public void updateMostVisitedDestAirportTenTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Christchurch Airport");
    HashMap<String, Integer> testDestAirports = new HashMap<>();
    testDestAirports.put("Wellington Airport", 100);
    testDestAirports.put("Auckland Airport", 84);
    testDestAirports.put("Christchurch Airport", 147);
    testDestAirports.put("Queenstown Airport", 44);
    testDestAirports.put("Nelson Airport", 44);
    testDestAirports.put("Hamilton Airport", 14);
    testDestAirports.put("Rotorua Airport", 57);
    testDestAirports.put("Hawke's Bay Airport", 45);
    testDestAirports.put("Palmerston North Airport", 4);
    airportStatsCalculator.updateMostVisitedDestAirports(testDestAirports);
    assertEquals(expectedResults, airportStatsCalculator.getMostVisitedDestAirports());
  }

  /**
   * Verify that when updateMostVisitedDestAirport is called when there are multiple destination
   * airports in history i.e. 9 and more than one of them e.g. 3 are of the most visited destination
   * airports, then those airports are the only airports added to the most visited destination
   * airports array.
   */
  @Test
  public void updateMostVisitedDestAirportTenDiffThreeMostTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Hamilton Airport");
    expectedResults.add("Auckland Airport");
    expectedResults.add("Wellington Airport");
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    testSrcAirports.put("Wellington Airport", 251);
    testSrcAirports.put("Auckland Airport", 251);
    testSrcAirports.put("Christchurch Airport", 147);
    testSrcAirports.put("Queenstown Airport", 44);
    testSrcAirports.put("Nelson Airport", 44);
    testSrcAirports.put("Hamilton Airport", 251);
    testSrcAirports.put("Rotorua Airport", 57);
    testSrcAirports.put("Hawke's Bay Airport", 45);
    testSrcAirports.put("Palmerston North Airport", 4);
    airportStatsCalculator.updateMostVisitedDestAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getMostVisitedDestAirports());
  }

  /**
   * Verify that when updateMostVisitedDestAirport is called when there are no destination aiport
   * entries, then the most visited destination airport array should be empty.
   */
  @Test
  public void updateMostVisitedDestAirportNoRoutesTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    airportStatsCalculator.updateMostVisitedDestAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getMostVisitedDestAirports());
  }

  /**
   * Verify that when resetRoutesArrays is called when there are no values in the arrays, then they
   * remain empty.
   */
  @Test
  public void resetAirportsArraysEmptyTest() {
    ArrayList<String> expectedMostVisitedDestAirports = new ArrayList<>();
    ArrayList<String> expectedMostVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> expectedLeastVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> expectedLeastVisitedDestAirports = new ArrayList<>();

    airportStatsCalculator.resetAirportArrays();

    assertEquals(
        expectedMostVisitedDestAirports, airportStatsCalculator.getMostVisitedDestAirports());
    assertEquals(
        expectedMostVisitedSrcAirports, airportStatsCalculator.getMostVisitedSrcAirports());
    assertEquals(
        expectedLeastVisitedSrcAirports, airportStatsCalculator.getLeastVisitedSrcAirports());
    assertEquals(
        expectedLeastVisitedDestAirports, airportStatsCalculator.getLeastVisitedDestAirports());
  }

  /**
   * Verify that when resetRoutesArrays is called when there is a single value in each array, that
   * the arrays become empty.
   */
  @Test
  public void resetAirportsArraysOneValueTest() {
    ArrayList<String> testMostVisitedDestAirports = new ArrayList<>();
    ArrayList<String> testMostVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> testLeastVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> testLeastVisitedDestAirports = new ArrayList<>();

    testMostVisitedDestAirports.add("GKA");
    testMostVisitedSrcAirports.add("BGG");
    testLeastVisitedSrcAirports.add("BHL");
    testLeastVisitedDestAirports.add("GBR");

    ArrayList<String> expectedMostVisitedDestAirports = new ArrayList<>();
    ArrayList<String> expectedMostVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> expectedLeastVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> expectedLeastVisitedDestAirports = new ArrayList<>();

    airportStatsCalculator.resetAirportArrays();

    assertEquals(
        expectedMostVisitedDestAirports, airportStatsCalculator.getMostVisitedDestAirports());
    assertEquals(
        expectedMostVisitedSrcAirports, airportStatsCalculator.getMostVisitedSrcAirports());
    assertEquals(
        expectedLeastVisitedSrcAirports, airportStatsCalculator.getLeastVisitedSrcAirports());
    assertEquals(
        expectedLeastVisitedDestAirports, airportStatsCalculator.getLeastVisitedDestAirports());
  }

  /**
   * Verify that when resetRoutesArrays is called when there is are multiple values in each array,
   * that the arrays become empty.
   */
  @Test
  public void resetAirportsArraysMultiValuesTest() {
    ArrayList<String> testMostVisitedDestAirports = new ArrayList<>();
    ArrayList<String> testMostVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> testLeastVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> testLeastVisitedDestAirports = new ArrayList<>();

    testMostVisitedDestAirports.add("GKA");
    testMostVisitedSrcAirports.add("BGG");
    testLeastVisitedSrcAirports.add("BHL");
    testLeastVisitedDestAirports.add("GBR");
    testMostVisitedDestAirports.add("AUR");
    testMostVisitedSrcAirports.add("HEP");
    testLeastVisitedSrcAirports.add("MAA");
    testLeastVisitedDestAirports.add("ETY");
    testMostVisitedDestAirports.add("UED");
    testMostVisitedSrcAirports.add("DEA");
    testLeastVisitedSrcAirports.add("PVE");
    testLeastVisitedDestAirports.add("LAC");

    ArrayList<String> expectedMostVisitedDestAirports = new ArrayList<>();
    ArrayList<String> expectedMostVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> expectedLeastVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> expectedLeastVisitedDestAirports = new ArrayList<>();

    airportStatsCalculator.resetAirportArrays();

    assertEquals(
        expectedMostVisitedDestAirports, airportStatsCalculator.getMostVisitedDestAirports());
    assertEquals(
        expectedMostVisitedSrcAirports, airportStatsCalculator.getMostVisitedSrcAirports());
    assertEquals(
        expectedLeastVisitedSrcAirports, airportStatsCalculator.getLeastVisitedSrcAirports());
    assertEquals(
        expectedLeastVisitedDestAirports, airportStatsCalculator.getLeastVisitedDestAirports());
  }

  /**
   * Verify that when resetRoutesArrays is called when there is a mixture of values in each array,
   * that the arrays become empty.
   */
  @Test
  public void resetAirportsArraysVaryingNumOfValuesTest() {
    ArrayList<String> testMostVisitedDestAirports = new ArrayList<>();
    ArrayList<String> testMostVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> testLeastVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> testLeastVisitedDestAirports = new ArrayList<>();

    testMostVisitedDestAirports.add("GKA");
    testLeastVisitedSrcAirports.add("BHL");
    testLeastVisitedDestAirports.add("GBR");
    testMostVisitedDestAirports.add("AUR");
    testMostVisitedSrcAirports.add("HEP");
    testLeastVisitedSrcAirports.add("MAA");
    testMostVisitedDestAirports.add("UED");
    testMostVisitedSrcAirports.add("DEA");
    testLeastVisitedSrcAirports.add("PVE");
    testLeastVisitedDestAirports.add("LAC");

    ArrayList<String> expectedMostVisitedDestAirports = new ArrayList<>();
    ArrayList<String> expectedMostVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> expectedLeastVisitedSrcAirports = new ArrayList<>();
    ArrayList<String> expectedLeastVisitedDestAirports = new ArrayList<>();

    airportStatsCalculator.resetAirportArrays();

    assertEquals(
        expectedMostVisitedDestAirports, airportStatsCalculator.getMostVisitedDestAirports());
    assertEquals(
        expectedMostVisitedSrcAirports, airportStatsCalculator.getMostVisitedSrcAirports());
    assertEquals(
        expectedLeastVisitedSrcAirports, airportStatsCalculator.getLeastVisitedSrcAirports());
    assertEquals(
        expectedLeastVisitedDestAirports, airportStatsCalculator.getLeastVisitedDestAirports());
  }

  // ------------------------------------ Testing for updateLeastVisitedSrcAirports()

  /**
   * Verify that when updateLeastVisitedSrcAirport is called when there is one source aiport entry,
   * then the least visited source airport is this entry
   */
  @Test
  public void updateLeastVisitedSrcAirportOneTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    String testString = "Wellington Airport";
    expectedResults.add(testString);
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    testSrcAirports.put("Wellington Airport", 100);
    airportStatsCalculator.updateLeastVisitedSrcAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getLeastVisitedSrcAirports());
  }

  /**
   * Verify that when updateLeastVisitedSrcAirport is called when there are two source aiport
   * entries, where one is less visited than the other, then the least visit source airport is the
   * one that is less visted out of the two entries.
   */
  @Test
  public void updateLeastVisitedSrcAirportTwoTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Wellington Airport");
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    testSrcAirports.put("Wellington Airport", 84);
    testSrcAirports.put("Auckland Airport", 100);
    airportStatsCalculator.updateLeastVisitedSrcAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getLeastVisitedSrcAirports());
  }

  /**
   * Verify that when updateLeastVisitedSrcAirport is called when there are multiple source airports
   * in history i.e. 9 and one of them is of the least visited source airports, then this airport is
   * the only airport added to the least visited source airports array.
   */
  @Test
  public void updateLeastVisitedSrcAirportTenDiffOneMostTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Christchurch Airport");
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    testSrcAirports.put("Wellington Airport", 100);
    testSrcAirports.put("Auckland Airport", 84);
    testSrcAirports.put("Christchurch Airport", 2);
    testSrcAirports.put("Queenstown Airport", 44);
    testSrcAirports.put("Nelson Airport", 44);
    testSrcAirports.put("Hamilton Airport", 14);
    testSrcAirports.put("Rotorua Airport", 57);
    testSrcAirports.put("Hawke's Bay Airport", 45);
    testSrcAirports.put("Palmerston North Airport", 4);
    airportStatsCalculator.updateLeastVisitedSrcAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getLeastVisitedSrcAirports());
  }

  /**
   * Verify that when updateLeastVisitedSrcAirport is called when there are multiple source airports
   * in history i.e. 9 and more than one of them e.g. 3 are of the least visited source airports,
   * then those airports are the only airports added to the least visited source airports array.
   */
  @Test
  public void updateLeastVisitedSrcAirportTenDiffThreeMostTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Hamilton Airport");
    expectedResults.add("Auckland Airport");
    expectedResults.add("Wellington Airport");
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    testSrcAirports.put("Wellington Airport", 2);
    testSrcAirports.put("Auckland Airport", 2);
    testSrcAirports.put("Christchurch Airport", 147);
    testSrcAirports.put("Queenstown Airport", 44);
    testSrcAirports.put("Nelson Airport", 44);
    testSrcAirports.put("Hamilton Airport", 2);
    testSrcAirports.put("Rotorua Airport", 57);
    testSrcAirports.put("Hawke's Bay Airport", 45);
    testSrcAirports.put("Palmerston North Airport", 4);
    airportStatsCalculator.updateLeastVisitedSrcAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getLeastVisitedSrcAirports());
  }

  /**
   * Verify that when updateLeastVisitedSrcAirport is called when there are no source aiport
   * entries, then the least visited source airport array should be empty.
   */
  @Test
  public void updateLeastVisitedSrcAirportNoRoutesTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    HashMap<String, Integer> testSrcAirports = new HashMap<>();
    airportStatsCalculator.updateLeastVisitedSrcAirports(testSrcAirports);
    assertEquals(expectedResults, airportStatsCalculator.getLeastVisitedSrcAirports());
  }

  // ----------------------------------- Testing for updateLeastVisitedDestAirports()

  /**
   * Verify that when updateLeastVisitedDestAirport is called when there is one destination aiport
   * entry, then the least visited destination airport is this entry.
   */
  @Test
  public void updateLeastVisitedDestAirportOneTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    String testString = "Wellington Airport";
    expectedResults.add(testString);
    HashMap<String, Integer> testDestAirports = new HashMap<>();
    testDestAirports.put("Wellington Airport", 100);
    airportStatsCalculator.updateLeastVisitedDestAirports(testDestAirports);
    assertEquals(expectedResults, airportStatsCalculator.getLeastVisitedDestAirports());
  }

  /**
   * Verify that when updateLeastVisitedDestAirport is called when there are two destination aiport
   * entries, where one is less visited than the other, then the least visited destination airport
   * is the one that is least visited out of the two entries.
   */
  @Test
  public void updateLeastVisitedDestAirportTwoTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Wellington Airport");
    HashMap<String, Integer> testDestAirports = new HashMap<>();
    testDestAirports.put("Wellington Airport", 84);
    testDestAirports.put("Auckland Airport", 100);
    airportStatsCalculator.updateLeastVisitedDestAirports(testDestAirports);
    assertEquals(expectedResults, airportStatsCalculator.getLeastVisitedDestAirports());
  }

  /**
   * Verify that when updateLeastVisitedDestAirport is called when there are multiple destination
   * airports in history i.e. 9 and one of them is of the least visited destination airports, then
   * this airport is the only airport added to the least visited destination airports array.
   */
  @Test
  public void updateLeastVisitedDestAirportTenDiffOneMostTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Christchurch Airport");
    HashMap<String, Integer> testDestAirports = new HashMap<>();
    testDestAirports.put("Wellington Airport", 100);
    testDestAirports.put("Auckland Airport", 84);
    testDestAirports.put("Christchurch Airport", 2);
    testDestAirports.put("Queenstown Airport", 44);
    testDestAirports.put("Nelson Airport", 44);
    testDestAirports.put("Hamilton Airport", 14);
    testDestAirports.put("Rotorua Airport", 57);
    testDestAirports.put("Hawke's Bay Airport", 45);
    testDestAirports.put("Palmerston North Airport", 4);
    airportStatsCalculator.updateLeastVisitedDestAirports(testDestAirports);
    assertEquals(expectedResults, airportStatsCalculator.getLeastVisitedDestAirports());
  }

  /**
   * Verify that when updateLeastVisitedDestAirport is called when there are multiple destination
   * airports in history i.e. 9 and more than one of them e.g. 3 are of the least visited
   * destination airports, then those airports are the only airports added to the least visited
   * destination airports array.
   */
  @Test
  public void updateLeastVisitedDestAirportTenDiffThreeMostTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    expectedResults.add("Hamilton Airport");
    expectedResults.add("Auckland Airport");
    expectedResults.add("Wellington Airport");
    HashMap<String, Integer> testDestAirports = new HashMap<>();
    testDestAirports.put("Wellington Airport", 2);
    testDestAirports.put("Auckland Airport", 2);
    testDestAirports.put("Christchurch Airport", 147);
    testDestAirports.put("Queenstown Airport", 44);
    testDestAirports.put("Nelson Airport", 44);
    testDestAirports.put("Hamilton Airport", 2);
    testDestAirports.put("Rotorua Airport", 57);
    testDestAirports.put("Hawke's Bay Airport", 45);
    testDestAirports.put("Palmerston North Airport", 4);
    airportStatsCalculator.updateLeastVisitedDestAirports(testDestAirports);
    assertEquals(expectedResults, airportStatsCalculator.getLeastVisitedDestAirports());
  }

  /**
   * Verify that when updateLeastVisitedDestAirport is called when there are no destination aiport
   * entries, then the least visited destination airport array should be empty.
   */
  @Test
  public void updateLeastVisitedDestAirportNoRoutesTest() {
    ArrayList<String> expectedResults = new ArrayList<>();
    HashMap<String, Integer> testDestAirports = new HashMap<>();
    airportStatsCalculator.updateLeastVisitedDestAirports(testDestAirports);
    assertEquals(expectedResults, airportStatsCalculator.getLeastVisitedDestAirports());
  }
}
