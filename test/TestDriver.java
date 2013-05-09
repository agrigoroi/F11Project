import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Date;
import org.junit.Test;

public class TestDriver
{
  static Driver driver;

  @BeforeClass
  public static void testSetup()
  {
    driver = new Driver(1);
  }
  
  @AfterClass
  public static void testCleanup()
  {
  }
  
  //Checks if it accpets a bad ID
  @Test(expected = IllegalArgumentException.class)
  public static void testDriverBuild()
  {
    Driver driverBad = new Driver(-1);
  }
  
  //Checks if it accpets a bad driver number
  @Test(expected = IllegalArgumentException.class)
  public static void testDriverBuild2() throws Exception
  {
    Driver driverBad2 = new Driver("0");
  }
  
  @Test
  public static void testGetAllDrivers()
  {
    //needs fiddling
    assertEquals("Should return 50 drivers", 50, driver.getAll().length);
  }
  
  
  @Test
  public static void testgetHolidaysLeft()
  {
    //needs fiddling
    assertEquals("Should return 10 days",10, driver.getHolidaysLeft());
  }
  
  @Test
  public static void testIsAvailable()
  {
    Date date = new Date();
    date.setYear(2008);
    assertTrue("Should return True", driver.isAvailable(date));
  }
}
