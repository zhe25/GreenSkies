package model.data;

import javafx.util.Pair;
import model.database.SQLiteDatabase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class to keep track of all the data that has been uploaded to the application, also interact with
 * database.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class Storage {

  /** A list of all the airlines that have been uploaded to the application. */
  private final HashMap<String, List<Airline>> airlineFiles = new HashMap<>();

  /** The name of the airline files which is currently in use. */
  private String currentAirlineFile;

  /** A list of all the airports that have been uploaded to the application. */
  private final HashMap<String, List<Airport>> airportFiles = new HashMap<>();

  /** The name of the airport file which is2 currently in use. */
  private String currentAirportFile;

  /** A list of all the routes that have been uploaded to the application. */
  private final HashMap<String, List<Route>> routeFiles = new HashMap<>();

  /** The name of the route file which is currently in use. */
  private String currentRouteFile;

  /** Temporary list of routes used when adding routes to history. */
  private final List<Route> tempRoutes = new ArrayList<>();

  /** A list of all the values calculated for the distances of the routes in the history. */
  private final List<Double> analyseDistanceResult = new ArrayList<>();

  /** A list of all the values calculated for the emissions of the routes in the history. */
  private final List<Double> analyseEmissionResult = new ArrayList<>();

  /** The names of routes compared using the analyse distance button. */
  private Pair<String, String> comparedRoutes;

  /** A list of all the routes that have been added to the user's personal history. */
  private final List<Route> history = new ArrayList<>();

  /**
   * A HashMap of all the source airports the user has visited and the number of times they have
   * visited them.
   */
  private final HashMap<String, Integer> historySrcAirports = new HashMap<>();

  /**
   * A HashMap of all the destination airports the user has visited and the number of times they
   * have visited them.
   */
  private final HashMap<String, Integer> historyDestAirports = new HashMap<>();

  /** The database in which data added to the application is stored. */
  private final SQLiteDatabase database = new SQLiteDatabase();

  /** Airport to display in google map. */
  private HashMap<Integer, ArrayList<Airport>> MapAirport = new HashMap<>();

  /**
   * This method returns a List of all the names of the stored airline files, or an empty List if no
   * Airline files have been stored.
   *
   * @return a List of the names of files in AirlineFiles.
   */
  public List<String> getAirlineFileNames() {
    return new ArrayList<>(airlineFiles.keySet());
  }

  /**
   * This method returns the name of the Airline file which is currently open, or null if none is
   * open.
   *
   * @return the name of the currently open Airline file.
   */
  public String getCurrentAirlineFile() {
    return currentAirlineFile;
  }

  /**
   * This method changes the name of the current file to the given name if the name matches one of
   * the stored files and throws an IllegalArgumentException if the name does not match one of the
   * stored files.
   *
   * @param newCurrentFile the name of an airline file to change currentAirlineFile to.
   */
  public void setCurrentAirlineFile(String newCurrentFile) {
    if (airlineFiles.containsKey(newCurrentFile) || newCurrentFile == null) {
      currentAirlineFile = newCurrentFile;
    }
  }

  /**
   * This method returns an empty List if there is not current file, or all the Airlines in the
   * current file if there is a current file.
   *
   * @return a list of Airlines in the current file.
   */
  public List<Airline> getAirlines() {
    if (currentAirlineFile == null) {
      return new ArrayList<>();
    }
    return airlineFiles.get(currentAirlineFile);
  }

  /**
   * This method returns a List of all the names of the stored airport files, or an empty List if no
   * Airport files have been stored.
   *
   * @return a List of the names of files in AirportFiles.
   */
  public List<String> getAirportFileNames() {
    return new ArrayList<>(airportFiles.keySet());
  }

  /**
   * This method returns the name of the Airport file which is currently open, or null if none is
   * open.
   *
   * @return the name of the currently open Airport file.
   */
  public String getCurrentAirportFile() {
    return currentAirportFile;
  }

  /**
   * This method changes the name of the current file to the given name if the name matches one of
   * the stored files and throws an IllegalArgumentException if the name does not match one of the
   * stored files.
   *
   * @param newCurrentFile the name of an airport file to change currentAirportFile to.
   */
  public void setCurrentAirportFile(String newCurrentFile) {
    if (airportFiles.containsKey(newCurrentFile) || newCurrentFile == null) {
      currentAirportFile = newCurrentFile;
    }
  }

  /**
   * This method returns an empty List if there is not current file, or all the Airports in the
   * current file if there is a current file.
   *
   * @return a list of Airports in the current file.
   */
  public List<Airport> getAirports() {
    if (currentAirportFile == null) {
      return new ArrayList<>();
    }
    return airportFiles.get(currentAirportFile);
  }

  /**
   * This method returns a List of all the names of the stored Route files, or an empty List if no
   * Route files have been stored.
   *
   * @return a List of the names of files in RouteFiles.
   */
  public List<String> getRouteFileNames() {
    return new ArrayList<>(routeFiles.keySet());
  }

  /**
   * This method returns the name of the Route file which is currently open, or null if none is
   * open.
   *
   * @return the name of the currently open Route file.
   */
  public String getCurrentRouteFile() {
    return currentRouteFile;
  }

  /**
   * This method changes the name of the current file to the given name if the name matches one of
   * the stored files and throws an IllegalArgumentException if the name does not match one of the
   * stored files.
   *
   * @param newCurrentFile the name of an airline file to change currentRouteFile to.
   */
  public void setCurrentRouteFile(String newCurrentFile) {
    if (routeFiles.containsKey(newCurrentFile) || newCurrentFile == null) {
      currentRouteFile = newCurrentFile;
    }
  }

  /**
   * This method returns an empty List if there is not current file, or all the Routes in the
   * current file if there is a current file.
   *
   * @return a list of Routes in the current file.
   */
  public List<Route> getRoutes() {
    if (currentRouteFile == null) {
      return new ArrayList<>();
    }
    return routeFiles.get(currentRouteFile);
  }

  /** @return a list of Route object from route to add to history. */
  public List<Route> getTempRoutes() {
    return tempRoutes;
  }

  /** @return a list of routes in the user's history. */
  public List<Route> getHistory() {
    return history;
  }

  /** @return a list of distance has been analysed . */
  public List<Double> getAnalyseDistanceResult() {
    return analyseDistanceResult;
  }

  /** @ADD distance has been analysed into a list . */
  public void setAnalyseDistanceResult(double distance) {
    analyseDistanceResult.add(distance);
  }

  /** @return a list of emissions has been analysed . */
  public List<Double> getAnalyseEmissionResult() {
    return analyseEmissionResult;
  }

  /** @ADD emissions has been analysed into a list . */
  public void setAnalyseEmissionResult(double emission) {
    analyseEmissionResult.add(emission);
  }

  /**
   * This method adds an arrayList of routes to the history list.
   *
   * @param routes An arrayList of routes.
   */
  public void addToHistory(Route routes) {
    history.add(routes);
  }

  /**
   * This method adds a list of data from a file to storage.
   *
   * @param data The list of data.
   * @param type Type of data to be stored.
   */
  public void setData(List<DataType> data, String type, String filename) {
    if (type.matches("Airline")) {
      List<Airline> airlines = new ArrayList<>();
      if (filename == null) {
        filename = currentAirlineFile;

      } else {
        currentAirlineFile = filename;
      }
      for (DataType entry : data) {
        Airline airline = (Airline) entry;
        if (airline != null) {
          airlines.add(airline);
        }
      }
      airlineFiles.put(filename, airlines);
    } else if (type.matches("Airport")) {
      List<Airport> airports = new ArrayList<>();
      if (filename == null) {
        filename = currentAirportFile;
      } else {
        currentAirportFile = filename;
      }
      for (DataType entry : data) {
        Airport airport = (Airport) entry;
        airports.add(airport);
      }
      airportFiles.put(filename, airports);
    } else if (type.matches("Route")) {
      List<Route> routes = new ArrayList<>();
      if (filename == null) {
        filename = currentRouteFile;
      } else {
        currentRouteFile = filename;
      }
      for (DataType entry : data) {
        Route route = (Route) entry;
        routes.add(route);
      }
      routeFiles.put(filename, routes);
    } else {
      throw new IllegalArgumentException("Type must be airline, airport or route");
    }
  }

  /**
   * This method update database after user upload data.
   *
   * @param fileType The string shows what type of data user may uploaded.
   */
  public void updateDatabase(String fileType) {
    if (!(fileType.matches("Airport")
        || fileType.matches("Airline")
        || fileType.matches("Route"))) {
      throw new IllegalArgumentException("Type must be Airport, Airline or Route");
    }
    database.closeAutoCommite();
    switch (fileType) {
      case "Airport":
        database.initialiseTable("Airport", getCurrentAirportFile());
        database.updateAirportTable(getAirports());
        database.updateTableList(getCurrentAirportFile(), "Airport");
        break;
      case "Airline":
        database.initialiseTable("Airline", getCurrentAirlineFile());
        database.updateAirlineTable(getAirlines());
        database.updateTableList(getCurrentAirlineFile(), "Airline");
        break;
      case "Route":
        database.initialiseTable("Route", getCurrentRouteFile());
        database.updateRoute(getRoutes());
        database.updateTableList(getCurrentRouteFile(), "Route");
    }
  }

  /**
   * This method initialises storage with data from database after user start the application
   *
   * @throws SQLException This throws an SQLException.
   */
  public void initialiseStorage() {
    database.initialiseStorage(this);
  }

  /**
   * This method updates the count of source aiport visits in the flight history.
   *
   * @param airportName The name of the source airport of the route being added to history.
   */
  public void addToHistorySrcAirports(String airportName) {
    if (historySrcAirports.containsKey(airportName)) {
      historySrcAirports.put(airportName, historySrcAirports.get(airportName) + 1);
    } else {
      historySrcAirports.put(airportName, 1);
    }
  }

  /**
   * This method gets the historySrcAirports.
   *
   * @return A HashMap with the names of source airports as keys and the number of times they have
   *     been visited as the value.
   */
  public HashMap<String, Integer> getHistorySrcAirports() {
    return historySrcAirports;
  }

  /**
   * This method updates the count of destination aiport visits in the flight history.
   *
   * @param airportName The name of the destination airport of the route being added to history.
   */
  public void addToHistoryDestAirports(String airportName) {
    if (historyDestAirports.containsKey(airportName)) {
      historyDestAirports.put(airportName, historyDestAirports.get(airportName) + 1);
    } else {
      historyDestAirports.put(airportName, 1);
    }
  }

  /**
   * This method gets the history destination airports.
   *
   * @return A HashMap of the names of destination airports as key and the number of times they have
   *     been added to history as value.
   */
  public HashMap<String, Integer> getHistoryDestAirports() {
    return historyDestAirports;
  }

  /**
   * This method updates the count of source airport visits in the flight history when the route is
   * removed.
   *
   * @param airportName The name of the source airport of the route being remove from history.
   */
  public void removeFromHistorySrcAirports(String airportName) {
    if (historySrcAirports.containsKey(airportName)) {
      historySrcAirports.put(airportName, historySrcAirports.get(airportName) - 1);
      if (historySrcAirports.get(airportName) < 0) {
        historySrcAirports.put(airportName, 0);
      }
    }
  }

  /**
   * This method updates the count of destination airport visits in the flight history when the
   * route is removed.
   *
   * @param airportName The name of the destination airport of the route being remove from history.
   */
  public void removeFromHistoryDestAirports(String airportName) {
    if (historyDestAirports.containsKey(airportName)) {
      historyDestAirports.put(airportName, historyDestAirports.get(airportName) - 1);
      if (historyDestAirports.get(airportName) < 0) {
        historyDestAirports.put(airportName, 0);
      }
    }
  }

  /**
   * This method gets the airline file list.
   *
   * @return A HashMap of the names of airline file as key and the list of airlines that is parsed
   *     in the file.
   */
  public HashMap<String, List<Airline>> getAirlineFileList() {
    return airlineFiles;
  }

  /**
   * This method gets the airport file list.
   *
   * @return A HashMap of the names of airport file as key and the list of airports that is parsed
   *     in the file.
   */
  public HashMap<String, List<Airport>> getAirportFileList() {
    return airportFiles;
  }

  /**
   * This method gets the route file list.
   *
   * @return A HashMap of the names of route file as key and the list of routes that is parsed in
   *     the file.
   */
  public HashMap<String, List<Route>> getRouteFileList() {
    return routeFiles;
  }

  /**
   * This method returns the names of the most recently compared routes.
   *
   * @return A pair of two route names.
   */
  public Pair<String, String> getComparedRoutes() {
    return comparedRoutes;
  }

  /**
   * This method sets the two compared route names to the given values.
   *
   * @param comparedRoutes Pair of strings corresponding to the names of two routes.
   */
  public void setComparedRoutes(Pair<String, String> comparedRoutes) {
    this.comparedRoutes = comparedRoutes;
  }

  /**
   * This method gets the route for google map.
   *
   * @return An array list with the route the user want to display on google map.
   */
  public HashMap<Integer, ArrayList<Airport>> getMapAirport() {
    return MapAirport;
  }
}
