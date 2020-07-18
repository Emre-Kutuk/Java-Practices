package creditClick;
import java.util.*;

public class Town 
{
	public String name;
	public int population;
	Random rnd = new Random();
	
	public Town(String name)
	{
		this.name = name;
		this.population = rnd.nextInt((120000 - 25000) + 1) + 25000; 
	}

	@Override
	public String toString()
	{
		return this.name + "\nPopulation: " + this.population + "\n\n";		
	}

}
