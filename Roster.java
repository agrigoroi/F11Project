import java.text.SimpleDateFormat;
import java.util.*;

public class Someclass 
{
	// A map that contains the driver allocation to services
	private static HashMap<Service, Driver> rosterDrivers = new HashMap<Service, Driver>();
	// Map that contains which bus to which services is allocated
	private static HashMap<Service, Bus> rosterBusses = new HashMap<Service, Bus>();
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");


	// Return the sum of duration of all services on a given dayType;
	public static int getTotalDuration(TimetableInfo.timetableKind dayType)
	{
		Route[] routes = Route.getAll();
		int thisDuration = 0;
		for(Route route: routes)
		{
			Service[] services = route.getServices(dayType);
			for(Service service: services)
				thisDuration += service.getDuration();
		}
		return thisDuration;
	}

	// Prints a full version of the bus timetable
	public static void printFullTimetable()
	{
		Route[] routes = Route.getAll();
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
		{
			for(Route route: routes)
			{
				System.out.println();
				// Print the route name and the day
				System.out.println("Route: " + route.getName() + ", " + dayType);
				System.out.println("--------------------------------------------");
				// Get all the services
				Service[] services = route.getServices(dayType);
				for(Service service: services)
				{
					// Print the number of the service
					System.out.println("Service number: " + service.getID());
					// Get all the timing points and print them
					TimingPoint[] stops = service.getTimingPoints();
					for(int j=0; j< stops.length; j++)
						System.out.println(BusStopInfo.getFullName(stops[j].getStop()) + ": "
							               + simpleDateFormat.format(stops[j].getTime()));
					System.out.println("Duration: " + service.getDuration() + " minutes");
					System.out.println();
				}
			}
		}
	}

	/**
	 * Assign drivers to services
	 */
	public static void generateDriverRoster()
	{
		// Get all the drivers, busses and routes
		Driver drivers[] = Driver.getAll();
		Bus busses[] = Bus.getAll();
		Route routes[] = Route.getAll();
		
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
		{
			// Keep track how much each driver worked a given day
			HashMap<Driver, Integer> minutesWorked = new HashMap<Driver, Integer>();
			for(Driver driver: drivers)
				minutesWorked.put(driver, 0);
			// If its weekday then its a special case, as the first service on route 0
			// doesnt have a equivalent service that goes back
			// so we just assign it to the first driver
			if(dayType == TimetableInfo.timetableKind.weekday)
			{
				rosterDrivers.put(new Service(0, routes[0], dayType), drivers[0]);
				minutesWorked.put(drivers[0], minutesWorked.get(drivers[0]) + 9);
			}
			System.out.println(dayType);
			System.out.println("-----------------------------------------");
			double workPerDay = (getTotalDuration(dayType)*1.0)/(drivers.length);
			workPerDay = workPerDay * 0.9;
			// System.out.println(workPerDay);
			for (Driver driver: drivers)
			{
				Date nextAvailable = null;
				System.out.print(driver.getID() + ":");
				Service[] services = routes[0].getServices(dayType);
				for(Service service: services)
					{
						// The service that goes back exactly after this one
						Service serviceback;
						if(dayType == TimetableInfo.timetableKind.weekday)
							serviceback = new Service(service.getIndex()+1, routes[1], dayType);
						else
							serviceback = new Service(service.getIndex(), routes[1], dayType);
						// Check if the driver is available for this service and if the service is unassigned
						if((nextAvailable == null) || (service.getTime(0).after(nextAvailable)))
							if(!rosterDrivers.containsKey(service))
							{
								// Assign the driver to this service and service that returns back
								rosterDrivers.put(service, driver);
								rosterDrivers.put(serviceback, driver);
								// Check how much time the driver will work and when it will be available
								if(nextAvailable == null)
								{
									long difference = serviceback.getTime(serviceback.getNumberOfTimingPoints()-1).getTime() - service.getTime(0).getTime();
									minutesWorked.put(driver, minutesWorked.get(driver) + (int)(difference/(1000*60)));
								}
								else
								{
									long difference = serviceback.getTime(service.getNumberOfTimingPoints()-1).getTime() - nextAvailable.getTime();
									minutesWorked.put(driver, minutesWorked.get(driver) + (int)(difference/(1000*60)));
								}
								nextAvailable = serviceback.getTime(serviceback.getNumberOfTimingPoints()-1);
								System.out.print(" " + service.getID() + "(" + simpleDateFormat.format(service.getTime(0)) + ")");
								System.out.print(" " + serviceback.getID() + "(" + simpleDateFormat.format(serviceback.getTime(0)) + ")" + minutesWorked.get(driver));
								// If the driver worked enough today then assign services for the next drivers
								if(minutesWorked.get(driver) > workPerDay)
									break;
							}
					}
				// Did we assign enough work to this driver?
				if(minutesWorked.get(driver) != 0)
				{
					System.out.println(" ");
					continue;
				}
				// Assign the circular services now
				for(int routeIndex = 2; routeIndex < routes.length; routeIndex++)
				{
					Route route = routes[routeIndex];
					services = route.getServices(dayType);
					for(Service service: services)
					{
						// Check if the driver is available for this service and if the service is not assigned yet
						if((nextAvailable == null)||(service.getTime(0).after(nextAvailable)))
							if(!rosterDrivers.containsKey(service))
							{
								// Assign the service to this driver
								rosterDrivers.put(service, driver);
								// Compute how much time this driver will work and next time he is available
								if(nextAvailable == null)
									minutesWorked.put(driver, minutesWorked.get(driver) + service.getDuration());
								else
								{
									long difference = service.getTime(service.getNumberOfTimingPoints()-1).getTime() - nextAvailable.getTime();
									minutesWorked.put(driver, minutesWorked.get(driver) + (int)(difference/(1000*60)));
								}
								nextAvailable = service.getTime(service.getNumberOfTimingPoints()-1);
								System.out.print(" " + service.getID() + "(" + simpleDateFormat.format(service.getTime(0)) + ")" + minutesWorked.get(driver));
								// System.out.print("            Driver is assigned to " + service.getID());
								// System.out.println(" next available is " + simpleDateFormat.format(nextAvailable));
								if(minutesWorked.get(driver) > workPerDay)
									break;
							}
					}
					//Did this driver get any assignment?			
					if(minutesWorked.get(driver) > 0)
						break;
				}
				System.out.println(" ");
			}
		}
	}

	public static void main(String[] args)
	{
		database.openBusDatabase();

		// printFullTimetable();
		generateDriverRoster();
	}
}
