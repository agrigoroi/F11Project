import java.util.Date;

public class TimingPoint
{
	private int stop;
	private Date time;

	public TimingPoint(int stop, Date time)
	{
		this.stop = stop;
		this.time = time;
	}

	public TimingPoint(int stop, int time)
	{
		Long timeVal  = new Date().getTime();
		timeVal = timeVal - timeVal % (24 * 60 * 60 * 1000);
		this.time = new Date(timeVal + time * 60 * 1000);
		this.stop = stop;
	}

	public int getStop()
	{
		return stop;
	}

	public Date getTime()
	{
		return time;
	}
}