package com.beulahworks.SDMfileGenerator;

import java.io.File;
import java.util.HashMap;
import com.aspose.diagram.*;

/**
 * Collects the information needed to export a sequence diagram to a file, 
 * then creates a file with this information, using the Aspose.Diagram library.
 * <br>
 * <br>
 * <b>Traceability:</b> Method interfaces are given in Section 5.2 of the SDD.
 * <br>
 * <b>Traceability:</b> Class design is given in Sections 6.1.7 and 6.2.7 of the SDD.
 * <br>
 * <br>
 * <b>Class Invariants:</b> <br>
 *     The diagram attribute is not null. <br>
 * 
 * @author Jesse Primiani
 * @author Isis Curiel
 */
public class OutputAspose implements OutputAdapter {

  /** This stores the aspose.diagram data structure used to generate the file. */
  private Diagram diagram;
  
  /** A 'virtual index map', used to relate the index of an element in the input model to an 
   *  element in the output library, via the relative order of the element. To do so, this map 
   *  stores the output library's element insertion order, which refers to the same element in 
   *  both input and output libraries, as the key, and its actual ID as the value.
   */
  private HashMap<Integer, Long> actualIDs;
  
  /** The next virtual index to store. For use with the virtual index map. */
  private int nextVirtualIndex;
  
  /**
   * Automatically call initialize() in the default constructor 
   * to prevent the diagram attribute from being null.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Creates an OutputAspose instance and calls initialize() on it.
   * 
   * @author Jesse Primiani
   * @throws Exception On an Aspose.Diagram error.
   */
  public OutputAspose() throws Exception {
    this.initializeDiagram();
  }
  
  /**
   * Create the internal data structures / object used for exporting files.
   * Creates a new diagram object for exporting a diagram.
   * <br>
   * Run this before adding information from a new diagram.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> The output library’s diagram object is created or reset. 
   * Logging information is returned.
   * 
   * @author Jesse Primiani
   * @return A logging message, stating that an empty diagram was created.
   */
  public String initializeDiagram() throws Exception {
    // Create the output diagram data structure.
    diagram = new Diagram();

    // Add stencils from the master file.
    Diagram masterFile = new Diagram("VisioMasters" + File.separator + "MasterSDM.vssx");
    diagram.addMaster(masterFile, "Dynamic connector");
    diagram.addMaster(masterFile, "UMLActorBlock");
    diagram.addMaster(masterFile, "UMLSeqEntityBlock");
    diagram.addMaster(masterFile, "UMLObjectBlock");
    diagram.addMaster(masterFile, "UMLActivationBlock");
    diagram.addMaster(masterFile, "UMLConstraintBlock");
    diagram.addMaster(masterFile, "UMLOptionLoopBlock");
    diagram.addMaster(masterFile, "UMLAlternativeBlock2");
    diagram.addMaster(masterFile, "UMLDeletionBlock");
    
    // Initialize the virtual index map.
    actualIDs = new HashMap<Integer, Long>();
    nextVirtualIndex = 0;

    return "Empty Diagram object created & masters added";
  }
  
  /**
   * Add an actor element to the sequence diagram with the given name to be saved.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> An actor diagram element with the given name is added to the diagram. 
   * Logging information is returned.
   * 
   * @author Isis Curiel with help from Brayden McCoy  
   * @param actorName The actor element's name.
   * @return A logging message, stating that an actor with a given name was added.
   * @throws Exception on an output library error.
   */
  public String addActor(String actorName) throws Exception {
	if (actorName == null) actorName = "";
	
	// Add an actor to the diagram.
	long shapeID = diagram.addShape(0, 0, "UMLActorBlock", 0); // Use this master in asposeMasters: "UMLActorBlock"
	Shape shape = diagram.getPages().getPage(0).getShapes().getShape(shapeID); // gets the added shape
	shape.getText().getValue().add(new Txt(actorName)); // Change actor's text to the assigned name
	
	// Add the actual element ID to the virtual index map.
	actualIDs.put(nextVirtualIndex, shapeID);
	nextVirtualIndex++;
	
    return "Actor: " + actorName + " added to UML Sequence Diagram"; 
  }
  
