package Hunt.Listeners;


import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Hunt.TreasureManager;

public class CommandListener implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args)
	{
		boolean invalid = false;
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
		else if(args[0].equalsIgnoreCase("location"))
		{
			if(args[1] != null)
			{
				if(args[1].equalsIgnoreCase("add"))
				{
					TreasureManager.instance.getTreasureSpots().add(p.getLocation());
				}
				else if(args[1].equalsIgnoreCase("remove"))
				{
					TreasureManager.instance.getTreasureSpots().remove(p.getLocation());
				}
			}
		}
		else
		{
			invalid = true;
		}
		
		if(invalid)
		{
			sender.sendMessage(ChatColor.RED+"Invalid arguments.");
		}
		return false;
	}
}
