package controller.guiController.dataview;

import controller.analysis.GeneralStatsCalculator;
import controller.analysis.RouteStatsCalculator;
import model.data.Route;
import model.data.Storage;
import model.database.SQLiteDatabase;
import model.loader.Loader;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for FlightHistoryController class.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class FlightHistoryControllerTest extends ApplicationTest {

  private final FlightHistoryController flightHistoryController = new FlightHistoryController();
  private Storage storage;
  RouteStatsCalculator routeStatsCalculator;
  GeneralStatsCalculator generalStatsCalculator;
  SQLiteDatabase database;
  Route AER_KZN;
  Route ASF_KZN;
  Route ASF_MRV;

  @Before
  public void setUp() throws NoSuchFieldException, IllegalAccessException {
    Field field_GeneralStatsCalculator =
        flightHistoryController.getClass().getDeclaredField("generalStatsCalculator");
    Field field_RouteStatsCalculator =
        flightHistoryController.getClass().getDeclaredField("routeStatsCalculator");
    Field field_Storage = flightHistoryController.getClass().getDeclaredField("storage");
    Field field_Database = flightHistoryController.getClass().getDeclaredField("database");

    field_GeneralStatsCalculator.setAccessible(true);
    field_RouteStatsCalculator.setAccessible(true);
    field_Storage.setAccessible(true);
    field_Database.setAccessible(true);

    storage = (Storage) field_Storage.get(flightHistoryController);
    routeStatsCalculator =
        (RouteStatsCalculator) field_RouteStatsCalculator.get(flightHistoryController);
    generalStatsCalculator =
        (GeneralStatsCalculator) field_GeneralStatsCalculator.get(flightHistoryController);
    database = (SQLiteDatabase) field_Database.get(flightHistoryController);

    Loader loader = new Loader(storage);
    try {
      loader.loadFile("../seng202_project/src/test/java/TestFiles/routesTest.csv", "Route");
      loader.loadFile("../seng202_project/src/test/java/TestFiles/airports.csv", "Airport");
      for (Route route : storage.getRoutes()) {
        route.initCheckBox();
      }
    } catch (Exception ignored) {
    }
    AER_KZN = storage.getRoutes().get(0); // 1506
    ASF_KZN = storage.getRoutes().get(1); // 1040
    ASF_MRV = storage.getRoutes().get(2); // 448
    AER_KZN.setTimesTaken(1);
    ASF_KZN.setTimesTaken(1);
    ASF_MRV.setTimesTaken(1);
    storage.getHistory().add(AER_KZN);
    storage.getHistory().add(ASF_KZN);
    storage.getHistory().add(ASF_MRV);
    RouteAddToHistoryPopUpController routeAddToHistoryPopUpController =
        new RouteAddToHistoryPopUpController();
    routeAddToHistoryPopUpController.updateReportStats(AER_KZN);
    routeAddToHistoryPopUpController.updateReportStats(ASF_KZN);
    routeAddToHistoryPopUpController.updateReportStats(ASF_MRV);
  }

  @Test
  public void updateMostDistanceRoute() {
    assertEquals(
        AER_KZN.getDistance(),
        routeStatsCalculator.getMostDistanceRoutes().get(0).getDistance(),
        0.0001);

    storage.getHistory().remove(AER_KZN);
    flightHistoryController.updateReportStatsDeletionSingleRoute(AER_KZN);

    assertEquals(
        AER_KZN.getDistance(),
        routeStatsCalculator.getMostDistanceRoutes().get(0).getDistance(),
        0.0001);
  }

  @Test
  public void updateMostEmissionsRoute() {
    assertEquals(
        AER_KZN.getEmissions(),
        routeStatsCalculator.getMostEmissionsRoutes().get(0).getEmissions(),
        0.0001);

    storage.getHistory().remove(AER_KZN);
    flightHistoryController.updateReportStatsDeletionSingleRoute(AER_KZN);

    assertEquals(
        AER_KZN.getEmissions(),
        routeStatsCalculator.getMostDistanceRoutes().get(0).getEmissions(),
        0.0001);
  }
}
