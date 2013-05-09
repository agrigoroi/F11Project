
public class TimingPoint
{
	private int stop;
	private long time;

	public TimingPoint(int stop, long time)
	{
		this.stop = stop;
		this.time = time;
	}

	public TimingPoint(int stop, int time)
	{
		this.time = time * 60 * 1000;
		this.stop = stop;
	}

	public int getStop()
	{
		return stop;
	}

	public long getTime()
	{
		return time;
	}
}
