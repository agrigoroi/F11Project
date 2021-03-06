import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.PriorityQueue;

public class JourneyPlanner
{
	
	private static class QueueItem implements Comparable<QueueItem>
	{
		public int stop;
		public Date time;
		public Journey journey;

		public QueueItem(int stop, Date time, Journey journey)
		{
			this.stop = stop;
			this.time = time;
			this.journey = journey;
		}

		public int compareTo(QueueItem other)
		{
			return time.compareTo(other.time);
		}
	}

	private static ArrayList<Journey> makeJourney(HashMap<Integer, Journey> path, int endStop)
	{
		ArrayList<Journey> journeys = new ArrayList<Journey>();
		int thisStop = endStop;
		while(true)
		{
			Journey thisJourney = path.get(thisStop);
			if(thisJourney == null)
				break;
			journeys.add(0, thisJourney);
			thisStop = thisJourney.getDepartBusStop();
		}
		return journeys;
	}

	public static ArrayList<Journey> dijkstra(String startStop, String endStop, Date time)
	{
		Calendar date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		long today = date.getTime().getTime();
		PriorityQueue<QueueItem> queue = new PriorityQueue<QueueItem>();
		HashMap<Integer, Journey> path = new HashMap<Integer, Journey>();
		int[] startStops = BusStopInfo.getBusStopIds(startStop);
		for(int i=0;i<startStops.length;i++)
			queue.add(new QueueItem(startStops[i], time, null));
		QueueItem next = queue.poll();
		while(next != null)
		{
			while((next != null) && (path.containsKey(next.stop)))
				next = queue.poll();
			if(next == null)
				break;
			path.put(next.stop, next.journey);
			if(BusStopInfo.getFullName(next.stop).equals(endStop))
				return makeJourney(path, next.stop);
			Route[] routes = Route.getRoutes(next.stop);
			for(Route route: routes)
			{
				if(next.journey != null)
					if(next.journey.getService().getRoute().getID() == route.getID())
						continue;
				Service[] services = route.getServices(next.time);
				Service nextService = null;
				Date nextServiceTime = null;
				for(Service service: services)
				{
					TimingPoint[] timingPoints = service.getTimingPoints();
					for(TimingPoint timingPoint: timingPoints)
						if(timingPoint.getStop() == next.stop)
						{
							Date ServiceTime = new Date(today+timingPoint.getTime());
							if(ServiceTime.compareTo(next.time)>0)
								if((nextServiceTime == null)||(nextServiceTime.compareTo(ServiceTime)>0))
								{
									nextService = service;
									nextServiceTime = ServiceTime;
								}
							break;
						}
				}
				if(nextService == null)
					continue;
				TimingPoint[] timingPoints = nextService.getTimingPoints();
				int timingPointID = 0;
				while(timingPoints[timingPointID].getStop() != next.stop)
					timingPointID++;
				timingPointID++;
				while(timingPointID < timingPoints.length)
				{
					int[] nextStops = BusStopInfo.getBusStopIds(BusStopInfo.getFullName(timingPoints[timingPointID].getStop()));
					for(int i=0;i<nextStops.length;i++)
						if(path.containsKey(nextStops[i]) == false)
						{
							Date arrivalTime = new Date(today+timingPoints[timingPointID].getTime());
							queue.add(new QueueItem(nextStops[i], arrivalTime,
													new Journey(next.stop, nextStops[i], nextServiceTime, arrivalTime, nextService)));
						}
					timingPointID++;
				}
			}
			next = queue.poll();
		}
		return null;
  }


	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
	public static void main(String args[])
	{
		//Testing this stuff...
		database.openBusDatabase();
		// int[] stops = BusStopInfo.getBusStopIds("Stockport, Bus Station");
		// for(int i=0;i<stops.length;i++)
		// {
		// 	System.out.println(stops[i]);
		// }
//		System.out.println(Roster.printFullTimetable());
		ArrayList<Journey> journeys = dijkstra("Hayfield, Bus Station", "Romiley, Train Station", new Date());
		for(Journey journey: journeys)
		{
			System.out.println("Take bus " + journey.getService().getRoute().getName() +" from " + BusStopInfo.getFullName(journey.getDepartBusStop()) + " to " + BusStopInfo.getFullName(journey.getArrivalBusStop()));
			System.out.println("Leaves at " + simpleDateFormat.format(journey.getDepartTime()) + " and arrives at " +simpleDateFormat.format(journey.getArrivalTime()));
			System.out.println();
		}
	}
}
