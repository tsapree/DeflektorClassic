package pro.oneredpixel.deflektorclassic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener, OnTouchListener {

	ImageView iv;
	Deflektor def;
	Bitmap bmcheck;
	
	boolean killThreadsOnDestroyActivity = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		def=Deflektor.getInstance();
		def.t.link(this);
		
		iv=(ImageView) findViewById(R.id.iv1);
        iv.setOnClickListener(this);
        iv.setOnTouchListener(this);

		iv.setImageBitmap(def.getBitmap());
		
	}
	
	public Object onRetainNonConfigurationInstance() {
    	killThreadsOnDestroyActivity=false;
	  	def.t.unlink();
	    return null;
	};
	
    protected void onDestroy() {
		super.onDestroy();
	
		def.t.unlink();
		if (killThreadsOnDestroyActivity) {
			def.t.destroy();
		}

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent ev) {
		if (ev.getAction()==MotionEvent.ACTION_DOWN) {
			int x = (int)(ev.getX()/(arg0.getWidth()/Deflektor.field_width));
			int y = (int)(ev.getY()/(arg0.getHeight()/Deflektor.field_height));
			def.touch(x, y);
			//iv.invalidate();
			return true;
		}
		return false;
	}

}
