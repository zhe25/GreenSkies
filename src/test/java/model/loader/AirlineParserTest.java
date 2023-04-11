package model.loader;

import model.data.Airline;
import model.data.DataType;
import model.data.Storage;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Test cases for AirlineParser.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirlineParserTest {
  private AirlineParser airlineParser;
  Loader loader = new Loader(new Storage());
  List<Airline> existingLines = new ArrayList<>();

  @Before
  public void setUp() {

    try {
      ArrayList<String> lines =
          loader.openFile("../seng202_project/src/test/java/TestFiles/airlinesTest.csv");

      airlineParser = new AirlineParser(lines, existingLines);

    } catch (FileNotFoundException ignored) {
    }
  }

  /** Test if method reject file that have more than 200 lines of errors. */
  @Test
  public void isDataParserValid200Error() {
    try {
      ArrayList<String> lines_200_Error =
          loader.openFile("../seng202_project/src/test/java/TestFiles/airports.csv");
      new AirlineParser(lines_200_Error, new ArrayList<>());
      fail();
    } catch (FileNotFoundException ignored) {
    } catch (RuntimeException e) {
      // test passed
    }
  }

  /** Test if method will add existing airline to data upon initialize. */
  @Test
  public void isAirlineParserValidAddExistingAirline() {
    try {
      ArrayList<String> lines =
          loader.openFile("../seng202_project/src/test/java/TestFiles/airlinesTest.csv");
      List<Airline> temp = new ArrayList<>();
      temp.add((Airline) airlineParser.parserData.get(1));
      airlineParser = new AirlineParser(lines, temp);

    } catch (FileNotFoundException ignored) {
    }
  }

  /** Test if method reject new airline that have same airlineID. */
  @Test
  public void isAddAirlineValidDupAirlineID() {
    Airline tempAirline =
        new Airline(
            3,
            "Airline Name",
            "Airline Alias",
            "IA",
            "ICA",
            "Airline Callsign",
            "Airline Country",
            true);
    airlineParser.addAirLine(3, tempAirline);
    assertNotSame(tempAirline, airlineParser.parserData.get(3));
  }

  /** Test if method reject duplicate airline. */
  @Test
  public void isAddAirlineValidDupAirline() {
    Airline tempAirline =
        new Airline(3, "1Time Airline", "\\N", "1T", "RNX", "NEXTIME", "South Africa", true);
    airlineParser.addAirLine(3, tempAirline);
    assertNotSame(tempAirline, airlineParser.parserData.get(3));
  }

  /** Test if method accept new airline id number that is not exist in the storage */
  @Test
  public void isAirlineIdValidNoDupId() {
    assertTrue(airlineParser.isIdValid("4"));
  }

  /** Test if method reject duplicate airlineID. */
  @Test
  public void isAirlineIdValidDupId() {
    assertFalse(airlineParser.isIdValid("3"));
  }

  /** Test if method reject airlineID that is not numerals */
  @Test
  public void isAirlineIdInvalid() {
    assertFalse(airlineParser.isIdValid("One"));
  }

  /** Test if method accept valid airline name without space. */
  @Test
  public void isNameValidWithoutSpace() {
    assertTrue(airlineParser.isNameValid("ab"));
  }

  /** Test if method accept valid airline name with space. */
  @Test
  public void isNameValidWithSpace() {
    assertTrue(airlineParser.isNameValid("a b"));
  }

  /** Test if method accept valid airline name started with numerals. */
  @Test
  public void isNameValidStartedWithNumerals() {
    assertTrue(airlineParser.isNameValid("4b"));
  }

  /** Test if method accept valid alias name. */
  @Test
  public void isAliasValidWithoutSpace() {
    assertTrue(airlineParser.isAliasValid("abcdefghi"));
  }

  /** Test if method reject invalid alias that have special character. */
  @Test
  public void isAliasInvalid() {
    assertFalse(airlineParser.isAliasValid("a%b"));
  }

  /** Test if method reject unexpected formation of IATA. */
  @Test
  public void isIATAValidUnknow() {
    assertFalse(airlineParser.isIATAValid("unknown"));
  }

  /** Test if method reject IATA code that have more than 2 character. */
  @Test
  public void isIATAValidNotFormated() {
    assertFalse(airlineParser.isIATAValid("abcd"));
  }

  /** Test if method reject IATA code with space. */
  @Test
  public void isIATAValidWithSpace() {
    assertFalse(airlineParser.isIATAValid("a b"));
  }

  /** Test if method accept IATA code with correct fomation. */
  @Test
  public void isIATAValidValid() {
    assertTrue(airlineParser.isIATAValid("AB"));
  }

  /** Test if method accept IATA code that is null. */
  @Test
  public void isICAOValidNull() {
    assertTrue(airlineParser.isICAOValid("\\N"));
  }

  /** Test if method accept ICAO code that is unknown */
  @Test
  public void isICAOValidUnknow() {
    assertTrue(airlineParser.isICAOValid("\\N"));
  }

  /** Test if method reject ICAO code with incorrect formation. */
  @Test
  public void isICAOValidNotFormated() {
    assertFalse(airlineParser.isICAOValid("abc"));
  }

  /** Test if method reject invalid ICAO code with space. */
  @Test
  public void isICAOValidWithSpace() {
    assertFalse(airlineParser.isICAOValid("a b"));
  }

  /** Test if method accept ICAO code that have 3 aphabetic characters. */
  @Test
  public void isICAOValidValid() {
    assertTrue(airlineParser.isICAOValid("ASS"));
  }

  /** Test if method accept Callsign that have 2 aphabetic characters. */
  @Test
  public void isCallsignValid() {
    assertTrue(airlineParser.isCallSignValid("AN"));
  }

  /** Test if method reject invalid Callsign that contain numeral characters. */
  @Test
  public void isCallsignValidStartedWithNumerals() {
    assertFalse(airlineParser.isCallSignValid("a1b"));
  }

  /** Test if method accept valid country without space. */
  @Test
  public void isCountryValidWithoutSpace() {
    assertTrue(airlineParser.isCountryValid("ab"));
  }

  /** Test if method reject invalid coutry name. */
  @Test
  public void isCountryValidInvalidName() {
    assertFalse(airlineParser.isCountryValid("4a"));
  }

  /** Test if method accept valid country name with space. */
  @Test
  public void isCountryWithSpace() {
    assertTrue(airlineParser.isCountryValid("a b"));
  }

  /** Test if method reject invalid country name with space. */
  @Test
  public void isCountryInvalid() {
    assertFalse(airlineParser.isCountryValid("a%b"));
  }

  /** Test if method accept airline with activeStatus field "Y" or "N". */
  @Test
  public void isActivaStatusValid() {
    assertTrue(airlineParser.isActiveStatusValid("Y"));
    assertTrue(airlineParser.isActiveStatusValid("N"));
  }

  /** Test if method reject airline with activeStatus field not "Y" and "N". */
  @Test
  public void isActivaStatusInvalid() {
    assertFalse(airlineParser.isActiveStatusValid("Yes"));
    assertFalse(airlineParser.isActiveStatusValid("No"));
  }

  /** Test if method would check all parameter if the previous parameter failed the check. */
  @Test
  public void isValidaterValidAllInvalid() {
    Airline testAirline =
        new Airline(2, "N@me", "@LI@S", "NotIATA", "NotICAO", "1Callsign", "1Country", true);
    airlineParser.addAirLine(1, testAirline);
    //    assertNotSame();
  }

  /** Test if method reject line with wrong number of parameters. */
  @Test
  public void isValidaterValidRedundantPara() {
    String[] invalidValider = {
      "-1", "N@me", "@LI@S", "NotIATA", "NotICAO", "1Callsign", "1Country", "Yes", "Redundant"
    };
    assertFalse(airlineParser.validater(invalidValider));
  }

  /**
   * Verify that the correct error message is produced when attempting to add a file where over 200
   * lines are wrong.
   */
  @Test
  public void errorMessageTest200WrongLines() throws FileNotFoundException {
    Loader loader = new Loader(new Storage());
    ArrayList<String> lines =
        loader.openFile("../seng202_project/src/test/java/TestFiles/airports.csv");
    try {
      AirlineParser airlineParser = new AirlineParser(lines, new ArrayList<>());
      fail();
    } catch (RuntimeException e) {
      assertEquals(
          "File rejected: more than 200 lines contain errors.\nError [0] Wrong number of parameters: 201 occurrences\n",
          e.getMessage());
    }
  }

  /**
   * Verify that the correct error message is produced when attempting to add a file all lines
   * contain errors but the file is less than 200 lines.
   */
  @Test
  public void errorMessageTestAllWrongLines() throws FileNotFoundException {
    Loader loader = new Loader(new Storage());
    ArrayList<String> lines =
        loader.openFile("../seng202_project/src/test/java/TestFiles/SearcherRoutesTest.csv");
    try {
      AirlineParser airlineParser = new AirlineParser(lines, new ArrayList<>());
      fail();
    } catch (RuntimeException e) {
      assertEquals(
          "File rejected: all lines contain errors.\nError [0] Wrong number of parameters: 50 occurrences\n",
          e.getMessage());
    }
  }

  /** Verify that the correct error code is produced when attempting to add a duplicate airline */
  @Test
  public void duplicateErrorMessageTest() throws FileNotFoundException {
    Loader loader = new Loader(new Storage());
    ArrayList<String> duplicateLines =
        loader.openFile("../seng202_project/src/test/java/TestFiles/duplicateAirlinesTest.csv");
    AirlineParser airlineParser = new AirlineParser(duplicateLines, new ArrayList<>());
    assertEquals(
        "File uploaded with 1 invalid lines rejected.\nError [1] Duplicate airline: 1 occurrences\n",
        airlineParser.getErrorMessage(true));
  }

  /**
   * Verify that the airline is only added to the data once when attempting to add a duplicate
   * airline
   */
  @Test
  public void duplicateNotAddedTest() throws FileNotFoundException {
    Loader loader = new Loader(new Storage());
    ArrayList<String> duplicateLines =
        loader.openFile("../seng202_project/src/test/java/TestFiles/duplicateAirlinesTest.csv");
    AirlineParser airlineParser = new AirlineParser(duplicateLines, new ArrayList<>());
    int numAirlines = 0;
    for (DataType dataType : airlineParser.getData()) {
      if (dataType != null) {
        numAirlines++;
      }
    }
    ;
    assertEquals(1, numAirlines);
  }
}
