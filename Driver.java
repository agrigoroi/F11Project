public class Driver
{
	private Boolean exists = false;
	private int id;
	private String name;
	private String number;
	private String password;

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

	public String getName()
	{
		return this.name;
	}
}