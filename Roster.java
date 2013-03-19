import java.text.SimpleDateFormat;
import java.util.*;

public class Roster
{
	// Map that contains which bus to which services is allocated
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

	/**********************************************
	 * Driver Roster;
	 *
	 */

	public static HashMap<Service, Driver> generateDriverRoster(Date date)
	{
		return generateDriverRoster(TimetableInfo.timetableKind(date));
	}

	/**
	 * Assign drivers to services
	 */
	public static HashMap<Service, Driver> generateDriverRoster(TimetableInfo.timetableKind dayType)
	{
		// A map that contains the driver allocation to services
		HashMap<Service, Driver> rosterDrivers = new HashMap<Service, Driver>();
		// Get all the drivers, busses and routes
		Driver drivers[] = Driver.getAll();
		Route routes[] = Route.getAll();
		// If its weekday then its a special case, as the first service on route 0
		// doesnt have a equivalent service that goes back
		// so we just assign it to the first driver
		if(dayType == TimetableInfo.timetableKind.weekday)
			rosterDrivers.put(new Service(0, routes[0], dayType), drivers[0]);
		double workPerDay = (getTotalDuration(dayType)*1.0)/(drivers.length);
		workPerDay = workPerDay * 0.9;
		for (Driver driver: drivers)
		{
			// Keep track how much time this driver worked a given day
			int minutesWorked = 0;
			Date nextAvailable = null;
			Service[] services = routes[0].getServices(dayType);
			Service[] servicesBack = routes[1].getServices(dayType);
			if(driver.equals(drivers[0]))
			{
				minutesWorked = 9;
				nextAvailable = services[0].getTime(services[0].getNumberOfTimingPoints() - 1);
			}
			for(Service service: services)
			{
				// The service that goes back exactly after this one
				Service serviceback;
				// Find the index of the service going back exactly after this one
				int index;
				if(dayType == TimetableInfo.timetableKind.weekday)
					index = service.getIndex() + 1;
				else
					index = service.getIndex();
				serviceback = servicesBack[index];
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
							minutesWorked += (int)(difference/(1000*60));
						}
						else
						{
							long difference = serviceback.getTime(service.getNumberOfTimingPoints()-1).getTime() - nextAvailable.getTime();
							minutesWorked += (int)(difference/(1000*60));
						}
						nextAvailable = serviceback.getTime(serviceback.getNumberOfTimingPoints()-1);
						// If the driver worked enough today then assign services for the next drivers
						if(minutesWorked > workPerDay)
							break;
					}
			}
			// Did we assign enough work to this driver?
			if(minutesWorked != 0)
				continue;
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
								minutesWorked += service.getDuration();
							else
							{
								long difference = service.getTime(service.getNumberOfTimingPoints()-1).getTime() - nextAvailable.getTime();
								minutesWorked += (int)(difference/(1000*60));
							}
							nextAvailable = service.getTime(service.getNumberOfTimingPoints()-1);
							if(minutesWorked > workPerDay)
								break;
						}
				}
				//Did this driver get any assignment?			
				if(minutesWorked > 0)
					break;
			}
			// System.out.println(" ");
		}
		return rosterDrivers;
	}


	/**********************************************
	 * Bus Roster;
	 *
	 */

	public static HashMap<Service, Bus> generateBusRoster(Date date)
	{
		return generateBusRoster(TimetableInfo.timetableKind(date));
	}

	/**
	 * Assign busses to services
	 */
	public static HashMap<Service, Bus> generateBusRoster(TimetableInfo.timetableKind dayType)
	{
		// A map that contains the bus allocation to services
		HashMap<Service, Bus> rosterBusses = new HashMap<Service, Bus>();
		// Get all the Busses, busses and routes
		Bus busses[] = Bus.getAll();
		Route routes[] = Route.getAll();
		// If its weekday then its a special case, as the first service on route 0
		// doesnt have a equivalent service that goes back
		// so we just assign it to the first driver
		if(dayType == TimetableInfo.timetableKind.weekday)
			rosterBusses.put(new Service(0, routes[0], dayType), busses[0]);
		for (Bus bus: busses)
		{
			// Keep track how much time this bus worked a given day
			int minutesWorked = 0;
			Date nextAvailable = null;
			Service[] services = routes[0].getServices(dayType);
			Service[] servicesBack = routes[1].getServices(dayType);
			if(busses.equals(busses[0]))
				nextAvailable = services[0].getTime(services[0].getNumberOfTimingPoints() - 1);
			for(Service service: services)
			{
				// The service that goes back exactly after this one
				Service serviceback;
				// Find the index of the service going back exactly after this one
				int index;
				if(dayType == TimetableInfo.timetableKind.weekday)
					index = service.getIndex() + 1;
				else
					index = service.getIndex();
				serviceback = servicesBack[index];
				// Check if the busses is available for this service and if the service is unassigned
				if((nextAvailable == null) || (service.getTime(0).after(nextAvailable)))
					if(!rosterBusses.containsKey(service))
					{
						// Assign the busses to this service and service that returns back
						rosterBusses.put(service, bus);
						rosterBusses.put(serviceback, bus);
						// Check when the bus will be available
						nextAvailable = serviceback.getTime(serviceback.getNumberOfTimingPoints()-1);
						// If the bus worked enough today then assign services for the next when
					}
			}
			// Did we assign enough work to this bus?
			if(nextAvailable != null)
				continue;
			// Assign the circular services now
			for(int routeIndex = 2; routeIndex < routes.length; routeIndex++)
			{
				Route route = routes[routeIndex];
				services = route.getServices(dayType);
				for(Service service: services)
				{
					// Check if the bus is available for this service and if the service is not assigned yet
					if((nextAvailable == null)||(service.getTime(0).after(nextAvailable)))
						if(!rosterBusses.containsKey(service))
						{
							// Assign the service to this bus
							rosterBusses.put(service, bus);
							// Compute next time he is available
							nextAvailable = service.getTime(service.getNumberOfTimingPoints()-1);
						}
				}
				if(nextAvailable != null)
					break;
			}
		}
		return rosterBusses;
	}

	public static void printDriverTimetable()
	{
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
		{
			System.out.println(dayType);
			System.out.println("--------------------------------");
			HashMap<Service, Driver> rosterDrivers = generateDriverRoster(dayType);
			//print the hashmap in a more prettier way;
			// A map that links each driver to a list of services he is assigned to;
			HashMap<Driver, ArrayList<Service>> reversedRosterDrivers = new HashMap<Driver, ArrayList<Service>>();
			for(Map.Entry<Service, Driver> rosterEntry: rosterDrivers.entrySet())
			{
				Driver thisDriver = (Driver)rosterEntry.getValue();
				if(reversedRosterDrivers.containsKey(thisDriver))
					reversedRosterDrivers.get(thisDriver).add((Service)rosterEntry.getKey());
				else
				{
					ArrayList<Service> assignedServices = new ArrayList<Service>();
					assignedServices.add((Service)rosterEntry.getKey());
					reversedRosterDrivers.put(thisDriver, assignedServices);
				}
			}
			for(Map.Entry<Driver, ArrayList<Service>> rosterEntry: reversedRosterDrivers.entrySet())
			{
				System.out.print(((Driver)rosterEntry.getKey()).getID() + ": ");
				ArrayList<Service> assignedServices = (ArrayList<Service>)rosterEntry.getValue();
				for(Service service: assignedServices)
				{
					System.out.print(service.getID() + "(" + simpleDateFormat.format(service.getTime(0)) + " -- " + simpleDateFormat.format(service.getTime(service.getNumberOfTimingPoints() - 1)) + ") ");
				}
				System.out.println(" ");
			}
		}
	}

		public static void printBusTimetable()
	{
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
		{
			System.out.println(dayType);
			System.out.println("--------------------------------");
			HashMap<Service, Bus> rosterBusses = generateBusRoster(dayType);
			//print the hashmap in a more prettier way;
			// A map that links each bus to a list of services he is assigned to;
			HashMap<Bus, ArrayList<Service>> reversedRosterBusses = new HashMap<Bus, ArrayList<Service>>();
			for(Map.Entry<Service, Bus> rosterEntry: rosterBusses.entrySet())
			{
				Bus thisBus = (Bus)rosterEntry.getValue();
				if(reversedRosterBusses.containsKey(thisBus))
					reversedRosterBusses.get(thisBus).add((Service)rosterEntry.getKey());
				else
				{
					ArrayList<Service> assignedServices = new ArrayList<Service>();
					assignedServices.add((Service)rosterEntry.getKey());
					reversedRosterBusses.put(thisBus, assignedServices);
				}
			}
			for(Map.Entry<Bus, ArrayList<Service>> rosterEntry: reversedRosterBusses.entrySet())
			{
				System.out.print(((Bus)rosterEntry.getKey()).getID() + ": ");
				ArrayList<Service> assignedServices = (ArrayList<Service>)rosterEntry.getValue();
				for(Service service: assignedServices)
				{
					System.out.print(service.getID() + "(" + simpleDateFormat.format(service.getTime(0)) + " -- " + simpleDateFormat.format(service.getTime(service.getNumberOfTimingPoints() - 1)) + ") ");
				}
				System.out.println(" ");
			}
		}
	}

	public static void main(String[] args)
	{
		database.openBusDatabase();
		System.out.println("Drivers\n------------------------");
		printDriverTimetable();
		System.out.println("Busses\n------------------------");
		printBusTimetable();
	}
}
