import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class DriverTimetable
{
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
	/**
	 * Assign drivers to services
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
		double workPerDay = (Roster.getTotalDuration(dayType)*1.0)/(drivers.length);
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

	private static String makeDriverTimetable(ArrayList<Service> assignedServices)
	{
		String timetableText = "";
		Collections.sort(assignedServices);
		for(Service service: assignedServices)
		{
			timetableText = timetableText + "Service number: " + service.getID() + "\n";
			timetableText = timetableText + "Bus number: "  + BusTimetable.getBus(service.getID()) + "\n";
			TimingPoint[] stops = service.getTimingPoints();
			for(int j=0; j< stops.length; j++)
				timetableText = timetableText + BusStopInfo.getFullName(stops[j].getStop()) + ": "
							  + simpleDateFormat.format(stops[j].getTime()) + "\n";
			timetableText += "\n";
		}
		return timetableText;
	}

	public static void printDriverTimetable()
	{
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
		{
			//System.out.println(dayType);
			//System.out.println("--------------------------------");
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
				Roster.printToFile("../roster/"+dayType+"/"+((Driver)rosterEntry.getKey()).getID(),
					        makeDriverTimetable((ArrayList<Service>)rosterEntry.getValue()));
		
			}
		}
	}
}
