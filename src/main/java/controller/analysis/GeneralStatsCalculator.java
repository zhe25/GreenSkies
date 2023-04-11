package controller.analysis;

import model.data.Route;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class contains the methods for calculating and updating the analysis data for the user's
 * carbon emissions report.
 *
 * @since 04/10/2020
 * @version 1.0
 */
public class GeneralStatsCalculator {
  /** This total distance the user has travelled via flying in km. */
  private double totalDistanceTravelled = 0.0;
  /** The total carbon emissions produced in g from the user's flight travel. */
  private double totalCarbonEmissions = 0.0;

  /**
   * The user's carbon emission goal which is the amount of carbon emissions they want to remain
   * below within the current year, in grams.
   */
  private double carbonEmissionGoal = 0.0;
  /** The rate of emissions produced so far in the current year. */
  private double emissionsPerDayBaseOnCurrDate;
  /**
   * The rate of emissions produced at the year in total if the user continues at their current
   * rate.
   */
  private double emissionsPerYear;
  /**
   * The amount of carbon emissions the user can produce whilst still achieving their carbon
   * emissions goal.
   */
  private double remainingCO2InYear;
  /** The percentage that the user needs to reduce their flight travel by to meet their goal. */
  public double reductionPercentage;
  /**
   * The amount the user needs to reduce their carbon emission production by via flight travel to
   * ensure their goal is met.
   */
  private double howMuchToReduceCO2By = 0.0;
  /** The number of trees the user would need to plant to counter their current carbon emissions. */
  private double treesToGrow = 0.0;
  /**
   * This method creates the comment of the user's carbon emission status in terms of their goal.
   */
  private String carbonEmissionsComment = "";
  /** The remaining days in the year. */
  private int remainingDaysInYear;
  /** The current day in the year. */
  private Integer dayInYear;
  /**
   * The maximum rate that carbon emissions can be produced by per day within the year whilst still
   * meeting the goal.
   */
  private double emissionsPerDayGoal;
  /** The current year; */
  private int currentYear;

  /**
   * This method adds the added route's carbon emissions to the total carbon emissions from flight
   * travel.
   *
   * @param currentRouteRecord The current route record that is being added to the user's flight
   *     history.
   */
  public void updateTotalEmissions(Route currentRouteRecord) {
    if (!Double.isNaN(currentRouteRecord.getEmissions())) {
      totalCarbonEmissions +=
          (currentRouteRecord.getEmissions() * currentRouteRecord.getTimesTaken());
    }
  }

  /**
   * This method adds the removes route's carbon emissions from the total carbon emissions from
   * flight travel.
   *
   * @param currentRouteRecord The current route record that is being removed for the user's flight
   *     history.
   */
  public void updateTotalEmissionsRemoval(Route currentRouteRecord) {
    if (!Double.isNaN(currentRouteRecord.getEmissions())) {
      totalCarbonEmissions -=
          (currentRouteRecord.getEmissions() * currentRouteRecord.getTimesTaken());
      if (totalCarbonEmissions < 0.00) {
        totalCarbonEmissions = 0.00;
      }
    }
  }

  /**
   * This method adds the removes route's distance the total distance travelled via flight travel.
   *
   * @param currentRouteRecord The current route record that is being removed from the user's flight
   *     history.
   */
  public void updateTotalDistance(Route currentRouteRecord) {
    totalDistanceTravelled +=
        (currentRouteRecord.getDistance() * currentRouteRecord.getTimesTaken());
  }

  /**
   * This method adds the added route's distance the total distance travelled via flight travel.
   *
   * @param currentRouteRecord The current route record that is being added to the user's flight
   *     history.
   */
  public void updateTotalDistanceRemoval(Route currentRouteRecord) {
    totalDistanceTravelled -=
        (currentRouteRecord.getDistance() * currentRouteRecord.getTimesTaken());
    if (totalDistanceTravelled < 0.00) {
      totalDistanceTravelled = 0.00;
    }
  }

  /** This method calculates the current day of the year and returns the integer of it. */
  public void calculateDateAsInt() {
    Date currDayInCurrYear = new Date();
    SimpleDateFormat dateForm = new SimpleDateFormat("D");
    String dayAsString = dateForm.format(currDayInCurrYear);
    Integer dayAsInt = Integer.valueOf(dayAsString);
    this.dayInYear = dayAsInt;
  }

