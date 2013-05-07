import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Date;
import org.junit.Test;

public class TestJourney
{

  static Route route;
  
  @BeforeClass
  public static void testSetup()
  {
    //may need to check this route
    route = new Route(358);
  }
  
  @AfterClass
  public static void testCleanup()
  {
  }
  
  @Test(expected = IllegalArgumentException.class)
  public static void testServiceBuild()
  {
    Date date = new date();
    Service service = new Service(-1, route, TimetableInfo.timetablekind(date));
  }
  
}
