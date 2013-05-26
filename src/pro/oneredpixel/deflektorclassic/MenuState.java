package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {

	MenuState(Deflektor defl) {
		super(defl);
		// TODO Auto-generated constructor stub
	}
	
	public boolean tap(float x, float y, int tapCount, int button) {
		app.gotoAppState(Deflektor.APPSTATE_SELECTLEVEL);
		return false;
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
		app.spr_putRegion(240/2-128/2, 8, 128, 16, 0, 56+144);
		
		app.drawButton(120, 80-8, "PLAY", true);
		app.drawButton(16, 160-32,"SETTINGS", false);
		app.drawButton(240-16-16-12*8, 160-32, "ACHIEVEMENTS", false);
		
		batch.end();
	};
	


}
