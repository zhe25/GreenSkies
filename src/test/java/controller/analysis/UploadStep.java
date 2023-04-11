package controller.analysis;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.data.Airline;
import model.data.Airport;
import model.data.Route;
import model.data.Storage;
import model.database.SQLiteDatabase;
import model.loader.Loader;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;

/**
 * This class is the step for the cucumber tests for the uploading data methods.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class UploadStep {
  private String fileType;
  private String filePath;
  private Storage storage = new Storage();
  private Loader loader = new Loader(storage);
  private SQLiteDatabase database = new SQLiteDatabase();

  @Given("the data type is {string} and the local path of the data file is {string}")
  public void theFileIUploadIs(String fileType, String filePath) {
    this.filePath = filePath;
    this.fileType = fileType;
  }

  @When("upload the file via upload page")
  public void uploadTheFile()
      throws ClassNotFoundException, FileNotFoundException, SQLException, FileSystemException {
    loader.loadFile(this.filePath, this.fileType);
  }

  @Then(
      "the data uploaded should contains following information of the airport {int},{string},{string},{string},{string},{string},{double},{double},{int},{float},{string},{string}")
  public void theAirportDataInStorageIs(
      int airportID,
      String name,
      String city,
      String country,
      String IATA,
      String ICAO,
      double lat,
      double lon,
      int alt,
      float timezone,
      String DST,
      String databaseTimeZone) {
    Airport airport =
        new Airport(
            airportID,
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
            databaseTimeZone);
    Assert.assertEquals(airport, storage.getAirports().get(0));
  }

  @Then(
      "the data uploaded should contains following information of the route {string},{int},{string},{int},{string},{int},{string},{int},{string}")
  public void theRouteDataInStorageIs(
      String airlineName,
      int airlineID,
      String sourceAirport,
      int sourceAirportID,
      String destinationAirport,
      int destinationAirportID,
      String codeShare,
      int numOfStops,
      String equipment) {
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
            equipment.split(" "));
    Assert.assertEquals(route, storage.getRoutes().get(0));
  }

  @Then(
      "the data uploaded should contains following information of the airline {int},{string},{string},{string},{string},{string},{string},{string}")
  public void theRouteDataInStorageIs(
      int airlineID,
      String name,
      String alias,
      String IATA,
      String ICAO,
      String callsign,
      String country,
      String activeStatus) {
    boolean actStat;
    actStat = activeStatus.equals("Y");
    Airline airline = new Airline(airlineID, name, alias, IATA, ICAO, callsign, country, actStat);
    Assert.assertEquals(airline, storage.getAirlines().get(0));
  }
}
