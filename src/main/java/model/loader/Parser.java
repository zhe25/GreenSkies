package model.loader;

import model.data.DataType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The super class of whole sub_parser classes
 *
 * @version 1.0
 * @since 04/10/2020
 */
public abstract class Parser {

  /** The database file. */
  protected final List<String> dataFile;

  /** Stores int key for error code and int value for error quantity. */
  protected Map<Integer, Integer> errorCollection = new HashMap<>();

  /** The number of error codes for each parser type. */
  protected int numCodes;

  /**
   * Arraylist of meaning of error codes for Parser, where each index corresponds to an error code.
   */
  protected String[] errorLookup;

  /** The total number of errors found while parsing the file. */
  protected int totalErrors = 0;

  /** The set contains airport, Airline, route for each sub-parser. */
  protected List<DataType> parserData = new ArrayList<>();

  /**
   * Constructor of Parser class.
   *
   * @param dataFile passed from loader, contains all data from datafile, one line per element in
   *     the list.
   */
  public Parser(List<String> dataFile, int numCodes) {
    this.dataFile = dataFile;
    this.numCodes = numCodes;
    errorCollectionInitializer(numCodes);
    errorLookup = new String[numCodes];
    initErrorLookup();
  }

  /** Initialize errorLookup with message for each error code */
  protected abstract void initErrorLookup();

  /** Abstract class of dataParser. */
  protected abstract void dataParser();

  /** Abstract class of validater. */
  protected abstract boolean validater(String[] line);

  /** Getter returning processed data result for all sub-parsers. */
  public List<DataType> getData() {
    return parserData;
  }

  /**
   * Initialize error code key in errorCollection.
   *
   * @param errorCodeNum number of error code that are expected to be generated in hashmap1
   */
  protected void errorCollectionInitializer(int errorCodeNum) {
    for (int i = 0; i < errorCodeNum; i++) {
      errorCollection.put(i, 0);
    }
  }

  /**
   * Count quantity of occurrences for same error. Will plus one on value to given error code.
   *
   * @param key error code initialized before use.
   */
  protected void errorCounter(int key) {
    try {
      errorCollection.put(key, errorCollection.get(key) + 1);
      totalErrors++;
    } catch (NullPointerException ignored) {
    }
  }

  /**
   * This method creates and return a message detailing the errors found in the file.
   *
   * @return String with information about error types in file.
   */
  public String getErrorMessage(boolean uploadSuccess) {
    StringBuilder errorMessage = new StringBuilder();
    if (uploadSuccess) {
      errorMessage.append(
          String.format("File uploaded with %d invalid lines rejected.\n", totalErrors));
    }
    String template = "Error [%d] %s: %d occurrences\n";
    for (int i = 0; i < numCodes; i++) {
      if (errorCollection.get(i) > 0) {
        errorMessage.append(String.format(template, i, errorLookup[i], errorCollection.get(i)));
      }
    }
    return errorMessage.toString();
  }

  /**
   * Checks if the number of errors in the file is less than the size of the file.
   *
   * @return true if there are less errors than lines in the file, false otherwise.
   */
  public boolean getValidFile() {
    return (totalErrors < dataFile.size());
  }
}
