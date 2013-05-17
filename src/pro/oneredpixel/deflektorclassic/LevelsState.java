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
		drawLevelBox(120-12-8-24-8-24,20,1);
		drawLevelBox(120-12-8-24     ,20,2);
		drawLevelBox(120-12          ,20,3);
		drawLevelBox(120-12+8+24     ,20,4);
		drawLevelBox(120-12+8+24+8+24,20,5);
		drawLevelBox(120-12-8-24-8-24,20+8+24,6);
		drawLevelBox(120-12-8-24     ,20+8+24,7);
		drawLevelBox(120-12          ,20+8+24,8);
		drawLevelBox(120-12+8+24     ,20+8+24,9);
		drawLevelBox(120-12+8+24+8+24,20+8+24,10);
		drawLevelBox(120-12-8-24-8-24,20+8+24+8+24,11);
		drawLevelBox(120-12-8-24     ,20+8+24+8+24,12);
		drawLevelBox(120-12          ,20+8+24+8+24,13);
		drawLevelBox(120-12+8+24     ,20+8+24+8+24,14);
		drawLevelBox(120-12+8+24+8+24,20+8+24+8+24,15);
		drawLevelBox(120-12-8-24-8-24,20+8+24+8+24+8+24,16);
		drawLevelBox(120-12-8-24     ,20+8+24+8+24+8+24,17);
		drawLevelBox(120-12          ,20+8+24+8+24+8+24,18);
		drawLevelBox(120-12+8+24     ,20+8+24+8+24+8+24,19);
		drawLevelBox(120-12+8+24+8+24,20+8+24+8+24+8+24,20);
		
		app.menu_putRegion(8, 160/2-8, 16, 16, 32,32);
		app.menu_putRegion(240-8-16, 160/2-8, 16, 16, 48,32);
		
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
		int up=levelNumber/10;
		int lo=levelNumber-up*10;
		app.menu_putRegion(x+4, y+8, 8, 8, up*8,96);
		app.menu_putRegion(x+12, y+8, 8, 8, lo*8,96);
		if (app.openedLevel<levelNumber) app.menu_putRegion(x+15, y+15, 8, 8, 24,32);
	};
	


}
