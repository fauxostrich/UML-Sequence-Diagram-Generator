package com.beulahworks.SDMfileGenerator;

import java.io.PrintStream;

/**
 * The primary SDM file conversion class.
 * <br>
 * <br>
 * <b>Traceability:</b> Initial details are given in Section 3.2.1 of the SRS.
 * <br>
 * <b>Traceability:</b> Method interfaces are given in Section 5.2 of the SDD.
 * <br>
 * <b>Traceability:</b> Class design is given in Sections 6.1.1 and 6.2.1 of the SDD.
 * <br>
 * <br>
 * <b>Class Invariants:</b> <br>
 * No attributes are null. <br>
 * filePath and fileName have valid path and name strings for the run-time operating system. <br>
 * fileType and outputAdapter both contain concrete implementations. <br>
 * 
 * @author Jesse Primiani
 */
public class SDMtoFile {
  
  /** This is set to true to allow overwriting a file with the same name as the one to be exported, if it exists. */
  private boolean overwrite = false;
  
  /** This represents the file path that the exported file will be saved to. */
  private String filePath = "";
  
  /** This represents the file name of the exported file, without the extension. */
  private String fileName = "SequenceDiagram";
  
  /** This contains the object in which the extension (of the file) and the file type data structure are held. */
  private OutputType<?> fileType;
  
  /** This contains the object that creates the output data structure and exports a sequence diagram to a file. */
  private OutputAdapter outputAdapter;
  
  /**
   * The default constructor.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Creates a SDMtoFile instance with the default attributes.
   * 
   * @author Jesse Primiani
   * @throws Exception on Aspose.Diagram error.
   */
  public SDMtoFile() throws Exception {
    fileType = new OutputTypeAsposeVSDX();
    outputAdapter = new OutputAspose();
  }
  
  /**
   * A convenience constructor.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Creates a SDMtoFile instance with the given file path and name.
   * 
   * @author Jesse Primiani
   * @param path The path to the output file.
   * @param name The name of the output file (without extension).
   * @throws SDMException on an invalid file path or name.
   */
  public SDMtoFile(final String path, final String name) throws Exception {
    setOutputFile(path, name);
    fileType = new OutputTypeAsposeVSDX();
    outputAdapter = new OutputAspose();
  }
  
  /**
   * A convenience constructor.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Creates a SDMtoFile instance with the given file output type object.
   * 
   * @author Jesse Primiani
   * @param type An object containing the output file's type information.
   * @throws SDMException on a null type.
   */
  public SDMtoFile(final OutputType<?> type) throws Exception {
    setOutputType(type);
    outputAdapter = new OutputAspose();
  }
  
  /**
   * A convenience constructor.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Creates a SDMtoFile instance with the given file output type object, 
   * as well as setting the output adapter object.
   * 
   * @author Jesse Primiani
   * @param type An object containing the output file's type information.
   * @param adapter The concrete object containing information for file output.
   * @throws SDMException on an invalid file path, invalid file name, null type, or null adapter.
   */
  public SDMtoFile(final OutputType<?> type, final OutputAdapter adapter) throws Exception {
    setOutputAdapter(adapter);
    setOutputType(type);
  }
  
  /**
   * A convenience constructor.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Creates a SDMtoFile instance with the given file path and name, 
   * while also setting the file output type object.
   * 
   * @author Jesse Primiani
   * @param path The path to the output file.
   * @param name The name of the output file (without extension).
   * @param type An object containing the output file's type information.
   * @throws SDMException on an invalid file path, invalid file name, or null type.
   */
  public SDMtoFile(final String path, final String name, final OutputType<?> type) throws Exception {
    setOutputType(type);
    setOutputFile(path, name);
    outputAdapter = new OutputAspose();
  }
  
  /**
   * A convenience constructor.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Creates a SDMtoFile instance with the given file path and name, 
   * while also setting the file output type object, as well as setting the output adapter object.
   * 
   * @author Jesse Primiani
   * @param path The path to the output file.
   * @param name The name of the output file (without extension).
   * @param type An object containing the output file's type information.
   * @param adapter The concrete object containing information for file output.
   * @throws SDMException on an invalid file path, invalid file name, null type, or null adapter.
   */
  public SDMtoFile(final String path, final String name, final OutputType<?> type, final OutputAdapter adapter) throws Exception {
    setOutputAdapter(adapter);
    setOutputType(type);
    setOutputFile(path, name);
  }
  
