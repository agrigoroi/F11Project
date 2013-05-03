import java.util.Date;
import java.util.PriorityQueue;


public class JourneyPlanner
{
  PriorityQueue<> queue;
  //plan a route for departure time
  public static Journey[] departTimeJourney(Date time, Stop start, Stop end))
  {
    Route[] startRoutes = start.getRoutes();
    
    timetableKind dayType = timetableKind(time);
    for(Route route: startRoutes) 
    {
      Service[] services = route.getServices();
      // TODO: are the services sorted??
      int thisService = 0;
      while(services[thisService].getTime(0).getTime() < time.getTime())
        thisService++;
      
    }
  }
  //plan a route for arrival time
  
  
}
