package model.loader;

import model.data.Airport;
import model.data.Storage;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * This class tests airportParser.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirportParserTest {
  private AirportParser airportParser;

  /** This method sets up environment before each test case. */
  @Before
  public void setUp() {
    Loader loader = new Loader(new Storage());
    List<Airport> existingLines = new ArrayList<Airport>();
    try {
      ArrayList<String> lines =
          loader.openFile("../seng202_project/src/test/java/TestFiles/airportsTest.csv");
      airportParser = new AirportParser(lines, existingLines);
    } catch (FileNotFoundException e) {
    }
  }

  /** Test if the airportId is valid with a new airport id number that is not exists in storage. */
  @Test
  public void isAirportIdValidNoDupId() {
    assertTrue(airportParser.isIdValid("4"));
  }

  /** Test if method reject airport with exists id. */
  @Test
  public void isAirportIdValidDupId() {
    airportParser.parseLine(
        "4,\"Nadzab\",\"Nadzab\",\"Papua New Guinea\",\"LAE\",\"AYNZ\",-6.569828,146.726242,239,10,\"U\",\"Pacific/Port_Moresby\"");
    assertFalse(airportParser.isIdValid("4"));
  }

  /** Test if method rejects negative number as airport ID. */
  @Test
  public void isAirportIDValidNegativeTest() {
    assertFalse(airportParser.isIdValid("-4"));
  }

  /** Test if method rejects letter as airport ID */
  @Test
  public void isAirportIDValidLetterTest() {
    assertFalse(airportParser.isIdValid("a"));
  }
  /** Test if method accept valid airport name without space. */
  @Test
  public void isNameValidWithoutSpace() {
    assertTrue(airportParser.isNameValid("ab"));
  }

  /** Test if method accept valid airport name with space. */
  @Test
  public void isNameValidWithSpace() {
    assertTrue(airportParser.isNameValid("a b"));
  }

  /** Test if method accept valid airport city without space. */
  @Test
  public void isCityValidWithoutSpace() {
    assertTrue(airportParser.isCityValid("ab"));
  }

  /** Test if method accept valid airport city with space. */
  @Test
  public void isCityWithSpace() {
    assertTrue(airportParser.isCityValid("a b"));
  }

  /** Test if method accept valid airport city with space. */
  @Test
  public void isCityInvalid() {
    assertFalse(airportParser.isCityValid("a%b"));
  }

  /** Test if method accept valid country without space. */
  @Test
  public void isCountryValidWithoutSpace() {
    assertTrue(airportParser.isCountryValid("ab"));
  }

  /** Test if method reject invalid coutry name. */
  @Test
  public void isCountryValidInvalidName() {
    assertFalse(airportParser.isCountryValid("4a"));
  }

  /** Test if method accept valid country name with space. */
  @Test
  public void isCountryWithSpace() {
    assertTrue(airportParser.isCountryValid("a b"));
  }

  /** Test if method reject invalid country name with space. */
  @Test
  public void isCountryInvalid() {
    assertFalse(airportParser.isCountryValid("a%b"));
  }

  /** Test if method accept null as IATA code. */
  @Test
  public void isIATAValidNull() {
    assertTrue(airportParser.isIATAValid("null"));
  }

  /** Test if method accept IATA code that is unknown. */
  @Test
  public void isIATAValidUnknow() {
    assertTrue(airportParser.isIATAValid("unknown"));
  }

  /** Test if method reject unexpected formation of code. */
  @Test
  public void isIATAValidNotFormated() {
    assertFalse(airportParser.isIATAValid("abcd"));
  }

  /** Test if method reject IATA code with space. */
  @Test
  public void isIATAValidWithSpace() {
    assertFalse(airportParser.isIATAValid("a b"));
  }

  /** Test if method accept IATA code with correct fomation. */
  @Test
  public void isIATAValidValid() {
    assertTrue(airportParser.isIATAValid("ABC"));
  }

  /** Test if method reject IATA code that is null. */
  @Test
  public void isICAOValidNull() {
    assertTrue(airportParser.isICAOValid("null"));
  }

  /** Test if method accept ICAO code that is unknown */
  @Test
  public void isICAOValidUnknow() {
    assertTrue(airportParser.isICAOValid("unknown"));
  }

  /** Test if method reject ICAO code with incorrect fomation. */
  @Test
  public void isICAOValidNotFormated() {
    assertFalse(airportParser.isICAOValid("abc"));
  }

  /** Test if method reject invalid ICAO code with space. */
  @Test
  public void isICAOValidWithSpace() {
    assertFalse(airportParser.isICAOValid("a b"));
  }

  /** Test if method accpet ICAO code with space. */
  @Test
  public void isICAOValidValid() {
    assertTrue(airportParser.isICAOValid("ABCD"));
  }

  /** Test if method accpet correct latitude. */
  @Test
  public void isLatValidCorrect() {
    assertTrue(airportParser.isLatValid("-6.081689834590001"));
  }

  /** Test if method reject invalid latitude. */
  @Test
  public void isLatValidWrong() {
    assertFalse(airportParser.isLatValid("-6.081689834590001ab"));
  }

  /** Test if method accpet correct longitude. */
  @Test
  public void isLonValidCorrect() {
    assertTrue(airportParser.isLonValid("145.391998291"));
  }

  /** Test if method reject invalid longitude. */
  @Test
  public void isLonValidWrong() {
    assertFalse(airportParser.isLonValid("145.391998291ab"));
  }

  /** Test if method accpet correct altitude. */
  @Test
  public void isAltValidCorrect() {
    assertTrue(airportParser.isAltValid("145"));
  }

  /** Test if method reject invalid altitude. */
  @Test
  public void isAltValidWrong() {
    assertFalse(airportParser.isAltValid("145.391998291ab"));
  }

  /** Test if method accpet correct timezome. */
  @Test
  public void isTZValidCorrect() {
    assertTrue(airportParser.isTZValid("5.5"));
  }

  /** Test if method reject invalid timezome. */
  @Test
  public void isTZValidWrong1() {
    assertFalse(airportParser.isTZValid("5a"));
  }

  /** Test if method reject invalid timezome number. */
  @Test
  public void isTZValidWrong2() {
    assertFalse(airportParser.isTZValid("15"));
  }

  /** Test if method reject invalid timezome number. */
  @Test
  public void isTZValidWrong3() {
    assertFalse(airportParser.isTZValid("-13"));
  }

  /** Test if method accpet correct timezome. */
  @Test
  public void isTZValidCorrect2() {
    assertTrue(airportParser.isTZValid("-12"));
  }

  /** Test if method accpet correct timezome. */
  @Test
  public void isTZValidCorrect3() {
    assertTrue(airportParser.isTZValid("14"));
  }

  /** Test if method accpet correct DST. */
  @Test
  public void isDSTValidCorrect() {
    assertTrue(airportParser.isDSTValid("E"));
  }

  /** Test if method reject invalid DST. */
  @Test
  public void isDSTValidWrong1() {
    assertFalse(airportParser.isDSTValid("14"));
  }

  /** Test if method reject invalid DST. */
  @Test
  public void isDSTValidWrong2() {
    assertFalse(airportParser.isDSTValid("B"));
  }

  /** Test if method accpet correct databaseTimezone. */
  @Test
  public void isDBValidCorrect() {
    assertTrue(airportParser.isDBTZValid("a/b"));
  }

  /** Test if method rejcet incorrect databaseTimezone. */
  @Test
  public void isDBValidWrong2() {
    assertFalse(airportParser.isDBTZValid("a1"));
  }

  /**
   * Verify that the correct error message is produced when attempting to add a file where over 200
   * lines are wrong.
   */
  @Test
  public void errorMessageTest200WrongLines() throws FileNotFoundException {
    Loader loader = new Loader(new Storage());
    ArrayList<String> lines =
        loader.openFile("../seng202_project/src/test/java/TestFiles/routes.csv");
    try {
      AirportParser airportParser = new AirportParser(lines, new ArrayList<>());
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
        loader.openFile("../seng202_project/src/test/java/TestFiles/SearcherAirlinesTest.csv");
    try {
      AirportParser airportParser = new AirportParser(lines, new ArrayList<>());
      fail();
    } catch (RuntimeException e) {
      assertEquals(
          "File rejected: all lines contain errors.\nError [0] Wrong number of parameters: 50 occurrences\n",
          e.getMessage());
    }
  }

  /** Verify that the correct error code is produced when attempting to add a duplicate airport */
  @Test
  public void duplicateErrorMessageTest() throws FileNotFoundException {
    Loader loader = new Loader(new Storage());
    ArrayList<String> duplicateLines =
        loader.openFile("../seng202_project/src/test/java/TestFiles/duplicateAirportTest.csv");
    AirportParser duplicateParser = new AirportParser(duplicateLines, new ArrayList<>());
    assertEquals(
        "File uploaded with 1 invalid lines rejected.\nError [1] Duplicate airport ID: 1 occurrences\n",
        duplicateParser.getErrorMessage(true));
  }

  /**
   * Verify that the route is only added to the data once when attempting to add a duplicate airport
   */
  @Test
  public void duplicateNotAddedTest() throws FileNotFoundException {
    Loader loader = new Loader(new Storage());
    ArrayList<String> duplicateLines =
        loader.openFile("../seng202_project/src/test/java/TestFiles/duplicateAirportTest.csv");
    AirportParser duplicateParser = new AirportParser(duplicateLines, new ArrayList<>());
    assertEquals(1, duplicateParser.getData().size());
  }

  /**
   * Verfiy that expected error message is given when attempting to upload a single invalid line.
   */
  @Test
  public void singleInvalidUploadErrorMessageTest() {
    Storage storage = new Storage();
    ArrayList<String> invalidLines = new ArrayList<>();
    invalidLines.add(
        "2609,\"Great Plains Airlines\",\\N,\"\",\"GRP\",\"GREAT PLAINS\",\"United States\",\"N\"");
    try {
      new AirportParser(invalidLines, storage.getAirports());
      fail();
    } catch (RuntimeException e) {
      assertEquals(
          "Entry contains errors and was not uploaded.\nError [0] Wrong number of parameters: 1 occurrences\n",
          e.getMessage());
    }
  }

  /** Test that validator returns true for a valid line. */
  @Test
  public void validatorValidLineTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37",
              "650",
              "-5",
              "A",
              "America/Toronto"
            });
    assertTrue(outcome);
  }

  /** Test that validator returns false for a line with the wrong number of parameters. */
  @Test
  public void validatorInvalidNumParamsTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37",
              "650",
              "-5",
              "A",
              "America/Toronto",
              ""
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with an invalid airport ID. */
  @Test
  public void validatorInvalidAirportIDTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "-2",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37",
              "650",
              "-5",
              "A",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid airport name. */
  @Test
  public void validatorInvalidAirportNameTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "*Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37",
              "650",
              "-5",
              "A",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid city. */
  @Test
  public void validatorInvalidCityTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto?",
              "Canada",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37",
              "650",
              "-5",
              "A",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid country. */
  @Test
  public void validatorInvalidCountryTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada,",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37",
              "650",
              "-5",
              "A",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid iata. */
  @Test
  public void validatorInvalidIATATest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YK",
              "CYKZ",
              "43.862221",
              "-79.37",
              "650",
              "-5",
              "A",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid icao. */
  @Test
  public void validatorInvalidICAOTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZZ",
              "43.862221",
              "-79.37",
              "650",
              "-5",
              "A",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid latitude. */
  @Test
  public void validatorInvalidLatitudeTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZ",
              "a",
              "-79.37",
              "650",
              "-5",
              "A",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid longitude. */
  @Test
  public void validatorInvalidLongitudeTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37.11",
              "650",
              "-5",
              "A",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid altitude. */
  @Test
  public void validatorInvalidAltitudeTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37",
              "",
              "-5",
              "A",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid timezone. */
  @Test
  public void validatorInvalidTimezoneTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37",
              "650",
              "-55",
              "A",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid DST. */
  @Test
  public void validatorInvalidDSTTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37",
              "650",
              "-5",
              "AA",
              "America/Toronto"
            });
    assertFalse(outcome);
  }

  /** Test that validator returns false for a line with invalid database timezone. */
  @Test
  public void validatorInvalidDBSTTest() {
    boolean outcome =
        airportParser.validater(
            new String[] {
              "82",
              "Buttonville Muni",
              "Toronto",
              "Canada",
              "YKZ",
              "CYKZ",
              "43.862221",
              "-79.37",
              "650",
              "-5",
              "A",
              "1/Toronto"
            });
    assertFalse(outcome);
  }
}
