import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class BusTimetable
{

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

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

	public static void printBusTimetable()
	{
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
		{
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
}