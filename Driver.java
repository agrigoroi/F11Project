public class Driver
{
	public static Boolean checkDetails(String id, String password)
	{
		try
		{
			int inputDriverID = Integer.parseInt(id);
			return checkDetails(inputDriverID, password);
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	public static Boolean checkDetails(int id, String password)
	{
		try
		{
			database.openBusDatabase();
			String correctPassword = DriverInfo.getPass(2012);
			if(correctPassword.equals(password))
				return true;
			else
				return false;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
}