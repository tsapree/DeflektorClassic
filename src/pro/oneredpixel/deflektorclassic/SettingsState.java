package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SettingsState extends State {
	
	Button bZX;
	Button bAmiga;
	Button bModern;
	
	SettingsState(Deflektor defl) {
		super(defl);
		// TODO Auto-generated constructor stub
		
		bZX = new Button(16, 32,0,0,false,"ZX");
		bAmiga = new Button(16+16+8*2, 32,0,0,false,"AMIGA");
		bModern = new Button(16+16+8*2+16+8*5, 32,0,0,false,"MODERN");
		
	}
	
	public boolean tap(float x, float y, int tapCount, int button) {
		int tapx=(int)(x-app.winX)/app.sprScale;
		int tapy=(int)(y-app.winY)/app.sprScale;
		
		//if (bPlay.checkRegion(tapx,tapy)) 
		//	app.gotoAppState(Deflektor.APPSTATE_SELECTLEVEL);
		return false;
	}
	
	public boolean keyUp(int k) {
		if (k==Keys.BACK) {
			app.gotoAppState(Deflektor.APPSTATE_MENU);
			return true;
		};
		return false;
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
		app.showString((240-8*8)/2, 8, "SETTINGS");
		
		app.drawButton(bZX);
		app.drawButton(bAmiga);
		app.drawButton(bModern);
		
		batch.end();
	};
	



}