  /** This method determines how many remaining days in the year there are. */
  public void calculateRemainingDaysInYear() {
    try {
      this.remainingDaysInYear = 365 - this.dayInYear;
      if (this.remainingDaysInYear <= 0) {
        throw new Exception("It is not possible to have a negative amount of days in the year.");
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * This method determines the amount of emissions per year based on the current rate of carbon
   * emissions produced at the current time of the year.
   */
  public void calculateEmissionsPerYearCurrentRate() {
    try {
      this.emissionsPerDayBaseOnCurrDate = getTotalCarbonEmissions() / this.dayInYear;
    } catch (ArithmeticException e) {
      System.out.println("Cannot divide the total amount of carbon emissions by zero days " + e);
    }
    this.emissionsPerYear = emissionsPerDayBaseOnCurrDate * 365;
  }

  /**
   * This method calculates the maximum rate that carbon emissions can be produced by per day within
   * the year whilst still meeting the goal.
   */
  public void calculateEmissionsPerYearGoalRate() {
    this.emissionsPerDayGoal = getCarbonEmissionGoal() / 365;
  }

  /**
   * This method calculates the carbon emissions production reduction percentage required to meet
   * the user's goal by the end of the year.
   */
  public void calculateReductionPercentage() {
    if (emissionsPerDayGoal > emissionsPerDayBaseOnCurrDate) {
      this.reductionPercentage = 0;
    } else {
      try {
        double negReductionDec = 1 - (emissionsPerDayBaseOnCurrDate / emissionsPerDayGoal);
        double posReductionPercentage = 100 - (negReductionDec * -100);
        this.reductionPercentage = posReductionPercentage;
      } catch (ArithmeticException e) {
        System.out.println(
            "Cannot divide the emissionsPerDayBaseOnCurrDate by a rate of zero carbon emissions per day "
                + e);
      }
    }
  }

  /**
   * This method gets the current year.
   *
   * @return The current year as an integer.
   */
  public int getCurrentYear() {
    Date currDayInCurrYear = new Date();
    SimpleDateFormat dateForm = new SimpleDateFormat("Y");
    String yearAsString = dateForm.format(currDayInCurrYear);
    Integer yearAsInt = Integer.valueOf(yearAsString);
    return yearAsInt;
  }

  /**
   * This method calculates the amount of CO2 that the user can produce whilst meeting their maximum
   * yearly CO2 production goal.
   */
  public void calculateRemainingCO2InYear() {
    this.remainingCO2InYear = this.carbonEmissionGoal - this.totalCarbonEmissions;
    if (remainingCO2InYear < 0) {
      this.remainingCO2InYear = 0;
    }
  }

  /**
   * This method calculates how many trees need to be planted to counter the carbon emissions
   * produced. It assumes that the trees planted have an approximate age of standing of at least 20
   * years and and that the age of stand when measured is also at least 20 years. Note that trees do
   * not sequester much carbon in the first few years after planting so the minimum standing age
   * must be no less than 20.
   */
  public void calculateOffsetTrees() {
    double CO2Tonnes =
        this.totalCarbonEmissions / 1000; // convert carbon emissions from grams to tonnes
    this.treesToGrow = (CO2Tonnes / 144.64) * 2500; // determine number of trees to offset emissions
  }

  /**
   * This method creates the comment of the user's carbon emission status in terms of their goal.
   */
  public void createCarbonEmissionsComment() {
    calculateDateAsInt();
    calculateRemainingDaysInYear();
    calculateEmissionsPerYearCurrentRate();
    calculateEmissionsPerYearGoalRate();
    calculateReductionPercentage();
    calculateRemainingCO2InYear();
    calculateOffsetTrees();

    String carbonEmissionsComment =
        "Currently, in "
            + getCurrentYear()
            + ", you are producing "
            + String.format("%.2f", getEmissionsPerDayBaseOnCurrDate())
            + " kg of carbon emissions per day from your \nflight travel."
            + " If you continue at this rate, you will produce "
            + String.format("%.2f", getEmissionsPerYear())
            + " kg by the end of this year \nfrom flight travel. ";

    if (getReductionPercentage() == 0.00) {
      carbonEmissionsComment += "This means that you will be below your carbon emissions goal.";
    } else if (getRemainingCO2InYear() == 0.00) {
      carbonEmissionsComment +=
          "This means you have breached your goal and should not produce any \nmore carbon emissions"
              + " in the remaining part of this year. To ensure you stay under\n your goal in "
              + (getCurrentYear() + 1)
              + ", you will need to reduce your flight travel by "
              + String.format("%.2f", (-1 * getReductionPercentage()))
              + " percent.";

    } else {
      carbonEmissionsComment +=
          "This means you can only produce "
              + String.format("%.2f", getRemainingCO2InYear())
              + " kg in the remaining part of this year.\n To ensure you stay under your goal, you will need to reduce your flight travel by "
              + String.format("%.2f", getReductionPercentage())
              + " percent.";
    }
    setCarbonEmissionsComment(carbonEmissionsComment);
  }

  /**
   * This function implements the binary search algorithm.
   *
   * @param arrayToSearch The array which is being searched.
   * @param searchElement The element that is being search for.
   * @param type The type of data used for comparison e.g. times a route is taken, a route's
   *     emissions, a route's distance.
   * @return
   */
  public static int binarySearch(List<Route> arrayToSearch, double searchElement, String type) {

    int firstIndex = 0;
    int lastIndex = arrayToSearch.size() - 1;
    while (firstIndex <= lastIndex) {
      int middleIndex = (firstIndex + lastIndex) / 2;
      double middleIndexValue = 0;

      if (type == "times taken") {
        middleIndexValue = (int) middleIndexValue;
        searchElement = (int) searchElement;
      } else if (type == "distance" | type == "emissions") {
        middleIndexValue = 0;
      }

      if (type == "times taken") {
        middleIndexValue = getMiddleIndexTimesTaken(arrayToSearch, middleIndex);
        searchElement = (int) searchElement;
      } else if (type == "distance") {
        middleIndexValue = getMiddleIndexDistance(arrayToSearch, middleIndex);
      } else if (type == "emissions") {
        middleIndexValue = getMiddleIndexEmissions(arrayToSearch, middleIndex);
      }

      if (middleIndexValue == searchElement) {
        return middleIndex;
      } else if (middleIndexValue < searchElement) firstIndex = middleIndex + 1;
      else if (middleIndexValue > searchElement) lastIndex = middleIndex - 1;
    }
    return -1;
  }

  /**
   * This method gets the times taken of the route at the middle index to be used in binarySearch
   *
   * @return The times the route has been taken.
   */
  public static int getMiddleIndexTimesTaken(List<Route> arrayToSearch, int middleIndex) {
    return arrayToSearch.get(middleIndex).getTimesTaken();
  }

  /**
   * This method gets the emissions of the route at the middle index to be used in binarySearch
   *
   * @return The emissions of the route.
   */
  public static double getMiddleIndexEmissions(List<Route> arrayToSearch, int middleIndex) {
    return arrayToSearch.get(middleIndex).getEmissions();
  }

  /**
   * This method gets the distance of the route at the middle index to be used in binarySearch
   *
   * @return The distance of the route.
   */
  public static double getMiddleIndexDistance(List<Route> arrayToSearch, int middleIndex) {
    return arrayToSearch.get(middleIndex).getDistance();
  }

  /**
   * This method sets up the quick sort algorithm.
   *
   * @param arrayToSort The array which needs to be sorted.
   * @param start The starting index of the arrayToSort.
   * @param end The ending index of the arrayToSort.
   * @param type The type of data used for comparison.
   */
  public static void quickSort(List<Route> arrayToSort, int start, int end, String type) {
    if (end <= start) return;
    int pivot = quickSortPartition(arrayToSort, start, end, type);
    quickSort(arrayToSort, start, pivot - 1, type);
    quickSort(arrayToSort, pivot + 1, end, type);
  }

  /**
   * This function implements the main logic of the quick sort algorithm.
   *
   * @param arrayToSort The array which needs to be sorted.
   * @param start The starting index of the arrayToSort.
   * @param end The ending index of the arrayToSort.
   * @param type The type of data used for comparison.
   * @return
   */
  public static int quickSortPartition(List<Route> arrayToSort, int start, int end, String type) {
    int pivot = end;
    int counter = start;
    for (int i = start; i < end; i++) {
      int isIndexValLessThanPivotVal = 0;
      if (type == "distance") {
        isIndexValLessThanPivotVal = checkIndexValLessThanPivotValDistance(arrayToSort, i, pivot);
      } else if (type == "emissions") {
        isIndexValLessThanPivotVal = checkIndexValLessThanPivotValEmissions(arrayToSort, i, pivot);
      } else if (type == "times taken") {
        isIndexValLessThanPivotVal = checkIndexValLessThanPivotValTimesTaken(arrayToSort, i, pivot);
      }
      if (isIndexValLessThanPivotVal == 1) {
        Route temp = arrayToSort.get(counter);
        arrayToSort.set(counter, arrayToSort.get(i));
        arrayToSort.set(i, temp);
        counter++;
      }
    }
    Route temp = arrayToSort.get(pivot);
    arrayToSort.set(pivot, arrayToSort.get(counter));
    arrayToSort.set(counter, temp);
    return counter;
  }

  /**
   * This method checks whether the distance of the route at the given index is less than the
   * distance of the route at the pivot.
   *
   * @param arrayToSort The array to sort based on the comparison of emissions produced.
   * @param i The current index in the search array.
   * @param pivot The current pivot in the search array.
   * @return 1 is the comparison is true, 0 is it is false
   */
  public static int checkIndexValLessThanPivotValDistance(
      List<Route> arrayToSort, int i, int pivot) {
    if (arrayToSort.get(i).getDistance() < arrayToSort.get(pivot).getDistance()) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * This method checks whether the emissions of the route at the given index is less than the
   * emissions of the route at the pivot.
   *
   * @param arrayToSort The array to sort based on the comparison of emissions produced.
   * @param i The current index in the search array.
   * @param pivot The current pivot in the search array.
   * @return 1 is the comparison is true, 0 is it is false
   */
  public static int checkIndexValLessThanPivotValEmissions(
      List<Route> arrayToSort, int i, int pivot) {
    if (arrayToSort.get(i).getEmissions() < arrayToSort.get(pivot).getEmissions()) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * This method checks whether the times taken of the route at the given index is less than the
   * times taken of the route at the pivot.
   *
   * @param arrayToSort The array to sort based on the comparison of times a route has been taken.
   * @param i The current index in the search array.
   * @param pivot The current pivot in the search array.
   * @return 1 is the comparison is true, 0 is it is false
   */
  public static int checkIndexValLessThanPivotValTimesTaken(
      List<Route> arrayToSort, int i, int pivot) {
    if (arrayToSort.get(i).getTimesTaken() < arrayToSort.get(pivot).getTimesTaken()) {
      return 1;
    } else {
      return 0;
    }
  }

  public void setCarbonEmissionsGoal(double carbonEmissionGoal) {
    this.carbonEmissionGoal = carbonEmissionGoal;
  }

  public double getCarbonEmissionGoal() {
    return carbonEmissionGoal;
  }

  public double getTotalDistanceTravelled() {
    return totalDistanceTravelled;
  }

  public double getTotalCarbonEmissions() {
    return totalCarbonEmissions;
  }

  public void setTotalCarbonEmissions(double totalCarbonEmissions) {
    this.totalCarbonEmissions = totalCarbonEmissions;
  }

  public void setTotalDistanceTravelled(double totalDistanceTravelled) {
    this.totalDistanceTravelled = totalDistanceTravelled;
  }

  public double getEmissionsPerDayBaseOnCurrDate() {
    return this.emissionsPerDayBaseOnCurrDate;
  }

  public double getEmissionsPerYear() {
    return this.emissionsPerYear;
  }

  public double getRemainingCO2InYear() {
    return this.remainingCO2InYear;
  }

  public double getReductionPercentage() {
    return this.reductionPercentage;
  }

  public int getRemainingDaysInYear() {
    return remainingDaysInYear;
  }

  public double getTreesToGrow() {
    return this.treesToGrow;
  }

  public String getCarbonEmissionsComment() {
    return carbonEmissionsComment;
  }

  public void setCarbonEmissionsComment(String comment) {
    this.carbonEmissionsComment = comment;
  }

  public void setDayInYear(int day) {
    this.dayInYear = day;
  }

  public int getDayInYear() {
    return dayInYear;
  }

  public void setEmissionsPerDayBaseOnCurrDate(int rate) {
    this.emissionsPerDayBaseOnCurrDate = rate;
  }

  public double getEmissionsPerDayGoal() {
    return this.emissionsPerDayGoal;
  }

  public void setEmissionsPerDayGoal(int rate) {
    this.emissionsPerDayGoal = rate;
  }

  public void setCurrentYear(int year) {
    this.currentYear = year;
  }

  public void setEmissionsPerYear(double rate) {
    this.emissionsPerYear = rate;
  }

  public void setRemainingCO2InYear(int amount) {
    this.remainingCO2InYear = amount;
  }

  public void setReductionPercentage(int percentage) {
    this.reductionPercentage = percentage;
  }
}
