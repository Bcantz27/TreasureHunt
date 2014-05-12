package Hunt;

public class TreasureTask implements Runnable {

	@Override
	public void run() {
		if(TreasureHunt.getInstance().started)
			TreasureManager.getInstance().startTreasureHunt();
		else
			TreasureHunt.getInstance().started = true;
	}
}
