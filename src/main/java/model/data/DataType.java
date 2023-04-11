package model.data;

/**
 * The Datatype interface allows Airline, Airport and Route to be cast as the same type so that the
 * same methods can be used to manipulate all three of these classes.
 *
 * @version 1.0
 * @since 04/10/2020
 */
public interface DataType {

  @Override
  boolean equals(Object o);
}
