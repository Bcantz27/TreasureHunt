package Hunt.Listeners;


import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Hunt.TreasureChest;
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

			if(!p.hasPermission("Hunt.admin"))
				return false;
		}
		if(args[0].equalsIgnoreCase("reload"))
		{

		}
		else if(args[0].equalsIgnoreCase("hunt"))
		{
			if(!TreasureManager.getInstance().found)
				p.sendMessage(ChatColor.GREEN + "Treasure Location " + TreasureManager.instance.currentTreasureLocation.toString());
			else
				p.sendMessage(ChatColor.GREEN + "No Treasure Hunt at the Moment.");
		}
		else if(args[0].equalsIgnoreCase("location"))
		{
			if(args[1] != null)
			{
				if(args[1].equalsIgnoreCase("add"))
				{
					p.sendMessage(ChatColor.AQUA + "Treasure Chest Location Added.");
					TreasureManager.getInstance().getTreasureLocations().add(p.getLocation());
					treasureChest.clearLoot();
				}
				else if(args[1].equalsIgnoreCase("remove"))
				{
					p.sendMessage(ChatColor.AQUA + "Treasure Chest Location Removed.");
					TreasureManager.getInstance().removeTreasureLocation(p.getTargetBlock(null, 5).getLocation());
				}
			}
		}
		else if(args[0].equalsIgnoreCase("treasure"))
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
				else if (args[1].equalsIgnoreCase("start"))
				{
					p.sendMessage(ChatColor.AQUA + "Force Starting Treasure Hunt.");
					TreasureManager.getInstance().startTreasureHunt();
				}
			}
		}
		else
		{

		}

		return false;
	}
}
