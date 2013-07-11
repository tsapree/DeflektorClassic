package pro.oneredpixel.deflektorclassic;

import pro.oneredpixel.deflektorclassic.GameHelper.GameHelperListener;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class DeflektorActivity extends AndroidApplication implements GameHelperListener {
	
	private GameHelper gHelper;
	
	public DeflektorActivity() {
		gHelper = new GameHelper(this);
		gHelper.enableDebugLog(true, "MYTAG");
	}

	
	@Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	        
	      AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
	      cfg.useGL20 = true;
	      cfg.useAccelerometer = false;
	      cfg.useCompass = false;
	      cfg.useWakelock = true;
	      
	      gHelper.setup(this);
	      initialize(new Deflektor(), cfg);
	   }

	@Override
	public void onStart(){
		super.onStart();
		gHelper.onStart(this);
	}
	
	@Override
	public void onStop(){
		super.onStop();
		gHelper.onStop();
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gHelper.onActivityResult(request, response, data);
	}
	
	@Override
	public void onSignInFailed() {
		System.out.println("sign in failed");
	}

	@Override
	public void onSignInSucceeded() {
		System.out.println("sign in succeeded");
	}

	public void Login() {
		try {
			runOnUiThread (new Runnable() {
				//@Override
				public void run() {
					gHelper.beginUserInitiatedSignIn();
				}
			} );
		} catch (final Exception ex) {
		}
	}

	public void LogOut() {
		try {
			runOnUiThread(new Runnable(){
			//@Override
				public void run() {
					gHelper.signOut();
				}
			});
		} catch (final Exception ex){
		}
	}

	public boolean getSignedIn() {
		return gHelper.isSignedIn();
	}

	public void getAchievements() {
		startActivityForResult(gHelper.getGamesClient().getAchievementsIntent(), 105);
	}
	
}
