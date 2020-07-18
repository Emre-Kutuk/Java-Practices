package creditClick;

public class Path 
{
	public int pathNo;
	public Town town1;
	public Town town2;
	public int distance;
	
	public Path(int pathNo, Town town1, Town town2, int distance)
	{
		this.pathNo = pathNo;
		this.town1 = town1;
		this.town2 = town2;
		this.distance = distance;
	}
	
	@Override
	public String toString()
	{
		return "Path " + pathNo +" between " + town1.name + " and " + town2.name + " and the distance is " + distance + " km.\n" ;
	}	
}
