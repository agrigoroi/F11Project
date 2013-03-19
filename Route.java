import java.util.Date;
import java.util.EnumMap;

/**
 * Instantiatable Class which represents information about a route.
 */
 
public class Route
{
	private int id;
	private String name;
	private EnumMap<TimetableInfo.timetableKind, Service[]> cachedServices = new EnumMap<TimetableInfo.timetableKind, Service[]>(TimetableInfo.timetableKind.class);

	public Route(int id)
	{
		this.id = id;
		this.name = BusStopInfo.getRouteName(id);
	}

	public static Route[] getAll()
	{
		int[] routesID = BusStopInfo.getRoutes();
		Route[] routes = new Route[routesID.length];
		for(int i=0;i<routesID.length;i++)
			routes[i] = new Route(routesID[i]);
		return routes;
	}

	public Service[] getServices(TimetableInfo.timetableKind dayType)
	{
		return getServices(dayType, false);
	}

	private Service[] getServices(TimetableInfo.timetableKind dayType, boolean updateFromDatabase)
	{
		if((!cachedServices.containsKey(dayType)) || (updateFromDatabase))
		{
			int[] servicesID = TimetableInfo.getServices(this.id, dayType);
			Service[] services = new Service[servicesID.length];
			for(int i=0; i<servicesID.length; i++)
				services[i] = new Service(i, this, dayType, servicesID[i]);
			cachedServices.put(dayType, services);
		}
		return cachedServices.get(dayType);
	}
	
	/**
         * Returns the ID of this route
         */
	public int getID()
	{
	  	return this.id;
	}
	
	/**
         * Returns the name of this route
         */
	public String getName()
	{
	  	return this.name;
	}

}
