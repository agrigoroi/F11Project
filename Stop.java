public class Stop {
  String areacode;
  String name;
  int ID;
  
  public Stop(String areacode, String name)
  {
    this.areacode = areacode;
    this.name = name;
    this.ID = BusStopInfo.findBusStop(areacode, name);
  }
  
  public Route[] getRoutes()
  {
    int[] routesID = BusStopInfo.getRoutes(ID);
    Route[] routes = new Route[routesID.length];
    for(int i = 0; i < routesID.length; i++)
      routes[i] = new Route(routesID[i]);
    return routes;
  }
  
  
}
