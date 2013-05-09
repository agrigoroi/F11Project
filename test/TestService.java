import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Date;
import org.junit.Test;

public class TestService
{

  static Route route;
  static Date date;
  static Service service;
  
  @BeforeClass
  public static void testSetup()
  {
    date = new Date();
    //may need to check this route
    route = new Route(358);
    Service service = new Service(1, route, TimetableInfo.timetableKind(date));
  }
  
  @AfterClass
  public static void testCleanup()
  {
  }
  
  //Test to see if it builds with a bad index
  @Test(expected = IllegalArgumentException.class)
  public static void testServiceBuild()
  {
    Service serviceBad = new Service(-1, route, TimetableInfo.timetableKind(date));
  }
  
  //may need to manually calculate these 2
  //small test to make sure this is calculated right
  @Test
  public static void testTimingPoints()
  {
    assertEquals("Timing point should be equal to X", 5, service.getNumberOfTimingPoints());
  }
  
  //small test to make sure the duration is calculated right
  @Test
  public static void testDuration()
  {
    assertEquals("Duration should be equal to X", 23, service.getDuration());
  }
  
}
