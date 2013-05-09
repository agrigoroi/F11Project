import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Date;
import org.junit.Test;

public class TestJourney
{
  @BeforeClass
  public static void testSetup()
  {
  }
  
  @AfterClass
  public static void testCleanup()
  {
  }
  
  //Test if it can handle bad timetable IDs
  @Test(expected = IllegalArgumentException.class)
  public static void testBuild()
  {
    RosterInfo.getDriver(-1);
  }
  
  //Test if it can handle bad timetable IDs
  @Test(expected = IllegalArgumentException.class)
  public static void testBuild()
  {
    RosterInfo.getBus(-1);
  }
}
