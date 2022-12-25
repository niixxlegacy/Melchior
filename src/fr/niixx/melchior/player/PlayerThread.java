package fr.niixx.melchior.player;

public class PlayerThread extends Thread {
	private int duration;
	private Player parent;
	
	public PlayerThread(int ms, Player pl) {
		duration = ms;
		parent = pl;
	}
	
	public void run() {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			return;
		}
		parent.handleMediaEndEvent();
	}
}