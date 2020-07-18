package creditClick;
import java.util.*;

public class Town 
{
	public String name;
	public int population;
	List <Path> paths = new ArrayList <Path>();
	Random rnd = new Random();
	
	public Town(String name)
	{
		this.name = name;
		this.population = rnd.nextInt((120000 - 25000) + 25000); 
	}
	
	public void createPaths(Country country)
	{
		//Deciding how many paths this town will have
		int pathNr = rnd.nextInt((4-1) + 1 ) + 1;
		
		for(int i = 0; i < pathNr; i++)
		{
			int index = rnd.nextInt(country.towns.size());
			
			//Preventing creating a road to itself and the same place twice
			while(pathExists(index, country))
				index = rnd.nextInt(country.towns.size());
			
						
			paths.add(new Path(this, country.towns.get(index), rnd.nextInt(50-10) + 10));
		}
		
		System.out.print(this.toString());
		
		paths.forEach(p -> System.out.print(p.toString()));
		
	}
	
	public void printNeighbours()
	{
		System.out.print("\nNeighbours of " + this.name + ": ");
		paths.forEach(p -> System.out.print(p.to.name + " "));
	}
	
	public void printTownsNearby(int km)
	{
		System.out.print("\n\nTowns in the range of " + km + " km for " + name + ": ");
		
		for(Path p : paths)
		{
			if(p.distance < km)
				System.out.print(p.to.name + " ");
		}
	}
	
	private Boolean pathExists(int index, Country country)
	{
		if(country.towns.get(index) != this)
		{
			for(Path p : paths)
			{
				if(country.towns.get(index) == p.to)
					return true;
			}
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString()
	{
		return "\n" + this.name + "\n"+ this.name + " has " + paths.size() + " path(s).\nPopulation: " + this.population + "\n";		
	}

}