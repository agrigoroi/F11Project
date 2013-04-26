import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestRosterInfo
{
	public static void main(String[] args)
	{
		// today    
		Calendar date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		Date day = date.getTime();
		database.openBusDatabase();
		RosterInfo.insert(234, day, 1, 2);
		// today    
		date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		day = date.getTime();
		int id = RosterInfo.findID(234, day);
		System.out.println(id + ": " + RosterInfo.getDriver(id) + ", " + RosterInfo.getBus(id));
	}
}