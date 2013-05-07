import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Date;
import org.junit.Test;

public class TestStop
{
  static Stop stop;
  
  @BeforeClass
  public static void testSetup()
  {
    //need this to be a correct stop
    stop = new Stop("areacode", "name");
  }
  
  @AfterClass
  public static void testCleanup()
  {
  }
  
  //test to see if can handle wrong area codes
  @Test(expected = IllegalArgumentException.class)
  public static void testStopBuild()
  {
    Stop stopBad = new Stop("foo", "bar");
  }
  
  //Check if it returns the correct number of routes
  @Test
  public static void testNoOfRoutes()
  {
    assertEquals("Route array length should be equal", 3, stop.getRoutes().length);
  }
  
  //Check if it returns the correct routes
  @Test
  public static void testRoutes()
  {
    //enter correct stuff here
    assertEquals("Route 0 should be whatever", 0, stop.getRoutes()[0].getID());
    assertEquals("Route 1 should be whatever", 1, stop.getRoutes()[1].getID());
    assertEquals("Route 2 should be whatever", 2, stop.getRoutes()[2].getID());
  }
}
