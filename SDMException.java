package com.beulahworks.SDMfileGenerator;

/**
 * The exception thrown by all methods in this package, usually used to 
 * indicate an adapter conversion or file exporting error.
 * <br>
 * <br>
 * <b>Traceability:</b> Method interfaces are given in Section 5.2 of the SDD.
 * <br>
 * <b>Traceability:</b> Class design is given in Sections 6.1.8 and 6.2.8 of the SDD.
 * 
 * @author Jesse Primiani
 */
public class SDMException extends Exception {

  private static final long serialVersionUID = 1L;
  
  SDMException() {
    super();
  }
  
  SDMException(String message) {
    super(message);
  }

}
