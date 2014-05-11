package Hunt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

public class TreasureManager 
{
	public static TreasureManager instance;
	
	private static CopyOnWriteArrayList<TreasureChest> possibleTreasures = new CopyOnWriteArrayList<TreasureChest>();
	private static CopyOnWriteArrayList<Location> possibleLocations = new CopyOnWriteArrayList<Location>();
	
	public static TreasureChest currentTreasure;
	public static Location currentTreasureLocation;
	
	private Random randomGenerator;
	
	public static boolean found = false;
	
	public TreasureManager()
	{
		instance = this;
		randomGenerator = new Random();
	}
	
	public static TreasureManager getInstance()
	{
		return instance;
	}
	
	public void startTreasureHunt()
	{
		found = false;
		
		if(currentTreasureLocation != null)
			currentTreasureLocation.getBlock().setType(Material.AIR);
		
		if(possibleTreasures.size() == 0 || possibleLocations.size() == 0)
		{
			TreasureHunt.getInstance().getLogger().log(Level.INFO, "No treasures or locations");
			return;
		}
		
		TreasureHunt.getInstance().getServer().broadcastMessage(ChatColor.GREEN + "Treasure Hunt is beginning! type /th hunt for the location of the treasure chest.");
		
		int index = randomGenerator.nextInt(possibleTreasures.size());
		currentTreasure = possibleTreasures.get(index);
		
		index = randomGenerator.nextInt(possibleLocations.size());
		currentTreasureLocation = possibleLocations.get(index);
		
		currentTreasureLocation.getBlock().setType(Material.CHEST);
		
		 Chest chest = (Chest)currentTreasureLocation.getBlock().getState();
		 Inventory inv = chest.getBlockInventory();
		 
		 for(int i = 0; i < currentTreasure.getLoot().size(); i++)
		 {
			 inv.setItem(i+1, currentTreasure.getLoot().get(i));
		 }
	}
	
	public CopyOnWriteArrayList<Location> getTreasureLocations()
	{
		return possibleLocations;
	}
	
	public CopyOnWriteArrayList<TreasureChest> getPossibleTreasures()
	{
		return possibleTreasures;
	}
	
	public void addTreasureLocation(Location loc)
	{
		if(possibleLocations.contains(loc)) return;
		possibleLocations.add(loc);
	}
	
	public void removeTreasureLocation(Location loc)
	{
		possibleLocations.remove(loc);
	}
	
	public void addTreasureChest(TreasureChest chest)
	{
		if(possibleTreasures.contains(chest)) return;
		possibleTreasures.add(chest);
	}
	
	public void removeTreasureChest(TreasureChest chest)
	{
		possibleTreasures.remove(chest);
	}
	
    public void saveTreasureSpots() throws IOException
    {
    	FileOutputStream fos = new FileOutputStream(TreasureHunt.getInstance().getDataFolder() + "/treasureLocations.xml");
    	if(fos == null) return;
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	if(oos == null) return;
    	oos.writeObject(possibleLocations);
    	oos.close();
    }
    
	@SuppressWarnings("unchecked")
	public void loadTreasureSpots() throws IOException, ClassNotFoundException
    {
    	FileInputStream fis = new FileInputStream(TreasureHunt.getInstance().getDataFolder() + "/treasureLocations.xml");
    	if(fis == null) return;
    	ObjectInputStream ois = new ObjectInputStream(fis);
    	if(ois == null) return;
    	possibleLocations = (CopyOnWriteArrayList<Location>) ois.readObject();
    	ois.close();
    }
	
    public void savePossibleTreasure() throws IOException
    {
    	FileOutputStream fos = new FileOutputStream(TreasureHunt.getInstance().getDataFolder() + "/possibletreasure.xml");
    	if(fos == null) return;
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	if(oos == null) return;
    	oos.writeObject(possibleTreasures);
    	oos.close();
    }
    
	@SuppressWarnings("unchecked")
	public void loadPossibleTreasure() throws IOException, ClassNotFoundException
    {
    	FileInputStream fis = new FileInputStream(TreasureHunt.getInstance().getDataFolder() + "/possibletreasure.xml");
    	if(fis == null) return;
    	ObjectInputStream ois = new ObjectInputStream(fis);
    	if(ois == null) return;
    	possibleTreasures = (CopyOnWriteArrayList<TreasureChest>) ois.readObject();
    	ois.close();
    }

}
