package creditClick;

public class Path 
{
	public Town from;
	public Town to;
	public int distance;
	
	public Path(Town from, Town to, int distance)
	{
		this.from = from;
		this.to = to;
		this.distance = distance;
	}
	
	@Override
	public String toString()
	{
		return "A path from " + from.name + " to " + to.name + " and the distance is " + distance + " km.\n" ;
	}	
}
