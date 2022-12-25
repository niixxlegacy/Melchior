package fr.niixx.melchior;

import java.util.HashMap;

import fr.niixx.melchior.casparsocket.CasparSocket;
import fr.niixx.melchior.player.Player;

public interface Registry {
	HashMap<String, String> config = new HashMap<>();
	CasparSocket casparsocket = new CasparSocket();
	Player activeplayer = new Player();
}
