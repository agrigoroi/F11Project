import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 */

/**
 * @author grigora1
 *
 */
public class JourneyPlannerTest {
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

	@Before
	public final void openDB()
	{
		database.openBusDatabase();
	}
	
	@Ignore
	private static Date makeDate(int hours, int minutes, TimetableInfo.timetableKind dayType)
	{
		Calendar date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		Date day = date.getTime();
		while(TimetableInfo.timetableKind(day) != dayType)
			day.setTime(day.getTime() + 1000*60*60*24);
		day.setTime(day.getTime()+1000*60*(minutes + 60*hours));
		return day;
	}
	
	@Ignore
	private static void testJourneyList(Journey[] journeyArray)
	{
		for(int i=1;i<journeyArray.length;i++)
		{
			assertTrue("Bus leaves before the previous arrives.", journeyArray[i-1].getArrivalTime().before(journeyArray[i].getDepartTime()));
			assertTrue("Bus leaves from a different stop.", BusStopInfo.getFullName(journeyArray[i-1].getArrivalBusStop()).equals(BusStopInfo.getFullName(journeyArray[i].getDepartBusStop())));
			assertTrue("Wait time bigger than 45 minutes.", journeyArray[i].getDepartTime().getTime() - journeyArray[i-1].getArrivalTime().getTime() < 45*60*1000);
		}
	}
	
	@Ignore
	private static void printJourney(Journey[] journeys)
	{
		for(Journey journey: journeys)
		{
			System.out.println("Take bus " + journey.getService().getRoute().getName() +" from " + BusStopInfo.getFullName(journey.getDepartBusStop()) + " to " + BusStopInfo.getFullName(journey.getArrivalBusStop()));
			System.out.println("Leaves at " + simpleDateFormat.format(journey.getDepartTime()) + " and arrives at " +simpleDateFormat.format(journey.getArrivalTime()));
			System.out.println();
		}
	}

	/**
	 * Test method for {@link JourneyPlanner#dijkstra(java.lang.String, java.lang.String, java.util.Date)}.
	 */
	@Test
	public final void testDijkstra1() {
		Date date = makeDate(7, 40, TimetableInfo.timetableKind.weekday);
		ArrayList<Journey> journeyList = JourneyPlanner.dijkstra("Hayfield, Bus Station", "Stockport, Lower Bents Lane/Stockport Road", date);
		System.out.println("Journey from Hayfield, Bus Station to Stockport, Lower Bents Lane/Stockport Road");
		if(journeyList == null)
			fail("Route is null");
		Journey[] journeys = new Journey[journeyList.size()];
		int next = 0;
		for(Journey journey: journeyList)
		{
			journeys[next] = journey;
			next++;
		}
		testJourneyList(journeys);
		printJourney(journeys);
	}
	
	@Test
	public final void testDijkstra2() {
		Date date = makeDate(7, 40, TimetableInfo.timetableKind.weekday);
		ArrayList<Journey> journeyList = JourneyPlanner.dijkstra("Hayfield, Bus Station", "Stockport, Asda/Sainsbury", date);
		System.out.println("Journey from Hayfield, Bus Station to Stockport, Asda/Sainsbury");
		if(journeyList == null)
			fail("Route is null");
		Journey[] journeys = new Journey[journeyList.size()];
		int next = 0;
		for(Journey journey: journeyList)
		{
			journeys[next] = journey;
			next++;
		}
		testJourneyList(journeys);
		printJourney(journeys);
	}

}
