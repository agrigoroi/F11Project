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
	
	private static Date get_date(String table, int id, String field_name)
	{
		database db = database.busDatabase;
	    db.select(field_name, table, table + "_id = " + id, "");
	    Object result;
		if (db.move_first())
		{
			result = db.get_field(field_name);
			return (Date)result;
		}
	    else return database.today();
	  }
	
	public static Date getStartDate(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return get_date("request", request, "start_date");
	}
	
	public static Date getEndDate(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return get_date("request", request, "end_date");
	}
	
	public static int getStatus(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return database.busDatabase.get_int("request", request, "status");
	}
	
	public static void setStatus(int request, String status)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		database.busDatabase.set_value("request", request, "status", status);
	}
	
	public static int getDriver(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return database.busDatabase.get_int("request", request, "driver_id");
	}
	
	public static void insert(int driver, Date startDate, Date endDate, int status)
	{
	    database.busDatabase.new_record("request", new Object[][]{{"driver_id", driver}, {"start_date", startDate}, {"end_date", endDate}, {"status", status}});
	}
	
	public static int[] findRequestByDriver(int driver)
	{
		return database.busDatabase.select_ids("request_id", "request", "driver_id", driver, "request_id");
	}
}
