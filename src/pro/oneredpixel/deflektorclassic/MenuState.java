package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {

	Button bPlay;
	Button bSettings;
	Button bAchievements;
	Button bAbout;
	Button bSoundOn;
	Button bSoundOff;
	
	MenuState(Deflektor defl) {
		super(defl);
		bPlay = new Button(120-12,80-16,64,176);
		bSettings = new Button(240-24-8, 160-32,80,176);
		bAbout = new Button(8, 160-32,0,240);
		bAchievements = new Button(8+8+24, 160-32,16,240);
		bSoundOn = new Button(240-24-8-24-8, 160-32,112,176);
		bSoundOff = new Button(240-24-8-24-8, 160-32,112,160);
	}
	
	void start() {
		untouchButtons();
	};
	
	public boolean touchDown(int x, int y, int pointer, int button) {
		int touchx=(int)(x-app.winX)/app.sprScale;
		int touchy=(int)(y-app.winY)/app.sprScale;
		if (bPlay.checkRegion(touchx,touchy)) {
			bPlay.touched=true;
			app.playSound(Deflektor.SND_TAP);
		}
		if (bSettings.checkRegion(touchx,touchy)) {
			bSettings.touched = true;
			app.playSound(Deflektor.SND_TAP);
		}
		if (bAbout.checkRegion(touchx,touchy)) {
			bAbout.touched = true;
			app.playSound(Deflektor.SND_TAP);
		}
		if (bSoundOn.checkRegion(touchx,touchy)) {
			bSoundOn.touched = true;
			bSoundOff.touched = true;
			app.playSound(Deflektor.SND_TAP);
		}
		return false;
	}
	
	public boolean touchUp (int x, int y, int pointer, int button) {
		untouchButtons();
		return false;
	};
	
	void untouchButtons() {
		if (bPlay.touched||bSettings.touched||bSoundOn.touched||bAbout.touched) app.playSound(Deflektor.SND_UNTAP);
		bPlay.touched = false;
		bSettings.touched = false;
		bSoundOn.touched = false;
		bSoundOff.touched = false;
		bAbout.touched = false;
	};
	
	public boolean tap(float x, float y, int tapCount, int button) {
		int tapx=(int)(x-app.winX)/app.sprScale;
		int tapy=(int)(y-app.winY)/app.sprScale;
		
		if (bPlay.checkRegion(tapx,tapy)) {
			app.gotoAppState(Deflektor.APPSTATE_SELECTLEVEL);
			bPlay.touched = false;
		};
		if (bSettings.checkRegion(tapx,tapy)) { 
			app.gotoAppState(Deflektor.APPSTATE_SETTINGS);
			bSettings.touched = false;
		};
		if (bAbout.checkRegion(tapx,tapy)) {
			app.gotoAppState(Deflektor.APPSTATE_ABOUT);
			bAbout.touched = false;
		}
		if (bSoundOn.checkRegion(tapx,tapy)) {
			app.soundEnabled=!app.soundEnabled;
			app.stopMelody();
			app.playMelody();
			bSoundOn.touched = false;
			bSoundOff.touched = false;
		}
		return false;
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
		app.spr_putRegion(240/2-128/2, 8, 128, 16, 0, 56+144);
		
		app.drawButton(bPlay);
		app.drawButton(bSettings);
		//app.drawButton(bAchievements);
		app.drawButton(bAbout);
		if (app.soundEnabled) app.drawButton(bSoundOn);
		else app.drawButton(bSoundOff);
		
		batch.end();
	};



}
