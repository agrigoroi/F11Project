import java.util.Date;

/**
 * @author Alexandru Grigoroi
 *
 * Class which represents information about Requests.
 * It also allows the application to get the starting and ending
 * dates of a particular request as well as to get and set the status
 * of a particular request.<br><br>
 * 
 * As well as an ID, requests have numbers which are traditionally
 * used to identify them. <br><br>
 * 
 * The methods contain checks for invalid request, which will result in
 * Exceptions being thrown, and also enforces some "business
 * rules" such as checking for dates in the past.
 *
 */

public class RequestInfo {
		
	private RequestInfo()
	{
	}
	
	/**
         * Returns all Requests in database
         */
	public static int[] getRequests()
	{
		return database.busDatabase.select_ids("request_id", "request", "start_date");
	}
	
	/**
         * 
         */
	private static Date get_date(String table, int id, String field_name)
	{
	    database db = database.busDatabase;
	    db.select(field_name, table, table + "_id = " + id, ""); //builds up a query to select a record from db
	    Object result;
		if (db.move_first())
		{
			result = db.get_field(field_name);
			return (Date)result;
		}
	    else return database.today();
	  }
	
	/**
         * Returns the starting date of a request
	 * @param request id
         */
	public static Date getStartDate(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return get_date("request", request, "start_date");
	}
	
	/**
         * Returns the ending date of a request
	 * @param request id
         */
	public static Date getEndDate(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return get_date("request", request, "end_date");
	}
	
	/**
         * Returns the status of a request
	 * @param request id
         */
	public static int getStatus(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return database.busDatabase.get_int("request", request, "status");
	}
	
	/**
         * Sets the status of a request
	 * @param request id, new status string
         */
	public static void setStatus(int request, String status)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		database.busDatabase.set_value("request", request, "status", status);
	}
	
	/**
         * Returns the driver id for a request
	 * @param the request id
         */
	public static int getDriver(int request)
	{
		if(request == 0) throw new InvalidQueryException("Nonexisted request");
		return database.busDatabase.get_int("request", request, "driver_id");
	}
	
	/**
         * Inserts a request into the database
	 * @param driver id, starting date, ending date, status string
         */
	public static void insert(int driver, Date startDate, Date endDate, int status)
	{
	    database.busDatabase.new_record("request", new Object[][]{{"driver_id", driver}, {"start_date", startDate}, {"end_date", endDate}, {"status", status}});
	}
	
	/**
         * Returns all the request IDs of every request requested by a driver
	 * @param driver id
         */
	public static int[] findRequestByDriver(int driver)
	{
		return database.busDatabase.select_ids("request_id", "request", "driver_id", driver, "request_id");
	}
}
