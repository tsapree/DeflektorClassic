package pro.oneredpixel.deflektorclassic;

import java.util.concurrent.TimeUnit;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.Toast;

public class DefThreads {
	
	public final static int DACT_TOAST = 0;
	public final static int DACT_GFXUPDATE = 1;

	MainActivity activity;
	Handler h;
	Thread g;
	Deflektor def;
	
	boolean shutThreads = false;
	
    void link(MainActivity m) {
    	activity=m;
    }
    
    void unlink() {
    	activity=null;
    }
	
	void create() {
		shutThreads=false;
		h = new Handler() {
		    public void handleMessage(android.os.Message msg) {
		    	try {
		    		//сведение вероятности падения при повороте экрана к минимуму
					while (activity==null) 
						TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
		    	switch (msg.what) {
	    		case DACT_GFXUPDATE:
	    			Bitmap b=Deflektor.getInstance().bm;
	    			if (activity.bmcheck!=b) {
	    				activity.iv.setImageBitmap(b);
	    				activity.bmcheck=b;
	    			}
	    			activity.iv.invalidate();
	    			break;
		    	case DACT_TOAST:
		    		Toast.makeText(activity, (String)msg.obj, Toast.LENGTH_LONG).show();
		    		break;
		    	}
		    };
		};
	
		g = new Thread(new Runnable() {
			public void run() {
				while(shutThreads!=true) {
					try {
						if ((Deflektor.getInstance()!=null) && (Deflektor.getInstance().DoPeriodGfxTask())) {
							h.removeMessages(DACT_GFXUPDATE);
							h.sendEmptyMessage(DACT_GFXUPDATE);
							TimeUnit.MILLISECONDS.sleep(50);
						} else TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					};
				}
			}
		});
		g.start();
		
	};
	
	void destroy() {
		shutThreads=true;
		if (g!=null) while (g.isAlive());
		g=null;
		shutThreads=false;
	};
	
}
