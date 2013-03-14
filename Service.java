import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Service
{
	private int id;
	private int index;
	private int routeID;
	private TimetableInfo.timetableKind dayType;
	private TimingPoint[] timingPoints;

	private void updateTimingPoints()
	{
		int times[] = TimetableInfo.getServiceTimes(routeID, dayType, index);
		int stops[] = TimetableInfo.getTimingPointsIDs(routeID, dayType, index);
		this.timingPoints = new TimingPoint[times.length];
		boolean needsResorting = false;
		for(int i=1;i<times.length;i++)
			if(times[i]-times[i-1] > 60)
			{
				for(int j=i-1;j>=0;j--)
					times[j]+=1440;
				needsResorting = true;
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

	public Service(int index, int routeID, TimetableInfo.timetableKind dayType)
	{
		this.index = index;
		this.routeID = routeID;
		this.dayType = dayType;
		this.id = TimetableInfo.getServices(routeID, dayType)[index];
		updateTimingPoints();
	}

	public int getID()
	{
		return id;
	}

	public int getRouteID()
	{
		return routeID;
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
		return this.timingPoints[index].getTime();
	}

	public int getNumberOfTimingPoint()
	{
		return timingPoints.length;
	}

	public int getDuration()
	{
		return (int)((timingPoints[timingPoints.length-1].getTime().getTime() - timingPoints[0].getTime().getTime())/(1000 * 60));
	}
}