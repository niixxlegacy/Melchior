package fr.niixx.melchior;

import java.util.HashMap;

import fr.niixx.melchior.casparsocket.CasparSocket;

public interface Registry {
	HashMap<String, String> config = new HashMap<>();
	CasparSocket casparsocket = new CasparSocket();
}
