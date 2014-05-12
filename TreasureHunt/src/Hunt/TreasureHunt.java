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
	public static final long TASK_PERIOD = 60L*60L*2L;
	public boolean started = false;

    @Override
    public void onEnable(){
    	instance = this;
    	TreasureManager thm = new TreasureManager();
    	
		try {
			TreasureManager.getInstance().loadPossibleTreasure();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    	try {
			TreasureManager.getInstance().loadTreasureSpots();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	this.getCommand("hunt").setExecutor(new CommandListener());
    	this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    	this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TreasureTask(), 0L, 20L*TASK_PERIOD);
    }
 
    @Override
    public void onDisable() {
    	
		try {
			TreasureManager.getInstance().savePossibleTreasure();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    	try {
			TreasureManager.getInstance().saveTreasureSpots();
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