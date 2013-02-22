import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexandru Grigoroi
 *
 */
public class Request {
	
	private static final int MINIMUM_DRIVERS_AVAILABLE = 10;
	private static String[] statusMessage = new String[]{"Awaiting Approval", "Approved", "Rejectect"};
	private static SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
	
	private Boolean exists = false;
	private int id;
	private Date startDate;
	private Date endDate;
	private int status;
	private Driver driver;
	
	public Request(int id)
	{
		try
		{
			this.id = id;
			this.startDate = RequestInfo.getStartDate(id);
			this.endDate = RequestInfo.getEndDate(id);
			this.status = RequestInfo.getStatus(id);
			Integer driver_id = RequestInfo.getDriver(id);
			this.driver = new Driver(driver_id.toString());
			this.exists = true;
		}
		catch(Exception ex)
		{
			this.exists = false;
		}
	}
	public Request(Date startDate, Date endDate, Driver driver) throws Exception
	{
		if(startDate.after(endDate))
			throw new Exception("End date is before start date");
		this.startDate = startDate;
		this.endDate = endDate;
		this.driver = driver;
		if(driver.getHolidaysLeft() < getLength())
			throw new Exception("Holiday duration is too big");
		Date date = new Date(startDate.getTime());
		Driver[] drivers = Driver.getDrivers();
		while(date.compareTo(endDate) <= 0)
		{
			int availableDrivers = 0;
			for(Driver thisDriver: drivers)
			{
				if(thisDriver.isAvailable(date))
					availableDrivers++;
				if(availableDrivers > MINIMUM_DRIVERS_AVAILABLE)
					break;
			}
			if(availableDrivers <= MINIMUM_DRIVERS_AVAILABLE)
				throw new Exception("Holidays for " + date + " are already fully booked");
			date.setTime(date.getTime() + 24*60*60*1000);
		}
		this.status = 0;
		this.exists = false;
	}
	
	public Request(String start, String end, Driver driver) throws ParseException, Exception
	{
		this(parser.parse(start), parser.parse(end), driver);
	}
	
	public int getLength()
	{
		return (int)(TimeUnit.MILLISECONDS.toDays(endDate.getTime()
                - startDate.getTime())) + 1;
	}
	
	public Boolean getExists() {
		return exists;
	}

	public int getId() {
		return id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getStatus() {
		return status;
	}

	public void save()
	{
		if(!exists)
		{
			RequestInfo.insert(driver.getID(), this.startDate, this.endDate, this.status);
			driver.takeHoliday(this);
		}
	}
	
	public static Request[] getByDriver(int driver)
	{
		int[] ids = RequestInfo.findRequestByDriver(driver);
		Request[] requests = new Request[ids.length];
		for(int i=0;i<ids.length;i++)
			requests[i] = new Request(ids[i]);
		return requests;
	}
	
	public static Request[] getByDriver(Driver driver)
	{
		return getByDriver(driver.getID());
	}
	
	public String getStatusMessage()
	{
		return statusMessage[this.status];
	}
}
