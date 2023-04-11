package model.loader;

import model.data.Route;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to process route data which has been extracted from a file by Loader class. Each entry in
 * each line is checked for errors. If the line has no errors than a Route object is created with
 * attributes determined by the data in the line. If the line contains an error this is added to the
 * ErrorCounter and the parser moves onto the next line without creating a route object.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class RouteParser extends Parser {

  /**
   * * Codes corresponding to each Route to check for duplicates. The external ArrayList indexed by
   * airline ID and the internal ArrayList contains {sourceID, destinationID} arrays for all Routes
   * with that airline ID.
   */
  private final ArrayList<ArrayList<int[]>> routeCodes = new ArrayList<>();

  /** Variable name to represent line index */
  private final int airline = 0,
      airlineID = 1,
      sourceAirport = 2,
      sourceAirportID = 3,
      destinationAirport = 4,
      destinationAirportID = 5,
      codeshare = 6,
      stops = 7,
      equipment = 8;

  /**
   * This method initializes error collection and calls dataParser method to begin processing data.
   *
   * @param dataFile ArrayList of a string for each line in the file.
   */
  public RouteParser(ArrayList<String> dataFile, List<Route> existingRoutes) {
    super(dataFile, 12);
    for (Route route : existingRoutes) {
      addRoute(route);
    }
    dataParser();
    if (!getValidFile()) {
      if (totalErrors == 1) {
        throw new RuntimeException(
            "Entry contains errors and was not uploaded.\n" + getErrorMessage(false));
      } else {
        throw new RuntimeException(
            "File rejected: all lines contain errors.\n" + getErrorMessage(false));
      }
    }
  }

  /**
   * This method adds a route to an array containing the routes source and destination airport IDs
   * to the internal ArrayList which is found at an index of the external ArrayList which
   * corresponds to the Route's airline ID.
   *
   * @param route The Route from which to get the code to add to RouteCodes.
   */
  private void addToRouteCodes(Route route) {
    int[] routeCode = new int[2];
    routeCode[0] = route.getSourceAirportID();
    routeCode[1] = route.getDestinationAirportID();
    int index = route.getAirlineID();
    while (routeCodes.size() < index + 1) {
      routeCodes.add(new ArrayList<>());
    }
    routeCodes.get(index).add(routeCode);
  }

  /** This method initializes the error lookup array with message for each error code. */
  @Override
  protected void initErrorLookup() {
    errorLookup[0] = "Wrong number of parameters";
    errorLookup[1] = "Invalid airline code";
    errorLookup[2] = "Invalid airline ID";
    errorLookup[3] = "Invalid source airport code";
    errorLookup[4] = "Invalid source airport ID";
    errorLookup[5] = "Invalid destination airport code";
    errorLookup[6] = "Invalid destination airport ID";
    errorLookup[7] = "Invalid value for codeshare";
    errorLookup[8] = "Invalid value for number of stops";
    errorLookup[9] = "Invalid equipment code";
    errorLookup[10] = "Duplicate route";
    errorLookup[11] = "Unknown error";
  }

  /**
   * This method is called when RouteParser is initialized. Calls validate method to check each
   * line. If line is valid, creates Route object with attributes from line and adds route to routes
   * set.
   */
  @Override
  protected void dataParser() {
    for (String dataLine : dataFile) {
      if (totalErrors > 200) {
        totalErrors = 0;
        throw new RuntimeException(
            "File rejected: more than 200 lines contain errors.\n" + getErrorMessage(false));
      }
      parseLine(dataLine);
    }
  }

  /**
   * This method calls the validator method to check a single line and then add that line to
   * parserData if it is valid.
   *
   * @param dataLine Line to be checked and added to parserData.
   */
  protected void parseLine(String dataLine) {
    String[] line = dataLine.split(",");
    if (validater(line)) {
      try {
        Route route =
            new Route(
                line[airline],
                Integer.parseInt(line[airlineID]),
                line[sourceAirport],
                Integer.parseInt(line[sourceAirportID]),
                line[destinationAirport],
                Integer.parseInt(line[destinationAirportID]),
                line[codeshare],
                Integer.parseInt(line[stops]),
                line[equipment].split(" "));
        addRoute(route);
      } catch (Exception e) {
        errorCounter(11);
      }
    }
  }

  /**
   * This method checks that line has expected number of entries and calls isValid method to check
   * that each token on the line matches the expected pattern.
   *
   * @param line A string made up of comma-separated tokens representing data about a route
   * @return True if all tokens are valid, false otherwise
   */
  @Override
  protected boolean validater(String[] line) {
    if (line.length != 9) {
      errorCounter(0);
      return false;
    }

    changeNulls(line);

    if (!isAirlineValid(line[airline])) {
      errorCounter(1);
      return false;
    }

    if (!isAirlineIDValid(line[airlineID])) {
      errorCounter(2);
      return false;
    }

    if (!isAirportValid(line[sourceAirport])) {
      errorCounter(3);
      return false;
    }

    if (!isAirportIDValid(line[sourceAirportID])) {
      errorCounter(4);
      return false;
    }

    if (!isAirportValid(line[destinationAirport])) {
      errorCounter(5);
      return false;
    }

    if (!isAirportIDValid(line[destinationAirportID])) {
      errorCounter(6);
      return false;
    }

    if (!isCodeshareValid(line[codeshare])) {
      errorCounter(7);
      return false;
    }

    if (!isStopsValid(line[stops])) {
      errorCounter(8);
      return false;
    }

    if (!isEquipmentValid(line[equipment])) {
      errorCounter(9);
      return false;
    }

    return true;
  }

  /**
   * This method replaces '\N' in string with the value 0.
   *
   * @param line Array to replace nulls in.
   */
  protected void changeNulls(String[] line) {
    if (line[airlineID].equals("\\N")) {
      line[airlineID] = "0";
    }

    if (line[sourceAirportID].equals("\\N")) {
      line[sourceAirportID] = "0";
    }

    if (line[destinationAirportID].equals("\\N")) {
      line[destinationAirportID] = "0";
    }
  }

  /**
   * This method verifies whether string is a valid ICAO or IATA airport code.
   *
   * @param airport The string to be verified.
   * @return True if string matches ICAO or IATA format, false otherwise.
   */
  protected boolean isAirportValid(String airport) {
    return (airport.length() == 3 || airport.length() == 4) && airport.matches("[A-Z]+");
  }

  /**
   * This method verifies whether string is a valid openFlight airport id code.
   *
   * @param airportID The string to be verified.
   * @return True if string matches openFlights format, false otherwise.
   */
  protected boolean isAirportIDValid(String airportID) {
    return (airportID.length() <= 5 && airportID.matches("[0-9]+"));
  }

  /**
   * This method verifies whether string is a valid ICAO or IATA airline code.
   *
   * @param airline The string to be verified.
   * @return True if string matches ICAO or IATA format, false otherwise.
   */
  protected boolean isAirlineValid(String airline) {
    return (airline.length() == 2 || airline.length() == 3) && airline.matches("[0-9A-Z]+");
  }

  /**
   * This method verifies whether string is a valid openFlights airline id code.
   *
   * @param airlineID The string to be verified.
   * @return True if string matches openFlights format, false otherwise.
   */
  protected boolean isAirlineIDValid(String airlineID) {
    return airlineID.length() <= 5 && airlineID.matches("[0-9]+");
  }

  /**
   * This method verifies whether string is "Y" (if route is a codeshare) or empty (if it is not).
   *
   * @param codeshare the string to be tested.
   * @return true if string is "Y" or empty, false otherwise.
   */
  protected boolean isCodeshareValid(String codeshare) {
    return codeshare.isEmpty() || codeshare.equals("Y");
  }

  /**
   * This method verifies whether string stops represents a number less than 10.
   *
   * @param stops The string to be verified.
   * @return True if string is a number less than 10, false otherwise.
   */
  protected boolean isStopsValid(String stops) {
    return stops.matches("[0-9]");
  }

  /**
   * This method verifies whether equipment is a sting of whitespace separated 3 character plane
   * codes.
   *
   * @param equipment The string to be tested.
   * @return True if string matches expected format, false otherwise.
   */
  protected boolean isEquipmentValid(String equipment) {
    String[] equipArray = equipment.split(" ");
    for (String plane : equipArray) {
      if (!(plane.length() == 3 && plane.matches("[0-9A-Z]+"))) {
        return false;
      }
    }
    return true;
  }

  /**
   * This method checks for duplicates in data. If there are no duplicates, added route to data.
   *
   * @param newRoute Route to be added.
   */
  private void addRoute(Route newRoute) {
    boolean inData = false;
    int[] newCode = new int[2];
    newCode[0] = newRoute.getSourceAirportID();
    newCode[1] = newRoute.getDestinationAirportID();
    if (routeCodes.size() > newRoute.getAirlineID()) {
      ArrayList<int[]> searchList = routeCodes.get(newRoute.getAirlineID());
      for (int[] existingCode : searchList) {
        if (existingCode[0] == newCode[0] && existingCode[1] == newCode[1]) {
          inData = true;
          errorCounter(10);
          break;
        }
      }
    }
    if (!inData) {
      parserData.add(newRoute);
      addToRouteCodes(newRoute);
    }
  }
}
