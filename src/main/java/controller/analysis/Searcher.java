package controller.analysis;

import model.data.Airline;
import model.data.Airport;
import model.data.Route;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for searching data for entry which matches specific search term.
 *
 * @since 04/10/2020
 * @version 1.0
 */
public class Searcher {

  /**
   * This method searches airport data for entries that match the given search term.
   *
   * @param searchTerm A string entered by the user for something they want information on.
   * @param type The type of attribute the user.
   * @param airports A list of airports to be searched through.
   * @return An arrayList of the airports that match the search term.
   */
  public static ArrayList<Airport> searchAirports(
      String searchTerm, String type, List<Airport> airports) {
    ArrayList<Airport> matches = new ArrayList<Airport>();
    for (Airport entry : airports) {
      String attribute = "";
      switch (type) {
        case "Name":
          attribute = entry.getName();
          break;
        case "Country":
          attribute = entry.getCountry();
          break;
        case "IATA":
          attribute = entry.getIATA();
          break;
        case "ICAO":
          attribute = entry.getICAO();
          break;
        case "City":
          attribute = entry.getCity();
          break;
        default:
          throw new IllegalArgumentException(
              "Search type must be one of: Name, Country, City, IATA, ICAO.");
      }
      if (attribute.toLowerCase().equals(searchTerm.toLowerCase())) {
        matches.add(entry);
      }
    }
    if (matches.isEmpty()) {
      throw new RuntimeException("No entries match your search term.");
    }
    return matches;
  }

  /**
   * This method searches airline data for entries that match the given search term.
   *
   * @param searchTerm A string entered by the user for something they want information on.
   * @param type The type of attribute the user.
   * @param airlines A list of airlines to be searched through.
   * @return An arrayList of the airlines that match the search term.
   */
  public static ArrayList<Airline> searchAirlines(
      String searchTerm, String type, List<Airline> airlines) {
    ArrayList<Airline> matches = new ArrayList<Airline>();
    for (Airline entry : airlines) {
      if (entry != null) {
        String attribute = "";
        switch (type) {
          case "Name":
            attribute = entry.getName();
            break;
          case "Country":
            attribute = entry.getCountry();
            break;
          case "IATA":
            attribute = entry.getIATA();
            break;
          case "ICAO":
            attribute = entry.getICAO();
            break;
          case "Active status":
            attribute = Boolean.toString(entry.getActiveStatus());
            break;
          default:
            throw new IllegalArgumentException(
                "Search type must be one of: Name, Country, IATA, ICAO, Active status.");
        }
        if (attribute.toLowerCase().equals(searchTerm.toLowerCase())) {
          matches.add(entry);
        }
      }
    }
    if (matches.isEmpty()) {
      throw new RuntimeException("No entries match your search term.");
    }
    return matches;
  }

  /**
   * This method searches route data for entries that match the given search term.
   *
   * @param searchTerm A string entered by the user for something they want information on.
   * @param type The type of attribute the user. Must by one of "Airline", "Source", "Destination".
   * @param routes A list of routes to be searched through.
   * @return An arrayList of the routes that match the search term.
   */
  public static ArrayList<Route> searchRoutes(String searchTerm, String type, List<Route> routes) {
    ArrayList<Route> matches = new ArrayList<Route>();
    for (Route entry : routes) {
      String attribute = "";
      switch (type) {
        case "Airline":
          attribute = entry.getAirlineName();
          break;
        case "Source":
          attribute = entry.getSourceAirport();
          break;
        case "Destination":
          attribute = entry.getDestinationAirport();
          break;
        default:
          throw new IllegalArgumentException(
              "Search type must be one of: Airline, Source, Destination.");
      }
      if (attribute.toLowerCase().equals(searchTerm.toLowerCase())) {
        matches.add(entry);
      }
    }
    if (matches.isEmpty()) {
      throw new RuntimeException("No entries match your search term.");
    }
    return matches;
  }
}
