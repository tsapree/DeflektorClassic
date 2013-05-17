package pro.oneredpixel.deflektorclassic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelsState extends State {

	LevelsState(Deflektor defl) {
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
		drawLevelBox(120-12-8-24-8-24,12,1);
		drawLevelBox(120-12-8-24     ,12,2);
		drawLevelBox(120-12          ,12,3);
		drawLevelBox(120-12+8+24     ,12,4);
		drawLevelBox(120-12+8+24+8+24,12,5);
		drawLevelBox(120-12-8-24-8-24,12+8+24,6);
		drawLevelBox(120-12-8-24     ,12+8+24,7);
		drawLevelBox(120-12          ,12+8+24,8);
		drawLevelBox(120-12+8+24     ,12+8+24,9);
		drawLevelBox(120-12+8+24+8+24,12+8+24,10);
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
	
	void drawLevelBox(int x, int y, int levelNumber) {
		app.menu_putRegion(x, y, 24, 24, 0,32);
	};
	


}
