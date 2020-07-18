package creditClick;
import java.util.*;

public class Country 
{
	public String countryName;
	public List <Town> towns = new ArrayList<Town>();
	private List <String> townNames = new ArrayList <String>();
	private int nrOfTowns;
	
	public Country(String countryName, int nrOfTowns)
	{
		this.countryName = countryName;
		this.nrOfTowns = nrOfTowns;
	}
	
	public void generateCountry()
	{
		System.out.print("Generating " + this.countryName + "...\n");
		Random rnd = new Random();
		getTownNames();
		
		//Creating towns with random names
		for(int i = 0; i < nrOfTowns; i++)
		{
			int index = rnd.nextInt(townNames.size());
			towns.add(new Town(townNames.get(index)));
			townNames.remove(index);
		}
		
		//Creating paths for each town
		for(Town t : towns)
		{
			t.toString();
			t.createPaths(this);
		}
	}
	
	//Getting average population of all towns
	public void printAvgPopulation()
	{
		List <Integer> populationList = new ArrayList<Integer>();
		
		towns.forEach(t -> populationList.add(t.population));
			
		System.out.print("\nAverage population of " + this.countryName +": " + populationList.stream().mapToInt(Integer::intValue).average() + "\n");
	}
	
	public Town getATownByName(String name)
	{
		for(Town t : towns)
		{
			if(t.name == name)
			{
				return t;
			}
		}
		return null;
	}
	//Getting hard coded countries
	private void getTownNames()
	{
		townNames.add("Amsterdam");
		townNames.add("The Hague");
		townNames.add("Rotterdam");
		townNames.add("Utrecht");
		townNames.add("Eindhoven");
		townNames.add("Maastricht");
		townNames.add("Haarlem");
		townNames.add("Leiden");
		townNames.add("Groningen");
		townNames.add("Breda");
		townNames.add("Delft");
		townNames.add("Lelystad");
		townNames.add("Enschede");
		townNames.add("Zwolle");
		townNames.add("Arnhem");
		townNames.add("Leeuwarden");
		townNames.add("Almere");
		townNames.add("Assen");
		townNames.add("Zaanstad");
		townNames.add("Middelburg");
		townNames.add("Kampen");
		
	}
}
