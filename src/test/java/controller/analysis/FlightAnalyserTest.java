package controller.analysis;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for FlightAnalysis class.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class FlightAnalyserTest {

  Storage storage;
  Loader loader;
  ArrayList<String> path1;
  ArrayList<String> path2;
  ArrayList<String> path3;
  ArrayList<String> path4;
  String[] rubbish1 = {"abc"};
  String[] rubbish2 = {"abc"};

  Route route1 =
      new Route("Air Inter Gabon", 219, "AER", 2965, "KZN", 2990, "dont know", 0, rubbish1);
  Route route2 = new Route("Air Cess", 55, "ASF", 2966, "SVX", 2975, "dont know", 0, rubbish2);
  Route route3 = new Route(null, 296, "CEK", 2968, "DME", 4029, null, 0, rubbish1);
  Route route4 = new Route(null, 297, "EGO", 2968, "UUA", 4029, null, 0, rubbish2);

  FlightAnalyser analyser;
  List<Airport> airport;
  private final double radius = 6371e3;
  double distance1;
  double distance2;
  double distance3;
  double distance4;
  double singleDistance;

  /**
   * check through two list, and find out whether their airport code are equal or not find their
   * longitude and Latitude in order to calculate distance call calculate distance function to find
   * out distance between two airports for two paths.
   */
  public void findCoordinateForTwoPath(ArrayList<String> firstPath, ArrayList<String> secondPath) {

    airport = new ArrayList<>();
    airport = storage.getAirports();
    ArrayList<Airport> listOfAirportPath1 = new ArrayList<>();
    ArrayList<Airport> listOfAirportPath2 = new ArrayList<>();
    for (String k : firstPath) {
      for (Airport i : airport) {
        if (i.getIATA().equals(k) || i.getICAO().equals(k)) {
          listOfAirportPath1.add(i);
        }
      }
    }

    for (String h : secondPath) {
      for (Airport j : airport) {
        if (j.getIATA().equals(h) || j.getICAO().equals(h)) {
          listOfAirportPath2.add(j);
        }
      }
    }

    double pathDistance1 =
        calculatedistance(
            listOfAirportPath1.get(0).getLatitude(),
            listOfAirportPath1.get(0).getLongitude(),
            listOfAirportPath1.get(1).getLatitude(),
            listOfAirportPath1.get(1).getLongitude());

    double pathDistance2 =
        calculatedistance(
            listOfAirportPath2.get(0).getLatitude(),
            listOfAirportPath2.get(0).getLongitude(),
            listOfAirportPath2.get(1).getLatitude(),
            listOfAirportPath2.get(1).getLongitude());
    distance1 = pathDistance1;
    distance2 = pathDistance2;
    distance3 = pathDistance1;
    distance4 = pathDistance2;
  }

  /**
   * check through two list, and find out whether their airport code are equal or not find their
   * longitude and Latitude in order to calculate distance call calculate distance function to find
   * out distance between two airports for one path.
   */
  public void findCoordinateForOnePath(ArrayList<String> Path) {

    airport = new ArrayList<>();
    airport = storage.getAirports();
    ArrayList<Airport> listOfAirportPath = new ArrayList<>();
    for (String k : Path) {
      for (Airport i : airport) {
        if (i.getIATA().equals(k) || i.getICAO().equals(k)) {
          listOfAirportPath.add(i);
        }
      }
    }

    singleDistance =
        calculatedistance(
            listOfAirportPath.get(0).getLatitude(),
            listOfAirportPath.get(0).getLongitude(),
            listOfAirportPath.get(1).getLatitude(),
            listOfAirportPath.get(1).getLongitude());
  }

  /**
   * Calculate emissions by using the distance between two airports
   *
   * @return Co2 emission
   */
  private double calculateCarbonEmission(double distance) {

    double FuelUsed;

    final int seatsOccupancy = 333; // number of passengers;

    final double Co2OfOneGramFuel = 3.15; // in gram

    final int CruisingSpeed = 910; // km per hour

    FuelUsed = distance * 12 / 1250; // fuel in tonns

    double FuelPerPassenger =
        (FuelUsed / (distance * seatsOccupancy)) * 1000000; // fuel use per passenger per km

    double Co2PerPassengerPerKm =
        FuelPerPassenger * Co2OfOneGramFuel; // co2 emissions per passenger km in gram

    double Co2Hour =
        (Co2PerPassengerPerKm * CruisingSpeed) / 1000; // how much Co2 genate per hour in kg

    double flytime = distance / CruisingSpeed; // in hour

    return flytime * Co2Hour; // in kg
  }

  /**
   * Calculate distance by using the longitude and latitude of two airports
   *
   * @return distance
   */
  private double calculatedistance(double Lati1, double Long1, double Lati2, double Long2) {
    double need1 = Lati1 * Math.PI / 180;
    double need2 = Lati2 * Math.PI / 180;
    double need3 = (Lati2 - Lati1) * Math.PI / 180;
    double need4 = (Long2 - Long1) * Math.PI / 180;

    double a =
        Math.sin(need3 / 2) * Math.sin(need3 / 2)
            + Math.cos(need1) * Math.cos(need2) * Math.sin(need4 / 2) * Math.sin(need4 / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    double distance = radius * c;

    return distance / 1000; // distance in kilometers
  }

  /**
   * Set up are the array list for testing
   *
   * @throws FileNotFoundException This throws a FileNotFoundException.
   * @throws FileSystemException This throws a FileSystemException.
   * @throws SQLException This throws an SQLException.
   */
  @Before
  public void setUp() throws FileNotFoundException, FileSystemException, SQLException {
    storage = new Storage();
    loader = new Loader(storage);
    loader.loadFile("../seng202_project/src/test/java/TestFiles/airports.csv", "Airport");

    path1 = new ArrayList<>();
    path2 = new ArrayList<>();
    path3 = new ArrayList<>();
    path4 = new ArrayList<>();

    path1.add(route1.getSourceAirport());
    path1.add(route1.getDestinationAirport());

    path2.add(route2.getSourceAirport());
    path2.add(route2.getDestinationAirport());

    path3.add(route3.getSourceAirport());
    path3.add(route3.getDestinationAirport());

    path4.add(route4.getSourceAirport());
    path4.add(route4.getDestinationAirport());
  }

  /**
   * check a single path distance calculate in the test class whether equal to the distance was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isDistanceSinglePath1Correct() {
    findCoordinateForOnePath(path1);
    analyser = new FlightAnalyser(route1, storage);
    double path1Distance = analyser.getTotalDistancePath1();

    assertEquals(singleDistance, path1Distance, 0.0);
  }

  /**
   * check a single path emission calculate in the test class whether equal to the emission was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isEmissionSinglePath1Correct() {
    findCoordinateForOnePath(path1);
    analyser = new FlightAnalyser(route1, storage);
    double path1Emission = analyser.getPath1Emission();

    assertEquals(calculateCarbonEmission(singleDistance), path1Emission, 0.0);
  }

  /**
   * check a single path distance calculate in the test class whether equal to the distance was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isDistanceSinglePath2Correct() {
    findCoordinateForOnePath(path2);
    analyser = new FlightAnalyser(route2, storage);
    double path2Distance = analyser.getTotalDistancePath1();

    assertEquals(singleDistance, path2Distance, 0.0);
  }

  /**
   * check a single path emission calculate in the test class whether equal to the emission was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isEmissionSinglePath2Correct() {
    findCoordinateForOnePath(path2);
    analyser = new FlightAnalyser(route2, storage);
    double path2Emission = analyser.getPath1Emission();

    assertEquals(calculateCarbonEmission(singleDistance), path2Emission, 0.0);
  }

  /**
   * check two paths distance calculate in the test class whether equal to the distance was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isDistancePath1Correct() {
    findCoordinateForTwoPath(path1, path2);
    analyser = new FlightAnalyser(route1, route2, storage);
    double path1Distance = analyser.getTotalDistancePath1();
    assertEquals(distance1, path1Distance, 0.0);
  }

  /**
   * check two paths distance calculate in the test class whether equal to the distance was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isDistancePath2Correct() {
    findCoordinateForTwoPath(path1, path2);
    analyser = new FlightAnalyser(route1, route2, storage);
    double path2Distance = analyser.getTotalDistancePath2();

    assertEquals(distance2, path2Distance, 0.0);
  }

  /**
   * check two paths distance calculate in the test class whether equal to the distance was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isDistancePath3Correct() {
    findCoordinateForTwoPath(path3, path4);
    analyser = new FlightAnalyser(route3, route4, storage);
    double path3Distance = analyser.getTotalDistancePath1();

    assertEquals(distance3, path3Distance, 0.0);
  }

  /**
   * check two paths distance calculate in the test class whether equal to the distance was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isDistancePath4Correct() {
    findCoordinateForTwoPath(path3, path4);
    analyser = new FlightAnalyser(route3, route4, storage);
    double path4Distance = analyser.getTotalDistancePath2();

    assertEquals(distance4, path4Distance, 0.0);
  }

  /**
   * check two paths emission calculate in the test class whether equal to the emission was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isEmissionPath1Correct() {
    findCoordinateForTwoPath(path1, path2);
    analyser = new FlightAnalyser(route1, route2, storage);
    assertEquals(calculateCarbonEmission(distance1), analyser.getPath1Emission(), 0.0);
  }

  /**
   * check two paths emission calculate in the test class whether equal to the emission was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isEmissionPath2Correct() {
    findCoordinateForTwoPath(path1, path2);
    analyser = new FlightAnalyser(route1, route2, storage);
    assertEquals(calculateCarbonEmission(distance2), analyser.getPath2Emission(), 0.0);
  }

  /**
   * check two paths emission calculate in the test class whether equal to the emission was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isEmissionPath3Correct() {
    findCoordinateForTwoPath(path3, path4);
    analyser = new FlightAnalyser(route3, route4, storage);
    assertEquals(calculateCarbonEmission(distance3), analyser.getPath1Emission(), 0.0);
  }

  /**
   * check two paths emission calculate in the test class whether equal to the emission was
   * calculated in the FlightAnalyser class
   */
  @Test
  public void isEmissionPath4Correct() {
    findCoordinateForTwoPath(path3, path4);
    analyser = new FlightAnalyser(route3, route4, storage);
    assertEquals(calculateCarbonEmission(distance4), analyser.getPath2Emission(), 0.0);
  }

  /**
   * check whether the difference of the distance between two airports is a positive number if path1
   * distance is smaller than path2
   */
  @Test
  public void isCompareDistanceCorrect() {
    findCoordinateForTwoPath(path1, path2);
    analyser = new FlightAnalyser(route1, route2, storage);
    assertTrue(analyser.compareDistance() > 0);
  }

  /**
   * check whether the difference of the emission between two airports is a positive number if path1
   * emission is smaller than path2
   */
  @Test
  public void isCompareEmissionCorrect() {
    findCoordinateForTwoPath(path1, path2);
    analyser = new FlightAnalyser(route1, route2, storage);
    assertTrue(analyser.compareEmission() > 0);
  }
}
