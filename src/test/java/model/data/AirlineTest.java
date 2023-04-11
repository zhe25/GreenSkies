package model.data;

import javafx.scene.control.CheckBox;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;
import static org.junit.Assert.*;

/**
 * Unit test for Airline class.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirlineTest {

  private Airline airline;

  @Before
  public void setUp() {
    // given
    airline = new Airline(2, "Private flight", "\\N", "-", "N/A", "", "", true);
  }

  @Test
  public void getAirlineIDTest() throws NoSuchFieldException, IllegalAccessException {
    // then
    final Field field = airline.getClass().getDeclaredField("airlineID");
    field.setAccessible(true);
    assertEquals(field.get(airline), 2);
  }

  @Test
  public void getNameTest() throws NoSuchFieldException, IllegalAccessException {
    // then
    final Field field = airline.getClass().getDeclaredField("name");
    field.setAccessible(true);
    assertEquals(field.get(airline), "Private flight");
  }

  @Test
  public void getAirlineAliasTest() throws NoSuchFieldException, IllegalAccessException {
    // then
    final Field field = airline.getClass().getDeclaredField("alias");
    field.setAccessible(true);
    assertEquals(field.get(airline), "\\N");
  }

  @Test
  public void getIATATest() throws NoSuchFieldException, IllegalAccessException {
    // then
    final Field field = airline.getClass().getDeclaredField("IATA");
    field.setAccessible(true);
    assertEquals(field.get(airline), "-");
  }

  @Test
  public void getICAOTest() throws NoSuchFieldException, IllegalAccessException {
    // then
    final Field field = airline.getClass().getDeclaredField("ICAO");
    field.setAccessible(true);
    assertEquals(field.get(airline), "N/A");
  }

  @Test
  public void getCallsignTest() throws NoSuchFieldException, IllegalAccessException {
    // then
    final Field field = airline.getClass().getDeclaredField("callsign");
    field.setAccessible(true);
    assertEquals(field.get(airline), "");
  }

  @Test
  public void getCountryTest() throws NoSuchFieldException, IllegalAccessException {
    // then
    final Field field = airline.getClass().getDeclaredField("country");
    field.setAccessible(true);
    assertEquals(field.get(airline), "");
  }

  @Test
  public void getActiveStatusTest() throws NoSuchFieldException, IllegalAccessException {
    // then
    final Field field = airline.getClass().getDeclaredField("activeStatus");
    field.setAccessible(true);
    assertEquals(field.get(airline), true);
  }

  @Test
  public void testEqualsTest() {
    // given
    Airline airline1 = new Airline(2, "Private flight", "\\N", "-", "N/A", "", "", true);
    Airport airport =
        new Airport(
            1,
            "Goroka",
            "Goroka",
            "Papua New Guinea",
            "GKA",
            "AYGA",
            -6.081689,
            145.391881,
            5282,
            10,
            "U",
            "Port_Moresby");
    // then
    assertEquals(airline, airline1);
    assertNotEquals(airline, airport);
  }
}
