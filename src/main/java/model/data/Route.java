package model.data;

import javafx.scene.control.CheckBox;
import java.util.Arrays;

/**
 * The Route class for containing all data for one unique flight route.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class Route implements DataType {

  /** The name of the airline which flies this route. */
  private final String airlineName;

  /** The database ID of the airline which flies this route. */
  private final int airlineID;

  /** The airport this route starts at. */
  private final String sourceAirport;

  /** The database ID of the airport this route starts at. */
  private final int sourceAirportID;

  /** The airport this route finishes at. */
  private final String destinationAirport;

  /** The database ID of the airport this route finishes at. */
  private final int destinationAirportID;

  /** "Y" if this route is a codeshare, empty string if it is not. */
  private final String codeShare;

  /** The number of stops on this route. */
  private final int numOfStops;

  /** The planes used on this route. */
  private final String[] equipment;

  /** The first plane in the equipment array. */
  private final String firstEquipment;

  /** The amount of carbon emissions produced by this route. */
  private double emissions;

  /** The distance travelled via this route. */
  private double distance;

  /** The number of times this route has been added to history. */
  private int timesTaken = 0;

  /** A checkbox to tell whether this route has been selected. */
  private CheckBox select;

  /** The Route constructor. */
  public Route(
      String airlineName,
      int airlineID,
      String sourceAirport,
      int sourceAirportID,
      String destinationAirport,
      int destinationAirportID,
      String codeShare,
      int numOfStops,
      String[] equipment) {
    this.airlineName = airlineName;
    this.airlineID = airlineID;
    this.sourceAirport = sourceAirport;
    this.sourceAirportID = sourceAirportID;
    this.destinationAirport = destinationAirport;
    this.destinationAirportID = destinationAirportID;
    this.codeShare = codeShare;
    this.numOfStops = numOfStops;
    this.equipment = equipment;
    if (equipment.length > 0) {
      firstEquipment = equipment[0];
    } else {
      firstEquipment = "";
    }
  }

  /** This method initializes the routes checkbox to use with JavaFx. */
  public void initCheckBox() {
    this.select = new CheckBox();
  }

  public String getAirlineName() {
    return airlineName;
  }

  public int getAirlineID() {
    return airlineID;
  }

  public String getSourceAirport() {
    return sourceAirport;
  }

  public int getSourceAirportID() {
    return sourceAirportID;
  }

  public String getDestinationAirport() {
    return destinationAirport;
  }

  public int getDestinationAirportID() {
    return destinationAirportID;
  }

  public String getCodeShare() {
    return codeShare;
  }

  public int getNumOfStops() {
    return numOfStops;
  }

  public String[] getEquipment() {
    return equipment;
  }

  public String getFirstEquipment() {
    return firstEquipment;
  }

  public double getEmissions() {
    return emissions;
  }

  public String getEmissionsDisplayString() {
    return String.format("%.2f", emissions);
  }

  public void setEmissions(double emissions) {
    this.emissions = emissions;
  }

  public double getDistance() {
    return distance;
  }

  public String getDistanceDisplayString() {
    return String.format("%.2f", distance);
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public int getTimesTaken() {
    return timesTaken;
  }

  public void setTimesTaken(int timesTaken) {
    this.timesTaken = timesTaken;
  }

  /**
   * This method returns true if object has the same attributes as the Route from which the method
   * is called, false otherwise.
   *
   * @param o object to be compared to the route calling the method.
   * @return true if the two objects attributes are the same, false if there are any differences.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Route) {
      Route another = (Route) o;
      return (this.airlineName.equals(another.getAirlineName())
          && this.airlineID == another.getAirlineID()
          && this.sourceAirport.equals(another.getSourceAirport())
          && this.sourceAirportID == another.getSourceAirportID()
          && this.destinationAirport.equals(another.getDestinationAirport())
          && this.destinationAirportID == another.getDestinationAirportID()
          && this.codeShare.equals(another.getCodeShare())
          && this.numOfStops == another.getNumOfStops()
          && Arrays.equals(this.equipment, another.getEquipment()));
    } else {
      return false;
    }
  }

  /**
   * This method returns the routes selection checkbox.
   *
   * @return select.
   */
  public CheckBox getSelect() {
    return select;
  }

  /**
   * This method adds a selection checkbox to the route.
   *
   * @param select The selection checkbox to be added.
   */
  public void setSelect(CheckBox select) {
    this.select = select;
  }

  /**
   * This method returns a string representation of the route.
   *
   * @return A string representation of the route.
   */
  @Override
  public String toString() {
    return "Airline name: "
        + airlineName
        + "\n"
        + "Airline ID: "
        + airlineID
        + "\n"
        + "Source Airport: "
        + sourceAirport
        + "\n"
        + "Source Airport ID: "
        + sourceAirportID
        + "\n"
        + "Destination Airport: "
        + destinationAirport
        + "\n"
        + "Destination Airport ID: "
        + destinationAirportID
        + "\n"
        + "Code Share: "
        + codeShare
        + "\n"
        + "Number of Stops: "
        + numOfStops
        + "\n"
        + "Equipment: "
        + Arrays.toString(equipment)
        + "\n"
        + "First Equipment: "
        + firstEquipment
        + "\n"
        + "Carbon Emissions: "
        + emissions
        + "\n"
        + "Distance: "
        + distance
        + "\n"
        + "Times Taken: "
        + timesTaken
        + "\n";
  }
}
