package com.beulahworks.SDMfileGenerator;

/**
 * Contains the methods used by SDMtoFile for reading the information from 
 * a sequence diagram, which is then sent to an output adapter.
 * <br>
 * <br>
 * Traceability: Method interfaces are given in Section 5.2 of the SDD.
 * <br>
 * Traceability: Class design is given in Sections 6.1.4 and 6.2.4 of the SDD.
 * 
 * See a concrete implementation for method-specific comments.
 * 
 * @author Jesse Primiani
 */
public interface InputAdapter {
  
  public int getActorCount();
  public String getActorName(int index) throws Exception;
  
  public int getClassBlockCount();
  public String getClassBlockInstanceName(int index) throws Exception;
  public String getClassBlockClassName(int index) throws Exception;
  
  public int getActivationBlockCount();
  
  public int getLifelineCount();
  public int getLifelineFromIndex(int index) throws Exception;
  public int getLifelineToIndex(int index) throws Exception;
  public boolean getLifelineActive(int index) throws Exception;
  
  public int getMethodCount();
  public int getMethodFromIndex(int index) throws Exception;
  public int getMethodToIndex(int index) throws Exception;
  public String getMethodText(int index) throws Exception;
  
  public int getConstraintCount();
  public String getConstraintText(int index) throws Exception;
  public int getLoopCount();
  public String getLoopText(int index) throws Exception;
  public int getAlternativeCount();
  public String getAlternativeText(int index) throws Exception;
  public String getAlternativeTextElse(int index) throws Exception;

}
