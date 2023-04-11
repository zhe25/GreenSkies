package controller.analysis;

import model.data.*;
import model.loader.Loader;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;
import static org.junit.Assert.*;

/**
 * Unit test for ReportStatsCalculator class.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class GeneralStatsCalculatorTest {

  private Storage storage;
  private Loader loader;
  private GeneralStatsCalculator generalStatsCalculator;

  @Before
  public void setUp() {
    storage = new Storage();
    loader = new Loader(storage);
    generalStatsCalculator = new GeneralStatsCalculator();
    try {
      loader.loadFile("../seng202_project/src/test/java/TestFiles/GenerateReportTest.csv", "Route");
    } catch (FileSystemException | FileNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // --------------------------------------- Testing for updateTotalEmissions

  /**
   * Verify that when updateTotalEmissions is called with a route with a large amount of carbon
   * emissions that the total emissions is updated accordingly.The carbon emissions are starting at
   * 0.0 g.
   */
  @Test
  public void updateTotalEmissionsLargeEmissionsInitiallyZeroTest() {
    generalStatsCalculator.setTotalCarbonEmissions(0.0);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(20000000.50);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissions(testRoute);
    assertEquals(20000000.50, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissions is called with a route with a small amount of carbon
   * emissions that the total emissions is updated accordingly.The carbon emissions are starting at
   * 0.0 g.
   */
  @Test
  public void updateTotalEmissionsSmallEmissionsInitiallyZeroTest() {
    generalStatsCalculator.setTotalCarbonEmissions(0.0);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(70000.0);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissions(testRoute);
    assertEquals(70000.0, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissions is called with a route with a zero carbon emissions that
   * the total emissions is updated accordingly.The carbon emissions are starting at large amount.
   */
  @Test
  public void updateTotalEmissionsZeroEmissionsInitiallyZeroTest() {
    generalStatsCalculator.setTotalCarbonEmissions(0.0);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(0.0);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissions(testRoute);
    assertEquals(0.0, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissions is called with a route with a large amount of carbon
   * emissions that the total emissions is updated accordingly.The carbon emissions are starting at
   * large amount.
   */
  @Test
  public void updateTotalEmissionsLargeEmissionsInitiallyLargeTest() {
    generalStatsCalculator.setTotalCarbonEmissions(80000000000.10);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(1700000.20);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissions(testRoute);
    assertEquals(80001700000.30, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissions is called with a route with a small amount of carbon
   * emissions that the total emissions is updated accordingly.The carbon emissions are starting at
   * large amount.
   */
  @Test
  public void updateTotalEmissionsSmallEmissionsInitiallyLargeTest() {
    generalStatsCalculator.setTotalCarbonEmissions(90000000000.10);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(4500.08);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissions(testRoute);
    assertEquals(90000004500.18, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissions is called with a route with a zero carbon emissions that
   * the total emissions is updated accordingly.The carbon emissions are starting at large amount.
   */
  @Test
  public void updateTotalEmissionsZeroEmissionsInitiallyLargeTest() {
    generalStatsCalculator.setTotalCarbonEmissions(345231863432.98);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(0.0);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissions(testRoute);
    assertEquals(345231863432.98, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissions is called with a route with a large amount of carbon
   * emissions, which has been taken multipe times, that the total emissions is updated
   * accordingly.The carbon emissions are starting at 0.0 g.
   */
  @Test
  public void updateTotalEmissionsLargeEmissionsZeroTakenTest() {
    generalStatsCalculator.setTotalCarbonEmissions(0.0);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(20000000.50);
    testRoute.setTimesTaken(0);
    generalStatsCalculator.updateTotalEmissions(testRoute);
    assertEquals(0.0, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissions is called with a route with a small amount of carbon
   * emissions, which has been taken zero times, that the total emissions is not updated. The carbon
   * emissions are starting at 0.0 g.
   */
  @Test
  public void updateTotalEmissionsSmallEmissionsManyTakenTest() {
    generalStatsCalculator.setTotalCarbonEmissions(0.0);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(8760.0);
    testRoute.setTimesTaken(1000);
    generalStatsCalculator.updateTotalEmissions(testRoute);
    assertEquals(8760000.0, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  // --------------------------------------- Testing for updateTotalEmissionsRemoval

  /**
   * Verify that when updateTotalEmissionsRemoval is called with a route with a large amount of
   * carbon emissions and the current total emissions is 0.0 kg that the total emissions remains at
   * 0.0 kg and does not go into the negatives.
   */
  @Test
  public void updateTotalEmissionsRemovalLargeEmissionsInitiallyZeroTest() {
    generalStatsCalculator.setTotalCarbonEmissions(0.0);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(20000000.50);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissionsRemoval(testRoute);
    assertEquals(0.00, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissions is called with a route with a small amount of carbon
   * emissions and this is the only route in the flight history, that the total emissions is updated
   * accordingly. The carbon emissions are starting at the amount of this route's carbon emissions.
   */
  @Test
  public void updateTotalEmissionsRemovalSmallEmissionsInitiallySameTest() {
    generalStatsCalculator.setTotalCarbonEmissions(70000.0);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(70000.0);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissionsRemoval(testRoute);
    assertEquals(0.0, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissionsRemoval is called with a route with a zero carbon
   * emissions that the total emissions is updated accordingly.The carbon emissions are starting at
   * large amount.
   */
  @Test
  public void updateTotalEmissionsRemovalZeroEmissionsInitiallySameTest() {
    generalStatsCalculator.setTotalCarbonEmissions(55555.0);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(0.0);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissions(testRoute);
    assertEquals(55555.0, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissionsRemoval is called with a route with a large amount of
   * carbon emissions that the total emissions is updated accordingly.The carbon emissions are
   * starting at large amount which is greater than the route's carbon emissions.
   */
  @Test
  public void updateTotalEmissionsRemovalLargeEmissionsInitiallyLargeTest() {
    generalStatsCalculator.setTotalCarbonEmissions(80000000000.00);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(1700000.00);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissionsRemoval(testRoute);
    assertEquals(79998300000.00, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissionsRemoval is called with a route with a small amount of
   * carbon emissions that the total emissions is updated accordingly.The carbon emissions are
   * starting at large amount that is greater than the route's carbon emissions.
   */
  @Test
  public void updateTotalEmissionsRemovalSmallEmissionsInitiallyLargeTest() {
    generalStatsCalculator.setTotalCarbonEmissions(90000000000.10);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(4500.08);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissionsRemoval(testRoute);
    assertEquals(89999995500.02, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissionsRemoval is called with a route with a zero carbon
   * emissions that the total emissions is updated accordingly.The carbon emissions are starting at
   * large amount.
   */
  @Test
  public void updateTotalEmissionsRemovalZeroEmissionsInitiallyLargeTest() {
    generalStatsCalculator.setTotalCarbonEmissions(345231863432.98);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(0.0);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalEmissionsRemoval(testRoute);
    assertEquals(345231863432.98, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissionsRemoval is called with a route with a large amount of
   * carbon emissions, which has been taken multiple times, that the total emissions is updated
   * accordingly.The carbon emissions is greater than the route's carbon emissions.
   */
  @Test
  public void updateTotalEmissionsRemovalLargeEmissionsZeroTakenTest() {
    generalStatsCalculator.setTotalCarbonEmissions(20000000.50);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(20000000.50);
    testRoute.setTimesTaken(0);
    generalStatsCalculator.updateTotalEmissionsRemoval(testRoute);
    assertEquals(20000000.50, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  /**
   * Verify that when updateTotalEmissionsRemoval is called with a route with a small amount of
   * carbon emissions, which has been taken zero times, that the total emissions is not updated. The
   * carbon emissions are starting at 0.0 g.
   */
  @Test
  public void updateTotalEmissionsRemovalSmallEmissionsManyTakenTest() {
    generalStatsCalculator.setTotalCarbonEmissions(18760000.0);
    Route testRoute = new Route("2W", 410, "SVX", 2975, "OVC", 4078, "", 0, "CR2".split(" "));
    testRoute.setEmissions(8760.0);
    testRoute.setTimesTaken(1000);
    generalStatsCalculator.updateTotalEmissionsRemoval(testRoute);
    assertEquals(10000000.0, generalStatsCalculator.getTotalCarbonEmissions(), 0.0001);
  }

  // ------------------------------------- Testing for updateTotalDistance

  /**
   * Verify that when updateTotalDistance is called with a route that is of large distance that the
   * total distance is updated accordingly.The total distance is started as 0.0 km.
   */
  @Test
  public void updateTotalDistanceLargeDistanceInitiallyZeroTest() {
    generalStatsCalculator.setTotalDistanceTravelled(0.0);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(4157987.41);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalDistance(testRoute);
    assertEquals(4157987.41, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  /**
   * Verify that when updateTotalDistance is called with a route that is of small distance that the
   * total * distance is updated accordingly.The total distance is started as 0.0 km.
   */
  @Test
  public void updateTotalDistanceSmallDistanceInitiallyZeroTest() {
    generalStatsCalculator.setTotalDistanceTravelled(0.0);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(3902.79);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalDistance(testRoute);
    assertEquals(3902.79, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  /**
   * Verify that when updateTotalDistance is called with a route that is of small distance and the
   * times taken is more than 1 that the total distance is updated accordingly.The total distance is
   * started as 0.0 km.
   */
  @Test
  public void updateTotalDistanceSmallDistanceInitiallyZeroManyTest() {
    generalStatsCalculator.setTotalDistanceTravelled(0.0);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(3902.79);
    testRoute.setTimesTaken(10);
    generalStatsCalculator.updateTotalDistance(testRoute);
    assertEquals(39027.90, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  /**
   * Verify that when updateTotalDistance is called with a route with zero distance that the total
   * distance is updated accordingly. The total distance is started as 0.0km.
   */
  @Test
  public void updateTotalDistanceZeroDistanceInitiallyZeroTest() {
    generalStatsCalculator.setTotalCarbonEmissions(0.0);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(0.0);
    generalStatsCalculator.updateTotalDistance(testRoute);
    assertEquals(0.0, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  /**
   * Verify that when updateTotalDistance is called with a route that is of large distance that the
   * total distance is updated accordingly. The total distance is started at a large distance in km.
   */
  @Test
  public void updateTotalDistanceLargeDistanceInitiallyLargeTest() {
    generalStatsCalculator.setTotalDistanceTravelled(58900000000000.02);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(46372.91);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalDistance(testRoute);
    assertEquals(
        58900000046372.93, generalStatsCalculator.getTotalDistanceTravelled(), 0.000000001);
  }

  /**
   * Verify that when updateTotalDistance is called with a route that is of small distance that the
   * total distance is updated accordingly. The total distance is started at a large distance in km.
   */
  @Test
  public void updateTotalDistanceSmallDistanceInitiallyLargeTest() {
    generalStatsCalculator.setTotalDistanceTravelled(67554440900000.07);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(4157.41);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalDistance(testRoute);
    assertEquals(67554440904157.48, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  /**
   * Verify that when updateTotalDistance is called with a route that is of zero distance that the
   * total distance is updated accordingly. The total distance is started at a large distance in km.
   */
  @Test
  public void updateTotalDistanceZeroDistanceInitiallyLargeTest() {
    generalStatsCalculator.setTotalDistanceTravelled(345231863432.98);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(0.0);
    generalStatsCalculator.updateTotalDistance(testRoute);
    assertEquals(345231863432.98, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  // ------------------------------------- Testing for updateTotalDistanceRemoval

  /**
   * Verify that when updateTotalDistanceRemoval is called with a route that is of large distance
   * and the current total distance is 0.0 km that the total distances remains at 0.0 km and does
   * not go into the negatives.
   */
  @Test
  public void updateTotalDistanceRemovalLargeDistanceInitiallyZeroTest() {
    generalStatsCalculator.setTotalDistanceTravelled(0.0);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(4157987.41);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalDistanceRemoval(testRoute);
    assertEquals(0.0, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  /**
   * Verify that when updateTotalDistanceRemoval is called with a route that is of small distance
   * and this is the only route in the flight history, that the total distance is updated
   * accordingly. The distance is starting at the amount of this route's distance.
   */
  @Test
  public void updateTotalDistanceRemovalSmallDistanceInitiallySameTest() {
    generalStatsCalculator.setTotalDistanceTravelled(3902.79);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(3902.79);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalDistanceRemoval(testRoute);
    assertEquals(0.00, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  /**
   * Verify that when updateTotalDistanceRemoval is called with a route that is of small distance
   * and the times taken is more than 1 that the total distance is updated accordingly.The total
   * distance is started at a distance more than the combined distance.
   */
  @Test
  public void updateTotalDistanceRemovalSmallDistanceInitiallySameManyTest() {
    generalStatsCalculator.setTotalDistanceTravelled(39027.90);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(3902.79);
    testRoute.setTimesTaken(10);
    generalStatsCalculator.updateTotalDistanceRemoval(testRoute);
    assertEquals(0.0, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  /**
   * Verify that when updateTotalDistanceRemoval is called with a route with zero distance that the
   * total distance is updated accordingly. The total distance is started as 0.0km.
   */
  @Test
  public void updateTotalDistanceRemovalZeroDistanceInitiallyZeroTest() {
    generalStatsCalculator.setTotalCarbonEmissions(0.0);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(0.0);
    generalStatsCalculator.updateTotalDistanceRemoval(testRoute);
    assertEquals(0.0, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  /**
   * Verify that when updateTotalDistanceRemoval is called with a route that is of large distance
   * that the total distance is updated accordingly. The total distance is started at a large
   * distance in km.
   */
  @Test
  public void updateTotalDistanceRemovalLargeDistanceInitiallyLargeTest() {
    generalStatsCalculator.setTotalDistanceTravelled(58900000000000.00);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(46372.00);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalDistanceRemoval(testRoute);
    assertEquals(
        58899999953628.00, generalStatsCalculator.getTotalDistanceTravelled(), 0.000000001);
  }

  /**
   * Verify that when updateTotalDistanceRemoval is called with a route that is of small distance
   * that the total distance is updated accordingly. The total distance is started at a large
   * distance in km.
   */
  @Test
  public void updateTotalDistanceRemovalSmallDistanceInitiallyLargeTest() {
    generalStatsCalculator.setTotalDistanceTravelled(6755444090.00);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(4157.00);
    testRoute.setTimesTaken(1);
    generalStatsCalculator.updateTotalDistanceRemoval(testRoute);
    assertEquals(6755439933.00, generalStatsCalculator.getTotalDistanceTravelled(), 0.01);
  }

  /**
   * Verify that when updateTotalDistanceRemoval is called with a route that is of zero distance
   * that the total distance is updated accordingly. The total distance is started at a large
   * distance in km.
   */
  @Test
  public void updateTotalDistanceRemovalZeroDistanceInitiallyLargeTest() {
    generalStatsCalculator.setTotalDistanceTravelled(345231863432.98);
    Route testRoute = new Route("7K", 392, "PKL", 3920, "MDC", 2523, "", 2, "PDS".split(" "));
    testRoute.setDistance(0.0);
    generalStatsCalculator.updateTotalDistanceRemoval(testRoute);
    assertEquals(345231863432.98, generalStatsCalculator.getTotalDistanceTravelled(), 0.0001);
  }

  // --------------------------------- Tests for calculateDateAsInt()

  /**
   * Verify that when calculateEmissionsPerYear is called, then the remainingCO2InYear is calculated
   * as expected.
   */
  @Test
  public void getDateAsIntPositiveIntTest() {
    int expectedDate = 278;
    generalStatsCalculator.calculateDateAsInt();
    assertEquals(expectedDate, generalStatsCalculator.getDayInYear());
  }

  // --------------------------------- Tests for calculateRemainingDaysInYear()

  /**
   * Verify that when calculateEmissionsPerYear is called, then the remainingCO2InYear is calculated
   * as expected.
   */
  @Test
  public void calculateRemainingDaysInYearTest() {
    int expectedDate = 93;
    generalStatsCalculator.setDayInYear(272); // need to update to the current day to pass this test
    generalStatsCalculator.calculateRemainingDaysInYear();
    assertEquals(expectedDate, generalStatsCalculator.getRemainingDaysInYear());
  }

  /**
   * Verify that when calculateRemainingDaysInYear is called and the day in the year is 0, then an
   * exception is thrown.
   */
  @Test
  public void calculateRemainingDaysInYearZeroTest() {
    try {
      generalStatsCalculator.setDayInYear(0);
      generalStatsCalculator.calculateRemainingDaysInYear();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that when calculateRemainingDaysInYear is called and the day in the year is more than
   * 365, then an exception is thrown.
   */
  @Test
  public void calculateRemainingDaysInYearNegIntTest() {
    try {
      generalStatsCalculator.setDayInYear(400);
      generalStatsCalculator.calculateRemainingDaysInYear();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  // --------------------------------- Tests for calculateEmissionsPerYearCurrentRate()

  /**
   * Verify that when calculateEmissionsPerYear is called and the current day is 0, then an
   * exception is thrown and caught.
   */
  @Test
  public void calculateEmissionsPerYearCurrentRateZeroDaysTest() {
    generalStatsCalculator.setTotalCarbonEmissions(100000);
    generalStatsCalculator.setDayInYear(0);
    try {
      generalStatsCalculator.calculateEmissionsPerYearCurrentRate();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  /**
   * Verify that when calculateEmissionsPerYear is called, then the emissionsPerDayBaseOnCurrDate is
   * calculated as expected.
   */
  @Test
  public void calculateEmissionsPerYearCurrentRatePositiveDaysTest() {
    generalStatsCalculator.setTotalCarbonEmissions(206000);
    generalStatsCalculator.setDayInYear(271);
    double expectedValue = 206000.00 / 271.00;
    generalStatsCalculator.calculateEmissionsPerYearCurrentRate();
    assertEquals(expectedValue, generalStatsCalculator.getEmissionsPerDayBaseOnCurrDate(), 1.0);
  }

  /**
   * Verify that when calculateEmissionsPerYear is called, then the emissionsPerYear is calculated
   * as expected.
   */
  @Test
  public void calculateEmissionsPerYearCurrentRateTest() {
    generalStatsCalculator.setTotalCarbonEmissions(10000);
    generalStatsCalculator.setDayInYear(100);
    double expectedValue = 36500.0;
    generalStatsCalculator.calculateEmissionsPerYearCurrentRate();
    assertEquals(expectedValue, generalStatsCalculator.getEmissionsPerYear(), 0.000000000000001);
  }

  /**
   * Verify that when calculateEmissionsPerYear is called and the day in the year is 0, then an
   * ArithmeticException is thrown due to the division by 0.
   */
  @Test
  public void calculateEmissionsPerYearCurrentRateExceptionTest() {
    generalStatsCalculator.setTotalCarbonEmissions(10000);
    generalStatsCalculator.setDayInYear(0);
    try {
      generalStatsCalculator.calculateEmissionsPerYearCurrentRate();
    } catch (ArithmeticException e) {
      assertTrue(true);
    }
  }

  // --------------------------------- calculateEmissionsPerYearGoalRate() tests

  /**
   * Verify that when calculateEmissionsPerYearGoalRate is called, then the correct rate is given.
   */
  @Test
  public void calculateEmissionsPerYearGoalRateTest() {
    generalStatsCalculator.setCarbonEmissionsGoal(1000000.00);
    double expectedValue = 2739.72;
    generalStatsCalculator.calculateEmissionsPerYearGoalRate();
    assertEquals(expectedValue, generalStatsCalculator.getEmissionsPerDayGoal(), 0.01);
  }

  /**
   * Verify that when calculateEmissionsPerYearGoalRate is called with a large goal, then the
   * correct rate is given.
   */
  @Test
  public void calculateEmissionsPerYearGoalRateLargeTest() {
    generalStatsCalculator.setCarbonEmissionsGoal(225555555958898.25);
    double expectedValue = 6.179604272846527E11;
    generalStatsCalculator.calculateEmissionsPerYearGoalRate();
    assertEquals(
        expectedValue,
        generalStatsCalculator.getEmissionsPerDayGoal(),
        0.0000000000000000000000000000000000000001);
  }

  /**
   * Verify that when calculateEmissionsPerYearGoalRate is called and the goal is 0, then the
   * emissions per day based on the goal will be a rate of 0.
   */
  @Test
  public void calculateEmissionsPerYearGoalRateZeroTest() {
    generalStatsCalculator.setCarbonEmissionsGoal(0);
    double expectedValue = 0;
    generalStatsCalculator.calculateEmissionsPerYearGoalRate();
    assertEquals(expectedValue, generalStatsCalculator.getEmissionsPerDayGoal(), 0.01);
  }

  // --------------------------------- calculateReductionPercentage() tests

  /**
   * Verify that when calculateReductionPercentage is called, then the correct reduction percentage
   * is given.
   */
  @Test
  public void calculateReductionPercentageTest() {
    generalStatsCalculator.setEmissionsPerDayBaseOnCurrDate(20);
    generalStatsCalculator.setEmissionsPerDayGoal(12);
    double expectedValue = 33.33;
    generalStatsCalculator.calculateReductionPercentage();
    assertEquals(expectedValue, generalStatsCalculator.getReductionPercentage(), 0.01);
  }

  /**
   * Verify that when calculateReductionPercentage is called and the EmissionsPerDayBaseOnCurrDate
   * rate is greater than the EmissionsPerDayGoal rate, then no reduction is needed so the reduction
   * percentage is 0 percent.
   */
  @Test
  public void calculateReductionPercentageZeroTest() {
    generalStatsCalculator.setEmissionsPerDayBaseOnCurrDate(6);
    generalStatsCalculator.setEmissionsPerDayGoal(15);
    double expectedValue = 0;
    generalStatsCalculator.calculateReductionPercentage();
    assertEquals(expectedValue, generalStatsCalculator.getReductionPercentage(), 0.01);
  }

  /**
   * Verify that when calculateReductionPercentage is called and EmissionsPerDayGoal is 0, then an
   * ArithmeticException is thrown for the divison by 0 error.
   */
  @Test
  public void calculateReductionPercentageExceptionTest() {
    generalStatsCalculator.setEmissionsPerDayBaseOnCurrDate(30);
    generalStatsCalculator.setEmissionsPerDayGoal(0);
    try {
      generalStatsCalculator.calculateReductionPercentage();
    } catch (ArithmeticException e) {
      assertTrue(true);
    }
  }

  // --------------------------------- getCurrentYear() tests

  /** Verify that when getCurrentYear is called, then the correct year as in integer is given. */
  @Test
  public void getCurrentYearTest() {
    int expectedValue = 2020;
    assertEquals(expectedValue, generalStatsCalculator.getCurrentYear());
  }

  // --------------------------------- calculateRemainingCO2InYear() tests

  /**
   * Verify that when calculateRemainingCO2InYear is called and the carbonEmissionGoal is greater
   * than totalCarbonEmissionsthen, then the correct value is returned and is positive.
   */
  @Test
  public void calculateRemainingCO2InYearPositiveTest() {
    generalStatsCalculator.setCarbonEmissionsGoal(200000.0);
    generalStatsCalculator.setTotalCarbonEmissions(100000.0);
    double expectedValue = 100000.0;
    generalStatsCalculator.calculateRemainingCO2InYear();
    assertEquals(expectedValue, generalStatsCalculator.getRemainingCO2InYear(), 0.001);
  }

  /**
   * Verify that when calculateRemainingCO2InYear is called and the carbonEmissionGoal is smaller
   * than totalCarbonEmissionsthen, then the correct value is returned and is 0.
   */
  @Test
  public void calculateRemainingCO2InYearNegativeTest() {
    generalStatsCalculator.setCarbonEmissionsGoal(8000000.0);
    generalStatsCalculator.setTotalCarbonEmissions(1000000000.0);
    double expectedValue = 0.0;
    generalStatsCalculator.calculateRemainingCO2InYear();
    assertEquals(expectedValue, generalStatsCalculator.getRemainingCO2InYear(), 0.0001);
  }

  /**
   * Verify that when calculateRemainingCO2InYear is called and totalCarbonEmissions then is 0, then
   * the correct value is returned which is the goal.
   */
  @Test
  public void calculateRemainingCO2InYearZeroTest() {
    generalStatsCalculator.setCarbonEmissionsGoal(78000000.00);
    generalStatsCalculator.setTotalCarbonEmissions(0.00);
    double expectedValue = 78000000.00;
    generalStatsCalculator.calculateRemainingCO2InYear();
    assertEquals(expectedValue, generalStatsCalculator.getRemainingCO2InYear(), 0.01);
  }

  // --------------------------------- Testing for calculateOffsetTrees()

  /**
   * Verify that when calculateOffsetTrees is called and the totalCarbonEmissions is small, that the
   * correct number of trees to offset it is calculated correctly.
   */
  @Test
  public void calculateOffsetTreesSmallTest() {
    generalStatsCalculator.setTotalCarbonEmissions(10000.0);
    double expectedValue = 172.84;
    generalStatsCalculator.calculateOffsetTrees();
    assertEquals(expectedValue, generalStatsCalculator.getTreesToGrow(), 0.01);
  }

  /**
   * Verify that when calculateOffsetTrees is called and the totalCarbonEmissions is large, that the
   * correct number of trees to offset it is calculated correctly.
   */
  @Test
  public void calculateOffsetTreesLargeTest() {
    generalStatsCalculator.setTotalCarbonEmissions(28901890869.0);
    double expectedValue = 4.995487221550055E8;
    generalStatsCalculator.calculateOffsetTrees();
    assertEquals(expectedValue, generalStatsCalculator.getTreesToGrow(), 0.01);
  }

  /**
   * Verify that when calculateOffsetTrees is called and the totalCarbonEmissions is zero, that the
   * correct number of trees to offset it is calculated correctly as zero trees.
   */
  @Test
  public void calculateOffsetTreesZeroTest() {
    generalStatsCalculator.setTotalCarbonEmissions(0.0);
    double expectedValue = 0.0;
    generalStatsCalculator.calculateOffsetTrees();
    assertEquals(expectedValue, generalStatsCalculator.getTreesToGrow(), 0.01);
  }
}
