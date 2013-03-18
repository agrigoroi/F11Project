import java.text.SimpleDateFormat;
import java.util.*;

public class Someclass 
{

	public static int getTotalDuration(TimetableInfo.timetableKind dayType)
	{
		int routes[] = BusStopInfo.getRoutes();
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
		return thisDuration;
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
		
		HashMap<Service, Driver> rosterDrivers = new HashMap<Service, Driver>();
		HashMap<Integer, Bus> rosterBusses = new HashMap<Service, Bus>();
		
		Driver drivers[] = Driver.getAll();
		Bus busses[] = Bus.getAll();
		Route routes[] = Route.getAll();

		int numberOfDrivers = drivers.length;
		
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
		{
			int minutesWorked[] = new int[drivers.length];
			if(dayType == TimetableInfo.timetableKind.weekday)
			{
				rosterDrivers.put(new Service(6460, route[0], dayType), drivers[0]);
				minutesWorked[0] += 9;
			}
			System.out.println(dayType);
			System.out.println("-----------------------------------------");
			double workPerDay = (getTotalDuration(dayType)*1.0)/(numberOfDrivers);
			workPerDay = workPerDay * 0.9;
			System.out.println(workPerDay);
			for (Driver driver: drivers)
			{
				int driverID = drivers[driver];
				Date nextAvailable = null;
				System.out.print(driverID + ":");
				
				int numberOfServices = TimetableInfo.getServices(routes[0].getID, dayType).length;
				Service[] services = routes[0].getServices(dayType);
				for(int index = 0; index < numberOfServices; index++)
					{
						Service service = new Service(index, routes[0], dayType), serviceback;
						if(dayType == TimetableInfo.timetableKind.weekday)
							serviceback = new Service(index+1, routes[1], dayType);
						else
							serviceback = new Service(index, routes[1], dayType);
						// System.out.println("        Trying Service " + service.getID());
						if((nextAvailable == null) || (service.getTime(0).after(nextAvailable)))
							if(!rosterDrivers.containsKey(service.getID()))
							{
								rosterDrivers.put(service.getID(), driverID);
								rosterDrivers.put(serviceback.getID(), driverID);
								//rosterBusses.put(service.getID(), busses[index]);
								if(nextAvailable == null)
								{
									long difference = serviceback.getTime(serviceback.getNumberOfTimingPoints()-1).getTime() - service.getTime(0).getTime();
									minutesWorked[driver] += (int)(difference/(1000*60));
								}
								else
								{
									long difference = serviceback.getTime(service.getNumberOfTimingPoints()-1).getTime() - nextAvailable.getTime();
									minutesWorked[driver] += (int)(difference/(1000*60));
								}
								nextAvailable = serviceback.getTime(serviceback.getNumberOfTimingPoints()-1);
								System.out.print(" " + service.getID() + "(" + simpleDateFormat.format(service.getTime(0)) + ")" + minutesWorked[driver]);
								System.out.print(" " + serviceback.getID() + "(" + simpleDateFormat.format(serviceback.getTime(0)) + ")" + minutesWorked[driver]);
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
				if(minutesWorked[driver] != 0)
				{
					System.out.println(" ");
					continue;
				}
				for(int route = 2; route < routes.length; route++)
				{
					int routeID = routes[route];
					// System.out.println("    Trying route " + routeID);
					numberOfServices = TimetableInfo.getServices(routeID, dayType).length;
					for(int index = 0; index < numberOfServices; index++)
					{
						Service service = new Service(index, routeID, dayType);
						// System.out.println("        Trying Service " + service.getID());
						if((nextAvailable == null)||(service.getTime(0).after(nextAvailable)))
							if(!rosterDrivers.containsKey(service.getID()))
							{
								rosterDrivers.put(service.getID(), driverID);
								if(nextAvailable == null)
									minutesWorked[driver] += service.getDuration();
								else
								{
									long difference = service.getTime(service.getNumberOfTimingPoints()-1).getTime() - nextAvailable.getTime();
									minutesWorked[driver] += (int)(difference/(1000*60));
								}
								nextAvailable = service.getTime(service.getNumberOfTimingPoints()-1);
								System.out.print(" " + service.getID() + "(" + simpleDateFormat.format(service.getTime(0)) + ")" + minutesWorked[driver]);
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
					if(minutesWorked[driver] > 0)
						break;
				}
				System.out.println(" ");
			}
		}
		for (Map.Entry entry : rosterDrivers.entrySet())
		{
    		System.out.println(entry.getKey() + ": " + entry.getValue());
		}

	}
}
