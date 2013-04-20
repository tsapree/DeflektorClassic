package pro.oneredpixel.deflektorclassic;

import android.app.Application;

public class DeflektorApp extends Application {

	public void onCreate() {
		super.onCreate();
		Deflektor.initInstance(this);
	};
}
