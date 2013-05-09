import java.util.Date;

public class Service implements Comparable<Service>
{
	private int id;
	private int index;
	private Route route;
	private TimetableInfo.timetableKind dayType;
	private TimingPoint[] timingPoints;

	private void updateTimingPoints()
	{
		int times[] = TimetableInfo.getServiceTimes(route.getID(), dayType, index);
		int stops[] = TimetableInfo.getTimingPointsIDs(route.getID(), dayType, index);
		this.timingPoints = new TimingPoint[times.length];
		boolean needsResorting = false;
		for(int i=1;i<times.length;i++)
			if(times[i]-times[i-1] > 60)
			{
				for(int j=i-1;j>=0;j--)
					times[j]+=1440;
				needsResorting = true;
				break;
			}
		if(needsResorting)
			for(int i=0;i<times.length;i++)
				for(int j=i+1;j<times.length;j++)
					if(times[i]>times[j])
					{
						int temp = times[i];
						times[i] = times[j];
						times[j] = temp;
						temp = stops[i];
						stops[i] = stops[j];
						stops[j] = temp;
					}
		for(int i=0;i<times.length;i++)
			this.timingPoints[i] = new TimingPoint(stops[i], times[i]);
	}

	public Service(int index, Route route, TimetableInfo.timetableKind dayType, int id)
	{
		this.index = index;
		this.route = route;
		this.dayType = dayType;
		this.id = id;
		updateTimingPoints();
	}

	public Service(int index, Route route, TimetableInfo.timetableKind dayType)
	{
		this.index = index;
		this.route = route;
		this.dayType = dayType;
		this.id = TimetableInfo.getServices(route.getID(), dayType)[index];
		updateTimingPoints();
	}

	public int getID()
	{
		return id;
	}

	public int getIndex()
	{
		return index;
	}

	public Route getRoute()
	{
		return route;
	}

	public TimetableInfo.timetableKind getDayType()
	{
		return dayType;
	}

	// Returns all the stops
	public TimingPoint[] getTimingPoints()
	{
		return this.timingPoints;
	}

	// Returns a single stop
	public TimingPoint getTimingPoint(int index)
	{
		return this.timingPoints[index];
	}

	public int getStopID(int index)
	{
		return this.timingPoints[index].getStop();
	}

	public Date getTime(int index)
	{
		return new Date(this.timingPoints[index].getTime());
	}

	public int getNumberOfTimingPoints()
	{
		return timingPoints.length;
	}

	public int getDuration()
	{
		return (int)((timingPoints[timingPoints.length-1].getTime() - timingPoints[0].getTime())/(1000 * 60));
	}

	// Simple Hash function, need for the hashmap
	public int hashCode()
	{
		return id;
	}

	@Override
	public boolean equals(Object s)
	{
		return (((Service)s).getID() == this.id);
	}

	@Override
	public int compareTo(Service otherService)
	{
		return (int)(this.getTime(0).getTime() - otherService.getTime(0).getTime());
	}
}
