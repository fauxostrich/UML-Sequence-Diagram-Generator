package com.beulahworks.SDMfileGenerator;

/**
 * Contains the methods used by SDMtoFile for collecting the information needed 
 * to export a sequence diagram to a file.
 * <br>
 * <br>
 * Traceability: Method interfaces are given in Section 5.2 of the SDD.
 * <br>
 * Traceability: Class design is given in Sections 6.1.6 and 6.2.6 of the SDD.
 * 
 * See a concrete implementation for method-specific comments.
 * 
 * @author Jesse Primiani
 */
public interface OutputAdapter {
  
  public String initializeDiagram() throws Exception;
  
  public String addActor(String actorName) throws Exception;
  public String addClassBlock(String instanceName, String className) throws Exception;
  public String addActivationBlocks(int count) throws Exception;
  
  public String addLifeline(int fromIndex, int toIndex, boolean active) throws Exception;
  public String addMethod(int fromIndex, int toIndex, String text) throws Exception;
  
  public String addConstraint(String text) throws Exception;
  public String addLoop(String text) throws Exception;
  public String addAlternative(String text, String textElse) throws Exception;
  
  public String finalizeDiagram() throws Exception;
  
  public String saveToFile(String path, String name, OutputType<?> type, boolean overwrite) throws Exception;

}
