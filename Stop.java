public class Stop
{
	private int id;
	private String name = null;

	public Stop(int id)
	{
		this.id = id;
	}

	public int getID()
	{
		return this.id;
	}

	public String getName()
	{
		if(this.name == null)
			this.name = BusStopInfo.getFullName(this.id);
		return this.name;
	}

	public static Stop[] getAllStops()
	{
		int[] stopIds = BusStopInfo.getAllBusStops();
		Stop[] stops = new Stop[stopIds.length];
		for(int i=0;i<stopIds.length;i++)
			stops[i] = new Stop(stopIds[i]);
		return stops;
	}

}