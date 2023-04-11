package model.loader;

import model.data.Airline;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Test cases for Parser superclass.
 *
 * @author Ella Johnson
 * @version 1.0
 * @since 04/10/2020
 */
public class ParserTest {

  private ArrayList<String> testLines;
  private AirlineParser airlineParser;

  @Before
  public void setUp() {
    testLines = new ArrayList<>();
    testLines.add("1729,\"Changan Airlines\",\\N,\"2Z\",\"CGN\",\"CHANGAN\",\"China\",\"N\"");
    testLines.add("1667,\"Caribintair\",\\N,\"\",\"CRT\",\"CARIBINTAIR\",\"Haiti\",\"N\"");
    testLines.add("19806,\"All Africa\",\"All Africa\",\"9A\",\"99F\",\"\",\"South Africa\",\"Y\"");
    List<Airline> existingLines = new ArrayList<Airline>();
    airlineParser = new AirlineParser(testLines, existingLines);
  }

  /**
   * Verify that erorCollectionInitializer does not change errorCollection when called with an
   * integer less than zero.
   */
  @Test
  public void errorCollectionInitializerNegativeTest() {
    airlineParser.errorCollection = new HashMap<>();
    airlineParser.errorCollectionInitializer(-3);
    assertTrue(airlineParser.errorCollection.isEmpty());
  }

  /**
   * Verify that errorCollectionInitializer does not change errorCollection when called with a zero
   */
  @Test
  public void errorCollectionIntializerZeroTest() {
    airlineParser.errorCollection = new HashMap<>();
    airlineParser.errorCollectionInitializer(0);
    assertTrue(airlineParser.errorCollection.isEmpty());
  }

  /**
   * Verify that when errorCollectionInitializer is called with a positive number its keys are all
   * the numbers up to that number
   */
  @Test
  public void errorCollectionInitializerPositiveKeysTest() {
    airlineParser.errorCollection = new HashMap<>();
    airlineParser.errorCollectionInitializer(6);
    Integer[] expectedKeys = {0, 1, 2, 3, 4, 5};
    Integer[] actualKeys = new Integer[6];
    int i = 0;
    for (Integer key : airlineParser.errorCollection.keySet()) {
      actualKeys[i] = key;
      i++;
    }
    Arrays.sort(actualKeys);
    assertArrayEquals(expectedKeys, actualKeys);
  }

  /**
   * Verify that when errorCollectionInitializer is called with a positive number its items are all
   * zero
   */
  @Test
  public void errorCollectionInitializerPositiveItemsTest() {
    airlineParser.errorCollection = new HashMap<>();
    airlineParser.errorCollectionInitializer(6);
    Integer[] expectedItems = {0, 0, 0, 0, 0, 0};
    Integer[] actualItems = new Integer[6];
    for (int i = 0; i < 6; i++) {
      actualItems[i] = airlineParser.errorCollection.get(i);
    }
    assertArrayEquals(expectedItems, actualItems);
  }

  /**
   * Verify that when errorCounter is called with an invalid number for the key, the values in
   * errorCollection do not change.
   */
  @Test
  public void errorCounterInvalidValuesTest() {
    airlineParser.errorCounter(52);
    for (int i = 0; i < 11; i++) {
      assertEquals(0, (long) airlineParser.errorCollection.get(i));
    }
  }

  /**
   * Verify that when errorCounter is called with an invalid number for the key, totalErrors remains
   * the same.
   */
  @Test
  public void errorCounterInvalidTotalTest() {
    airlineParser.errorCounter(52);
    assertEquals(0, airlineParser.totalErrors);
  }

  /**
   * Verify that when errorCounter is called with a valid key, the value corresponding to that key
   * increases by one.
   */
  @Test
  public void errorCounterValidCorrespondingValueTest() {
    airlineParser.errorCounter(3);
    assertEquals(1, (long) airlineParser.errorCollection.get(3));
  }

  /**
   * Verify that when errorCounter is called with a valid key, all values which do not correspond to
   * that key remain unchanged.
   */
  @Test
  public void errorCounterValidOtherValuesTest() {
    airlineParser.errorCounter(3);
    for (int i = 0; i < 11; i++) {
      if (i != 3) {
        assertEquals(0, (long) airlineParser.errorCollection.get(i));
      }
    }
  }

  /** Verify that when errorCounter is called with a valid key, totalErrors increase by 1. */
  @Test
  public void errorCounterValidTotalTest() {
    airlineParser.errorCounter(3);
    assertEquals(1, airlineParser.totalErrors);
  }

  /**
   * Verify that getErrorMessage returns the correct message when no errors have been found in the
   * file.
   */
  @Test
  public void getErrorMessageNoErrorTest() {
    String errorMessage = airlineParser.getErrorMessage(true);
    assertEquals("File uploaded with 0 invalid lines rejected.\n", errorMessage);
  }

  /**
   * Verify that getErrorMessage returns the correct message when an error has been found in the
   * file.
   */
  @Test
  public void getErrorMessageErrorTest() {
    airlineParser.errorCounter(3);
    String errorMessage = airlineParser.getErrorMessage(true);
    assertEquals(
        "File uploaded with 1 invalid lines rejected.\n"
            + "Error [3] Invalid airline name: 1 occurrences\n",
        errorMessage);
  }
}
