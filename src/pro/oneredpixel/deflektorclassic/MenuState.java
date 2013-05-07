package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {

	MenuState(Deflektor defl) {
		super(defl);
		// TODO Auto-generated constructor stub
	}
	
	public boolean tap(float x, float y, int tapCount, int button) {
		app.gotoAppState(Deflektor.APPSTATE_GAME);
		return false;
	}

	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(app.camera.combined);
		batch.begin();
		app.menu_putRegion(240/2-128/2, 8, 128, 16, 0, 56);
		app.menu_putRegion(240/2-96/2, 64, 96, 24, 0, 32);
		app.menu_putRegion(240/2-80/2, 128, 80, 24, 0, 72);
		batch.end();
		// process user input
		//if(Gdx.input.isTouched()) {
		//	gotoAppState(APPSTATE_GAME);
		//	//Vector3 touchPos = new Vector3();
		//	//touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		//	//camera.unproject(touchPos);
		//	//touch(Gdx.input.getX()/16/2, Gdx.input.getY()/16/2);
		//}
	};
	


}
