package model.data;

import javafx.scene.control.CheckBox;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;
import java.util.Arrays;
import static org.junit.Assert.*;

/**
 * Unit test for Route class.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class RouteTest {

  private Route route;

  @Before
  public void setUp() {
    route = new Route("2B", 410, "AER", 2965, "KZN", 2990, "", 0, new String[] {"CR2"});
    Route routeEquipmentTest =
        new Route("2B", 410, "AER", 2966, "KZN", 2990, "", 0, new String[] {});
  }

  @Test
  public void getAirlineName() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("airlineName");
    field.setAccessible(true);
    assertEquals(field.get(route), route.getAirlineName());
  }

  @Test
  public void getAirlineID() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("airlineID");
    field.setAccessible(true);
    assertEquals(field.get(route), route.getAirlineID());
  }

  @Test
  public void getSourceAirport() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("sourceAirport");
    field.setAccessible(true);
    assertEquals(field.get(route), route.getSourceAirport());
  }

  @Test
  public void getSourceAirportID() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("sourceAirportID");
    field.setAccessible(true);
    assertEquals(field.get(route), route.getSourceAirportID());
  }

  @Test
  public void getDestinationAirport() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("destinationAirport");
    field.setAccessible(true);
    assertEquals(field.get(route), route.getDestinationAirport());
  }

  @Test
  public void getDestinationAirportID() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("destinationAirportID");
    field.setAccessible(true);
    assertEquals(field.get(route), route.getDestinationAirportID());
  }

  @Test
  public void getCodeShare() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("codeShare");
    field.setAccessible(true);
    assertEquals(field.get(route), route.getCodeShare());
  }

  @Test
  public void getNumOfStops() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("numOfStops");
    field.setAccessible(true);
    assertEquals(field.get(route), route.getNumOfStops());
  }

  @Test
  public void getEquipment() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("equipment");
    field.setAccessible(true);
    assertEquals(
        Arrays.toString((String[]) field.get(route)), Arrays.toString(route.getEquipment()));
  }

  @Test
  public void getFirstEquipment() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("equipment");
    field.setAccessible(true);
    assertEquals(Arrays.asList((String[]) field.get(route)).get(0), route.getFirstEquipment());
  }

  @Test
  public void getEmissions() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("emissions");
    field.setAccessible(true);
    field.set(route, 123.123);
    assertEquals(123.123, route.getEmissions(), 0.001);
  }

  @Test
  public void setEmissions() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("emissions");
    field.setAccessible(true);
    route.setEmissions(123.123);
    assertEquals(123.123, field.get(route));
  }

  @Test
  public void getDistance() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("distance");
    field.setAccessible(true);
    field.set(route, 123.123);
    assertEquals(123.123, route.getDistance(), 0.001);
  }

  @Test
  public void setDistance() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("distance");
    field.setAccessible(true);
    route.setDistance(123.123);
    assertEquals(123.123, field.get(route));
  }

  @Test
  public void getTimesTaken() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("timesTaken");
    field.setAccessible(true);
    assertEquals(field.get(route), route.getTimesTaken());
  }

  @Test
  public void setTimesTaken() throws NoSuchFieldException, IllegalAccessException {
    final Field field = route.getClass().getDeclaredField("timesTaken");
    field.setAccessible(true);
    route.setTimesTaken(123);
    assertEquals(123, field.get(route));
  }

  @Test
  public void testEquals() {
    Route route1 = new Route("2B", 410, "AER", 2965, "KZN", 2990, "", 0, new String[] {"CR2"});
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
    assertEquals(route, route1);
    assertNotEquals(route, airport);
  }

  @Test
  public void getEmissionsDisplayString() {
    route.setEmissions(123.123456789);
    assertEquals(route.getEmissionsDisplayString(), "123.12");
  }

  @Test
  public void getDistanceDisplayString() {
    route.setDistance(123.123456789);
    assertEquals(route.getDistanceDisplayString(), "123.12");
  }

  @Test
  public void testToString() {
    assertEquals(
        route.toString(),
        "Airline name: 2B\n"
            + "Airline ID: 410\n"
            + "Source Airport: AER\n"
            + "Source Airport ID: 2965\n"
            + "Destination Airport: KZN\n"
            + "Destination Airport ID: 2990\n"
            + "Code Share: \n"
            + "Number of Stops: 0\n"
            + "Equipment: [CR2]\n"
            + "First Equipment: CR2\n"
            + "Carbon Emissions: 0.0\n"
            + "Distance: 0.0\n"
            + "Times Taken: 0\n");
  }
}
