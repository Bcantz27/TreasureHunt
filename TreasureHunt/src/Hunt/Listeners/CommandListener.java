package Hunt.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Hunt.TreasureChest;
import Hunt.TreasureLocation;
import Hunt.TreasureManager;

public class CommandListener implements CommandExecutor{
	
	private static TreasureChest treasureChest = new TreasureChest();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args)
	{
		Player p = null;
		
		if(sender instanceof Player)
		{
			p = (Player) sender;
		}

		if(args[0].equalsIgnoreCase("info"))
		{
			if(!TreasureManager.getInstance().found && TreasureManager.instance.currentTreasureLocation != null)
				p.sendMessage(ChatColor.GREEN + "Treasure Location x: " + TreasureManager.instance.currentTreasureLocation.getX() + " y: " + TreasureManager.instance.currentTreasureLocation.getY() + " z: " + TreasureManager.instance.currentTreasureLocation.getZ() );
			else
				p.sendMessage(ChatColor.GREEN + "No Treasure Hunt at the Moment.");
		}
		else if(args[0].equalsIgnoreCase("start") && p.hasPermission("Hunt.admin"))
		{
			p.sendMessage(ChatColor.AQUA + "Force Starting Treasure Hunt.");
			TreasureManager.getInstance().startTreasureHunt();
		}
		else if(args[0].equalsIgnoreCase("loot") && p.hasPermission("Hunt.admin"))
		{
			for(int i = 0; i < TreasureManager.instance.getPossibleTreasures().size(); i++)
			{
				p.sendMessage("Treasure " + i);
				TreasureManager.instance.getPossibleTreasures().get(i).listLoot(p);
			}
		}
		else if(args[0].equalsIgnoreCase("location") && p.hasPermission("Hunt.admin"))
		{
			if(args[1] != null)
			{
				if(args[1].equalsIgnoreCase("add"))
				{
					p.sendMessage(ChatColor.AQUA + "Treasure Chest Location Added.");
					TreasureManager.getInstance().getTreasureLocations().add(new TreasureLocation(p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ()));
					treasureChest.clearLoot();
				}
				else if(args[1].equalsIgnoreCase("remove"))
				{
					p.sendMessage(ChatColor.AQUA + "Treasure Chest Location Removed.");
					TreasureManager.getInstance().removeTreasureLocation(new TreasureLocation(p.getTargetBlock(null, 5).getLocation().getBlockX(),p.getTargetBlock(null, 5).getLocation().getBlockY(),p.getTargetBlock(null, 5).getLocation().getBlockZ()));
				}
			}
		}
		else if(args[0].equalsIgnoreCase("treasure") && p.hasPermission("Hunt.admin"))
		{
			if(args[1] != null)
			{
				if(args[1].equalsIgnoreCase("add"))
				{
					if(p.getItemInHand() != null){
						p.sendMessage(ChatColor.AQUA + "Treasure Chest item added. " + p.getItemInHand().getType().name());
						treasureChest.addLoot(p.getItemInHand());
					}
				}
				else if(args[1].equalsIgnoreCase("clear"))
				{
					p.sendMessage(ChatColor.AQUA + "Treasure Chest cleared.");
					treasureChest.clearLoot();
				}
				else if(args[1].equalsIgnoreCase("done"))
				{
					p.sendMessage(ChatColor.AQUA + "Treasure Chest added to Possible Treasure Chests.");
					TreasureManager.getInstance().addTreasureChest(treasureChest);
					treasureChest = new TreasureChest();
				}
				else if (args[1].equalsIgnoreCase("list"))
				{
					p.sendMessage(ChatColor.AQUA + "Treasure Chest Items:");
					treasureChest.listLoot(p);
				}
			}
		}
		else
		{

		}

		return false;
	}
}
