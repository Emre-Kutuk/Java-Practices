package creditClick;
import java.util.*;

public class Program 

{
	public static void main(String[] args)
	{
		Program myprogram = new Program();
		myprogram.Start();
	}
	
	@SuppressWarnings("resource")
	void Start()
	{
		Scanner s = new Scanner(System.in);
		
		System.out.print("Enter a name for the country: ");
		String name = s.nextLine();
		
		System.out.print("Enter town number (max 20): ");
		int nrOfTowns = Integer.parseInt(s.nextLine());
		
		System.out.print("Enter min road for a town(min 1): ");
		int minPath = Integer.parseInt(s.nextLine());
		System.out.print("Enter max road for a town: ");
		int maxPath = Integer.parseInt(s.nextLine());
		
		Country country = new Country(name, nrOfTowns, minPath, maxPath);
		country.GenerateCountry();
		
		country.PrintAvgPopulation();
		
		System.out.printf("\n\n%s and %s are connected to each other : %b",
				country.towns.get(0).name,country.towns.get(1).name, 
				country.CheckIfConnectedDirectly(country.towns.get(0), country.towns.get(1)));
		
		System.out.printf("\n\nPrinting neigbours of %s...\n", country.towns.get(5).name);
		for(Town t : country.FindNeighbours(country.towns.get(5)))
			System.out.printf("%s ", t.name);
		
		
		System.out.printf("\n\nPrinting neigbours of %s in 20km range...\n", country.towns.get(5).name);
		for(Town t : country.FindNeighbours(country.towns.get(5), 20))
			System.out.printf("%s ", t.name);
	}
}
