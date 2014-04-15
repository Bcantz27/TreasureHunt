package Hunt;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import Hunt.Listeners.CommandListener;
import Hunt.Listeners.PlayerListener;

 
public final class TreasureHunt extends JavaPlugin 
{ 
	private static TreasureHunt instance;
	public static Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onEnable(){
    	
    	try {
			TreasureManager.instance.loadTreasureSpots();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	this.getCommand("hunt").setExecutor(new CommandListener());
    	this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }
 
    @Override
    public void onDisable() {
    	try {
			TreasureManager.instance.saveTreasureSpots();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static TreasureHunt getInstance()
    {
    	return instance;
    }  
}