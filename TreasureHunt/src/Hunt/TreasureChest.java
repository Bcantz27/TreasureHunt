package Hunt;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class TreasureChest {
	public String name;
	public ArrayList<ItemStack> loot = new ArrayList<ItemStack>();
	
	public TreasureChest()
	{

	}
	
	public TreasureChest(String name)
	{
		this.name = name;
	}
	
	public void addLoot(ItemStack item)
	{
		if(item == null) return;
		
		loot.add(item);
	}
	
	

}
