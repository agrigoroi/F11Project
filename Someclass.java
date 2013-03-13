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
				int services[] = TimetableInfo.getServices(routeID, dayType);
				for(int index = 0; index < services.length; index++)
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
				int services[] = TimetableInfo.getServices(routeID, dayType);
				Long time  = new Date().getTime();
				time = time - time % (24 * 60 * 60 * 1000);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
				for(int index = 0; index < services.length; index++)
				{
					Service service = new Service(index, routeID, dayType);
					
					System.out.println("Service number: " + service.getID());
					System.out.println(BusStopInfo.getFullName(service.getStopID(0)) + ": "
									   + simpleDateFormat.format(new Date(time+service.getTime(0) * 60 * 1000)));
					int j=1;
					for (; j< service.getTimes().length; j++)
						System.out.println(BusStopInfo.getFullName(service.getStopID(j)) + ": "
							               + simpleDateFormat.format(new Date(time + service.getTime(j) * 60 * 1000)));
					System.out.println("Duration: " + service.getDuration() + " minutes");
					System.out.println();
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		database.openBusDatabase();
		
		HashMap<Integer, Integer> rosterDrivers = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> rosterBusses = new HashMap<Integer, Integer>();
		
		TimetableInfo.timetableKind dayType = TimetableInfo.timetableKind.weekday;
		int drivers[] = DriverInfo.getDrivers();
		Long time  = new Date().getTime();
		time = time - time % (24 * 60 * 60 * 1000);

		int minutesWorked[drivers.length] = new int[drivers.length];
		minutesWorked = {0};


		int numberOfDrivers = DriverInfo.getDrivers().length;
		double workPerDay = (getTotalDuration()*1.0)/(numberOfDrivers*7.0);
		for (int driver = 0; driver < drivers.length ;driver++)
		{
			int driverID = drivers[driver]
			for(int route = 0; route < routes.length; route++)
			{
				int routeID = routes[route];
				int services[] = TimetableInfo.getServices(routeID, dayType);
				for(int index = 1; index < services.length; index++)
				{
					Service service = new Service(services[index], routeID, dayType);
					if(!rosterDrivers.containsKey(service.getID()))
					{
						rosterDrivers.put(service.getID(), driverID);
						minutesWorked[driver] += service.getDuration();
						break;
					}
					rosterDrivers.put(service.getID());
				}
				if(minutesWorked[driver] > workPerDay)
					
			}
		}
		for(int i = 0; i < routes.length; i++)
		{
			int routeID = routes[i];
			int services[] = TimetableInfo.getServices(routeID, dayType);
			rosterDrivers.put(services[0], drivers[0]);
			minutesWorked[0] += new Service(service[0], routeID, dayType).getDuration();
			if(minutesWorked)

			for(int index = 1; index < services.length; index++)
			{
				Service service = new Service(services[index], routeID, dayType);
				rosterDrivers.put(service.getID());
			}
		}
	}
}
