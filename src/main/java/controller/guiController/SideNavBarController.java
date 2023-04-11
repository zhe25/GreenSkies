package controller.guiController;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.data.Storage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Superclass for all data view pages. All such pages contain a table of data, tools for searching
 * and filtering that data, and a navigation bar which can be used to navigate to other pages.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public abstract class SideNavBarController implements Initializable {

  @FXML protected Button btnUpload;
  @FXML protected Button btnRouteDataView;
  @FXML protected Button btnAirportDataView;
  @FXML protected Button btnAirlineDataView;
  @FXML protected Button btnFlightHistory;
  @FXML protected Button btnCarbonEmissionsReport;
  @FXML protected Button btnMapOfAirports;
  @FXML protected Button btnGraphs;
  @FXML protected Button btnAnalyseFlight;

  /** The search types. */
  protected ObservableList<String> searchTypes;
  /** The storage for the application. */
  protected final Storage storage = Main.getStorage();

  /**
   * This method initializes the class.
   *
   * @param url The provided url.
   * @param rb The provided resource bundle.
   */
  public abstract void initialize(URL url, ResourceBundle rb);

  /**
   * This method closes the current page and opens the Upload Data page.
   *
   * @throws IOException
   */
  public void toUploadData() throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/upload/upload.fxml"));
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }

  /**
   * This method closes the current page and opens the View Route Data page.
   *
   * @throws IOException
   */
  public void toRouteDataView() throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/dataview/viewRouteData.fxml"));
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }

  /**
   * This method closes the current page and opens the View Airport Data page.
   *
   * @throws IOException
   */
  public void toAirportDataView() throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/dataview/viewAirportData.fxml"));
    // open the View Airport Data page
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }

  /**
   * This method closes the current page and opens the View Airline Data page.
   *
   * @throws IOException
   */
  public void toAirlineDataView() throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/dataview/viewAirlineData.fxml"));
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }

  /**
   * This method closes the current page and opens the Flight History page.
   *
   * @throws IOException
   */
  public void toFlightHistory() throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/dataview/flightHistory.fxml"));
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }

  /**
   * This method closes the current page and opens the Carbon Emissions Report page.
   *
   * @throws IOException
   */
  public void toCarbonEmissionsReport() throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/carbonEmissionsReport.fxml"));
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }

  /**
   * This method closes the current page and opens the Map of Airports page.
   *
   * @throws IOException
   */
  public void toMapOfAirports() throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/mapOfRoutes.fxml"));
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }

  /**
   * This method closes the current page and opens the Graphs page.
   *
   * @throws IOException
   */
  public void toGraphs() throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/graphs.fxml"));
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }

  /**
   * This method closes the current page and opens the Flight Analyser page.
   *
   * @throws IOException
   */
  public void toAnalyseFlight() throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/analyse.fxml"));
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }
}
