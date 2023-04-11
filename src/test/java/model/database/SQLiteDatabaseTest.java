package model.database;

import model.data.Airline;
import model.data.Airport;
import model.data.Route;
import model.data.Storage;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Unit test for database.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class SQLiteDatabaseTest {
  private static Connection con;
  private static Statement cleanState;
  private static ResultSet tableNames;
  private static SQLiteDatabase databaseForCleanUp;
  private Storage storage;
  private SQLiteDatabase database;
  private Statement state;
  private PreparedStatement prep;
  private ResultSet res;

  /** Setup storage and database with one row in each table and list in storage. */
  @Before
  public void setUp() {
    database = new SQLiteDatabase();
    con = database.getCon();
    database.closeAutoCommite();
  }

  /** Test that if the provided csv file will be processed and remove extension. */
  @Test
  public void isTableNameSetAndProcessed() {
    assertEquals("'testFile'", database.setTableName("testFile.csv"));
  }

  /** Test that if airport table is correctly created. */
  @Test
  public void isAirportsTableCreated() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'airportTest'");
    database.startCommite();

    database.setTableName("airportTest");
    database.buildAirportsTable();
    res =
        state.executeQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='airportTest'");
    assertTrue(res.next());
    res.close();
    state.close();
    con.close();
  }
  /** Test that if airline table is correctly created. */
  @Test
  public void isAirlinesTableCreated() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'airlineTest'");
    database.startCommite();

    database.setTableName("airlineTest");
    database.buildAirlinesTable();
    res =
        state.executeQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='airlineTest'");
    assertTrue(res.next());
    res.close();
    state.close();
    con.close();
  }
  /** Test that if route table is correctly created. */
  @Test
  public void isRouteTableCreated() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'routeTest'");
    database.startCommite();

    database.setTableName("routeTest");
    database.buildRoutesTable();
    res =
        state.executeQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='routeTest'");
    assertTrue(res.next());
    res.close();
    state.close();
    con.close();
  }
  /** Test that if file_list table is correctly created. */
  @Test
  public void isFileListCreated() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'file_list'");
    database.startCommite();
    database.buildTableList();
    res =
        state.executeQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='file_list'");
    assertTrue(res.next());
    res.close();
    state.close();
    con.close();
  }
  /** Test that if history table is correctly created. */
  @Test
  public void isHistoryCreated() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'history'");
    database.startCommite();
    database.buildHistoryTable();
    res =
        state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='history'");
    assertTrue(res.next());
    res.close();
    state.close();
    con.close();
  }
  /** Test that if airport successfully insert into database. */
  @Test
  public void isAirportInsertIntoDatabase() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'AirportInsertion'");
    database.startCommite();
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
    database.setTableName("AirportInsertion");
    database.buildAirportsTable();
    database.addAirports(airport);
    database.startCommite();
    res = state.executeQuery("select name from 'AirportInsertion' where airport_id=1");
    assertEquals("Goroka", res.getString("name"));
    res.close();
    state.close();
    con.close();
  }

  /** Test that if route successfully insert into database. */
  @Test
  public void isRouteInsertIntoDatabase() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'routeInsertion'");
    database.startCommite();
    Route route = new Route("2B", 410, "AER", 2965, "KZN", 2990, "", 0, new String[] {"CR2"});
    database.setTableName("routeInsertion");
    database.buildRoutesTable();
    database.addRoutes(route);
    database.startCommite();
    res =
        state.executeQuery("select sourceAirport from 'routeInsertion' where sourceAirportID=2965");
    assertEquals("AER", res.getString("sourceAirport"));
    res.close();
    state.close();
    con.close();
  }
  /** Test that if airline successfully insert into database. */
  @Test
  public void isAirlineInsertIntoDatabase() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'airlineInsertion'");
    database.startCommite();
    Airline airline = new Airline(2, "Private flight", "\\N", "-", "N/A", "", "", true);
    database.setTableName("airlineInsertion");
    database.buildAirlinesTable();
    database.addAirlines(airline);
    database.startCommite();
    res = state.executeQuery("select airlineName from 'airlineInsertion' where airline_id=2");
    assertEquals("Private flight", res.getString("airlineName"));
    res.close();
    state.close();
    con.close();
  }
  /**
   * Test that if airport table successfully initialised into database, if not exist then create
   * one.
   */
  @Test
  public void isAirportTableInitialised() throws SQLException {
    database.initialiseTable("Airport", "airportTableinit.csv");
    state = con.createStatement();
    res =
        state.executeQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='airportTableinit'");
    assertTrue(res.next());
    res.close();
    state.close();
    con.close();
  }
  /**
   * Test that if route table successfully initialised into database, if not exist then create one.
   */
  @Test
  public void isRouteTableInitialised() throws SQLException {
    database.initialiseTable("Route", "routeTableinit.csv");
    state = con.createStatement();
    res =
        state.executeQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='routeTableinit'");
    assertTrue(res.next());
    res.close();
    state.close();
    con.close();
  }
  /**
   * Test that if airline table successfully initialised into database, if not exist then create
   * one.
   */
  @Test
  public void isAirlineTableInitialised() throws SQLException {
    database.initialiseTable("Airline", "airlineTableinit.csv");
    state = con.createStatement();
    res =
        state.executeQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='airlineTableinit'");
    assertTrue(res.next());
    res.close();
    state.close();
    con.close();
  }
  /** Test that if airport table successfully up-to-dated. */
  @Test
  public void isAirportInTheStorgeUpdated() throws SQLException {
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
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'airportStorageUpdate'");
    database.startCommite();
    database.initialiseTable("Airport", "airportStorageUpdate");
    database.addAirports(airport);
    database.startCommite();
    database.updateTableList("airportStorageUpdate.csv", "Airport");
    storage = new Storage();
    database.initialiseStorage(storage);
    assertEquals(1, storage.getAirports().size());
    state.close();
    con.close();
  }
  /** Test that if route table successfully up-to-dated. */
  @Test
  public void isRouteInTheStorgeUpdated() throws SQLException {
    Route route = new Route("2B", 410, "AER", 2965, "KZN", 2990, "", 0, new String[] {"CR2"});

    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'routeStorageUpdate'");
    database.startCommite();
    database.initialiseTable("Route", "routeStorageUpdate");
    database.addRoutes(route);
    database.startCommite();
    database.updateTableList("routeStorageUpdate.csv", "Route");
    storage = new Storage();
    database.initialiseStorage(storage);
    assertEquals(1, storage.getRoutes().size());
    state.close();
    con.close();
  }

  /** Test that if airline table successfully up-to-dated. */
  @Test
  public void isAirlineInTheStorgeUpdated() throws SQLException {
    Airline airline = new Airline(2, "Private flight", "\\N", "-", "N/A", "", "", true);
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'airlineStorageUpdate'");
    database.startCommite();
    database.initialiseTable("Airline", "airlineStorageUpdate");
    database.addAirlines(airline);
    database.startCommite();
    database.updateTableList("airlineStorageUpdate.csv", "Airline");
    storage = new Storage();
    database.initialiseStorage(storage);
    assertEquals(1, storage.getAirlines().size());
    state.close();
    con.close();
  }

  /** Test that if history table successfully up-to-dated. */
  @Test
  public void isHistoryinTheStorgeUpdated() throws SQLException {
    Route route = new Route("2B", 410, "AER", 2965, "KZN", 2990, "", 0, new String[] {"CR2"});
    ArrayList<Route> history = new ArrayList<>();
    history.add(route);
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'history'");
    database.startCommite();
    database.initialiseTable("History", "history");
    database.updateHistoryTable(history);
    storage = new Storage();
    database.initialiseStorage(storage);
    assertEquals(1, storage.getHistory().size());
    state.close();
    con.close();
  }
  /** Test that if airport table successfully up-to-dated from list. */
  @Test
  public void isAirportTableUpdated() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'AirportTableUpdate'");
    database.startCommite();
    database.initialiseTable("Airport", "AirportTableUpdate");
    Airport airport1 =
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
    Airport airport2 =
        new Airport(
            2,
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
    ArrayList<Airport> airports = new ArrayList<>();
    airports.add(airport1);
    airports.add(airport2);
    database.updateAirportTable(airports);
    res = state.executeQuery("select count(*) as row_count from 'AirportTableUpdate'");
    assertEquals(2, res.getInt("row_count"));
    res.close();
    state.close();
    con.close();
  }
  /** Test that if airline table successfully up-to-dated from list. */
  @Test
  public void isAirlineTableUpdated() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'AirlineTableUpdate'");
    database.startCommite();
    database.initialiseTable("Airline", "AirlineTableUpdate");

    Airline airline1 = new Airline(1, "Private flight", "\\N", "-", "N/A", "", "", true);
    Airline airline2 = new Airline(2, "Private flight", "\\N", "-", "N/A", "", "", true);

    ArrayList<Airline> airlines = new ArrayList<>();
    airlines.add(airline1);
    airlines.add(airline2);
    database.updateAirlineTable(airlines);
    res = state.executeQuery("select count(*) as row_count from 'AirlineTableUpdate'");
    assertEquals(2, res.getInt("row_count"));
    res.close();
    state.close();
    con.close();
  }
  /** Test that if route table successfully up-to-dated from list. */
  @Test
  public void isRouteTableUpdated() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'routeTableUpdate'");
    database.startCommite();
    database.initialiseTable("Route", "routeTableUpdate");

    Route route1 = new Route("2B", 410, "AER", 2965, "KZN", 2990, "", 0, new String[] {"CR2"});
    Route route2 = new Route("2B", 410, "AER", 2965, "KZN", 2990, "", 0, new String[] {"CR2"});

    ArrayList<Route> routes = new ArrayList<>();
    routes.add(route1);
    routes.add(route2);
    database.updateRoute(routes);
    res = state.executeQuery("select count(*) as row_count from 'routeTableUpdate'");
    assertEquals(2, res.getInt("row_count"));
    res.close();
    state.close();
    con.close();
  }
  /** Test that if history table successfully up-to-dated from list. */
  @Test
  public void isHistoryTableUpdated() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'history'");
    database.startCommite();
    database.initialiseTable("history", "history");

    Route route1 = new Route("2B", 410, "AER", 2965, "KZN", 2990, "", 0, new String[] {"CR2"});
    Route route2 = new Route("2B", 410, "AER", 2965, "KZN", 2990, "", 0, new String[] {"CR2"});

    ArrayList<Route> history = new ArrayList<>();
    history.add(route1);
    history.add(route2);
    database.updateHistoryTable(history);
    res = state.executeQuery("select count(*) as row_count from 'history'");
    assertEquals(2, res.getInt("row_count"));
    res.close();
    state.close();
    con.close();
  }
  /** Test that if airport file name from file_list can be fetched into a list. */
  @Test
  public void getFileNameByTypeAirport() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'file_list'");
    database.startCommite();
    database.initialiseTable("fileName", "file_name");
    database.updateTableList("getFileNameAirport.csv", "Airport");
    assertEquals("getFileNameAirport.csv", database.getFileNamesByType("Airport").get(0));
    state.close();
    con.close();
  }
  /** Test that if route file name from file_list can be fetched into a list. */
  @Test
  public void getFileNameByTypeRoute() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'file_list'");
    database.startCommite();
    database.initialiseTable("fileName", "file_name");
    database.updateTableList("getFileNameRoute.csv", "Route");
    assertEquals("getFileNameRoute.csv", database.getFileNamesByType("Route").get(0));
    state.close();
    con.close();
  }
  /** Test that if airline name from file_list can be fetched into a list. */
  @Test
  public void getFileNameByTypeAirline() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'file_list'");
    database.startCommite();
    database.initialiseTable("fileName", "file_name");
    database.updateTableList("getFileNameAirline.csv", "Airline");
    assertEquals("getFileNameAirline.csv", database.getFileNamesByType("Airline").get(0));
    state.close();
    con.close();
  }
  /** Test that if file name does not in file list will return an empty list. */
  @Test
  public void getFileNameByTypeNameNotExist() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'file_list'");
    database.startCommite();
    database.initialiseTable("fileName", "file_name");
    ArrayList<String> emptyList = new ArrayList<>();
    assertEquals(emptyList, database.getFileNamesByType("Airline"));
    state.close();
    con.close();
  }
  /** Test that if wrong type of file provided will return an empty list. */
  @Test
  public void getFileNameByTypeWrongType() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'file_list'");
    database.startCommite();
    database.initialiseTable("fileName", "file_name");
    database.updateTableList("getFileNameAirline.csv", "Airline");
    ArrayList<String> emptyList = new ArrayList<>();
    assertEquals(emptyList, database.getFileNamesByType("Airport"));
    state.close();
    con.close();
  }

  /** Test that if specific table can be deleted from database. */
  @Test
  public void isFileDeleted() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'fileToBeDeleted'");
    database.startCommite();
    database.initialiseTable("Airport", "fileToBeDeleted.csv");
    database.updateTableList("fileToBeDeleted.csv", "Airport");
    res =
        state.executeQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='fileToBeDeleted'");
    if (res.next()) {
      state.close();
      database.deleteFile("fileToBeDeleted.csv", "Airport");
      res =
          state.executeQuery(
              "SELECT name FROM sqlite_master WHERE type='table' AND name='fileToBeDeleted'");
      assertFalse(res.next());
    } else {
      Exception e = new Exception("File not deleted.");
    }
    res.close();
    state.close();
    con.close();
  }

  /** Test that if specific file name can be deleted from file_list. */
  @Test
  public void isFileDeletedFromFileList() throws SQLException {
    state = con.createStatement();
    state.executeUpdate("drop table IF EXISTS 'fileToBeDeleted'");
    database.startCommite();
    database.initialiseTable("Airport", "fileToBeDeleted.csv");
    database.updateTableList("fileToBeDeleted.csv", "Airport");
    res =
        state.executeQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='fileToBeDeleted'");
    if (res.next()) {
      state.close();
      state = con.createStatement();
      database.deleteFile("fileToBeDeleted.csv", "Airport");
      res = state.executeQuery("select count(*) as row_count from 'file_list'");
      assertEquals(0, res.getInt("row_count"));
    } else {
      Exception e = new Exception("File not deleted.");
    }
    res.close();
    state.close();
    con.close();
  }
}