  /**
   * Add an object element with the given name and class to be saved.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> An object diagram element with the given name and class text is added to the diagram. 
   * Logging information is returned.
   * 
   * @author Isis Curiel with help from Jesse Primiani
   * @param instanceName The object element's name.
   * @param className The name of the object element's class.
   * @return A logging message, stating that an object with the given instance and class names was added.
   * @throws Exception on an output library error.
   */
  public String addClassBlock(String instanceName, String className) throws Exception {
	if (instanceName == null) instanceName = "";
	if (className == null) className = "";
	
	// Add a class to the diagram.
	long shapeID = diagram.addShape(0, 0, "UMLObjectBlock", 0); // Use this master in asposeMasters: "UMLObjectBlock"
	Shape shape = diagram.getPages().getPage(0).getShapes().getShape(shapeID); // gets the added shape
	shape.getText().getValue().add(new Txt (instanceName+":"+className)); // Change class text to the assigned names
	
	// Add the actual element ID to the virtual index map.
    actualIDs.put(nextVirtualIndex, shapeID);
    nextVirtualIndex++;
	
    return "Class: " + className + " with instance: " + instanceName + " added to the UML Sequence Diagram" ;
  }
  
  /**
   * Add process block elements to the internal diagram to be saved.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> The given number, count, of process block diagram elements are added to the diagram. 
   * Logging information is returned.
   * 
   * @author Isis Curiel
   * @param count The number of process blocks to add.
   * @return A logging message, stating that the given number of activation blocks were added.
   * @throws Exception on an output library error.
   */
  public String addActivationBlocks(int count) throws Exception {
	  if (count < 0) count = 0;
	  
	  for (int i = 0; i < count; i++) {
	      // Add an activation block to the diagram.
		  long shapeID = diagram.addShape(0, 0, "UMLActivationBlock", 0); // Use this master in asposeMasters: "UMLActivationBlock"
		  
		  // Add the actual element ID to the virtual index map.
		  actualIDs.put(nextVirtualIndex, shapeID);
		  nextVirtualIndex++;
	  }
	  
	  return count + " activation blocks added to the UML Sequence Diagram";
  } 
  
  /**
   * Connect two added elements with a line.
   * <br>
   * <br>
   * <b>Preconditions:</b> fromIndex and toIndex must refer to valid, previously added elements.
   * <br>
   * <b>Postconditions:</b> A connection starting at the element referred to by fromIndex, and ending at that 
   * referred to by toIndex, is added to the diagram. With a dashed line when active is false, and a 
   * solid line otherwise. Logging information is returned.
   * 
   * @author Isis Curiel 
   * @param fromIndex The starting element for the lifeline.
   * @param toIndex The ending element for the lifeline.
   * @param active Whether the lifeline represents an active object.
   * @return A logging message, stating that an object's lifeline, active or not, was added.
   * @throws SDMException if an index is negative or invalid.
   * @throws Exception on output library error.
   */
  public String addLifeline(int fromIndex, int toIndex, boolean active) throws Exception {
	  if (fromIndex < 0) throw new SDMException("fromIndex in method 'addLifeline' is less than 0");
	  if (toIndex < 0) throw new SDMException("toIndex in method 'addLifeline' is less than 0");

	  // Add a life-line to the diagram.
	  long connectorID = diagram.addShape(0, 0, "Dynamic connector", 0); // Use this master in asposeMasters: "Dynamic connector"
	  Shape shape = diagram.getPages().getPage(0).getShapes().getShape(connectorID); // gets the added shape
	  shape.getLine().getBeginArrow().setValue(0); // Remove beginning arrow
	  shape.getLine().getEndArrow().setValue(0); // Remove ending arrow
	  shape.getLine().getLineColor().setValue("#000000"); // Use a black line
      shape.getLine().getLineWeight().setValue(0.014); // Use a thicker line
	  
	  // Set the life-line to be solid or dashed.
	  if (active) {
	    shape.getLine().getLinePattern().setValue(1); // 1 = solid line
	  } else {
	    shape.getLine().getLinePattern().setValue(2); // 2 = dashed line
	  }
	  
	  // Add the actual element ID to the virtual index map.
      actualIDs.put(nextVirtualIndex, connectorID);
      nextVirtualIndex++;
	  
	  // Convert from virtual to real indices, then connect shapes.
      if (!actualIDs.containsKey(fromIndex)) throw new SDMException("fromIndex in method 'addLifeline' is currently invalid");
      if (!actualIDs.containsKey(toIndex)) throw new SDMException("toIndex in method 'addLifeline' is currently invalid");
      
	  long realFromIndex = actualIDs.get(fromIndex);
	  long realToIndex = actualIDs.get(toIndex);
	  diagram.getPages().getPage(0).connectShapesViaConnector(realFromIndex, ConnectionPointPlace.BOTTOM, 
	      realToIndex, ConnectionPointPlace.TOP, connectorID); // connect shapes via indices
	  
      return "Lifeline from index: " + fromIndex + " to index: " + toIndex + " active: " + active + " added to the UML Sequence Diagram";
  }
  
