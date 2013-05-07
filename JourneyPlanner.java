import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public static Route[] getRoutes(int stop)
	{
		int[] routesID = BusStopInfo.getRoutes(stop);
		Route[] routes = new Route[routesID.length];
		for(int i = 0; i < routesID.length; i++)
			routes[i] = new Route(routesID[i]);
		return routes;
	}
	
	private static Journey[] makeJourney(HashMap<Integer, Journey> path, int startStop, int endStop)
	{
		ArrayList<Journey> journeys = new ArrayList<Journey>();
		int thisStop = endStop;
		while(thisStop != startStop)
		{
			Journey thisJourney = path.get(thisStop);
			journeys.add(thisJourney);
			thisStop = thisJourney.getDepartBusStop();
		}
		Journey[] toReturn = new Journey[journeys.size()];
		toReturn = (Journey[]) journeys.toArray();
		return toReturn;
	}

	public static Journey[] dijkstra(int startStop, int endStop, Date time)
	{
		PriorityQueue<QueueItem> queue = new PriorityQueue<QueueItem>();
		HashMap<Integer, Journey> path = new HashMap<Integer, Journey>();
		queue.add(new QueueItem(startStop, time, null));
		QueueItem next = queue.poll();
		while(next != null)
		{
			path.put(next.stop, next.journey);
			if(next.stop == endStop)
				return makeJourney(path, startStop, endStop);
			Route[] routes = getRoutes(next.stop);
			for(Route route: routes)
			{
				Service[] services = route.getServices(next.time);
				Service nextService = null;
				Date nextServiceTime = null;
				for(Service service: services)
				{
					TimingPoint[] timingPoints = service.getTimingPoints();
					for(TimingPoint timingPoint: timingPoints)
					{
						if(timingPoint.getStop() == next.stop)
							if(timingPoint.getTime().compareTo(next.time)>0)
								if((nextServiceTime == null)||(nextServiceTime.compareTo(timingPoint.getTime())>0))
								{
									nextService = service;
									nextServiceTime = timingPoint.getTime();
								}
					}
				}
				TimingPoint[] timingPoints = nextService.getTimingPoints();
				int timingPointID = 0;
				while(timingPoints[timingPointID].getStop() != next.stop)
					timingPointID++;
				timingPointID++;
				while(timingPointID < timingPoints.length)
				{
					queue.add(new QueueItem(timingPoints[timingPointID].getStop(), timingPoints[timingPointID].getTime(),
											new Journey(next.stop, timingPoints[timingPointID].getStop(), nextServiceTime, timingPoints[timingPointID].getTime(), nextService)));
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
//		System.out.println(Roster.printFullTimetable());
		Journey[] journeys = dijkstra(781, 783, new Date());
		for(Journey journey: journeys)
		{
			System.out.println("Take bus " + journey.getService().getRoute().getName() +" from " + journey.getDepartBusStop() + " to " + journey.getArrivalBusStop());
			System.out.println("Leaves at " + simpleDateFormat.format(journey.getDepartTime()) + " and arrives at " +simpleDateFormat.format(journey.getArrivalTime()));
			System.out.println();
		}
	}
}
