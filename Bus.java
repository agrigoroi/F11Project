

/**
 * Instantiatable Class which represents information about a bus and its availability.
 * 
 * As well as an ID, buses have numbers which are traditionally
 * used to identify them.
 *
 */
 
public class Bus
{
	private int id;
	private String number;

	public Bus(int id)
	{
		this.id = id;
		this.number = BusInfo.busNumber(id);
	}

	public static Bus[] getAll()
	{
		int[] bussesID = BusInfo.getBuses();
		Bus[] busses = new Bus[bussesID.length];
		for(int i=0;i<bussesID.length;i++)
			busses[i] = new Bus(bussesID[i]);
		return busses;
	}
	
	/**
         * Returns the ID of this bus
         */
	public int getID()
	{
	  	return this.id;
	}
	
	/**
         * Returns the ID of this bus
         */
	public String getNumber()
	{
	  	return this.number;
	}

}
