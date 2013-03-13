import java.util.Date;

/**
 * Instantiatable Class which represents information about drivers and their availability.
 * It also allows the application to get and set the number of hours a
 * driver has worked over time periods of a week or a year, and the
 * holidays taken by a driver in the current calendar year.<br><br>
 * 
 * As well as an ID, drivers (like buses) have numbers which are traditionally
 * used to identify them. You can also get the name of a specified driver<br><br>
 * 
 * This Class also contains a few methods used to show GUI windows necessary for
 * the application.
 *
 */
 
public class Driver
{
	private Boolean exists = false;
	private int id = -1;
	private String name;
	private String number;
	private String password;
	private final static int totalHolidays = 25;
	
	public Driver(String driverNumber) throws Exception
	{
		try
		{
			int driverIds[] = DriverInfo.getDrivers(), driverNumbers[] = new int[driverIds.length];
			
			//get driver numbers
			for(int i = 0; i < driverIds.length; i++)
				driverNumbers[i] = Integer.parseInt(DriverInfo.getNumber(driverIds[i]));
			
			//get the drivers id
			for(int i = 0; i < driverNumbers.length; i++)
				if(Integer.parseInt(driverNumber) == driverNumbers[i])
				{
					this.id = driverIds[i];
					break;
				}
			
			if(this.id == -1)
				throw new Exception();
			
			this.name = DriverInfo.getName(this.id);
			this.number = DriverInfo.getNumber(this.id);
			this.password = DriverInfo.getPass(this.id);
			this.exists = true;
		}
		catch(Exception ex)
		{
			this.exists = false;
			
			throw new Exception("Driver does not exist");
		}	
	}

	/**
         * Checks a password a returns whether that password is valid
	 * @param password
         */
	public Boolean checkPassword(String password)
	{
		if(this.exists)
			if(this.password.equals(password))
				return true;
		return false;
	}
	
	/**
         * Method to open up showWelcome GUI
         */
	public void showWelcome()
	{
		LoginGUI.window.openWindow(new WelcomeGUI(this));
	}
	
	/**
         * Method to open up showHolidays GUI
         */
	public void showHolidays()
	{
		Request[] requests = Request.getByDriver(id);
		if(requests.length == 0)
			LoginGUI.window.openWindow(new HolidayGUI(this, null));
		else
			LoginGUI.window.openWindow(new HolidayGUI(this, requests));
	}
	
	/**
         * Returns the name of this driver
         */
	public String getName()
	{
		return this.name;
	}
	
	/**
         * Returns the number of holidays a driver has remaining
         */
	public int getHolidaysLeft()
	{
		return totalHolidays-DriverInfo.getHolidaysTaken(id);
	}
	
	/**
         * Method to submit a holiday request that has been validated
	 * Updates variuos driver information
	 * @param the request instance of the request
         */
	public void takeHoliday(Request request)
	{
		int daysTaken = DriverInfo.getHolidaysTaken(id) + request.getLength();
		DriverInfo.setHolidaysTaken(id, daysTaken);
		Date date = new Date(request.getStartDate().getTime());
		while(date.compareTo(request.getEndDate()) <= 0)
		{
			DriverInfo.setAvailable(getID(), date, false);
			date.setTime(date.getTime() + 24*60*60*1000);
		}
	}
	
	/**
         * Returns the ID of this driver
         */
	public int getID()
	{
	  	return this.id;
	}
	
	/**
         * Returns whether the driver is available today
         */
	public Boolean isAvailable()
	{
		return isAvailable(database.today());
	}
	
	/**
         * Returns whether the driver is avilable for a particular date
	 * @param date wanted to check driver availability for
         */
	public Boolean isAvailable(Date date)
	{
		return DriverInfo.isAvailable(this.id, date);
	}
	
	/**
         * Returns all the drivers in database
         */
	public static Driver[] getDrivers()
	{
		int[] driver_ids = DriverInfo.getDrivers();
		Driver[] drivers = new Driver[driver_ids.length];
		for(int index = 0; index < driver_ids.length; index++)
			try
			{
				drivers[index] = new Driver(((Integer)driver_ids[index]).toString());
			}
			catch(Exception e){}
		return drivers;
	}
}
