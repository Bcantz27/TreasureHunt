package Hunt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Location;

public class TreasureManager 
{
	public static TreasureManager instance;
	
	private static CopyOnWriteArrayList<Location> treasureSpots = new CopyOnWriteArrayList<Location>();
	private static CopyOnWriteArrayList<TreasureChest> possibleTreasures = new CopyOnWriteArrayList<TreasureChest>();
	
	public TreasureManager()
	{
		instance = this;
	}
	
	public TreasureManager getInstance()
	{
		return instance;
	}
	
	public CopyOnWriteArrayList<Location> getTreasureSpots()
	{
		return treasureSpots;
	}
	
	public CopyOnWriteArrayList<TreasureChest> getPossibleTreasures()
	{
		return possibleTreasures;
	}
	
	public void addTreasureSpot(Location loc)
	{
		if(!treasureSpots.contains(loc)) return;
		treasureSpots.add(loc);
	}
	
	public void removeTreasureSpot(Location loc)
	{
		treasureSpots.remove(loc);
	}
	
	public void addTreasureChest(TreasureChest chest)
	{
		if(!possibleTreasures.contains(chest)) return;
		possibleTreasures.add(chest);
	}
	
	public void removeTreasureChest(TreasureChest chest)
	{
		possibleTreasures.remove(chest);
	}
	
    public void saveTreasureSpots() throws IOException
    {
    	FileOutputStream fos = new FileOutputStream(TreasureHunt.getInstance().getDataFolder() + "/treasurespots.xml");
    	if(fos == null) return;
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	if(oos == null) return;
    	oos.writeObject(treasureSpots);
    	oos.close();
    }
    
	@SuppressWarnings("unchecked")
	public void loadTreasureSpots() throws IOException, ClassNotFoundException
    {
    	FileInputStream fis = new FileInputStream(TreasureHunt.getInstance().getDataFolder() + "/treasurespots.xml");
    	if(fis == null) return;
    	ObjectInputStream ois = new ObjectInputStream(fis);
    	if(ois == null) return;
    	treasureSpots = (CopyOnWriteArrayList<Location>) ois.readObject();
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
