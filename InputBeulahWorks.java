package com.beulahworks.SDMfileGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import edu.purdue.cs59000.umltranslator.*;
import edu.purdue.cs59000.umltranslator.message.*;
import edu.purdue.cs59000.umltranslator.umlcontainer.*;

/**
 * Reads the information from a UMLSequenceDiagram object. 
 * This information will then be sent to an output adapter and saved to a file.
 * <br>
 * <br>
 * <b>Traceability:</b> Method interfaces are given in Section 5.2 of the SDD.
 * <br>
 * <b>Traceability:</b> Class design is given in Sections 6.1.5 and 6.2.5 of the SDD.
 * <br>
 * <br>
 * <b>Class Invariants:</b> <br>
 *     The diagram attribute is not null. <br>
 * 
 * @author Jesse Primiani
 * @author Brayden McCoy
 * @author Jacob Taylor
 */
public class InputBeulahWorks implements InputAdapter {

  /** This contains the data structures to read from, as given in the Beulah Work’s SDM package. */
  private UMLSequenceDiagram diagram;

  /** A 'virtual index map', used to relate the index of an element in the input model to an 
   *  element in the output library, via the relative order of the element. To do so, this map 
   *  stores the input library's element's ID as the key, and the virtual index, which refers to 
   *  the same element in both input and output libraries, as the value.
   */
  private HashMap<Integer, Integer> virtualIndices;
  
  /**
   * Private default constructor.
   */
  @SuppressWarnings("unused")
  private InputBeulahWorks() {}

  /**
   * The constructor that refers to a UMLSequenceDiagram object.
   * <br>
   * <br>
   * <b>Preconditions:</b> The diagram parameter must not be null.
   * <br>
   * <b>Postconditions:</b> The diagram attribute is set to the contents of the diagram parameter.
   * 
   * @author Jesse Primiani
   * @param diagram The BeulahWorks UMLSequenceDiagram object.
   * @throws SDMException If the diagram is null.
   */
  public InputBeulahWorks(final UMLSequenceDiagram diagram) throws SDMException {
    if (diagram == null) throw new SDMException("Null 'diagram' parameter in: InputBeulahWorks constructor");
    this.diagram = diagram;

    // Setup the virtual index map.
    virtualIndices = new HashMap<Integer, Integer>();
    int nextVirtualIndex = 0;
    
    // Add actor IDs to the virtual index map.
    List<UMLSymbol> actors = diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLActor).collect(Collectors.toList());
    for (UMLSymbol actor : actors) {
      virtualIndices.put(actor.getId(), nextVirtualIndex);
      nextVirtualIndex++;
    }
    
