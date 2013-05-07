import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Date;
import org.junit.Test;
import java.text.SimpleDateFormat;

public class TestJourney
{
  static Journey journey;
  static Date date;
  static Service service;
  static Route route;

  @BeforeClass
  public static void testSetup()
  {
    date = new Date();
    //These might fail
    route = new Route(358);
    service = new Service(1, route, TimetableInfo.timetableKind(date));
  }
  
  @AfterClass
  public static void testCleanup()
  {
  }
  
  //Test to see if Journey checks whether bus stops exist
  @Test(expected = IllegalArgumentException.class)
  public static void testJourneyBuild()
  {
    journey = new Journey(9873, 9893, date, date, service);
  }
  
  //Tests to see if Journey checks service number exists
  @Test(expected = IllegalArgumentException.class)
  public static void testJourneyBuild1()
  {
    journey = new Journey(987373, 9838393, date, date, service);
  }
}
