package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AboutState extends State{
	Button bBack;
	
	AboutState(Deflektor defl) {
		super(defl);
		bBack = new Button(8,160-8-24, 96,160);
	}
	
	void start() {
		untouchButtons();
	};
	
	public boolean touchDown(int x, int y, int pointer, int button) {
		int touchx=(int)(x-app.winX)/app.sprScale;
		int touchy=(int)(y-app.winY)/app.sprScale;
		if (bBack.checkRegion(touchx,touchy)) {
			bBack.touched=true;
			app.playSound(Deflektor.SND_TAP);
		}
		return false;
	}
	
	public boolean touchUp (int x, int y, int pointer, int button) {
		untouchButtons();
		return false;
	};
	
	void untouchButtons() {
		if (bBack.touched) app.playSound(Deflektor.SND_UNTAP);
		bBack.touched = false;
	};
	
	public boolean tap(float x, float y, int tapCount, int button) {
		int tapx=(int)(x-app.winX)/app.sprScale;
		int tapy=(int)(y-app.winY)/app.sprScale;
		
		if (bBack.checkRegion(tapx,tapy)) {
			app.gotoAppState(Deflektor.APPSTATE_MENU);
			bBack.touched = false;
		};
		return false;
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
		
		app.showString((240-8*5)/2, 8, "ABOUT");
		
		app.drawButton(bBack);
		batch.end();
	};

}
