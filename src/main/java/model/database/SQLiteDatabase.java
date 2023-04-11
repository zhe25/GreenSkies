package model.database;

import model.data.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to store persistent data in database.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class SQLiteDatabase {
  /** database connection. */
  private static Connection con;

  /** statement for building tables. */
  private Statement buildTable;

  /** Variable that contains statement for database. */
  private Statement state;

  /** Variable that contains statement for prepare statement database. */
  private PreparedStatement prep;

  /** Variable that contains result fetched form database. */
  private ResultSet res;

  /** Variable for table name that is going to be created in database. */
  private String tableName;

  /** The constructor for the SQLiteDatabase. */
  public SQLiteDatabase() {
    buildConnection();
    closeAutoCommite();
  }

  /**
   * This method create connection to local database, and will create one if there is no database.
   */
  protected void buildConnection() {
    try {
      // sqlite driver
      Class.forName("org.sqlite.JDBC");
      // database path, if it's new database, it will be created in the project folder
      con = DriverManager.getConnection("jdbc:sqlite:app.sqlite");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  /** This method is getter for connection returns connection as an object. */
  public Connection getCon() {
    return con;
  }

  /**
   * This method closes auto-commit feature for database to store all query in one transection, it
   * will help to significantly improve insertion speed.
   */
  public void closeAutoCommite() {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    try {
      con.setAutoCommit(false);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  /** This method manually starts commit for database. */
  public void startCommite() {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
    try {
      con.commit();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  /**
   * This method sets table name that is going to be created in database.
   *
   * @param fileName Provided file name.
   * @return Return fileName without extension name.
   */
  public String setTableName(String fileName) {
    this.tableName = "'" + fileName.split("\\.")[0] + "'";
    return tableName;
  }

  /** This method builds airports table with airport attributes as columns in database. */
  protected void buildAirportsTable() {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    try {
      buildTable = con.createStatement();
      buildTable.executeUpdate(
          "create table "
              + tableName
              + "(airport_id integer,"
              + "name varchar(60),"
              + "city varchar(60),"
              + "country varchar(60),"
              + "IATA varchar(5),"
              + "ICAO varchar(5),"
              + "lat double(4, 6),"
              + "lon double(4, 6),"
              + "alt integer,"
              + "timezone float,"
              + "DST varchar(60),"
              + "DBTimezone varchar(60),"
              + "primary key (airport_id))");
      startCommite();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        buildTable.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }

  /** This method builds routes table with routes attributes as columns in database. */
  protected void buildRoutesTable() {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    try {
      buildTable = con.createStatement();
      buildTable.executeUpdate(
          "create table "
              + tableName
              + "(route_id integer,"
              + "airlineName varchar(60),"
              + "airlineID integer,"
              + "sourceAirport varchar(60),"
              + "sourceAirportID integer,"
              + "destinationAirport varchar(60),"
              + "destinationAirportID integer,"
              + "codeShare varchar(60),"
              + "numOfStops integer,"
              + "equipment varchar(256),"
              + "emissions double(100, 10),"
              + "distance double(100, 10),"
              + "timesTaken integer,"
              + "primary key (route_id))");
      startCommite();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        buildTable.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }

  /**
   * This method builds airlines table with given name and with airline attributes as columns in
   * database.
   */
  protected void buildAirlinesTable() {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    try {
      buildTable = con.createStatement();
      buildTable.executeUpdate(
          "create table"
              + tableName
              + "(airline_id integer,"
              + "airlineName varchar(60),"
              + "alias varchar(60),"
              + "IATA varchar(5),"
              + "ICAO varchar(5),"
              + "callsign varchar(60),"
              + "country varchar(60),"
              + "activeStatus boolean,"
              + " primary key (airline_id))");
      startCommite();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        buildTable.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }

  /** This method create a table that contains all file names and corresponding types */
  protected void buildTableList() {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
    try {
      buildTable = con.createStatement();
      buildTable.executeUpdate(
          "create table file_list"
              + "(file_id integer,"
              + "file_name varchar(255),"
              + "file_type varchar(10),"
              + " primary key (file_id))");
      startCommite();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        buildTable.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }

  /** * This method insert all attributes of an route object into history table in the database. */
  public void buildHistoryTable() {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    try {
      buildTable = con.createStatement();
      buildTable.executeUpdate(
          "create table "
              + "history"
              + "(route_id integer,"
              + "airlineName varchar(60),"
              + "airlineID integer,"
              + "sourceAirport varchar(60),"
              + "sourceAirportID integer,"
              + "destinationAirport varchar(60),"
              + "destinationAirportID integer,"
              + "codeShare varchar(60),"
              + "numOfStops integer,"
              + "equipment varchar(256),"
              + "emissions double(100, 10),"
              + "distance double(100, 10),"
              + "timesTaken integer,"
              + "primary key (route_id))");
      startCommite();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        buildTable.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }

  /**
   * This method insert all attributes of an airport object into database.
   *
   * @param airport Object of airport contains information of airport as attributes.
   */
  public void addAirports(Airport airport) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
    try {
      prep = con.prepareStatement("insert into " + tableName + " values(?,?,?,?,?,?,?,?,?,?,?,?);");
      prep.setInt(1, airport.getAirportID());
      prep.setString(2, airport.getName());
      prep.setString(3, airport.getCity());
      prep.setString(4, airport.getCountry());
      prep.setString(5, airport.getIATA());
      prep.setString(6, airport.getICAO());
      prep.setDouble(7, airport.getLatitude());
      prep.setDouble(8, airport.getLongitude());
      prep.setInt(9, airport.getAltitude());
      prep.setDouble(10, airport.getTimezone());
      prep.setString(11, airport.getDST());
      prep.setString(12, airport.getDataBaseTimeZone());
      prep.execute();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        prep.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }

  /**
   * This method insert all attributes of an route object into database.
   *
   * @param route Object of route contains information of route as attributes.
   */
  public void addRoutes(Route route) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    try {
      prep =
          con.prepareStatement("insert into " + tableName + " values(?,?,?,?,?,?,?,?,?,?,?,?,?);");
      prep.setString(2, route.getAirlineName());
      prep.setInt(3, route.getAirlineID());
      prep.setString(4, route.getSourceAirport());
      prep.setInt(5, route.getSourceAirportID());
      prep.setString(6, route.getDestinationAirport());
      prep.setInt(7, route.getDestinationAirportID());
      prep.setString(8, route.getCodeShare());
      prep.setInt(9, route.getNumOfStops());

      String[] equipments = route.getEquipment();
      StringBuilder stringEquipment = new StringBuilder();
      if (equipments != null) {
        for (String equipment : equipments) {
          stringEquipment.append(" ").append(equipment);
        }
      }

      prep.setString(10, stringEquipment.toString());
      prep.setDouble(11, route.getEmissions());
      prep.setDouble(12, route.getDistance());
      prep.setInt(13, route.getTimesTaken());
      prep.execute();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        prep.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }

  /**
   * This method insert all attributes of an airline object into database.
   *
   * @param airline Object of airline contains information of airline as attributes.
   */
  public void addAirlines(Airline airline) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
    try {
      prep = con.prepareStatement("insert into" + tableName + "values(?,?,?,?,?,?,?,?);");
      prep.setInt(1, airline.getAirlineID());
      prep.setString(2, airline.getName());
      prep.setString(3, airline.getAirlineAlias());
      prep.setString(4, airline.getIATA());
      prep.setString(5, airline.getICAO());
      prep.setString(6, airline.getCallsign());
      prep.setString(7, airline.getCountry());
      prep.setBoolean(8, airline.getActiveStatus());
      prep.execute();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        prep.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }

  /**
   * This method initialise table according to provided table type, it deletes table and recreates
   * it. This method also set table name for global attribute for other method to interact to
   * desired table.
   *
   * @param tableType Three types of table corresponding to airport, route and airline.
   */
  public void initialiseTable(String tableType, String fileName) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    setTableName(fileName);

    try {
      state = con.createStatement();
      res =
          state.executeQuery(
              "SELECT name FROM sqlite_master WHERE type='table' AND name='file_list'");
      if (!res.next()) {
        buildTableList();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        res.close();
        state.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }

    switch (tableType) {
      case "Airport":
        try {
          state = con.createStatement();
          res =
              state.executeQuery(
                  "SELECT name FROM sqlite_master WHERE type='table' AND name=" + tableName);
          if (!res.next()) {
            buildAirportsTable();
          }
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        } finally {
          try {
            res.close();
            state.close();
          } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
          }
        }
        break;
      case "Route":
        try {
          state = con.createStatement();
          res =
              state.executeQuery(
                  "SELECT name FROM sqlite_master WHERE type='table' AND name=" + tableName);
          if (!res.next()) {
            buildRoutesTable();
          }
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        } finally {
          try {
            res.close();
            state.close();
          } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
          }
        }
        break;
      case "Airline":
        try {
          state = con.createStatement();
          res =
              state.executeQuery(
                  "SELECT name FROM sqlite_master WHERE type='table' AND name=" + tableName);
          if (!res.next()) {
            buildAirlinesTable();
          }
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        } finally {
          try {
            res.close();
            state.close();
          } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
          }
        }
        break;
    }
    startCommite();
  }

  /**
   * This method initialise storage with data from database.
   *
   * @param storage The storage used.
   * @throws ClassNotFoundException This throws a ClassNotFoundException.
   */
  public void initialiseStorage(Storage storage) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    List<String> airportfileNames = getFileNamesByType("Airport");

    for (String airportsName : airportfileNames) {
      setTableName(airportsName);

      try {
        state = con.createStatement();
        res =
            state.executeQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name=" + tableName);

        if (res.next()) {
          state = con.createStatement();
          res = state.executeQuery("select * from " + tableName);
          ArrayList<DataType> airports = new ArrayList<>();
          while (res.next()) {
            int airportId = res.getInt("airport_id");
            String name = res.getString("name");
            String city = res.getString("city");
            String country = res.getString("country");
            String IATA = res.getString("IATA");
            String ICAO = res.getString("ICAO");
            double lat = res.getDouble("lat");
            double lon = res.getDouble("lon");
            int alt = res.getInt("alt");
            float timezone = res.getFloat("timezone");
            String DST = res.getString("DST");
            String DBTimezone = res.getString("DBTimezone");
            Airport airport =
                new Airport(
                    airportId,
                    name,
                    city,
                    country,
                    IATA,
                    ICAO,
                    lat,
                    lon,
                    alt,
                    timezone,
                    DST,
                    DBTimezone);
            airports.add(airport);
          }

          storage.setData(airports, "Airport", airportsName);
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      } finally {
        try {
          res.close();
          state.close();
          //          con.close();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
      }
    }

    List<String> airlinesFileNames = getFileNamesByType("Airline");

    for (String airlinesName : airlinesFileNames) {
      setTableName(airlinesName);

      try {
        state = con.createStatement();
        res =
            state.executeQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name=" + tableName);

        if (res.next()) {
          state = con.createStatement();
          res = state.executeQuery("select * from " + tableName);
          ArrayList<DataType> airlines = new ArrayList<>();
          while (res.next()) {
            int airlineId = res.getInt("airline_id");
            String airlineName = res.getString("airlineName");
            String alias = res.getString("alias");
            String IATA = res.getString("IATA");
            String ICAO = res.getString("ICAO");
            String callsign = res.getString("callsign");
            String country = res.getString("country");
            Boolean activeStatus = res.getBoolean("activeStatus");
            Airline airline =
                new Airline(
                    airlineId, airlineName, alias, IATA, ICAO, callsign, country, activeStatus);
            airlines.add(airline);
          }

          storage.setData(airlines, "Airline", airlinesName);
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      } finally {
        try {
          res.close();
          state.close();
          //        con.close();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
      }
    }

    List<String> routeNames = getFileNamesByType("Route");

    for (String routesName : routeNames) {
      setTableName(routesName);

      try {
        state = con.createStatement();
        res =
            state.executeQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name=" + tableName);

        if (res.next()) {
          state = con.createStatement();
          res = state.executeQuery("select * from " + tableName);
          ArrayList<DataType> routes = new ArrayList<>();
          while (res.next()) {
            //                int route_id = routesRow.getInt("route_id");
            String airlineName = res.getString("airlineName");
            int airlineID = res.getInt("airlineID");
            String sourceAirport = res.getString("sourceAirport");
            int sourceAirportID = res.getInt("sourceAirportID");
            String destinationAirport = res.getString("destinationAirport");
            int destinationAirportID = res.getInt("destinationAirportID");
            String codeShare = res.getString("codeShare");
            int numOfStops = res.getInt("numOfStops");

            String equipment = res.getString("equipment");
            String[] equipmentArray;
            if (equipment != null) {
              equipmentArray = equipment.split(" ");
            } else {
              equipmentArray = null;
            }

            double emissions = res.getDouble("emissions");
            double distance = res.getDouble("distance");
            int timesTaken = res.getInt("timesTaken");

            assert equipmentArray != null;
            Route route =
                new Route(
                    airlineName,
                    airlineID,
                    sourceAirport,
                    sourceAirportID,
                    destinationAirport,
                    destinationAirportID,
                    codeShare,
                    numOfStops,
                    equipmentArray);
            route.setEmissions(emissions);
            route.setTimesTaken(timesTaken);
            route.setDistance(distance);
            routes.add(route);
          }

          storage.setData(routes, "Route", routesName);
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      } finally {
        try {
          res.close();
          state.close();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
      }
    }

    setTableName("history");

    try {
      state = con.createStatement();
      res =
          state.executeQuery(
              "SELECT name FROM sqlite_master WHERE type='table' AND name=" + tableName);

      if (res.next()) {
        state = con.createStatement();
        res = state.executeQuery("select * from " + tableName);
        ArrayList<DataType> routesHistory = new ArrayList<>();
        while (res.next()) {
          //                int route_id = routesRow.getInt("route_id");
          String airlineName = res.getString("airlineName");
          int airlineID = res.getInt("airlineID");
          String sourceAirport = res.getString("sourceAirport");
          int sourceAirportID = res.getInt("sourceAirportID");
          String destinationAirport = res.getString("destinationAirport");
          int destinationAirportID = res.getInt("destinationAirportID");
          String codeShare = res.getString("codeShare");
          int numOfStops = res.getInt("numOfStops");

          String equipment = res.getString("equipment");
          String[] equipmentArray;
          if (equipment != null) {
            equipmentArray = equipment.split(" ");
          } else {
            equipmentArray = null;
          }

          double emissions = res.getDouble("emissions");
          double distance = res.getDouble("distance");
          int timesTaken = res.getInt("timesTaken");

          assert equipmentArray != null;
          Route route =
              new Route(
                  airlineName,
                  airlineID,
                  sourceAirport,
                  sourceAirportID,
                  destinationAirport,
                  destinationAirportID,
                  codeShare,
                  numOfStops,
                  equipmentArray);
          route.setEmissions(emissions);
          route.setTimesTaken(timesTaken);
          route.setDistance(distance);
          routesHistory.add(route);
        }

        List<Route> history = storage.getHistory();
        for (DataType route : routesHistory) {
          Route bufferRoute = (Route) route;
          history.add(bufferRoute);
        }
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        res.close();
        state.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }

    try {
      con.close();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  /**
   * This method update airport table with given airports list, this method can be used after call
   * to initialiseTable method.
   *
   * @param airports A list contains all airports needs to be uploaded to database.
   */
  public void updateAirportTable(List<Airport> airports) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    try {
      if (!airports.isEmpty()) {
        for (Airport airport : airports) {
          addAirports(airport);
        }
        startCommite();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  /**
   * This method update airline table with given airlines list, this method can be used after call
   * to initialiseTable method.
   *
   * @param airlines A list contains all airlines needs to be uploaded to database.
   */
  public void updateAirlineTable(List<Airline> airlines) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    try {
      if (!airlines.isEmpty()) {
        for (Airline airline : airlines) {
          addAirlines(airline);
        }
        startCommite();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  /**
   * This method update route table with given route list, this method can be used after call to
   * initialiseTable method.
   *
   * @param routes A list contains all routes needs to be uploaded to database.
   */
  public void updateRoute(List<Route> routes) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    try {
      if (!routes.isEmpty()) {
        for (Route route : routes) {
          addRoutes(route);
        }
        startCommite();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  /**
   * this method update table name list when user upload a data file.
   *
   * @param fileName Name of the file that user uploaded.
   * @param fileType Type of the file that user uploaded.
   */
  public void updateTableList(String fileName, String fileType) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
    try {
      state = con.createStatement();
      res =
          state.executeQuery(
              "select * from 'file_list' where file_name='"
                  + fileName
                  + "' and file_type='"
                  + fileType
                  + "'");
      if (!res.next()) {
        prep = con.prepareStatement("insert into 'file_list' values(?,?,?);");
        prep.setString(2, fileName);
        prep.setString(3, fileType);
        prep.execute();
        prep.close();
        startCommite();
      }

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        res.close();
        state.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }

  /**
   * This method will update history in database when user add routes to history.
   *
   * @param routes Route Object of route contains information of route as attributes.
   */
  public void updateHistoryTable(List<Route> routes) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    setTableName("history");
    try {
      state = con.createStatement();
      res =
          state.executeQuery(
              "SELECT name FROM sqlite_master WHERE type='table' AND name='history'");

      if (res.next()) {
        state.executeUpdate("delete from 'history'");
        startCommite();
      } else {
        buildHistoryTable();
      }
      setTableName("history");
      if (!routes.isEmpty()) {
        for (Route route : routes) {
          addRoutes(route);
        }
        startCommite();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        res.close();
        state.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }

  /**
   * This method get specific type of file name from database and returns a list containing them.
   *
   * @param fileType File type includes Airport, Airline and route.
   * @return A list that contains all specified type name in it.
   */
  public List<String> getFileNamesByType(String fileType) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
    ArrayList<String> file_names = new ArrayList<>();
    try {
      state = con.createStatement();
      res =
          state.executeQuery(
              "SELECT name FROM sqlite_master WHERE type='table' AND name='file_list'");
      if (res.next()) {
        res = state.executeQuery("select * from 'file_list' where file_type='" + fileType + "'");
        while (res.next()) {
          String fileName = res.getString("file_name");
          file_names.add(fileName);
        }
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        state.close();
        res.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
    return file_names;
  }

  /**
   * This method delete the row in file_list table where file_name and file_Type column is as
   * provided from input.
   *
   * @param fileName The file_name of the row that will be deleted.
   * @param fileType The file_type of the row that will be deleted.
   */
  public void deleteFile(String fileName, String fileType) {
    try {
      if (con.isClosed()) {
        // get connection
        buildConnection();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

    setTableName(fileName);
    try {
      state = con.createStatement();
      state.executeUpdate("drop table " + tableName);
      res =
          state.executeQuery(
              "select count(*) as row_count from 'file_list' where file_name='"
                  + fileName
                  + "' and file_type='"
                  + fileType
                  + "'");
      if (res.getInt("row_count") != 0) {

        state.executeUpdate(
            "delete from file_list where file_name='"
                + fileName
                + "' and file_type='"
                + fileType
                + "'");
      }
      startCommite();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    } finally {
      try {
        res.close();
        state.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
      }
    }
  }
}
