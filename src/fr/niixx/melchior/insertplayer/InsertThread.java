package fr.niixx.melchior.insertplayer;

public class InsertThread extends Thread {
	private int duration;
	private int layer;
	private InsertPlayer parent;
	
	public InsertThread(int ms, InsertPlayer pl, int lr) {
		duration = ms;
		parent = pl;
		layer = lr;
	}
	
	public void run() {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			return;
		}
		parent.clearLayer(layer);
	}
}