  /**
   * Add a method call message between processes.
   * <br>
   * <br>
   * <b>Preconditions:</b> fromIndex and toIndex must refer to valid, previously added elements.
   * <br>
   * <b>Postconditions:</b> A method starting at the element referred to by fromIndex, and ending at that 
   * referred to by toIndex, with name for the method name, is added to the diagram. 
   * Logging information is returned.
   * 
   * @author Isis Curiel 
   * @param fromIndex The starting element for the message.
   * @param toIndex The ending element for the message.
   * @param text The message's (method's) text.
   * @return A logging message, stating that a message between the given objects with the given text was added.
   * @throws SDMException if an index is negative or invalid.
   * @throws Exception on output library error.
   */
  public String addMethod(int fromIndex, int toIndex, String text) throws Exception {
      if (fromIndex < 0) throw new SDMException("fromIndex in method 'addMethod' is less than 0");
      if (toIndex < 0) throw new SDMException("toIndex in method 'addMethod' is less than 0");
    
      if (text == null) text = "";

      // Add a method to the diagram.
	  long connectorID = diagram.addShape(0, 0, "Dynamic connector", 0); // Use this master in asposeMasters: "Dynamic connector"
	  Shape shape = diagram.getPages().getPage(0).getShapes().getShape(connectorID); // gets the added shape
	  shape.getText().getValue().add(new Txt(text)); // Change method text to the assigned text
	  shape.getLine().getBeginArrow().setValue(0); // Remove beginning arrow
      shape.getLine().getEndArrow().setValue(2); // Set ending arrow to a basic arrow type
      shape.getLine().getLinePattern().setValue(1); // Use a solid line
      shape.getLine().getLineColor().setValue("#000000"); // Use a black line
      shape.getLine().getLineWeight().setValue(0.014); // Use a thicker line
	  
	  // Convert from virtual to real indices, then connect shapes.
      if (!actualIDs.containsKey(fromIndex)) throw new SDMException("fromIndex in method 'addMethod' is currently invalid");
      if (!actualIDs.containsKey(toIndex)) throw new SDMException("toIndex in method 'addMethod' is currently invalid");
      
      long realFromIndex = actualIDs.get(fromIndex);;
      long realToIndex = actualIDs.get(toIndex);;
      diagram.getPages().getPage(0).connectShapesViaConnector(realFromIndex, ConnectionPointPlace.RIGHT, 
          realToIndex, ConnectionPointPlace.LEFT, connectorID); // connect shapes via indices

      return "Method from index: " + fromIndex + " to index: " + toIndex + " with text: " + text + " added to the UML Sequence Diagram";
  }
  
  /**
   * Add a constraint box with the given constraint text to be saved.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> A constraint diagram element with the given internal text is added to the diagram. 
   * Logging information is returned.
   * 
   * @author Isis Curiel
   * @param text The constraint's text.
   * @return A logging message, stating that a constraint with the given text was added.
   * @throws Exception on an output library error.
   */
  public String addConstraint(String text) throws Exception {
	  if (text == null) text = "";

	  // Add a constraint block to the diagram.
	  long shapeID = diagram.addShape(0, 0, "UMLConstraintBlock", 0); // Use this master in asposeMasters: "UMLConstraintBlock"
	  Shape shape = diagram.getPages().getPage(0).getShapes().getShape(shapeID); // gets the added shape
	  shape.getText().getValue().add(new Txt (text)); // Change constraint text to the assigned text
	  
      return "Constraint with text: " + text + " added to the UML Sequence Diagram";
  }
  
