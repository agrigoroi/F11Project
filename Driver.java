import java.util.Date;

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

	public Boolean checkPassword(String password)
	{
		if(this.exists)
			if(this.password.equals(password))
				return true;
		return false;
	}
	
	public void showWelcome()
	{
		LoginGUI.window.openWindow(new WelcomeGUI(this));
	}
	
	public void showHolidays()
	{
		Request[] requests = Request.getByDriver(id);
		if(requests.length == 0)
			LoginGUI.window.openWindow(new HolidayGUI(this, null));
		else
			LoginGUI.window.openWindow(new HolidayGUI(this, requests));
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getHolidaysLeft()
	{
		return totalHolidays-DriverInfo.getHolidaysTaken(id);
	}
	
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
	
	public int getID()
	{
	  	return this.id;
	}
	
	public Boolean isAvailable()
	{
		return isAvailable(database.today());
	}
	
	public Boolean isAvailable(Date date)
	{
		return DriverInfo.isAvailable(this.id, date);
	}
	
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
