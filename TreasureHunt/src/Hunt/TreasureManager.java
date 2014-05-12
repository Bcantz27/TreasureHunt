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
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TreasureManager 
{
	public static TreasureManager instance;
	
	private static CopyOnWriteArrayList<TreasureChest> possibleTreasures = new CopyOnWriteArrayList<TreasureChest>();
	private static CopyOnWriteArrayList<TreasureLocation> possibleLocations = new CopyOnWriteArrayList<TreasureLocation>();
	
	public static TreasureChest currentTreasure;
	public static TreasureLocation currentTreasureLocation;
	
	public static int numberOfTreasures;
	
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
		
		if(currentTreasureLocation != null && currentTreasureLocation.getBlock().getType().equals(Material.CHEST))
		{
			Chest chest = (Chest)currentTreasureLocation.getBlock().getState();
			Inventory inv = chest.getBlockInventory();
			inv.clear();
			currentTreasureLocation.getBlock().setType(Material.AIR);
		}
		
		if(possibleTreasures.size() == 0 || possibleLocations.size() == 0)
		{
			TreasureHunt.getInstance().getLogger().log(Level.INFO, "No treasures or locations");
			return;
		}
		
		TreasureHunt.getInstance().getServer().broadcastMessage(ChatColor.GREEN + "Treasure Hunt is beginning! type '/th info' for the location of the treasure chest.");
		
		int index = randomGenerator.nextInt(possibleTreasures.size());
		currentTreasure = possibleTreasures.get(index);
		TreasureHunt.getInstance().getLogger().log(Level.INFO, "Treasure: " + index);
		
		randomGenerator = new Random();
		index = randomGenerator.nextInt(possibleLocations.size());
		currentTreasureLocation = possibleLocations.get(index);
		TreasureHunt.getInstance().getLogger().log(Level.INFO, "Location: " + index);
		
		currentTreasureLocation.getBlock().setType(Material.CHEST);
		
		 Chest chest = (Chest)currentTreasureLocation.getBlock().getState();
		 Inventory inv = chest.getBlockInventory();
		 
		 for(int i = 0; i < currentTreasure.getLoot().size(); i++)
		 {
			 inv.setItem(i, currentTreasure.getLoot().get(i));
		 }
	}
	
	public CopyOnWriteArrayList<TreasureLocation> getTreasureLocations()
	{
		return possibleLocations;
	}
	
	public CopyOnWriteArrayList<TreasureChest> getPossibleTreasures()
	{
		return possibleTreasures;
	}
	
	public void addTreasureLocation(TreasureLocation loc)
	{
		if(possibleLocations.contains(loc)) return;
		possibleLocations.add(loc);
	}
	
	public void removeTreasureLocation(TreasureLocation loc)
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
    	possibleLocations = (CopyOnWriteArrayList<TreasureLocation>) ois.readObject();
    	ois.close();
    }
	
    public void savePossibleTreasure() throws IOException
    {
    	numberOfTreasures = possibleTreasures.size();
    	
    	FileOutputStream fos = new FileOutputStream(TreasureHunt.getInstance().getDataFolder() + "/numberOfTreasures.xml");
    	if(fos == null) return;
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	if(oos == null) return;
    	oos.writeObject(numberOfTreasures);
    	oos.close();
    	
    	for(int i = 0; i < possibleTreasures.size(); i++)
    	{
        	for(int j = 0; j < possibleTreasures.get(i).getLoot().size(); j++)
        	{
        		TreasureHunt.getInstance().getConfig().set("PossibleTreasures."+i+"."+j, possibleTreasures.get(i).getLoot().get(j));
        	}
    	}
    	TreasureHunt.getInstance().saveConfig();
    }
    
	@SuppressWarnings("unchecked")
	public void loadPossibleTreasure() throws IOException, ClassNotFoundException
    {
    	FileInputStream fis = new FileInputStream(TreasureHunt.getInstance().getDataFolder() + "/numberOfTreasures.xml");
    	if(fis == null) return;
    	ObjectInputStream ois = new ObjectInputStream(fis);
    	if(ois == null) return;
    	numberOfTreasures = (int) ois.readObject();
    	ois.close();
    	
    	boolean done = false;
    	int j = -1;
    	TreasureChest chest = new TreasureChest();
    	
    	if(numberOfTreasures == 0)
    		return;
    	
    	for(int i = 0; i < numberOfTreasures; i++)
    	{
    		 while (done == false) {
                 j++;
                 //Checks if the config contains that slot
                 if (TreasureHunt.getInstance().getConfig().contains("PossibleTreasures."+i+"."+j)) {
                     //Adds the itemstack to the list
                	 chest.addLoot(TreasureHunt.getInstance().getConfig().getItemStack("PossibleTreasures."+i+"."+j));
                 } else {
                     //No more items, the list is complete
                     done = true;
                 }
             }
    		 addTreasureChest(chest);
    		 chest = new TreasureChest();
    		 done = false;
    		 j = -1;
    	}
    }

}
