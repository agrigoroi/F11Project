public class Driver
{
	private Boolean exists = false;
	private int id;
	private String name;
	private String number;
	private String password;
	private final static int totalHolidays = 25;

	public Driver(String id)
	{
		try
		{
			this.id = Integer.parseInt(id);
			this.name = DriverInfo.getName(this.id);
			this.number = DriverInfo.getNumber(this.id);
			this.password = DriverInfo.getPass(this.id);
			this.exists = true;
		}
		catch(Exception ex)
		{
			this.exists = false;
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
			LoginGUI.window.openWindow(new HolidayGUI(this, requests[0]));
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getHolidaysLeft()
	{
		return totalHolidays-DriverInfo.getHolidaysTaken(id);
	}
}