package pro.oneredpixel.deflektorclassic;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class DeflektorActivity extends AndroidApplication {
	@Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	        
	      AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
	      cfg.useGL20 = true;
	      cfg.useAccelerometer = false;
	      cfg.useCompass = false;
	      cfg.useWakelock = true;
	        
	      initialize(new Deflektor(), cfg);
	   }

}