  /**
   * Add a loop box with the given condition text to be saved.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br> 
   * <b>Postconditions:</b> A loop block diagram element with the given internal text for the looping condition 
   * is added to the diagram. Logging information is returned.
   * 
   * @author Isis Curiel
   * @param text The loop's constraint text.
   * @return A logging message, stating that a loop with the given constraint text was added.
   * @throws Exception on an output library error.
   */
  public String addLoop(String text) throws Exception {
	  if (text == null) text = "";
	  
	  // Add a loop block to the diagram.
	  long shapeID = diagram.addShape(0, 0, "UMLOptionLoopBlock", 0); // Use this master in asposeMasters: "UMLOptionLoopBlock"
	  Shape shape = diagram.getPages().getPage(0).getShapes().getShape(shapeID); // gets the added shape
	  shape.getText().getValue().add(new Txt (text)); // Change constraint text to the assigned text
	  
      return "Loop with constraint: " + text + " added to the UML Sequence Diagram";
  }
  
  /**
   * Add an if-statement box with the given condition text to be saved.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> An alternative (if-else) block diagram element, with the given internal text for 
   * the if condition, and textElse for the else text, is added to the diagram. Logging information 
   * is returned.
   * 
   * @author Isis Curiel
   * @param text The initial constraint's text.
   * @param textElse The else section's text.
   * @return A logging message, stating that an if-block with the given constraint and else text was added.
   * @throws Exception on an output library error.
   */
  public String addAlternative(String text, String textElse) throws Exception {
      if (text == null) text = "";
      if (textElse == null) textElse = "";
	  
	  // Add an alternative block to the diagram.
	  long shapeID = diagram.addShape(0, 0, "UMLAlternativeBlock2", 0); // Use this master in asposeMasters: "UMLAlternativeBlock2"
	  Shape shape = diagram.getPages().getPage(0).getShapes().getShape(shapeID); // gets the added shape
	  shape.getText().getValue().add(new Txt (text)); // Change constraint text to the assigned text
	  shape.getText().getValue().add(new Txt (textElse)); // Change else text to the assigned text

      return "Alternative block with if statement: " + text + " and else statement: " + textElse + " added to the UML Sequence Diagram";
  }
  
  /**
   * Called after all content is added to the diagram, but before the diagram is saved. 
   * Used for shape positioning code, and any other diagram finalization code.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> The shapes will be properly positioned in the diagram.
   * 
   * @author Jesse Primiani
   * @return A logging message, stating that shapes were properly positioned.
   */
  public String finalizeDiagram() throws Exception {
    // TODO: Implement shape positioning code.
    
    return "Shapes were properly positioned in the diagram.";
  }
  
  /**
   * Saves all the added elements contained in the diagram object to a file.
   * <br>
   * <br>
   * <b>Preconditions:</b> All parameters must be valid, as given in SDMtoFile, and not null.
   * <br>
   * <b>Postconditions:</b> A file containing all the information previously added to the diagram attribute 
   * is created. Logging information is returned.
   * 
   * @author Jesse Primiani
   * @param path The output directory's path.
   * @param name The output file's name.
   * @param type An object containing the output file's type information.
   * @param overwrite Whether to overwrite an already existing file if it exists.
   * @return A logging message, stating whether the file was successfully created.
   * @throws SDMException If any parameter is null.
   * @throws Exception on an output library error.
   */
  public String saveToFile(final String path, final String name, final OutputType<?> type, boolean overwrite) throws Exception {
    if (path == null) {
      throw new SDMException("Null 'path' parameter in: saveToFile");
    } else if (name == null) {
      throw new SDMException("Null 'name' parameter in: saveToFile");
    } else if (type == null) {
      throw new SDMException("Null 'type' parameter in: saveToFile");
    }

    String appendString = "";
    
    // Create the output directory if it does not exist.
    File pathFile = new File(path);
    if (!pathFile.exists()) {
      pathFile.mkdir();
    }
    
    // Rename the output file if it exists and overwrite is false via appending an integer.
    if (!overwrite) {
      int appendNum = 0;
      File resultFile = new File(path, name + type.getExtension());
      while (resultFile.exists()) {
        appendNum++;
        appendString = Integer.toString(appendNum);
        resultFile = new File(path, name + appendString + type.getExtension());
      }
    }
    
    // Add a file path seperator on the path if it is not empty.
    String pathSeperator = File.separator;
    if (path.isEmpty()) {
      pathSeperator = "";
    }
    
    // Save the created Aspose diagram to disk.
    String fullFileName = path + pathSeperator + name + appendString + type.getExtension();
    diagram.save(fullFileName, (int) type.getType());
    
    return "Diagram successfully saved as: " + fullFileName;
  }

}
