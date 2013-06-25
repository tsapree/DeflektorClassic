package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SettingsState extends State {
	
	Button bBack;
	
	Button bZX;
	Button bAmiga;
	Button bModern;
	//TODO: убрать для релизной версии
	Button bResetProgress;
	Button bUnlockLevels;
	Button bCheat;
	
	Button bMinusSensitivity;
	Button bPlusSensitivity;
	
	Button bDifficultyEasy;
	Button bDifficultyClassic;
	
	SettingsState(Deflektor defl) {
		super(defl);
		// TODO Auto-generated constructor stub
		
		bBack = new Button(8,160-8-24, 96,160);
		
		bZX = new Button(16+8*6+8+8, 32,0,0,false,"ZX");
		bAmiga = new Button(16+16+8*2+8*6+8+8, 32,0,0,false,"AMIGA");
		bModern = new Button(16+16+8*2+16+8*5+8*6+8+8, 32,0,0,false,"MODERN");
		
		bDifficultyEasy = new Button(16+12*6+8+8+8, 64,0,0,false,"EASY");
		bDifficultyClassic = new Button(16+4*8+8+12*6+8+16+8, 64,0,0,false,"CLASSIC");
		
		bMinusSensitivity = new Button(8+8*12+7*8,96, 64,160);
		bPlusSensitivity = new Button(8+24+24+8*12+7*8,96, 80,160);
		
		bResetProgress = new Button(16+24+8, 160-24-8,0,0,false,"RESET PROGRESS");
		bUnlockLevels = new  Button(16+24+8, 160-24-8,0,0,false,"UNLOCK  LEVELS");
		bCheat = new Button(16+8+8+15*8+16+8,160-24-8,0,0,false,"CHEAT");
		
		
		
	}
	
	public boolean tap(float x, float y, int tapCount, int button) {
		int tapx=(int)(x-app.winX)/app.sprScale;
		int tapy=(int)(y-app.winY)/app.sprScale;
		
		if (bBack.checkRegion(tapx,  tapy)) {
			app.playSound(Deflektor.SND_TAP);
			app.gotoAppState(Deflektor.APPSTATE_MENU);
		}
		
		if (bZX.checkRegion(tapx,  tapy) && app.appGfxId!=Deflektor.APPGFX_ZX) {
			app.playSound(Deflektor.SND_TAP);
			app.appGfxId=Deflektor.APPGFX_ZX;
			app.loadMedia();
			app.playMelody();
		};
		if (bAmiga.checkRegion(tapx,  tapy) && app.appGfxId!=Deflektor.APPGFX_AMIGA) {
			app.playSound(Deflektor.SND_TAP);
			app.appGfxId=Deflektor.APPGFX_AMIGA;
			app.loadMedia();
			app.playMelody();
		};
		//todo: modern
		
		if (bMinusSensitivity.checkRegion(tapx, tapy) && app.controlsSensitivity>1) {
			app.controlsSensitivity--;
			app.playSound(Deflektor.SND_TAP);
		}
		if (bPlusSensitivity.checkRegion(tapx, tapy) && app.controlsSensitivity<8) {
			app.controlsSensitivity++;
			app.playSound(Deflektor.SND_TAP);
		}

		
		if (app.difficultyClassic && bDifficultyEasy.checkRegion(tapx, tapy)) {
			app.playSound(Deflektor.SND_TAP);
			app.difficultyClassic=false;
		};
		if (!app.difficultyClassic && bDifficultyClassic.checkRegion(tapx, tapy)) {
			app.playSound(Deflektor.SND_TAP);
			app.difficultyClassic=true;
		};
		
		if (!app.cheat && bCheat.checkRegion(tapx,  tapy)) {
			app.cheat=true;
			app.playSound(Deflektor.SND_TAP);
			app.gotoAppState(Deflektor.APPSTATE_MENU);
		};
		
		if (app.unlockedLevel==60) {
			if (bResetProgress.checkRegion(tapx,  tapy)) {
				app.unlockedLevel=1;
				app.playSound(Deflektor.SND_TAP);
				app.gotoAppState(Deflektor.APPSTATE_MENU);
			}
		} else {
			if (bUnlockLevels.checkRegion(tapx,  tapy)) {
				app.unlockedLevel=60;
				app.playSound(Deflektor.SND_TAP);
				app.gotoAppState(Deflektor.APPSTATE_MENU);
			}
		}
		
		return false;
	}
	
	public boolean keyUp(int k) {
		if (k==Keys.BACK) {
			app.gotoAppState(Deflektor.APPSTATE_MENU);
			app.playSound(Deflektor.SND_TAP);
			return true;
		};
		return false;
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
		app.showString((240-8*8)/2, 8, "SETTINGS");
		
		app.drawButton(bBack);

		app.showString(8, 40, "DESIGN");
		if (app.appGfxId!=Deflektor.APPGFX_ZX) app.drawButton(bZX); else app.drawButton(bZX, 24, 176); 
		if (app.appGfxId!=Deflektor.APPGFX_AMIGA) app.drawButton(bAmiga); else app.drawButton(bAmiga, 24, 176);
		app.drawButton(bModern);
		
		app.showString(8, 72, "DIFFICULTY");
		if (app.difficultyClassic)  app.drawButton(bDifficultyEasy);    else app.drawButton(bDifficultyEasy, 24, 176);
		if (!app.difficultyClassic) app.drawButton(bDifficultyClassic); else app.drawButton(bDifficultyClassic, 24, 176);

		app.showString(8, 104, "SENSITIVITY");
		if (app.controlsSensitivity>1) app.drawButton(bMinusSensitivity); else app.drawButton(bMinusSensitivity, 24, 176); 
		app.showString(8+24+8+8*12+7*8,96+8, String.format("%d", app.controlsSensitivity));
		if (app.controlsSensitivity<8) app.drawButton(bPlusSensitivity); else app.drawButton(bPlusSensitivity, 24, 176);
		
		if (app.unlockedLevel==60) app.drawButton(bResetProgress);
		else app.drawButton(bUnlockLevels);
		if (!app.cheat) app.drawButton(bCheat);
		
		batch.end();
	};
	



}
