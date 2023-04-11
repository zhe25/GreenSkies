package model.loader;

import model.data.*;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Unit tests for the Loader class
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class LoaderTest {

  private Loader loader;
  private Storage storage;
  private ArrayList<String> testLines;

  /** Set up before each test case. */
  @Before
  public void setUp() {
    storage = new Storage();
    loader = new Loader(storage);
    testLines = new ArrayList<>();

    testLines.add(
        "1,\"Goroka Airport\",\"Goroka\",\"Papua New Guinea\",\"GKA\",\"AYGA\",-6.081689834590001,145.391998291,5282,10,\"U\",\"Pacific/Port_Moresby\"");
    testLines.add(
        "2,\"Madang Airport\",\"Madang\",\"Papua New Guinea\",\"MAG\",\"AYMD\",-5.20707988739,145.789001465,20,10,\"U\",\"Pacific/Port_Moresby\"");
    testLines.add(
        "3,\"Mount Hagen Kagamuga Airport\",\"Mount Hagen\",\"Papua New Guinea\",\"HGU\",\"AYMH\",-5.826789855957031,144.29600524902344,5388,10,\"U\",\"Pacific/Port_Moresby\"");
  }

  /**
   * Test that checkFileType throws an exception when called with a filename with an invalid
   * extension
   */
  @Test
  public void testCheckFileTypeInvalidFileName() {
    try {
      loader.checkFileType("../seng202_project/src/test/java/TestFile/badFile.jpg");
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  /**
   * Test that checkFileType doesn't throw an exception when called with a filename with a valid
   * extension
   */
  @Test
  public void testCheckFileTypeValidFileName() {
    try {
      loader.checkFileType("../seng202_project/src/test/java/TestFiles/goodFile.csv");
    } catch (Exception e) {
      fail();
    }
  }

  /** Test that checkFileType throws an exception when called with a file with no extension */
  @Test
  public void testCheckFileTypeNoExtension() {
    try {
      loader.checkFileType("../seng202_project/src/test/java/TestFiles/airportsTest");
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  /**
   * Test that getFileName returns the correct filename for a file path containing forward slashes.
   */
  @Test
  public void testGetFileNameForwardSlash() {
    String filename =
        loader.getFileName(
            "C:/Users/ellaj/Documents/GreenSkies/seng202_project/src/test/java/TestFiles/airlines.csv");
    assertEquals("airlines.csv", filename);
  }

  /** Test that getFileName returns the correct filename for a filepath contiaining back slashes. */
  @Test
  public void testGetFileNameBackSlash() {
    String filename =
        loader.getFileName(
            "C:\\Users\\ellaj\\Documents\\GreenSkies\\seng202_project\\src\\test\\java\\TestFiles\\airlines.csv");
    assertEquals("airlines.csv", filename);
  }

  /** Test that getFileName returns the correct filename for a file path containing no slashes. */
  @Test
  public void testGetFileNameNoSlash() {
    String filename = loader.getFileName("airlines.csv");
    assertEquals("airlines.csv", filename);
  }

  /**
   * Test that checkDuplicateFileName raises an error when another file with the same name has been
   * uploaded.
   */
  @Test
  public void testCheckDuplicateFileNameDuplicateUploaded()
      throws FileNotFoundException, FileSystemException, SQLException {
    loader.loadFile("../seng202_project/src/test/java/TestFiles/airlines.csv", "Airline");
    try {
      loader.checkDuplicateFileName("airlines.csv");
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test that checkDuplicateFileName does not raise an exception when another file with the same
   * name has not been uploaded.
   */
  @Test
  public void testCheckDuplicatFileNameDuplicateNotUploaded()
      throws FileNotFoundException, FileSystemException, SQLException {
    loader.loadFile("../seng202_project/src/test/java/TestFiles/airlines.csv", "Airline");
    try {
      loader.checkDuplicateFileName("airports.csv");
      assertTrue(true);
    } catch (RuntimeException e) {
      fail();
    }
  }

  /**
   * Test that checkDuplicateFileName does not raise an exception when another file with the same
   * name has been uploaded and then deleted.
   */
  @Test
  public void testCheckDuplicateFileNameDuplicateDeleted()
      throws FileNotFoundException, FileSystemException, SQLException {
    loader.loadFile("../seng202_project/src/test/java/TestFiles/airlines.csv", "Airline");
    storage.getAirlineFileList().remove("airlines.csv");
    try {
      loader.checkDuplicateFileName("airlines.csv");
      assertTrue(true);
    } catch (RuntimeException e) {
      fail();
    }
  }

  /** Test that checkDuplicateFileName raises an error when called with a reserved filename. */
  @Test
  public void testCheckDuplicateFileNameReservedName() {
    try {
      loader.checkDuplicateFileName("singleEntryRoutes.csv");
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test that openFile instantiates the Parser class with an ArrayList of Lines matching the
   * contents of the file
   */
  @Test
  public void testOpenFileValidFile() {
    ArrayList<String> actualLines = new ArrayList<>();

    try {
      actualLines = loader.openFile("../seng202_project/src/test/java/TestFiles/airportsTest.csv");
    } catch (Exception e) {
      fail();
    }

    assertArrayEquals(testLines.toArray(), actualLines.toArray());
  }

  /** Test that openFile throws an exception when a file cannot be found */
  @Test
  public void testOpenFileNotFound() {
    try {
      loader.openFile("..seng202_project/src/test/java/TestFiles/doesntExist.csv");
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  /**
   * Test that constructParser instantiates a parser of the correct datatype when called with a
   * valid datatype
   */
  @Test
  public void testConstructParserValid() {
    Parser testParser = loader.constructParser("Airport", testLines, false);
    assertTrue(testParser instanceof AirportParser);
  }

  /** Test that constructParser throws an exception when called with an invalid datatype */
  @Test
  public void testConstructParserInvalid() {
    try {
      loader.constructParser("plane", testLines, false);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Test that loadFile returns correct error message if called with an empty string for the
   * filename parameter
   */
  @Test
  public void testLoadFileEmptyFilename() {
    try {
      loader.loadFile("", "airport");
      fail();
    } catch (Exception e) {
      assertEquals("Filename cannot be empty.", e.getMessage());
    }
  }

  /**
   * Test that loadFile returns correct error message if called with an empty string for the
   * datatype parameter
   */
  @Test
  public void testLoadFileEmptyDatatype() {
    try {
      loader.loadFile("../seng202_project/src/test/java/TestFiles/airportsTest.csv", "");
    } catch (Exception e) {
      assertEquals("Datatype cannot be empty.", e.getMessage());
    }
  }

  /**
   * Test that expected data is stored in Storage when loadFile is called with valid input for
   * filename and datatype
   */
  @Test
  public void testLoadFileValid() {
    List<DataType> testRoutes = new ArrayList<>();
    testRoutes.add(new Route("2B", 410, "AER", 2965, "KZN", 2990, "", 0, "CR2".split(" ")));
    testRoutes.add(new Route("2B", 410, "ASF", 2966, "KZN", 2990, "", 0, "CR2".split(" ")));
    testRoutes.add(new Route("2B", 410, "ASF", 2966, "MRV", 2962, "", 0, "CR2".split(" ")));

    try {
      loader.loadFile("../seng202_project/src/test/java/TestFiles/routesTest.csv", "Route");
    } catch (Exception e) {
      fail();
    }

    assertArrayEquals(testRoutes.toArray(), storage.getRoutes().toArray());
  }

  /** Test that getLineFileName returns default name for route when current route file is null. */
  @Test
  public void testGetLineFileNameDefaultRoute() {
    String fileName = loader.getLineFileName("Route");
    assertEquals("singleEntryRoutes.csv", fileName);
  }

  /**
   * Test that getLineFileName returns the current filename for route when current route filename is
   * not null.
   */
  @Test
  public void testGetLineFileNameCurrentRoute() {
    storage.setData(new ArrayList<>(), "Route", "testRoutes.csv");
    assertEquals("testRoutes.csv", loader.getLineFileName("Route"));
  }

  /**
   * Test that getLineFileName returns default name for airport when current airport file is null.
   */
  @Test
  public void testGetLineFileNameDefaultAirport() {
    String fileName = loader.getLineFileName("Airport");
    assertEquals("singleEntryAirports.csv", fileName);
  }

  /**
   * Test that getLineFileName returns the current filename for airport when current airport
   * filename is not null.
   */
  @Test
  public void testGetLineFileNameCurrentAirport() {
    storage.setData(new ArrayList<>(), "Airport", "testAirports.csv");
    assertEquals("testAirports.csv", loader.getLineFileName("Airport"));
  }

  /**
   * Test that getLineFileName returns default name for airline when current airline file is null.
   */
  @Test
  public void testGetLineFileNameDefaultAirline() {
    String fileName = loader.getLineFileName("Airline");
    assertEquals("singleEntryAirlines.csv", fileName);
  }

  /**
   * Test that getLineFileName returns the current filename for airline when current airline
   * filename is not null.
   */
  @Test
  public void testGetLineFileNameCurrentAirline() {
    storage.setData(new ArrayList<>(), "Airline", "testAirlines.csv");
    assertEquals("testAirlines.csv", loader.getLineFileName("Airline"));
  }

  /**
   * Test that getLineFileName throws an illegal arguement exception when called with an illegal
   * datatype.
   */
  @Test
  public void testGetLineFileNameIllegalFileName() {
    try {
      loader.getLineFileName("Potato");
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Test that when loadLine is called with a valid route and there is a route file currently open
   * the route is added to that file.
   */
  @Test
  public void testLoadLineRouteAddedToExistingFile()
      throws FileNotFoundException, FileSystemException, SQLException {
    loader.loadFile("../seng202_project/src/test/java/TestFiles/singleRoute.csv", "Route");
    loader.loadLine("2G,1654,IKT,2937,YKS,2923,,0,A81", "Route");
    Route[] expected = new Route[2];
    expected[0] = new Route("2B", 410, "EGO", 6156, "KZN", 2990, "", 0, "CR2".split(" "));
    expected[1] = new Route("2G", 1654, "IKT", 2937, "YKS", 2923, "", 0, "A81".split(" "));
    assertArrayEquals(expected, storage.getRoutes().toArray());
  }

  /**
   * Test that when loadLine is called with a valid airline and there is an airline file currently
   * open the route is added to that file.
   */
  @Test
  public void testLoadLineAirlineAddedToExistingFile()
      throws FileNotFoundException, FileSystemException, SQLException {
    loader.loadFile("../seng202_project/src/test/java/TestFiles/singleairline.csv", "Airline");
    loader.loadLine(
        "2508,\"Galaxy Air\",\\N,\"7O\",\"GAL\",\"GALAXY\",\"Kyrgyzstan\",\"N\"", "Airline");
    Airline[] expected = new Airline[2];
    expected[0] =
        new Airline(
            6,
            "223 Flight Unit State Airline",
            "\\N",
            "",
            "CHD",
            "CHKALOVSK-AVIA",
            "Russia",
            false);
    expected[1] =
        new Airline(2508, "Galaxy Air", "\\N", "7O", "GAL", "GALAXY", "Kyrgyzstan", false);
    assertArrayEquals(expected, storage.getAirlines().toArray());
  }

  /**
   * Test that when loadLine is called with a valid airport and there is an airport file currently
   * open the route is added to that file.
   */
  @Test
  public void testLoadLineAirportAddedToExistingFile()
      throws FileNotFoundException, FileSystemException, SQLException {
    loader.loadFile("../seng202_project/src/test/java/TestFiles/singleairport.csv", "Airport");
    loader.loadLine(
        "7800,\"Essen HBF\",\"Essen\",\"Germany\",\"ESX\",\"ESSE\",51.451389,7.0138,1000,1,\"E\",\"Europe/Berlin\"",
        "Airport");
    Airport[] expected = new Airport[2];
    expected[0] =
        new Airport(
            14,
            "Husavik",
            "Husavik",
            "Iceland",
            "HZK",
            "BIHU",
            65.952328,
            -17.425978,
            48,
            0,
            "N",
            "Atlantic/Reykjavik");
    expected[1] =
        new Airport(
            7800,
            "Essen HBF",
            "Essen",
            "Germany",
            "ESX",
            "ESSE",
            51.451389,
            7.0138,
            1000,
            1,
            "E",
            "Europe/Berlin");
    assertArrayEquals(expected, storage.getAirports().toArray());
  }

  /**
   * Test that when loadLine is called with a valid airport and there is not current airport file
   * that a filename is created with the default name.
   */
  @Test
  public void testloadLineNewFileCreated() throws SQLException {
    loader.loadLine(
        "7800,\"Essen HBF\",\"Essen\",\"Germany\",\"ESX\",\"ESSE\",51.451389,7.0138,1000,1,\"E\",\"Europe/Berlin\"",
        "Airport");
    assertEquals("singleEntryAirports.csv", storage.getCurrentAirportFile());
  }

  /**
   * Test that when loadLine is called with a valid airline and there is not current airline file
   * that the airline is added to a new file with the default name.
   */
  @Test
  public void testloadLineAddToNewFile() throws SQLException {
    loader.loadLine(
        "2508,\"Galaxy Air\",\\N,\"7O\",\"GAL\",\"GALAXY\",\"Kyrgyzstan\",\"N\"", "Airline");
    Airline[] expected = new Airline[1];
    expected[0] =
        new Airline(2508, "Galaxy Air", "\\N", "7O", "GAL", "GALAXY", "Kyrgyzstan", false);
    assertArrayEquals(expected, storage.getAirlines().toArray());
  }

  /** Test that checkFile returns the expected error message when called with a valid file. */
  @Test
  public void testCheckFileValidErrorMessage() throws FileNotFoundException, FileSystemException {
    String message =
        loader.checkFile("../seng202_project/src/test/java/TestFiles/singleairline.csv", "Airline");
    assertEquals("File uploaded with 0 invalid lines rejected.\n", message);
  }

  /** Test that checkFile raises a runtime exception when called with an invalid file. */
  @Test
  public void testCheckFileInvalidErrorMessage() throws FileNotFoundException, FileSystemException {
    try {
      loader.checkFile(
          "../seng202_project/src/test/java/TestFiles/singleairlinewrong.csv", "Airline");
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /** Test that checkFile doesn't upload any data when called with a valid file. */
  @Test
  public void testCheckFileDoesntUploadData() throws FileNotFoundException, FileSystemException {
    loader.checkFile("../seng202_project/src/test/java/TestFiles/singleairline.csv", "Airline");
    assertTrue(storage.getAirlines().isEmpty());
  }
}
