package model.loader;

import model.data.Airport;
import model.data.DataType;

import java.util.ArrayList;
import java.util.List;

/**
 * The sub-class Parser for airport. AirportParser class receives a list of airport data, validates
 * each of the data line and create airport object. Then put the object into a hashSet for return
 * value.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirportParser extends Parser {
  /**
   * AirportParser Error code: 100: not enough parameters 101: airport id exists 102: invalid id
   * number 103: invalid airport name 104: invalid airport city 105: invalid airport country 106:
   * invalid airport IATA code 107: invalid airport ICAO code 108: invalid latitude 109: invalid
   * longitude 110: invalid altitude 111: invalid timezone 112: invalid DST 113: invalid database
   * timezone 114: invalid unknown error 115: number of failed insertions 116: invalid alias 117:
   * invalid callsign 118: invalid activeStatus
   */
  private final int airportID = 0;

  private final int name = 1;
  private final int city = 2;
  private final int country = 3;
  private final int IATA = 4;
  private final int ICAO = 5;
  private final int latitude = 6;
  private final int longitude = 7;
  private final int altitude = 8;
  private final int timezone = 9;
  private final int DST = 10;
  private final int dataBaseTimeZone = 11;

  /**
   * The constructor of AirportParser, it will start dataParse method as well.
   *
   * @param dataFile The list contains one line of datafile per element.
   */
  public AirportParser(ArrayList<String> dataFile, List<Airport> existingAirports) {
    super(dataFile, 16);
    parserData.addAll(existingAirports);
    dataParser();
  }

  /** This method initializes the error messages for each error code */
  @Override
  protected void initErrorLookup() {
    errorLookup[0] = "Wrong number of parameters";
    errorLookup[1] = "Duplicate airport ID";
    errorLookup[2] = "Invalid airport ID";
    errorLookup[3] = "Invalid airport name";
    errorLookup[4] = "Invalid airport city";
    errorLookup[5] = "Invalid airport country";
    errorLookup[6] = "Invalid IATA code";
    errorLookup[7] = "Invalid ICAO code";
    errorLookup[8] = "Invalid latitude";
    errorLookup[9] = "Invalid longitude";
    errorLookup[10] = "Invalid altitude";
    errorLookup[11] = "Invalid timezone";
    errorLookup[12] = "Invalid DST";
    errorLookup[13] = "Invalid database timezone";
    errorLookup[14] = "Unknown error";
    errorLookup[15] = "Failed insertion";
  }

  /**
   * This method is called when airportParser is initialized. Calls validate method to check each
   * line. If line is valid, creates airport object with attributes from line and adds route to
   * routes set.
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
   * This method is a data parser to convert airport data from list into airport objects and add to
   * HashSet. It will also call validater to verify each airport data.
   *
   * @param dataLine line from file to split into segments.
   */
  protected void parseLine(String dataLine) {
    String[] line = dataLine.replaceAll("\"", "").split(",");
    if (validater(line)) {
      try {
        Airport airport =
            new Airport(
                Integer.parseInt(line[airportID]),
                line[name],
                line[city],
                line[country],
                line[IATA],
                line[ICAO],
                Double.parseDouble(line[latitude]),
                Double.parseDouble(line[longitude]),
                Integer.parseInt(line[altitude]),
                Float.parseFloat(line[timezone]),
                line[DST],
                line[dataBaseTimeZone]);
        parserData.add(airport);
      } catch (Exception e) {
        errorCounter(14);
      }
    }
  }

  /**
   * This method Validates the data in one line is valid or not.
   *
   * @param line String list contains 11 data for airport attributes.
   * @return true if the data line is valid, false if the data line is not expected.
   */
  @Override
  protected boolean validater(String[] line) {
    if (line.length != 12) {
      errorCounter(0);
      return false;
    }

    if (!isIdValid(line[airportID])) {
      return false;
    }
    if (!isNameValid(line[name])) {
      return false;
    }

    if (!isCityValid(line[city])) {
      return false;
    }

    if (!isCountryValid(line[country])) {
      return false;
    }

    if (!isIATAValid(line[IATA])) {
      return false;
    }

    if (!isICAOValid(line[ICAO])) {
      return false;
    }

    if (!isLatValid(line[latitude])) {
      return false;
    }

    if (!isLonValid(line[longitude])) {
      return false;
    }

    if (!isAltValid(line[altitude])) {
      return false;
    }

    if (!isTZValid(line[timezone])) {
      return false;
    }

    if (!isDSTValid(line[DST])) {
      return false;
    }

    return isDBTZValid(line[dataBaseTimeZone]);
  }

  /**
   * This method checks if the id is valid (duplication and valid integer).
   *
   * @param id airport id as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isIdValid(String id) {
    for (DataType data : parserData) {
      try {
        Airport airport = (Airport) data;
        if (airport.getAirportID() == Integer.parseInt(id)) {
          errorCounter(1);
          return false;
        }
      } catch (Exception e) {
        errorCounter(2);
        return false;
      }
    }
    return Integer.parseInt(id) >= 0;
  }

  /**
   * This method checks if the name is valid.
   *
   * @param name airport name as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isNameValid(String name) {
    if (!name.matches("[\\p{L}0-9 .'()/-]+")) {
      errorCounter(3);
      return false;
    }
    return true;
  }

  /**
   * This method checks if the city is valid.
   *
   * @param city airport city as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isCityValid(String city) {
    if (!city.matches("[\\p{L}0-9 .'()/-]+")) {
      errorCounter(4);
      return false;
    }
    return true;
  }

  /**
   * This method checks if the country is valid.
   *
   * @param country airport country as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isCountryValid(String country) {
    // ISO 3166-1 codes not implemented
    if (!country.matches("[a-zA-Z .'()/-]+")) {
      errorCounter(5);
      return false;
    }
    return true;
  }

  /**
   * This method checks if the IATA is valid.
   *
   * @param IATA airport IATA as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isIATAValid(String IATA) {
    if (!IATA.equalsIgnoreCase("null")
        && !IATA.equalsIgnoreCase("unknown")
        && !IATA.equalsIgnoreCase("")) {
      if (!IATA.matches("[a-zA-Z0-9]+") || IATA.length() != 3) {
        errorCounter(6);
        return false;
      }
    }
    return true;
  }

  /**
   * This method checks if the ICAO is valid.
   *
   * @param ICAO airport ICAO as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isICAOValid(String ICAO) {
    if (!ICAO.equalsIgnoreCase("null")
        && !ICAO.equalsIgnoreCase("unknown")
        && !ICAO.equalsIgnoreCase("\\N")
        && !ICAO.equalsIgnoreCase("")) {
      if (!ICAO.matches("[a-zA-Z0-9]+") || ICAO.length() != 4) {
        errorCounter(7);
        return false;
      }
    }
    return true;
  }

  /**
   * This method checks if the latitude is valid.
   *
   * @param lat airport latitude as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isLatValid(String lat) {
    try {
      Float.parseFloat(lat);
      return true;
    } catch (Exception e) {
      errorCounter(8);
      return false;
    }
  }

  /**
   * This method checks if the longitude is valid.
   *
   * @param lon airport longitude as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isLonValid(String lon) {
    try {
      Float.parseFloat(lon);
      return true;
    } catch (Exception e) {
      errorCounter(9);
      return false;
    }
  }

  /**
   * This method checks if the altitude is valid.
   *
   * @param alt airport altitude as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isAltValid(String alt) {
    try {
      Integer.parseInt(alt);
      return true;
    } catch (Exception e) {
      errorCounter(10);
      return false;
    }
  }

  /**
   * This method checks if the timeZone is valid.
   *
   * @param timeZone airport timeZone as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isTZValid(String timeZone) {
    try {
      return -12 <= Float.parseFloat(timeZone) && Float.parseFloat(timeZone) <= 14;
    } catch (Exception e) {
      errorCounter(11);
      return false;
    }
  }

  /**
   * This method checks if the DST is valid.
   *
   * @param DST airport DST as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isDSTValid(String DST) {
    if (DST.matches("[EASOZNU]+") && DST.length() == 1) {
      return true;
    } else {
      errorCounter(12);
      return false;
    }
  }

  /**
   * This method checks if the DBTZ is valid.
   *
   * @param DBTZ airport DBTZ as a string.
   * @return true if valid, false if invalid.
   */
  protected boolean isDBTZValid(String DBTZ) {
    if (!DBTZ.equalsIgnoreCase("\\N")) {
      if (!DBTZ.matches("[a-zA-Z-/a-zA-Z_]+")) {
        errorCounter(13);
        return false;
      }
    }
    return true;
  }
}