    // Add class IDs to the virtual index map.
    List<UMLSymbol> classes = diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLClass).collect(Collectors.toList());
    for (UMLSymbol getClass : classes) {
      virtualIndices.put(getClass.getId(), nextVirtualIndex);
      nextVirtualIndex++;
    }
    
    // Add activation block IDs to the virtual index map.
    List<UMLSymbol> procs = diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLActivationBox).collect(Collectors.toList());
    for (UMLSymbol proc : procs) {
      virtualIndices.put(proc.getId(), nextVirtualIndex);
      nextVirtualIndex++;
    }
    
    // Add life-line IDs to the virtual index map.
    List<UMLSymbol> lifelines = diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLLifeline).collect(Collectors.toList());
    for (UMLSymbol lifeline : lifelines) {
      virtualIndices.put(lifeline.getId(), nextVirtualIndex);
      nextVirtualIndex++;
    }
  }
  
  /**
   * Gets the number of actor elements in the diagram.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Returns the number of actor elements in the sequence diagram.
   * 
   * @author Brayden McCoy
   * @return The number of actor elements in the diagram.
   */
  public int getActorCount() {
	  // Filters list via the use of lambdas, then get it the total count of instances of UMLActor
	  return diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLActor).collect(Collectors.toList()).size();
  }
  
  /**
   * Gets the name of the actor at the given index.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid actor in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the given actor element’s name.
   * 
   * @author Brayden McCoy
   * @param index The index of the requested element.
   * @return The name of the requested actor element.
   * @throws SDMException If the index is negative.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public String getActorName(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getActorName' method");
      
	  // Filters list via the use of lambdas, then get it's the index of the object before casting it to UMLActor
	  String name = ((UMLActor)(diagram.getUMLSymbols().stream().filter(p -> p instanceof UMLActor).collect(Collectors.toList()).get(index))).getName();
	  if (name == null) name = "";
      return name;
  }
  
  /**
   * Gets the number of object elements in the diagram.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Returns the number of object elements in the sequence diagram.
   * 
   * @author Brayden McCoy
   * @return The number of object elements in the diagram.
   */
  public int getClassBlockCount() {
	  // Filters list via the use of lambdas, then get it the total count of instances of UMLClass
	  return diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLClass).collect(Collectors.toList()).size();
  }
  
  /**
   * Gets the name of the object at the given index.
   * Reads an object element's 'variable' name.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid object in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the given object element’s name.
   * 
   * @author Brayden McCoy
   * @param index The index of the requested element.
   * @return The requested object's instantiated name.
   * @throws SDMException If the index is negative.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public String getClassBlockInstanceName(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getClassBlockInstanceName' method");
      
	  // Filters list via the use of lambdas, then get it's the index of the object before casting it to UMLClass
	  String name = ((UMLClass)(diagram.getUMLSymbols().stream().filter(p -> p instanceof UMLClass).collect(Collectors.toList()).get(index))).getInstanceName();
	  if (name == null) name = "";
      return name;
  }
  
  /**
   * Gets the name of the object’s class at the given index.
   * Reads an object element's class name.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid object in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the given object element’s class name.
   * 
   * @author Brayden McCoy
   * @param index The index of the requested element.
   * @return The name of the requested object's class.
   * @throws SDMException If the index is negative.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public String getClassBlockClassName(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getClassBlockClassName' method");
    
	  // Filters list via the use of lambdas, then get it's the index of the object before casting it to UMLClass
	  String name = ((UMLClass)(diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLClass).collect(Collectors.toList()).get(index))).getClassName();
	  if (name == null) name = "";
      return name;
  }
  
  /**
   * Gets the number of process block elements in the diagram.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Returns the number of process elements in the sequence diagram.
   * 
   * @author Brayden McCoy
   * @return The number of activation block elements in the diagram.
   */
  public int getActivationBlockCount() {
	  // Filters list via the use of lambdas, then get it the total count of instances of UMLActivationBox
	  return diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLActivationBox).collect(Collectors.toList()).size();
  }
  
  /**
   * Gets the number of connection between processes and elements.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Returns the number of connections between elements in the sequence diagram.
   * 
   * @author Brayden McCoy
   * @return The number of lifelines in the diagram.
   */
  public int getLifelineCount() {
	  // Filters list via the use of lambdas, then get it the total count of instances of UMLLifeline
	  return diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLLifeline).collect(Collectors.toList()).size();
  }
  
  /**
   * Gets the starting element’s index.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid connection in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the index of the connection’s starting element.
   * 
   * @author Brayden McCoy
   * @param index The index of the requested element.
   * @return The starting index for the requested lifeline element.
   * @throws SDMException If the index is negative, or the life-line has no source element.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public int getLifelineFromIndex(int index) throws Exception {
      // TODO: Serious Issue: A UMLLifeline in this input model cannot have its source be a UMLActivationBox, which is required for ordering these boxes.
    
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getLifelineFromIndex' method");
      
	  // Filters list (of UMLLifeline) via the use of lambdas, then get it's the source of the object at index
	  // Then proceeds to get the id of the parent (source) and find a match against the whole list of UMLSymbols
	  
	  List<UMLSymbol> umlSymbols = diagram.getUMLSymbols();
	  UMLSymbol parentSymbol = (umlSymbols.stream().filter(s -> s instanceof UMLLifeline).collect(Collectors.toList()).get(index)).getSource();
	  
	  if (parentSymbol == null) {
		  throw new SDMException("Lifeline has no source in method 'getLifelineFromIndex'");
	  }
	  if (!virtualIndices.containsKey(parentSymbol.getId())) {
	      throw new SDMException("Object cannot be indexed in method 'getLifelineFromIndex'");
	  }

	  return virtualIndices.get(parentSymbol.getId());
  }
  
  /**
   * Gets the ending element’s index.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid connection in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the index of the connection’s ending element.
   * 
   * @author Brayden McCoy
   * @param index The index of the requested element.
   * @return The ending index for the requested lifeline element.
   * @throws SDMException If the index is negative, or the life-line has no destination element.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public int getLifelineToIndex(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getLifelineToIndex' method");
    
	  // Filters list (of UMLLifeline) via the use of lambdas, then get it's the id of UMLLifeline at the given index
	  // Then proceeds to go throughout the list of UMLSymbols and find a non-null match to a given UMLSymbol's source ID
	  
	  List<UMLSymbol> umlSymbols = diagram.getUMLSymbols();
	  int lifelineID = (umlSymbols.stream().filter(s -> s instanceof UMLLifeline).collect(Collectors.toList()).get(index)).getId();
	  
	  for (UMLSymbol symbol : umlSymbols) {
		  if (symbol.getSource() != null && lifelineID == symbol.getSource().getId()) {
		      if (!virtualIndices.containsKey(symbol.getId())) {
		          throw new SDMException("Object cannot be indexed in method 'getLifelineToIndex'");
		      }
		      return virtualIndices.get(symbol.getId());
		  }
	  }

	  // Throws SDMException if no match is found
	  throw new SDMException("Lifeline has no destination in method 'getLifelineToIndex'");
  }
  
  /**
   * Gets whether the connected object exists.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid connection in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the whether the connection refers to an object that currently exists.
   * 
   * @author Brayden McCoy
   * @param index The index of the requested element.
   * @return Whether the requested lifeline element represents an existing object.
   * @throws SDMException If the index is negative.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public boolean getLifelineActive(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getLifelineActive' method");
    
	  // TODO: UMLLifeline doesn't have a way to determine if a life-line is dashed or solid based on the given input model
	  return true;
  }
  
  /**
   * Gets the number of methods between multiple processes.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Returns the number of method connections between elements in the sequence diagram.
   * 
   * @author Jacob Taylor
   * @return The number of methods in the diagram.
   */
  public int getMethodCount() {
	  // Filters list via the use of lambdas, then get it the total count of instances of UMLMessage
	  return diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLMessage).collect(Collectors.toList()).size();
  }
  
  /**
   * Gets the starting element’s index.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid method in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the index of the method’s starting element.
   * 
   * @author Jacob Taylor
   * @param index The index of the requested element.
   * @return The starting index for the requested method element.
   * @throws SDMException If the index is negative, or the method has no source element.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public int getMethodFromIndex(int index) throws Exception {
      // TODO: A UMLMessage cannot have its source or destination be a UMLLifeline in this input model; would be useful for UMLActors.
    
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getMethodFromIndex' method");
    
	  // Filters list (of UMLMessage) via the use of lambdas, then get it's the source of the object at index
	  // Then proceeds to get the id of the parent (source) and find a match against the whole list of UMLSymbols
	  
	  List<UMLSymbol> umlSymbols = diagram.getUMLSymbols();
	  UMLSymbol parentSymbol = (umlSymbols.stream().filter(s -> s instanceof UMLMessage).collect(Collectors.toList()).get(index)).getSource();
	  
	  if (parentSymbol == null) {
		  throw new SDMException("Method has no source in method 'getMethodFromIndex'");
	  }
	  if (!virtualIndices.containsKey(parentSymbol.getId())) {
          throw new SDMException("Object cannot be indexed in method 'getMethodFromIndex'");
      }

      return virtualIndices.get(parentSymbol.getId());
  }
  
  /**
   * Gets the ending element’s index.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid method in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the index of the method’s ending element.
   * 
   * @author Jacob Taylor
   * @param index The index of the requested element.
   * @return The ending index for the requested method element.
   * @throws SDMException If the index is negative, or the method has no destination element.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public int getMethodToIndex(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getMethodToIndex' method");
    
      // Refer to how the getMethodFromIndex and the getLifelineToIndex was implemented.
	  List<UMLSymbol> umlSymbols = diagram.getUMLSymbols();
	  UMLSymbol childSymbol = ((UMLMessage) umlSymbols.stream().filter(s -> s instanceof UMLMessage).collect(Collectors.toList()).get(index)).getDestination();
	  
	  if (childSymbol == null) {
        throw new SDMException("Method has no destination in method 'getMethodToIndex'");
      }
	  if (!virtualIndices.containsKey(childSymbol.getId())) {
          throw new SDMException("Object cannot be indexed in method 'getMethodFromIndex'");
      }

      return virtualIndices.get(childSymbol.getId());
  }
  
  /**
   * Gets the method name and other method text.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid method in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the method’s name and other related method text.
   * 
   * @author Jacob Taylor
   * @param index The index of the requested element.
   * @return The text for the requested method element.
   * @throws SDMException If the index is negative.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public String getMethodText(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getMethodText' method");
      
      List<UMLSymbol> umlSymbols = diagram.getUMLSymbols();
      UMLSymbol message = umlSymbols.stream().filter(s -> s instanceof UMLMessage).collect(Collectors.toList()).get(index);
    
	  // Use getName() method for the name, getReturnType() for return type
	  // May also need to call getArguments() which returns a List of UMLMessageArguments
	  // May also need getReturnMessage as well, which returns UMLReturnMessage
      String methodName = "message";
      String methodArgs = "";
      String methodReturn = "";
      
      // Get method name and return type, if applicable
      if (message instanceof UMLSynchronousMessage || message instanceof UMLCreateMessage) {
        if (message instanceof UMLSynchronousMessage) methodName = ((UMLSynchronousMessage) message).getName();
        else if (message instanceof UMLCreateMessage) methodName = ((UMLCreateMessage) message).getName();
        if (methodName == null) methodName = "message";
        
        if (message instanceof UMLSynchronousMessage) methodReturn = ((UMLSynchronousMessage) message).getReturnType();
        else if (message instanceof UMLCreateMessage) methodReturn = ((UMLCreateMessage) message).getReturnType();
        if (methodReturn == null) methodReturn = "";
        else if (!methodReturn.isEmpty()) methodReturn = ":" + methodReturn;
      }
      else if (message instanceof UMLReturnMessage) {
        methodName = ((UMLReturnMessage) message).getReturnName();
        if (methodName == null) methodName = "return";
      }
      
      // Setup message arguments, if applicable
      if (message instanceof UMLSynchronousMessage || message instanceof UMLCreateMessage) {
        List<UMLMessageArgument> args = ((UMLSynchronousMessage) message).getArguments();
        int argNum = 1;
        for (UMLMessageArgument arg : args) {
          String argName = arg.getName();
          if (argName == null) argName = "";
          methodArgs += argName;
          if (arg.getDataType() != null && !arg.getDataType().isEmpty()) methodArgs += ":" + arg.getDataType();
          if (arg.getInitializedTo() != null && !arg.getInitializedTo().isEmpty()) methodArgs += "=" + arg.getInitializedTo();
          if (arg.isHasVarArgs()) methodArgs += ", ...";
          if (argNum < args.size()) methodArgs += ", ";
          argNum++;
        }
      }
	  
	  return methodName + "(" + methodArgs + ")" + methodReturn;
  }
  
  /**
   * Gets the number of constraint elements.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Returns the number of constraint elements in the sequence diagram.
   * 
   * @author Jacob Taylor
   * @return The number of constraint elements in the diagram.
   */
  public int getConstraintCount() {
	  // Filters list via the use of lambdas, then get it the total count of instances of UMLCondition
	  // UMLCondition defines the if / conditional statement (such as if x > 3)
	  return diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLCondition).collect(Collectors.toList()).size();
  }
  
  /**
   * Gets the constraint’s text, aka its constraint.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid constraint in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the constraint’s inner text.
   * 
   * @author Jacob Taylor
   * @param index The index of the requested element.
   * @return The text for the requested constraint element.
   * @throws SDMException If the index is negative.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public String getConstraintText(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getConstraintText' method");
    
	  // Refer to my getClassBlockClassName method, but instead of casting it to UMLClass and calling getClassName()
	  // cast to UMLCondition and call getCondition()

	  String condition = ((UMLCondition)(diagram.getUMLSymbols().stream().filter(p -> p instanceof UMLCondition).collect(Collectors.toList()).get(index))).getCondition();
	  if (condition == null) condition = "";
      return condition;
  }
  
  /**
   * Gets the number of loop boxes.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Returns the number of loop block elements in the sequence diagram.
   * 
   * @author Jacob Taylor
   * @return The number of loop block elements in the diagram.
   */
  public int getLoopCount() {
	  // Refer to getConstraintCount(), but use UMLLoop instead of UMLCondition for the instanceof

	  return diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLLoop).collect(Collectors.toList()).size();
  }
  
  /**
   * Gets the loop condition / constraint.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid loop block in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the looping condition’s inner text.
   * 
   * @author Jacob Taylor
   * @param index The index of the requested element.
   * @return The constraint text for the requested loop block element.
   * @throws SDMException If the index is negative.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public String getLoopText(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getLoopText' method");
    
	  UMLLoop loop = (UMLLoop)(diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLLoop).collect(Collectors.toList()).get(index));

	  // This statement is saying get the UMLCondition from UMLLoop, then get the string "condition" from UMLCondition
	  String condition = "";
	  if (loop.getCondition() != null) {
	    condition = loop.getCondition().getCondition();
	    if (condition == null) condition = "";
	  }
      return condition;
  }
  
  /**
   * Gets the number of if-statement boxes.
   * <br>
   * <br>
   * <b>Preconditions:</b> None
   * <br>
   * <b>Postconditions:</b> Returns the number of alternative (if-else) block elements in the sequence diagram.
   * 
   * @author Jacob Taylor
   * @return The number of if-statement block elements in the diagram.
   */
  public int getAlternativeCount() {
	  // Refer to getConstraintCount(), but use UMLLoop instead of UMLAlternatives for the instanceof

	  return diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLAlternatives).collect(Collectors.toList()).size();
  }
  
  /**
   * Gets the condition needed for the first section.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid alternative (if-else) statement block in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the if statement’s inner text.
   * 
   * @author Jacob Taylor
   * @param index The index of the requested element.
   * @return The constraint text for the requested if-statement block element.
   * @throws SDMException If the index is negative.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public String getAlternativeText(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getAlternativeText' method");
    
	  UMLAlternatives alternative = (UMLAlternatives)(diagram.getUMLSymbols().stream().filter(s -> s instanceof UMLAlternatives).collect(Collectors.toList()).get(index));
	  
	  // This statement is saying get the UMLCondition from UMLLoop, then get the string "condition" from UMLCondition
	  String condition = "";
      if (alternative.getCondition() != null) {
        condition = alternative.getCondition().getCondition();
        if (condition == null) condition = "";
      }
      return condition;
  }
  
  /**
   * Gets the text for the else section.
   * <br>
   * <br>
   * <b>Preconditions:</b> index must refer to a valid alternative (if-else) statement block in the sequence diagram.
   * <br>
   * <b>Postconditions:</b> Returns the else statement’s inner text.
   * 
   * @author Jacob Taylor
   * @param index The index of the requested element.
   * @return The else block text for the requested if-statement block element.
   * @throws SDMException If the index is negative.
   * @throws IndexOutOfBoundsException If the index is invalid.
   */
  public String getAlternativeTextElse(int index) throws Exception {
      if (index < 0) throw new SDMException("'index' parameter must be positive in: InputBeulahWorks 'getAlternativeTextElse' method");

	  // TODO: UMLElse does not contain any methods to get else-if condition text based on the given input model
	  return "else";
  }
  
}
