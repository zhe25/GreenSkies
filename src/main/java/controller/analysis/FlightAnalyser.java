package controller.analysis;

import model.data.Airport;
import model.data.Route;
import model.data.Storage;
import java.util.ArrayList;
import java.util.List;

/**
 * The FlightAnalyser class which calculate two paths' carbon emission, total distance of the path,
 * and comparison of two paths' distance and emission.
 *
 * @version 1.2
 * @since 04/10/2020
 */
public class FlightAnalyser {
  /** radius of the earth */
  private final double radius = 6371e3;
  /** the amount of fuel have been used */
  private double FuelUsed;
  /** the number of seats have been occupancy */
  private final int seatsOccupancy = 333;
  /** the amount of Co2 produce by one gram of fuel in grams */
  private final double Co2OfOneGramFuel = 3.15;
  /** cruising speed of the aircraft in km per hour */
  private final int CruisingSpeed = 910;
  /** array list path1 to store two airport code */
  private ArrayList<String> path1 = new ArrayList<>();
  /** array list path2 to store two airport code */
  private ArrayList<String> path2 = new ArrayList<>();
  /** array list to store all airport code which are available */
  private List<Airport> airports = new ArrayList<>();
  /** array list to store airport code */
  private ArrayList<ArrayList<Double>> path1Coords = new ArrayList<>();
  /** array list to store airport code */
  private ArrayList<ArrayList<Double>> path2Coords = new ArrayList<>();
  /** The distance between one airport to another airport */
  private double totalDistancePath1 = 0;
  /** The distance between one airport to another airport */
  private double totalDistancePath2 = 0;
  /** The emission the aircraft produce when flying one airport to another airport */
  private double totalEmissionPath1 = 0;
  /** The emission the aircraft produce when flying one airport to another airport */
  private double totalEmissionPath2 = 0;

  /**
   * The constructor of FlightAnalyser which starts processing and calculation.
   *
   * @param route1 An arraylist contains IATA or ICAO code for each airport which the flight may
   *     pass for path1.
   * @param route2 An arraylist contains IATA or ICAO code for each airport which the flight may
   *     pass for path2.
   * @param storage Storage contains all information about airports, routes, and airlines.
   */
  public FlightAnalyser(Route route1, Route route2, Storage storage) {
    this.path1.add(route1.getSourceAirport());
    this.path1.add(route1.getDestinationAirport());
    this.path2.add(route2.getSourceAirport());
    this.path2.add(route2.getDestinationAirport());

    this.airports = storage.getAirports();
    processsPath();
    calculateTotalDistance();
    calculatePathsEmission();
  }

  /**
   * The constructor of FlightAnalyser which starts processing and calculation.
   *
   * @param route1 An arraylist contains IATA or ICAO code for each airport which the flight may
   *     pass for a path.
   * @param storage Storage contains all information about airports, routes, and airlines.
   */
  public FlightAnalyser(Route route1, Storage storage) {
    this.airports = storage.getAirports();
    this.path1.add(route1.getSourceAirport());
    this.path1.add(route1.getDestinationAirport());
    processsPathSingle();
    calculateTotalDistanceSingle();
    calculatePathsEmissionSingle();
  }

  /** This method gets the longitude and the latitude from each airport and put them into a list. */
  private void processsPathSingle() {
    for (String airportCode : path1) {
      for (Airport airport : airports) {
        if (airport.getIATA().equals(airportCode) || airport.getICAO().equals(airportCode)) {
          ArrayList<Double> coord = new ArrayList<>();
          coord.add(airport.getLatitude());
          coord.add(airport.getLongitude());
          path1Coords.add(coord);
        }
      }
    }
  }

  /** This method gets the coordinate and then calculate the single airline distance. */
  private void calculateTotalDistanceSingle() {
    for (int i = 0; i < path1Coords.size() - 1; i++) {
      double airport1lat = path1Coords.get(i).get(0); // lat
      double airport1lon = path1Coords.get(i).get(1);
      double airport2lat = path1Coords.get(i + 1).get(0); // lat
      double airport2lon = path1Coords.get(i + 1).get(1);
      totalDistancePath1 += calculateDistance(airport1lat, airport1lon, airport2lat, airport2lon);
    }
  }

