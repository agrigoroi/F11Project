import java.util.ArrayList;
import java.util.Arrays;

public class Service
{
	private int id;
	private int routeID;
	private int[] stops;
	private int[] times;

	public Service(int id,int routeID,TimetableInfo.timetableKind dayType)
	{
		this.id = id;
		this.routeID = routeID;
		times = getTimes(routeID, dayType, id);
		stops = getStops(id, routeID);
	}

	public int getID()
	{
		return id;
	}

	public int[] getTimes()
	{
		return times;
	}

	public int getTime(int index)
	{
		return times[index];
	}

	public int getStopID(int index)
	{
		return stops[index];
	}

	public int[] getStops()
	{
		return stops;
	}

	public int getDuration()
	{
		return times[times.length-1] - times[0];
	}

	private int[] getTimes(int routeID, TimetableInfo.timetableKind dayType, int service)
	{
		int times[] = TimetableInfo.getServiceTimes(routeID, dayType, service);
		for(int qw=1;qw<times.length;qw++)
			if(times[qw]-times[qw-1]>60)
			{
				for(int j=0;j<qw;j++)
					times[j]+=1440;
				break;
			}
		Arrays.sort(times);
		return times;
	}


	// Done for weekdays :))
	private static int[] getStops(int service, int route)
	{
		int[] toReturn, stops;
		int numOfStops, i, j;
		ArrayList<Integer> goodstops;
		stops = BusStopInfo.getBusStops(route);
		goodstops = new ArrayList<Integer>();
		for(j=0;j<stops.length;j++)
			if(BusStopInfo.isTimingPointOnRoute(stops[j], route))
				goodstops.add(stops[j]);
		if(service == 6512)
		{
			
			numOfStops = 4;
			toReturn = new int[numOfStops];
			for(i=0;i<numOfStops;i++)
				toReturn[i] = goodstops.get(goodstops.size()-numOfStops+i-3);
		}
		else if((service  <= 6530) && (service >= 6513))
		{
			numOfStops = goodstops.size();
			toReturn = new int[numOfStops];
			for(i=0;i<numOfStops;i++)
				toReturn[i] = goodstops.get(goodstops.size()-numOfStops+i);
			for(i=7;i<numOfStops;i++)
				toReturn[i-1] = toReturn[i];
		}
		else if((service >= 6460) && (service <= 6463))
		{
			numOfStops = goodstops.size();
			toReturn = new int[numOfStops-3];
			for(i=0;i+3<numOfStops;i++)
				toReturn[i] = goodstops.get(i+3);
		}
		else if(service == 6478)
		{
			numOfStops = goodstops.size();
			toReturn = new int[numOfStops];
			for(i=0;i<numOfStops;i++)
				toReturn[i] = goodstops.get(goodstops.size()-numOfStops+i);
			for(i=8;i<toReturn.length;i++)
				toReturn[i-1] = toReturn[i];
		}
		else if((service >= 6464) && (service <= 6479))
		{
			numOfStops = goodstops.size();
			toReturn = new int[numOfStops-3];
			for(i=0;i+3<numOfStops;i++)
				toReturn[i] = goodstops.get(i+3);
			for(i=4;i<toReturn.length;i++)
				toReturn[i-1] = toReturn[i];
		}
		else
		{
			numOfStops = goodstops.size();
			toReturn = new int[numOfStops];
			for(i=0;i<numOfStops;i++)
				toReturn[i] = goodstops.get(goodstops.size()-numOfStops+i);
		}
		return toReturn;
	}

}