import java.util.Date;

/**
 * @author Alexandru Grigoroi
 *
 */
public class Request {
	
	private Boolean exists = false;
	private int id;
	private Date startDate;
	private Date endDate;
	private String status;
	
	public Request(int id)
	{
		try
		{
			this.id = id;
			this.startDate = RequestInfo.getStartDate(id);
			this.endDate = RequestInfo.getEndDate(id);
			this.status = RequestInfo.getStatus(id);
			this.exists = true;
		}
		catch(Exception ex)
		{
			this.exists = false;
		}
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

	public String getStatus() {
		return status;
	}

	
	public static Request[] getByDriver(int driver)
	{
		int[] ids = RequestInfo.findRequestByDriver(driver);
		Request[] requests = new Request[ids.length];
		for(int i=0;i<ids.length;i++)
			requests[i] = new Request(ids[i]);
		return requests;
	}
}
