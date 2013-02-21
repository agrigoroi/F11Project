/**
 * @author Alexandru Grigoroi
 *
 */

import java.util.Date;

public class RequestInfo {
	
	private RequestInfo()
	{
	}
	
	public static int[] getRequests()
	{
		return database.busDatabase.select_ids("request_id", "request", "start_date");
	}
	
	public static Date getStartDate(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return database.busDatabase.get_date("request", request, "start_date");
	}
	
	public static Date getEndDate(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return database.busDatabase.get_date("request", request, "end_date");
	}
	
	public static String getStatus(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return database.busDatabase.get_string("request", request, "status");
	}
	
	public static void setStatus(int request, String status)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		database.busDatabase.set_value("request", request, "status", status);
	}
	
	public static void insert(int id, Date startDate, Date endDate, String status)
	{
	    database.busDatabase.new_record("request", new Object[][]{{"request_id", id}, {"start_date", startDate}, {"end_date", endDate}, {"status", status}});
	}
	
	public static int[] findRequestByDriver(int driver)
	{
		return database.busDatabase.select_ids("request_id", "request", "driver", driver, "request_id");
	}
}