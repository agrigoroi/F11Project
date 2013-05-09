import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Roster
{
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
	// Map that contains which bus to which services is allocated
	public static void printToFile(String filePath, String toPrint)
	{
		try
		{
			// Create file
			FileWriter fstream = new FileWriter(filePath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(toPrint);
			//Close the output stream
			out.close();
		}
		catch (Exception e)
		{//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			System.exit(-1);
		}
	}

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

	public static String printTimetable(Route route, TimetableInfo.timetableKind dayType)
	{
		Calendar date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		long today = date.getTime().getTime();
		String timetableText = "";
		// Print the route name and the day
		timetableText += "Route: " + route.getName() + ", " + dayType
		              + "\n--------------------------------------------\n";
		// Get all the services
		Service[] services = route.getServices(dayType);
		for(Service service: services)
		{
			// Print the number of the service
			timetableText += "Service number: " + service.getID() + "\n";
			// Get all the timing points and print them
			TimingPoint[] stops = service.getTimingPoints();
			for(int j=0; j< stops.length; j++)
				timetableText += stops[j].getStop() + ": " + BusStopInfo.getFullName(stops[j].getStop()) + ": "
					          + simpleDateFormat.format(new Date(today+stops[j].getTime())) + "\n";
			timetableText += "Duration: " + service.getDuration() + " minutes\n\n";
		}
		return timetableText;
	}

	// Prints a full version of the bus timetable
	public static String printFullTimetable()
	{
		String timetableText = "";
		Route[] routes = Route.getAll();
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
		{
			for(Route route: routes)
			{
				// Print the route name and the day
				timetableText += "Route: " + route.getName() + ", " + dayType
				              + "\n--------------------------------------------\n";
				// Get all the services
				Service[] services = route.getServices(dayType);
				for(Service service: services)
				{
					// Print the number of the service
					timetableText += "Service number: " + service.getID() + "\n";
					// Get all the timing points and print them
					TimingPoint[] stops = service.getTimingPoints();
					for(int j=0; j< stops.length; j++)
						timetableText += stops[j].getStop() + ": " + BusStopInfo.getFullName(stops[j].getStop()) + ": "
							          + simpleDateFormat.format(stops[j].getTime()) + "\n";
					timetableText += "Duration: " + service.getDuration() + " minutes\n\n";
				}
			}
		}
		return timetableText;
	}

	public void run()
	{
	  database.openBusDatabase();
	  //System.out.println("Drivers\n------------------------");
          BusTimetable.printBusTimetable();
          DriverTimetable.printDriverTimetable();
	  printFullTimetable();
	  //System.out.println("Busses\n------------------------");
	}

	public static void main(String[] args)
	{
		database.openBusDatabase();
		Route[] routes = Route.getAll();
		for(TimetableInfo.timetableKind dayType: TimetableInfo.timetableKind.values())
			for(Route route: routes)
				printToFile("timetable/" + route.getName()+"_"+dayType, printTimetable(route, dayType));
		// Roster roster = new Roster();
		// roster.run();
	}
}
