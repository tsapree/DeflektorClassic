package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class State {
	
	//live cycle:  create ........ start --- stop --- start --- stop .......... destroy 
	
	Deflektor app;
	
	State (Deflektor defl) {
		app=defl;
	}
	
	void create() {
		
	};
	
	void destroy() {
		
	};
	
	//init state for showing
	void start() {
		
	};
	
	//state stoped
	void stop() {
		
	};
	
	public void render(SpriteBatch batch) {
		
	};
	
	//------
	//--- controlling
	//------
	public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
		return false;
	}

	public boolean fling(float arg0, float arg1, int arg2) {
		return false;
	}

	public boolean longPress(float arg0, float arg1) {
		return false;
	}

	public boolean tap(float x, float y, int tapCount, int button) {
		return false;
	}

	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	} 

	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}
	
	public boolean zoom(float arg0, float arg1) {
		return false;
	}

}
