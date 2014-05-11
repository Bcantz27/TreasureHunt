package Hunt;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TreasureChest implements Serializable{
	
	public String name;
	public ArrayList<ItemStack> loot = new ArrayList<ItemStack>();
	
	public TreasureChest()
	{

	}
	
	public TreasureChest(String name)
	{
		this.name = name;
	}
	
	public ArrayList<ItemStack> getLoot()
	{
		return loot;
	}
	
	public void addLoot(ItemStack item)
	{
		if(item == null) return;
		
		loot.add(item);
	}
	
	public void clearLoot()
	{
		loot = new ArrayList<ItemStack>();
	}
	
	public void listLoot(Player p)
	{
		if(loot.isEmpty())
		{
			p.sendMessage("Loot is empty.");
			return;
		}
		
		for(ItemStack item: loot)
		{
			p.sendMessage(item.getType().name());
		}
	}
	
	

}
