import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Date;
import org.junit.Test;

//Could try and get a few more tests in here
public class TestBusTimetable
{
  static BusTimetable busTimetable;  
  static Roster roster;
  
  @BeforeClass
  public static void testSetup()
  {
    roster
  }
  
  @AfterClass
  public static void testCleanup()
  {
  }
  
  
  @Test
  public static void testGetBus()
  {
    //needs fixing
    assertEquals("Should return bus ID 7", 7, BusTimetable.getBus(6017));
  }
}
