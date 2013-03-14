import java.text.SimpleDateFormat;
import java.util.*;

public class Someclass {

	public static int getTotalDuration()
	{
		int routes[] = BusStopInfo.getRoutes();
		int totalDuration = 0;
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
		{
			int thisDuration = 0;
			for(int i = 0; i < routes.length; i++)
			{
				int routeID = routes[i];
				int numberOfServices = TimetableInfo.getServices(routeID, dayType).length;
				for(int index = 0; index < numberOfServices; index++)
				{
					Service service = new Service(index, routeID, dayType);
					thisDuration += service.getDuration();
				}
			}
			if(dayType == TimetableInfo.timetableKind.weekday)
				totalDuration += thisDuration*5;
			else
				totalDuration += thisDuration;
		}
		return totalDuration;
	}

	public static void printFullTimetable()
	{
		int routes[] = BusStopInfo.getRoutes();	
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
		{
			for(int i = 0; i < routes.length; i++)
			{
				int routeID = routes[i];
				System.out.println();
				System.out.println("Route: " + BusStopInfo.getRouteName(routeID) + ", " + dayType);
				System.out.println("--------------------------------------------");
				int numberOfServices = TimetableInfo.getServices(routeID, dayType).length;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
				for(int index = 0; index < numberOfServices; index++)
				{
					Service service = new Service(index, routeID, dayType);
					System.out.println("Service number: " + service.getID());
					for(int j=0; j< service.getTimingPoints().length; j++)
						System.out.println(BusStopInfo.getFullName(service.getStopID(j)) + ": "
							               + simpleDateFormat.format(service.getTime(j)));
					System.out.println("Duration: " + service.getDuration() + " minutes");
					System.out.println();
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		database.openBusDatabase();

		printFullTimetable();
		
		HashMap<Integer, Integer> rosterDrivers = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> rosterBusses = new HashMap<Integer, Integer>();
		
		TimetableInfo.timetableKind dayType = TimetableInfo.timetableKind.weekday;
		int drivers[] = DriverInfo.getDrivers();

		int minutesWorked[drivers.length] = new int[drivers.length];
		minutesWorked = {0};

		int numberOfDrivers = DriverInfo.getDrivers().length;
		double workPerDay = (getTotalDuration()*1.0)/(numberOfDrivers*7.0);

		for(int i = 0; i < routes.length; i++)
		{
			int routeID = routes[i];
			int numberOfServices = TimetableInfo.getServices(routeID, dayType).length;
			rosterDrivers.put(services[0], drivers[0]);
			minutesWorked[0] += new Service(service[0], routeID, dayType).getDuration();
			if(minutesWorked)

			for(int index = 1; index < numberOfServices; index++)
			{
				Service service = new Service(index, routeID, dayType);
				//rosterDrivers.put(service.getID());
			}
		}
	}
}
