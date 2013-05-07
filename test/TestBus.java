import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Date;
import org.junit.Test;

public class TestBus
{
  static Bus bus;  
  
  @BeforeClass
  public static void testSetup()
  {
    bus = new Bus(1);
  }
  
  @AfterClass
  public static void testCleanup()
  {
  }
  
  //Test to see if it accpets a bad ID
  @Test(expected = IllegalArgumentException.class)
  public static void testBuild()
  {
    Bus busBad = new Bus(-1);
  }
  
  //Checks to see if it gets every bus
  @Test
  public static void test()
  {
    //may need some fixing
    assertEquals("The number of buses should be 50", 50, bus.getAll().length);
  }
}
