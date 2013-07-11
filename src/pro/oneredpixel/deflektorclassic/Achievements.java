package pro.oneredpixel.deflektorclassic;

import com.google.android.gms.games.GamesClient;

public class Achievements {
	boolean explicitSignOut = false;
	boolean innSignInFlow = false; // set to true when you're in the middle of the
	               // sign in flow, to know you should not attempt
	               // to connect on onStart()
	GamesClient gamesClient;  // initialized in onCreate

	void init() {
		gamesClient=new GamesClient();
	}
	
	void connect() {
		gamesClient.connect();

	}
	
}
