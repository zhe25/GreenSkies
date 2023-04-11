package controller.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the methods relating to the report's calculations corresponding to airports.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public class AirportStatsCalculator extends GeneralStatsCalculator {

  private ArrayList<String> mostVisitedSrcAirports = new ArrayList<>();
  /** The destination airports which were the most visited. */
  private ArrayList<String> mostVisitedDestAirports = new ArrayList<>();
  /** The routes which were most travelled. */
  private ArrayList<String> leastVisitedSrcAirports = new ArrayList<>();
  /** The destination airports which were the most visited. */
  private ArrayList<String> leastVisitedDestAirports = new ArrayList<>();

  /**
   * Calculates the source airport(s) that was the most visited, based on the user's flight history
   * entries.
   *
   * @param srcAirportCounts A count of the number of times each source airport has been visited
   *     with the name of the airport as the key.
   */
  public void updateMostVisitedSrcAirports(HashMap<String, Integer> srcAirportCounts) {
    int currSrcAirportMax = 0;
    if (leastVisitedSrcAirports.isEmpty() == true & srcAirportCounts.isEmpty() == false) {
      currSrcAirportMax = (Integer) srcAirportCounts.values().toArray()[0];
    }
    for (Map.Entry<String, Integer> entry : srcAirportCounts.entrySet()) {
      if (entry.getValue() > currSrcAirportMax) {
        currSrcAirportMax = entry.getValue();
        mostVisitedSrcAirports.clear();
        mostVisitedSrcAirports.add(entry.getKey());
      } else if (entry.getValue() == currSrcAirportMax) {
        currSrcAirportMax = entry.getValue();
        if (!mostVisitedSrcAirports.contains(entry.getKey())) {
          mostVisitedSrcAirports.add(entry.getKey());
        }
      }
    }
  }

  /**
   * Calculates the destination airport(s) that was the most visited, based on the user's flight
   * history entries.
   *
   * @param destAirportCounts A count of how many times each destination airport has been visited
   *     with the names as the key.
   */
  public void updateMostVisitedDestAirports(HashMap<String, Integer> destAirportCounts) {
    int currDestAirportMax = 0;
    if (leastVisitedSrcAirports.isEmpty() == true & destAirportCounts.isEmpty() == false) {
      currDestAirportMax = (Integer) destAirportCounts.values().toArray()[0];
    }
    for (Map.Entry<String, Integer> entry : destAirportCounts.entrySet()) {
      if (entry.getValue() > currDestAirportMax) {
        currDestAirportMax = entry.getValue();
        mostVisitedDestAirports.clear();
        mostVisitedDestAirports.add(entry.getKey());
      } else if (entry.getValue() == currDestAirportMax) {
        currDestAirportMax = entry.getValue();
        if (!mostVisitedDestAirports.contains(entry.getKey())) {
          mostVisitedDestAirports.add(entry.getKey());
        }
      }
    }
  }

  /**
   * Calculates the source airport(s) that was the least visited, based on the user's flight history
   * entries.
   *
   * @param srcAirportCounts A count of how many times each airport has been visited with the name
   *     of the airport as the key.
   */
  public void updateLeastVisitedSrcAirports(HashMap<String, Integer> srcAirportCounts) {
    int currSrcAirportMin = 0;
    if (leastVisitedSrcAirports.isEmpty() == true && srcAirportCounts.isEmpty() == false) {
      currSrcAirportMin = (Integer) srcAirportCounts.values().toArray()[0];
    }
    for (Map.Entry<String, Integer> entry : srcAirportCounts.entrySet()) {
      if (entry.getValue() < currSrcAirportMin) {
        currSrcAirportMin = entry.getValue();
        leastVisitedSrcAirports.clear();
        leastVisitedSrcAirports.add(entry.getKey());
      } else if (entry.getValue() == currSrcAirportMin) {
        currSrcAirportMin = entry.getValue();
        if (!leastVisitedSrcAirports.contains(entry.getKey())) {
          leastVisitedSrcAirports.add(entry.getKey());
        }
      }
    }
  }

  /**
   * Calculates the destination airport(s) that was the least visited, based on the user's flight
   * history entries.
   *
   * @param destAirportCounts A count of how many times each destination airport has been visited
   *     with the name of the airport as the key.
   */
  public void updateLeastVisitedDestAirports(HashMap<String, Integer> destAirportCounts) {
    int currDestAirportMin = 0;
    if (leastVisitedSrcAirports.isEmpty() == true & destAirportCounts.isEmpty() == false) {
      currDestAirportMin = (Integer) destAirportCounts.values().toArray()[0];
    }
    for (Map.Entry<String, Integer> entry : destAirportCounts.entrySet()) {
      if (entry.getValue() < currDestAirportMin) {
        currDestAirportMin = entry.getValue();
        leastVisitedDestAirports.clear();
        leastVisitedDestAirports.add(entry.getKey());
      } else if (entry.getValue() == currDestAirportMin) {
        currDestAirportMin = entry.getValue();
        if (!leastVisitedDestAirports.contains(entry.getKey())) {
          leastVisitedDestAirports.add(entry.getKey());
        }
      }
    }
  }

  /** This method empties all the airport arrays. */
  public void resetAirportArrays() {
    mostVisitedDestAirports.clear();
    mostVisitedSrcAirports.clear();
    leastVisitedSrcAirports.clear();
    leastVisitedDestAirports.clear();
  }

  /**
   * This methods returns an array of the most visited source airports.
   *
   * @return mostVisitedSrcAirports The most visited source airports.
   */
  public ArrayList<String> getMostVisitedSrcAirports() {
    return mostVisitedSrcAirports;
  }

  /**
   * This methods returns an array of the most visited destination airports.
   *
   * @return mostVisitedDestAirports The most visited destination airports.
   */
  public ArrayList<String> getMostVisitedDestAirports() {
    return mostVisitedDestAirports;
  }

  /**
   * This methods returns an array of the least visited source airports.
   *
   * @return leastVisitedSrcAirports The least visited source airports.
   */
  public ArrayList<String> getLeastVisitedSrcAirports() {
    return leastVisitedSrcAirports;
  }

  /**
   * This methods returns an array of the least visited destination airports.
   *
   * @return leastVisitedDestAirports The least visited destination airports.
   */
  public ArrayList<String> getLeastVisitedDestAirports() {
    return leastVisitedDestAirports;
  }
}
