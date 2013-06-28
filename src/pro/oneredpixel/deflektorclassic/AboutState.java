package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AboutState extends State{
	Button bBack;
	
	AboutState(Deflektor defl) {
		super(defl);
		bBack = new Button(2,160-2-24, 96,160);
		bBack.box=false;
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
	
	public boolean keyUp(int k) {
		if (k==Keys.BACK || k==Keys.MENU) {
			app.gotoAppState(Deflektor.APPSTATE_MENU);
			app.playSound(Deflektor.SND_TAP);
			return true;
		};
		return false;
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
										 //12345678901234567890123456789012
		app.showString((240-8*16)/2, 8*1, "GREAT ZX VERSION");
		app.showString((240-8*23)/2, 8*3-4, "WRITTEN BY COSTA PANAYI");
		app.showString((240-8*22)/2, 8*4-2, "MUSIC BY BENN DANGLISH"); 
		app.showString((240-8*23)/2, 8*5, "(C)1987 VORTEX SOFTWARE");

		app.showString((240-8*20)/2, 8*8, "BEAUTY AMIGA VERSION");
		app.showString((240-8*23)/2, 8*10-4, "GRAPHICS BY STEVE KERRY");
		app.showString((240-8*21)/2, 8*11-2,"MUSIC BY BENN DAGLISH");
		app.showString((240-8*24)/2, 8*12,"(C)1988 GREMLIN GRAPHICS");

		app.showString((240-8*20)/2, 8*15,"THIS ANDROID VERSION");
		app.showString((240-8*23)/2, 8*17-4,"WRITTEN BY PAUL STAKHOV");
		app.showString((240-8*16)/2, 8*18-2,"2013 ONEREDPIXEL");

		
		app.drawButton(bBack);
		batch.end();
	};

}
