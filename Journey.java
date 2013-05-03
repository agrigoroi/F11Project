import java.util.Date;

public class Journey{

private Stop departBusStop;
private Stop arrivalBusStop;
private Date departTime;
private Date arrivalTime
private Route route;

  public Journey(Stop dStop, Stop aStop, Date dTime, Date aTime, Route r)
  {
    departBusStop = dStop;
    arrivalBusStop = aStop;
    departTime = dTime;
    arrivalTime = aTime;
    route=r;
  }
  
  public Date getArrivalTime()
  {
    return this.departTime;
  }
  
  @Override
	public boolean equals(Object j)
	{
		return (((Journey)j).getArrivalTime().equals(this.getArrivalTime()));
	}

	@Override
	public int compareTo(Journey other)
	{
		return (int)(this.getArrivalTime().getTime() - other.getArrivalTime().getTime());
	}
}
