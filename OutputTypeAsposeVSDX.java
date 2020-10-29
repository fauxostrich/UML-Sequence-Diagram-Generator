package com.beulahworks.SDMfileGenerator;

import com.aspose.diagram.SaveFileFormat;

/**
 * Sets the extension and type for exporting a Visio VSDX file.
 * Used by OutputAspose to determine which file type to export.
 * <br>
 * <br>
 * <b>Traceability:</b> Method interfaces are given in Section 5.2 of the SDD.
 * <br>
 * <b>Traceability:</b> Class design is given in Sections 6.1.3 and 6.2.3 of the SDD.
 * 
 * @author Jesse Primiani
 */
public class OutputTypeAsposeVSDX extends OutputType<Integer> {
  
  /** This represents the file name’s extension. */
  private final String EXTENSION = ".vsdx";
  
  /** This represents the type that OutputAspose uses to determine the output file’s format / type. */
  private final int TYPE = SaveFileFormat.VSDX;
  
  /**
   * Gets the file extension string from this class.
   * <br>
   * <br>
   * <b>Preconditions:</b> None 
   * <br>
   * <b>Postconditions:</b> Returns a string containing the extension part of a file name.
   * 
   * @author Jesse Primiani
   * @return The file extension String. (".vsdx")
   */
  public final String getExtension() {
    return this.EXTENSION;
  }
  
  /**
   * Gets the file type data structure from this class.
   * <br>
   * <br>
   * <b>Preconditions:</b> None 
   * <br>
   * <b>Postconditions:</b> Returns an Integer object, used by the 
   * OutputAspose class to format the file.
   * 
   * @author Jesse Primiani
   * @return The file formatting data structure used by the output library. (Integer)
   */
  public final Integer getType() {
    return this.TYPE;
  }

}
