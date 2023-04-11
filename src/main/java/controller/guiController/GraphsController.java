package controller.guiController;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import model.data.Route;
import java.net.URL;
import java.util.*;

/**
 * The controller class which contains the controls for the graphs page.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class GraphsController extends SideNavBarController {

  @FXML NumberAxis yAxis;
  @FXML CategoryAxis xAxis;
  @FXML BarChart<String, Number> barChart;
  @FXML Text warningText;

  List<Route> routes;
  private double NaN;

  /**
   * This method is the initializer for this class. Displays graph of routes from history
   *
   * @param url The provided resource bundle.
   * @param resourceBundle The resource bundle.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    XYChart.Series<String, Number> data = new XYChart.Series();
    routes = storage.getHistory();
    if (routes.size() == 0) {
      warningText.setVisible(true);
    } else {
      if (routes.size() <= 10) {
        for (Route route : routes) {
          String axisString = createAxisString(route);
          double emissions = route.getEmissions();
          data.getData().add(new XYChart.Data(axisString, emissions));
        }
      } else {
        ArrayList<Route> sortedByEmissions = new ArrayList<Route>(routes);
        Collections.sort(
            sortedByEmissions,
            new Comparator<Route>() {
              @Override
              public int compare(Route o1, Route o2) {
                return Double.valueOf(o2.getEmissions()).compareTo(o1.getEmissions());
              }
            });

        int i = 0;
        int limit = 10;
        while (i < limit) {
          if (!Double.isNaN(sortedByEmissions.get(i).getEmissions())) {
            String axisString = createAxisString(sortedByEmissions.get(i));
            double emissions = sortedByEmissions.get(i).getEmissions();
            data.getData().add(new XYChart.Data(axisString, emissions));
            i += 1;
          } else {
            limit += 1;
            i += 1;
          }
        }
      }
      barChart.getData().add(data);
    }
  }

  /** This method creates the string to be placed on the x axis for each route */
  public String createAxisString(Route route) {
    String dest = route.getDestinationAirport();
    String src = route.getSourceAirport();
    double distance = route.getDistance();
    String axisString = String.format("%s - %s\n(%.0f km)", src, dest, distance);
    return axisString;
  }
}
