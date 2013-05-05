import java.util.Date;

public class Journey{

private int departBusStop;
private int arrivalBusStop;
private Date departTime;
private Date arrivalTime;
private Service service;

  public Journey(int dStop, int aStop, Date dTime, Date aTime, Service s)
  {
    departBusStop = dStop;
    arrivalBusStop = aStop;
    departTime = dTime;
    arrivalTime = aTime;
    service=s;
  }
  
  public int getDepartBusStop()
  {
	  return departBusStop;
  }
  
  public int getArrivalBusStop()
  {
	  return arrivalBusStop;
  }
  
  public Service getService()
  {
	  return service;
  }
  
  public Date getDepartTime()
  {
	  return this.departTime;
  }
  
  public Date getArrivalTime()
  {
    return this.arrivalTime;
  }
}
