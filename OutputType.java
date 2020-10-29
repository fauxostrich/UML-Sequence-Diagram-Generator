package com.beulahworks.SDMfileGenerator;

/**
 * Contains extension and type information used by an OutputAdapter for 
 * generating a file of a certain type.
 * <br>
 * <br>
 * <b>Traceability:</b> Method interfaces are given in Section 5.2 of the SDD.
 * <br>
 * <b>Traceability:</b> Class design is given in Sections 6.1.2 and 6.2.2 of the SDD.
 * 
 * @author Jesse Primiani
 */
public abstract class OutputType <TypeInfo> {

  /**
   * Gets the file extension string of this class’ concrete implementation.
   * <br>
   * <br>
   * <b>Preconditions:</b> None 
   * <br>
   * <b>Postconditions:</b> Returns a string containing the extension part of a file name.
   * 
   * @author Jesse Primiani
   * @return The file extension String.
   */
  public abstract String getExtension();
  
  /**
   * Gets the file type data structure of this class’ concrete implementation.
   * <br>
   * <br>
   * <b>Preconditions:</b> None 
   * <br>
   * <b>Postconditions:</b> Returns a TypeInfo object using Java generics, used by the 
   * output adapter to format the file.
   * 
   * @author Jesse Primiani
   * @return The file formatting data structure used by the output library.
   */
  public abstract TypeInfo getType();

}
