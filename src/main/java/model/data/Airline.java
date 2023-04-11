package model.data;

import javafx.scene.control.CheckBox;

/**
 * The Airline class which contains all data for one unique airline.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class Airline implements DataType {
  /** The ID of an airline (unique). */
  private final int airlineID;

  /** The name of the airline. */
  private final String name;

  /** The alias of an airline. */
  private final String alias;

  /** The IATA code of an airline. */
  private final String IATA;

  /** The ICAO code of an airline. */
  private final String ICAO;

  /** The callsign of an airline. */
  private final String callsign;

  /** The country or territory where airport is located. */
  private final String country;

  /** "Y" if the airline is or has until recently been operational, "N" if it is defunct. */
  private final Boolean activeStatus;

  /** A checkbox to tell whether this airline has been selected. */
  private CheckBox select;

  /** The Airline constructor. */
  public Airline(
      int airlineID,
      String name,
      String alias,
      String IATA,
      String ICAO,
      String callsign,
      String country,
      Boolean activeStatus) {
    this.airlineID = airlineID;
    this.name = name;
    this.alias = alias;
    this.IATA = IATA;
    this.ICAO = ICAO;
    this.callsign = callsign;
    this.country = country;
    this.activeStatus = activeStatus;
  }

  /** This method initializes the routes checkbox to use with JavaFx. */
  public void initCheckBox() {
    this.select = new CheckBox();
  }

  public int getAirlineID() {
    return airlineID;
  }

  public String getName() {
    return name;
  }

  public String getAirlineAlias() {
    return alias;
  }

  public String getIATA() {
    return IATA;
  }

  public String getICAO() {
    return ICAO;
  }

  public String getCallsign() {
    return callsign;
  }

  public String getCountry() {
    return country;
  }

  public boolean getActiveStatus() {
    return activeStatus;
  }

  /**
   * This method returns the airline selection checkbox.
   *
   * @return select.
   */
  public CheckBox getSelect() {
    return select;
  }

  /**
   * This method adds a selection checkbox to the airline.
   *
   * @param select The selection checkbox to be added.
   */
  public void setSelect(CheckBox select) {
    this.select = select;
  }

  /**
   * Returns true if all another is instance of airline and has all the same attributes as this
   * airline, false otherwise..
   *
   * @param o any object to check equality against.
   * @return true if objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Airline) {
      Airline another = (Airline) o;
      return (this.airlineID == another.getAirlineID())
          && this.name.equals(another.getName())
          && this.alias.equals(another.getAirlineAlias())
          && this.IATA.equals(another.getIATA())
          && this.ICAO.equals(another.getICAO())
          && this.callsign.equals(another.getCallsign())
          && this.country.equals(another.getCountry())
          && this.activeStatus == another.getActiveStatus();
    } else {
      return false;
    }
  }
}
