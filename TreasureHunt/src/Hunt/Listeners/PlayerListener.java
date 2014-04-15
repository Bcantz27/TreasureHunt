package Hunt.Listeners;


import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerListener implements Listener
{
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerHit(EntityDamageByEntityEvent e)
	{
		final Entity ent = e.getEntity();
		Entity damager = e.getDamager();
		
		if(ent instanceof Player && damager instanceof Player)		//Pvp
		{
			if(((Player)ent).hasPermission("PirateRevengeEssentials.navy") && ((Player)damager).hasPermission("PirateRevengeEssentials.navy"))	//Navy vs Navy
			{
				((Player)damager).sendMessage(ChatColor.RED + "You cant attack other navy members!");
				e.setCancelled(true);
			}
		}
	}
}
