import java.text.SimpleDateFormat;
import java.util.Date;


public class Someclass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		database.openBusDatabase();
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
				System.out.println(services.length);
			/*	Long time  = new Date().getTime();
				time = time - time % (24 * 60 * 60 * 1000);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
				for(int index = 0; index < services.length; index++)
				{
					System.out.println("Service number: " + services[index]);
					int times[] = TimetableInfo.getServiceTimes(routeID, dayType, index);
					System.out.println("Start time: " + simpleDateFormat.format(new Date(time+times[0] * 60 * 1000)));
					for (int j = 1; j< times.length - 1; j++)
						System.out.println("Timming point:" + simpleDateFormat.format(new Date(time + times[j] * 60 * 1000)));
					System.out.println("End time: " + simpleDateFormat.format(new Date(time+times[times.length-1] * 60 * 1000)));
					System.out.println("Duration: " + (times[times.length-1] - times[0]) + " minutes");
					System.out.println();
				}*/
			}
		}
	}
}
