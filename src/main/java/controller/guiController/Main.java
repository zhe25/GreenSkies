package controller.guiController;

import controller.analysis.AirportStatsCalculator;
import controller.analysis.Filterer;
import controller.analysis.GeneralStatsCalculator;
import controller.analysis.RouteStatsCalculator;
import model.data.Storage;
import model.loader.Loader;
import java.sql.SQLException;

/**
 * The controller class which contains the controls for the main.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class Main {
  /** The storage for the application. */
  private static Storage storage = new Storage();
  /** The data loader for the the application. */
  private static Loader loader = new Loader(storage);
  /** The filter for the application. */
  private static Filterer filterer = new Filterer();
  /** The GeneralStatsCalculator for the application. */
  private static GeneralStatsCalculator generalStatsCalculator = new GeneralStatsCalculator();
  /** The RouteStatsCalculator for the application. */
  private static RouteStatsCalculator routeStatsCalculator = new RouteStatsCalculator();
  /** The AirportStatsCalculator for the application. */
  private static AirportStatsCalculator airportStatsCalculator = new AirportStatsCalculator();

  public static Storage getStorage() {
    return storage;
  }

  public static Loader getLoader() {
    return loader;
  }

  public static Filterer getFilterer() {
    return filterer;
  }

  public static GeneralStatsCalculator getGeneralStatsCalculator() {
    return generalStatsCalculator;
  }

  public static RouteStatsCalculator getRouteStatsCalculator() {
    return routeStatsCalculator;
  }

  public static AirportStatsCalculator getAirportStatsCalculator() {
    return airportStatsCalculator;
  }

  /**
   * This method is the main which initializes the storage and starts the application.
   *
   * @param args The arguments passed in.
   * @throws SQLException This throws an SQLException.
   * @throws ClassNotFoundException This throws a ClassNotFoundException.
   */
  public static void main(String[] args) throws SQLException, ClassNotFoundException {

    storage.initialiseStorage();
    GreenSkiesApplication.main(args);
  }
}
