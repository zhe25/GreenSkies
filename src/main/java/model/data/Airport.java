package model.data;

import javafx.scene.control.CheckBox;

/**
 * The Airport class which contains all data for one unique airport.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class Airport implements DataType {

  /** Unique OpenFlights identifier for this airport. */
  private final int airportID;

  /** Name of airport. May or may not contain the City name. */
  private final String name;

  /** Main city served by airport. May be spelled differently from Name. */
  private final String city;

  /** Country or territory where airport is located. */
  private final String country;

  /** 3-letter IATA code. Null if not assigned/unknown. */
  private final String IATA;

  /** 4-letter ICAO code */
  private final String ICAO;

  /** Decimal degrees, usually to six significant digits. Negative is South, positive is North. */
  private final double latitude;

  /** Decimal degrees, usually to six significant digits. Negative is West, positive is East. */
  private final double longitude;

  /** Unit of altitudes is in feet */
  private final int altitude;

  /** Hours offset from UTC. Fractional hours are expressed as decimals, eg. India is 5.5. */
  private final double timezone;

  /**
   * Daylight savings time. One of E (Europe), A (US/Canada), S (South America), O (Australia), Z
   * (New Zealand), N (None) or U (Unknown).
   */
  private final String DST;

  /** Timezone in "tz" (Olson) format, eg. "America/Los_Angeles". */
  private final String dataBaseTimeZone;

  /** A checkbox to tell whether this route has been selected. */
  private CheckBox select;

  /** The Airport constructor. */
  public Airport(
      int airportID,
      String name,
      String city,
      String country,
      String IATA,
      String ICAO,
      double latitude,
      double longitude,
      int altitude,
      float timezone,
      String DST,
      String dataBaseTimeZone) {
    this.airportID = airportID;
    this.name = name;
    this.city = city;
    this.country = country;
    this.IATA = IATA;
    this.ICAO = ICAO;
    this.latitude = latitude;
    this.longitude = longitude;
    this.altitude = altitude;
    this.timezone = timezone;
    this.DST = DST;
    this.dataBaseTimeZone = dataBaseTimeZone;
  }

  /** This method initializes the routes checkbox to use with JavaFx. */
  public void initCheckBox() {
    this.select = new CheckBox();
  }

  public int getAirportID() {
    return airportID;
  }

  public String getName() {
    return name;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public String getIATA() {
    return IATA;
  }

  public String getICAO() {
    return ICAO;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public int getAltitude() {
    return altitude;
  }

  public double getTimezone() {
    return timezone;
  }

  public String getDST() {
    return DST;
  }

  public String getDataBaseTimeZone() {
    return dataBaseTimeZone;
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
   * This method returns true if object has the same attributes as the airport from which the method
   * is called, false otherwise.
   *
   * @param o object to be compared to the airport calling the method.
   * @return true if the two objects attributes are the same, false if there are any differences.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Airport) {
      Airport another = (Airport) o;
      return (this.airportID == another.getAirportID())
          && this.name.equals(another.getName())
          && this.city.equals(another.getCity())
          && this.country.equals(another.getCountry())
          && this.IATA.equals(another.getIATA())
          && this.ICAO.equals(another.getICAO())
          && Math.abs(this.latitude - another.getLatitude()) < 0.0000001
          && Math.abs(this.longitude - another.getLongitude()) < 0.0000001
          && this.altitude == another.getAltitude()
          && Math.abs(this.timezone - another.getTimezone()) < 0.0000001
          && this.DST.equals(another.getDST())
          && this.dataBaseTimeZone.equals(another.getDataBaseTimeZone());
    } else {
      return false;
    }
  }
}
