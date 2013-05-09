import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Date;
import org.junit.Test;

public class TestJourneyPlanner {

  static Journey[] journeys;
  static Journey[] midnight;
  
  @BeforeClass
  public static void testSetup() 
  {
    JourneyPlanner tester = new JourneyPlanner();
    Date date = new Date();
    journeys = tester.dijkstra(781, 783, date);
    //find apprpriate times and stops
    //midnight = tester.dijkstra(x,x, 00:00);
  }

  @AfterClass
  public static void testCleanup() 
  {
    // Teardown for data used by the unit tests
  }

  @Test
  public void testDepart() 
  {
    assertEquals("The departing stop must be the same", 781, journeys[0].getDepartBusStop());
  }
  
  @Test 
  public void testArrival()
  {
    assertEquals("The final stop must be the same", 783, journeys[journeys.length-1].getArrivalBusStop());
  }
  
  //I saw something about the journey not being allowed to make the passenger take an after midnight
  //bus and then wait 5 hours for the next one. This tests for that.
  @Test 
  public void testMidnight()
  {
    //need to sort out the time bit at the end of here I think
    //boolean bool = (midnight[midnight.length-1].getArrivalTime().getTime() - midnight[0].getDepartTime().getTime()) < 5;
    //assertTrue("The journey isn't allowed to make passenger wait", bool);
  }
} 
