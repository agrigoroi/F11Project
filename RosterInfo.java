
import java.util.*;
import static java.util.Calendar.*;

/**
 * A class providing information about timetables. This is given in a low-level
 * manner, e.g. with arrays numbers representing service times. The IBMS
 * itself should implement sensible classes to represent this information
 * in a more high-level intuitive way.<br><br>
 * 
 * A Service identifies a row of the timetable. The times for each service,
 * along with the timing point information in the BusStopInfo class provide
 * all the data in the timetable. 
 * 
 * None of the UCs in the pilot IBMS change timetable information, so this
 * interface provides read-only access
 */
public class RosterInfo
{
  private static Calendar calendar = new GregorianCalendar();

  // This class is not intended to be instantiated
  private RosterInfo() 
  { 
  }

  public static int getRoster(int service, Date date)
  {
    return database.busDatabase.find_id("timetable_id", "timetable", "service", service, date);
  }

  public static int getDriver(int timetable_id)
  {
    return database.busDatabase.get_int("timetable", timetable_id, "driver");
  }

  public static int getBus(int timetable_id)
  {
    return database.busDatabase.get_int("timetable", timetable_id, "bus");
  }

  public static void insert(int service, Date date, int driver, int bus)
  {
      database.busDatabase.new_record("timetable", new Object[][]{{"service", service}, {"date", date}, {"driver", driver}, {"bus", bus}});
  }
}
