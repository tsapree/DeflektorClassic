package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {

	Button bPlay;
	Button bSettings;
	Button bAchievements;
	
	MenuState(Deflektor defl) {
		super(defl);
		// TODO Auto-generated constructor stub
		bPlay = new Button(120,80-8,0,0,true,"PLAY",null);
		bSettings = new Button(16, 160-32,0,0,false,"SETTINGS",null);
		bAchievements = new Button(240-16-16-12*8, 160-32,0,0,false,"ACHIEVEMENTS",null);
	}
	
	public boolean tap(float x, float y, int tapCount, int button) {
		int tapx=(int)(x-app.winX);
		int tapy=(int)(y-app.winY);
		
		if (bPlay.checkRegion(tapx,tapy)) 
			app.gotoAppState(Deflektor.APPSTATE_SELECTLEVEL);
		return false;
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
		app.spr_putRegion(240/2-128/2, 8, 128, 16, 0, 56+144);
		
		//app.drawButton(120, 80-8, "PLAY", true);
		app.drawButton(bPlay);
		app.drawButton(16, 160-32,"SETTINGS", false);
		app.drawButton(240-16-16-12*8, 160-32, "ACHIEVEMENTS", false);
		
		batch.end();
	};
	


}