  /**
   * This method processes two arraylist path1 and path2, loop through the airports data and put
   * coordinates of each airport which is contained in the path into arraylist path1coords and
   * path2coords.
   */
  private void processsPath() {
    for (String airportCode : path1) {
      for (Airport airport : airports) {
        if (airport.getIATA().equals(airportCode) || airport.getICAO().equals(airportCode)) {
          ArrayList<Double> coord = new ArrayList<>();
          coord.add(airport.getLatitude());
          coord.add(airport.getLongitude());
          path1Coords.add(coord);
        }
      }
    }
    for (String airportCode : path2) {
      for (Airport airport : airports) {
        if (airport.getIATA().equals(airportCode) || airport.getICAO().equals(airportCode)) {
          ArrayList<Double> coord = new ArrayList<>();
          coord.add(airport.getLatitude());
          coord.add(airport.getLongitude());
          path2Coords.add(coord);
        }
      }
    }
  }

  /** This method calculates the single path emission by using the distance has been calculated */
  private void calculatePathsEmissionSingle() {
    this.totalEmissionPath1 = calculateCarbonEmission(totalDistancePath1);
  }

  /** This method calculates totalDistance of path1 and path2. */
  private void calculateTotalDistance() {
    for (int i = 0; i < path1Coords.size() - 1; i++) {
      double airport1lat = path1Coords.get(i).get(0); // lat
      double airport1lon = path1Coords.get(i).get(1);
      double airport2lat = path1Coords.get(i + 1).get(0); // lat
      double airport2lon = path1Coords.get(i + 1).get(1);
      totalDistancePath1 += calculateDistance(airport1lat, airport1lon, airport2lat, airport2lon);
    }

    for (int i = 0; i < path2Coords.size() - 1; i++) {
      double airport1lat = path2Coords.get(i).get(0); // lat
      double airport1lon = path2Coords.get(i).get(1);
      double airport2lat = path2Coords.get(i + 1).get(0); // lat
      double airport2lon = path2Coords.get(i + 1).get(1);
      totalDistancePath2 += calculateDistance(airport1lat, airport1lon, airport2lat, airport2lon);
    }
  }

  /**
   * This method calculates distance between two airports.
   *
   * @param Lati1 Latitude of the airport in path1.
   * @param Long1 longitude of the airport in path1.
   * @param Lati2 Latitude of the airport in path1.
   * @param Long2 longitude of the airport in path1.
   * @return returns distance between two airports in kilometres.
   */
  public double calculateDistance(double Lati1, double Long1, double Lati2, double Long2) {
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

  /** This method starts the calculation of two paths' total carbon emission. */
  private void calculatePathsEmission() {
    this.totalEmissionPath1 = calculateCarbonEmission(totalDistancePath1);
    this.totalEmissionPath2 = calculateCarbonEmission(totalDistancePath2);
  }

  /**
   * This method calculates the carbon emission between two airports.
   *
   * @param distance Distance between two airports.
   * @return carbon emission in kilograms.
   */
  private double calculateCarbonEmission(double distance) {
    FuelUsed = distance * 12 / 1250; // fuel in tonnes

    double FuelPerPassenger =
        (FuelUsed / (distance * seatsOccupancy)) * 1000000; // fuel use per passenger per km

    double Co2PerPassengerPerKm =
        FuelPerPassenger * Co2OfOneGramFuel; // co2 emissions per passenger km in gram

    double Co2Hour =
        (Co2PerPassengerPerKm * CruisingSpeed) / 1000; // how much Co2 generate per hour in kg

    double flytime = distance / CruisingSpeed; // in hour

    double finalCo2 = flytime * Co2Hour;

    return finalCo2; // in kg
  }

  /**
   * This method compares the distance between two paths by getting absolute number of their
   * difference.
   *
   * @return difference of two distances.
   */
  public double compareDistance() {
    return Math.abs(totalDistancePath1 - totalDistancePath2);
  }

  /**
   * This method compares the emissions between two paths by getting absolute number of their
   * difference.
   *
   * @return difference of two emissions.
   */
  public double compareEmission() {
    return Math.abs(totalEmissionPath1 - totalEmissionPath2);
  }

  /**
   * Get path1 emission
   *
   * @return emission
   */
  public double getPath1Emission() {
    return totalEmissionPath1;
  }
  /**
   * Get path2 emission
   *
   * @return emission
   */
  public double getPath2Emission() {
    return totalEmissionPath2;
  }
  /**
   * Get path1 distance
   *
   * @return distance
   */
  public double getTotalDistancePath1() {
    return totalDistancePath1;
  }
  /**
   * Get path2 distance
   *
   * @return distance
   */
  public double getTotalDistancePath2() {
    return totalDistancePath2;
  }
}
