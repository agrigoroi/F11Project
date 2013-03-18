import java.util.Date;

/**
 * Instantiatable Class which represents information about a route.
 */
 
public class Route
{
	private int id;
	private String name;

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
		int numberOfServices = TimetableInfo.getNumberOfServices(this.id, dayType);
		Service[] services = new Service[numberOfServices];
		for(int i=0; i<numberOfServices; i++)
			services[i] = new Service(i, this, dayType);
		return services;
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
