package Hunt;

public class TreasureTask implements Runnable {

	@Override
	public void run() {
		TreasureManager.getInstance().startTreasureHunt();
	}
}
