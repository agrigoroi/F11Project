import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Date;
import org.junit.Test;

public class TestRequest
{
  static Request request;
  
  @BeforeClass
  public static void testSetup()
  {
  }
  
  @AfterClass
  public static void testCleanup()
  {
  }
  
  //Test to see if checks the id number
  @Test(expected = IllegalArgumentException.class)
  public static void testRequestBuild() throws Exception
  {
    request = new Request(-1);
  }
  
  //Test to see if the strings are checked
  @Test(expected = IllegalArgumentException.class)
  public static void testRequestBuild1() throws Exception
  {
    //Get a valid driverID number
    Driver driver = new Driver(1);
    request = new Request("incorrect", "incorrrect", driver);
  }
  
  //test to see if requests date length is too big
  @Test(expected = IllegalArgumentException.class)
  public static void testRequestBuild2() throws Exception
  {
    Driver driver = new Driver(1);
    Date date = new Date();
    Date date2 = date;
    date2.setYear(3014);
    request = new Request(date, date2, driver);
  }
  
  //test to see if start date is after end date
  @Test(expected = IllegalArgumentException.class)
  public static void testRequestBuild3() throws Exception
  {
    Driver driver = new Driver(1);
    Date date = new Date();
    Date date2 = date;
    date2.setYear(1014);
    request = new Request(date, date2, driver);
  }
}
