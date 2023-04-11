package controller.guiController.upload;

import controller.guiController.AlertPopUp;
import controller.guiController.GreenSkiesApplication;
import controller.guiController.Main;
import controller.guiController.SideNavBarController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.data.DataType;
import model.data.Route;
import model.data.Storage;
import model.database.SQLiteDatabase;
import model.loader.Loader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller class which contains the controls for the upload data view.
 *
 * @version 1.1
 * @since 04/10/2020
 */
public class UploadController extends SideNavBarController {

    private final Storage storage = Main.getStorage();
    private final Loader loader = Main.getLoader();

    private String currentAirlineFile;

    private String currentAirportFile;

    private String currentRouteFile;

    private SQLiteDatabase database = new SQLiteDatabase();

    /**Iniitialize the list of data types to be added to the ChoiceBox 'dataTypeSelect'*/
    ObservableList<String> dataTypeList =
            FXCollections.observableArrayList("Airport", "Route", "Airline");

    @FXML
    public ListView<String> airlineFileList;
    @FXML
    private ListView<String> airportFileList;
    @FXML
    private ListView<String> routeFileList;
    @FXML
    private ChoiceBox dataTypeSelect;
    @FXML
    private Button backButton;

    /**
     * This method adds the data types from dataTypeList to the dataTypeSelect list. Must have
     * implementation of this method as superclass implements the Initializable interface.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataTypeSelect.getItems().addAll(dataTypeList);

        airlineFileList.setItems(FXCollections.observableList(storage.getAirlineFileNames()));
        airportFileList.setItems(FXCollections.observableList(storage.getAirportFileNames()));
        routeFileList.setItems(FXCollections.observableList(storage.getRouteFileNames()));

        airlineFileList.getSelectionModel().select(storage.getCurrentAirlineFile());
        airportFileList.getSelectionModel().select(storage.getCurrentAirportFile());
        routeFileList.getSelectionModel().select(storage.getCurrentRouteFile());
    }

    /**
     * This method returns a boolean value based on whether the user has selected a data type or not
     */
    public boolean checkDataTypeSelected() {

        if ((dataTypeSelect.getValue()) == null) {
            Alert ErrorAlert = new Alert(Alert.AlertType.NONE);
            ErrorAlert.setAlertType(Alert.AlertType.ERROR);
            ErrorAlert.setContentText("You must select the type of data that you are going to add");
            ErrorAlert.show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method opens the user's file browser when the 'browse' button is clicked.
     */
    public void browseFiles() {

        if (checkDataTypeSelected()) {

            FileChooser fileChooser = new FileChooser(); // opens a file local file browser
            File selectedFile = fileChooser.showOpenDialog(null);

            try {
                String fileType = dataTypeSelect.getValue().toString();
                String stringFile;
                stringFile = selectedFile.toString();

                String resultString = loader.checkFile(stringFile, fileType);
                Alert ConfirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                ConfirmAlert.setContentText(resultString);
                ConfirmAlert.setHeaderText("Confirm file upload");
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType cancelButton = new ButtonType("Cancel");
                ConfirmAlert.getButtonTypes().setAll(yesButton, cancelButton);

                Optional<ButtonType> result = ConfirmAlert.showAndWait();
                if (result.get() == yesButton) {
                    loader.loadFile(stringFile, fileType);
                    storage.updateDatabase(fileType);
                    switch (fileType) {
                        case "Airport":
                            airportFileList.setItems(FXCollections.observableList(storage.getAirportFileNames()));
                            airportFileList.getSelectionModel().select(storage.getCurrentAirportFile());
                            break;
                        case "Airline":
                            airlineFileList.setItems(FXCollections.observableList(storage.getAirlineFileNames()));
                            airlineFileList.getSelectionModel().select(storage.getCurrentAirlineFile());
                            break;
                        case "Route":
                            routeFileList.setItems(FXCollections.observableList(storage.getRouteFileNames()));
                            routeFileList.getSelectionModel().select(storage.getCurrentRouteFile());
                            break;
                    }
                    ConfirmAlert.close();
                    /**only load data if it is not erroneous*/
                    for (DataType line : storage.getRoutes()) {
                        Route test = (Route) line;
                    }
                } else if (result.get() == cancelButton) {
                    ConfirmAlert.close();
                }
            }
            catch (Exception e) {
                if (e.getMessage() != null) {
                    Alert ErrorAlert = new Alert(Alert.AlertType.NONE);
                    ErrorAlert.setAlertType(Alert.AlertType.ERROR);
                    ErrorAlert.setContentText(e.getMessage());
                    ErrorAlert.show();
                }
            }
        }
        //
    }

    /**
     * This method opens a screen for manual data entry of a single item when the 'Add single entry'
     * button is clicked.
     *
     * @throws IOException This throws an IOException.
     */
    public void addSingle() throws IOException {

        if (checkDataTypeSelected()) {
            String fileType = dataTypeSelect.getValue().toString();
            switch (fileType) {
                case "Airport": {
                    Stage newStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/view/upload/airportSingleEntry.fxml"));
                    Scene scene = new Scene(root);
                    newStage.setScene(scene);
                    newStage.showAndWait();
                    airportFileList.setItems(FXCollections.observableList(storage.getAirportFileNames()));
                    airportFileList.getSelectionModel().select(storage.getCurrentAirportFile());
                    break;
                }
                case "Airline": {
                    Stage newStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/view/upload/airlineSingleEntry.fxml"));
                    Scene scene = new Scene(root);
                    newStage.setScene(scene);
                    newStage.showAndWait();
                    airlineFileList.setItems(FXCollections.observableList(storage.getAirlineFileNames()));
                    airlineFileList.getSelectionModel().select(storage.getCurrentAirlineFile());
                    break;
                }
                case "Route": {
                    Stage newStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/view/upload/routeSingleEntry.fxml"));
                    Scene scene = new Scene(root);
                    newStage.setScene(scene);
                    newStage.showAndWait();
                    routeFileList.setItems(FXCollections.observableList(storage.getRouteFileNames()));
                    routeFileList.getSelectionModel().select(storage.getCurrentRouteFile());
                    break;
                }
            }
        }
    }

  /**
   * This method closes the Upload Data page and opens the Welcome page.
   *
   * @throws IOException This throws an IOException.
   */
  public void backToWelcome() throws IOException {
    Stage newStage = GreenSkiesApplication.getPrimaryStage();
    Parent root =
        FXMLLoader.load(getClass().getResource("/view/welcome.fxml")); // open the Welcome page
    Scene scene = new Scene(root);
    newStage.setScene(scene);
  }

    /**
     * This method changes the currentAirlinefile to the select airline file name.
     */
    public void changeAirlineFile() {
        currentAirlineFile = airlineFileList.getSelectionModel().getSelectedItem();
        checkState("Airline");
        storage.setCurrentAirlineFile(airlineFileList.getSelectionModel().getSelectedItem());
    }


    /**
     * This method deletes selected file and raises alertBox.
     */
    @FXML
    public void deleteFile() {
        if (currentAirlineFile != null) {
            Optional<ButtonType> result = AlertPopUp.showDeleteAlert("airline file");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                storage.getAirlineFileList().remove(currentAirlineFile);
                database.deleteFile(currentAirlineFile, "Airline");
                storage.setCurrentAirlineFile(null);
                airlineFileList.setItems(FXCollections.observableList(storage.getAirlineFileNames()));
            }
        }  if (currentAirportFile != null) {
            Optional<ButtonType> result = AlertPopUp.showDeleteAlert("airport file");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                storage.getAirportFileList().remove(currentAirportFile);
                database.deleteFile(currentAirportFile, "Airport");
                storage.setCurrentAirportFile(null);
                airportFileList.setItems(FXCollections.observableList(storage.getAirportFileNames()));
            }
        } if (currentRouteFile != null) {
            Optional<ButtonType> result = AlertPopUp.showDeleteAlert("route file");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                storage.getRouteFileList().remove(currentRouteFile);
                database.deleteFile(currentRouteFile, "Route");
                storage.setCurrentRouteFile(null);
                routeFileList.setItems(FXCollections.observableList(storage.getRouteFileNames()));
            }
        }

    }

    /**
     * This method changes the currentAirportfile to the select airport file name.
     */
    public void changeAirportFile() {
        currentAirportFile = airportFileList.getSelectionModel().getSelectedItem();
        checkState("Airport");
        storage.setCurrentAirportFile(airportFileList.getSelectionModel().getSelectedItem());
    }

    /**
     * This method changes the currentRoutefile to the select Route file name.
     */
    public void changeRouteFile() {
        currentRouteFile = routeFileList.getSelectionModel().getSelectedItem();
        checkState("Route");
        storage.setCurrentRouteFile(routeFileList.getSelectionModel().getSelectedItem());

    }

    /**
     * This method checks the type of file user wish to delete.
     * @param fileType The type of file the user has selected.
     */
    private void checkState(String fileType) {
        if(fileType == "Airport") {
            currentAirlineFile = null;
            currentRouteFile = null;
        }
         else if(fileType == "Airline") {
            currentAirportFile = null;
            currentRouteFile = null;
        }
        else if(fileType == "Route") {
            currentAirportFile = null;
            currentAirlineFile = null;
        }
    }

}
