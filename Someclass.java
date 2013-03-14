import java.text.SimpleDateFormat;
import java.util.*;

public class Someclass 
{

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

		// printFullTimetable();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		
		HashMap<Integer, Integer> rosterDrivers = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> rosterBusses = new HashMap<Integer, Integer>();
		
		TimetableInfo.timetableKind dayType = TimetableInfo.timetableKind.weekday;
		int drivers[] = DriverInfo.getDrivers();
		Arrays.sort(drivers);
		int routes[] = BusStopInfo.getRoutes();

		int minutesWorked[] = new int[drivers.length];

		int numberOfDrivers = DriverInfo.getDrivers().length;
		double workPerDay = (getTotalDuration()*1.0)/(numberOfDrivers*7.0);
		for (int driver = 0; driver < drivers.length ;driver++)
		{
			int driverID = drivers[driver];
			Date nextAvailable = TimingPoint.getMidnight();
			System.out.print(driverID + ":");
			for(int route = 0; route < routes.length; route++)
			{
				int routeID = routes[route];
				// System.out.println("    Trying route " + routeID);
				int numberOfServices = TimetableInfo.getServices(routeID, dayType).length;
				for(int index = 1; index < numberOfServices; index++)
				{
					Service service = new Service(index, routeID, dayType);
					// System.out.println("        Trying Service " + service.getID());
					if(service.getTime(0).after(nextAvailable))
						if(!rosterDrivers.containsKey(service.getID()))
						{
							rosterDrivers.put(service.getID(), driverID);
							minutesWorked[driver] += service.getDuration();
							nextAvailable = service.getTime(service.getNumberOfTimingPoints()-1);
							System.out.print(" " + service.getID() + "(" + simpleDateFormat.format(service.getTime(0)) + ")");
							// System.out.print("            Driver is assigned to " + service.getID());
							// System.out.println(" next available is " + simpleDateFormat.format(nextAvailable));
							if(minutesWorked[driver] > workPerDay)
								break;
						}
						// else
							// System.out.println("            Service is already assigned");
					// else
						// System.out.println("            Driver is not available for this service");
				}					
				if(nextAvailable.getTime() - TimingPoint.getMidnight().getTime() > 0)
					break;
			}
			System.out.println(" ");
		}
	}
}
