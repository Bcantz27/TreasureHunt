package Hunt.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import Hunt.TreasureHunt;
import Hunt.TreasureManager;

public class PlayerListener implements Listener
{
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		Location l = e.getClickedBlock().getLocation();
		
		if(l.equals(TreasureManager.currentTreasureLocation) && !TreasureManager.found && l.getBlock().getType().equals(Material.CHEST))
		{
			TreasureHunt.getInstance().getServer().broadcastMessage(ChatColor.GREEN + "Treasure Hunt is ending! " + p.getName() + " found the treasure chest.");
			TreasureManager.found = true;
		}
	}
}
