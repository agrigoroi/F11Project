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
		this.name = BusStopInfo.getRouteName();
	}

	public static Route[] getAll()
	{
		int[] routesID = BusStopInfo.getRoutes();
		Route[] routes = new Route[routesID.length];
		for(int i=0;i<routesID.length;i++)
			routes[i] = new Bus(routesID[i]);
		return routes;
	}
	
	/**
         * Returns the ID of this bus
         */
	public int getID()
	{
	  	return this.id;
	}
	
	/**
         * Returns the ID of this bus
         */
	public int getName()
	{
	  	return this.name;
	}

}
