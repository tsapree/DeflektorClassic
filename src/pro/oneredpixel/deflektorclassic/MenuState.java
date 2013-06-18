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
		//bPlay = new Button(120,80-8,0,0,true,"PLAY");
		bPlay = new Button(120-12,80-16,64,176);
		//bSettings = new Button(16, 160-32,0,0,false,"SETTINGS");
		bSettings = new Button(240-24-8, 160-32,80,176);
		bAchievements = new Button(8, 160-32,0,0,false,"A");
		bAbout = new Button(8+8+24, 160-32,0,0,false,"I");
		bSoundOn = new Button(240-24-8-24-8, 160-32,112,176);
		bSoundOff = new Button(240-24-8-24-8, 160-32,112,160);
	}
	
	public boolean tap(float x, float y, int tapCount, int button) {
		int tapx=(int)(x-app.winX)/app.sprScale;
		int tapy=(int)(y-app.winY)/app.sprScale;
		
		if (bPlay.checkRegion(tapx,tapy)) {
			app.gotoAppState(Deflektor.APPSTATE_SELECTLEVEL);
			app.playSound(Deflektor.SND_TAP);
		};
		if (bSettings.checkRegion(tapx,tapy)) { 
			app.gotoAppState(Deflektor.APPSTATE_SETTINGS);
			app.playSound(Deflektor.SND_TAP);
		};
		if (bSoundOn.checkRegion(tapx,tapy)) {
			app.soundEnabled=!app.soundEnabled;
			//app.playSound(Deflektor.SND_TAP);
			app.stopMelody();
			app.playMelody();
		}
		return false;
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
		app.spr_putRegion(240/2-128/2, 8, 128, 16, 0, 56+144);
		
		app.drawButton(bPlay);
		app.drawButton(bSettings);
		app.drawButton(bAchievements);
		app.drawButton(bAbout);
		if (app.soundEnabled) app.drawButton(bSoundOn);
		else app.drawButton(bSoundOff);
		
		batch.end();
	};



}