  /**
   * Sets whether to overwrite an already existing file on file output. 
   * <br>
   * On a naming conflict, this is used to determine whether the old file 
   * should be overwritten, or the new file name should have a number 
   * appended to it.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> The overwrite attribute is set to the value contained in overwriteFile.
   * 
   * @author Jesse Primiani
   * @param overwriteFile Whether to overwrite an existing file.
   */
  public void setOverwrite(final boolean overwriteFile) {
    overwrite = overwriteFile;
  }
  
  /**
   * Sets the output file directory and file name, minus extension. 
   * <br>
   * Used to set the output file's name and the path to its future directory.
   * <br>
   * <br>
   * <b>Preconditions:</b> Parameters must not be null or improperly formatted.
   * <br>
   * <b>Postconditions:</b> The filePath and fileName attributes are set to the corresponding values of the parameters.
   * 
   * @author Jesse Primiani
   * @param path The path to the output file.
   * @param name The name of the output file (without extension).
   * @throws SDMException on an invalid file path or invalid file name.
   */
  public void setOutputFile(final String path, final String name) throws Exception {
    if (path == null) {
      throw new SDMException("Invalid 'path' parameter in: setOutputFile");
    }
    if (name == null || name.isEmpty()) {
      throw new SDMException("Invalid 'name' parameter in: setOutputFile");
    }
    filePath = path;
    fileName = name;
  }

  /**
   * Sets the output file’s type using an OutputType object.
   * <br>
   * Used to set the type of output file generated.
   * <br>
   * <br>
   * <b>Preconditions:</b> The parameter must not be null, and must be a concrete object.
   * <br>
   * <b>Postconditions:</b> The fileType attribute is set to that stored in the type parameter.
   * 
   * @author Jesse Primiani
   * @param type An object containing the output file's type information.
   * @throws SDMException on a null or invalid type.
   */
  public void setOutputType(final OutputType<?> type) throws Exception {
    if (type == null) throw new SDMException("Null 'type' parameter in: setOutputType");
    fileType = type;
  }
  
  /**
   * Sets the OutputAdapter object used for file saving.
   * <br>
   * Used to set the concrete adapter for generating needed 
   * data structures and the final output file.
   * <br>
   * <br>
   * <b>Preconditions:</b> The parameter must not be null, and must be a concrete object.
   * <br>
   * <b>Postconditions:</b> The outputAdapter attribute is set to that stored in the adapter parameter.
   * 
   * @author Jesse Primiani
   * @param adapter The concrete object containing information for file output.
   * @throws SDMException on a null or invalid adapter.
   */
  public void setOutputAdapter(final OutputAdapter adapter) throws Exception {
    if (adapter == null) throw new SDMException("Null 'adapter' parameter in: setOutputAdapter");
    outputAdapter = adapter;
  }
  
  /**
   * Exports the sequence diagram in the InputAdapter object.
   * <br>
   * Used to perform the conversion from the input adapter's data structures to 
   * the output adapter's data structures. Also generates the output file using 
   * the output adapter, alongside this class' attributes.
   * <br>
   * <br>
   * <b>Preconditions:</b> The diagram parameter must not be null, and must be a concrete object.
   * <br>
   * <b>Postconditions:</b> A file containing the parameter’s internal sequence diagram will be saved 
   * using the information found in the SDMtoFile object’s attributes, with the file export status 
   * printed to the log parameter while this method runs.
   * 
   * @author Jesse Primiani
   * @param diagram The concrete object containing the diagram to save to a file.
   * @param log The PrintStream object that prints status updates; null disables logging.
   * @throws SDMException if the file export process does not successfully complete.
   */
  public void exportFile(final InputAdapter diagram, final PrintStream log) throws Exception {
    if (diagram == null) throw new SDMException("Null 'diagram' parameter in: exportFile");
    
    StringBuilder logString = new StringBuilder();
    
    try {
      logString.append("--------------------------------" + System.lineSeparator());
      
      // Initialize the output adapter's internal data structures.
      String logInit = outputAdapter.initializeDiagram();
      logString.append(logInit + System.lineSeparator() + System.lineSeparator());
      
      // Add all actor elements.
      int count = diagram.getActorCount();
      logString.append("Input Actors: " + count + System.lineSeparator());
      for (int index = 0; index < count; index++) {
        String logObject = outputAdapter.addActor(
            diagram.getActorName(index));
        logString.append(logObject + System.lineSeparator());
      }
      if (count > 0) logString.append(System.lineSeparator());
      
      // Add all class block (object) elements.
      count = diagram.getClassBlockCount();
      logString.append("Input Class Blocks: " + count + System.lineSeparator());
      for (int index = 0; index < count; index++) {
        String logObject = outputAdapter.addClassBlock(
            diagram.getClassBlockInstanceName(index), 
            diagram.getClassBlockClassName(index));
        logString.append(logObject + System.lineSeparator());
      }
      if (count > 0) logString.append(System.lineSeparator());
      
      // Add all activation block (process) elements.
      count = diagram.getActivationBlockCount();
      logString.append("Input Activation Blocks: " + count + System.lineSeparator());
      String logObjectList = outputAdapter.addActivationBlocks(count);
      logString.append(logObjectList + System.lineSeparator() + System.lineSeparator());
      
      // All all lifelines (internal lines).
      count = diagram.getLifelineCount();
      logString.append("Input Lifelines: " + count + System.lineSeparator());
      for (int index = 0; index < count; index++) {
        String logObject = outputAdapter.addLifeline(
            diagram.getLifelineFromIndex(index), 
            diagram.getLifelineToIndex(index), 
            diagram.getLifelineActive(index));
        logString.append(logObject + System.lineSeparator());
      }
      if (count > 0) logString.append(System.lineSeparator());
      
      // Add all methods (external lines with message names).
      count = diagram.getMethodCount();
      logString.append("Input Methods: " + count + System.lineSeparator());
      for (int index = 0; index < count; index++) {
        String logObject = outputAdapter.addMethod(
            diagram.getMethodFromIndex(index), 
            diagram.getMethodToIndex(index), 
            diagram.getMethodText(index));
        logString.append(logObject + System.lineSeparator());
      }
      if (count > 0) logString.append(System.lineSeparator());
      
      // Add all constraints.
      count = diagram.getConstraintCount();
      logString.append("Input Constraints: " + count + System.lineSeparator());
      for (int index = 0; index < count; index++) {
        String logObject = outputAdapter.addConstraint(
            diagram.getConstraintText(index));
        logString.append(logObject + System.lineSeparator());
      }
      if (count > 0) logString.append(System.lineSeparator());
      
      // Add all loop blocks.
      count = diagram.getLoopCount();
      logString.append("Input Loop Blocks: " + count + System.lineSeparator());
      for (int index = 0; index < count; index++) {
        String logObject = outputAdapter.addLoop(
            diagram.getLoopText(index));
        logString.append(logObject + System.lineSeparator());
      }
      if (count > 0) logString.append(System.lineSeparator());
      
      // Add all alternative blocks.
      count = diagram.getAlternativeCount();
      logString.append("Input Alternative Blocks: " + count + System.lineSeparator());
      for (int index = 0; index < count; index++) {
        String logObject = outputAdapter.addAlternative(
            diagram.getAlternativeText(index), 
            diagram.getAlternativeTextElse(index));
        logString.append(logObject + System.lineSeparator());
      }
      if (count > 0) logString.append(System.lineSeparator());
      
      // Finalize the output adapter's internal data structures.
      String logFinalize = outputAdapter.finalizeDiagram();
      logString.append(logFinalize + System.lineSeparator() + System.lineSeparator());
      
      // Save the created output library data structure to a file.
      String logSaved = outputAdapter.saveToFile(filePath, fileName, fileType, overwrite);
      logString.append(logSaved + System.lineSeparator());
      logString.append("--------------------------------");

      if (log != null){
        log.println(logString.toString());
      }
    }
    catch (Exception ex) {
      if (log != null){
        log.println(logString.toString());
      }
      throw ex;
    }
  }
  
  /**
   * A convenience version of: exportFile(InputAdapter diagram, PrintStream log). 
   * <br>
   * Used to perform the conversion from the input adapter's data structures to 
   * the output adapter's data structures. Also generates the output file using 
   * the output adapter, alongside this class' attributes.
   * <br>
   * <br>
   * <b>Preconditions:</b> The diagram parameter must not be null, and must be a concrete object.
   * <br>
   * <b>Postconditions:</b> A file containing the parameter’s internal sequence diagram will be saved 
   * using the information found in the SDMtoFile object’s attributes.
   * 
   * @author Jesse Primiani
   * @param diagram The concrete object containing the diagram to save to a file.
   * @throws SDMException if the file export process does not successfully complete.
   */
  public void exportFile(final InputAdapter diagram) throws Exception {
    exportFile(diagram, null);
  }

}
